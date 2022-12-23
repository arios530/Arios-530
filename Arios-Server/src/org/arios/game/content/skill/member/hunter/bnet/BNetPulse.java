package org.arios.game.content.skill.member.hunter.bnet;

import java.util.Random;

import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.skill.SkillPulse;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.combat.DeathTask;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.tools.RandomFunction;
import org.arios.tools.StringUtils;

/**
 * Handles the butterfly net catch pulse.
 * @author Vexia
 */
public final class BNetPulse extends SkillPulse<NPC> {

	/**
	 * The swinging animation.
	 */
	private static final Animation ANIMATION = new Animation(6999);

	/**
	 * The net node.
	 */
	private final BNetNode type;

	/**
	 * If we are successfull.
	 */
	private boolean success;

	/**
	 * The ticks passed.
	 */
	private int ticks;

	/**
	 * Constructs a new {@code BNetPulse} {@code Object}.
	 * @param player the player.
	 * @param node the node.
	 * @param type the type.
	 */
	public BNetPulse(Player player, NPC node, BNetNode type) {
		super(player, node);
		this.type = type;
		this.resetAnimation = false;
	}

	@Override
	public boolean checkRequirements() {
		if (player.getHunterManager().getStaticLevel() < type.getLevel()) {
			player.sendMessage("You need a Hunter level of at least " + type.getLevel() + " in order to do that.");
			return false;
		}
		if (!type.isBareHand(player)) {
			if (type.hasWeapon(player)) {
				player.getPacketDispatch().sendMessage("Your hands need to be free.");
				return false;
			} else if (!type.hasNet(player)) {
				player.sendMessage("You need to be wielding a butterfly net to catch " + (type instanceof ImplingNode ? "implings" : "butterflies") + ".");
				return false;
			} else if (!type.hasJar(player)) {
				player.getPacketDispatch().sendMessage("You need to have a" + (StringUtils.isPlusN(type.getJar().getName()) ? "n" : "") + " " + type.getJar().getName().toLowerCase() + ".");
				return false;
			}
		}
		if (node.isHidden() || DeathTask.isDead(node)) {
			return false;
		}
		return true;
	}

	@Override
	public void animate() {
		if (ticks < 1) {
			player.animate(ANIMATION);
		}
	}

	@Override
	public boolean reward() {
		if (node.isHidden() || DeathTask.isDead(node)) {
			return true;
		}
		if (++ticks % 2 != 0) {
			return false;
		}
		if (node.getAttribute("dead", 0) > GameWorld.getTicks()) {
			player.sendMessage("Ooops! It's gone.");
			return true;
		}
		if ((success = isSuccessful())) {
			node.finalizeDeath(player);
			type.reward(player, node);
			node.setAttribute("dead", GameWorld.getTicks() + 10);
		} else {
			node.moveStep();
		}
		return true;
	}

	@Override
	public void message(int type) {
		if (type == 0) {
			node.setAttribute("looting", GameWorld.getTicks() + (ANIMATION.getDuration() + 1));
			player.lock(ANIMATION.getDuration());
		}
		this.type.message(player, type, success);
	}

	/**
	 * Checks if the player has succesfully caught the impling.
	 * @return {@code True} if succesful, {@code false} if not.
	 */
	private boolean isSuccessful() {
		int huntingLevel = player.getSkills().getLevel(Skills.HUNTER);
		int level = type.getLevel();
		if (type.hasNet(player)) {
			Item net = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
			if (net != null && net.getId() == 11259) {
				huntingLevel += 5;
			}
		} else {
			huntingLevel *= 0.5;
		}
		int currentLevel = RandomFunction.random(huntingLevel) + 1;
		double ratio = currentLevel / (new Random().nextInt(level + 5) + 1);
		return Math.round(ratio * huntingLevel) >= level;
	}

}
