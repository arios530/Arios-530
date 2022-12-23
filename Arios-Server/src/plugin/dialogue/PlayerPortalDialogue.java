package plugin.dialogue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.arios.game.component.Component;
import org.arios.game.content.dialogue.DialogueInterpreter;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.global.Lamps;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.portal.Perks;
import org.arios.game.node.entity.state.EntityState;
import org.arios.game.system.task.TaskExecutor;

/**
 * Handles the player portal dialogue, here you can handle your credits.
 * @author Vexia
 */
public final class PlayerPortalDialogue extends DialoguePlugin {

	/**
	 * The start index.
	 */
	private int startIndex;

	/**
	 * Constructs a new {@code PlayerPortalDialogue} {@code Object}.
	 */
	public PlayerPortalDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code PlayerPortalDialogue} {@code Object}.
	 * @param player the player.
	 */
	public PlayerPortalDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new PlayerPortalDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		sendOptions("Shop with credits/perks", "View my perks");
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case -1:
			sendOptions("Shop with credits/perks", "View my perks");
			stage++;
			break;
		case 0:
			switch (buttonId) {
			case 1:
				if (!player.getDetails().getShop().syncCredits()) {
					end();
					break;
				}
				sendCreditOptions();
				break;
			case 2:
				end();
				sendPerks();
				break;
			}
			break;
		case 1:
			if (startIndex + (buttonId - 1) > CreditReward.values().length - 1) {
				end();
				break;
			}
			CreditReward.values()[startIndex + (buttonId - 1)].buy(player, interpreter);
			stage++;
			break;
		case 2:
			end();
			break;
		}
		return true;
	}

	/**
	 * Send credit options.
	 */
	private void sendCreditOptions() {
		CreditReward.sendOptions(interpreter, player, startIndex);
		stage = 1;
	}

	/**
	 * Sends the perks on the component.
	 */
	private void sendPerks() {
		player.getInterfaceManager().open(new Component(275));
		for (int i = 0; i < 257; i++) {
			player.getPacketDispatch().sendString("", 275, i);
		}
		String blue = "<col=08088A>";
		String red = "<col=8A0808>";
		player.getPacketDispatch().sendString("<col=8A0808>" + "Perks" + "</col>", 275, 2);
		StringBuilder builder = new StringBuilder(player.getDetails().getPortal().getShop().getPerks().isEmpty() ? (blue + "You don't have any " + red + "perks.") : ("<col=08088A>Your current active " + red + "perks:<br><br>"));
		for (Perks p : player.getDetails().getPortal().getShop().getPerks()) {
			if (p == null || p.getInfoName() == null) {
				continue;
			}
			builder.append(red + p.getInfoName() + "<br><br>");
		}
		player.getPacketDispatch().sendString(builder.toString(), 275, 4);
	}

	/**
	 * Sends the options with a premade header.
	 * @param options the options.
	 */
	public void sendOptions(String... options) {
		interpreter.sendOptions("Credit balance: " + player.getDetails().getPortal().getShop().getCredits(), options);
	}

	/**
	 * A credit reward.
	 * @author Vexia
	 */
	public enum CreditReward {
		DXP("Double experience (2 hours)", 10) {

			@Override
			public boolean handle(Player player, DialogueInterpreter interpreter) {
				long milliseconds = player.getDetails().getPortal().getShop().getDoubleExp() - System.currentTimeMillis();
				int minutes = (int) Math.round(milliseconds / 60000);
				if (player.getDetails().getPortal().getShop().getDoubleExp() > System.currentTimeMillis()) {
					if (minutes == 0) {
						interpreter.sendDialogue("You currently have double experience active for another minute.");
						return false;
					}
					interpreter.sendDialogue("You currently have double experience active for " + minutes, "more" + " minute" + (minutes > 1 ? "s" : "") + ".");
					return false;
				}
				if (player.getSavedData().getGlobalData().getDoubleExpDelay() > System.currentTimeMillis()) {
					milliseconds = player.getSavedData().getGlobalData().getDoubleExpDelay() - System.currentTimeMillis();
					long millis = milliseconds;
					String time = String.format("%2d hours and %02d minutes", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
					interpreter.sendDialogue("You must wait " + time, "to claim double experience again.");
					return false;
				}
				return true;
			}

			@Override
			public void reward(Player player, DialogueInterpreter interpreter, int credits) {
				player.getDetails().getShop().setDoubleExp(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2));
				player.getSavedData().getGlobalData().setDoubleExpDelay(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12));
				player.getStateManager().set(EntityState.DOUBLE_EXPERIENCE, 12000);
			}

			@Override
			public boolean canBuy(Player player, DialogueInterpreter interpreter, int credits) {
				if (player.getDetails().getShop().hasPerk(Perks.DOUBLE_XP)) {
					return true;
				}
				return super.canBuy(player, interpreter, credits);
			}

			@Override
			public boolean finalize(Player player, DialogueInterpreter interpreter, int credits) {
				if (player.getDetails().getShop().hasPerk(Perks.DOUBLE_XP) && player.getDetails().getPortal().getShop().removePerk(Perks.DOUBLE_XP)) {
					interpreter.sendDialogue("Thank you, you have activated your double experience perk.");
					return true;
				}
				return super.finalize(player, interpreter, credits);
			}

		},
		EXP_LAMP("Experience Lamp", 3) {

			@Override
			public void reward(Player player, DialogueInterpreter interpreter, int credits) {
				player.getInventory().add(Lamps.K_ACHIEVEMENT_2.getItem());
				super.reward(player, interpreter, credits);
			}

			@Override
			public boolean canBuy(Player player, DialogueInterpreter interpreter, int credits) {
				if (player.getInventory().freeSlots() < 1) {
					interpreter.sendDialogue("You don't have enough inventory space.");
					return false;
				}
				return super.canBuy(player, interpreter, credits);
			}
		};

		/**
		 * The description of the reward.
		 */
		private final String description;

		/**
		 * The credit amount.
		 */
		private final int amount;

		/**
		 * Constructs a new {@code CreditReward} {@code Object}.
		 * @param description the description.
		 * @param amount the amount.
		 */
		private CreditReward(String description, int amount) {
			this.description = description;
			this.amount = amount;
		}

		/**
		 * Handles the reward.
		 * @param player the player.
		 * @param interpreter the interpreter.
		 * @return {@code True} if handled.
		 */
		public boolean handle(Player player, DialogueInterpreter interpreter) {
			return true;
		}

		/**
		 * Rewards the player with what they purchased.
		 * @param player the player.
		 * @param interpreter the interpreter.
		 * @param credits the credits.
		 */
		public void reward(Player player, DialogueInterpreter interpreter, int credits) {

		}

		/**
		 * Buys a credit reward.
		 * @param player the player.
		 * @param interpreter the interpreter.
		 */
		public void buy(final Player player, final DialogueInterpreter interpreter) {
			TaskExecutor.executeSQL(new Runnable() {

				@Override
				public void run() {
					int credits = player.getDetails().getShop().getCredits();
					if (!canBuy(player, interpreter, credits)) {
						return;
					}
					if (handle(player, interpreter) && CreditReward.this.finalize(player, interpreter, credits)) {
						reward(player, interpreter, credits);
					}
				}
			});
		}

		/**
		 * Finalizes the purchase.
		 * @param player the player.
		 * @param interpreter the interpreter.
		 * @param credits the credits.
		 * @return {@code True} if fianlzied.
		 */
		public boolean finalize(Player player, DialogueInterpreter interpreter, int credits) {
			player.getDetails().getShop().setCredits(credits - amount, true);
			credits = player.getDetails().getShop().getCredits();
			interpreter.sendDialogue("Thank you, your purchase has been processed. You now", "have " + credits + " credit" + (credits > 1 || credits < 1 ? "s" : "") + " left.");
			return true;
		}

		/**
		 * Checks if we can buy the reward.
		 * @param player the player.
		 * @param interpreter the interpreter.
		 * @param credits the credits.
		 * @return {@code True} if so.
		 */
		public boolean canBuy(Player player, DialogueInterpreter interpreter, int credits) {
			if (credits < 0) {
				return false;
			}
			if (credits < amount) {
				interpreter.sendDialogue("Sorry, you don't have enough credits. You need a minimum of " + amount, "credit" + (amount > 1 ? "s" : "") + " in order to buy this perk.");
				return false;
			}
			return true;
		}

		/**
		 * Sends options.
		 * @param interpreter the interpreter.
		 * @param player the player.
		 * @param startIndex the start index.
		 */
		private static void sendOptions(DialogueInterpreter interpreter, Player player, int startIndex) {
			final List<String> options = new ArrayList<>();
			for (int i = startIndex; i < values().length; i++) {
				options.add(values()[i].getDescription() + " - " + values()[i].getAmount() + "C");
			}
			if (options.size() == 1) {
				options.add("End");
			}
			interpreter.sendOptions("Credit balance: " + player.getDetails().getPortal().getShop().getCredits(), options.toArray(new String[] {}));
		}

		/**
		 * Gets the description.
		 * @return The description.
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Gets the amount.
		 * @return The amount.
		 */
		public int getAmount() {
			return amount;
		}

	}

	@Override
	public int[] getIds() {
		return new int[] { DialogueInterpreter.getDialogueKey("player_portal") };
	}

}
