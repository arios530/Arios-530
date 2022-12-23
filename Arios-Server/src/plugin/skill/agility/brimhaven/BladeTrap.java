package plugin.skill.agility.brimhaven;

import org.arios.game.content.skill.member.agility.AgilityHandler;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.task.LocationLogoutTask;
import org.arios.game.system.task.LogoutTask;
import org.arios.game.system.task.MovementHook;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.update.flag.context.Animation;

/**
 * Handles the blade trap.
 * @author Emperor
 */
public final class BladeTrap implements MovementHook {

	@Override
	public boolean handle(Entity e, final Location l) {
		if (BrimhavenArena.sawBladeActive) {
			final Direction dir = e.getDirection();
			final Player player = (Player) e;
			final Location start = l.transform(-dir.getStepX(), -dir.getStepY(), 0);
			e.lock(5);
			e.addExtension(LogoutTask.class, new LocationLogoutTask(5, start));
			GameWorld.submit(new Pulse(2, e) {
				@Override
				public boolean pulse() {
					Direction direction = dir;
					Direction d = Direction.get(direction.toInteger() + 2 & 3);
					if (RegionManager.getObject(player.getLocation().transform(dir)) != null) {
						Direction s = d;
						d = direction;
						direction = s;
					}
					Location loc = player.getLocation();
					AgilityHandler.failWalk(player, 1, loc, loc.transform(direction), loc.transform(direction), Animation.create(846), 10, 3, "You were hit by the saw blade!").setDirection(d);
					return true;
				}
			});
			return false;
		}
		return true;
	}

}