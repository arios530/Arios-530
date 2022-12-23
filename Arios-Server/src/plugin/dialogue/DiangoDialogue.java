package plugin.dialogue;

import java.util.ArrayList;
import java.util.List;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.shop.Shop;
import org.arios.game.content.global.shop.ShopViewer;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.portal.Perks;
import org.arios.game.node.item.Item;
import org.arios.game.system.mysql.impl.ShopSQLHandler;

/**
 * Represents the dialogue plugin used by diango.
 * @author 'Vexia
 * @version 1.1
 */
public final class DiangoDialogue extends DialoguePlugin {

	/**
	 * The diango cosmetic shop.
	 */
	private static final DiangoCosmeticShop DIANGO_COSMETIC = new DiangoCosmeticShop();

	/**
	 * Constructs a new {@code DiangoDialogue {@code Object}.
	 */
	public DiangoDialogue() {
		ShopSQLHandler.getUidShops().put(182312, DIANGO_COSMETIC);
	}

	/**
	 * Constructs a new {@code DiangoDialogue} {@code Object}.
	 * @param player the player.
	 */
	public DiangoDialogue(Player player) {
		super(player);
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			options("Spinning plates?", "I'd like to check holiday items please!", "I'd like to claim purchased cosmetics.");
			stage++;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				player("Spinning plates?");
				stage = 10;
				break;
			case 2:
				player("I'd like to check holiday items please?");
				stage = 20;
				break;
			case 3:
				player("I'd like to claim purchased cosmetics.");
				stage = 30;
				break;
			}
			break;
		case 10:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "That's right. There's a funny story behind them, their", "shipment was held up by thieves");
			stage = 11;
			break;
		case 11:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "The crate was marked 'Dragon Plates'.", "Apparently they thought it was some kind of armour,", "when really it's just a plate!");
			stage = 12;
			break;
		case 12:
			end();
			npc.openShop(player);
			break;
		case 20:
			npc("Sure thing, let me just see what you're missing.");
			stage++;
			break;
		case 21:
			openItemReturn(player);
			end();
			break;
		case 30:
			end();
			DIANGO_COSMETIC.open(player);
			break;
		}
		return true;
	}

	/**
	 * Opens the item return.
	 * @param player the player.
	 */
	private void openItemReturn(Player player) {

	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new DiangoDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Howdy there, partner! Want to see my spinning plates?", "Or did ya want a holiday item back?");
		stage = 0;
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 970 };
	}

	/**
	 * Represents the diango cosmetic shop.
	 * @author Vexia
	 *
	 */
	public static class DiangoCosmeticShop extends Shop {

		/**
		 * Constructs a new {@Code DiangoCosmeticShop} {@Code Object}
		 */
		public DiangoCosmeticShop() {
			super("Diango Cosmetic Shop", getStock(), false);
		}

		@Override
		public boolean handleBuy(Player player, Item currency) {
			Perks perk = player.getAttribute("perk", null);
			if (perk == null) {
				player.sendMessage("Error perk/item not found.");
				return false;
			}
			if (player.getDetails().getPortal().getShop().removePerk(perk)) {
				return true;
			} else {
				player.sendMessage("Error on buying item!");
				return false;
			}
		}

		@Override
		public int getAmount(Player player, Item add) {
			return 1;
		}

		@Override
		public boolean canBuy(Player player, Item item, int price, Item currency) {
			Perks perk = Perks.forItem(item);
			if (perk == null) {
				player.sendMessage("Error! Report to admin.");
				return false;
			}
			if (!player.hasPerk(perk)) {
				player.sendMessages("You don't have the ability to redeem this perk. Purchase it on the Arios shop first.");
				return false;
			}
			player.setAttribute("perk", perk);
			return true;
		}

		@Override
		public boolean canSell(Player player, Item item, ItemDefinition def) {
			player.sendMessage("You can't sell items back to diango.");
			return false;
		}

		@Override
		public void value(Player player, ShopViewer viewer, Item item, boolean sell) {
			if (sell) {
				player.sendMessage("You can't sell items to this shop.");
				return;
			}
			player.sendMessage("You must have purchased this item on the Arios shop in order to redeem it.");
		}

		/**
		 * Gets the stock items.
		 * @return the items.
		 */
		public static Item[] getStock() {
			List<Item> items = new ArrayList<>();
			for (Perks perk : Perks.values()) {
				if (perk.getData() != null && perk.getData().length > 0) {
					for (Object o : perk.getData()) {
						if (o != null && o instanceof Item) {
							items.add(new Item(((Item) o).getId(), 100));
						}
					}
				}
			}
			return items.toArray(new Item[] {});
		}
	}
}