package plugin.spawn;

import org.arios.ServerConstants;
import org.arios.game.content.dialogue.DialogueInterpreter;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.tutorial.CharacterDesign;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.path.Pathfinder;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.CameraContext;
import org.arios.net.packet.context.CameraContext.CameraType;
import org.arios.net.packet.context.MinimapStateContext;
import org.arios.net.packet.out.CameraViewPacket;
import org.arios.net.packet.out.MinimapState;

/**
 * Handles the tutorial for spawn mode.
 * @author
 */
public class SpawnTutorialDialogue extends DialoguePlugin {

	/**
	 * The name of the spawn tutorial dialogue.
	 */
	public static final String NAME = "spawn-tutorial";

	/**
	 * The classes.
	 */
	private static final String[] CLASSES = new String[] { "Main", "Hybrid", "Range Tank", "Pure", "Zerker" };

	/**
	 * The falling animation used when logging in.
	 */
	private static final Animation FALLING_ANIMATION = new Animation(4367);

	/**
	 * The default bank.
	 */
	public static final Item[] DEFAULT_BANK = new Item[] {
	/* runes */
	new Item(556, 20000), new Item(557, 20000), new Item(558, 10000), new Item(559, 10000), new Item(560, 10000), new Item(561, 10000), new Item(562, 12000), new Item(563, 5000), new Item(564, 10000), new Item(565, 12000), new Item(566, 10000), new Item(9075, 10000),
	/* food and potions */
	new Item(385, 5000), new Item(2436, 1000), new Item(2440, 1000), new Item(2442, 1000), new Item(2448, 500), new Item(3040, 1000), new Item(2434, 1000), new Item(6685, 1000), new Item(3024, 1000), new Item(3016, 500), new Item(2452, 500),
	/* melee weapons */
	new Item(4153, 50), new Item(6528, 50), new Item(4587, 50), new Item(1305, 50), new Item(7158, 50), new Item(1434, 50), new Item(1377, 50), new Item(1215, 50), new Item(5698, 35), new Item(4151, 50),
	/* Range weapons and bolts */
	new Item(11230, 750), new Item(11234, 350), new Item(10499, 150), new Item(861, 250), new Item(861, 250), new Item(892, 5000), new Item(5627, 1000), new Item(9185, 250), new Item(9144, 5000), new Item(9244, 2500), new Item(9242, 2500), new Item(9243, 2500), new Item(9245, 2500),
	/* amulets and rings */
	new Item(1725, 100), new Item(1727, 100), new Item(1729, 100), new Item(1712, 100), new Item(2550, 100), new Item(2552, 100), new Item(2570, 100),
	/* gear - helmets and misc */
	new Item(10828, 150), new Item(3749, 200), new Item(3751, 200), new Item(3753, 200), new Item(3755, 200),
	/* Random melee gear + range gear */
	new Item(9672, 150), new Item(9674, 150), new Item(9676, 150), new Item(6328, 100), new Item(4131, 50), new Item(3105, 100), new Item(1540, 100), new Item(577, 150), new Item(579, 150), new Item(1011, 150), new Item(1013, 150), new Item(4502, 100), new Item(1129, 100), new Item(1067, 100), new Item(1115, 100), new Item(1153, 100), new Item(7003, 100), new Item(2491, 100), new Item(2497, 100), new Item(1167, 100), new Item(6106, 100), new Item(6107, 100), new Item(6109, 100), new Item(6111, 100), new Item(542, 100), new Item(544, 100), new Item(656, 100), new Item(658, 100), new Item(660, 100), new Item(662, 100), new Item(664, 100), new Item(3105, 100), new Item(2579, 100), new Item(7458, 100), new Item(1949, 100), new Item(1079, 100), new Item(1093, 100), new Item(1127, 100), new Item(1163, 100), new Item(1201, 100), new Item(6524, 100), new Item(3122, 100), new Item(1052, 100),
	/* gear (mystic - all sets) */
	new Item(4089, 100), new Item(4091, 100), new Item(4093, 100), new Item(4095, 100), new Item(4097, 100), new Item(4099, 100), new Item(4101, 100), new Item(4103, 100), new Item(4105, 100), new Item(4107, 100), new Item(4109, 100), new Item(4111, 100), new Item(4113, 100), new Item(4115, 100), new Item(4117, 100),
	/* Mage books */
	new Item(3840, 10), new Item(3842, 10), new Item(3844, 10) };

