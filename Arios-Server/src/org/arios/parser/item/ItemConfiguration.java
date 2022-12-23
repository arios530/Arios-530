package org.arios.parser.item;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.arios.cache.Cache;
import org.arios.cache.ServerStore;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.impl.Animator.Priority;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.parser.Parser;

/**
 * Handles the item configurations.
 * @author Emperor
 */
public class ItemConfiguration implements Parser {

	/**
	 * The tradeable item configuration key.
	 */
	public static final String TRADEABLE = "tradeable";

	/**
	 * The lendable item configuration key.
	 */
	public static final String LENDABLE = "lendable";

	/**
	 * The destroy item configuration key.
	 */
	public static final String DESTROY = "destroy";

	/**
	 * The two-handed item configuration key.
	 */
	public static final String TWO_HANDED = "two_handed";

	/**
	 * The high-alchemy price item configuration key.
	 */
	public static final String HIGH_ALCHEMY = "high_alchemy";

	/**
	 * The low-alchemy price item configuration key.
	 */
	public static final String LOW_ALCHEMY = "low_alchemy";

	/**
	 * The shop price item configuration key.
	 */
	public static final String SHOP_PRICE = "shop_price";

	/**
	 * The grand exchange price item configuration key.
	 */
	public static final String GE_PRICE = "grand_exchange_price";

	/**
	 * The examine item configuration key.
	 */
	public static final String EXAMINE = "examine";

	/**
	 * The weight item configuration key.
	 */
	public static final String WEIGHT = "weight";

	/**
	 * The bonuses item configuration key.
	 */
	public static final String BONUS = "bonuses";

	/**
	 * The absorb item configuration key.
	 */
	public static final String ABSORB = "absorb";

	/**
	 * The equipment slot item configuration key.
	 */
	public static final String EQUIP_SLOT = "equipment_slot";

	/**
	 * The attack speed item configuration key.
	 */
	public static final String ATTACK_SPEED = "attack_speed";

	/**
	 * The remove hair item configuration key.
	 */
	public static final String REMOVE_HEAD = "remove_head";

	/**
	 * The remove beard item configuration key.
	 */
	public static final String REMOVE_BEARD = "remove_beard";

	/**
	 * The remove sleeves item configuration key.
	 */
	public static final String REMOVE_SLEEVES = "remove_sleeves";

	/**
	 * The stand anim item configuration key.
	 */
	public static final String STAND_ANIM = "stand_anim";

	/**
	 * The stand-run anim item configuration key.
	 */
	public static final String STAND_TURN_ANIM = "stand-turn_anim";

	/**
	 * The walk anim item configuration key.
	 */
	public static final String WALK_ANIM = "walk_anim";

	/**
	 * The run anim item configuration key.
	 */
	public static final String RUN_ANIM = "run_anim";

	/**
	 * The turn 180 anim item configuration key.
	 */
	public static final String TURN180_ANIM = "turn180_anim";

	/**
	 * The turn 90cw anim item configuration key.
	 */
	public static final String TURN90CW_ANIM = "turn90cw_anim";

	/**
	 * The turn 90ccw anim item configuration key.
	 */
	public static final String TURN90CCW_ANIM = "turn90ccw_anim";

	/**
	 * The weapon interface.
	 */
	public static final String WEAPON_INTERFACE = "weapon_interface";

	/**
	 * If the item has a special attack bar.
	 */
	public static final String HAS_SPECIAL = "has_special";

	/**
	 * The item's attack animations.
	 */
	public static final String ATTACK_ANIMS = "attack_anims";

	/**
	 * The items destroy message.
	 */
	public static final String DESTROY_MESSAGE = "destroy_message";

	/**
	 * The requirements.
	 */
	public static final String REQUIREMENTS = "requirements";

	/**
	 * The grand exchange buying limit attribute key.
	 */
	public static final String GE_LIMIT = "ge_buy_limit";

	/**
	 * The defence animation key.
	 */
	public static final String DEFENCE_ANIMATION = "defence_anim";

	/**
	 * The attack sound key.
	 */
	public static final String ATTACK_AUDIO = "attack_audios";

	/**
	 * The point price.
	 */
	public static final String POINT_PRICE = "point_price";

	/**
	 * If the item is spawnable.
	 */
	public static final String SPAWNABLE = "spawnable";

	/**
	 * If the item is bankable.
	 */
	public static final String BANKABLE = "bankable";

	/**
	 * If the item is a rare item.
	 */
	public static final String RARE_ITEM = "rare_item";

	/**
	 * The tokkul price of an item.
	 */
	public static final String TOKKUL_PRICE = "tokkul_price";

	/**
	 * The render animation id of an item.
	 */
	public static final String RENDER_ANIM_ID = "render_anim";

	/**
	 * Constructs a new {@code ItemConfiguration} {@code Object}.
	 */
	public ItemConfiguration() {
		/*
		 * empty.
		 */
	}

