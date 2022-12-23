package org.arios.net.event;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.sql.SQLException;

import org.arios.cache.Cache;
import org.arios.cache.crypto.ISAACCipher;
import org.arios.cache.crypto.ISAACPair;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.info.ClientInfo;
import org.arios.game.node.entity.player.info.PlayerDetails;
import org.arios.game.node.entity.player.info.UIDInfo;
import org.arios.game.node.entity.player.info.login.LoginParser;
import org.arios.game.node.entity.player.info.login.LoginType;
import org.arios.game.node.entity.player.info.login.Response;
import org.arios.game.node.entity.player.info.portal.PlayerSQLManager;
import org.arios.game.system.SystemLogger;
import org.arios.game.system.task.TaskExecutor;
import org.arios.net.Constants;
import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.net.amsc.WorldCommunicator;
import org.arios.tools.StringUtils;

/**
 * Handles login reading events.
 * @author Emperor
 */
public final class LoginReadEvent extends IoReadEvent {

	/**
	 * The RSA exponent.
	 */
	public static final BigInteger RSA_KEY = new BigInteger("63836541338254930133129004074732382929998437615009296959260995188633082779361618777286690536401412536788693103949475863420785422077437411678826531544580956203799783573564225075359462174640338384017065666188771184000361929010560260535244941334940115723150494202345239634306833859051437359114435513508472366353");

	/**
	 * The RSA modulus.
	 */
	public static final BigInteger MODULUS = new BigInteger("119365899446067315932975991898363325061579719991294025359328021960040125142258621067848949689980866028232491082585431814345859060363748342297790362002830405818586025541018815563000741957417375211440504983329981059065255756529758598479962175681326119784430342275130902058984323109363665114655494006708620299283");

	/**
	 * Constructs a new {@code LoginReadEvent}.
	 * @param session The session.
	 * @param buffer The buffer with data to read from.
	 */
	public LoginReadEvent(IoSession session, ByteBuffer buffer) {
		super(session, buffer);
	}

	@Override
	public void read(IoSession session, ByteBuffer buffer) {
		int opcode = buffer.get() & 0xFF;
		if ((buffer.getShort() & 0xFFFF) != buffer.remaining()) {
			session.write(Response.BAD_SESSION_ID);
			return;
		}
		if (buffer.getInt() != Constants.REVISION) {// || buffer.getInt() != Constants.CLIENT_BUILD) {
			session.write(Response.UPDATED);
			return;
		}
		switch (opcode) {
		case 16: // Reconnect world login
		case 18: // World login
			decodeWorld(opcode, session, buffer);
			break;
		default:
			System.err.println("[Login] Unhandled login type [opcode=" + opcode + "]!");
			session.disconnect();
			break;
		}
	}

	/**
	 * Decodes a world login request.
	 * @param session The session.
	 * @param buffer The buffer to read from.
	 */
	private static void decodeWorld(final int opcode, final IoSession session, ByteBuffer buffer) {
		buffer.get(); // Memory?
		buffer.get();// no advertisement = 1
		buffer.get();// 1
		int windowMode = buffer.get();// Screen size mode
		int screenWidth = buffer.getShort(); // Screen size Width
		int screenHeight = buffer.getShort(); // Screen size Height
		int displayMode = buffer.get(); // Display mode
		byte[] data = new byte[24]; // random.dat data.
		buffer.get(data);
		ByteBufferUtils.getString(buffer);
		buffer.getInt();// Affiliate id
		buffer.getInt(); // Hash containing a bunch of settings
		buffer.getShort();//Current interface packet counter.
		for (int i = 0; i < Cache.getIndexes().length; i++) {
			int crc = Cache.getIndexes()[i] == null ? 0 : Cache.getIndexes()[i].getInformation().getInformationContainer().getCrc();
			if (crc != buffer.getInt() && crc != 0) {
				session.write(Response.UPDATED);
				return;
			}
		}
		buffer = getRSABlock(buffer);
		int[] isaacSeed = getISAACSeed(buffer);
		ISAACCipher inCipher = new ISAACCipher(isaacSeed);
		for (int i = 0; i < 4; i++) {
			isaacSeed[i] += 50;
		}
		ISAACCipher outCipher = new ISAACCipher(isaacSeed);
		session.setIsaacPair(new ISAACPair(inCipher, outCipher));
		session.setClientInfo(new ClientInfo(displayMode, windowMode, screenWidth, screenHeight));
		final String username = StringUtils.longToString(buffer.getLong());
		final String password = ByteBufferUtils.getString(buffer);
		final PlayerDetails details = new PlayerDetails(username, null, session);
		final ByteBuffer b = buffer;
		TaskExecutor.executeSQL(new Runnable() {
			@Override
			public void run() {
				try {
					Response response = PlayerSQLManager.getCredentialResponse(details, username, password);
					SystemLogger.log("res " + response);
					//This is false to prevent NON_CREATED_ACCOUNT (aka to allow client registration)
					//This false prevents password checking.
					if (false && response != Response.SUCCESSFUL) {
						session.write(response, true);
						return;
					}
					login(details, username, password, session, b, opcode);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Handles the login procedure after we check an acc is registered.
	 * @param username the username.
	 * @param password the password.
	 * @param session the session.
	 * @param buffer the byte buffer.
	 * @param opcode the opcode.
	 */
	private static void login(final PlayerDetails details, String username, String password, IoSession session, ByteBuffer buffer, int opcode) {
		LoginParser parser = new LoginParser(details, password, new UIDInfo(session.getAddress(), "Unspecified", "00-00-00-00", "Unspecified-000000000"), LoginType.fromType(opcode));
		if (WorldCommunicator.isEnabled()) {
			WorldCommunicator.register(parser);
		} else {
			TaskExecutor.executeSQL(parser);
		}
	}

	/**
	 * Gets the ISAAC seed from the buffer.
	 * @param buffer The buffer to read from.
	 * @return The ISAAC seed.
	 */
	public static int[] getISAACSeed(ByteBuffer buffer) {
		int[] seed = new int[4];
		for (int i = 0; i < 4; i++) {
			seed[i] = buffer.getInt();
		}
		return seed;
	}

	/**
	 * Gets the RSA block buffer.
	 * @param buffer The buffer to get the RSA block from.
	 * @return The RSA block buffer.
	 */
	public static ByteBuffer getRSABlock(ByteBuffer buffer) {
		byte[] rsaData = new byte[buffer.get() & 0xFF];
		buffer.get(rsaData);
		ByteBuffer block = ByteBuffer.wrap(rsaData);// ByteBuffer.wrap(new
													// BigInteger(rsaData).modPow(RSA_KEY,
													// MODULUS).toByteArray());
		int num = block.get();
		if (num != 10) {
			throw new IllegalArgumentException("Invalid RSA header " + num + "!");
		}
		return block;
	}

}