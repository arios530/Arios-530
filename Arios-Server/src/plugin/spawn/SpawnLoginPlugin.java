package plugin.spawn;

import org.arios.game.component.Component;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.CameraContext;
import org.arios.net.packet.context.CameraContext.CameraType;
import org.arios.net.packet.out.CameraViewPacket;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManifest;
import org.arios.plugin.PluginType;

/**
 * Handles the login plugin used for the spawn world.
 * @author Vexia
 */
@PluginManifest(type = PluginType.LOGIN)
public final class SpawnLoginPlugin implements Plugin<Player> {

	/**
	 * The falling animation used when logging in.
	 */
	private static final Animation FALLING_ANIMATION = new Animation(4367);

	@Override
	public Plugin<Player> newInstance(final Player player) throws Throwable {
		if (GameWorld.isEconomyWorld()) {
			return this;
		}
		if (player.getDetails().getRights() == Rights.ADMINISTRATOR && player.getSavedData().getSpawnData().getTutorialStage() == 0) {
			player.getSavedData().getSpawnData().setTutorialStage(3);
		}
		if (!player.getSavedData().getSpawnData().hasCompletedTutorial()) {
			player.lock();
			player.getInterfaceManager().removeTabs(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
			if (player.getSavedData().getSpawnData().getTutorialStage() == 0) {
				player.animate(FALLING_ANIMATION);
				player.getAudioManager().send(4500);
				PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, player.getLocation().getX(), player.getLocation().getY() - 5, 450, 1, 100));
				PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, player.getLocation().getX(), player.getLocation().getY() - 5, 450, 1, 100));
				Component.setUnclosable(player, player.getDialogueInterpreter().sendPlaneMessage(true, "You fall from high above the clouds..."));
				GameWorld.submit(new Pulse(FALLING_ANIMATION.getDuration(), player) {
					@Override
					public boolean pulse() {
						startTutorial(player, 0);
						return true;
					}
				});
			} else {
				startTutorial(player, player.getSavedData().getSpawnData().getTutorialStage());
			}
		}
		for (Quest q : player.getQuestRepository().getQuests().values()) {
			if (q.getStage() == 0 && !q.isInDevelopment()) {
				q.setStage(100);
				q.setState(QuestState.COMPLETED);
			}
		}
		player.getSavedData().getSpawnData().drawStatsTab(player);
		return this;
	}

	/**
	 * Starts the tutorial for the player.
	 * @param player the player.
	 * @param stage the stage.
	 */
	private void startTutorial(Player player, int stage) {
		player.getDialogueInterpreter().open("spawn-tutorial", stage);
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

}
