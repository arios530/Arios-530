package plugin.interaction.inter;

import org.arios.game.component.CloseEvent;
import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;

/**
 * Represents the component plugin used for the game interface.
 * @author Vexia
 * 
 */
public final class GameInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(548, this);
		ComponentDefinition.put(750, this);
		ComponentDefinition.put(751, this);
		ComponentDefinition.put(740, this);
		ComponentDefinition.put(746, this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Component component, int opcode, int button, int slot, int itemId) {
		switch (component.getId()) {
		case 740:
			switch (button){
			case 3:
				player.getInterfaceManager().closeChatbox();
				break;
			}
			return true;
		case 548:
		case 746:
			if (button >= 38 && button<= 44F || button >= 20 && button <= 26) {
				player.getInterfaceManager().setCurrentTabIndex(getTabIndex(button));
			}
			//System.out.println("Button id is " + button);
			switch (button) {
				case 40:
					if (GameWorld.isEconomyWorld()) {
						player.getQuestRepository().update(player);
					} else {
						player.getSavedData().getSpawnData().drawStatsTab(player);
					}
					break;
				case 51:
					if (TutorialSession.getExtension(player).getStage() > TutorialSession.MAX_STAGE) {
						if (player.getExtension(WeaponInterface.class) == WeaponInterfaces.STAFF) {
							final Component c = new Component(WeaponInterfaces.STAFF.getInterfaceId());
							player.getInterfaceManager().openTab(0, c);
							final WeaponInterface inter = player.getExtension(WeaponInterface.class);
							inter.updateInterface();
						}
					}
					break;
				case 66:
				case 107:
				case 110:
					if (player.getInterfaceManager().isOpened() || player.getInterfaceManager().hasChatbox() || player.getInterfaceManager().getSingleTab() != null) {
						player.sendMessage("Please finish what you're currently doing before opening the World Map.");
						break;
					}
					//System.out.println("button id is " + button);
					player.getPacketDispatch().sendRunScript(3336, "");//3336
					player.getInterfaceManager().openWindowsPane(new Component(755), true);
					//int position = player.getLocation().getX() << 14 | player.getLocation().getY();// | player.getLocation().getZ() << 28;
					int position = player.getLocation().getX() << 14 | player.getLocation().getY() | player.getLocation().getZ() << 28;
					player.getPacketDispatch().sendScriptConfig(622, position);
					player.getPacketDispatch().sendScriptConfig(674, position);
					break;
			}
			return true;
		case 750:
			switch (opcode) {
			case 155:
				switch (button) {
				case 1:
					player.getSettings().toggleRun();
					break;
				}
				break;
			}
			return true;
		case 751:
			switch (opcode) {
			case 155:
				switch (button) {
				case 27:
					openReport(player);
					break;
				}
				break;
			}
			return true;
		}
		return true;
	}

	/**
	 * Method used to open the report interface.
	 * @param player the player.
	 */
	public static void openReport(final Player player) {
		player.getInterfaceManager().open(new Component(553)).setCloseEvent(new CloseEvent() {
			@Override
			public boolean close(Player player, Component c) {
				player.getPacketDispatch().sendRunScript(80, "");
				player.getPacketDispatch().sendRunScript(137, "");
				return true;
			}
		});
		player.getPacketDispatch().sendRunScript(508, "");
		if (player.getDetails().getRights() != Rights.REGULAR_PLAYER) {
			for (int i = 0; i < 18; i++) {
				player.getPacketDispatch().sendInterfaceConfig(553, i, false);
			}
		}
	}

	/**
	 * Gets the tab index.
	 * @param button The button id.
	 * @return The tab index.
	 */
	private static int getTabIndex(int button) {
		int tabIndex = button - 38;
		if (button < 27) {
			tabIndex = (button - 20) + 7;
		}
		return tabIndex;
	}
}