	@Override
	public boolean parse() throws Throwable {
		ByteBuffer buf = ServerStore.getArchive("item_config");
		int opcode;
		ItemDefinition def = null;
		int size;
		Map<String, Object> configs;
		for (int itemId = 0; itemId < Cache.getItemDefinitionsSize(); itemId++) {
			if (!buf.hasRemaining()) {
				System.err.println("No item configurations from item " + itemId + "!");
				break;
			}
			def = ItemDefinition.forId(itemId);
			configs = def.getConfigurations();
			while ((opcode = buf.get() & 0xFF) != 0) {
				switch (opcode) {
				case 1:
					configs.put(TRADEABLE, true);
					break;
				case 2:
					configs.put(LENDABLE, true);
					break;
				case 3:
					configs.put(HIGH_ALCHEMY, buf.getInt());
					break;
				case 4:
					configs.put(LOW_ALCHEMY, buf.getInt());
					break;
				case 5:
					configs.put(DESTROY, true);
					break;
				case 6:
					configs.put(SHOP_PRICE, buf.getInt());
					break;
				case 7:
					configs.put(GE_PRICE, buf.getInt());
					break;
				case 8:
					String s = ByteBufferUtils.getString(buf);
					while (s.length() > 0 && s.charAt(0) == ' ') {
						s = s.substring(1, s.length());
					}
					if (s.length() > 255) {
						s = s.substring(0, 255);
					}
					def.getConfigurations().put(EXAMINE, s);
					def.setExamine(s);
					break;
				case 9:
					configs.put(WEIGHT, (double) (buf.getShort() / 1000D));
					break;
				case 10:// Bonuses
					int[] bonuses = new int[15];
					for (int i = 0; i < bonuses.length; i++) {
						bonuses[i] = buf.getShort();
					}
					def.getConfigurations().put(BONUS, bonuses);
					break;
				case 11:// Absorb.
					int[] absorb = new int[3];
					for (int i = 0; i < absorb.length; i++) {
						absorb[i] = buf.getShort();
					}
					def.getConfigurations().put(ABSORB, absorb);
					break;
				case 12:// Two handed.
					def.getConfigurations().put(TWO_HANDED, true);
					break;
				case 13:// Equipment slot.
					def.getConfigurations().put(EQUIP_SLOT, (int) buf.get());
					break;
				case 14:// Attack speed.
					def.getConfigurations().put(ATTACK_SPEED, (int) buf.get());
					break;
				case 15:
					def.getConfigurations().put(REMOVE_HEAD, true);
					break;
				case 16:
					def.getConfigurations().put(REMOVE_BEARD, true);
					break;
				case 17:
					def.getConfigurations().put(REMOVE_SLEEVES, true);
					break;
				case 18:
					configs.put(STAND_ANIM, buf.getInt());
					break;
				case 19:
					configs.put(STAND_TURN_ANIM, buf.getInt());
					break;
				case 20:
					configs.put(WALK_ANIM, buf.getInt());
					break;
				case 21:
					configs.put(RUN_ANIM, buf.getInt());
					break;
				case 22:
					configs.put(TURN180_ANIM, buf.getInt());
					break;
				case 23:
					configs.put(TURN90CW_ANIM, buf.getInt());
					break;
				case 24:
					configs.put(TURN90CCW_ANIM, buf.getInt());
					break;
				case 25:
					configs.put(WEAPON_INTERFACE, (int) buf.get());
					break;
				case 26:
					configs.put(HAS_SPECIAL, true);
					break;
				case 27:
					size = buf.get();
					Animation[] animation = new Animation[size];
					for (int i = 0; i < size; i++) {
						animation[i] = new Animation(buf.getShort() & 0xFFFF, Priority.HIGH);
					}
					def.getConfigurations().put(ATTACK_ANIMS, animation);
					break;
				case 28:
					configs.put(DESTROY_MESSAGE, ByteBufferUtils.getString(buf));
					break;
				case 29:
					size = buf.get() & 0xFF;
					Map<Integer, Integer> requirements = new HashMap<>();
					for (int i = 0; i < size; i++) {
						requirements.put(buf.get() & 0xFF, buf.get() & 0xFF);
					}
					def.getConfigurations().put(REQUIREMENTS, requirements);
					break;
				case 30:
					configs.put(GE_LIMIT, buf.getInt());
					break;
				case 31:
					def.getConfigurations().put(DEFENCE_ANIMATION, Animation.create(buf.getInt()));
					break;
				case 33:
					size = buf.get();
					Audio[] audios = new Audio[size];
					for (int i = 0; i < size; i++) {
						audios[i] = new Audio(buf.getShort());
					}
					configs.put(ATTACK_AUDIO, audios);
					break;
				case 34:
					configs.put(POINT_PRICE, buf.getInt());
					break;
				case 35:
					configs.put(SPAWNABLE, false);
					break;
				case 36:
					configs.put("trade-override", true);
					break;
				case 37:
					configs.put(BANKABLE, false);
					break;
				case 38:
					configs.put(RARE_ITEM, true);
					break;
				case 39:
					configs.put(TOKKUL_PRICE, buf.getInt());
					break;
				case 40:
					configs.put("fun_weapon", true);
					break;
				case 41:
					configs.put(RENDER_ANIM_ID, buf.getShort() & 0xFFFF);
					break;
				}
			}
		}
		return true;
	}
}