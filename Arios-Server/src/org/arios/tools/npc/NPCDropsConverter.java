package org.arios.tools.npc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.arios.ServerConstants;
import org.arios.cache.ServerStore;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.node.entity.npc.drop.DropFrequency;
import org.arios.game.node.entity.npc.drop.RareDropTable;
import org.arios.game.node.item.ChanceItem;
import org.arios.game.world.GameWorld;
import org.arios.tools.RandomFunction;

/**
 * Converts the NPC drops data from the dump text files
 * (./data/essentials/tool/npc_dump/).
 * @author Emperor
 */
public class NPCDropsConverter {

	private static boolean convertDump = false;
	static List<String> names = new ArrayList<>();

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		if (!convertDump) {
			int count = 0;
			int size = new File("./data/list/raw_npc_drop/").listFiles().length * 2 * 50 * 12;
			ByteBuffer buffer = ByteBuffer.allocate(size);
			for (int i = 0; i < NPCDefinition.getDefinitions().size(); i++) {
				File file = new File("./data/list/raw_npc_drop/" + i + ".txt");
				if (!file.exists()) {
					continue;
				}
				count++;
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				buffer.putShort((short) i); // Npc id.
				while ((line = br.readLine()) != null) {
					if (line.startsWith("table=")) {
						buffer.putShort((short) -1);
						switch (line.replace("table=", "")) {
						case "default":
							buffer.put((byte) 1);
							break;
						case "charm":
							buffer.put((byte) 2);
							break;
						case "main":
							buffer.put((byte) 3);
							break;
						}
					} else if (line.startsWith("item=")) {
						line = line.replace("item=", "");
						if (line.contains("//")) {
							line = line.substring(0, line.indexOf(" //"));
						}
						String[] arg = line.replaceAll(", ", ";").split(";");
						buffer.putShort(Short.parseShort(arg[0])); // Item id
						buffer.putInt(Integer.parseInt(arg[1])); // Min amount
						buffer.putInt(Integer.parseInt(arg[2])); // Max amount
						DropFrequency frequency = null;
						for (DropFrequency f : DropFrequency.values()) {
							if (f.name().equals(arg[3])) {
								frequency = f;
							}
						}
						buffer.putShort((short) (Double.parseDouble(arg[4]) * 1000));
						buffer.put((byte) frequency.ordinal()); // Drop
						// frequency
					}
				}
				buffer.putShort((short) -1);
				buffer.put((byte) 0);
				br.close();
			}
			buffer.putShort((short) -1);
			ServerStore.setArchive("npc_drops", (ByteBuffer) buffer.flip(), false);
			ServerStore.createStaticStore(ServerConstants.STORE_PATH);
			System.out.println("Completed dumping drops for " + count + " npcs!");
			return;
		}
		int maxLevel = 0;
		for (NPCDefinition def : NPCDefinition.getDefinitions().values()) {
			if (def.getCombatLevel() > maxLevel) {
				maxLevel = def.getCombatLevel();
			}
		}
		System.out.println("Maximum combat level: " + maxLevel);
		int npcId = -1;

