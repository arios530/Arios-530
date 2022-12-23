package org.arios.tools.npc;

import java.util.Arrays;

import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.CombatSpell;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.combat.equipment.WeaponInterface.AttackStyle;
import org.arios.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.ai.ArtificialSession;
import org.arios.game.node.entity.player.info.PlayerDetails;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;
import org.arios.game.node.entity.player.link.prayer.PrayerType;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;

/**
 * Tests an NPC's combat stats.
 * @author Emperor
 */
public final class TestStats {

	/*
	 * Best player magic attack: +139 Best player magic defence: +202
	 */
	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		// Cache.init(GameConstants.CACHE_PATH);
		// ParserSequence.prepare();
		// ParserSequence.parse();
		GameWorld.prompt(false);
		NPC npc = NPC.create(6222, Location.create(3222, 3217, 0));
		npc.init();
		//
		// npc.getSkills().setStaticLevel(Skills.ATTACK, 70);
		// npc.getSkills().setStaticLevel(Skills.STRENGTH, 70);
		// npc.getSkills().setStaticLevel(Skills.DEFENCE, 70);
		// npc.getSkills().setStaticLevel(Skills.RANGE, 70);
		// npc.getSkills().setStaticLevel(Skills.MAGIC, 70);
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_STAB] = 80;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_SLASH] = 90;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_CRUSH] = 85;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_MAGIC] = 178;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_RANGE] = 40;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_STAB + 5] =
		// 320;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_SLASH + 5] =
		// 428;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_CRUSH + 5] =
		// 453;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_MAGIC + 5] =
		// 300;
		// npc.getProperties().getBonuses()[WeaponInterface.BONUS_RANGE + 5] =
		// 300;
		npc.configure();
		System.out.println("NPC bonuses: ");
		System.out.println("Accuracy: " + Arrays.toString(Arrays.copyOf(npc.getProperties().getBonuses(), 5)));
		System.out.println("Defence: " + Arrays.toString(Arrays.copyOfRange(npc.getProperties().getBonuses(), 5, 10)));

		Player player = new Player(new PlayerDetails("roar", "roar", ArtificialSession.getSingleton()));
		player.init();
		player.getSkills().setStaticLevel(Skills.ATTACK, 99);
		player.getSkills().setStaticLevel(Skills.STRENGTH, 99);
		player.getSkills().setStaticLevel(Skills.DEFENCE, 99);
		player.getSkills().setStaticLevel(Skills.RANGE, 99);
		player.getSkills().setStaticLevel(Skills.MAGIC, 99);
		player.getSkills().setStaticLevel(Skills.PRAYER, 99);
		player.getPrayer().toggle(PrayerType.PIETY);
		setMaxedMeleeAcc(player);
		player.getEquipment().replace(new Item(5698), EquipmentContainer.SLOT_WEAPON);
		System.out.println("Player bonuses: ");
		System.out.println("Accuracy: " + Arrays.toString(Arrays.copyOf(player.getProperties().getBonuses(), 5)));
		System.out.println("Defence: " + Arrays.toString(Arrays.copyOfRange(player.getProperties().getBonuses(), 5, 10)));
		setAttackStyle(player, 1);
		player.getProperties().setSpell((CombatSpell) SpellBook.MODERN.getSpell(45));
		double accuracy = getAccuracy(CombatStyle.RANGE, player, npc);
		System.out.println("Hit chance: " + accuracy + "%.");
	}

	/**
	 * Sets the player's armour to maximize melee accuracy.
	 * @param player The player.
	 */
	public static void setMaxedMeleeAcc(Player player) {
		player.getEquipment().replace(new Item(6570), EquipmentContainer.SLOT_CAPE);
		player.getEquipment().replace(new Item(6585), EquipmentContainer.SLOT_AMULET);
		player.getEquipment().replace(new Item(11694), EquipmentContainer.SLOT_WEAPON);
		player.getEquipment().replace(new Item(7462), EquipmentContainer.SLOT_HANDS);
		player.getEquipment().replace(new Item(6735), EquipmentContainer.SLOT_RING);
	}

	/**
	 * Sets the player's armour to maximize melee strength.
	 * @param player The player.
	 */
	public static void setMaxedMeleeStr(Player player) {
		player.getEquipment().replace(new Item(10828), EquipmentContainer.SLOT_HAT);
		player.getEquipment().replace(new Item(6570), EquipmentContainer.SLOT_CAPE);
		player.getEquipment().replace(new Item(1725), EquipmentContainer.SLOT_AMULET);
		player.getEquipment().replace(new Item(11694), EquipmentContainer.SLOT_WEAPON);
		player.getEquipment().replace(null, EquipmentContainer.SLOT_SHIELD);
		player.getEquipment().replace(new Item(11724), EquipmentContainer.SLOT_CHEST);
		player.getEquipment().replace(new Item(11726), EquipmentContainer.SLOT_LEGS);
		player.getEquipment().replace(new Item(7462), EquipmentContainer.SLOT_HANDS);
		player.getEquipment().replace(new Item(6737), EquipmentContainer.SLOT_RING);
		player.getEquipment().replace(new Item(11732), EquipmentContainer.SLOT_FEET);
	}

	/**
	 * Sets the player's armour to maximize magic accuracy.
	 * @param player The player.
	 */
	public static void setMaxedMagicAcc(Player player) {
		player.getEquipment().replace(new Item(10342), EquipmentContainer.SLOT_HAT);
		player.getEquipment().replace(new Item(2414), EquipmentContainer.SLOT_CAPE);
		player.getEquipment().replace(new Item(10344), EquipmentContainer.SLOT_AMULET);
		player.getEquipment().replace(new Item(6914), EquipmentContainer.SLOT_WEAPON);
		player.getEquipment().replace(new Item(4712), EquipmentContainer.SLOT_CHEST);
		player.getEquipment().replace(new Item(6889), EquipmentContainer.SLOT_SHIELD);
		player.getEquipment().replace(new Item(4714), EquipmentContainer.SLOT_LEGS);
		player.getEquipment().replace(new Item(7462), EquipmentContainer.SLOT_HANDS);
		player.getEquipment().replace(new Item(6920), EquipmentContainer.SLOT_FEET);
		player.getEquipment().replace(new Item(6731), EquipmentContainer.SLOT_RING);
	}

	private static void setAttackStyle(Player player, int slot) {
		WeaponInterface inter = player.getExtension(WeaponInterface.class);
		if (inter == null) {
			return;
		}
		WeaponInterfaces current = inter.getWeaponInterface();
		AttackStyle style = current.getAttackStyles()[slot];
		player.getProperties().setAttackStyle(style);
		player.getSettings().toggleAttackStyleIndex(slot);
	}

	/**
	 * Gets the accuracy.
	 * @param style The combat style.
	 * @param attacker The attacker.
	 * @param victim The victim.
	 * @return The accuracy percentage.
	 */
	private static double getAccuracy(CombatStyle style, Entity attacker, Entity victim) {
		double attack = style.getSwingHandler().calculateAccuracy(attacker) * style.getSwingHandler().getSetMultiplier(attacker, Skills.ATTACK);
		double defence = style.getSwingHandler().calculateDefence(victim, attacker) * style.getSwingHandler().getSetMultiplier(victim, Skills.DEFENCE);
		double chance = 0.0;
		if (attack < defence) {
			chance = (attack - 1) / (defence * 2);
		} else {
			chance = 1 - ((defence + 1) / (attack * 2));
		}
		double ratio = chance * 100;
		return Math.floor(ratio);
	}
}