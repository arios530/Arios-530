package org.arios.game.node.entity.player.info;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.sql.SQLException;

import org.arios.ServerConstants;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.info.portal.PlayerPortal;
import org.arios.game.node.entity.player.info.portal.PlayerSQLManager;
import org.arios.game.node.entity.player.info.portal.PortalShop;
import org.arios.game.system.communication.CommunicationInfo;
import org.arios.game.system.task.TaskExecutor;
import org.arios.net.IoSession;
import org.arios.tools.StringUtils;

/**
 * Represents the details of a players account.
 * @author Vexia
 */
public class PlayerDetails {

	/**
	 * The player sql manager.
	 */
	private final PlayerSQLManager sqlManager = new PlayerSQLManager(this);

	/**
	 * The player portal.
	 */
	private final PlayerPortal portal = new PlayerPortal(this);

	/**
	 * The username.
	 */
	private final String username;

	/**
	 * The password.
	 */
	private String password;

	/**
	 * Represents the unique id.
	 */
	private int uid;

	/**
	 * The rights of the player.
	 */
	private Rights rights = Rights.REGULAR_PLAYER;

	/**
	 * The unique id info.
	 */
	private UIDInfo info = new UIDInfo();

	/**
	 * Represents the session.
	 */
	private IoSession session;

	/**
	 * Represents the last login.
	 */
	private long lastLogin = -1;

	/**
	 * If the sql data has been parsed.
	 */
	private boolean parsedSql;

	/**
	 * The players display name.
	 */
	private String displayName;

	/**
	 * The communication info.
	 */
	private final CommunicationInfo communicationInfo;

	/**
	 * Constructs a new {@code PlayerDetails}.
	 * @param username the username to set.
	 * @param password the password to set.
	 * @param session the session.
	 */
	public PlayerDetails(String username, String password, IoSession session) {
		this.username = username;
		this.password = password;
		this.session = session;
		this.displayName = username;
		this.communicationInfo = new CommunicationInfo();
		this.uid = username.hashCode();
	}

