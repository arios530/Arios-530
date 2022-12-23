package plugin.zone;

import org.arios.game.world.map.zone.MapZone;
import org.arios.game.world.map.zone.ZoneBorders;
import org.arios.game.world.map.zone.ZoneBuilder;
import org.arios.game.world.map.zone.ZoneRestriction;
import org.arios.plugin.Plugin;

/**
 * The grand exchange map zone.
 * @author Emperor
 */
public final class GrandExchangeZone extends MapZone implements Plugin<Object> {

	/**
	 * Constructs a new {@code GrandExchangeZone} {@code Object}.
	 */
	public GrandExchangeZone() {
		super("grand exchange", true, ZoneRestriction.FOLLOWERS, ZoneRestriction.CANNON);
	}

	@Override
	public void configure() {
		super.register(new ZoneBorders(3146, 3472, 3183, 3508));
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ZoneBuilder.configure(this);
		return this;
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

}