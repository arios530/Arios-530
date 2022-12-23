package plugin.quest.rovingelves;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.RovingElves;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManager;

/**
 * Master plugin file for Roving Elves.
 * @author Splinter
 */
public final class RovingElvesPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		PluginManager.definePlugin(new RovingElvesObstacles());
		PluginManager.definePlugin(new ElunedDialogue());
		PluginManager.definePlugin(new IslwynDialogue());
		PluginManager.definePlugin(new MossGiantGuardianNPC());
		ObjectDefinition.forId(5252).getConfigurations().put("option:search", this);
		ItemDefinition.forId(RovingElves.CONSECRATION_SEED_CHARGED.getId()).getConfigurations().put("option:plant", this);
		return this;
	}

	/**
	 * The digging animation.
	 */
	public static final Animation ANIMATION_DIG = Animation.create(830);

	/**
	 * The drop animation.
	 */
	public static final Animation ANIMATION_DROP = Animation.create(827);

	@SuppressWarnings("static-access")
	@Override
	public boolean handle(final Player player, Node node, String option) {
		final Quest quest = player.getQuestRepository().getQuest(RovingElves.NAME);
		if (quest == null) {
			player.sendMessage("Error! RovingElves quest cannot be found, please contact an admin!");
			return true;
		}
		switch (node.getId()) {
		case 5252:
			player.getDialogueInterpreter().sendDialogue("The firepit is still warm, there must be travellers about. Maybe I", "should look for them.");
			break;
		case 4206:
			if (player.getLocation().getDistance(new Location(2603, 9911), player.getLocation()) <= 3 && quest.getStage() == 15) {
				if (!player.getInventory().containsItem(new Item(952, 1))) {
					player.getPacketDispatch().sendMessage("You need a spade to plant the seed.");
				} else {
					player.animate(ANIMATION_DIG);
					player.getPacketDispatch().sendMessage("You dig a small hole with your spade.");
					GameWorld.submit(new Pulse(1, player) {
						int counter;

						@Override
						public boolean pulse() {
							switch (counter++) {
							case 3:
								player.getPacketDispatch().sendMessage("You drop the crystal seed in the hole.");
								player.animate(ANIMATION_DROP);
								player.faceLocation(new Location(2604, 9907, 0));
								break;
							case 6:
								player.getInventory().remove(new Item(RovingElves.CONSECRATION_SEED_CHARGED.getId()));
								quest.setStage(20);
								quest.update();
								player.getPacketDispatch().sendGlobalPositionGrapicc(719, new Location(2604, 9907, 0));
								player.getPacketDispatch().sendMessage("The seed vanishes in a puff of smoke.");
								break;
							}
							return false;
						}
					});
				}
			}
			break;
		}

		return true;
	}

	@Override
	public Location getDestination(Node node, Node n) {
		return null;
	}

	@Override
	public boolean isWalk(Player player, Node node) {
		return !(node instanceof Item);
	}

	@Override
	public boolean isWalk() {
		return false;
	}

}