	/**
	 * The pure bank.
	 */
	private static final Item[] PURE_BANK = new Item[] {
	/* runes */
	new Item(556, 20000), new Item(557, 20000), new Item(558, 10000), new Item(559, 10000), new Item(560, 10000), new Item(561, 10000), new Item(562, 12000), new Item(563, 5000), new Item(564, 10000), new Item(565, 12000), new Item(566, 10000), new Item(9075, 10000),

	/* food and potions */
	new Item(385, 5000), new Item(2440, 1000), new Item(2442, 1000), new Item(2448, 500), new Item(3040, 1000), new Item(2434, 1000), new Item(6685, 1000), new Item(3024, 1000), new Item(3016, 500), new Item(2452, 500),

	/* Armour / gear */
	new Item(577, 150), new Item(579, 150), new Item(1011, 150), new Item(1013, 150), new Item(4502, 100), new Item(1129, 100), new Item(1067, 100), new Item(1115, 100), new Item(1153, 100), new Item(7003, 100), new Item(2491, 100), new Item(2497, 100), new Item(1167, 100), new Item(6106, 100), new Item(6107, 100), new Item(6109, 100), new Item(6111, 100), new Item(542, 100), new Item(544, 100), new Item(656, 100), new Item(658, 100), new Item(660, 100), new Item(662, 100), new Item(664, 100), new Item(3105, 100), new Item(2579, 100), new Item(7458, 100), new Item(1949, 100),

	/* Range weapons and bolts */
	new Item(6522, 850), new Item(11230, 750), new Item(10499, 150), new Item(11234, 250), new Item(861, 250), new Item(861, 250), new Item(892, 5000), new Item(5627, 1000), new Item(9185, 100), new Item(9144, 5000), new Item(9244, 2500), new Item(9242, 2500), new Item(9243, 2500), new Item(9245, 2500),

	/* melee weapons */
	new Item(4153, 50), new Item(6528, 50), new Item(4587, 50), new Item(1305, 50), new Item(7158, 50), new Item(1434, 50), new Item(1377, 50), new Item(1215, 50), new Item(5698, 50), new Item(4151, 50),

	/* Mage books */
	new Item(3840, 10), new Item(3842, 10), new Item(3844, 10),

	/* amulets and rings */
	new Item(1725, 100), new Item(1727, 100), new Item(1729, 100), new Item(1712, 100), new Item(2550, 100), new Item(2552, 100), new Item(2570, 100) };

	/**
	 * The class type selected.
	 */
	private ClassType type;

	/**
	 * The start loc.
	 */
	private Location start;

