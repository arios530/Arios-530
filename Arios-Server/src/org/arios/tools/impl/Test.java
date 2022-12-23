package org.arios.tools.impl;

import java.util.Arrays;

import org.arios.game.node.entity.player.Player;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;
import org.arios.parser.player.PlayerParser;

public class Test {

	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		Player player = Repository.getPlayerFile("noob123");
		//debug what you want to know
		System.out.println("Player inventory: " + Arrays.toString(player.getInventory().toArray()));
		System.out.println("Player pass: " + player.getDetails().getPassword());
		
		//edit the player's account.
		player.getBank().clear();
		player.getQuestRepository().setPoints(666);
		
		PlayerParser.dump(player);
		player.getDetails().save();
		
	}

}
