package org.arios.game.node.entity.player.info;

import java.nio.ByteBuffer;

import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.GameWorld;
import org.arios.parser.player.SavingModule;
import org.arios.tools.StringUtils;

/**
 * The unique info of a player.
 * @author Vexia
 */
public class UIDInfo implements SavingModule {

	/**
	 * The ip address.
	 */
	private String ip;

	/**
	 * The computer name.
	 */
	private String compName;

	/**
	 * The mac-address.
	 */
	private String mac;

	/**
	 * The motherboard serial of the user.
	 */
	private String serial;

	/**
	 * Constructs a new {@code UIDInfo} {@code Object}
	 */
	public UIDInfo() {
		/*
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code UIDInfo} {@code Object}
	 * @param ip the ip.
	 * @param compName the computer name.
	 * @param mac the mac.
	 * @param serial the serial.
	 */
	public UIDInfo(String ip, String compName, String mac, String serial) {
		this.ip = ip;
		this.compName = compName;
		this.mac = mac;
		this.serial = serial;
	}

	@Override
	public void save(ByteBuffer buffer) {
		save(buffer, ip, 1);
		save(buffer, compName, 2);
		save(buffer, mac, 3);
		save(buffer, serial, 4);
		buffer.put((byte) 0);
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		String ip = "", compName = "", mac = "", serial = "";
		while ((opcode = buffer.get()) != 0) {
			switch (opcode) {
			case 1:
				ip = ByteBufferUtils.getString(buffer);
				break;
			case 2:
				compName = ByteBufferUtils.getString(buffer);
				break;
			case 3:
				mac = ByteBufferUtils.getString(buffer);
				break;
			case 4:
				serial = ByteBufferUtils.getString(buffer);
				break;
			}
		}
		if (this.ip == null) {
			this.ip = ip;
		}
		if (this.compName == null) {
			this.compName = compName;
		}
		if (this.mac == null) {
			this.mac = mac;
		}
		if (this.serial == null) {
			this.serial = serial;
		}
	}

	/**
	 * Saves a string value to the buffer.
	 * @param buffer the buffer.
	 * @param value the value.
	 * @param opcode the opcode.
	 */
	private void save(ByteBuffer buffer, String value, int opcode) {
		if (value == null) {
			return;
		}
		ByteBufferUtils.putString(value, buffer.put((byte) opcode));
	}

	/**
	 * Gets the compName.
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * Gets the ip.
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Gets the mac.
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Gets the serial.
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Sets the ip.
	 * @param ip the ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Sets the compName.
	 * @param compName the compName to set.
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * Sets the mac.
	 * @param mac the mac to set.
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * Sets the serial.
	 * @param serial the serial to set.
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * Converts a to string in format mode for an admin or mod.
	 * @param admin the admin.
	 * @return the string.
	 */
	public String toString(Player player, Player target) {
		boolean admin = player.isAdmin();
		if (GameWorld.getSettings().isDevMode() && (!player.getName().equals("vexia") && !player.getName().equals("emperor"))) {
			admin = false;
		}
		String format = toString();
		if (!admin) {// formats for non-admins
			String[] tokens = format.split("serial=");
			format = format.replace("serial=", "uid=").replace(tokens[tokens.length - 1], "*****");
		}
		player.sendMessage("[----------Info Debug----------]");
		String[] lines = StringUtils.splitIntoLine(format, 60);
		player.sendMessages(lines);
		player.sendMessage("[-------------------------------]");
		return format;
	}

	@Override
	public String toString() {
		// make sure serials always at end
		return "[ip=" + ip + ", compName=" + compName + ", mac=" + mac + ", serial=" + serial + "]";
	}

}
