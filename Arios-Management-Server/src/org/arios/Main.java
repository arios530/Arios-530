package org.arios;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;

import org.arios.net.NioReactor;
import org.arios.net.packet.WorldPacketRepository;
import org.arios.store.ServerStore;
import org.arios.system.ShutdownSequence;
import org.arios.system.mysql.SQLManager;
import org.arios.system.util.Command;
import org.arios.world.GameServer;
import org.arios.world.PlayerSession;
import org.arios.world.WorldDatabase;
import org.arios.world.info.UIDInfo;

/**
 * The main class.
 * @author Emperor
 *
 */
public final class Main {

	/**
	 * The commands.
	 */
	private static final Command[] COMMANDS = {
		new Command("-commands", "Print a list of all commands.") {
			@Override
			public void run(String...args) {
				for (Command c : COMMANDS) {
					System.out.println("Command " + c.getName() + ": " + c.getInfo());
				}
			}
		},
		new Command("-s", "Safely shuts down the server.") {
			@Override
			public void run(String...args) {
				System.out.println("Shutting down Management server...");
				ShutdownSequence.shutdown();
			}
		},
		new Command("-debug", "Debug world info.") {
			@Override
			public void run(String...args) {
				System.out.println("---------------------------------------------");
				for (GameServer server : WorldDatabase.getWorlds()) {
					if (server != null) {
						System.out.println("World [id=" + server.getInfo().getWorldId() + ", IP=" + server.getInfo().getAddress() + ", country=" + server.getInfo().getCountry() + ", members=" + server.getInfo().isMembers() + ", players=" + server.getPlayers().size() + ", active=" + server.isActive() + "].");
					}
				}
			}
		},
		new Command("-pinfo", "Debugs player information (usage: -pinfo emperor).") {
			@Override
			public void run(String...args) {
				String name = args[1];
				PlayerSession player = WorldDatabase.getPlayer(name);
				if (player == null) {
					System.out.println("Player " + name + " was not registered!");
					return;
				}
				System.out.println("Player [name=" + name + ", world=" + player.getWorldId() + ", active=" + player.isActive() + "].");
			}
		},
		new Command("-update", "Calls an update on all the game servers (-update -1 to cancel).") {
			@Override
			public void run(String...args) {
				int ticks = Integer.parseInt(args[1]);
				for (GameServer server : WorldDatabase.getWorlds()) {
					if (server != null && server.isActive()) {
						WorldPacketRepository.sendUpdate(server, ticks);
					}
				}
			}
		},
		new Command("-rank", "Changes the rank of a player (usage: -rank emperor 2).") {
			@Override
			public void run(String...args) {
				String name = args[1];
				int rank = Integer.parseInt(args[2]);
				PlayerSession player = WorldDatabase.getPlayer(args[1]);
				if (player == null) {
					player = new PlayerSession(name, "password", new UIDInfo());
					if (!player.parse(false)) {
						System.out.println("Player " + args[1] + " does not exist!");
						return;
					}
				}
				player.setRights(rank);
				player.save();
			}
		},
		new Command("-reloadconfig", "Reloads the configurations of all worlds.") {

			@Override
			public void run(String... args) {
				for (GameServer server : WorldDatabase.getWorlds()) {
					if(server == null) {
						continue;
					}
					WorldPacketRepository.sendConfigReload(server);
				}
			}
			
		}
	};
	
	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String...args) throws Throwable {
		if (!isLocallyHosted(ServerConstants.HOST_ADDRESS)) {
			System.err.println("WARNING: Configure host address in server constants!");
		}
		System.out.println("-------- Arios Management server --------");
		ServerStore.init(ServerConstants.STORE_PATH); //Why was this commented out?
		SQLManager.init();
		NioReactor.configure(ServerConstants.PORT).start();
		NioReactor.configure(43595).start();
		Runtime.getRuntime().addShutdownHook(new ShutdownSequence());
		System.out.println("Status: ready.");
		System.out.println("Use -commands for a list of commands!");
		Scanner s = new Scanner(System.in);
		while (s.hasNext()) {
			try {
				String command = s.nextLine();
				if (!command.startsWith("-")) {
					continue;
				}
				String[] arguments = command.split(" ");
				command = arguments[0];
				for (Command c : COMMANDS) {
					if (c.getName().equals(command)) {
						System.out.println("Handling command \"" + command + "\"!");
						c.run(arguments);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		s.close();
	}
	
	/**
	 * Checks if the Management server is locally hosted.
	 * @return {@code True} if so.
	 * @throws IOException When an I/O exception occurs.
	 */
	private static boolean isLocallyHosted(String ip) throws IOException {
		InetAddress address = InetAddress.getByName(ip);
		if (address.isAnyLocalAddress() || address.isLoopbackAddress()) {
			return true;
		}
		return NetworkInterface.getByInetAddress(address) != null;
	}
}