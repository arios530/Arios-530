package org.arios.tools.impl;

import java.nio.ByteBuffer;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.ServerStore;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.InterfaceType;
import org.arios.game.world.GameWorld;

/**
 * Handles the component definitions editing.
 * @author Emperor
 *
 */
public final class ComponentDefinitionsEditor {

	/**
	 * If definitions are being edited.
	 */
	public static boolean editing = false;

	/**
	 * The main method.
	 * @param args The arguments.
	 * @throws Throwable When an exception occurs.
	 */
	public static final void main(String... args) throws Throwable {
		editing = false;
		GameWorld.prompt(false);
		System.out.println("Loaded components.");
		editing = true;
		ComponentDefinition def = ComponentDefinition.forId(667);
		def.setType(InterfaceType.DEFAULT);

		def = ComponentDefinition.forId(13);
		def.setType(InterfaceType.DEFAULT);

		def = ComponentDefinition.forId(102);
		def.setType(InterfaceType.DEFAULT);

		def = ComponentDefinition.forId(381);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);

		def = ComponentDefinition.forId(482);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(656);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(598);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(601);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(12);
		def.setType(InterfaceType.DEFAULT);
		// TODO: def.setAccessMask(new AccessMaskContext(null, 1026, 89, 12, 0,
		// 536));

		def = ComponentDefinition.forId(259);
		// TODO: def.setContext(new InterfaceContext(null, 548, 130, 259,
		// false));

		def = ComponentDefinition.forId(15);
		def.setType(InterfaceType.SINGLE_TAB);
		// TODO: def.setAccessMask(new AccessMaskContext(null, 1026, 0, 15, 0,
		// 28));

		def = ComponentDefinition.forId(107);
		def.setType(InterfaceType.SINGLE_TAB);
		
		def = ComponentDefinition.forId(763);
		def.setType(InterfaceType.SINGLE_TAB);

		
		def = ComponentDefinition.forId(336);
		// TODO: def.setContext(new InterfaceContext(null, 548, 131, 336,
		// true)); //True
		// TODO: def.setCs2ScriptContext(new RunScriptContext(null, 150,
		// "IviiiIsssssssss", "", "", "", "", "", "", "", "", "Wear", -1, 0, 7,
		// 4, 98, 22020096));
		// TODO: def.setAccessMask(new AccessMaskContext(null, 1278, 0, 336, 0,
		// 28));

