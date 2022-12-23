package plugin.npc.familiar;

import java.util.ArrayList;
import java.util.List;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.skill.SkillBonus;
import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.free.gather.SkillingResource;
import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.content.skill.member.summoning.familiar.FamiliarSpecial;
import org.arios.game.content.skill.member.summoning.familiar.Forager;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManager;

/**
 * Represents the Desert Wyrm familiar.
 * @author Vexia
 * @author Aero
 */
public final class DesertWyrmNPC extends Forager {

	/**
	 * Constructs a new {@code DesertWyrmNPC} {@code Object}.
	 */
	public DesertWyrmNPC() {
		this(null, 6831);
	}

	/**
	 * Constructs a new {@code DesertWyrmNPC} {@code Object}.
	 * @param owner The owner.
	 * @param id The id.
	 */
	public DesertWyrmNPC(Player owner, int id) {
		super(owner, id, 1900, 12049, 6, WeaponInterface.STYLE_AGGRESSIVE);
		boosts.add(new SkillBonus(Skills.MINING, 1));
	}

	@Override
	public Familiar construct(Player owner, int id) {
		return new DesertWyrmNPC(owner, id);
	}

	@Override
	protected boolean specialMove(FamiliarSpecial special) {
		final Entity target = (Entity) special.getNode();
		if (!canCombatSpecial(target)) {
			return false;
		}
		faceTemporary((Entity) special.getNode(), 2);
		visualize(new Animation(7795), new Graphics(1410));
		Projectile.magic(this, target, 1411, 40, 36, 51, 10).send();
		sendFamiliarHit(target, 5);
		return true;
	}

	@Override
	public void configureFamiliar() {
		PluginManager.definePlugin(new OptionHandler() {
			@Override
			public Plugin<Object> newInstance(Object arg) throws Throwable {
				for (int i : getIds()) {
					NPCDefinition.forId(i).getConfigurations().put("option:burrow", this);
				}
				return this;
			}

			@Override
			public boolean handle(final Player player, final Node node, String option) {
				final GameObject rock = getClosestRock(player);
				if (!player.getFamiliarManager().isOwner((Familiar) node)) {
					return true;
				}
				if (((NPC) node).getLocks().isMovementLocked()) {
					return true;
				}
				if (rock == null) {
					player.getPacketDispatch().sendMessage("There are no rocks around here for the desert wyrm to mine from!");
					return true;
				}
				final SkillingResource resource = SkillingResource.forId(rock.getId());
				if (resource == null) {
					player.getPacketDispatch().sendMessage("There are no rocks around here for the desert wyrm to mine from!");
					return true;
				}
				final Familiar familiar = (Familiar) node;
				player.lock(9);
				familiar.lock(8);
				familiar.visualize(new Animation(7800), new Graphics(1412));
				GameWorld.submit(new Pulse(1, player, familiar) {
					int counter;

					@Override
					public boolean pulse() {
						switch (++counter) {
						case 4:
							familiar.setHidden(true);
							break;
						case 8:
							familiar.call();
							GroundItemManager.create(new Item(resource.getReward()), familiar.getLocation(), player);
							return true;
						}
						return false;
					}
				});
				return true;
			}

			/**
			 * Gets the closest combat rock.
			 * @return the object.
			 */
			public GameObject getClosestRock(Player player) {
				List<GameObject> rocks = new ArrayList<>();
				for (int k = 0; k < 7; k++) {
					for (int i = 0; i < 4; i++) {
						Direction dir = Direction.get(i);
						Location loc = player.getLocation().transform(dir.getStepX() * k, dir.getStepY() * k, 0);
						GameObject object = RegionManager.getObject(loc);
						if (object != null && object.getName().equals("Rocks")) {
							rocks.add(object);
						}
					}
				}
				int ordinal = 0;
				GameObject o = null;
				for (GameObject r : rocks) {
					SkillingResource resource = SkillingResource.forId(r.getId());
					if (resource != null && resource.ordinal() > ordinal) {
						ordinal = resource.ordinal();
						o = r;
					}
				}
				return o;
			}

		});
	}

	@Override
	public int[] getIds() {
		return new int[] { 6831, 6832 };
	}

}