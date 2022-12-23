package org.arios.parser.npc;

import java.nio.ByteBuffer;
import java.util.Map;

import org.arios.cache.Cache;
import org.arios.cache.ServerStore;
import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.content.global.ttrail.ClueLevel;
import org.arios.game.content.skill.member.slayer.Tasks;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.parser.Parser;

/**
 * Handles the NPC configuration parsing.
 * @author Emperor
 */
public final class NPCConfiguration implements Parser {

	/**
	 * The weakness attribute.
	 */
	public static final String WEAKNESS = "weakness";

	/**
	 * The lifepoints attribute.
	 */
	public static final String LIFEPOINTS = "lifepoints";

	/**
	 * The attack level attribute.
	 */
	public static final String ATTACK_LEVEL = "attack_level";

	/**
	 * The strength level attribute.
	 */
	public static final String STRENGTH_LEVEL = "strength_level";

	/**
	 * The defence level attribute.
	 */
	public static final String DEFENCE_LEVEL = "defence_level";

	/**
	 * The range level attribute.
	 */
	public static final String RANGE_LEVEL = "range_level";

	/**
	 * The range level attribute.
	 */
	public static final String MAGIC_LEVEL = "magic_level";

	/**
	 * The examine attribute.
	 */
	public static final String EXAMINE = "examine";

	/**
	 * The slayer task attribute.
	 */
	public static final String SLAYER_TASK = "slayer_task";

	/**
	 * The poisonous attribute.
	 */
	public static final String POISONOUS = "poisonous";

	/**
	 * The aggressive attribute.
	 */
	public static final String AGGRESSIVE = "aggressive";

	/**
	 * The respawn delay attribute.
	 */
	public static final String RESPAWN_DELAY = "respawn";

	/**
	 * The attack speed attribute.
	 */
	public static final String ATTACK_SPEED = "attack_speed";

	/**
	 * The poison immune attribute.
	 */
	public static final String POISON_IMMUNE = "poison_immune";

	/**
	 * The bonuses attribute.
	 */
	public static final String BONUSES = "bonuses";

	/**
	 * The start graphic attribute.
	 */
	public static final String START_GRAPHIC = "start_gfx";

	/**
	 * The projectile attribute.
	 */
	public static final String PROJECTILE = "projectile";

	/**
	 * The end graphic attribute.
	 */
	public static final String END_GRAPHIC = "end_gfx";

	/**
	 * The combat style attribute.
	 */
	public static final String COMBAT_STYLE = "combat_style";

	/**
	 * The aggressive radius attribute.
	 */
	public static final String AGGRESSIVE_RADIUS = "agg_radius";

	/**
	 * The slayer experience attribute.
	 */
	public static final String SLAYER_EXP = "slayer_exp";

	/**
	 * The amount to poison.
	 */
	public static final String POISON_AMOUNT = "poison_amount";

	/**
	 * The movement radius.
	 */
	public static final String MOVEMENT_RADIUS = "movement_radius";

	/**
	 * The spawn animation.
	 */
	public static final String SPAWN_ANIMATION = "spawn_animation";

	/**
	 * The start height.
	 */
	public static final String START_HEIGHT = "start_height";

	/**
	 * The projectile height.
	 */
	public static final String PROJECTILE_HEIGHT = "prj_height";

	/**
	 * The end height.
	 */
	public static final String END_HEIGHT = "end_height";

	/**
	 * The clue level.
	 */
	public static final String CLUE_LEVEL = "clue_level";

	/**
	 * The spell id.
	 */
	private static final String SPELL_ID = "spell_id";

	/**
	 * The combat audio.
	 */
	public static final String COMBAT_AUDIO = "combat_audio";

	/**
	 * Constructs a new {@code NPCConfiguration} {@Code Object}.
	 */
	public NPCConfiguration() {
		/*
		 * empty.
		 */
	}

	/**
	 * Represents a weakness of an NPC.
	 * @author Emperor
	 */
	public static enum Weakness {
		STAB, SLASH, CRUSH, ARROW, BOLT, THROWN, AIR, WATER, EARTH, FIRE, NONE;
	}

