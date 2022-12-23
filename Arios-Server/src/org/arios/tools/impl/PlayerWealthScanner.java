package org.arios.tools.impl;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import org.arios.ServerConstants;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;

/**
 * Scans through the player's files to check their wealth.
 * @author Emperor
 */
public final class PlayerWealthScanner {

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 */
	public static void main(String[] args) throws Throwable {
		GameWorld.prompt(false);
		Item item = null;
		if (args.length > 0) {
			String s = args[0];
			if (s.startsWith("item:")) {
				item = new Item(Integer.parseInt(s.substring("item:".length(), s.length())), 1);
			}
		}
		Object[][] mostWealthy = new Object[50][2];
		int player = 0;
		long count = 0;
		File[] players = new File(ServerConstants.PLAYER_SAVE_PATH).listFiles();
		for (File f : players) {
			if (f.isDirectory()) {
				continue;
			}
			Player p = Repository.getPlayerFile(f.getName().substring(0, f.getName().lastIndexOf(".")));
			if (p.getDetails().getRights() == Rights.ADMINISTRATOR) {
				continue;
			}
			if (p.getDetails().getRights() == Rights.PLAYER_MODERATOR) {
				p.getDetails().setRights(Rights.REGULAR_PLAYER);
				p.getDetails().save();
				System.err.println("Demoted - " + p.getDetails());
			}
			if (item != null) {
				long old = count;
				count += p.getInventory().getAmount(item);
				count += p.getEquipment().getAmount(item);
				count += p.getBank().getAmount(item);
				if (old != count) {
					player++;
				}
			}
			for (int i = 0; i < mostWealthy.length; i++) {
				Long value = (Long) mostWealthy[i][0];
				if (value == null || value < p.getMonitor().getNetworth()) {
					for (int j = mostWealthy.length - 1; j > i; j--) {
						mostWealthy[j] = mostWealthy[j - 1];
					}
					mostWealthy[i] = new Object[2];
					mostWealthy[i][0] = p.getMonitor().getNetworth();
					mostWealthy[i][1] = p.getName();
					break;
				}
			}
		}
		for (int i = mostWealthy.length - 1; i >= 0; i--) {
			Object[] info = mostWealthy[i];
			if (info != null && info[0] != null) {
				System.out.println(i + ": " + info[1] + " - " + NumberFormat.getNumberInstance(Locale.US).format((Long) info[0]) + " coins.");
			}
		}
		if (item != null) {
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(count) + " occurances of item " + item.getName() + " (id=" + item.getId() + ") by " + player + " players!");
		}
	}
}