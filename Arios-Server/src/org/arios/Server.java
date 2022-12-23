package org.arios;

import org.arios.game.system.SystemLogger;
import org.arios.game.system.SystemShutdownHook;
import org.arios.game.system.monitor.ExceptionLog;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.world.GameSettings;
import org.arios.game.world.GameWorld;
import org.arios.gui.AriosFrame;
import org.arios.net.NioReactor;
import org.arios.net.amsc.WorldCommunicator;
import org.arios.tools.TimeStamp;

/**
 * The main class, for those that are unable to read the class' name.
 * @author Emperor
 * @author Vexia
 * 
 */
public final class Server {

	/**
	 * The time stamp of when the server started running.
	 */
	public static long startTime;

	/**
	 * The NIO reactor.
	 */
	public static NioReactor reactor;

	/**
	 * The main method, in this method we load background utilities such as
	 * cache and our world, then end with starting networking.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		if (args.length > 0) {
			GameWorld.setSettings(GameSettings.parse(args));
		}
		if (GameWorld.getSettings().isGui()) {
			AriosFrame.getInstance().init();
		}
		System.setErr(new ExceptionLog("error.txt"));
		startTime = System.currentTimeMillis();
		final TimeStamp t = new TimeStamp();
		GameWorld.prompt(true);
		SQLManager.init();
		Runtime.getRuntime().addShutdownHook(new Thread(new SystemShutdownHook()));
		SystemLogger.log("Starting NIO reactor...");
		reactor = NioReactor.configure(43594);
		NioReactor.configure(50001).start();
		WorldCommunicator.connect();
		reactor.start();
		SystemLogger.log(GameWorld.getName() + " flags " + GameWorld.getSettings().toString());
		SystemLogger.log(GameWorld.getName() + " started in " + t.duration(false, "") + " milliseconds.");
	}

	/**
	 * Gets the startTime.
	 * @return the startTime
	 */
	public static long getStartTime() {
		return startTime;
	}

	/**
	 * Sets the bastartTime.
	 * @param startTime the startTime to set.
	 */
	public static void setStartTime(long startTime) {
		Server.startTime = startTime;
	}
}