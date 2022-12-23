package org.arios.tools.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.arios.cache.Cache;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.eco.ge.GrandExchangeDatabase;
import org.arios.game.content.eco.ge.GrandExchangeEntry;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.system.task.TaskExecutor;
import org.arios.game.world.GameWorld;
import org.arios.parser.item.ItemConfiguration;

/**
 * Handles the price guide tool to populate the db.
 * @author Vexia
 */
public class PriceGuideTool {

	/**
	 * The main method.
	 * @param args the args.
	 * @throws Throwable the throwable.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		System.out.println(Cache.getItemDefinitionsSize());
		SQLManager.init();
		TaskExecutor.executeSQL(new Runnable() {

			@Override
			public void run() {
				push();
			}

		});
	}

	private static int inserted;

	private static ItemDefinition def;

	private static void push() {
		for (int i = 0; i < Cache.getItemDefinitionsSize(); i++) {
			def = ItemDefinition.forId(i);
			if (def.getName().equals("null")) {
				continue;
			}
			if (!def.isTradeable()) {
				continue;
			}
			if (!def.isUnnoted()) {
				continue;
			}
			if (i <= 13246) {// TEMP
				continue;
			}
			GrandExchangeEntry entry = GrandExchangeDatabase.getDatabase().get(def.getId());
			int gePrice = entry == null ? def.getConfiguration(ItemConfiguration.GE_PRICE, 0) : entry.getValue();
			PreparedStatement statement;
			Connection connection = SQLManager.getConnection();
			System.out.println("Inserting item - " + def.getName() + " (" + def.getId() + ") to the db! GE value=" + gePrice + ", lowAlch=" + def.getAlchemyValue(false) + ", highAlch=" + def.getAlchemyValue(true));
			try {
				statement = connection.prepareStatement("INSERT prices(id, name, ge_price, low_alch, high_alch) VALUES('" + def.getId() + "', '" + def.getName().replace("'", "''") + "', '" + gePrice + "', '" + def.getAlchemyValue(false) + "', '" + def.getAlchemyValue(true) + "')");
				statement.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error inserting! - " + e.getMessage());
				SQLManager.close(connection);
				continue;
			}
			SQLManager.close(connection);
			inserted++;

		}
		System.out.println("Inserted " + inserted + " item prices into the database.");
	}

	/**
	 * /**Connection connection = SQLManager.getConnection(); ResultSet result =
	 * null; PreparedStatement statement; statement =
	 * connection.prepareStatement("SELECT * FROM " + "prices" + " WHERE " + ""
	 * + "id" + "='" + i + "'"); result = statement.executeQuery(); if (result
	 * == null || result.next()) {//exists SQLManager.close(connection);
	 * System.out.println("Row already exists for " + def.getName() + "!");
	 * continue; }
	 */
	// PreparedStatement statement =
	// connection.prepareStatement("INSERT reports(reporter, reported, messages, reason) VALUES('"
	// + value + "', '" + entry.getVictim() + "', '" + entry.getMessages() +
	// "', '" + entry.getRule().name() + "')");

}
