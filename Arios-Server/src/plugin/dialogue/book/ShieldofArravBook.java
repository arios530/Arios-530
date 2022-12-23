package plugin.dialogue.book;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.book.Book;
import org.arios.game.content.dialogue.book.BookLine;
import org.arios.game.content.dialogue.book.Page;
import org.arios.game.content.dialogue.book.PageSet;
import org.arios.game.content.global.quest.impl.free.ShieldofArrav;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the shield of arrav book.
 * @author 'Vexia
 * @version 1.0
 */
public final class ShieldofArravBook extends Book {

	/**
	 * Represents the shield of arrav book id.
	 */
	public static int ID = 49610758;

	/**
	 * Represents the array of pages for this book.
	 */
	private static final PageSet[] PAGES = new PageSet[] { new PageSet(new Page(new BookLine("The Shield of Arrav", 87), new BookLine("by A.R Wright", 89), new BookLine("Arrav is probably the best", 93), new BookLine("known hero of the 4th", 94), new BookLine("age. Many legends are", 95), new BookLine("told of his heroics. One", 96), new BookLine("surviving artefact from", 97)), new Page(new BookLine("the 4th age is a fabulous", 98), new BookLine("shield.", 99), new BookLine("This shield is believed to", 101), new BookLine("have once belonged to", 102), new BookLine("Arrav and is now indeed", 103), new BookLine("known as the Shield of", 104), new BookLine("Arrav. For over 150", 105), new BookLine("years it was the prize", 106), new BookLine("piece in the royal", 107), new BookLine("museum of Varrock.", 108))), new PageSet(new Page(new BookLine("However, in the year 143", 87), new BookLine("of the fith age a gang of", 88), new BookLine("thieves called the Phoenix", 89), new BookLine("Gang broke into the", 90), new BookLine("museum and stole the", 91), new BookLine("shield in a daring raid. As", 92), new BookLine("a result, the current", 93), new BookLine("ruler, King Roald, put a", 94), new BookLine("1200 gold bounty (a", 95), new BookLine("massive sum of money in", 96), new BookLine("those days) on the return", 97)), new Page(new BookLine("of the shield, hoping that", 98), new BookLine("one of the culprits would", 99), new BookLine("betray his fellows out of", 100), new BookLine("greed.", 101))), new PageSet(new Page(new BookLine("This tactic did not work", 87), new BookLine("however, and the thieves", 88), new BookLine("who stole the shield have", 89), new BookLine("since gone on to become", 90), new BookLine("the most powerful crime", 91), new BookLine("gang in Varrock, despite", 92), new BookLine("making an enemy of the", 93), new BookLine("Royal Family many", 94), new BookLine("years ago.", 95)), new Page(new BookLine("The reward for the", 98), new BookLine("return of the shield still", 99), new BookLine("stands.", 100))) };

	/**
	 * Constructs a new {@code ShieldofArravBook} {@code Object}.
	 */
	public ShieldofArravBook(final Player player) {
		super(player, "The Shield of Arrav", ShieldofArrav.BOOK.getId(), PAGES);
	}

	/**
	 * Constructs a new {@code ShieldofArravBook} {@code Object}.
	 */
	public ShieldofArravBook() {
		/**
		 * empty.
		 */
	}

	@Override
	public void finish() {
		if (player.getQuestRepository().getQuest("Shield of Arrav").getStage() == 10) {
			player.getQuestRepository().getQuest("Shield of Arrav").setStage(20);
		}
	}

	@Override
	public void display(Page[] set) {
		player.lock();
		player.getInterfaceManager().open(getInterface());
		for (int i = 87; i < 112; i++) {
			player.getPacketDispatch().sendString("", getInterface().getId(), i);
		}
		player.getPacketDispatch().sendString(getName(), getInterface().getId(), 38);
		player.getPacketDispatch().sendString("", getInterface().getId(), 109);
		player.getPacketDispatch().sendString("", getInterface().getId(), 110);
		for (Page page : set) {
			for (BookLine line : page.getLines()) {
				player.getPacketDispatch().sendString(line.getMessage(), getInterface().getId(), line.getChild());
			}
		}
		player.getPacketDispatch().sendInterfaceConfig(getInterface().getId(), 84, index < 1 ? true : false);
		boolean lastPage = index == sets.length - 1;
		player.getPacketDispatch().sendInterfaceConfig(getInterface().getId(), 86, lastPage ? true : false);
		if (lastPage) {
			finish();
		}
		player.unlock();
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new ShieldofArravBook(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { ID };
	}
}
