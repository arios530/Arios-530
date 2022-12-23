package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.system.mysql.impl.ShopSQLHandler;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.plugin.Plugin;

/**
 * Handles the culinomancer chest.
 * @author Vexia
 */
public final class CulinomancerChestPliugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(12309).getConfigurations().put("option:bank", this);
		ObjectDefinition.forId(12309).getConfigurations().put("option:buy-items", this);
		ObjectDefinition.forId(12309).getConfigurations().put("option:buy-food", this);
		GameObject object = RegionManager.getObject(new Location(3219, 9623, 0));
		ObjectBuilder.replace(object, object.transform(object.getId(), 5));
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (!option.equals("bank") && !GameWorld.isEconomyWorld()) {
			player.sendMessage("This is disabled on the spawn world.");
			return true;
		}
		switch (option) {
		case "bank":
			player.getBank().open();
			return true;
		case "buy-items":
			ShopSQLHandler.openUid(player, 178);
			return true;
		case "buy-food":
			ShopSQLHandler.openUid(player, 177);
			return true;
		}
		return true;
	}

}
