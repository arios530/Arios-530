package org.arios.game.system.registry;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.info.portal.PlayerSQLManager;
import org.arios.game.system.SystemManager;
import org.arios.game.system.mysql.SQLEntryHandler;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.system.task.TaskExecutor;
import org.arios.net.Constants;
import org.arios.net.IoSession;

/**
 * Handles the registry of new accounts.
 * @author Vexia
 *
 */
public class AccountRegister extends SQLEntryHandler<RegistryDetails> {
	
	/**
	 * The table name.
	 */
	private static final String TABLE = "members";
	
	/**
	 * The column name.
	 */
	private static final String COLUMN = "username";
	
	/**
	 * The pattern compiler.
	 */
	private static final Pattern PATTERN = Pattern.compile("[a-z0-9 _]{1,12}");
	
	/**
	 * Constructs a new {@Code AccountRegister} {@Code Object}
	 * @param entry The registry entry.
	 */
	public AccountRegister(RegistryDetails entry) {
		super(entry, TABLE, COLUMN, entry.getUsername());
	}
	
	/**
	 * Reads the incoming opcode of an account register.
	 * @param session the session.
	 * @param opcode the opcode.
	 * @param buffer the buffer.
	 */
	public static void read(final IoSession session, int opcode, ByteBuffer buffer) {
		int day,month,year,country;
		switch (opcode) {
			case 147://details
				day = buffer.get();
				month = buffer.get();
				year = buffer.getShort();
				country = buffer.getShort();
				response(session, RegistryResponse.SUCCESS);
				break;
			case 186://username
				final String username = ByteBufferUtils.getString(buffer).replace(" ", "_").toLowerCase().replace("|", "");
				if (username.length() <= 0 || username.length() > 12) {
					response(session, RegistryResponse.INVALID_USERNAME);
					break;
				}
				if (!validUsername(username)) {
					response(session,RegistryResponse.INVALID_USERNAME);
					break;
				}
				TaskExecutor.executeSQL(new Runnable() {
					@Override
					public void run() {
						try {
							if (PlayerSQLManager.hasSqlAccount(username, "username")) {
								response(session, RegistryResponse.NOT_AVAILBLE_USER);
								return;
							}
							response(session, RegistryResponse.SUCCESS);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				break;
			case 36://Register details
				buffer.get();
				buffer = ByteBufferUtils.getRSABlock(buffer);
				buffer.getShort();
				int revision = buffer.getShort();//revision?
				if (revision != Constants.REVISION) {
					response(session, RegistryResponse.CANNOT_CREATE);
					break;
				}
				final String name = ByteBufferUtils.getString(buffer).replace(" ", "_").toLowerCase().replace("|", "");
				buffer.getInt();
				String password = ByteBufferUtils.getString(buffer);
				if (password.length() < 5 || password.length() > 20) {
					response(session, RegistryResponse.INVALID_PASS_LENGTH);
					break;
				}
				if (password.equals(name)) {
					response(session, RegistryResponse.PASS_SIMILAR_TO_USER);
					break;
				}
				if (!validUsername(name)) {
					response(session, RegistryResponse.INVALID_USERNAME);
					break;
				}
				buffer.getInt();
				buffer.getShort();
				day = buffer.get();
				month = buffer.get();
				buffer.getInt();
				year = buffer.getShort();
				country = buffer.getShort();
				buffer.getInt();
				@SuppressWarnings("deprecation")
				final RegistryDetails details = new RegistryDetails(name, SystemManager.getEncryption().hashPassword(password), new Date(year, month, day), country);
				TaskExecutor.executeSQL(new Runnable() {
					@Override
					public void run() {
						try {
							if (PlayerSQLManager.hasSqlAccount(name, "username")) {
								response(session, RegistryResponse.CANNOT_CREATE);
								return;
							}
							SQLEntryHandler.write(new AccountRegister(details));
							response(session, RegistryResponse.SUCCESS);
						} catch (SQLException e) {
							e.printStackTrace();
							response(session, RegistryResponse.CANNOT_CREATE);
						}
					}
				});
				break;
			default:
				System.err.println("Unhandled account registry opcode = " + opcode);
				break;
		}
	}
	
	@Override
	public void create() throws SQLException {}
	
	@Override
	public void parse() throws SQLException {}
	
	@Override
	public void save() throws SQLException {
		PreparedStatement statement = getWritingStatement(true, "password", "salt", "birthday", "countryCode");
		statement.setString(1, entry.getUsername());
		statement.setString(2, entry.getPassword());
		statement.setString(3, entry.getPassword().substring(0, 29));
		statement.setDate(4, entry.getBirth());
		statement.setInt(5, entry.getCountry());
		statement.executeUpdate();
		SQLManager.close(statement.getConnection());
	}
	
	/**
	 * Sends a registry response code.
	 * @param response the response.
	 */
	private static void response(IoSession session, RegistryResponse response) {
		ByteBuffer buf = ByteBuffer.allocate(100);
		buf.put((byte) response.getId());
		session.write(buf);
	}
	
	/**
	 * Checks if a username is valid.
	 * @return {@code True} if so.
	 */
	public static boolean validUsername(final String username) {
		Matcher matcher = PATTERN.matcher(username);
		return matcher.matches();
		
	}
	
	@Override
	public Connection getConnection() {
		return SQLManager.getConnection();
	}
	
}
