package org.arios.tools.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.arios.ServerConstants;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.PlayerDetails;
import org.arios.game.world.GameWorld;
import org.arios.parser.player.PlayerParser;

/**
 * Ensures the character accounts data.
 * @author Emperor
 */
public final class EnsureCharacterAccounts {

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		File backupDir = new File("./invalid_chars/");
		for (File file : new File(ServerConstants.PLAYER_SAVE_PATH).listFiles()) {
			if (!file.getName().endsWith(".arios")) {
				continue;
			}
			try {
				PlayerDetails details = new PlayerDetails(file.getName().replaceAll(".arios", ""), "unknown", null);
				details.parse();
				Player player = new Player(details);
				PlayerParser.parse(player);
				PlayerParser.dump(player);
				System.out.println("Ensured " + file.getName().replaceAll(".arios", "") + "'s account data!");
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("Error ensuring " + file.getName().replaceAll(".arios", "") + "'s account data!");
				try {
					if (!backupDir.isDirectory()) {
						backupDir.mkdir();
					}
					copyFile(file, new File("./invalid_chars/" + file.getName()));
					file.delete();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Copies a file.
	 * @param in The file to be copied.
	 * @param out The file to copy to.
	 */
	private static void copyFile(File in, File out) {
		try (FileChannel channel = new FileInputStream(in).getChannel()) {
			try (FileChannel output = new FileOutputStream(out).getChannel()) {
				channel.transferTo(0, channel.size(), output);
				channel.close();
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}