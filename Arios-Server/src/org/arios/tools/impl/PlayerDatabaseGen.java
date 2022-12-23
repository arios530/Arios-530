package org.arios.tools.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.UIDInfo;
import org.arios.game.system.communication.ClanRank;
import org.arios.game.system.communication.Contact;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;
import org.arios.tools.StringUtils;

/**
 * Handles generating a player database.
 * @author Emperor
 */
public final class PlayerDatabaseGen {

	/**
	 * The main method.
	 * @param args The arguments.
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		GameWorld.prompt(false);
		List<String> failed = new ArrayList<>();
		int count = 0;
		for (File f : new File("./data/players/").listFiles()) {
			try {
				if (!f.getName().endsWith(".arios")) {
					continue;
				}
				String name = f.getName().replaceAll(".arios", "");
				Player player = Repository.getPlayerFile(name);
				PlayerSession p = new PlayerSession(name, player.getDetails().getPassword(), player.getUidInfo());
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				p.loginTime = dateFormat.getCalendar().getTime().getTime();
				p.rights = player.getDetails().getRights().ordinal();
				p.worldId = 1;
				p.communication.blocked.addAll(player.getCommunication().getBlocked());
				for (String c : player.getCommunication().getContacts().keySet()) {
					Contact contact = player.getCommunication().getContacts().get(c);
					p.communication.contacts.put(c, contact.getRank());
				}
				p.communication.privateChatSetting = player.getSettings().getPrivateChatSetting();
				p.communication.publicChatSetting = player.getSettings().getPublicChatSetting();
				p.communication.tradeSetting = player.getSettings().getTradeSetting();
				p.save();
				count++;
			} catch (Throwable t) {
				t.printStackTrace();
				failed.add(f.getName());
			}
		}
		if (!failed.isEmpty()) {
			System.out.println("Failed generating players " + failed);
		}
		System.out.println("Converted " + count + " players!");
		System.exit(0);
	}

	static final class PlayerSession {

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
		 * The time value of this login.
		 */
		private long loginTime = 0l;

		/**
		 * The time stamp of last disconnection.
		 */
		private long disconnectionTime = 0l;

		/**
		 * The communication information.
		 */
		private CommunicationInfo communication = new CommunicationInfo();

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

		@Override
		public boolean equals(Object o) {
			return username.equals(((PlayerSession) o).username);
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
			buffer.put((byte) 0);// EOF opcode.
			buffer.flip();
			try (RandomAccessFile raf = new RandomAccessFile("./out/" + username + ".arios", "rw"); FileChannel channel = raf.getChannel()) {
				channel.write(buffer);
				raf.close();
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	static final class CommunicationInfo {

		/**
		 * The maximum list size.
		 */
		public static final int MAX_LIST_SIZE = 200;

		/**
		 * The clan ranks.
		 */
		private final Map<String, ClanRank> contacts = new HashMap<>();

		/**
		 * The list of blocked players.
		 */
		private final List<String> blocked = new ArrayList<>();

		/**
		 * The player's clan name.
		 */
		private String clanName = "";

		/**
		 * The current clan this player is in.
		 */
		private String currentClan = "arios";

		/**
		 * The rank required for joining.
		 */
		private ClanRank joinRequirement = ClanRank.FRIEND;

		/**
		 * The rank required for messaging.
		 */
		private ClanRank messageRequirement = ClanRank.NONE;

		/**
		 * The rank required for kicking members.
		 */
		private ClanRank kickRequirement = ClanRank.OWNER;

		/**
		 * The rank required for loot-share.
		 */
		private ClanRank lootRequirement = ClanRank.ARIOS_MOD;

		/**
		 * The public chat setting.
		 */
		private int publicChatSetting = 0;

		/**
		 * The private chat setting.
		 */
		private int privateChatSetting = 0;

		/**
		 * The trade setting.
		 */
		private int tradeSetting = 0;

		/**
		 * Constructs a new {@code CommunicationInfo} {@code Object}.
		 */
		public CommunicationInfo() {
		}

		/**
		 * Saves the communication info.
		 * @param buffer The buffer.
		 */
		public void save(ByteBuffer buffer) {
			buffer.put((byte) contacts.size());
			for (String name : contacts.keySet()) {
				ByteBufferUtils.putString(name, buffer);
				buffer.put((byte) contacts.get(name).ordinal());
			}
			buffer.put((byte) blocked.size());
			for (String name : blocked) {
				ByteBufferUtils.putString(name, buffer);
			}
			ByteBufferUtils.putString(clanName, buffer);
			if (currentClan != null) {
				ByteBufferUtils.putString(currentClan, buffer.put((byte) 1));
			} else {
				buffer.put((byte) 0);
			}
			buffer.put((byte) joinRequirement.ordinal());
			buffer.put((byte) messageRequirement.ordinal());
			buffer.put((byte) kickRequirement.ordinal());
			buffer.put((byte) lootRequirement.ordinal());
			buffer.put((byte) publicChatSetting);
			buffer.put((byte) privateChatSetting);
			buffer.put((byte) tradeSetting);
		}
	}
}