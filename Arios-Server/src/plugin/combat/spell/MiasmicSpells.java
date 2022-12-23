 package plugin.combat.spell;

import java.util.List;

import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.skill.free.magic.Runes;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.BattleState;
import org.arios.game.node.entity.combat.CombatSpell;
import org.arios.game.node.entity.combat.equipment.SpellType;
import org.arios.game.node.entity.impl.Animator.Priority;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.plugin.Plugin;

/**
 * Handles the Miasmic spells that are a part of the Ancient spellbook.
 * @author Splinter
 * @version 1.0
 */
public final class MiasmicSpells extends CombatSpell {
	
	/**
	 * The start graphic for Miasmic rush.
	 */
	private static final Graphics RUSH_START = new Graphics(1845, 0, 15);
	
	/**
	 * The end graphic for Miasmic rush.
	 */
	private static final Graphics RUSH_END = new Graphics(1847, 40);
	
	/**
	 * The start graphic for Miasmic rush.
	 */
	private static final Graphics BURST_START = new Graphics(1848, 0);
	
	/**
	 * The end graphic for Miasmic barrage.
	 */
	private static final Graphics BURST_END = new Graphics(1849, 20, 30);

	/**
	 * The start graphic for Miasmic rush.
	 */
	private static final Graphics BLITZ_START = new Graphics(1850, 15);

	/**
	 * The end graphic for Miasmic rush.
	 */
	private static final Graphics BLITZ_END = new Graphics(1851, 0);
	
	/**
	 * The start graphic for Miasmic rush.
	 */
	private static final Graphics BARRAGE_START = new Graphics(1853, 0);
	
	/**
	 * The end graphic for Miasmic barrage.
	 */
	private static final Graphics BARRAGE_END = new Graphics(1854, 0, 30);

	/**
	 * Constructs a new {@code MiasmicSpells} {@code Object}.
	 */
	public MiasmicSpells() {
	}

	/**
	 * Constructs a new {@code MiasmicSpells} {@Code Object}
	 * @param type The spell type.
	 * @param impactSound The impact sound id.
	 * @param anim The animation.
	 * @param start The start graphics.
	 * @param projectile The projectile.
	 * @param end The end graphics.
	 */
	private MiasmicSpells(SpellType type, int level, double baseExperience, int impactSound, Animation anim, Graphics start, Projectile projectile, Graphics end, Item... runes) {
		super(type, SpellBook.ANCIENT, level, baseExperience, 171, impactSound, anim, start, projectile, end, runes);
	}

	@Override
	public void visualize(Entity entity, Node target) {
		entity.graphics(graphic);
		if (projectile != null) {
			projectile.transform(entity, (Entity) target, false, 58, 10).send();
		}
		entity.animate(animation);
		sendAudio(entity, audio);
	}

	@Override
	public void visualizeImpact(Entity entity, Entity target, BattleState state) {
		super.visualizeImpact(entity, target, state);
	}

	@Override
	public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
		SpellBook.ANCIENT.register(16, new MiasmicSpells(SpellType.RUSH, 61, 36.0, 173, new Animation(10513, Priority.HIGH), RUSH_START, null, RUSH_END, Runes.DEATH_RUNE.getItem(2), Runes.CHAOS_RUNE.getItem(2), Runes.WATER_RUNE.getItem(2)));
		SpellBook.ANCIENT.register(18, new MiasmicSpells(SpellType.BURST, 73, 42.0, 170, new Animation(10516, Priority.HIGH), BURST_START, null, BURST_END, Runes.DEATH_RUNE.getItem(2), Runes.CHAOS_RUNE.getItem(4), Runes.WATER_RUNE.getItem(4)));
		SpellBook.ANCIENT.register(17, new MiasmicSpells(SpellType.BLITZ, 85, 48.0, 169, new Animation(10524, Priority.HIGH), BLITZ_START, null, BLITZ_END, Runes.BLOOD_RUNE.getItem(2), Runes.DEATH_RUNE.getItem(2), Runes.WATER_RUNE.getItem(3)));
		SpellBook.ANCIENT.register(19, new MiasmicSpells(SpellType.BARRAGE, 97, 54.0, 168, new Animation(10518, Priority.HIGH), BARRAGE_START, null, BARRAGE_END, Runes.BLOOD_RUNE.getItem(4), Runes.EARTH_RUNE.getItem(4), Runes.SOUL_RUNE.getItem(4)));
		return this;
	}

	@Override
	public void fireEffect(Entity entity, Entity victim, BattleState state) {
		//TODO: Add the reduced attack speed debuff on the targets affected
		//TODO: Change rune requirements above
	}
	
	private int[] validStaffIds = { 13867, 13869, 13841, 13843 };
	
	public boolean validStaffEquipped(Entity entity){
		for(int i = 0; i < validStaffIds.length; i++){
			if(((Player) entity).getEquipment().getNew(EquipmentContainer.SLOT_WEAPON).getId() == validStaffIds[i]){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean cast(Entity entity, Node target) {
		if (!validStaffEquipped(entity) && !GameWorld.getSettings().isDevMode()) {
		    ((Player) entity).getPacketDispatch().sendMessage("You need to be weilding Zuriel's staff in order to cast this spell.");
		    return false;
		}
		if (!meetsRequirements(entity, true, false)) {
			return false;
		}
		return super.cast(entity, target);
	}

	@Override
	public BattleState[] getTargets(Entity entity, Entity target) {
		if (animation.getId() == 10513 || animation.getId() == 10524
				|| !entity.getProperties().isMultiZone() || !target.getProperties().isMultiZone()) {
			return super.getTargets(entity, target);
		}
		List<Entity> list = getMultihitTargets(entity, target, 9);
		BattleState[] targets = new BattleState[list.size()];
		int index = 0;
		for (Entity e : list) {
			targets[index++] = new BattleState(entity, e);
		}
		return targets;
	}

	@Override
	public int getMaximumImpact(Entity entity, Entity victim, BattleState state) {
		int add = 9;
		if(animation.getId() == 10524 || animation.getId() == 10516){
			add = 6;
		}
		if(animation.getId() == 10513){
			add = 4;
		}
		return getType().getImpactAmount(entity, victim, add);
	}

}