package plugin.quest.arravshield;

import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin handler used for the certificates related to shield of
 * arrav.
 * @author 'Vexia
 * @version 1.0
 */
public final class CertificatePlugin extends UseWithHandler {

	/**
	 * Represents the certificate item.
	 */
	private static final Item CERTIFICATE = new Item(769);

	/**
	 * Constructs a new {@code CertificatePlugin} {@code Object}.
	 */
	public CertificatePlugin() {
		super(11173);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(11174, ITEM_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		final Player player = event.getPlayer();
		if (player.getInventory().remove(new Item(event.getUsedItem().getId(), 1), new Item(event.getBaseItem().getId(), 1))) {
			player.getInventory().add(CERTIFICATE);
		}
		return true;
	}

}
