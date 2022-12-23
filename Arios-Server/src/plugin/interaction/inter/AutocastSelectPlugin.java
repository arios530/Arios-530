package plugin.interaction.inter;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the auto casting component plugin.
 * @author Emperor
 * 
 */
public final class AutocastSelectPlugin extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.forId(388).setPlugin(this);//797
		ComponentDefinition.forId(319).setPlugin(this);
		ComponentDefinition.forId(310).setPlugin(this);
		ComponentDefinition.forId(406).setPlugin(this);
		return this;
	}

	@Override
	public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
		if (!player.getAttribute("autocast_select", false)) {
			return true;
		}
		player.removeAttribute("autocast_select");
		final WeaponInterface w = player.getExtension(WeaponInterface.class);
		if (w != null) {
			w.selectAutoSpell(button, true);
			player.getInterfaceManager().openTab(w);
		}
		return true;
	}

}
