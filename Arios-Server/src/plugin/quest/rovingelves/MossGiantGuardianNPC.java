package plugin.quest.rovingelves;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.RovingElves;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManager;

/**
 * The level 84 Moss Giant in Glarial's tomb.
 * @author Splinter
 */
public final class MossGiantGuardianNPC extends AbstractNPC {

	/**
	 * Constructs a new {@code MossGiantGuardianNPC} {@code Object}.
	 */
	public MossGiantGuardianNPC() {
		super(0, null);
	}

	/**
	 * Constructs a new {@code MossGiantGuardianNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public MossGiantGuardianNPC(int id, Location location) {
		super(id, location);
		this.setAggressive(true);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new MossGiantGuardianNPC(id, location);
	}

	@Override
	public void finalizeDeath(Entity killer) {
		super.finalizeDeath(killer);
		if (killer instanceof Player) {
			final Player player = (Player) killer;
			final Quest quest = player.getQuestRepository().getQuest(RovingElves.NAME);
			if (quest.getStage() == 15 && !player.getInventory().contains(RovingElves.CONSECRATION_SEED.getId(), 1)) {
				player.getPacketDispatch().sendMessages("A small grey seed drops on the ground.");
				GroundItemManager.create(new Item(RovingElves.CONSECRATION_SEED.getId()), getLocation(), player);
			}
		}
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		PluginManager.definePlugin(new OptionHandler() {

			@Override
			public Plugin<Object> newInstance(Object arg) throws Throwable {
				NPCDefinition.forId(getIds()[0]).getConfigurations().put("option:attack", this);
				return this;
			}

			@Override
			public boolean handle(Player player, Node node, String option) {
				((NPC) node).attack(player);
				return true;
			}

		});
		return super.newInstance(arg);
	}

	@Override
	public int[] getIds() {
		return new int[] { 1681 };
	}

}
