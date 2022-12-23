package org.arios.game.node.entity.player.info.login;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.PlayerDetails;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.entity.player.info.UIDInfo;
import org.arios.game.node.entity.player.info.portal.PlayerSQLManager;
import org.arios.game.system.SystemManager;
import org.arios.game.system.monitor.PlayerMonitor;
import org.arios.game.system.mysql.SQLEntryHandler;
import org.arios.game.system.registry.AccountRegister;
import org.arios.game.system.registry.RegistryDetails;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;
import org.arios.net.amsc.MSPacketRepository;
import org.arios.net.amsc.ManagementServerState;
import org.arios.net.amsc.WorldCommunicator;
import org.arios.parser.player.PlayerParser;
import org.arios.tools.StringUtils;

/**
 * Parses the login of a player.
 *
 * @author Emperor
 * @author Vexia
 */
public final class LoginParser implements Runnable {
	
	/**
	 * The lock used to disable 2 of the same player being logged in.
	 */
	private static final Lock LOCK = new ReentrantLock();
	
	/**
	 * The player details file.
	 */
	private final PlayerDetails details;
	
	/**
	 * The password entered in the login request.
	 */
	private String password;
	
	/**
	 * The unique id info.
	 */
	private final UIDInfo info;
	
	/**
	 * The login type.
	 */
	private final LoginType type;
	
	/**
	 * The player in the game, used for reconnect login type.
	 */
	private Player gamePlayer;
	
	/**
	 * The time stamp.
	 */
	private final int timeStamp;
	
	/**
	 * Constructs a new {@code LoginParser} {@code Object}.
	 *
	 * @param details  the player details.
	 * @param password The password entered in the login request.
	 * @param the      unique info attempting to login.
	 * @param type     The login type.
	 */
	public LoginParser(PlayerDetails details, String password, UIDInfo info, LoginType type) {
		this.details = details;
		this.password = password;
		this.type = type;
		this.info = info;
		this.timeStamp = GameWorld.getTicks();
	}
	
	@Override
	public void run() {
		LOCK.lock();
		try {
			if (validateRequest()) {
				parseDetails();
				handleLogin();
			}
		} catch (Throwable t) {
			t.printStackTrace();
			try {
				flag(Response.ERROR_LOADING_PROFILE);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		LOCK.unlock();
	}
	
	/**
	 * Handles the actual login.
	 */
	private void handleLogin() {
		/*if (GameWorld.getSettings().isBeta() && details.getRights() == Rights.REGULAR_PLAYER) {
			flag(Response.CLOSED_BETA);
			return;
		}*/
		Player p = getWorldInstance();
		final Player player = p == null ? new Player(details) : p;
		player.setAttribute("login_type", type);
		if (p != null) { // Reconnecting
			reconnect(p, type);
			return;
		}
		initialize(player);
	}
	
	/**
	 * Initializes the player.
	 *
	 * @param player The player.
	 */
	public void initialize(final Player player) {
		PlayerParser.parse(player);
		Repository.getPlayerNames().put(player.getName(), player);
		GameWorld.submit(new Pulse(1) {
			@Override
			public boolean pulse() {
				try {
					if (details.getSession().isActive()) {
						if (!Repository.getPlayers().contains(player)) {
							Repository.getPlayers().add(player);
						}
						player.getDetails().getSession().setObject(player);
						flag(Response.SUCCESSFUL);
						player.init();
						player.getMonitor().log(player.getDetails().getIp(), PlayerMonitor.ADDRESS_LOG);
						player.getMonitor().log(player.getDetails().getSerial(), PlayerMonitor.ADDRESS_LOG);
						player.getMonitor().log(player.getDetails().getMacAddress(), PlayerMonitor.ADDRESS_LOG);
					} else {
						Repository.getPlayerNames().remove(player.getName());
						MSPacketRepository.sendPlayerRemoval(player.getName());
					}
				} catch (Throwable t) {
					t.printStackTrace();
					Repository.getPlayerNames().remove(player.getName());
					MSPacketRepository.sendPlayerRemoval(player.getName());
				}
				return true;
			}
		});
	}
	
	/**
	 * Gets the player instance in the current world.
	 *
	 * @return The player instance, if found.
	 */
	private Player getWorldInstance() {
		Player player = Repository.getDisconnectionQueue().get(details.getUsername());
		if (player == null) {
			player = gamePlayer;
		}
		return player;
	}
	
	/**
	 * Initializes a reconnecting player.
	 *
	 * @param player The player.
	 * @param type   The login type.
	 */
	private void reconnect(final Player player, LoginType type) {
		Repository.getDisconnectionQueue().remove(details.getUsername());
		player.getSession().disconnect();
		player.getDetails().setSession(details.getSession());
		player.getDetails().getSession().setObject(player);
		player.initReconnect();
		player.setActive(true);
		flag(Response.SUCCESSFUL);
		player.updateSceneGraph(true);
		player.getConfigManager().init();
		LoginConfiguration.configureGameWorld(player);
		Repository.getPlayerNames().put(player.getName(), player);
		GameWorld.submit(new Pulse(1) {
			@Override
			public boolean pulse() {
				if (!Repository.getPlayers().contains(player)) {
					Repository.getPlayers().add(player);
				}
				return true;
			}
		});
	}
	
	/**
	 * Checks if the login request is valid.
	 *
	 * @return {@code True} if the request is valid.
	 */
	private boolean validateRequest() {
		if (WorldCommunicator.getState() == ManagementServerState.CONNECTING) {
			return flag(Response.LOGIN_SERVER_OFFLINE);
		}
		if (!details.getSession().isActive()) {
			return false;
		}
		if (SystemManager.isUpdating()) {
			return flag(Response.UPDATING);
		}
		if (StringUtils.invalidAccountName(details.getUsername())) {
			return flag(Response.ACCOUNT_DISABLED);
		}
		if ((gamePlayer = Repository.getPlayerByName(details.getUsername())) != null && gamePlayer.getSession().isActive()) {
			return flag(Response.ALREADY_ONLINE);
		}
		try {
			if (!PlayerSQLManager.hasSqlAccount(details.getUsername(), "username")) {
				final RegistryDetails registryDetails = new RegistryDetails(
						details.getUsername(),
						SystemManager.getEncryption().hashPassword(password),
						new Date(0),
						21);
				if (!SQLEntryHandler.write(new AccountRegister(registryDetails))) {
					return flag(Response.INVALID_CREDENTIALS);
				}
			}
		} catch (SQLException exception) {
			System.err.println("Site offline! " + exception.getMessage());
			return flag(Response.INVALID_CREDENTIALS);
		}
		return true;
	}
	
	/**
	 * Flags a response.
	 *
	 * @param response the {@link Response}.
	 * @return {@code True} if successfully logged in.
	 */
	public boolean flag(Response response) {
		details.getSession().write(response, true);
		return response == Response.SUCCESSFUL;
	}
	
	/**
	 * Used to sync the details with the database or .arios files.
	 */
	public void parseDetails() {
		details.setPassword(SystemManager.getEncryption().hashPassword(password));
		details.parse();
		details.setUid(details.getUsername().hashCode());
		if (details.isParsedSql()) {
			details.getSqlManager().parse();
		}
		details.setInfo(info);
	}
	
	/**
	 * Gets the player details.
	 *
	 * @return The player details.
	 */
	public PlayerDetails getDetails() {
		return details;
	}
	
	/**
	 * Gets the timeStamp.
	 *
	 * @return the timeStamp
	 */
	public int getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public UIDInfo getInfo() {
		return info;
	}
	
}