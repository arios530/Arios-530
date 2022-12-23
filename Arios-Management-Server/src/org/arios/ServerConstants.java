package org.arios;

/**
 * Holds constants for the management server.
 * @author v4rg
 *
 */
public final class ServerConstants {

	/**
	 * The port to be used for communications.
	 */
	public static final int PORT = 55555;
	
	/**
	 * The maximum amount of worlds.
	 */
	public static final int WORLD_LIMIT = 10;
	
	/**
	 * The world switching delay in milliseconds.
	 */
	public static final long WORLD_SWITCH_DELAY = 20_000l;
	
	/**
	 * The address of the Management server.
	 */
	public static final String HOST_ADDRESS = "127.0.0.1";

	/**
	 * The store path.
	 */
	public static final String STORE_PATH = "./store/";
	
	/**
	 * The maximum amount of players per world.
	 */
	public static final int MAX_PLAYERS = (1 << 11) - 1;
	
	/**
	 * The administrators.
	 */
	public static final String[] ADMINISTRATORS = {
		"hope",
		"imacer",
		"splinter",
		"vexia"
	};
	
	/**
	 * Stops from instantiating.
	 */
	private ServerConstants() {
		/*
		 * empty.
		 */
	}
}