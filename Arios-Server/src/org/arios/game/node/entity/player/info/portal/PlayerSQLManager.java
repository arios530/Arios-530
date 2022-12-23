package org.arios.game.node.entity.player.info.portal;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.PlayerDetails;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.entity.player.info.login.Response;
import org.arios.game.node.entity.player.link.IronmanMode;
import org.arios.game.system.SystemManager;
import org.arios.game.system.mysql.SQLColumn;
import org.arios.game.system.mysql.SQLEntryHandler;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.system.mysql.SQLTable;
import org.arios.game.system.mysql.impl.HighscoreSQLHandler;
import org.arios.game.system.mysql.impl.PlayerSQLHandler;
import org.arios.game.system.task.TaskExecutor;
import org.arios.game.world.GameWorld;

/**
 * Handles the players SQL functions.
 * @author Vexia
 */
public final class PlayerSQLManager {

	/**
	 * The SQL table.
	 */
	private final SQLTable table = new SQLTable(new SQLColumn("password", String.class), new SQLColumn("salt", String.class), new SQLColumn("rights", Integer.class), new SQLColumn("donatorType", Integer.class), new SQLColumn("credits", Integer.class), new SQLColumn("icon", Integer.class), new SQLColumn("perks", String.class), new SQLColumn("ip", String.class), new SQLColumn("mac", String.class), new SQLColumn("serial", String.class), new SQLColumn("computerName", String.class), new SQLColumn("netWorth", BigInteger.class), new SQLColumn("ironManMode", String.class), new SQLColumn("bank", String.class), new SQLColumn("inventory", String.class), new SQLColumn("equipment", String.class), new SQLColumn("ge", String.class));

	/**
	 * The details.
	 */
	private final PlayerDetails details;

	/**
	 * Constructs a new {@code PlayerSQLManager} {@code Object}.
	 * @param details the details.
	 */
	public PlayerSQLManager(final PlayerDetails details) {
		this.details = details;
	}

	/**
	 * Parses the sql on login.
	 */
	public void parse() {
		details.getSqlManager().setDefaultTable();
		SQLEntryHandler.read(new PlayerSQLHandler(details));
		details.getSqlManager().setSQLData();
	}

	/**
	 * Saves the sql data.
	 * @param player the player.
	 */
	public void save(final Player player) {
		if (!GameWorld.isEconomyWorld() || GameWorld.getSettings().isDevMode() && !SystemManager.getSystemConfig().isAdmin(player.getName())) {
			return;
		}
		TaskExecutor.executeSQL(new Runnable() {
			@Override
			public void run() {
				if (player != null) {
					table.getColumn("netWorth").updateValue(player.getMonitor().getNetworth());
					table.getColumn("ironManMode").updateValue(player.getIronmanManager().getMode().name());
					table.getColumn("bank").updateValue(player.getBank().format());
					table.getColumn("inventory").updateValue(player.getInventory().format());
					table.getColumn("equipment").updateValue(player.getEquipment().format());
					table.getColumn("ge").updateValue(player.getGrandExchange().format());
				}
				SQLEntryHandler.write(new PlayerSQLHandler(getDetails()));
				if (player != null) {
					SQLEntryHandler.write(new HighscoreSQLHandler(player));
				}
			}
		});
	}

	/**
	 * Saves the data without a player instance.
	 */
	public void save() {
		save(null);
	}

	/**
	 * Sets the data from the SQL table.
	 */
	@SuppressWarnings("deprecation")
	public void setSQLData() {
		if ((int) table.getColumn("rights").getValue() == -1) {
			table.getColumn("rights").setValue(0);
		}
		details.setPassword((String) table.getColumn("password").getValue());
		details.getPortal().setDonatorType((int) table.getColumn("donatorType").getValue() != -1 ? DonatorType.values()[(int) table.getColumn("donatorType").getValue()] : null);
		details.setRights(Rights.forId((Integer) table.getColumn("rights").getValue()));
		details.getPortal().getShop().setCredits((Integer) table.getColumn("credits").getValue());
		details.getPortal().getIconManager().setIcon((Integer) table.getColumn("icon").getValue());
		details.getShop().parsePerks((String) table.getColumn("perks").getValue());
		table.getColumn("ip").updateValue(getAddressLog((String) table.getColumn("ip").getValue(), details.getInfo().getIp()));
		table.getColumn("mac").updateValue(getAddressLog((String) table.getColumn("mac").getValue(), details.getInfo().getMac()));
		table.getColumn("serial").updateValue(getAddressLog((String) table.getColumn("serial").getValue(), details.getInfo().getSerial()));
		table.getColumn("computerName").updateValue(getAddressLog((String) table.getColumn("computerName").getValue(), details.getInfo().getCompName()));
		if ((int) table.getColumn("icon").getValue() == 0 && details.getPortal().isDonator()) {
			table.getColumn("icon").updateValue(details.getPortal().getDonatorType().getIcon().getId());
			details.getPortal().getIconManager().setIcon(details.getPortal().getDonatorType().getIcon());
		}
	}

