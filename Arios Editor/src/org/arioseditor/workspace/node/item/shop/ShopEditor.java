package org.arioseditor.workspace.node.item.shop;

import org.arioseditor.workspace.editor.EditorTab;
import org.arioseditor.workspace.editor.NodeEditor;
import org.arioseditor.workspace.node.Node;

/**
 * Handles the shop editor.
 * @author Vexia
 *
 */
public class ShopEditor extends EditorTab {

	/**
	 * The serial UID.
	 */
	private static final long serialVersionUID = 868480191503316753L;

	/**
	 * Constructs a new {@Code ShopEditor} {@Code Object}
	 * @param name the name.
	 */
	public ShopEditor(String name) {
		super(name);
	}
	
	@Override
	public NodeEditor getEditor(Node<?> edit) {
		return new ShopPanel(edit);
	}

	@Override
	public void parse() {
		ShopManager.parse();
	}

	@Override
	public boolean save() {
		ShopManager.save();
		return true;
	}

}
