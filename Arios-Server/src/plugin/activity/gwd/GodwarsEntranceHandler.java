package plugin.activity.gwd;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.skill.Skills;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.impl.ForceMovement;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Handles the entrance hole to the godwars dungeon.
 * @author Emperor
 */
public final class GodwarsEntranceHandler extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(26340).getConfigurations().put("option:tie-rope", this);
		ObjectDefinition.forId(26341).getConfigurations().put("option:climb-down", this);
		ObjectDefinition.forId(26338).getConfigurations().put("option:move", this);
		ObjectDefinition.forId(26305).getConfigurations().put("option:crawl-through", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, String option) {
		GameObject object = (GameObject) node;
		switch (object.getId()) {
		case 26340:
			if (!player.getInventory().remove(new Item(954))) {
				player.getPacketDispatch().sendMessage("You don't have a rope to tie around the pillar.");
				return true;
			}
			int value = player.getConfigManager().get(1048) | 0x1;
			player.getConfigManager().set(1048, value, true);
			return true;
		case 26341:
			if (player.getSkills().getStaticLevel(Skills.AGILITY) < 15) {
				player.getPacketDispatch().sendMessage("You need an Agility level of 15 to enter this.");
				return true;
			}
			if ((player.getConfigManager().get(1048) & 16) == 0) {
				player.getDialogueInterpreter().sendDialogues(6201, FacialExpression.NORMAL, "Cough... Hey, over here.");
				return true;
			}
			player.lock(2);
			player.getPacketDispatch().sendMessage("You climb down the rope.");
			player.animate(Animation.create(828));
			GameWorld.submit(new Pulse(1, player) {
				@Override
				public boolean pulse() {
					player.getProperties().setTeleportLocation(Location.create(2882, 5311, 2));
					return true;
				}
			});
			return true;
		case 26338:
			if (player.getSkills().getStaticLevel(Skills.STRENGTH) < 60) {
				player.getPacketDispatch().sendMessage("You need a Strength level of 60 to move this boulder.");
				return true;
			}
			player.getPacketDispatch().sendObjectAnimation(object, Animation.create(6980));
			if (player.getLocation().getY() < 3716) {
				ForceMovement.run(player, Location.create(2898, 3715, 0), Location.create(2898, 3719, 0), new Animation(6978), 3);
			} else {
				ForceMovement.run(player, Location.create(2898, 3719, 0), Location.create(2898, 3715, 0), new Animation(6979), 3);
			}
			GameWorld.submit(new Pulse(12, player) {
				@Override
				public boolean pulse() {
					player.getPacketDispatch().sendObjectAnimation(RegionManager.getObject(0, 2898, 3716), Animation.create(6981));
					return true;
				}
			});
			return true;
		case 26305:
			if (player.getSkills().getStaticLevel(Skills.AGILITY) < 60) {
				player.getPacketDispatch().sendMessage("You need an Agility level of 60 to squeeze through the crack.");
				return true;
			}
			if (object.getLocation().equals(Location.create(2900, 3713, 0))) {
				player.getProperties().setTeleportLocation(Location.create(2904, 3720, 0));
			} else {
				player.getProperties().setTeleportLocation(Location.create(2899, 3713, 0));
			}
			return true;
		}
		return false;
	}

}
