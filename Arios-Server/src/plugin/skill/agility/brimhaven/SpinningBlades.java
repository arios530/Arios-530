package plugin.skill.agility.brimhaven;

import org.arios.game.content.skill.Skills;
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
 * Handles the spinning blades trap.
 * @author Emperor
 */
public final class SpinningBlades implements MovementHook {

	@Override
	public boolean handle(Entity e, final Location l) {
		final Direction dir = e.getDirection();
		final Player player = (Player) e;
		final Location start = l.transform(-dir.getStepX(), -dir.getStepY(), 0);
		e.lock(5);
		e.addExtension(LogoutTask.class, new LocationLogoutTask(5, start));
		GameWorld.submit(new Pulse(3, e) {
			@Override
			public boolean pulse() {
				sendObjectAnimation(player, l);
				if (AgilityHandler.hasFailed(player, 40, 0.15)) {
					if (player.getSkills().getLevel(Skills.AGILITY) < 40) {
						player.getPacketDispatch().sendMessage("You need an agility of at least 40 to get past this trap!");
					}
					int hit = player.getSkills().getLifepoints() / 12;
					if (hit < 2) {
						hit = 2;
					}
					AgilityHandler.failWalk(player, 1, player.getLocation(), start, start, Animation.create(1114), 10, hit, "You were hit by the spinning blades.").setDirection(dir);
				} else {
					AgilityHandler.forceWalk(player, -1, l, l.transform(dir.getStepX() << 1, dir.getStepY() << 1, 0), Animation.create(1115), 20, 26, null);
				}
				return true;
			}
		});
		return false;
	}

	/**
	 * Sends the object animation for the spinning blades.
	 * @param player The player.
	 * @param l The location.
	 */
	private static void sendObjectAnimation(Player player, Location l) {
		if (l.equals(Location.create(2778, 9579, 3))) {
			l = Location.create(2777, 9580, 3);
		} else if (l.equals(Location.create(2783, 9574, 3))) {
			l = Location.create(2782, 9573, 3);
		} else if (l.equals(Location.create(2778, 9557, 3))) {
			l = Location.create(2777, 9556, 3);
		}
		player.getPacketDispatch().sendObjectAnimation(RegionManager.getObject(l), Animation.create(1107));
	}
}