	@Override
	public boolean parse() throws Throwable {
		ByteBuffer buf = ServerStore.getArchive("npc_config");
		int opcode;
		NPCDefinition def;
		Map<String, Object> configs = null;
		for (int id = 0; id < Cache.getNPCDefinitionsSize(); id++) {
			def = NPCDefinition.forId(id);
			configs = def.getConfigurations();
			int[] bonus = new int[15];
			int bonusCounter = 0;
			if (!buf.hasRemaining()) {
				System.err.println("No configurations starting from NPC " + id + "!");
				break;
			}
			while ((opcode = buf.get() & 0xFF) != 0) {
				switch (opcode) {
				case 1:
					configs.put(LIFEPOINTS, (int) buf.getShort());
					break;
				case 2:
					configs.put(ATTACK_LEVEL, (int) buf.getShort());
					break;
				case 3:
					configs.put(STRENGTH_LEVEL, (int) buf.getShort());
					break;
				case 4:
					configs.put(DEFENCE_LEVEL, (int) buf.getShort());
					break;
				case 5:
					configs.put(RANGE_LEVEL, (int) buf.getShort());
					break;
				case 6:
					configs.put(MAGIC_LEVEL, (int) buf.getShort());
					break;
				case 7:
					configs.put(EXAMINE, ByteBufferUtils.getString(buf));
					break;
				case 8:
					configs.put(POISON_AMOUNT, (int) buf.get());
					break;
				case 9:
					configs.put(POISON_IMMUNE, true);
					break;
				case 10:
					configs.put(RESPAWN_DELAY, (int) (int) buf.get());
					break;
				case 11:
					configs.put(ATTACK_SPEED, (int) (int) buf.get());
					break;
				case 12:
					configs.put(MOVEMENT_RADIUS, (int) buf.get());
					break;
				case 13:
					configs.put(AGGRESSIVE_RADIUS, (int) buf.get());
					break;
				case 14:
					def.getCombatAnimations()[0] = new Animation((int) buf.getShort());
					break;
				case 15:
					def.getCombatAnimations()[3] = new Animation((int) buf.getShort());
					break;
				case 16:
					def.getCombatAnimations()[4] = new Animation((int) buf.getShort());
					break;
				case 17:
					configs.put(SPAWN_ANIMATION, (int) buf.getShort());
					break;
				case 18:
					configs.put(START_GRAPHIC, (int) buf.getShort());
					break;
				case 19:
					configs.put(PROJECTILE, (int) buf.getShort());
					break;
				case 20:
					configs.put(END_GRAPHIC, (int) buf.getShort());
					break;
				case 21:
					configs.put(WEAKNESS, Weakness.values()[buf.get()]);
					break;
				case 22:
					configs.put(SLAYER_TASK, Tasks.values()[buf.get()]);
					break;
				case 23:
					configs.put(SLAYER_EXP, buf.getDouble());
					break;
				case 24:
					configs.put(COMBAT_STYLE, CombatStyle.values()[buf.get()]);
					break;
				case 25:
				case 26:
				case 27:
				case 28:
				case 29:
				case 30:
				case 31:
				case 32:
				case 33:
				case 34:
				case 35:
				case 36:
				case 37:
				case 38:
				case 39:
					bonus[bonusCounter] = (int) buf.getShort();
					bonusCounter++;
					break;
				case 40:
					configs.put(POISONOUS, true);
					break;
				case 41:
					configs.put(AGGRESSIVE, true);
					break;
				case 42:
					configs.put(START_HEIGHT, (int) buf.get());
					break;
				case 43:
					// magic anim.
					def.getCombatAnimations()[1] = new Animation((int) buf.getShort());
					break;
				case 44:
					// range anim.
					def.getCombatAnimations()[2] = new Animation((int) buf.getShort());
					break;
				case 45:
					configs.put(PROJECTILE_HEIGHT, (int) buf.get());
					break;
				case 46:
					configs.put(END_HEIGHT, (int) buf.get());
					break;
				case 47:
					configs.put(CLUE_LEVEL, ClueLevel.values()[buf.get() - 1]);
					break;
				case 48:
					configs.put(SPELL_ID, (int) buf.getShort());
					break;
				case 49:
					int size = buf.get();
					Audio[] audios = new Audio[size];
					for (int i = 0; i < size; i++) {
						audios[i] = new Audio(buf.getShort());
					}
					configs.put(COMBAT_AUDIO, audios);
					break;
				default:
					System.out.println("Unhandled NPC configuration opcode [op=" + opcode + "]!");
					break;
				}
			}
			if (bonusCounter > 0) {
				configs.put(BONUSES, bonus);
			}
		}
		return true;
	}

}