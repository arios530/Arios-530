package org.arios.tools.item;

import java.nio.ByteBuffer;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.ServerStore;
import org.arios.game.node.entity.combat.equipment.Ammunition;
import org.arios.game.node.entity.combat.equipment.RangeWeapon;
import org.arios.game.world.GameWorld;

/**
 * Converts the range data from XML format to the arios format (.config498).
 * @author Emperor
 */
public final class RangeDataConverter {

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		Ammunition.initialize();
		RangeWeapon.initialize();
		ByteBuffer buffer = ByteBuffer.allocate(666666);
		for (int id = 0; id < Cache.getItemDefinitionsSize(); id++) {
			RangeWeapon weapon = RangeWeapon.get(id);
			if (weapon != null) {
				buffer.putShort((short) id).put((byte) weapon.getAmmunitionSlot()).put((byte) weapon.getType()).putShort((short) weapon.getAnimation().getId()).put((byte) (weapon.isDropAmmo() ? 1 : 0)).put((byte) weapon.getAmmunition().size());
				for (int ammo : weapon.getAmmunition()) {
					buffer.putShort((short) ammo);
				}
			}
		}
		buffer.putShort((short) -1);
		for (int id = 0; id < Cache.getItemDefinitionsSize(); id++) {
			Ammunition ammo = Ammunition.get(id);
			if (ammo != null) {
				buffer.putShort((short) id).putShort((short) ammo.getStartGraphics().getId()).put((byte) ammo.getStartGraphics().getHeight()).putShort((short) (ammo.getDarkBowGraphics() == null ? -1 : ammo.getDarkBowGraphics().getId())).put((byte) (ammo.getDarkBowGraphics() == null ? 0 : ammo.getDarkBowGraphics().getHeight())).putShort((short) ammo.getProjectile().getProjectileId()).put((byte) ammo.getProjectile().getStartHeight()).put((byte) ammo.getProjectile().getEndHeight()).put((byte) ammo.getProjectile().getStartDelay()).put((byte) ammo.getProjectile().getSpeed()).put((byte) ammo.getProjectile().getAngle()).put((byte) ammo.getProjectile().getDistance()).put((byte) ammo.getPoisonDamage());

			}
		}
		buffer.putShort((short) -1);
		buffer.flip();
		ServerStore.setArchive("range_data", buffer, false);
		ServerStore.createStaticStore(ServerConstants.STORE_PATH);
		System.exit(1);
	}
}