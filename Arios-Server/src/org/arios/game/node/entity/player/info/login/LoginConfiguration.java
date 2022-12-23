package org.arios.game.node.entity.player.info.login;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.arios.game.component.Component;
import org.arios.game.content.activity.ActivityManager;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.SystemManager;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.repository.Repository;
import org.arios.game.world.update.UpdateSequence;
import org.arios.game.world.update.flag.player.AppearanceFlag;
import org.arios.plugin.Plugin;
import plugin.interaction.player.AriosSettings;

/**
 * Sends the login configuration packets.
 * @author Emperor
 * 
 */
public final class LoginConfiguration {
	
	/**
	 * The login plugins.
	 */
	private static final List<Plugin<Object>> LOGIN_PLUGINS = new ArrayList<>();

	/**
	 * Constructs a new {@Code LoginConfiguration} {@Code Object}
	 */
	public LoginConfiguration() {
		/*
		 * empty.
		 */
	}

	/**
	 * Configures the lobby login.
	 * @param player The player.
	 */
	public static void configureLobby(Player player) {
		player.updateSceneGraph(true);
		boolean lobby = false;
		if (lobby/*!player.isArtificial() && TutorialSession.getExtension(player).getStage() >= TutorialSession.MAX_STAGE && player.getAttribute("login_type", LoginType.NORMAL_LOGIN) != LoginType.RECONNECT_TYPE*/) {
			sendLobbyScreen(player);
		} else {
			configureGameWorld(player);
		}
	}

	/**
	 * Sends the lobby interface-related packets.
	 * @param player The player.
	 */
	public static void sendLobbyScreen(Player player) {
		/*Repository.getLobbyPlayers().add(player);
		player.getPacketDispatch().sendString("Welcome to " + GameWorld.getName() + "", 378, 115);
		player.getPacketDispatch().sendString(lastLogin(player), 378, 117);
		final int messages = player.getDetails().getPortal().getMessages();
		if (messages > 1) {
			player.getPacketDispatch().sendString("                                                                                                                                                                              " + "You have <col=01DF01>" + messages + " unread message</col> in your message centre.", 378, 15);
		} else {
			player.getPacketDispatch().sendString("                                                                                                                                                                              " + "You have " + messages + " unread message in your message centre.", 378, 15);
		}
		player.getPacketDispatch().sendString("text1", 378, 39);
		player.getPacketDispatch().sendString("Visit the forums", 378, 37);
		player.getPacketDispatch().sendString("Visit the official "+GameWorld.getName()+" forums to stay in touch with the latest updates.", 378, 38);
		player.getPacketDispatch().sendString("Forums", 378, 14);
		player.getPacketDispatch().sendString(GameWorld.getName() + " Store", 378, 7);
		if (player.isDonator()) {
			player.getPacketDispatch().sendString("text2", 378, 96);
			player.getPacketDispatch().sendString("You have <col=01DF01>unlimited</col> days of " + GameWorld.getName() + " member credit remaining.", 378, 94);
			player.getPacketDispatch().sendString("You have an unlimited amount of days of member credit. Thank you for supporting "+GameWorld.getName()+"!", 378, 93);
		} else {
			player.getPacketDispatch().sendString("text3", 378, 96);
			player.getPacketDispatch().sendString("You have zero days of " + GameWorld.getName() + " member credit.", 378, 94);
			player.getPacketDispatch().sendString("You are not a donator. Choose to donate and you'll get loads of extra benefits and features.", 378, 93);
		}
		player.getPacketDispatch().sendString("Never tell anyone your password, even if they claim to work for " + GameWorld.getName() + "!", 378, 56);
		player.getBankPinManager().drawLoginMessage();
		//player.getPacketDispatch().sendString(weeklyMessage, WEEKLY_MESSAGE.getComponent(), WEEKLY_MESSAGE.getChild());
		player.getInterfaceManager().openWindowsPane(new Component(549));
		player.getInterfaceManager().setOpened(new Component(378));
		PacketRepository.send(Interface.class, new InterfaceContext(player, 549, 2, 378, true));
		//PacketRepository.send(Interface.class, new InterfaceContext(player, 549, 3, WEEKLY_MESSAGE.getComponent(), true));
		
		player.getInterfaceManager().openWindowsPane(new Component(378));
		*/
	}

	/**
	 * Configures the game world.
	 * @param player The player.
	 */
	public static void configureGameWorld(final Player player) {
		player.getConfigManager().reset();
		sendGameConfiguration(player);
		Repository.getLobbyPlayers().remove(player);
		player.setPlaying(true);
		UpdateSequence.getRenderablePlayers().add(player);
		RegionManager.move(player);
		player.getMusicPlayer().init();
		player.getUpdateMasks().register(new AppearanceFlag(player));
		player.getPlayerFlags().setUpdateSceneGraph(true);
		player.getStateManager().init();
	}

