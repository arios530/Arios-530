package plugin.quest.demonslayer;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.free.DemonSlayer;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the demon slayer plugin used to handle washing the key down the
 * drain.
 * @author 'Vexia
 * @version 1.0
 */
public final class DSlayerDrainPlugin extends UseWithHandler {

	/**
	 * Represents the animation to use.
	 */
	private static final Animation ANIMATION = new Animation(827);

	/**
	 * Represents the bucket of water item.
	 */
	private static final Item BUCKET_OF_WATER = new Item(1929);

	/**
	 * Represents the bucket item.
	 */
	private static final Item BUCKET = new Item(1925);

	/**
	 * Constructs a new {@code DSlayerDrainPlugin} {@code Object}.
	 */
	public DSlayerDrainPlugin() {
		super(1929);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(DemonSlayerPlugin.DRAIN_ID, OBJECT_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		final Player player = event.getPlayer();
		final Quest quest = player.getQuestRepository().getQuest("Demon Slayer");
		if (player.getInventory().remove(BUCKET_OF_WATER)) {
			player.getInventory().add(BUCKET);
			player.animate(ANIMATION);
			player.getPacketDispatch().sendMessage("You pour the liquid down the drain.");
			if (quest.getStage() != 20) {
				return true;
			}
			if (player.getAttribute("demon-slayer:just-poured", false)) {
				return true;
			}
			if (!player.getInventory().containsItem(DemonSlayer.FIRST_KEY) && !player.getBank().containsItem(DemonSlayer.FIRST_KEY)) {
				player.getSavedData().getQuestData().getDemonSlayer()[0] = false;
			}
			if (quest.getStage() == 20 && !player.getInventory().containsItem(DemonSlayer.FIRST_KEY) && !player.getBank().containsItem(DemonSlayer.FIRST_KEY) && !player.getSavedData().getQuestData().getDemonSlayer()[0]) {
				player.getDialogueInterpreter().sendDialogues(player, null, "OK, I think I've washed the key down into the sewer.", "I'd better go down and get it!");
				player.getSavedData().getQuestData().getDemonSlayer()[0] = true;// poured
				player.getConfigManager().set(222, 2660610, true);
				player.setAttribute("demon-slayer:just-poured", true);
				return true;
			}
		}
		return true;
	}

}
