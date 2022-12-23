package org.arios.game.system.mysql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.node.entity.npc.drop.DropFrequency;
import org.arios.game.node.entity.npc.drop.NPCDropTables;
import org.arios.game.node.item.ChanceItem;
import org.arios.game.system.SystemLogger;
import org.arios.game.system.mysql.SQLEntryHandler;
import org.arios.game.system.mysql.SQLManager;


/**
 * Handles the parsing of npc drops.
 * @author Vexia
 *
 */
public class NPCDropSQLHandler extends SQLEntryHandler<Object> {

	/**
	 * Constructs a new {@Code NPCDropSQLHandler} {@Code Object}
	 */
	public NPCDropSQLHandler() {
		super(null, "", "", "");
	}

	@Override
	public void parse() throws SQLException {
		connection = getConnection();
		if (connection == null) {
			SQLManager.close(connection);
			return;
		}
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM npc_drops");
		ResultSet set = statement.executeQuery();
		while (set.next()) {
			parseDrop(set);
		}
		SQLManager.close(connection);
	}

	@Override
	public void create() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("");
		statement.executeUpdate();
		SQLManager.close(statement.getConnection());
	}

	@Override
	public void save() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("");
		statement.executeUpdate();
		SQLManager.close(statement.getConnection());
	}

	/**
	 * Parses an npc's drops from a result set.
	 * @param set the result set.
	 * @throws SQLException  the SQL exception if thrown.
	 */
	public void parseDrop(ResultSet set) throws SQLException {
		int id = set.getInt(1);
		String defaultTable = set.getString(2);
		String mainTable = set.getString(3);
		String charmTable = set.getString(4);
		final NPCDefinition def = NPCDefinition.forId(id);
		final NPCDropTables tables = new NPCDropTables(def);
		def.setDropTables(tables);
		tables.getDefaultTable().addAll(parseTable(defaultTable));
		tables.getMainTable().addAll(parseTable(mainTable));
		tables.getCharmTable().addAll(parseTable(charmTable));
		tables.prepare();
	}

	/**
	 * Parses the data into a chance item list.
	 * @param data the data.
	 * @return the list of chance items.
	 */
	private List<ChanceItem> parseTable(String data) {
		List<ChanceItem> table = new ArrayList<>();
		//make sure first row has empty space in it in database and is not NULL otherwise this if statement will return early.
		if (data.length() == 0) {
			return table;
		}
		String[] datas = data.split("~");
		String[] tokens = null;
		for (String d : datas) {
			tokens = d.replace(" ", "").replace("{", "").replace("}", "").split(",");
			try {
				table.add(new ChanceItem(Integer.valueOf(tokens[0].trim()), Integer.valueOf(tokens[1].trim()), Integer.valueOf(tokens[2].trim()), 1000, Double.parseDouble(tokens[3].trim()), DropFrequency.valueOf(tokens[4].trim()), tokens.length == 6 ? Integer.valueOf(tokens[5].trim()) : -1));
			} catch (Exception e)
			{
				SystemLogger.log("Exception occurred: " + e.getMessage());
			}

		}
		return table;
	}

	@Override
	public Connection getConnection() {
		return SQLManager.getConnection();
	}

}
