package plugin.skill.construction;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.component.Component;
import org.arios.game.content.skill.member.construction.BuildHotspot;
import org.arios.game.content.skill.member.construction.Decoration;
import org.arios.game.content.skill.member.construction.RoomBuilder;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.plugin.Plugin;

/**
 * The build option handling plugin.
 * @author Emperor
 */
public final class BuildOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.setOptionHandler("build", this);
		ObjectDefinition.setOptionHandler("remove", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (!player.getHouseManager().isBuildingMode()) {
			player.getPacketDispatch().sendMessage("You have to be in building mode to do this.");
			return true;
		}
		if (option.equals("remove")) {
			Decoration decoration = Decoration.getDecoration(player, node.getLocation());
			if (decoration == null) {
				return false;
			}
			player.getPacketDispatch().sendMessage("Roar " + decoration + ".");
			return true;
		}
		player.setAttribute("con:hsobject", node);
		if (node.asObject().getType() < 4) {
			player.getInterfaceManager().open(new Component(402));
			return true;
		}
		BuildHotspot hotspot = BuildHotspot.forId(((GameObject) node).getId());
		if (hotspot == null) {
			return false;
		}
		player.setAttribute("con:hotspot", hotspot);
		RoomBuilder.openBuildInterface(player, hotspot);
		return true;
	}

}