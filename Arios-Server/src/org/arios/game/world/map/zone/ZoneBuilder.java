package org.arios.game.world.map.zone;

import org.arios.game.world.callback.CallBack;
import org.arios.game.world.map.zone.impl.BankZone;
import org.arios.game.world.map.zone.impl.DarkZone;
import org.arios.game.world.map.zone.impl.DonatorZone;
import org.arios.game.world.map.zone.impl.KaramjaZone;
import org.arios.game.world.map.zone.impl.ModeratorZone;
import org.arios.game.world.map.zone.impl.MultiwayCombatZone;
import org.arios.game.world.map.zone.impl.WildernessZone;

/**
 * Loads all the default zones.
 * @author Emperor
 */
public class ZoneBuilder implements CallBack {

	@Override
	public boolean call() {
		configure(WildernessZone.getInstance());
		configure(MultiwayCombatZone.getInstance());
		configure(new ModeratorZone());
		configure(new DarkZone());
		configure(new KaramjaZone());
		configure(new BankZone());
		configure(DonatorZone.getInstance());
		return true;
	}

	/**
	 * Configures the map zone.
	 * @param zone The map zone.
	 */
	public static void configure(MapZone zone) {
		zone.setUid(zone.getName().hashCode());
		zone.configure();
	}
}