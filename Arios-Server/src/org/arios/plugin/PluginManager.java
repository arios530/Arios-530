package org.arios.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.arios.game.content.activity.ActivityManager;
import org.arios.game.content.activity.ActivityPlugin;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.node.entity.player.info.login.LoginConfiguration;
import org.arios.game.system.SystemLogger;
import org.arios.game.world.GameWorld;

/**
 * Represents a class used to handle the loading of all plugins.
 * @author Emperor
 */
public final class PluginManager {

	/**
	 * The amount of plugins loaded.
	 */
	private static int pluginCount;

	/**
	 * The currently loaded plugin names.
	 */
	private static List<String> loadedPlugins = new ArrayList<>();

	/**
	 * Initializes the plugin manager.
	 */
	public static void init() {
		try {
			loadLocal(new File("plugin/"));
			loadedPlugins.clear();
			loadedPlugins = null;
			SystemLogger.log("Initialized " + pluginCount + " plugins...");
		} catch (Throwable t) {
			SystemLogger.log("Error initializing Plugins...");
		}
	}

	/**
	 * Loads the plugins in the local directory.
	 * @param directory The directory.
	 * @throws Throwable When an exception occurs.
	 */
	@SuppressWarnings("rawtypes")
	public static void loadLocal(File directory) throws Throwable {
		final URL[] url = new URL[] { directory.toURI().toURL(), null };
		URLClassLoader loader;
		for (File file : directory.listFiles()) {
			if (file.getName().equals(".DS_Store")) {
				continue;
			}
			if (GameWorld.isEconomyWorld() && file.getPath().startsWith("plugin/spawn")) {
				continue;
			}
			if (file.isDirectory()) {
				loadLocal(file);
				continue;
			}
			String fileName = file.getName().replace(".jar", "").replace(".class", "");
			if (loadedPlugins.contains(fileName)) {
				System.err.println("Duplicate plugin - " + fileName);
			}
			loadedPlugins.add(fileName);
			url[1] = file.toURI().toURL();
			loader = new URLClassLoader(url);
			JarFile jar = new JarFile(file);
			Enumeration<JarEntry> entries = jar.entries();
			boolean loaded = false;
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith(fileName + ".class")) {
					StringBuilder sb = new StringBuilder();
					for (String path : entry.getName().split("/")) {
						if (sb.length() != 0) {
							sb.append(".");
						}
						sb.append(path);
						if (path.endsWith(".class")) {
							sb.setLength(sb.length() - 6);
							break;
						}
					}
					try {
						final Plugin plugin = (Plugin) loader.loadClass(sb.toString()).newInstance();
						definePlugin(plugin);
						loaded = true;
					} catch (Throwable t) {
						System.err.println("Error for class at " + entry.getName());
						t.printStackTrace();
					}
				}
			}
			if (!loaded) {
				System.err.println("Failed to load plugin " + fileName + "!");
			}
			// loader.close(); //TODO: why is this commented out?
			jar.close();
		}
	}

	/**
	 * Defines a list of plugins.
	 * @param plugins the plugins.
	 */
	public static void definePlugin(Plugin<?>... plugins) {
		for (Plugin<?> p : plugins) {
			definePlugin(p);
		}
	}

	/**
	 * Defines the plugin.
	 * @param plugin The plugin.
	 */
	@SuppressWarnings("unchecked")
	public static void definePlugin(Plugin<?> plugin) {
		try {
			PluginManifest manifest = plugin.getClass().getAnnotation(PluginManifest.class);
			if (manifest == null) {
				manifest = plugin.getClass().getSuperclass().getAnnotation(PluginManifest.class);
			}
			if (manifest == null || manifest.type() == PluginType.ACTION) {
				plugin.newInstance(null);
			} else if (manifest.type() == PluginType.DIALOGUE) {
				((DialoguePlugin) plugin).init();
			} else if (manifest.type() == PluginType.ACTIVITY) {
				ActivityManager.register((ActivityPlugin) plugin);
			} else if (manifest.type() == PluginType.LOGIN) {
				LoginConfiguration.getLoginPlugins().add((Plugin<Object>) plugin);
			} else {
				System.out.println("Manifest: " + manifest.type());
			}
			pluginCount++;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the amount of plugins currently loaded.
	 * @return The plugin count.
	 */
	public static int getAmountLoaded() {
		return pluginCount;
	}

	/**
	 * Checks if the plugin shouldn't be repacked.
	 * @param name The plugin name.
	 * @return {@code True} if so.
	 */
	public static boolean isAutoPacked(String name) {
		return name.equals("VicTheTraderPlugin.jar");
	}
}