		// Wildy interface
		def = ComponentDefinition.forId(380);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);

		// Fight pits interface
		def = ComponentDefinition.forId(373);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);

		def = ComponentDefinition.forId(553);
		def.setType(InterfaceType.DEFAULT);

		// Dialogue interfaces.
		def = ComponentDefinition.forId(140);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(228);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(230);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(232);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(234);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(241);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(242);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(243);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(244);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(64);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(131);// one line two or one items on inter.
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(307);// cooking
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(311);// smelting
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(74);// smelting
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(65);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(66);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(67);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(210);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(211);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(212);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(213);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(621);
		def.setType(InterfaceType.SINGLE_TAB);
		// TODO: def.setAccessMask(new AccessMaskContext(null, 1278, 0, 621, 0,
		// 28));

		def = ComponentDefinition.forId(620);
		def.setType(InterfaceType.DEFAULT);
		// TODO: def.setAccessMask(new AccessMaskContext(null, 1278, 24, 620, 0,
		// 40));

		int[] levelupInterfaces = new int[] { 158, 161, 175, 167, 171, 170, 168, 159, 177, 165, 164, 163, 160, 174, 169, 166, 157, 176, 173, 162, 172, 519, 395, 630 };
		for (int id : levelupInterfaces) {
			def = ComponentDefinition.forId(id);
			def.setType(InterfaceType.DIALOGUE);
			def.setWalkable(false);
		}
		def = ComponentDefinition.forId(668);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(306);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(140);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(303);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(309);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(304);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(305);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		/**
		 * The musuem kudos.
		 */
		def = ComponentDefinition.forId(532);
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);

		/**
		 * 94 : destroy item.
		 */
		def = ComponentDefinition.forId(94);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		/**
		 * 319
		 */
		def = ComponentDefinition.forId(319);
		def.setType(InterfaceType.DEFAULT);

		def = ComponentDefinition.forId(324);
		def.setType(InterfaceType.DEFAULT);

		def = ComponentDefinition.forId(660);
		def.setType(InterfaceType.DEFAULT);

		def = ComponentDefinition.forId(372);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(582);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(421);
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(92);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(0);
		def.setWalkable(true);

		def = ComponentDefinition.forId(320);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(1);
		def.setWalkable(true);

		def = ComponentDefinition.forId(274);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(2);
		def.setWalkable(true);

		def = ComponentDefinition.forId(149);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(3);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(259);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(2);
		def.setWalkable(true);
		

		def = ComponentDefinition.forId(387);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(4);
		def.setWalkable(true);

		def = ComponentDefinition.forId(271);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(5);
		def.setWalkable(true);

		def = ComponentDefinition.forId(192);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(6);
		def.setWalkable(true);

		def = ComponentDefinition.forId(193);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(6);
		def.setWalkable(true);

		def = ComponentDefinition.forId(430);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(6);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(662);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(7);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(550);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(8);
		def.setWalkable(true);

		def = ComponentDefinition.forId(551);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(9);
		def.setWalkable(true);

		def = ComponentDefinition.forId(589);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(10);
		def.setWalkable(true);

		def = ComponentDefinition.forId(261);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(11);
		def.setWalkable(true);

		def = ComponentDefinition.forId(464);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(12);
		def.setWalkable(true);

		def = ComponentDefinition.forId(187);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(13);
		def.setWalkable(true);

		def = ComponentDefinition.forId(182);
		def.setType(InterfaceType.TAB);
		def.setTabIndex(14);
		def.setWalkable(true);

		def = ComponentDefinition.forId(548);
		def.setType(InterfaceType.WINDOW_PANE);
		def.setWalkable(true);

		def = ComponentDefinition.forId(746);
		def.setType(InterfaceType.WINDOW_PANE);
		def.setWalkable(true);

		def = ComponentDefinition.forId(755);
		def.setType(InterfaceType.WINDOW_PANE);
		def.setWalkable(false);
		
		def = ComponentDefinition.forId(740); 
		def.setType(InterfaceType.DIALOGUE);
		def.setWalkable(false);

		def = ComponentDefinition.forId(516); 
		def.setWalkable(false);

		def = ComponentDefinition.forId(137); //ONLY this uses CHATBOX interface type, all the rest uses DIALOGUE!
		def.setType(InterfaceType.CHATBOX);
		def.setWalkable(true);
		
		def = ComponentDefinition.forId(24); 
		def.setType(InterfaceType.OVERLAY);
		def.setWalkable(true);

		def = ComponentDefinition.forId(389);
		def.setType(InterfaceType.CS_CHATBOX);
		def.setWalkable(false);

		dump();
	}

	/**
	 * Dumps the component definitions.
	 */
	private static void dump() {
		editing = false;
		System.out.println("Dumping " + Cache.getInterfaceDefinitionsSize() + " components...");
		ByteBuffer buffer = ByteBuffer.allocate(2048);
		for (int id = 0; id < Cache.getInterfaceDefinitionsSize(); id++) {
			ComponentDefinition def = ComponentDefinition.getDefinitions().get(id);
			if (def == null) {
				continue;
			}
			buffer.putShort((short) id);
			if (def.getType() != InterfaceType.DEFAULT) {
				buffer.put((byte) 1).put((byte) def.getType().ordinal());
			}
			if (def.isWalkable()) {
				if (def.getType() != InterfaceType.TAB && def.getType() != InterfaceType.CHATBOX && def.getType() != InterfaceType.WINDOW_PANE && def.getType() != InterfaceType.OVERLAY) {
					System.err.println("Warning: Component [id=" + id + ", type=" + def.getType() + "] is most-likely non-walkable!");
				}
				buffer.put((byte) 2);
			}
			if (def.getTabIndex() > -1) {
				buffer.put((byte) 3).put((byte) def.getTabIndex());
			}
			buffer.put((byte) 0);
		}
		buffer.putShort((short) -1);
		buffer.flip();
		ServerStore.setArchive("component_config", buffer, false);
		ServerStore.createStaticStore(ServerConstants.STORE_PATH);
	}
}