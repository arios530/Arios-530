package plugin.activity.barbassault;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.activity.ActivityPlugin;
import org.arios.game.content.global.action.ClimbActionHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManager;

/**
 * Represents the Barbarian Assault minigame.
 * <https://www.youtube.com/watch?v=yggDff2ZKEA>
 * <http://2007.runescape.wikia.com/wiki/Barbarian_Assault>
 * @author Splinter
 */
public class BarbarianAssaultActivityPlugin extends ActivityPlugin {

	/**
	 * Constructs a new {@code BarbarianAssaultActivityPlugin} {@code Object}
	 */
	public BarbarianAssaultActivityPlugin() {
		super("Barbarian Assault", false, true, true);
	}

	@Override
	public ActivityPlugin newInstance(Player p) throws Throwable {
		return new BarbarianAssaultActivityPlugin();
	}

	@Override
	public Location getSpawnLocation() {
		return null;
	}

	@Override
	public void configure() {
		PluginManager.definePlugin(new OptionHandler() {
			@Override
			public Plugin<Object> newInstance(Object arg) throws Throwable {
				ObjectDefinition.forId(20226).getConfigurations().put("option:climb-down", this);
				ObjectDefinition.forId(20227).getConfigurations().put("option:climb-up", this);
				for (int i = 20199; i < 20208; i++) {
					ObjectDefinition.forId(i).getConfigurations().put("option:pass", this);
				}
				return this;
			}

			@Override
			public boolean handle(final Player player, Node node, String option) {
				GameObject object = (GameObject) node;
				switch (object.getId()) {
				case 20226:
					ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, new Location(2593, 5261));
					return true;
				case 20227:
					ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, new Location(2534, 3573));
					return true;
				case 20199:
				case 20200:
				case 20201:
				case 20202:
				case 20203:
				case 20204:
				case 20205:
				case 20206:
				case 20207:
				case 20208:
					return true;
				}
				return false;
			}
		});
	}
}
