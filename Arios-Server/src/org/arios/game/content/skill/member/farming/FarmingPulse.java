package org.arios.game.content.skill.member.farming;

import org.arios.game.node.entity.player.Player;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.callback.CallBack;
import org.arios.game.world.repository.Repository;

/**
 * Represents the pulsed used to update all players farming states.
 * @author 'Vexia
 */
public final class FarmingPulse extends Pulse implements CallBack {

	@Override
	public boolean pulse() {
		for (Player p : Repository.getPlayers()) {
			if (p == null) {
				continue;
			}
			p.getFarmingManager().cycle();
		}
		return false;
	}

	@Override
	public boolean call() {
		GameWorld.submit(this);
		return true;
	}

}
