package org.arios.parser.misc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.arios.cache.ServerStore;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.equipment.Ammunition;
import org.arios.game.node.entity.combat.equipment.BoltEffect;
import org.arios.game.node.entity.combat.equipment.RangeWeapon;
import org.arios.game.node.entity.impl.Animator.Priority;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.parser.Parser;
import org.arios.parser.item.ItemConfiguration;

/**
 * Parses the range data.
 * @author Emperor
 */
public final class RangeDataParser implements Parser {

	@Override
	public boolean parse() throws Throwable {
		ByteBuffer buf = ServerStore.getArchive("range_data");
		int itemId;
		while ((itemId = buf.getShort()) != -1) {
			int slot = buf.get();
			int type = buf.get();
			int anim = buf.getShort();
			boolean drop = buf.get() == 1;
			int size = buf.get();
			List<Integer> ammunition = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				ammunition.add((Integer) (int) buf.getShort());
			}
			RangeWeapon weapon = new RangeWeapon(itemId, new Animation(anim, Priority.HIGH), ItemDefinition.forId(itemId).getConfiguration(ItemConfiguration.ATTACK_SPEED, 4), slot, type, drop, ammunition);
			RangeWeapon.getRangeWeapons().put(itemId, weapon);
		}
		while ((itemId = buf.getShort()) != -1) {
			Graphics start = new Graphics(buf.getShort(), buf.get());
			Graphics darkBow = new Graphics(buf.getShort(), buf.get());
			if (darkBow.getId() == -1) {
				darkBow = null;
			}
			Projectile p = Projectile.create((Entity) null, null, buf.getShort(), buf.get(), buf.get(), buf.get(), buf.get(), buf.get(), buf.get());
			int poisonDamage = buf.get();
			Ammunition ammo = new Ammunition(itemId, start, darkBow, p, poisonDamage);
			BoltEffect effect = BoltEffect.forId(itemId);
			if (effect != null) {
				ammo.setEffect(effect);
			}
			Ammunition.getAmmunition().put(itemId, ammo);
		}
		return true;
	}

}