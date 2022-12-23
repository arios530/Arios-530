package plugin.interaction.inter;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.content.dialogue.DialogueAction;
import org.arios.game.content.skill.LevelUp;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.RunScript;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle the skilling tab.
 * @author Vexia
 * @author Splinter
 * @version 1.1
 */
public final class SkillTabInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(320, this);
		return this;
	}

	@Override
	public boolean handle(Player p, Component component, int opcode, int button, int slot, int itemId) {
		final SkillConfig config = SkillConfig.forId(button);
		if (config == null) {
			return true;
		}
		if (!GameWorld.isEconomyWorld()) {
			p.getDialogueInterpreter().sendOptions("Select an Option", "Skill Menu", "Set Skill");
			p.getDialogueInterpreter().addAction(new DialogueAction() {

				@Override
				public void handle(Player player, int buttonId) {
					switch (buttonId) {
					case 1:
						player.getPulseManager().clear();
						player.getInterfaceManager().open(new Component(499));
						player.getConfigManager().set(965, config.getConfig());
						player.getAttributes().put("skillMenu", config.getConfig());
						break;
					case 2:
						if (player.inCombat() || player.getLocks().isInteractionLocked() || player.getSkullManager().isWilderness() || player.getAttribute("activity", null) != null) {
							player.getPacketDispatch().sendMessage("You can't do that right now.");
							return;
						}
						player.getDialogueInterpreter().sendInput(false, "Enter the level:");
						player.setAttribute("runscript", new RunScript() {

							@Override
							public boolean handle() {
								int val = (int) getValue();
								int slot = config.getSkillId();
								if (val > 99) {
									val = 99;
								} else if (val < 1) {
									val = 1;
								}
								if ((slot == Skills.ATTACK || slot == Skills.DEFENCE || slot == Skills.STRENGTH || slot == Skills.HITPOINTS || slot == Skills.RANGE || slot == Skills.MAGIC) && player.getEquipment().itemCount() != 0) {
									player.sendMessage("You can't have equipment on when setting a level.");
									return true;
								}
								player.getSkills().setLevel(slot, val);
								player.getSkills().setStaticLevel(slot, val);
								player.getSkills().updateCombatLevel();
								player.getAppearance().sync();
								return true;
							}

						});
						break;
					}
				}

			});
			return true;
		}
		if (p.getAttribute("levelup:" + config.getSkillId(), false)) {
			p.removeAttribute("levelup:" + config.getSkillId());
			LevelUp.sendFlashingIcons(p, -1);
			p.getConfigManager().set(1230, ADVANCE_CONFIGS[config.getSkillId()]);
			p.getInterfaceManager().open(new Component(741));
		} else {
			p.getPulseManager().clear();
			p.getInterfaceManager().open(new Component(499));
			p.getConfigManager().set(965, config.getConfig());
			p.getAttributes().put("skillMenu", config.getConfig());
		}
		return true;
	}
	
	/**
	 * Holds all the config values of the skill advances.
	 */
	public static final int[] ADVANCE_CONFIGS = {
		9, 40, 17, 49,
		25, 57, 33, 641,
		659, 664, 121, 649,
		89, 114, 107, 107,
		64, 80, 673, 680,
		99, 698, 689, 705, 
	};

	public enum SkillConfig {
		ATTACK(125, 1, Skills.ATTACK), 
		STRENGTH(126, 2, Skills.STRENGTH), 
		DEFENCE(127, 5, Skills.DEFENCE), 
		RANGE(128, 3, Skills.RANGE), 
		PRAYER(129, 7, Skills.PRAYER), 
		MAGIC(130, 4, Skills.MAGIC), 
		RUNECRAFTING(131, 12, Skills.RUNECRAFTING), 
		HITPOINTS(133, 6, Skills.HITPOINTS), 
		AGILITY(134, 8, Skills.AGILITY), 
		HERBLORE(135, 9, Skills.HERBLORE), 
		THIEVING(136, 10, Skills.THIEVING), 
		CRAFTING(137, 11, Skills.CRAFTING), 
		FLETCHING(138, 19, Skills.FLETCHING), 
		SLAYER(139, 20, Skills.SLAYER), 
		MINING(141, 13, Skills.MINING), 
		SMITHING(142, 14, Skills.SMITHING), 
		FISHING(143, 15, Skills.FISHING), 
		COOKING(144, 16, Skills.COOKING), 
		FIREMAKING(145, 17, Skills.FIREMAKING),
		WOODCUTTING(146, 18, Skills.WOODCUTTING),
		FARMING(147, 21, Skills.FARMING), 
		CONSTRUCTION(132, 22, Skills.CONSTRUCTION), 
		HUNTER(140, 23, Skills.HUNTER), 
		SUMMONING(148, 24, Skills.SUMMONING);

		/**
		 * Constructs a new {@code SkillConfig} {@code Object}.
		 * @param button the button.
		 * @param config the config.
		 */
		SkillConfig(int button, int config, int skillId) {
			this.button = button;
			this.config = config;
			this.skillId = skillId;
		}

		/**
		 * Represents the button.
		 */
		private int button;

		/**
		 * Represents the config.
		 */
		private int config;

		/**
		 * The skill id.
		 */
		private int skillId;
		
		/**
		 * Gets the skill config.
		 * @param id the id.
		 * @return the skill config.
		 */
		public static SkillConfig forId(int id) {
			for (SkillConfig config : SkillConfig.values()) {
				if (config.button == id)
					return config;
			}
			return null;
		}

		/**
		 * Gets the button.
		 * @return the button.
		 */
		public int getButton() {
			return button;
		}

		/**
		 * Gets the config.
		 * @return the config.
		 */
		public int getConfig() {
			return config;
		}
		
		/**
		 * Gets the skill id.
		 * @return The skill id.
		 */
		public int getSkillId() {
			return skillId;
		}
	}
}