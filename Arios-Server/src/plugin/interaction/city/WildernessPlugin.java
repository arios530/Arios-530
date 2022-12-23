package plugin.interaction.city;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.action.ClimbActionHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.TeleportManager.TeleportType;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents a plugin used to handle wilderness nodes.
 * @author 'Vexia
 * @version 1.0
 */
public final class WildernessPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		new KBDPlugin().newInstance(arg);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		return true;
	}

	/**
	 * Represents the plugin used to handle kbd nodes.
	 * @author 'Vexia
	 * @version 1.0
	 */
	public static final class KBDPlugin extends OptionHandler {

		/**
		 * Represents the locations used for kbd interactions.
		 */
		private static final Location[] LOCATIONS = new Location[] { new Location(3017, 3849, 0), Location.create(3069, 10257, 0), new Location(3069, 3856, 0), new Location(3017, 3850, 0), Location.create(2272, 4680, 0), Location.create(3067, 10253, 0), new Location(3069, 10256, 0) };

		@Override
		public Plugin<Object> newInstance(Object arg) throws Throwable {
			ObjectDefinition.forId(1765).getConfigurations().put("option:climb-down", this);// ladder
			// to
			// get
			// to
			// kbd
			// lair.
			ObjectDefinition.forId(1766).getConfigurations().put("option:climb-up", this);// ladder
			// to
			// get
			// back
			// up.
			ObjectDefinition.forId(1816).getConfigurations().put("option:pull", this);// kbd
			// entrance
			// lever.
			ObjectDefinition.forId(1817).getConfigurations().put("option:pull", this);// kbd
			// entrance
			// lever.
			return this;
		}

		@Override
		public boolean handle(Player player, Node node, String option) {
			int id = ((GameObject) node).getId();
			switch (option) {
			case "climb-down":
				switch (id) {
				case 1765:// kbd ladder.
					if (node.getLocation().equals(LOCATIONS[0])) {
						ClimbActionHandler.climb(player, new Animation(827), LOCATIONS[1]);
					} else {
						ClimbActionHandler.climbLadder(player, (GameObject) node, option);
						return true;
					}
					break;
				}
				break;
			case "climb-up":
				switch (id) {
				case 1766:// kbd leave ladder.
					if (node.getLocation().equals(LOCATIONS[6])) {
						ClimbActionHandler.climb(player, new Animation(828), LOCATIONS[3]);
					} else {
						ClimbActionHandler.climbLadder(player, (GameObject) node, option);
						return true;
					}
					break;
				}
				break;
			case "pull":
				switch (id) {
				case 1816:// kbd lever.
					player.getPacketDispatch().sendMessage("You pull the lever...");
					player.getTeleporter().send(LOCATIONS[4], TeleportType.NORMAL);
					player.getPacketDispatch().sendMessage("... and teleport into the lair of the King Black Dragon!", 5);
					break;
				case 1817:
					player.getPacketDispatch().sendMessage("You pull the lever...");
					player.getTeleporter().send(LOCATIONS[5], TeleportType.NORMAL);
					player.getPacketDispatch().sendMessage("... and teleport out of the lair of the King Black Dragon!", 5);
					break;
				}
				break;
			}
			return true;
		}

		@Override
		public Location getDestination(Node node, Node n) {
//			if (n instanceof GameObject) {
//				int id = ((GameObject) n).getId();
//				if (id == 1817) {
//					return Location.create(2273, 4680, 0);
//				} else if (id == 1816) {
//					return Location.create(3067, 10253, 0);
//				}
//			}
			return null;
		}
	}
}