		try {
			for (int i = 0; i < NPCDefinition.getDefinitions().size(); i++) {
				npcId = i;
				File file = new File("./data/essentials/tool/npc_drops/" + i + ".txt");
				if (!file.exists()) {
					continue;
				}
				NPCDefinition def = NPCDefinition.forId(i);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				List<ChanceItem> defaultTable = new ArrayList<>();
				List<ChanceItem> charmTable = new ArrayList<>();
				List<ChanceItem> mainTable = new ArrayList<>();
				List<ChanceItem> current = null;
				double charmPercentage = 1000.0;
				int mainCharm = 0; // itemId | percentage << 16
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("table=")) {
						switch (line.replace("table=", "")) {
						case "default":
							current = defaultTable;
							break;
						case "charm":
							current = charmTable;
							break;
						case "main":
							current = mainTable;
							break;
						}
					} else if (current != null) {
						String[] arg = line.replaceAll(" - ", ";").split(";");
						int itemId = -1;
						boolean noted = arg[arg.length - 1].equals("noted");
						itemId = findItem(arg[0], noted);
						DropFrequency frequency = DropFrequency.ALWAYS;
						double rate = def.getCombatLevel() / maxLevel;
						String[] amt = arg[1].replace("(un-noted)", "").replace("or", "-").split("-");
						if (current == charmTable) {
							switch (arg[0]) {
							case "none":
								itemId = 0;
								break;
							case "gold":
								itemId = 12158;
								break;
							case "green":
								itemId = 12159;
								break;
							case "crimson":
								itemId = 12160;
								break;
							case "blue":
								itemId = 12163;
								break;
							}
							double percentage = Double.parseDouble(amt[0].replace("%", "")) * 10;
							if (itemId != 0 && (mainCharm >> 16) < percentage) {
								mainCharm = itemId | (int) percentage << 16;
							}
							charmPercentage -= percentage;
							rate = percentage / 1000;
							amt = new String[] { "1" };
						} else if (current != defaultTable) {
							switch (arg[arg.length - (noted ? 2 : 1)].toLowerCase()) {
							case "common":
								frequency = DropFrequency.COMMON;
								break;
							case "rare":
								frequency = DropFrequency.RARE;
								break;
							case "very rare":
								frequency = DropFrequency.VERY_RARE;
								break;
							case "always":
								break;
							case "uncommon":
							default:
								frequency = DropFrequency.UNCOMMON;
								break;
							}
						}
						amt[0] = amt[0].replaceAll(" ", "").replace("(noted/unnoted)", "").replace("(Noted)", "").replace("(un", "").replace("noted)", "").replace("onion", "");
						if (amt[0].getBytes().length > 9) {
							System.out.println("Id " + i + " drops " + amt[0] + " of " + itemId);
						}
						int amount = (amt[0].equals("") || amt[0].getBytes().length > 9 || amt[0].equals("?") || amt[0].equalsIgnoreCase("unknown")) ? 1 : Integer.parseInt(amt[0]);// 500-15000
						int amount2 = amount;
						if (amt.length > 1) {
							amt[1] = amt[1].replaceAll(" ", "").replace("?", "").replace("(noted)", "").replace(".", "").replace("(noted/unnoted)", "").replace("(Noted)", "").replace("(un", "").replace("noted)", "");
							if (amt[1].getBytes().length < 10 && !amt[1].equals("")) {
								amount2 = Integer.parseInt(amt[1]);
							}
						}
						if (itemId == -1) {
							// System.out.println("NPC id=" + i);
							continue;
						}
						ChanceItem item = new ChanceItem(itemId, amount, amount2, 1000, rate, frequency);
						if (current == mainTable && frequency == DropFrequency.ALWAYS) {
							defaultTable.add(item);
						} else {
							current.add(item);
						}
					}
				}
				if (mainCharm != 0) {
					for (ChanceItem charm : charmTable) {
						if (charm.getId() == (mainCharm & 0xFFFF)) {
							charm.setChanceRate(charm.getChanceRate() + (charmPercentage / 1000));
						}
					}
				}
				reader.close();
				dumpDropData(i, def, defaultTable, charmTable, mainTable);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println("Crashed on NPC " + npcId);
		}
		System.out.println("Done!");
	}

	private static void dumpDropData(int npcId, NPCDefinition def, List<ChanceItem> defaultTable, List<ChanceItem> charmTable, List<ChanceItem> mainTable) throws Throwable {
		if (defaultTable.isEmpty() && charmTable.isEmpty() && mainTable.isEmpty()) {
			System.out.println("NPC [id=" + npcId + ", name=" + def.getName() + "] has no drop data!");
			return;
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./data/essentials/list/raw_npc_drop/" + npcId + ".txt")));
		if (!defaultTable.isEmpty()) {
			bw.append("table=default");
			bw.newLine();
			for (ChanceItem item : defaultTable) {
				bw.append("item=" + item.getId() + ", " + item.getMinimumAmount() + ", " + item.getMaximumAmount() + ", " + item.getDropFrequency().name() + ", " + item.getChanceRate() + " //" + item.getDefinition().getName());
				bw.newLine();
			}
		}
		if (!charmTable.isEmpty()) {
			bw.append("table=charm");
			bw.newLine();
			for (ChanceItem item : charmTable) {
				String name = item.getId() == 0 ? "None" : item.getDefinition().getName();
				bw.append("item=" + item.getId() + ", " + item.getMinimumAmount() + ", " + item.getMaximumAmount() + ", " + item.getDropFrequency().name() + ", " + item.getChanceRate() + " //" + name);
				bw.newLine();
			}
		}
		if (!mainTable.isEmpty()) {
			bw.append("table=main");
			bw.newLine();
			for (ChanceItem item : mainTable) {
				bw.append("item=" + item.getId() + ", " + item.getMinimumAmount() + ", " + item.getMaximumAmount() + ", " + item.getDropFrequency().name() + ", " + item.getChanceRate() + " //" + (item.getId() == RareDropTable.SLOT_ITEM_ID ? "Rare drop table slot" : item.getDefinition().getName()));
				bw.newLine();
			}
		}
		bw.flush();
		bw.close();
	}

	private static int findItem(String name, boolean noted) {
		name = name.toLowerCase();
		if (name.contains("rare_dt_slot")) {
			return RareDropTable.SLOT_ITEM_ID;
		}
		if (name.contains("coin")) {
			return 995;
		}
		if (name.equals("dragon chainbody")) {
			return 3140;
		}
		if (name.contains(" ashes")) {
			return 592;
		}
		if ((name.contains("bronze") || name.contains("iron") || name.contains("steel") || name.contains("black") || name.contains("mithril") || name.contains("white") || name.startsWith("ad") || name.startsWith("run") || name.contains("dragon")) && !name.contains(" full") && name.contains("helm")) {
			name = name.replace("helm", "med helm");
		} else if (name.equals("rune bar")) {
			name = "runite bar";
		} else if (name.equals("hard leather body")) {
			name = "hardleather body";
		} else if (name.equals("adamant bar")) {
			name = "adamantite bar";
		} else if (name.equals("wheat")) {
			name = "grain";
		} else if (name.contains("throwing axe")) {
			name = name.replace("throwing axe", "thrownaxe");
		} else if (name.contains("arrowheads")) {
			name = name.replace("arrowheads", "arrowtips");
		} else if (name.contains("weapon poison") && name.contains("(") && !name.contains("(unf)")) {
			name = name.replace(" (", "(");
			name = name.substring(0, name.indexOf("("));
		} else if (name.equals("adamant warhammer")) {
			name = "addy warhammer";
		} else if (name.equals("rune claw")) {
			name = "rune claws";
		} else if (name.contains(" hatchet")) {
			name = name.replace("hatchet", "axe");
		} else if (name.contains("rune cross")) {
			name = name.replace("crossbow", "c'bow");
		} else if (name.contains("shieldbow")) {
			name = name.replace("shieldbow", "longbow");
		} else if (name.contains("vamb")) {
			if (name.contains(" age ") || name.contains("leather") || name.startsWith("spiky")) {

			} else if (name.contains("spiky")) {
				name = name.replace("vambraces", "vambs");
			} else if (name.contains("d'hide")) {
				name = name.replace("vambraces", "vamb");
			}
		} else if (name.contains("clue scroll")) {
			if (name.contains("hard") || name.contains("level 3")) {
				return 2723 + (RandomFunction.random(13) * 2);
			} else if (name.contains("med") || name.contains("level 2")) {
				return 2801 + (RandomFunction.random(29) * 2);
			} else if (name.contains("easy") || name.contains("level 1")) {
				return RandomFunction.random(2677, 2717);
			}
		}
		for (ItemDefinition def : ItemDefinition.getDefinitions().values()) {
			if (def.getName().equalsIgnoreCase(name) && def.isUnnoted() == !noted) {
				return def.getId();
			}
		}
		if (name.contains(" (")) {
			return findItem(name.replace(" (", "("), noted);
		}
		if (!names.contains(name)) {
			names.add(name);
			System.out.println("Non-existing item: [name=" + name + ", noted=" + noted + "]!");
		}
		return -1;
	}
}
