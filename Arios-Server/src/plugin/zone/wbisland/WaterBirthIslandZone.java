package plugin.zone.wbisland;

import org.arios.game.component.Component;
import org.arios.game.interaction.Option;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.zone.MapZone;
import org.arios.game.world.map.zone.ZoneBorders;
import org.arios.game.world.map.zone.ZoneBuilder;
import org.arios.plugin.Plugin;

/**
 * Handles the waterbirth island zone.
 * @author Vexia
 */
public final class WaterBirthIslandZone extends MapZone implements Plugin<Object> {

	/**
	 * Constructs a new {@code WaterBirthIslandZone} {@code Object}.
	 */
	public WaterBirthIslandZone() {
		super("Water birth island", true);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ZoneBuilder.configure(this);
		ZoneBuilder.configure(new WaterBirthDungeonZone());
		return this;
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

	@Override
	public boolean interact(Entity e, Node target, Option option) {
		if (e instanceof Player) {
			final Player player = (Player) e;
			switch (target.getId()) {
			case 2438:
				if (option.getName().equals("Travel")) {
					player.getDialogueInterpreter().open(target.getId(), target, true);
					return true;
				}
				break;
			case 8929:
				player.getInterfaceManager().open(new Component(574));
				return true;
			case 8930:
				player.getProperties().setTeleportLocation(new Location(2545, 10143, 0));
				return true;
			}
		}
		return super.interact(e, target, option);
	}

	@Override
	public boolean enter(Entity e) {
		if (e instanceof Player) {
			final Player player = (Player) e;
			player.getInterfaceManager().openOverlay(new Component(482));
		}
		return true;
	}

	@Override
	public boolean leave(Entity e, boolean logout) {
		if (e instanceof Player) {
			((Player) e).getInterfaceManager().closeOverlay();
		}
		return true;
	}

	@Override
	public boolean actionButton(Player player, int interfaceId, int buttonId, int slot, int itemId, int opcode) {
		switch (interfaceId) {
		case 574:
			switch (buttonId) {
			case 17:
				player.getInterfaceManager().close();
				player.getProperties().setTeleportLocation(Location.create(2443, 10146, 0));
				break;
			case 18:
				player.getInterfaceManager().close();
				break;
			}
			break;
		}
		return super.actionButton(player, interfaceId, buttonId, slot, itemId, opcode);
	}

	@Override
	public void configure() {
		register(new ZoneBorders(2487, 3711, 2565, 3776));
	}

}
