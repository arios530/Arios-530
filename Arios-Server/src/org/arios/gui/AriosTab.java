package org.arios.gui;

import javax.swing.JPanel;
import java.awt.Rectangle;

/**
 * The arios tab.
 * @author Vexia
 */
public class AriosTab extends JPanel {

	/**
	 * The serial UID.
	 */
	private static final long serialVersionUID = 2899642836866716523L;

	/**
	 * The name of the arios tab.
	 */
	private final String name;

	/**
	 * Constructs a new {@Code AriosTab} {@Code Object}
	 * @param name the name.
	 */
	public AriosTab(String name) {
		super();
		this.name = name;
		setLayout(null);
		setBounds(new Rectangle(0, 0, 1068, 663));
	}

	/**
	 * Gets the name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
