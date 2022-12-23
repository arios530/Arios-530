package org.arios.world;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.arios.ServerConstants;
import org.arios.net.packet.IoBuffer;
import org.arios.system.communication.ClanRepository;
import org.arios.system.communication.CommunicationInfo;
import org.arios.system.mysql.SQLManager;
import org.arios.system.util.ByteBufferUtils;
import org.arios.system.util.EncryptionManager;
import org.arios.system.util.StringUtils;
import org.arios.world.info.UIDInfo;

/**
 * Represents a player session.
 * @author Emperor
 *
 */
public final class PlayerSession {

	/**
	 * The username.
	 */
	private final String username;

	/**
	 * The password.
	 */
	private String password;

	/**
	 * The player's UID info.
	 */
	private UIDInfo uid;

	/**
	 * The player's display name.
	 */
	private String displayName;

	/**
	 * The player's rights.
	 */
	private int rights;

	/**
	 * The world id.
	 */
	private int worldId;

	/**
	 * If the player session is active.
	 */
	private boolean active;

	/**
	 * The last login.
	 */
	private long lastLogin = -1l;

	/**
	 * The time value of this login.
	 */
	private long loginTime = 0l;

	/**
	 * The time stamp of last disconnection.
	 */
	private long disconnectionTime = 0l;

	/**
	 * How long the player is muted for.
	 */
	private long muteTime;

	/**
	 * How long the player is banned for.
	 */
	private long banTime;

	/**
	 * The last world the player logged in.
	 */
	private int lastWorld = -1;

	/**
	 * The player's communication info.
	 */
	private CommunicationInfo communication = new CommunicationInfo(this);

	/**
	 * The game server the player is currently in.
	 */
	private GameServer world;

	/**
	 * The current clan.
	 */
	private ClanRepository clan;

	/**
	 * The chat icon.
	 */
	private int chatIcon;

	/**
	 * Constructs a new {@code PlayerSession} {@code Object}.
	 * @param username The username.
	 * @param password The password.
	 * @param ipAddress The ip address.
	 * @param macAddress The mac address.
	 * @param computerName The computer name.
	 * @param serial The motherboard serial key.
	 */
	public PlayerSession(String username, String password, UIDInfo uid) {
		this.username = username;
		this.password = password;
		this.uid = uid;
	}

	/**
	 * Gets the player session for the given username.
	 * @param name The username.
	 * @param load If the player should be loaded if not active.
	 * @return The player for the given username.
	 */
	public static PlayerSession get(String name, boolean load) {
		PlayerSession player = WorldDatabase.getPlayer(name);
		if (player != null) {
			return player;
		}
		if (load) {
			player = new PlayerSession(name, name, new UIDInfo());
			if (!player.parse(false)) {
				return null;
			}
		}
		return player;
	}

	@Override
	public boolean equals(Object o) {
		return username.equals(((PlayerSession) o).username);
	}

	/**
	 * Sends the account information to the game server upon login.
	 * @param buffer The buffer to write on.
	 */
	public void sendAccountInfo(IoBuffer buffer) {
		buffer.putLong(lastLogin);
		buffer.putLong(muteTime);
		buffer.put((byte) communication.getPublicChatSetting());
		buffer.put((byte) communication.getPrivateChatSetting());
		buffer.put((byte) communication.getTradeSetting());
	}