	/**
	 * Saves the player details.
	 * @param directory The directory to save to.
	 */
	public void save(String directory) {
		ByteBuffer buffer = ByteBuffer.allocate(32676);
		buffer.put((byte) 1);
		buffer.putLong(getLastLogin());
		buffer.put((byte) 2);
		ByteBufferUtils.putString(getPassword(), buffer);
		buffer.put((byte) 3);
		buffer.put((byte) getRights().toInteger());
		buffer.put((byte) 5);
		getPortal().save(buffer);
		// Don't use opcode 4 or 6, 7, 8, 9, 10,
		if (displayName != null && !displayName.equals(StringUtils.formatDisplayName(username))) {
			buffer.put((byte) 11);
			ByteBufferUtils.putString(displayName, buffer);
		}
		info.save(buffer.put((byte) 12));
		communicationInfo.save(buffer.put((byte) 13));
		buffer.put((byte) 0);// EOF opcode.
		buffer.flip();
		try (RandomAccessFile raf = new RandomAccessFile(directory + "players/details/" + username + ".arios", "rw"); FileChannel channel = raf.getChannel()) {
			channel.write(buffer);
			raf.close();
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses a player's details.
	 * @return {@code True} if the player file existed.
	 */
	public boolean parse() {
		return parse(true);
	}

	/**
	 * Parses a player's details.
	 * @return {@code True} if the player file existed.
	 */
	public boolean parse(boolean create) {
		final File file = new File(ServerConstants.PLAYER_DETAILS_PATH + username + ".arios");
		if (!file.exists()) {
			if (create) {
				TaskExecutor.executeSQL(new Runnable() {
					@Override
					public void run() {
						try {
							if ((PlayerSQLManager.hasSqlAccount(username, "username"))) {
								getSqlManager().parse();
								parsedSql = true;
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				save();
			}
			return false;
		}
		try (RandomAccessFile raf = new RandomAccessFile(file, "r"); FileChannel channel = raf.getChannel()) {
			final MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
			int opcode;
			while ((opcode = buffer.get() & 0xFF) != 0) {
				switch (opcode) {
				case 1:
					setLastLogin(buffer.getLong());
					break;
				case 2:
					password = ByteBufferUtils.getString(buffer);
					break;
				case 3:
					int right = buffer.get();
					rights = right == 0 ? Rights.REGULAR_PLAYER : right == 1 ? Rights.PLAYER_MODERATOR : right == 2 ? Rights.ADMINISTRATOR : Rights.REGULAR_PLAYER;
					break;
				case 4: // Don't remove
					ByteBufferUtils.getString(buffer);
					break;
				case 5:
					getPortal().parse(buffer);
					break;
				case 6: // Don't remove
					ByteBufferUtils.getString(buffer);
					break;
				case 7:
					for (int i = 0; i < 4; i++) {
						ByteBufferUtils.getString(buffer);
					}
					break;
				case 8:
				case 9:
				case 10:// dont use.
					ByteBufferUtils.getString(buffer);
					break;
				case 11:
					displayName = ByteBufferUtils.getString(buffer);
					break;
				case 12:
					info.parse(buffer);
					break;
				case 13:
					communicationInfo.parse(buffer);
					break;
				default:
					System.err.println("Unknown opcode with player details - " + opcode + " for player " + username + " !");
					break;
				}
			}
			raf.close();
			channel.close();
		} catch (Exception e) {
			System.err.println("Failed to load player " + username + "!");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Saves the player details.
	 */
	public void save() {
		save("./data/");
	}

	/**
	 * Checks if they have a display name.
	 * @return the name.
	 */
	public boolean hasDisplayName() {
		return displayName != null && !displayName.equals(username);
	}

	/**
	 * Gets the shop.
	 * @return the shop.
	 */
	public PortalShop getShop() {
		return portal.getShop();
	}

	/**
	 * Gets the sql manager.
	 * @return the sql manager.
	 */
	public PlayerSQLManager getSqlManager() {
		return sqlManager;
	}

	/**
	 * Gets the ip.
	 * @return the ip.
	 */
	public String getIp() {
		return session.getAddress();
	}

	/**
	 * Gets the rights.
	 * @return The rights.
	 */
	public Rights getRights() {
		return rights;
	}

	/**
	 * Sets the credentials.
	 * @param rights The credentials to set.
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * Gets the session.
	 * @return The session.
	 */
	public IoSession getSession() {
		return session;
	}

	/**
	 * Sets the session.
	 * @param session The session to set.
	 */
	public void setSession(IoSession session) {
		this.session = session;
	}

	/**
	 * Sets the password.
	 * @param password the password.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Gets the lastLogin.
	 * @return The lastLogin.
	 */
	public long getLastLogin() {
		return lastLogin;
	}

	/**
	 * Sets the lastLogin.
	 * @param lastLogin The lastLogin to set.
	 */
	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * Gets the username.
	 * @return The username.
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * Gets the portal.
	 * @return The portal.
	 */
	public PlayerPortal getPortal() {
		return portal;
	}

	/**
	 * Gets the uid.
	 * @return the uid.
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 * @param uid the uid.
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * Gets the password.
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the parsedSql.
	 * @return The parsedSql.
	 */
	public boolean isParsedSql() {
		return parsedSql;
	}

	/**
	 * Gets the displayName.
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the badisplayName.
	 * @param displayName the displayName to set.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the mac address.
	 * @return the address.
	 */
	public String getMacAddress() {
		return info.getMac();
	}

	/**
	 * Gets the computer name.
	 * @return the name.
	 */
	public String getCompName() {
		return info.getCompName();
	}

	/**
	 * Gets the ip address.
	 * @return the ip.
	 */
	public String getIpAddress() {
		if (info.getIp() == null) {
			return session.getAddress();
		}
		return info.getIp();
	}

	/**
	 * Gets the serial.
	 * @return the serial.
	 */
	public String getSerial() {
		return info.getSerial();
	}

	/**
	 * Gets the info.
	 * @return the info
	 */
	public UIDInfo getInfo() {
		return info;
	}

	/**
	 * Sets the info.
	 * @param info the info to set.
	 */
	public void setInfo(UIDInfo info) {
		this.info = info;
	}

	/**
	 * Gets the communicationInfo.
	 * @return the communicationInfo
	 */
	public CommunicationInfo getCommunication() {
		return communicationInfo;
	}

	@Override
	public String toString() {
		return "PlayerDetails [username=" + username + ", password=" + password + ", uid=" + uid + ", rights=" + rights + ", info=" + info + ", lastLogin=" + lastLogin + ", displayName=" + displayName + "]";
	}

}