	/**
	 * Sends the game configuration packets.
	 * @param player The player to send to.
	 */
	public static void sendGameConfiguration(final Player player) {
		player.getInterfaceManager().openWindowsPane(new Component(player.getInterfaceManager().isResizable() ? 746 : 548));
		player.getInterfaceManager().openChatbox(137);
		player.getInterfaceManager().openDefaultTabs();
		player.getInterfaceManager().openInfoBars();
		welcome(player);
		config(player);
		conditions(player);
		player.getCommunication().sync(player);
	}

	/**
	 * Method used to welcome the player.
	 * @param player the player.
	 */
	public static final void welcome(final Player player) {
		player.getPacketDispatch().sendMessage("Welcome to " + GameWorld.getName() + ".");
		if (!GameWorld.getSettings().isEconomy()) {
			player.getPacketDispatch().sendMessage("<col=FF0000>You are currently playing on Arios's spawn PK world.");
		}
		if (player.getDetails().getPortal().isMuted()) {
			player.getPacketDispatch().sendMessage("You are muted.");
			player.getPacketDispatch().sendMessage("To prevent further mutes please read the rules.");
		}
		if (SystemManager.getSystemConfig().getConfig("dxp", false)) {
			player.sendMessage("<col=CC6600>There is currently a double XP weekend active.");
		}
	}

	/**
	 * Method used to configure all possible settings for the player.
	 * @param player the player.
	 */
	public static final void config(final Player player) {
		player.getInventory().refresh();
		player.getEquipment().refresh();
		player.getSkills().refresh();
		player.getSkills().configure();
		player.getSettings().update();
		player.getInteraction().setDefault();
		player.getPacketDispatch().sendRunEnergy();
		player.getFamiliarManager().login();
		player.getInterfaceManager().openDefaultTabs();
		player.getGrandExchange().init();
		player.getPacketDispatch().sendString("Friends List - World " + GameWorld.getSettings().getWorldId(), 550, 3);
		player.getConfigManager().init();
		player.getAntiMacroHandler().init();
		player.getQuestRepository().update(player);
		player.getGraveManager().update();
		player.getInterfaceManager().close();
		player.getEmoteManager().refresh();
		AriosSettings.sendSettings(player);
	}

	/**
	 * Method used to check for all possible conditions on login.
	 * @param player the player.
	 */
	public static final void conditions(final Player player) {
		player.unlock();
		if (player.isArtificial()) {
			return;
		}
		if (GameWorld.isEconomyWorld()) {
			TutorialSession.extend(player);
			if (!TutorialSession.getExtension(player).finished()) {
				GameWorld.submit(new Pulse(1, player) {
					@Override
					public boolean pulse() {
						TutorialSession.getExtension(player).init();
						return true;
					}
				});
			}
		}
		if (player.getAttribute("fc_wave", -1) > -1) {
			ActivityManager.start(player, "fight caves", true);
		}
		if (player.getAttribute("falconry", false)) {
			ActivityManager.start(player, "falconry", true);
		}
		player.getConfigManager().set(678, 5);// RFD
		if (player.getSavedData().getQuestData().getDragonSlayerAttribute("repaired")) {
			player.getConfigManager().set(177, 1967876);
		}
		if (player.getSession().getClientInfo().getWindowMode() >= 2) {
			player.getConfigManager().set(281, 1000);
		}
		if (player.getSavedData().getGlobalData().getLootShareDelay() < System.currentTimeMillis() && player.getSavedData().getGlobalData().getLootShareDelay() != 0L) {
			player.getGlobalData().setLootSharePoints((int) (player.getGlobalData().getLootSharePoints() - player.getGlobalData().getLootSharePoints() * 0.10));
		} else {
			player.getSavedData().getGlobalData().setLootShareDelay(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
		}
		for (Plugin<Object> plugin : LOGIN_PLUGINS) {
			try {
				plugin.newInstance(player);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Calculates the last login and returns the message to display on the login
	 * screen.
	 * @param player The player.
	 * @return The message to display.
	 */
	@SuppressWarnings("unused")
	private static String lastLogin(Player player) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		long time = player.getDetails().getLastLogin();
		long diffDays = -1;
		if (time != -1) {
			long currentTime = dateFormat.getCalendar().getTime().getTime();
			diffDays = (currentTime - time) / (24 * 60 * 60 * 1000);
		}
		player.getDetails().setLastLogin(dateFormat.getCalendar().getTime().getTime());
		if (diffDays < 0) {
			return "Welcome to " + GameWorld.getName() + "!";
		}
		if (diffDays == 0) {
			return "You last logged in <col=ff0000>earlier today.";
		}
		if (diffDays == 1) {
			return "You last logged in <col=ff0000> yesterday.";
		}
		if (diffDays >= 2) {
			return "You last logged in <col=ff0000> " + diffDays + " days ago."; // <col=000000>
			// from:
			// "+player.getDetails().getIp() + "
		}
		return null;
	}

	/**
	 * Gets the loginPlugins.
	 * @return The loginPlugins.
	 */
	public static List<Plugin<Object>> getLoginPlugins() {
		return LOGIN_PLUGINS;
	}

}