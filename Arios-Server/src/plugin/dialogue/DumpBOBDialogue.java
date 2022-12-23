package plugin.dialogue;

import org.arios.game.container.Container;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.parser.item.ItemConfiguration;

/**
 * Represents the dialogue plugin used to handle the DUMP BOB button
 * @author Splinter
 */
public final class DumpBOBDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code DumpBOBDialogue} {@code Object}.
	 */
	public DumpBOBDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code DumpBOBDialogue} {@code Object}.
	 * @param player the player.
	 */
	public DumpBOBDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new DumpBOBDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		player.getDialogueInterpreter().sendOptions("Select an Option", "Deposit Inventory", "Deposit Equipment", "Deposit Beast of Burden", "Cancel");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			switch (buttonId) {
			case 1:
				if (player.getInventory().freeSlots() >= 28) {
					player.getPacketDispatch().sendMessage("You have nothing to deposit.");
					end();
					return true;
				} else {
					dump(player.getInventory());
					end();
				}
				break;
			case 2:
				if (player.getEquipment().freeSlots() >= 14) {
					player.getPacketDispatch().sendMessage("You have nothing to deposit.");
					end();
					return true;
				} else {
					dump(player.getEquipment());
					end();
				}
				break;
			case 3:
				player.getFamiliarManager().dumpBob();
				end();
				break;
			case 4:
				end();
				break;
			}
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 628371 };
	}

	/**
	 * Dumps a container.
	 * @param container the container.
	 */
	public void dump(Container container) {
		for (Item i : container.toArray()) {
			if (i == null) {
				continue;
			}
			if (!i.getDefinition().getConfiguration(ItemConfiguration.BANKABLE, true)) {
				player.getPacketDispatch().sendMessage("A magical force prevents you from banking your equipment.");
				return;
			}
			if (!player.getBank().hasSpaceFor(i)) {
				player.getPacketDispatch().sendMessage("There is not enough space left in your bank.");
				return;
			}
			if (container.remove(i)) {
				player.getBank().add(i.getDefinition().isUnnoted() ? i : new Item(i.getNoteChange(), i.getAmount()));
			}
		}
	}
}