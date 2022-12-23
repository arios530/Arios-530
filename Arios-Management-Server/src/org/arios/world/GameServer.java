package org.arios.world;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.arios.net.IoSession;
import org.arios.net.packet.WorldPacketRepository;
import org.arios.system.mysql.SQLEntryHandler;
import org.arios.system.mysql.WorldListSQLHandler;
import org.arios.system.util.TaskExecutor;
import org.arios.world.info.Response;
import org.arios.world.info.WorldInfo;

/**
 * Represents a game world.
 * @author Emperor
 *
 */
public final class GameServer {

	/**
	 * The world info.
	 */
	private final WorldInfo info;
	
	/**
	 * The I/O session.
	 */
	private IoSession session;
	
	/**
	 * The players active on the game server.
	 */
	private final Map<String, PlayerSession> players = new HashMap<>();
	
	/**
	 * The scheduled future for the updating task.
	 */
	private ScheduledFuture<?> future;
	
	/**
	 * constructs a new {@code GameServer} {@code Object}.
	 * @param info The world info.
	 */
	public GameServer(WorldInfo info) {
		this.info = info;
	}
	
	/**
	 * Configures the game server.
	 * @param session The I/O session.
	 */
	public void configure(IoSession session) {
		this.session = session;
		session.setGameServer(this);
		future = TaskExecutor.getExecutor().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				SQLEntryHandler.write(new WorldListSQLHandler(GameServer.this));
				if (!isActive()) {
					future.cancel(false);
					future = null;
				}
			}
		}, 0, 10, TimeUnit.SECONDS);
	}

	/**
	 * Registers a player.
	 * @param player The player.
	 */
	public void register(PlayerSession player) {
		players.put(player.getUsername(), player);
		player.setWorld(this);
		player.setActive(true);
		player.setWorldId(info.getWorldId());
		player.configure();
		WorldPacketRepository.sendRegistryResponse(this, player, Response.SUCCESSFUL);
		player.getCommunication().sync();
	}

	/**
	 * Checks if the game server is active.
	 * @return {@code True} if so.
	 */
	public boolean isActive() {
		return session.isActive();
	}

	/**
	 * @return the playerAmount
	 */
	public int getPlayerAmount() {
		return players.size();
	}

	/**
	 * @return the info
	 */
	public WorldInfo getInfo() {
		return info;
	}

	/**
	 * @return the session
	 */
	public IoSession getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(IoSession session) {
		this.session = session;
	}

	/**
	 * Gets the players value.
	 * @return The players.
	 */
	public Map<String, PlayerSession> getPlayers() {
		return players;
	}
}