	/**
	 * Saves the player session details.
	 * @param directory The directory to save to.
	 */
	public void save() {
		ByteBuffer buffer = ByteBuffer.allocate(32676);
		buffer.put((byte) 1).putLong(loginTime);
		ByteBufferUtils.putString(password, buffer.put((byte) 2));
		buffer.put((byte) 3).put((byte) rights);
		uid.save(buffer.put((byte) 4));
		if (displayName != null && !displayName.equals(StringUtils.formatDisplayName(username))) {
			buffer.put((byte) 5);
			ByteBufferUtils.putString(displayName, buffer);
		}
		buffer.put((byte) 8).putLong(disconnectionTime);
		buffer.put((byte) 9).put((byte) worldId);
		communication.save(buffer.put((byte) 10));
		buffer.put((byte) 0);//EOF opcode.
		buffer.flip();
		try (RandomAccessFile raf = new RandomAccessFile("./store/players/" + username + ".arios", "rw"); FileChannel channel = raf.getChannel()) {
			channel.write(buffer);
			raf.close();
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the player's details.
	 */
	public void parse() {
		parse(true);
	}

	/**
	 * Method used to parse the details of this class.
	 * @param create If a new file should be created if the file did not exist.
	 * @return {@code True} if the file existed already.
	 */
	public boolean parse(boolean create) {
		password = EncryptionManager.getInstance().hashPassword(password);
		final File file = new File("./store/players/" + username + ".arios");
		if (!file.exists()) {
			if (create) {
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
					this.lastLogin = buffer.getLong();
					break;
				case 2:
					this.password = ByteBufferUtils.getString(buffer);
					break;
				case 3:
					this.rights = buffer.get();
					break;
				case 4:
					uid.parse(buffer);
					break;
				case 5:
					displayName = ByteBufferUtils.getString(buffer);
					break;
				case 6:
					buffer.getLong();
					break;
				case 7:
					buffer.getLong();
					break;
				case 8:
					disconnectionTime = buffer.getLong();
					break;
				case 9:
					lastWorld = buffer.get() & 0xFF;
					break;
				case 10:
					communication.parse(buffer);
					break;
				default:
					System.err.println("Unknown opcode with player details - " + opcode + "!");
					break;
				}
			}
			raf.close();
			channel.close(); 
		} catch (Exception e) {
			System.err.println("Failed to load player " + username + "!");
			e.printStackTrace();
		}
		Connection connection = SQLManager.getConnection();
		if (connection == null) {
			return false;
		}
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM members WHERE username ='" + username + "'");
			if (set == null || !set.next()) {
				SQLManager.close(connection);
				return false;
			}
			muteTime = set.getLong("muteTime");
			banTime = set.getLong("banTime");
		} catch (SQLException e) {
			e.printStackTrace();
			SQLManager.close(connection);
			return false;
		}
		SQLManager.close(connection);
		return true;
	}

	/**
	 * Gets the chat icon.
	 * @return The chat icon.
	 */
	public int getChatIcon() {
		return chatIcon;
	}

	/**
	 * Checks if the player has just moved worlds.
	 * @return {@code True} if so.
	 */
	public boolean hasMovedWorld() {
		if (rights == 2) {
			return false;
		}
		return System.currentTimeMillis() - disconnectionTime < ServerConstants.WORLD_SWITCH_DELAY;
	}

