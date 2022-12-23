package org.arios.game.content.global.quest;

import org.arios.game.component.Component;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;

/**
 * Represents a quest.
 * @author 'Vexia
 */
public abstract class Quest {

	/**
	 * Represents the red string.
	 */
	protected static final String RED = "<col=8A0808>";

	/**
	 * Represents the blue string.
	 */
	protected static final String BLUE = "<col=08088A>";

	/**
	 * The constant representing the journal component.
	 */
	protected final int JOURNAL_COMPONENT = 275;

	/**
	 * The constant representing the quest reward component.
	 */
	protected final int REWARD_COMPONENT = 277;

	/**
	 * Represents the state of the quest.
	 */
	protected QuestState state = QuestState.NOT_STARTED;

	/**
	 * The player instance.
	 */
	protected Player player;

	/**
	 * The stage of the quest.
	 */
	protected int stage;

	/**
	 * Constructs a new {@code Quest} {@code Object}.
	 * @param player the player.
	 */
	public Quest(Player player) {
		this.player = player;
	}

	/**
	 * Method will update your quest stage on the interface.
	 */
	public abstract void update();

	/**
	 * Method will finish the quest.
	 */
	public abstract void finish();

	/**
	 * Gets the config id basted on the state of the quest.
	 * @return the config.
	 */
	public abstract int[] getConfig();

	/**
	 * Gets the index of the quest.
	 * @return the index id.
	 */
	public abstract int getIndex();

	/**
	 * Gets the quest name.
	 * @return the name of the quest.
	 */
	public abstract String getName();

	/**
	 * Gets the quest points reward.
	 * @return the quest points reward.
	 */
	public abstract int getQuestPoints();
	
	/**
	 * Gets the button id.
	 * @return the id of the button.
	 */
	public abstract int getButtonId();

	/**
	 * Clears the screen.
	 */
	public void clear() {
		for (int i = 0; i < 311; i++) {
			player.getPacketDispatch().sendString("" , JOURNAL_COMPONENT, i);
		}
		player.getPacketDispatch().sendString("<col=8A0808>" + getName() + "</col>", JOURNAL_COMPONENT, 2);
	}

	/**
	 * Clears the quest reward component of any text.
	 */
	protected final void clearRewardComponent() {
		for (int i = 0; i < 18; i++) {
			player.getPacketDispatch().sendString("", REWARD_COMPONENT, i);
		}
	}

	/**
	 * Method used to write a line.
	 * @param message the message.
	 * @param line the line.
	 */
	protected final void line(String message, int line) {
		line(message.replace("<n>", "<br><br>").replace("<blue>", BLUE).replace("<red>", RED), line, false);
	}

	/**
	 * Draws a line on the quest journal component.
	 * @param message The message.
	 * @param line The line number.
	 * @param crossed True if the message should be crossed out.
	 */
	protected final void line(String message, final int line, final boolean crossed) {
		player.getPacketDispatch().sendString(crossed ? "<str>" + message + "</str>" : message, JOURNAL_COMPONENT, line);
	}

	/**
	 * Draws text on the quest reward component.
	 * @param string The string to draw.
	 * @param line The line number to draw on.
	 */
	protected final void drawReward(final String string, final int line) {
		player.getPacketDispatch().sendString(string, REWARD_COMPONENT, line);
	}

	/**
	 * Method used to finalize the quest.
	 */
	public void finalize() {
		player.getQuestRepository().incrementPoints(getQuestPoints());
		player.getQuestRepository().update(player);
		player.getConfigManager().set(101, player.getQuestRepository().getPoints());
		player.getInterfaceManager().open(new Component(277));
		for (int i = 0; i < 18; i++) {
			if (i == 9 || i == 3 || i == 6) {
				continue;
			}
			player.getPacketDispatch().sendString("", 277, i);
		}
		setStage(100);
		setState(QuestState.COMPLETED);
		player.getPacketDispatch().sendString("" + player.getQuestRepository().getPoints() + "", 277, 7);
		player.getPacketDispatch().sendString("You have completed the " + getName() + " Quest!", 277, 4);
		player.getPacketDispatch().sendMessage("Congratulations! Quest complete!");
	}

	/**
	 * Method used to start the quest.
	 */
	public void start() {
		setStage(10);
		setState(QuestState.STARTED);
		player.getQuestRepository().update(player);
	}

	/**
	 * Checks if the player has requirements for the quest.
	 * @return {@code True} if so.
	 */
	public boolean hasRequirements() {
		return true;
	}

	/**
	 * Checks to see if the player still has a specific item.
	 * @param item The item to check.
	 * @return True if the player still has the item.
	 */
	public final boolean hasItem(final Item item) {
		return (player.getInventory().containsItem(item) || player.getBank().containsItem(item));
	}

	/**
	 * Gets the state.
	 * @return The state.
	 */
	public QuestState getState() {
		return state;
	}

	/**
	 * Sets the state.
	 * @param state The state to set.
	 */
	public void setState(QuestState state) {
		this.state = state;
	}

	/**
	 * Gets the player.
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player.
	 * @param player The player to set.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the stage.
	 * @return The stage.
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * Sets the stage.
	 * @param stage The stage to set.
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}

	/**
	 * Gets the value colour.
	 * @return the color.
	 */
	public String getBlue() {
		return BLUE;
	}

	/**
	 * Gets the red colour.
	 * @return the color.
	 */
	public String getRed() {
		return RED;
	}

	/**
	 * Checks if the quest is still in development.
	 * @return {@code True} if so.
	 */
	public boolean isInDevelopment() {
		return false;
	}

}