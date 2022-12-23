package org.arios.game.system.mysql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.arios.cache.Cache;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.system.mysql.SQLEntryHandler;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.tools.TimeStamp;

/**
 * Handles the spawning of NPC's.
 * @author Vexia
 *
 */
public class NPCSpawnSQLHandler extends SQLEntryHandler<NPC> {

	/**
	 * The current npc being spawned.
	 */
	private NPC npc;

	/**
	 * Constructs a new {@Code NPCSpawnSqlHandler} {@Code Object}
	 */
	public NPCSpawnSQLHandler() {
		super(null, "", "", "");
	}

	/**
	 * Constructs a new {@Code NPCSpawnSqlHandler} {@Code Object}
	 * @param entry the entry.
	 * @param table the table.
	 * @param column the column.
	 * @param value the value.
	 */
	public NPCSpawnSQLHandler(NPC entry, String table, String column, String value) {
		super(entry, "npc_spawns", "npc_id", "" + entry.getId());
	}

	/**
	 * Runs the NPC Spawn SQL handler.
	 * @param args the args.
	 * @throws SQLException the exception if thrown.
	 */
	public static void main(String... args) throws SQLException {
		Cache.init();
		TimeStamp stamp = new TimeStamp();
		new NPCSpawnSQLHandler().parse();
		stamp.duration(true, "");
	}

	@Override
	public void parse() throws SQLException {
		connection = getConnection();
		if (connection == null) {
			SQLManager.close(connection);
			return;
		}
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM npc_spawns");
		ResultSet set = statement.executeQuery();
		while (set.next()) {
			parseNpc(set.getInt(1), set.getString(2));
		}
		SQLManager.close(statement.getConnection());
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
	 * Parses an npc id's spawns.
	 * @param id the id.
	 * @param data the data.
	 */
	public void parseNpc(int id, String data) {
		String[] datas = data.split("-");
		String[] tokens = null;
		for (String d : datas) {
			tokens = d.replace(" ", "").replace("{", "").replace("}", "").split(",");
			npc = NPC.create(id, Location.create(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2])));
			npc.setWalks(tokens[3].equals("1"));
			npc.setDirection(Direction.values()[Integer.valueOf(tokens[4])]);
			npc.setAttribute("spawned:npc", true);
			npc.init();
		}
		npc = null;
	}

	@Override
	public Connection getConnection() {
		return SQLManager.getConnection();
	}

}
