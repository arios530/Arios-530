package org.arios.system.mysql;

import org.arios.world.GameServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages the sql connections.
 * @author Vexia
 *
 */
public final class SQLManager {
	
	/**
	 * The database URL.
	 */
	//public static final String DATABASE_URL = "167.114.128.71:3306/arios_global";
	public static final String DATABASE_URL = "localhost:3306/arios_global";

	/**
	 * The username of the user.
	 */
	private static final String USERNAME = "root";
	
	/**
	 * The password of the user.
	 */
	private static final String PASSWORD = getProperties("password.properties", true).getProperty("password");

	private static Properties getProperties(String path, boolean password) {
		FileInputStream fileInputStream;
		Properties properties = new Properties();
		try {
			if (password && !Files.exists(Paths.get(path))) {		
				FileOutputStream fileOutputStream = new FileOutputStream(path);
				properties.put("password", "");
				properties.store(fileOutputStream, "Automatically generated password properties file.");
			}
			fileInputStream = new FileInputStream(path);
			properties.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	/**
	 * IF the sql manager is initialized.
	 */
	private static boolean initialized;
	
	/**
	 * Constructs a new {@code SQLManager} {@code Object}
	 */
	public SQLManager() {
		/**
		 * empty.
		 */
	}

	/**
	 * Initializes the sql manager.
	 */
	public static void init() {
		try {
			//Old JDBC driver
			//Class.forName("com.mysql.jdbc.Driver");
			//New JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		initialized = true;
		WorldListSQLHandler.clearWorldList();
	}
	
	/**
	 * Gets a connection from the pool.
	 * @return The connection.
	 */
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://" + DATABASE_URL + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=GMT-5",  USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Error: Mysql error message=" + e.getMessage() + ".");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Releases the connection so it's available for usage.
	 * @param connection The connection.
	 */
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the initialized.
	 * @return the initialized
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets the bainitialized.
	 * @param initialized the initialized to set.
	 */
	public static void setInitialized(boolean initialized) {
		SQLManager.initialized = initialized;
	}

}
