package plugin.interaction.object;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.component.CloseEvent;
import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.content.dialogue.DialogueInterpreter;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.eco.ge.GrandExchangeOffer;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.content.global.tutorial.TutorialStage;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.IronmanMode;
import org.arios.game.node.entity.player.link.RunScript;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.repository.Repository;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ContainerContext;
import org.arios.net.packet.out.ContainerPacket;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used for anything related to banking.
 * @author Vexia
 * @author Emperor
 * 
 */
public final class BankingPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.setOptionHandler("use-quickly", this);
		ObjectDefinition.setOptionHandler("use", this);
		ObjectDefinition.setOptionHandler("bank", this);
		ObjectDefinition.setOptionHandler("collect", this);
		ObjectDefinition.setOptionHandler("deposit", this);
		new BankingInterface().newInstance(arg);
		new BankDepositInterface().newInstance(arg);
		new BankNPCPlugin().newInstance(arg);
		new BankNPC().newInstance(arg);
		new BankerDialogue().init();
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final GameObject object = (GameObject) node;
		if (player.getIronmanManager().checkRestriction(IronmanMode.ULTIMATE)) {
			return true;
		}
		if (object.getName().contains("Bank") || object.getName().contains("Deposit")) {
			switch (option) {
			case "use":
				final Location l = object.getLocation();
				final Location p = player.getLocation();
				final NPC npc = Repository.findNPC(l.transform(l.getX() - p.getX(), l.getY() - p.getY(), 0));
				if (node.getId() == 4483) {
					player.getBank().open();
					return true;
				}
				if (npc != null && DialogueInterpreter.contains(npc.getId())) {
					npc.faceLocation(node.getLocation());
					player.getDialogueInterpreter().open(npc.getId(), npc.getId());
				} else {
					player.getDialogueInterpreter().open(494);
				}
				return true;
			case "use-quickly":
			case "bank":
				player.getBankPinManager().openType(1);
				return true;
			case "collect":
				player.getGrandExchange().openCollectionBox();
				return true;
			case "deposit":
				openDepositBox(player);
				return true;
			}
		}
		return true;
	}

	/**
	 * Method used to open the deposit box.
	 * @param player the player.
	 */
	private void openDepositBox(final Player player) {
		player.getInterfaceManager().removeTabs(3, 4);
		player.getInterfaceManager().open(new Component(11)).setCloseEvent(new CloseEvent() {
			@Override
			public boolean close(Player player, Component c) {
				player.getInterfaceManager().openDefaultTabs();
				return true;
			}
		});
		PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 11, 61, 91, player.getInventory(), true));
	}

	/**
	 * Represents the dialogue plugin used for all bankers.
	 * @author 'Vexia
	 * @version 1.0
	 */
	public static final class BankerDialogue extends DialoguePlugin {

		/**
		 * Represents the banker npc ids.
		 */
		private static final int[] NPC_IDS = { 44, 45, 166, 494, 495, 496, 497, 498, 499, 902, 1036, 1360, 1702, 2163, 2164, 2354, 2355, 2568, 2569, 2570, 2619, 3046, 3198, 3199, 3824, 4296, 4519, 5257, 5258, 5259, 5260, 5383, 5488, 5776, 5777, 5898, 5912, 5913, 6200, 6362, 6532, 6533, 6534, 6535, 6538, 7049, 7050, 7445, 7446, 7605 };

		/**
		 * Represents the id to use.
		 */
		private int id;

		/**
		 * Constructs a new {@code BankerDialogue} {@code Object}.
		 */
		public BankerDialogue() {
			/**
			 * empty.
			 */
		}

		/**
		 * Constructs a new {@code BankerDialoguePlugin} {@code Object}.
		 * @param player the player.
		 */
		public BankerDialogue(Player player) {
			super(player);
		}

		@Override
		public DialoguePlugin newInstance(Player player) {
			return new BankerDialogue(player);
		}

		@Override
		public boolean open(Object... args) {
			if (args[0] instanceof NPC) {
				setId(((NPC) args[0]).getId());
			} else {
				setId((int) args[0]);
			}
			if (TutorialSession.getExtension(player).getStage() < TutorialSession.MAX_STAGE) {
				Component.setUnclosable(player, interpreter.sendDialogues(id, FacialExpression.NORMAL, "Good day, would you like to access your bank account?"));
				stage = 999;
				return true;
			}
			interpreter.sendDialogues(id, FacialExpression.NORMAL, "Good day, How may I help you?");
			for (GrandExchangeOffer o : player.getGrandExchange().getOffers()) {
				if (o != null && (o.getWithdraw()[0] != null || o.getWithdraw()[1] != null)) {
					stage = -1;
					break;
				}
			}
			return true;
		}

		@Override
		public boolean handle(int interfaceId, int buttonId) {
			switch (stage) {
			case -1:
				interpreter.sendDialogues(id, FacialExpression.NORMAL, "Before we go any further, I should inform you that you", "have items ready for collection from the Grand Exchange.");
				stage = 0;
				break;
			case 0:
				interpreter.sendOptions("What would you like to say?", "I'd like to access my bank account, please.", "I'd like to check my PIN settings.", "I'd like to see my collection box.", "What is this place?");
				stage = 10;
				break;
			case 1:
				interpreter.sendDialogues(id, FacialExpression.NORMAL, "This is a branch of the Bank of " + GameWorld.getName() + ". We have", "branches in many towns.");
				stage = 2;
				break;
			case 2:
				interpreter.sendOptions("What would you like to say?", "And what do you do?", "Didn't you used to be called the Bank of Varrock?");
				stage = 3;
				break;
			case 3:
				if (buttonId == 1) {
					interpreter.sendDialogues(player, FacialExpression.NORMAL, "And what do you do?");
					stage = 4;
				} else if (buttonId == 2) {
					interpreter.sendDialogues(player, FacialExpression.NORMAL, "Didnt you used to be called the Bank of Varrock?");
					stage = 5;
				}
				break;
			case 4:
				interpreter.sendDialogues(id, FacialExpression.NORMAL, "We will look after your items and money for you.", "Leave your valuables with us if you want to keep them", "safe.");
				stage = 100;
				break;
			case 5:
				interpreter.sendDialogues(id, FacialExpression.NORMAL, "Yes we did, but people kept on coming into our", "signs were wrong. They acted as if we didn't know", "what town we were in or something.");
				stage = 100;
				break;
			case 100:
				end();
				break;
			case 999:
				interpreter.sendOptions("Select an Option", "Yes.", "No.");
				stage = 1000;
				break;
			case 1000:
				switch (buttonId) {
				case 1:
					end();
					if (TutorialSession.getExtension(player).getStage() == 56) {
						player.getBank().add(new Item(995, 25));
					}
					player.getBank().open();
					if (TutorialSession.getExtension(player).getStage() == 56) {
						TutorialStage.load(player, 57, false);
					}
					break;
				case 2:
					end();
					TutorialStage.load(player, TutorialSession.getExtension(player).getStage(), false);
					break;
				}
				break;
			case 10:
				switch (buttonId) {
					case 1:
					case 2:
					case 3:
						player.getBankPinManager().openType(buttonId);
						end();
						break;
					case 4:
						interpreter.sendDialogues(player, FacialExpression.NORMAL, "What is this place?");
						stage = 1;
						break;
				}
				break;
			}
			return true;
		}

		@Override
		public int[] getIds() {
			return NPC_IDS;
		}

		/**
		 * Gets the id.
		 * @return The id.
		 */
		public int getId() {
			return id;
		}

		/**
		 * Sets the id.
		 * @param id The id to set.
		 */
		public void setId(int id) {
			this.id = id;
		}

	}

	/**
	 * Represents the component plugin used to handle banking interfaces.
	 * @author Emperor
	 * @author 'Vexia
	 * @version 1.0
	 */
	public static final class BankingInterface extends ComponentPlugin {

		@Override
		public Plugin<Object> newInstance(Object arg) throws Throwable {
			ComponentDefinition.put(763, this);
			ComponentDefinition.put(762, this);
			return this;
		}

		@Override
		public boolean handle(final Player p, Component component, int opcode, int button, final int slot, int itemId) {
			final Item item = component.getId() == 762 ? p.getBank().get(slot) : p.getInventory().get(slot);
			switch (component.getId()) {
			case 762:
				switch (button) {
				case 18:
					// p.getFamiliarManager().dumpBob();
					p.getDialogueInterpreter().open(628371);
					return true;
				case 16:
					p.getBank().setNoteItems(!p.getBank().isNoteItems());
					return true;
				case 73:
					int amount = 0;
					switch (opcode) {
					case 155:
						amount = 1;
						break;
					case 196:
						amount = 5;
						break;
					case 124:
						amount = 10;
						break;
					case 199:
						amount = p.getBank().getLastAmountX();
						break;
					case 234:
						p.setAttribute("runscript", new RunScript() {
							@Override
							public boolean handle() {
								int amount = (int) value;
								p.getBank().takeItem(slot, amount);
								p.getBank().updateLastAmountX(amount);
								return true;
							}
						});
						p.getDialogueInterpreter().sendInput(false, "Enter the amount.");
						break;
					case 168:
						amount = p.getBank().getAmount(item);
						break;
					case 166:
						amount = p.getBank().getAmount(item) - 1;
						break;
					default:
						return false;
					}
					if (amount > 0) {
						final int withdraw = amount;
						GameWorld.submit(new Pulse(1, p) {
							@Override
							public boolean pulse() {
								if (item == null) {
									return true;
								}
								p.getBank().takeItem(slot, withdraw);
								return true;
							}
						});
					}
					return true;
				case 41:
				case 39:
				case 37:
				case 35:
				case 33:
				case 31:
				case 29:
				case 27:
				case 25:
				case 23:
					int tabIndex = -((button - 41) / 2);
					switch (opcode) {
					case 155:
						p.getBank().setTabIndex(tabIndex);
						return true;
					case 196:
						p.getBank().collapseTab(tabIndex);
						return true;
					}
					return false;
				}
				break;
			case 763:// overlay.
				switch (opcode) {
				case 155:
					p.getBank().addItem(slot, 1);
					break;
				case 196:
					p.getBank().addItem(slot, 5);
					break;
				case 124:
					p.getBank().addItem(slot, 10);
					break;
				case 199:
					p.getBank().addItem(slot, p.getBank().getLastAmountX());
					break;
				case 234:
					p.setAttribute("runscript", new RunScript() {
						@Override
						public boolean handle() {
							int amount = (int) value;
							p.getBank().addItem(slot, amount);
							p.getBank().updateLastAmountX(amount);
							return false;
						}
					});
					p.getDialogueInterpreter().sendInput(false, "Enter the amount.");
					break;
				case 168:
					p.getBank().addItem(slot, p.getInventory().getAmount(item));
					break;
				}
				break;
			}
			return true;
		}

	}

	/**
	 * Represents the bank deposit interface handler.
	 * @author 'Vexia
	 * @version 1.0
	 */
	public static final class BankDepositInterface extends ComponentPlugin {

		/**
		 * Represents the deposit animation.
		 */
		private static final Animation DEPOSIT_ANIMATION = new Animation(834);

		@Override
		public Plugin<Object> newInstance(Object arg) throws Throwable {
			ComponentDefinition.put(11, this);
			return this;
		}

		@Override
		public boolean handle(final Player p, Component component, int opcode, int button, final int slot, int itemId) {
			final Item item = p.getInventory().get(slot);
			if (item == null && opcode != 180) {
				return true;
			}
			switch (component.getId()) {
			case 11:
				p.animate(DEPOSIT_ANIMATION);
				switch (opcode) {
				case 133: // Equip.
					p.getBank().addItem(slot, 1);
					break;
				case 236:
					p.getBank().addItem(slot, 5);
					break;
				case 157:
					p.getBank().addItem(slot, 10);
					break;
				case 243:
					p.getPulseManager().run(new Pulse(1, p) {
						@Override
						public boolean pulse() {
							p.getBank().addItem(slot, p.getInventory().getAmount(item));
							PacketRepository.send(ContainerPacket.class, new ContainerContext(p, 11, 61, 91, p.getInventory(), true));
							return true;
						}
					});
					return true;
				case 145:
					p.setAttribute("runscript", new RunScript() {
						@Override
						public boolean handle() {
							int ammount = (int) value;
							p.getBank().addItem(slot, ammount);
							PacketRepository.send(ContainerPacket.class, new ContainerContext(p, 11, 61, 91, p.getInventory(), true));
							return false;
						}
					});
					p.getDialogueInterpreter().sendInput(false, "Enter the amount.");
					break;
				case 180:// B.O.B (deposit box)
					p.getFamiliarManager().dumpBob();
					break;
				}
				break;
			}
			PacketRepository.send(ContainerPacket.class, new ContainerContext(p, 11, 61, 91, p.getInventory(), true));
			return true;
		}

	}

	/**
	 * Represents the plugin used to handle the banker npc.
	 * @author 'Vexia
	 * @author Emperor
	 * @version 1.01
	 */
	public static final class BankNPCPlugin extends OptionHandler {

		@Override
		public Plugin<Object> newInstance(Object arg) throws Throwable {
			NPCDefinition.setOptionHandler("bank", this);
			NPCDefinition.setOptionHandler("collect", this);
			return this;
		}

		@Override
		public boolean handle(Player player, Node node, String option) {
			final NPC npc = ((NPC) node);
			npc.faceLocation(node.getLocation());
			if (option.equals("bank")) {
				player.getBank().open();
			} else {
				player.getGrandExchange().openCollectionBox();
			}
			return true;
		}

		@Override
		public Location getDestination(Node n, Node node) {
			NPC npc = (NPC) node;
			if (npc.getAttribute("facing_booth", false)) {
				Direction dir = npc.getDirection();
				return npc.getLocation().transform(dir.getStepX() << 1, dir.getStepY() << 1, 0);
			}
			if (npc.getId() == 6533) {
				return Location.create(3167, 3490, 0);// ge bankers.
			} else if (npc.getId() == 6535) {
				return Location.create(3162, 3489, 0);
			} else if (npc.getId() == 4907) {
				return npc.getLocation().transform(0, -2, 0);
			}
			return super.getDestination(npc, node);
		}
	}

	/**
	 * Represents the abstract npc of a banker.
	 * @author 'Vexia
	 * @author Emperor
	 * @version 1.2
	 */
	public static final class BankNPC extends AbstractNPC {

		/**
		 * Represents the ids of this class.
		 */
		private final int[] IDS = new int[] { 4907, 44, 45, 166, 494, 495, 496, 497, 498, 499, 902, 1036, 1360, 1702, 2163, 2164, 2354, 2355, 2568, 2569, 2570, 2619, 3046, 3198, 3199, 4296, 4519, 5257, 5258, 5259, 5260, 5383, 5488, 5776, 5777, 5898, 5912, 5913, 6200, 6362, 6532, 6533, 6534, 6535, 6538, 7049, 7050, 7445, 7446, 7605 };

		/**
		 * Constructs a new {@code BankNPC} {@code Object}.
		 */
		public BankNPC() {
			super(0, null);
		}

		@Override
		public void init() {
			super.init();
			for (int i = 0; i < 4; i++) {
				Direction dir = Direction.get(i);
				Location loc = getLocation().transform(dir.getStepX(), dir.getStepY(), 0);
				GameObject bank = RegionManager.getObject(loc);
				if (bank != null && bank.getName().equals("Bank booth")) {
					setDirection(dir);
					setAttribute("facing_booth", true);
					super.setWalks(false);
					break;
				}
			}
		}

		/**
		 * Constructs a new {@code BankNPC} {@code Object}.
		 * @param id The NPC id.
		 * @param location The location.
		 */
		private BankNPC(int id, Location location) {
			super(id, location);
		}

		@Override
		public AbstractNPC construct(int id, Location location, Object... objects) {
			return new BankNPC(id, location);
		}

		@Override
		public int[] getIds() {
			return IDS;
		}
	}
}