	/**
	 * Sets the default table values.
	 */
	public void setDefaultTable() {
		table.getColumn("credits").setValue(details.getShop().getCredits());
		table.getColumn("password").setValue(details.getPassword());
		table.getColumn("rights").setValue(details.getRights().ordinal());
		table.getColumn("donatorType").setValue(details.getPortal().isDonator());
		table.getColumn("icon").setValue(details.getPortal().getIconManager().getIcon().getId());
		table.getColumn("donatorType").setValue(details.getPortal().getDonatorValue());
		table.getColumn("perks").setValue("");
		table.getColumn("salt").setValue(details.getPassword().substring(0, 29));
		table.getColumn("ip").setValue(details.getInfo().getIp() == null ? "" : details.getInfo().getIp());
		table.getColumn("mac").setValue(details.getInfo().getMac() == null ? "" : details.getInfo().getMac());
		table.getColumn("serial").setValue(details.getInfo().getSerial() == null ? "" : details.getInfo().getSerial());
		table.getColumn("computerName").setValue(details.getInfo().getCompName() == null ? "" : details.getInfo().getCompName());
		table.getColumn("netWorth").setValue(0);
		table.getColumn("ironManMode").setValue(IronmanMode.NONE.name());
	}

	/**
	 * Gets the table.
	 * @return The table.
	 */
	public SQLTable getTable() {
		return table;
	}

	/**
	 * Gets the details.
	 * @return The details.
	 */
	public PlayerDetails getDetails() {
		return details;
	}

	/**
	 * Gets the address log.
	 * @param original the original log.
	 * @param address the address.
	 * @return the address.
	 */
	private String getAddressLog(String original, String address) {
		String log = "";
		if (original != null && original.length() > 0) {
			log += original;
			if (log.charAt(log.length() - 1) != '|') {
				log += "|";
			}
		}
		if (address != null && address.length() > 0 && (original == null || !original.contains(address))) {
			log += address + "|";
		}
		if (log.length() > 0 && log.charAt(log.length() - 1) == '|') {
			log = log.substring(0, log.length() - 1);
		}
		if (log == null) {
			log = "";
		}
		return log;
	}

	/**
	 * Checks if an sql account was created under a name.
	 * @param name the name.
	 * @param field the field
	 * @return {@code True} if so.
	 */
	public static boolean hasSqlAccount(String name, String field) throws SQLException {
		Connection connection = SQLManager.getConnection();
		if (connection == null) {
			return true;
		}
		ResultSet result = null;
		PreparedStatement statement;
		statement = connection.prepareStatement("SELECT * FROM " + "members" + " WHERE " + "" + field + "='" + name.toLowerCase() + "' LIMIT 1");
		result = statement.executeQuery();
		if (result == null || !result.next()) {
			SQLManager.close(connection);
			return false;
		}
		SQLManager.close(connection);
		return true;
	}

	/**
	 * Checks if a username & password are correct.
	 * @param name the name.
	 * @param pass the pass.
	 * @return the response.
	 * @throws SQLException the exception if thrown.
	 */
	public static Response getCredentialResponse(PlayerDetails details, String name, String pass) throws SQLException {
		if (!SQLManager.isInitialized()) {
			return Response.INVALID_CREDENTIALS;
		}
		Connection connection = SQLManager.getConnection();
		if (connection == null) {
			details.parse(false);
			if (SystemManager.getEncryption().checkPassword(pass, details.getPassword())) {
				return Response.INVALID_CREDENTIALS;
			}
			return Response.WEBSITE_ERROR;
		}
		ResultSet result = null;
		PreparedStatement statement;
		statement = connection.prepareStatement("SELECT * FROM " + "members" + " WHERE " + "" + "username" + "='" + name.toLowerCase() + "' LIMIT 1");
		result = statement.executeQuery();
		if (result == null || !result.next()) {
			SQLManager.close(connection);
			return Response.NON_CREATED_ACCOUNT;
		}
		String realPass = result.getString("password");
		if (SystemManager.getEncryption().checkPassword(pass, realPass)) {
			SQLManager.close(connection);
			return Response.SUCCESSFUL;
		}
		SQLManager.close(connection);
		return Response.INVALID_CREDENTIALS;
	}

}
