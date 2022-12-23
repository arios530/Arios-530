package org.arios.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.arios.gui.component.ConsoleLogger;
import org.arios.gui.tab.StatisticsTab;

/**
 * The arios frame tool.
 * @author Vexia
 */
public class AriosFrame extends JFrame {

	/**
	 * The arios frame instance.
	 */
	public static final AriosFrame INSTANCE = new AriosFrame();

	/**
	 * The serail UID.
	 */
	private static final long serialVersionUID = 6368064564449356833L;

	/**
	 * The size of the frame.
	 */
	private static final Dimension SIZE = new Dimension(1074, 664);

	/**
	 * The tabbed pane.
	 */
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * The statistics tab.
	 */
	private final StatisticsTab statisticsTab = StatisticsTab.INSTANCE.init();

	/**
	 * Constructs a new {@Code AriosFrame} {@Code Object}
	 */
	private AriosFrame() {
		super("Arios Frame");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the arios frame.
	 * @return the frame.
	 */
	public AriosFrame init() {
		setLocationRelativeTo(null);
		setSize(SIZE);
		getContentPane().setLayout(null);
		tabbedPane.setBounds(0, 0, 1068, 636);
		getContentPane().add(tabbedPane);
		addTabs(statisticsTab/* , playerTab, grandExchangeTab, utilityTab */);
		setResizable(false);
		setVisible(true);
		System.setOut(new ConsoleLogger(System.out));
		return this;
	}

	/**
	 * Adds tabs.
	 * @param tabs the tabs.
	 */
	public void addTabs(AriosTab... tabs) {
		for (AriosTab tab : tabs) {
			addTab(tab);
		}
	}

	/**
	 * Adds a tab to the tabbed pane.
	 * @param tab the tab.
	 */
	public void addTab(AriosTab tab) {
		tabbedPane.add(tab.getName(), tab);
	}

	/**
	 * Gets the statisticsTab.
	 * @return the statisticsTab
	 */
	public StatisticsTab getStatisticsTab() {
		return statisticsTab;
	}

	/**
	 * Gets the tabbedPane.
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Gets the instance.
	 * @return the instance.
	 */
	public static AriosFrame getInstance() {
		return INSTANCE;
	}

}
