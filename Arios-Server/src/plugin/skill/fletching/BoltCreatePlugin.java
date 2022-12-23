package plugin.skill.fletching;

import org.arios.game.content.dialogue.SkillDialogueHandler;
import org.arios.game.content.dialogue.SkillDialogueHandler.SkillDialogue;
import org.arios.game.content.skill.member.fletching.items.bolts.Bolt;
import org.arios.game.content.skill.member.fletching.items.bolts.BoltPulse;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ChildPositionContext;
import org.arios.net.packet.out.RepositionChild;
import org.arios.plugin.Plugin;

/**
 * Represents the bolt creating plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class BoltCreatePlugin extends UseWithHandler {

	/**
	 * Constructs a new {@code BoltCreatePlugin} {@code Object}.
	 */
	public BoltCreatePlugin() {
		super(314);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (Bolt bolt : Bolt.values()) {
			addHandler(bolt.getItem().getId(), ITEM_TYPE, this);
		}
		return this;
	}

	@Override
	public boolean handle(final NodeUsageEvent event) {
		final Player player = event.getPlayer();
		final Bolt bolt = Bolt.forItem(event.getUsedItem());
		SkillDialogueHandler handler = new SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, bolt.getProduct()) {
			@Override
			public void create(final int amount, int index) {
				player.getPulseManager().run(new BoltPulse(player, event.getUsedItem(), bolt, amount));
			}
			@Override
			public int getAll(int index) {
				return player.getInventory().getAmount(event.getUsedItem());
			}
		};
		handler.open();
		PacketRepository.send(RepositionChild.class, new ChildPositionContext(player, 309, 2, 210, 10));
		return true;
	}

}
