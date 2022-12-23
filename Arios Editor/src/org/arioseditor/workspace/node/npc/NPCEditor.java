package org.arioseditor.workspace.node.npc;

import java.nio.ByteBuffer;

import org.arioseditor.cache.ServerStore;
import org.arioseditor.cache.misc.DefinitionSize;
import org.arioseditor.workspace.editor.EditorTab;

/**
 * An npc editor.
 * @author Vexia
 *
 */
public class NPCEditor extends EditorTab {

	/**
	 * The serial UID.
	 */
	private static final long serialVersionUID = 9106740527672015864L;

	/**
	 * Constructs a new {@Code NPCEditor} {@Code Object}
	 * @param name the name.
	 */
	public NPCEditor(String name) {
		super(name);
	}

	@Override
	public void parse() {
		ByteBuffer buf = ServerStore.getArchive("npc_config");
		NPC npc;
		for (int id = 0; id < DefinitionSize.getNPCDefinitionsSize(); id++) {
			npc = new NPC(id);
			if (buf.hasRemaining()) {
				npc.parse(buf);
			}
			nodes.put(id, npc);
		}
		NPCDropManager.parse();
		NPC imp = (NPC) nodes.get(6604);
		for (int id = 0; id < DefinitionSize.getNPCDefinitionsSize(); id++) {
			npc = (NPC) nodes.get(id);
			if (npc == null) {
				continue;
			}
			if (npc.getName().startsWith("Revenant") && id != 6604) {
				npc.setConfig("clue_level", (byte) (npc.getDefinition().getCombatLevel() >= 70 ? 3 : 2));
				NPCDropManager.getDropTables().get(id).getDrops(TableType.MAIN).clear();
				NPCDropManager.getDropTables().get(id).getDrops(TableType.CHARM).clear();
				for (NPCDrop drop : imp.getDrobTable(TableType.MAIN)) {
					NPCDropManager.getDropTables().get(id).addDrop(TableType.MAIN, drop);
				}
				for (NPCDrop drop : imp.getDrobTable(TableType.CHARM)) {
					NPCDropManager.getDropTables().get(id).addDrop(TableType.CHARM, drop);
				}
				for (NPCDrop drop : imp.getDrobTable(TableType.DEFAULT)) {
					NPCDropManager.getDropTables().get(id).addDrop(TableType.DEFAULT, drop);
				}
			}
		}
	}

	@Override
	public boolean save() {
		ByteBuffer buffer = ByteBuffer.allocate(3145680 << 1);
		for (int id = 0; id < DefinitionSize.getNPCDefinitionsSize(); id++) {
			NPC npc = (NPC) nodes.get(id);
			npc.save(buffer);
		}
		buffer.flip();
		ServerStore.setArchive("npc_config", buffer, false);
		NPCDropManager.save();
		return true;
	}
}
