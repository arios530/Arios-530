package plugin.skill.construction;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.dialogue.DialogueInterpreter;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.skill.member.construction.HouseLocation;
import org.arios.game.content.skill.member.construction.HouseManager;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.RunScript;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.repository.Repository;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManager;
import org.arios.plugin.PluginManifest;
import org.arios.plugin.PluginType;

/**
 * Handles the house portal options.
 * @author Emperor
 */
public final class PortalOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (int objectId = 15477; objectId < 15483; objectId++) {
			ObjectDefinition.forId(objectId).getConfigurations().put("option:enter", this);
		}
		ObjectDefinition.forId(13405).getConfigurations().put("option:enter", this);
		PluginManager.definePlugin(new PortalDialogue());
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		GameObject object = node.asObject();
		if (object.getId() == 13405) {
			HouseManager.leave(player);
			return true;
		}
		player.setAttribute("con:portal", object.getId());
		player.getDialogueInterpreter().open("con:portal");
		return true;
	}

	/**
	 * Handles the portal dialogue.
	 * @author Emperor
	 */
	@PluginManifest(type = PluginType.DIALOGUE)
	private class PortalDialogue extends DialoguePlugin {

		/**
		 * The house location.
		 */
		private HouseLocation location;

		/**
		 * Constructs a new {@code PortalDialogue} {@code Object}
		 */
		public PortalDialogue() {
			/*
			 * empty.
			 */
		}

		/**
		 * Constructs a new {@code PortalDialogue} {@code Object}
		 * @param player The player.
		 */
		public PortalDialogue(Player player) {
			super(player);
		}

		@Override
		public DialoguePlugin newInstance(Player player) {
			return new PortalDialogue(player);
		}

		@Override
		public boolean open(Object... args) {
			location = HouseLocation.forId(player.getAttribute("con:portal", -1));
			options("Go to your house", "Go to your house (building mode)", "Go to a friend's house", "Never mind");
			stage = 1;
			return true;
		}

		@Override
		public boolean handle(int interfaceId, int buttonId) {
			end();
			switch (stage) {
			case 1:
				switch (buttonId) {
				case 1:
				case 2:
					if (!player.getHouseManager().hasHouse()) {
						player.getPacketDispatch().sendMessage("You should talk to the estate agent to get a house first.");
						break;
					}
					if (player.getHouseManager().getLocation().getPortalId() != player.getAttribute("con:portal", -1)) {
						player.getPacketDispatch().sendMessage("Your house is in " + player.getHouseManager().getLocation().name().toLowerCase() + ".");
						break;
					}
					player.getHouseManager().enter(player, buttonId == 2);
					break;
				case 3:
					player.getDialogueInterpreter().sendInput(true, "Enter name:");
					player.setAttribute("runscript", new RunScript() {
						@Override
						public boolean handle() {
							Player p = Repository.getPlayerByDisplay((String) getValue());
							if (p == null || !p.isActive() || (p.getHouseManager().isInHouse() && p.getHouseManager().isBuildingMode())) {
								player.getPacketDispatch().sendMessage("That player is offline, or has privacy mode enabled.");
								return true;
							}
							if (!p.getHouseManager().isInHouse()) {
								player.getPacketDispatch().sendMessage("They do not seem to be at home.");
								return true;
							}
							if (p.getHouseManager().getLocation() != location) {
								player.sendMessage("That friend's house is not located here.");
								return true;
							}
							p.getHouseManager().enter(player, false);
							return true;
						}
					});
					break;
				}
				break;
			}
			return true;
		}

		@Override
		public int[] getIds() {
			return new int[] { DialogueInterpreter.getDialogueKey("con:portal") };
		}
	}
}