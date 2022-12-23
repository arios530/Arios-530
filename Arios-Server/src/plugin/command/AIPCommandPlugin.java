package plugin.command;

import java.util.ArrayList;
import java.util.List;

import org.arios.game.interaction.Interaction;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.ai.AIPBuilder;
import org.arios.game.node.entity.player.ai.AIPlayer;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;
import org.arios.plugin.Plugin;

/**
 * Handles the AIPlayer commands.
 * @author Emperor
 */
public final class AIPCommandPlugin extends CommandPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		link(CommandSet.DEVELOPER);
		return this;
	}

	@Override
	public boolean parse(final Player player, String name, String[] args) {
		List<AIPlayer> legion = player.getAttribute("aip_legion");
		switch (name) {
		case "desaip":
			player.removeAttribute("aip_select");
			return true;
		case "sellegion":
			if (legion != null && !legion.isEmpty()) {
				player.setAttribute("aip_select", legion.get(0));
			}
			return true;
		case "regroup":
			Player last = player;
			if (legion != null && !legion.isEmpty()) {
				for (AIPlayer p : legion) {
					p.follow(last);
					last = p;
				}
			}
			player.removeAttribute("aip_select");
			return true;
		case "clearlegion":
			if (legion != null && !legion.isEmpty()) {
				for (AIPlayer p : legion) {
					AIPlayer.deregister(p.getUid());
				}
				legion.clear();
			}
			player.removeAttribute("aip_select");
			player.removeAttribute("aip_legion");
			return true;
		case "clearaips":
			for (Player p : Repository.getPlayers()) {
				if (p.isArtificial()) {
					p.clear();
				}
			}
			return true;
		case "aip":
			name = args.length < 2 ? player.getName() : args[1];
			AIPlayer p = AIPBuilder.copy(player, name, player.getLocation().transform(0, 1, 0));
			Repository.getPlayers().add(p);
			p.init();
			Interaction.sendOption(player, 7, "Control");
			return true;
		case "legion":
			int size = args.length < 2 ? 10 : Integer.parseInt(args[1]);
			last = player;
			if (legion == null) {
				player.setAttribute("aip_legion", legion = new ArrayList<>());
			}
			Interaction.sendOption(player, 7, "Control");
			boolean joinClan = player.getCommunication().getClan() != null && !player.getCommunication().getClan().isDefault();
			String message = player.getName().equals("emperor") ? "The Dark Army marches again!" : null; // Add
			// your
			// own
			// message
			for (int i = 0; i < size; i++) {
				final AIPlayer aip = AIPBuilder.copy(player, last.getLocation().transform(0, 1, 0));
				Repository.getPlayers().add(aip);
				aip.init();
				if (legion.isEmpty()) {
					aip.setAttribute("aip_legion", legion);
				}
				legion.add(aip);
				final Player l = last;
				if (joinClan) {
					if (player.getCommunication().getClan().enter(aip)) {
						aip.getCommunication().setClan(player.getCommunication().getClan());
					}
					if (player.getCommunication().getClan().getClanWar() != null) {
						player.getCommunication().getClan().getClanWar().fireEvent("join", aip);
					}
				}
				GameWorld.submit(new Pulse(1) {
					@Override
					public boolean pulse() {
						aip.follow(l);
						return true;
					}
				});
				if (message != null) {
					aip.sendChat("The Dark Army marches again!");
				}
				last = aip;
			}
			return true;
		}
		return false;
	}

}