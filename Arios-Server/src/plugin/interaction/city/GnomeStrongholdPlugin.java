package plugin.interaction.city;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.skill.member.agility.AgilityHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.impl.ForceMovement;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Handles Gnome stronghold options.
 * @author Emperor
 */
public final class GnomeStrongholdPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(190).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(1967).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(1968).getConfigurations().put("option:open", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		GameObject object = (GameObject) node;
		switch (object.getId()) {
		case 1967:
		case 1968:
			openTreeDoor(player, object);
			return true;
		case 190:
			openGates(player, object);
			return true;
		}
		return true;
	}

	/**
	 * Opens the tree doors.
	 * @param player the player.
	 * @param object the object.
	 */
	private void openTreeDoor(final Player player, final GameObject object) {
		if (object.getCharge() == 88) {
			return;
		}
		object.setCharge(88);
		ObjectBuilder.replace(object, object.transform(object.getId() == 1967 ? 1969 : 1970), 4);
		AgilityHandler.walk(player, -1, player.getLocation(), player.getLocation().transform(0, player.getLocation().getY() <= 3491 ? 2 : -2, 0), ForceMovement.WALK_ANIMATION, 0, null);
		GameWorld.submit(new Pulse(4) {
			@Override
			public boolean pulse() {
				object.setCharge(1000);
				return true;
			}
		});
	}

	/**
	 * Opens the stronghold gates.
	 * @param player The player.
	 * @param object The door.
	 */
	private void openGates(Player player, final GameObject object) {
		if (object.getCharge() == 88) {
			return;
		}
		object.setCharge(88);
		ObjectBuilder.replace(object, object.transform(191), 4);
		ObjectBuilder.add(new GameObject(192, Location.create(2462, 3383, 0)), 4);
		Location start = Location.create(2461, 3382, 0);
		Location end = Location.create(2461, 3385, 0);
		if (player.getLocation().getY() > object.getLocation().getY()) {
			Location s = start;
			start = end;
			end = s;
		}
		AgilityHandler.walk(player, -1, start, end, ForceMovement.WALK_ANIMATION, 0, null);
		GameWorld.submit(new Pulse(4) {
			@Override
			public boolean pulse() {
				object.setCharge(1000);
				return true;
			}
		});
	}

	@Override
	public Location getDestination(Node node, Node n) {
		if (n instanceof GameObject) {
			switch (((GameObject) n).getId()) {
			case 190:
				if (node.getLocation().getY() < n.getLocation().getY()) {
					return Location.create(2461, 3382, 0);
				}
				return Location.create(2461, 3385, 0);
			}
		}
		return null;
	}

}