package org.arios.tools.item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.world.GameWorld;

public class ItemList {

	/**
	 * Represents the items.
	 */
	private static final Object[][] ITEMS = { // [item id, name to dump as]
	{ 4856, "Ahrim's hood" }, { 4857, "Ahrim's hood" }, { 4858, "Ahrim's hood" }, { 4859, "Ahrim's hood" }, { 4860, "Ahrim's hood" }, { 4862, "Ahrim's staff" }, { 4863, "Ahrim's staff" }, { 4864, "Ahrim's staff" }, { 4865, "Ahrim's staff" }, { 4866, "Ahrim's staff" }, { 4868, "Ahrim's top" }, { 4869, "Ahrim's top" }, { 4870, "Ahrim's top" }, { 4871, "Ahrim's top" }, { 4872, "Ahrim's top" }, { 4874, "Ahrim's skirt" }, { 4875, "Ahrim's skirt" }, { 4876, "Ahrim's skirt" }, { 4877, "Ahrim's skirt" }, { 4878, "Ahrim's skirt" }, { 4880, "Dharok's helm" }, { 4881, "Dharok's helm" }, { 4882, "Dharok's helm" }, { 4883, "Dharok's helm" }, { 4884, "Dharok's helm" }, { 4886, "Dharok's greataxe" }, { 4887, "Dharok's greataxe" }, { 4888, "Dharok's greataxe" }, { 4889, "Dharok's greataxe" }, { 4890, "Dharok's greataxe" }, { 4892, "Dharok's platebody" }, { 4893, "Dharok's platebody" }, { 4894, "Dharok's platebody" }, { 4895, "Dharok's platebody" }, { 4896, "Dharok's platebody" }, { 4898, "Dharok's platelegs" }, { 4899, "Dharok's platelegs" }, { 4900, "Dharok's platelegs" }, { 4901, "Dharok's platelegs" }, { 4902, "Dharok's platelegs" }, { 4904, "Guthan's helm" }, { 4905, "Guthan's helm" }, { 4906, "Guthan's helm" }, { 4907, "Guthan's helm" }, { 4908, "Guthan's helm" }, { 4910, "Guthan's spear" }, { 4911, "Guthan's spear" }, { 4912, "Guthan's spear" }, { 4913, "Guthan's spear" }, { 4914, "Guthan's spear" }, { 4916, "Guthan's body" }, { 4917, "Guthan's body" }, { 4918, "Guthan's body" }, { 4919, "Guthan's body" }, { 4920, "Guthan's body" }, { 4922, "Guthan's skirt" }, { 4923, "Guthan's skirt" }, { 4924, "Guthan's skirt" }, { 4925, "Guthan's skirt" }, { 4926, "Guthan's skirt" }, { 4928, "Karil's coif" }, { 4929, "Karil's coif" }, { 4930, "Karil's coif" }, { 4931, "Karil's coif" }, { 4932, "Karil's coif" }, { 4934, "Karil's x-bow" }, { 4935, "Karil's x-bow" }, { 4936, "Karil's x-bow" }, { 4937, "Karil's x-bow" }, { 4938, "Karil's x-bow" }, { 4940, "Karil's top" }, { 4941, "Karil's top" }, { 4942, "Karil's top" }, { 4943, "Karil's top" }, { 4944, "Karil's top" }, { 4946, "Karil's skirt" }, { 4947, "Karil's skirt" }, { 4948, "Karil's skirt" }, { 4949, "Karil's skirt" }, { 4950, "Karil's skirt" }, { 4952, "Torag's helm" }, { 4953, "Torag's helm" }, { 4954, "Torag's helm" }, { 4955, "Torag's helm" }, { 4956, "Torag's helm" }, { 4958, "Torag's hammers" }, { 4959, "Torag's hammers" }, { 4960, "Torag's hammers" }, { 4961, "Torag's hammers" }, { 4962, "Torag's hammers" }, { 4964, "Torag's body" }, { 4965, "Torag's body" }, { 4966, "Torag's body" }, { 4967, "Torag's body" }, { 4968, "Torag's body" }, { 4970, "Torag's legs" }, { 4971, "Torag's legs" }, { 4972, "Torag's legs" }, { 4973, "Torag's legs" }, { 4974, "Torag's legs" }, { 4976, "Verac's helm" }, { 4977, "Verac's helm" }, { 4978, "Verac's helm" }, { 4979, "Verac's helm" }, { 4980, "Verac's helm" }, { 4982, "Verac's flail" }, { 4983, "Verac's flail" }, { 4984, "Verac's flail" }, { 4985, "Verac's flail" }, { 4986, "Verac's flail" }, { 4988, "Verac's top" }, { 4989, "Verac's top" }, { 4990, "Verac's top" }, { 4991, "Verac's top" }, { 4992, "Verac's top" }, { 4994, "Verac's skirt" }, { 4995, "Verac's skirt" }, { 4996, "Verac's skirt" }, { 4997, "Verac's skirt" }, { 4998, "Verac's skirt" } };

	/**
	 * The main method.
	 * @param args The agruments.
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		Cache.init(ServerConstants.CACHE_PATH);
		GameWorld.prompt(false);
		dumpItems();
	}

	/**
	 * Dump the items.
	 * @throws IOException
	 */
	public static void dumpItems() throws IOException {
		if (true) {
			BufferedWriter writer = new BufferedWriter(new FileWriter("./temp.txt"));
			for (int i = 0; i < Cache.getItemDefinitionsSize(); i++) {

				ItemDefinition def = ItemDefinition.forId(i);

				/*
				 * f (!def.hasAction("empty")) { continue; } if
				 * (def.getName().contains("pot") ||
				 * def.getName().contains("Pot") /**||
				 * def.getName().contains("potion") ||
				 * def.getName().contains(" mix(") ||
				 * def.getName().contains("Magic essence") ||
				 * def.getName().contains("balance") ||
				 * def.getName().contains(" brew") ||
				 * def.getName().contains("Plant cure") ||
				 * def.getName().contains("poison") ||
				 * def.getName().contains(" restore") ||
				 * def.getName().contains("serum") ||
				 * def.getName().contains("Super")*) { /**
				 * writer.append(def.getName().toUpperCase().replaceAll(" ",
				 * "_").replace('.', '_').replace('(', '_').replace(')', (char)
				 * 0) + "(" + i +
				 * ", 1931, \"You empty the contents of the l on the floor.\"),"
				 * ); writer.newLine(); } /*if (def.getName().contains("ucket")
				 * || def.getName().contains("potion") ||
				 * def.getName().contains(" mix(") ||
				 * def.getName().contains("Magic essence") ||
				 * def.getName().contains("balance") ||
				 * def.getName().contains(" brew") ||
				 * def.getName().contains("Plant cure") ||
				 * def.getName().contains("poison") ||
				 * def.getName().contains(" restore") ||
				 * def.getName().contains("serum") ||
				 * def.getName().contains("Super")) { continue; }
				 */

				if (def.getName().contains("broken")) {
					System.out.println(def.getId() + " - " + def.getName());
				}
			}
			writer.flush();
			writer.close();
			return;
		}
		@SuppressWarnings("unused")
		File file = new File("./data/lists/itemlist.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("./data/lists/itemlist.txt"));
		for (int i = 0; i < Cache.getItemDefinitionsSize(); i++) {
			String name = ItemDefinition.forId(i).getName();
			if (name == null)
				continue;
			writer.write(i + " - " + name);
			writer.newLine();
		}
		writer.close();
	}

	/**
	 * @return the items.
	 */
	public static Object[][] getItems() {
		return ITEMS;
	}
}
