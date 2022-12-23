package plugin.command;

import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.member.slayer.Task;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.entity.player.info.portal.Perks;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.plugin.Plugin;

/**
 * Handles the perk related commands.
 * @author Vexia
 */
public class PerkCommandPlugin extends CommandPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		link(CommandSet.PLAYER);
		return this;
	}

	@Override
	public boolean parse(Player player, String name, String[] args) {
		switch (name) {
		case "swap":
		case "spellswap":
			if (!player.hasPerk(Perks.SPELL_SWAP) && player.getDetails().getRights() != Rights.ADMINISTRATOR) {
				player.sendMessage("You don't have the spell swap perk.");
				return false;
			}
			if (player.inCombat() || player.getLocks().isInteractionLocked() || player.getSkullManager().isWilderness() || player.getAttribute("activity", null) != null) {
				player.getPacketDispatch().sendMessage("You can't do that right now.");
				return true;
			}
			long cooldown = player.getDetails().getShop().get("spellSwap", 0L);
			if (cooldown > System.currentTimeMillis() && player.getDetails().getRights() != Rights.ADMINISTRATOR) {
				player.sendMessage("You are still cooling down from the last spell swap.");
				return true;
			}
			player.animate(new Animation(6299));
			player.graphics(new Graphics(1062));
			player.getDialogueInterpreter().open(3264731, true, true);
			return true;
		case "shop":
		case "credits":
		case "credit":
		case "purchase":
		case "doublexp":
		case "dxp":
			player.getDialogueInterpreter().open("player_portal");
			return true;
		case "cart":
		case "ridecart":
		case "minecart":
			rideLocomotive(player, true);
			return true;
		case "landgc":
		case "resetcard":
		case "stopcart":
		case "endcart":
		case "land":
			endLocomotive(player);
			return true;
		case "copter":
		case "ridecopter":
		case "ridegc":
		case "gnomecopter":
			rideLocomotive(player, false);
			return true;
		case "resettask":
		case "cleartask":
			if (!player.getSlayer().hasTask()) {
				player.sendMessage("You don't have a slayer task!");
				return true;
			}
			Task task = player.getSlayer().getTask();
			if (task.isDisabled() || player.getDetails().getShop().hasPerk(Perks.SLAYER_BETRAYER)) {
				player.sendMessage("You have reset your task.");
				player.getSlayer().clear();
			} else {
				player.sendMessage("This task is obtainable.");
			}
			return true;
		case "setskill":
		case "skill":
		case "cleanslate":
			if (!player.hasPerk(Perks.CLEAN_SLATE)) {
				if (player.isAdmin()) {
					return false;
				}
				player.sendMessage("You need to purchase the 'Clean Slate' perk.");
				return true;
			}
			if (args.length < 2) {
				player.getPacketDispatch().sendMessage("Use as ::skill skillname/id level.");
				return true;
			}
			int id = 0;
			int skillId = -1;
			if (Character.isDigit(args[1].charAt(0))) {
				skillId = toInteger(args[1]);
			} else {
				for (id = 0; id < Skills.SKILL_NAME.length; id++) {
					String skill = Skills.SKILL_NAME[id];
					if (args[1].equals(skill.toLowerCase())) {
						skillId = id;
						break;
					}
				}
			}
			if (skillId < 0) {
				player.getPacketDispatch().sendMessage("Use as ::skill skillname/id level.");
				return true;
			}
			int level = Math.abs(toInteger(args[2]));
			if (level > player.getSkills().getStaticLevel(skillId)) {
				player.sendMessage("You can't set your new level higher than your current.");
				return true;
			}
			if (level < 1) {
				level = 1;
			}
			if (level < 3 && skillId == Skills.HERBLORE) {
				level = 3;
			}
			if (removeCleanSlate(player)) {
				player.getDetails().getShop().getPerks().remove(Perks.CLEAN_SLATE);
				player.getSkills().setLevel(skillId, level);
				player.getSkills().setStaticLevel(skillId, level);
				player.getSkills().updateCombatLevel();
				player.getAppearance().sync();
				player.getDetails().save();
				player.getDialogueInterpreter().sendDialogue("You have set your " + Skills.SKILL_NAME[skillId] + " to " + level + ".");
			} else {
				player.sendMessage("Error! Unable to execute this command.");
			}
			return true;
		}
		return false;
	}

	/**
	 * Removes the clean slate.
	 * @param player the player.
	 */
	private boolean removeCleanSlate(Player player) {
		return player.getDetails().getPortal().getShop().removePerk(Perks.CLEAN_SLATE);
	}

	/**
	 * Rides a locomotive.
	 * @param player the player.
	 * @param cart the cart.
	 */
	private void rideLocomotive(Player player, boolean cart) {
		Perks perk = cart ? Perks.MINE_CART : Perks.GNOME_COPTER;
		if (!player.getDetails().getShop().hasPerk(perk)) {
			player.getDialogueInterpreter().sendDialogue("You don't have access to that perk.");
			return;
		}
		if (cart && player.getAppearance().isRidingMinecart() && player.getAppearance().getAnimationCache()[0] != 2148) {
			player.getAppearance().setRidingMinecart(false);
		}
		if (player.getAppearance().isRidingMinecart() || player.inCombat() || player.getEquipment().getId(EquipmentContainer.SLOT_WEAPON) == 12842 || player.getLocks().isInteractionLocked() || player.getLocks().isMovementLocked() || player.getLocks().isTeleportLocked()) {
			player.getDialogueInterpreter().sendDialogue("You can't use this perk right now.");
			return;
		}
		if (!cart && player.getEquipment().get(EquipmentContainer.SLOT_WEAPON) != null) {
			player.getDialogueInterpreter().sendDialogue("Your weapon slot needs to be open in order to use", "a gnome copter.");
			return;
		}
		if (cart) {
			player.getAppearance().rideCart(true);
		} else {
			player.getAppearance().flyCopter();
		}
	}

	/**
	 * Ends the locomotive session.
	 * @param player the player.
	 */
	private void endLocomotive(Player player) {
		player.getAppearance().rideCart(false);
		if (player.getEquipment().get(EquipmentContainer.SLOT_WEAPON) != null) {
			if (player.getEquipment().get(EquipmentContainer.SLOT_WEAPON).getId() == 12842) {
				player.animate(Animation.create(8957));
				player.getEquipment().replace(null, 3);
			}
		}
	}

}
