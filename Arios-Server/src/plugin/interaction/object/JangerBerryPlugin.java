package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.skill.member.agility.AgilityHandler;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Handles the janger berry plugin.
 * @author Vexia
 */
public class JangerBerryPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		UseWithHandler.addHandler(2326, UseWithHandler.OBJECT_TYPE, new UseWithHandler(954) {
			@Override
			public Plugin<Object> newInstance(Object arg) throws Throwable {
				return this;
			}

			@Override
			public boolean handle(NodeUsageEvent event) {
				GameObject object = event.getUsedWith().asObject();
				if (object.isActive())
					ObjectBuilder.replace(object, object.transform(2325));
				event.getPlayer().getInventory().remove(event.getUsedItem());
				return true;
			}

		});
		ObjectDefinition.forId(2325).getConfigurations().put("option:swing-on", this);
		ObjectDefinition.forId(2324).getConfigurations().put("option:swing-on", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (!player.getLocation().withinDistance(node.getLocation(), 2)) {
			player.sendMessage("I can't reach that.");
			return true;
		}
		Location end = node.getId() == 2325 ? new Location(2505, 3087, 0) : new Location(2511, 3096, 0);
		player.getPacketDispatch().sendObjectAnimation(node.asObject(), Animation.create(497), true);
		AgilityHandler.forceWalk(player, 0, player.getLocation(), end, Animation.create(751), 50, 22, "You skillfully swing across.", 1);
		return true;
	}

	@Override
	public Location getDestination(Node node, Node n) {
		return n.getId() == 2324 ? new Location(2511, 3092, 0) : new Location(2501, 3087, 0);
	}
}