	/**
	 * Configures the player session.
	 */
	public void configure() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		setLoginTime(dateFormat.getCalendar().getTime().getTime());
		ClanRepository clan = ClanRepository.getClans().get(username);
		if (clan != null) {
			clan.setOwner(this);
		}
	}

	/**
	 * Gets the username value.
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password value.
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the ipAddress value.
	 * @return The ipAddress.
	 */
	public String getIpAddress() {
		return uid.getIp();
	}

	/**
	 * Gets the macAddress value.
	 * @return The macAddress.
	 */
	public String getMacAddress() {
		return uid.getMac();
	}

	/**
	 * Gets the computerName value.
	 * @return The computerName.
	 */
	public String getComputerName() {
		return uid.getCompName();
	}

	/**
	 * Gets the worldId value.
	 * @return The worldId.
	 */
	public int getWorldId() {
		return worldId;
	}

	/**
	 * Sets the worldId value.
	 * @param worldId The worldId to set.
	 */
	public void setWorldId(int worldId) {
		this.worldId = worldId;
	}

	/**
	 * Gets the active value.
	 * @return The active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active value.
	 * @param active The active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the serialKey value.
	 * @return The serialKey.
	 */
	public String getSerialKey() {
		return uid.getSerial();
	}

	/**
	 * Gets the loginTime value.
	 * @return The loginTime.
	 */
	public long getLoginTime() {
		return loginTime;
	}

	/**
	 * Sets the loginTime value.
	 * @param loginTime The loginTime to set.
	 */
	public void setLoginTime(long timeStamp) {
		this.loginTime = timeStamp;
	}

	/**
	 * Gets the lastLogin value.
	 * @return The lastLogin.
	 */
	public long getLastLogin() {
		return lastLogin;
	}

	/**
	 * Sets the lastLogin value.
	 * @param lastLogin The lastLogin to set.
	 */
	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * Gets the rights value.
	 * @return The rights.
	 */
	public int getRights() {
		return rights;
	}

	/**
	 * Sets the rights value.
	 * @param rights The rights to set.
	 */
	public void setRights(int rights) {
		this.rights = rights;
	}

	/**
	 * If the player is muted.
	 * @return {@code true} if so.
	 */
	public boolean isMuted() {
		return muteTime > System.currentTimeMillis();
	}

	/**
	 * Gets the muteTime value.
	 * @return The muteTime.
	 */
	public long getMuteTime() {
		return muteTime;
	}

	/**
	 * Sets the muteTime value.
	 * @param muteTime The muteTime to set.
	 */
	public void setMuteTime(long muteTime) {
		this.muteTime = muteTime;
	}

	/**
	 * If the player is banned.
	 * @return {@code true} if so.
	 */
	public boolean isBanned() {
		return banTime > System.currentTimeMillis();
	}

	/**
	 * Gets the banTime value.
	 * @return The banTime.
	 */
	public long getBanTime() {
		return banTime;
	}

	/**
	 * Sets the banTime value.
	 * @param banTime The banTime to set.
	 */
	public void setBanTime(long banTime) {
		this.banTime = banTime;
	}

	/**
	 * Gets the disconnectionTime value.
	 * @return The disconnectionTime.
	 */
	public long getDisconnectionTime() {
		return disconnectionTime;
	}

	/**
	 * Sets the disconnectionTime value.
	 * @param disconnectionTime The disconnectionTime to set.
	 */
	public void setDisconnectionTime(long disconnectionTime) {
		this.disconnectionTime = disconnectionTime;
	}

	/**
	 * Gets the lastWorld value.
	 * @return The lastWorld.
	 */
	public int getLastWorld() {
		return lastWorld;
	}

	/**
	 * Sets the lastWorld value.
	 * @param lastWorld The lastWorld to set.
	 */
	public void setLastWorld(int lastWorld) {
		this.lastWorld = lastWorld;
	}

	/**
	 * Gets the communication value.
	 * @return The communication.
	 */
	public CommunicationInfo getCommunication() {
		return communication;
	}

	/**
	 * Sets the communication value.
	 * @param communication The communication to set.
	 */
	public void setCommunication(CommunicationInfo communication) {
		this.communication = communication;
	}

	/**
	 * Gets the world value.
	 * @return The world.
	 */
	public GameServer getWorld() {
		return world;
	}

	/**
	 * Sets the world value.
	 * @param world The world to set.
	 */
	public void setWorld(GameServer world) {
		this.world = world;
	}

	/**
	 * Gets the clan value.
	 * @return The clan.
	 */
	public ClanRepository getClan() {
		return clan;
	}

	/**
	 * Sets the clan value.
	 * @param clan The clan to set.
	 */
	public void setClan(ClanRepository clan) {
		this.clan = clan;
	}

	/**
	 * Sets the chat icon.
	 * @param chatIcon
	 */
	public void setChatIcon(int chatIcon) {
		this.chatIcon = chatIcon;
	}

	@Override
	public String toString() {
		return "player [name=" + username + ", pass=" + password + ", ip=" + uid.getIp() + ", mac=" + uid.getMac() + ", comp=" + uid.getCompName() + ", msk=" + uid.getSerial() + "]";
	}

	/**
	 * Gets the uid.
	 * @return the uid.
	 */
	public UIDInfo getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 * @param uid the uid to set
	 */
	public void setUid(UIDInfo uid) {
		this.uid = uid;
	}
}