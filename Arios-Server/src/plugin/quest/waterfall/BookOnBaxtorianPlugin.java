package plugin.quest.waterfall;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.book.Book;
import org.arios.game.content.dialogue.book.BookLine;
import org.arios.game.content.dialogue.book.Page;
import org.arios.game.content.dialogue.book.PageSet;
import org.arios.game.content.global.quest.impl.member.WaterFall;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the book on Baxtorian that nobody will read.
 * @author Splinter
 */
public final class BookOnBaxtorianPlugin extends Book {

	/**
	 * Represents the book id
	 */
	public static int ID = 183764;

	/**
	 * Represents the array of pages for this book.
	 */
	private static final PageSet[] PAGES = new PageSet[] { new PageSet(new Page(new BookLine("The missing relics", 87), new BookLine("", 88), new BookLine("    Many artefacts of", 89), new BookLine("elven history were lost", 90), new BookLine("after the fourth age. The", 91), new BookLine("greatest loss to our", 92), new BookLine("collections of elf history", 93), new BookLine("were the hidden treasures", 94), new BookLine("of Baxtorian.", 95), new BookLine("  Some believe these", 96), new BookLine("treasures are still", 97)), new Page(new BookLine("unclaimed, but it is more", 98), new BookLine("commonly believed that", 99), new BookLine("dwarf miners recovered", 100), new BookLine("the treasure at the", 101), new BookLine("beginning of the third", 102), new BookLine("age. Another great loss", 103), new BookLine("was Glarial's pebble, a key", 104), new BookLine("which allowed her family", 105), new BookLine("to visit her tomb.", 106), new BookLine("    The stone was taken", 107), new BookLine("by a gnome family over a", 108))), new PageSet(new Page(new BookLine("century ago. It is", 87), new BookLine("believed that the gnomes'", 88), new BookLine("descendent Golrie still has", 89), new BookLine("the stone hidden in the", 90), new BookLine("caves under the gnome", 91), new BookLine("tree village.", 92), new BookLine("", 93), new BookLine("The sonnet of Baxtorian", 94), new BookLine("", 95), new BookLine("The love between", 96), new BookLine("Baxtorian and Glarial was", 97)), new Page(new BookLine("said to have lasted over a", 98), new BookLine("century. They lived a", 99), new BookLine("peaceful life learning and", 100), new BookLine("teaching the laws of", 101), new BookLine("nature. When Baxtorian's", 102), new BookLine("kingdom was invaded by", 103), new BookLine("the dark forces he left on", 104), new BookLine("a five year campaign. He", 105), new BookLine("returned to find his", 106), new BookLine("people slaughtered and his", 107), new BookLine("wife taken by the enemy.", 108))), new PageSet(new Page(new BookLine("    After years of", 87), new BookLine("searching for his love he", 88), new BookLine("finally gave up and", 89), new BookLine("returned to the home he", 90), new BookLine("made for Glarial under", 91), new BookLine("the Baxtorian Waterfall.", 92), new BookLine("Once he entered he", 93), new BookLine("never returned. Only", 94), new BookLine("Glarial had the power to", 95), new BookLine("also enter the waterfall.", 96), new BookLine("  Since Baxtorian", 97)), new Page(new BookLine("entered no one but her", 98), new BookLine("can follow him in, it's as if", 99), new BookLine("the powers of nature still", 100), new BookLine("work to protect him.", 101), new BookLine("", 102), new BookLine("The power of nature", 103), new BookLine("", 104), new BookLine("    Glarial and Baxtorian", 105), new BookLine("were masters of nature.", 106), new BookLine("Trees would grow, hills", 107), new BookLine("form and rivers flood on", 108))), new PageSet(new Page(new BookLine("their command. Baxtorian", 87), new BookLine("in particular had", 88), new BookLine("perfected rune lore. It", 89), new BookLine("was said that he could", 90), new BookLine("uses the stones to control", 91), new BookLine("water, earth, and air.", 92), new BookLine("", 93), new BookLine("Ode to eternity", 94), new BookLine("", 95), new BookLine("A short piece written by", 96), new BookLine("Baxtorian himself.", 97)), new Page(new BookLine("", 98), new BookLine("What care I for this", 99), new BookLine("mortal coil,", 100), new BookLine("where treasures are yet", 101), new BookLine("so frail,", 102), new BookLine("for it is you that is my", 103), new BookLine("life blood,", 104), new BookLine("the wine to my holy grail", 105), new BookLine("and if I see the", 106), new BookLine("judgement day,", 107), new BookLine("when the gods fill the air", 108))), new PageSet(new Page(new BookLine("with dust,", 87), new BookLine("I'll happily choke on your", 88), new BookLine("memory,", 89), new BookLine("as my kingdom turns to", 90), new BookLine("rust", 91))),

	};

	/**
	 * Constructs a new {@code ShieldofArravBook} {@code Object}.
	 */
	public BookOnBaxtorianPlugin(final Player player) {
		super(player, "Book On Baxtorian", 292, PAGES);
	}

	/**
	 * Constructs a new {@code ShieldofArravBook} {@code Object}.
	 */
	public BookOnBaxtorianPlugin() {
		/**
		 * empty.
		 */
	}

	@Override
	public void finish() {
		if (player.getQuestRepository().getQuest(WaterFall.NAME).getStage() == 20) {
			player.getQuestRepository().getQuest(WaterFall.NAME).setStage(30);
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
		return new BookOnBaxtorianPlugin(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { ID };
	}
}
