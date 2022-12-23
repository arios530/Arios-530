package org.arios.tools.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.arios.game.node.entity.player.Player;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.system.task.TaskExecutor;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;

/**
 * Creates a database for a player(s).
 * @author Vexia
 */
public class PlayerDatabaseCreator {

	/**
	 * The dump directory.
	 */
	private static final String DUMP_DIR = "data/players/";

	/**
	 * The main method.
	 * @param args the args.
	 * @throws Throwable the throwable.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		SQLManager.init();
		dumpAll();
		File[] files = new File("data/players/details/").listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				continue;
			}
			if (f.getName().contains(".ds") || f.isHidden()) {
				continue;
			}
			if (!new File("data/players/" + f.getName()).exists()) {
				f.delete();
				System.out.println("Deleting " + f.getName());
			}
		}
	}

	/**
	 * Dumps all acounts.
	 */
	private static void dumpAll() {
		TaskExecutor.executeSQL(new Runnable() {
			@Override
			public void run() {
				final Player[] players = getPlayers(DUMP_DIR);
				System.out.println("Player size=" + players.length);
				for (final Player p : players) {
					if (p == null) {
						System.out.println("Null player!");
						continue;
					}
					TaskExecutor.executeSQL(new Runnable() {
						@Override
						public void run() {
							System.out.println("Player : " + p.getUsername());
							p.getDetails().getSqlManager().parse();
							p.getDetails().getSqlManager().save(p);
						}

					});
				}
				// System.out.println("Total players=" + total + ", pushed=" +
				// pushed);
			}
		});
	}

	/**
	 * Gets the player.
	 * @param name the name.
	 * @return the player.
	 */
	public static Player getPlayer(String name) {
		return Repository.getPlayerFile(name);
	}

	/**
	 * Creates the database for the player.
	 * @param player the player.
	 */
	public static void create(Player player) {
		if (player == null) {
			return;
		}
		System.out.println("Creating database entry for " + player.getUsername());
		int credits = player.getDetails().getShop().getCredits();
		player.getDetails().getSqlManager().parse();
		System.out.println("Parsed.");
		player.getDetails().getShop().setCredits(credits, false);
		player.getDetails().getSqlManager().save();
		System.out.println("Pushed player " + player.getUsername() + " into the database.");
	}

	/**
	 * Gets the players.
	 * @param dir the dir.
	 * @return the player.
	 */
	public static Player[] getPlayers(String dir) {
		List<Player> players = new ArrayList<>();
		@SuppressWarnings("unused")
		List<Player> remove = new ArrayList<>();
		File[] files = new File(dir).listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				continue;
			}
			if (f.getName().contains(".ds") || f.isHidden()) {
				continue;
			}
			Player p = getPlayer(f.getName().replace(".arios", "").trim());
			if (p == null) {
				continue;
			}
			players.add(p);
		}
		return players.toArray(new Player[] {});
	}

}