	/**
	 * Constructs a new {@Code SpawnTutorialDialogue} {@Code
	 * Object}
	 */
	public SpawnTutorialDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@Code SpawnTutorialDialogue} {@Code
	 * Object}
	 * @param player the player.
	 */
	public SpawnTutorialDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new SpawnTutorialDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		start = ServerConstants.getStartLocation();
		switch (player.getSavedData().getSpawnData().getTutorialStage()) {
		case 0:
			interpreter.sendDialogues(player, FacialExpression.DISTRESSED, "What was that!?");
			break;
		default:
			player.getProperties().setTeleportLocation(ServerConstants.getStartLocation());
			interpreter.sendDialogues(3123, null, false, "Welcome back, let's get back to where we left off.");
			player.getAppearance().setHeadIcon(12);
			player.getAppearance().sync();
			break;
		}
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (player.getSavedData().getSpawnData().getTutorialStage()) {
		case 0:
			switch (stage) {
			case 0:
				interpreter.sendDialogues(3123, FacialExpression.SNEAKY, false, "Sorry about that one mate! My aim isn't always", "the greatest! Anyways, since you're here let's", "begin!");
				stage = -1;
				break;
			case -1:
				interpreter.sendDialogues(3123, null, false, "Welcome to " + GameWorld.getName() + ". I'll be your tour guide.", "Would you like to complete a quick walk-through", "explaining the basic gameplay of " + GameWorld.getName() + "?");
				stage = 1;
				break;
			case 1:
				options("Yes, please.", "No, thanks.");
				stage++;
				break;
			case 2:
				switch (buttonId) {
				case 1:
					player("Yes, please.");
					stage = 10;
					break;
				case 2:
					player("No, thanks.");
					stage = 20;
					break;
				}
				break;
			case 10:
				progress();
				interpreter.sendDialogues(3123, null, false, "Fantastic! Let's get started...");
				stage = 0;
				break;
			case 20:
				interpreter.sendDialogues(3123, null, false, "There's only a couple things you need to do before playing.", "That is to make yourself look a bit more appealing!");
				stage++;
				break;
			case 21:
				interpreter.sendDialogues(player, FacialExpression.ANGRY, "What's wrong with the way I look?!");
				stage++;
				break;
			case 22:
				interpreter.sendDialogues(3123, null, false, "Let's just say that the women of Edgeville", "won't fancy someone that looks like you.");
				player.getSavedData().getSpawnData().setTutorialStage(2);
				stage = 0;
				break;
			}
			break;
		case 1:
			switch (stage) {
			case 0:
				player.getAppearance().setHeadIcon(12);
				player.getAppearance().sync();
				player.getLocks().unlockMovement();
				Pathfinder.find(player.getLocation(), Location.create(3093, 3493, 0)).walk(player);
				player.getLocks().lock();
				PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, start.getX(), start.getY() + 4, 450, 1, 4));
				PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, start.getX(), start.getY() + 4, 450, 1, 4));
				interpreter.sendPlaneMessage(true, "Here are the necessary vendors of " + GameWorld.getName() + ". They will", "provide you with weapons, armour, food, potions", "and many other resources.");
				GameWorld.submit(new Pulse(18, player) {
					int counter;

					@Override
					public boolean pulse() {
						switch (++counter) {
						case 1:
							setDelay(15);
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, start.getX() + 2, start.getY() + 18, 450, 1, 10));
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, start.getX() + 2, start.getY() + 18, 450, 1, 10));
							interpreter.sendPlaneMessage(true, "I have also taken the courtesy to place a set of items", "in your bank. You will be choosing specific", "items at the end of the walk-through.");
							break;
						case 2:
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, start.getX() + 1, start.getY() + 26, 450, 1, 10));
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, start.getX() + 1, start.getY() + 26, 450, 1, 10));
							interpreter.sendPlaneMessage(true, "Here you have the altars of " + GameWorld.getName() + ", you", "can switch between the various magical books and", "refill both your prayer and special attack points.");
							setDelay(12);
							break;
						case 3:
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, start.getX() - 6, start.getY() + 30, 450, 1, 10));
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, start.getX() - 6, start.getY() + 30, 450, 1, 10));
							interpreter.sendPlaneMessage(true, "Out past the wilderness ditch barrier are player", "killers awaiting to challenge you to a deathly fight. Each", "and everyone is competing to become number one. It is up", "to you to gain that title!");
							setDelay(18);
							break;
						case 4:
							player.getLocks().unlockMovement();
							Pathfinder.find(player.getLocation(), start).walk(player);
							player.getLocks().lock();
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, start.getX() - 6, start.getY() + 20, 450, 1, 10));
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, start.getX() - 6, start.getY() + 30, 450, 1, 10));
							interpreter.sendPlaneMessage(true, "Here is the main gathering area where players", "collaborate in numerous ways, trade and discuss different", "events that are currently happening in " + GameWorld.getName() + ".", "You can be sure to find help here at any time.");
							setDelay(15);
							break;
						case 5:
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.POSITION, start.getX(), start.getY() - 5, 450, 1, 10));
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.ROTATION, start.getX(), start.getY() - 5, 450, 1, 10));
							interpreter.sendDialogues(3123, null, false, "Now that we have gone over the basics of " + GameWorld.getName() + "", "you are now ready to change your appearance.");
							return true;

						}
						return false;
					}
				});
				stage++;
				break;
			case 1:
				interpreter.sendDialogues(player, FacialExpression.SAD, "What's wrong with the way I look?");
				stage++;
				break;
			case 2:
				interpreter.sendDialogues(3123, null, false, "Let's just say that the women of Edgeville", "won't fancy someone that looks like you.");
				stage = 0;
				progress();
				break;
			}
			break;
		case 2:
			switch (stage) {
			case 0:
				interpreter.sendPlaneMessageWithBlueTitle("Character Design", "Now you can design the way your character appears in the world.", "Use the buttons on the screen in order to browse through different", "combinations of appearance.", "");
				CharacterDesign.open(player);
				stage++;
				break;
			case 1:
				player("What's a class?");
				stage++;
				break;
			case 2:
				interpreter.sendDialogues(3123, null, false, "A class is the type of item layout you wish to start with.", "We offer various types of classes for players to start off", "with. Your bank will be set up with this class type.");
				stage++;
				break;
			case 3:
				interpreter.sendOptions("Select a Class", "Main", "Hybrid", "Range Tank", "Pure", "Zerker");
				stage++;
				break;
			case 4:
				type = ClassType.values()[buttonId - 1];
				interpreter.sendDialogues(3123, null, false, "You have selected the " + CLASSES[buttonId - 1] + " class type.");
				stage++;
				break;
			case 5:
				interpreter.sendOptions("Are you sure?", "Yes, I'm sure.", "No, I want to pick again.");
				stage++;
				break;
			case 6:
				if (buttonId == 1) {
					player("Yes, I'm sure.");
					stage++;
					break;
				}
				player("No, I want to pick again.");
				stage = 3;
				break;
			case 7:
				progress();
				interpreter.sendDialogues(3123, null, false, "Awesome! You have setup everything you need in order", "to begin your journey of playing " + GameWorld.getName() + "! You may now", "plunder into a game full of surprises.");
				stage = 0;
				break;
			}
			break;
		case 3:
			switch (stage) {
			case 0:
				player.setAttribute("close_c_", true);
				end();
				player.getInterfaceManager().closeChatbox();
				GameWorld.submit(new Pulse(1, player) {
					int counter;

					@Override
					public boolean pulse() {
						switch (counter++) {
						case 1:
							player.lock();
							player.getInterfaceManager().openComponent(115);
							break;
						case 3:
							PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 2));
							break;
						case 4:
							player.getProperties().setTeleportLocation(ServerConstants.getHomeLocation());
							break;
						case 5:
							end();
							if (player.getSession().isActive()) {
								player.getInterfaceManager().close();
							}
							if (player.getSession().isActive()) {
								PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 0));
							}
							player.getInterfaceManager().restoreTabs();
							type.setClass(player);
							player.getAppearance().setHeadIcon(-1);
							player.getAppearance().sync();
							PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.RESET, start.getX() + 1, start.getY() + 26, 450, 1, 10));
							player.animate(FALLING_ANIMATION);
							GameWorld.submit(new Pulse(FALLING_ANIMATION.getDuration(), player) {
								@Override
								public boolean pulse() {
									player.unlock();
									player.getDialogueInterpreter().sendDialogues(player, FacialExpression.ANGRY, "Again!?");
									return true;
								}
							});
							return true;
						}
						return false;
					}
				});
				stage++;
				break;
			case 1:
				player.getInterfaceManager().closeChatbox();
				end();
				break;
			}
			break;
		default:
			System.err.println(player.getSavedData().getSpawnData().getTutorialStage() + ", " + stage);
			break;
		}
		return true;
	}

	/**
	 * Progresses the stage of the tutorial.
	 */
	private void progress() {
		player.getSavedData().getSpawnData().incrementTutorialStage();
	}

	/**
	 * A class type.
	 * @author Vexia
	 */
	public static enum ClassType {
		MAIN(null, DEFAULT_BANK, new Item[] { new Item(6685, 2), new Item(3024, 3), new Item(2436), new Item(2440), new Item(557, 1000), new Item(9075, 400), new Item(560, 200), new Item(5698, 1), new Item(385, 17) }, new Item[] { new Item(3751, 1), new Item(1712, 1), new Item(1079, 1), new Item(1127, 1), new Item(3105, 1), new Item(4151, 1), new Item(1052, 1), new Item(7460, 1) }), HYBRID(null, DEFAULT_BANK, new Item[] { new Item(5698, 1), new Item(2497, 1), new Item(2503, 1), new Item(6685, 3), new Item(3024, 3), new Item(2436, 1), new Item(2440, 1), new Item(385, 13), new Item(560, 400), new Item(555, 600), new Item(565, 200), new Item(8007, 1) }, new Item[] { new Item(10828, 1), new Item(1712, 1), new Item(4091, 1), new Item(4093, 1), new Item(10828, 1), new Item(4675, 1), new Item(3842, 1), new Item(7460, 1), new Item(2413, 1), new Item(3105, 1) }), RANGE_TANK(new int[][] { { Skills.ATTACK, 40 }, { Skills.STRENGTH, 40 } }, DEFAULT_BANK, new Item[] { new Item(2444, 1), new Item(6685, 3), new Item(9244, 30), new Item(3024, 3), new Item(385, 16), new Item(560, 800), new Item(557, 1000), new Item(9075, 400), new Item(8007, 1) }, new Item[] { new Item(3749, 1), new Item(1712, 1), new Item(1079, 1), new Item(2503, 1), new Item(7460, 1), new Item(3105, 1), new Item(9144, 250), new Item(3749, 1), new Item(6524, 1), new Item(9185, 1), new Item(3749, 1), new Item(10499, 1), }), PURE(new int[][] { { Skills.DEFENCE, 1 }, { Skills.PRAYER, 52 } }, PURE_BANK, new Item[] { new Item(4153, 1), new Item(2436, 1), new Item(2440, 1), new Item(2434, 1), new Item(385, 3), new Item(2434, 1), new Item(385, 19), new Item(8007, 1), }, new Item[] { new Item(656, 1), new Item(1712, 1), new Item(6107, 1), new Item(7458, 1), new Item(3105, 1), new Item(4587, 1), new Item(3842, 1), new Item(2414, 1), new Item(2497, 1) }), ZERKER(new int[][] { { Skills.ATTACK, 70 }, { Skills.DEFENCE, 45 }, { Skills.PRAYER, 52 } }, DEFAULT_BANK, new Item[] { new Item(5698, 1), new Item(6685, 3), new Item(3024, 3), new Item(2440, 1), new Item(2436, 1), new Item(385, 15), new Item(560, 800), new Item(557, 1000), new Item(9075, 400), new Item(8007, 1) }, new Item[] { new Item(3751, 1), new Item(1712, 1), new Item(1079, 1), new Item(1127, 1), new Item(3105, 1), new Item(4151, 1), new Item(1052, 1), new Item(7460, 1), });

		/**
		 * The special skills to set.
		 */
		private final int[][] skills;

		/**
		 * The items.
		 */
		private final Item[] bank;

		/**
		 * The inventory.
		 */
		private final Item[] invy;

		/**
		 * The equipment.
		 */
		private final Item[] equipment;

		/**
		 * Constructs a new {@Code ClassType} {@Code Object}
		 * @param items the items.
		 */
		private ClassType(int[][] skills, Item[] bank, Item[] invy, Item[] equipment) {
			this.skills = skills;
			this.bank = bank;
			this.invy = invy;
			this.equipment = equipment;
		}

		/**
		 * Sets a class for a player.
		 * @param player the player.
		 */
		public void setClass(Player player) {
			for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
				player.getSkills().setLevel(i, 99);
				player.getSkills().setStaticLevel(i, 99);
			}
			if (skills != null) {
				for (int i = 0; i < skills.length; i++) {
					player.getSkills().setLevel(skills[i][0], skills[i][1]);
					player.getSkills().setStaticLevel(skills[i][0], skills[i][1]);
				}
			}
			player.getInventory().clear();
			player.getEquipment().clear();
			player.getBank().clear();
			player.getSkills().updateCombatLevel();
			player.getInventory().add(invy);
			player.getBank().add(bank);
			for (Item i : equipment) {
				player.getEquipment().add(i, true, false);
			}
			player.getAppearance().sync();
		}

		/**
		 * Gets the invy.
		 * @return the invy.
		 */
		public Item[] getInvy() {
			return invy;
		}

		/**
		 * Gets the items.
		 * @return the items.
		 */
		public Item[] getBank() {
			return bank;
		}
	}

	@Override
	public int[] getIds() {
		return new int[] { DialogueInterpreter.getDialogueKey(NAME) };
	}

}
