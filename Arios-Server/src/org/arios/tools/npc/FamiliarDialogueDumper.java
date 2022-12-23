package org.arios.tools.npc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.def.impl.NPCDefinition;
import org.arios.tools.impl.URLParser;

/**
 * Dumps familiar dialogues.
 * @author Vexia
 */
public class FamiliarDialogueDumper {

	/**
	 * The base url.
	 */
	private static final String BASE_URL = "http://runescape.wikia.com";

	/**
	 * The url to dump from.
	 */
	private static final String URL = "http://runescape.wikia.com/wiki/Category:Familiar_dialogue";

	/**
	 * The dialogues to parse.
	 */
	private static final List<DialoguePage> dialogues = new ArrayList<>();

	/**
	 * The successes.
	 */
	private static int success;

	/**
	 * Method used to run the program.
	 * @param args the arguments.
	 * @throws Throwable the throwable.
	 */
	public static void main(String... args) throws Throwable {
		Cache.init(ServerConstants.CACHE_PATH);
		List<String> list = new ArrayList<>();
		list.add(URL);
		final URLParser parser = new URLParser(list, 0);
		parser.parse();
		list = parser.getData().get(URL);
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (s.contains("/dialogue") && !s.startsWith("<span")) {
				dialogues.add(DialoguePage.parseBasic(s));
			}
		}
		DialoguePage page = null;
		for (int i = 8; i < dialogues.size(); i++) {
			page = dialogues.get(i);
			page.dump();
		}
		// dialogues.get(8).dump();
		System.out.println("Completed " + success + " out of " + dialogues.size() + " dialogue dumps.");
		System.exit(-1);
	}

	/**
	 * The dialogue page.
	 * @author Vexia
	 */
	public static class DialoguePage {

		/**
		 * The basic line.
		 */
		private final String baseLine;

		/**
		 * The dialogue url.
		 */
		private final String url;

		/**
		 * The title.
		 */
		private final String title;

		/**
		 * The conversations to create.
		 */
		private List<Conversation> convos = new ArrayList<>();

		/**
		 * Constructs a new {@code DialoguePage} {@code Object}.
		 * @param baseLine the base line.
		 * @param url the url.
		 * @param title the title.
		 */
		public DialoguePage(String baseLine, String url, String title) {
			this.baseLine = baseLine;
			this.url = url;
			this.title = title;
		}

		/**
		 * Dumps the url.
		 */
		public void dump() {
			System.out.println("Dumping " + formatUrl());
			List<String> list = new ArrayList<>();
			list.add(formatUrl());
			final URLParser parser = new URLParser(list, 0);
			parser.parse();
			list = parser.getData().get(formatUrl());
			Conversation convo = null;
			int convoId = 0;
			for (String s : list) {
				if (s.startsWith("<ul><li><i>") && s.contains("Conversation")) {
					if (convo != null) {
						convos.add(convo);
					}
					convo = new Conversation(convoId++);
				}
				if (convo != null) {
					if (s.startsWith("<ul><li><b>") || s.startsWith("</li><li><b>")) {
						convo.parseLine(s);
					}
				}
			}
			if (convos.size() > 1) {
				convos.add(convo);
			}
			dumpConvos();
		}

		/**
		 * Dumps the convos in the dialogue.
		 */
		private void dumpConvos() {
			if (convos.size() < 1) {
				return;
			}
			try {
				File file = new File("./scripts/dialogue/test/" + getFileName());
				if (!file.exists()) {
					file.createNewFile();
				}
				BufferedWriter writter = new BufferedWriter(new FileWriter(file));
				writter.append(getHeader());
				writter.newLine();
				writter.append("#random = random(" + convos.size() + ")");
				writter.newLine();
				writter.newLine();
				for (Conversation convo : convos) {
					writter.append("$random is " + convo.getConvoId() + " {");
					writter.newLine();
					convo.output(writter);
					writter.append("}");
					writter.newLine();
					writter.newLine();
				}
				writter.flush();
				System.out.println(file.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			success++;
		}

		/**
		 * Parses a basic url string.
		 * @param base the base.
		 * @return the instance.
		 */
		public static DialoguePage parseBasic(final String base) {
			String[] tokens = base.split("<a href=\"");
			String url = cutUrl(tokens[1]);
			String title = cutTitle(tokens[1]);
			return new DialoguePage(base, url, title);
		}

		/**
		 * Cuts the url.
		 * @param line the line.
		 * @return the url.
		 */
		private static String cutUrl(String line) {
			return line.split("title=\"")[0].replace("\"", "").trim();
		}

		/**
		 * Cuts the title.
		 * @param line the line.
		 * @return the title.
		 */
		private static String cutTitle(String line) {
			String base = line.split("title=\"")[0];
			return base.replace("/wiki/", "").replace("/dialogue\"", "").trim();
		}

		/**
		 * Gets the file name.
		 * @return the name.
		 */
		private String getFileName() {
			return title.toLowerCase() + ".asc";
		}

		/**
		 * Gets the header.
		 * @return the header.
		 */
		private String getHeader() {
			int[] ids = getNpcIds();
			StringBuilder builder = new StringBuilder("@dialogue npc:");
			if (ids[1] == 0) {
				builder.append(ids[0]);
			} else {
				for (int i = 0; i < ids.length; i++) {
					builder.append(ids[i] + (i != ids.length - 1 ? "," : ""));
				}
			}
			return builder.toString();
		}

		/**
		 * Gets the npcs.
		 * @return the npc ids.
		 */
		public int[] getNpcIds() {
			final String name = getTitle().replace("_", " ").trim();
			int[] ids = new int[2];
			NPCDefinition def = null;
			int k = 0;
			for (int i = 0; i < Cache.getNPCDefinitionsSize(); i++) {
				def = NPCDefinition.forId(i);
				if (def.getName().contains(name)) {
					if (k > 1) {
						break;
					}
					ids[k] = def.getId();
					k++;
				}
			}
			return ids;
		}

		/**
		 * Gets the baseLine.
		 * @return The baseLine.
		 */
		public String getBaseLine() {
			return baseLine;
		}

		/**
		 * Formats the url.
		 * @return the url.
		 */
		public String formatUrl() {
			return BASE_URL + getUrl();
		}

		/**
		 * Gets the url.
		 * @return The url.
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * Gets the title.
		 * @return The title.
		 */
		public String getTitle() {
			return title;
		}

		@Override
		public String toString() {
			return "DialoguePage [baseLine=" + baseLine + ", url=" + url + ", title=" + title + "]";
		}

	}

	/**
	 * Represents a conversation.
	 * @author Vexia
	 */
	public static class Conversation {

		/**
		 * The convo id.
		 */
		private final int convoId;

		/**
		 * The dialogues in the cnvo.
		 */
		private final List<DialogueSet> dialogues = new ArrayList<>();

		/**
		 * Constructs a new {@code Conversation} {@code Object}.
		 * @param convoId the convo id.
		 */
		public Conversation(int convoId) {
			this.convoId = convoId;
		}

		/**
		 * Parsing a convo line.
		 * @param line the line.
		 */
		public void parseLine(String l) {
			final String dialLine = l.split("<b>")[1];
			try {
				final String name = splitDialogue(dialLine)[0].trim();
				final String line = splitDialogue(dialLine)[1].trim();
				dialogues.add(DialogueSet.parse(name, line.trim()));
			} catch (ArrayIndexOutOfBoundsException exception) {
				System.err.println("Error with dialogue - " + getConvoId() + ", line=" + dialLine);
				System.err.println("full line=" + l);
			}
		}

		/**
		 * Splits the dialogue into the name & the line.
		 * @param dial the dial.
		 * @return
		 */
		private String[] splitDialogue(String dial) {
			return dial.split("</b>:");
		}

		/**
		 * Gets the convoId.
		 * @return The convoId.
		 */
		public int getConvoId() {
			return convoId;
		}

		/**
		 * Gets the dialogues.
		 * @return The dialogues.
		 */
		public List<DialogueSet> getDialogues() {
			return dialogues;
		}

		/**
		 * Outputs the convo.
		 * @param writter the writer.
		 */
		public void output(BufferedWriter writter) {
			for (String s : formatDialogue()) {
				try {
					writter.append(s.trim());
					writter.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Format the dialogue.
		 * @return the dialogue.
		 */
		public String[] formatDialogue() {
			String[] lines = new String[dialogues.size()];
			for (int i = 0; i < lines.length; i++) {
				lines[i] = dialogues.get(i).toString();
			}
			return lines;
		}

		/**
		 * A dialogue set.
		 * @author Vexia
		 */
		public static class DialogueSet {

			/**
			 * The speaker.
			 */
			private final String speaker;

			/**
			 * The lines.
			 */
			private final String[] lines;

			/**
			 * Constructs a new {@code DialogueLine} {@code Object}.
			 * @param speaker the speaker.
			 * @param lines the line.
			 */
			public DialogueSet(String speaker, String[] lines) {
				this.speaker = speaker;
				this.lines = lines;
			}

			/**
			 * Gets the string builder of lines.
			 * @return the lines.
			 */
			public StringBuilder getStringBuilder() {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < lines.length; i++) {
					String line = lines[i];
					String l = "\"" + line + "\"" + (i == lines.length - 1 ? "" : ",").trim();
					if (l.charAt(1) == ' ') {
						StringBuilder b = new StringBuilder(l);
						b.delete(0, 1);
						l = b.toString().trim();
					}
					builder.append(l);
				}
				return builder;
			}

			/**
			 * Parses a dialogue set.
			 * @param speaker the speaker.
			 * @param line the line.
			 * @return the set.
			 */
			public static DialogueSet parse(String speaker, String line) {
				return new DialogueSet(speaker, splitLines(line));
			}

			/**
			 * Splits the lines into two if needed.
			 * @param line the line.
			 * @return the lines.
			 */
			private static String[] splitLines(String line) {
				final StringBuilder builder = new StringBuilder();
				final StringBuilder second = new StringBuilder();
				boolean useSecond = false;
				if (line.length() > 31) {
					String[] tokens = line.split(" ");
					for (String s : tokens) {
						s = " " + s;
						if (useSecond || builder.length() + s.length() > 86) {
							second.append(s);
							useSecond = true;
						} else {
							builder.append(s);
						}
					}
				}
				if (builder.length() == 0) {
					builder.append(line);
				}
				if (second.length() == 0) {
					return new String[] { builder.toString() };
				}
				return new String[] { builder.toString(), second.toString() };
			}

			/**
			 * Gets the dialogue type.
			 * @return the string.
			 */
			public String getType() {
				return (isPlayer() ? "player" : "npc");
			}

			/**
			 * Checks if it is a player speaker.
			 * @return {@code True} if so.
			 */
			public boolean isPlayer() {
				return speaker.equals("Player");
			}

			/**
			 * Gets the speaker.
			 * @return The speaker.
			 */
			public String getSpeaker() {
				return speaker;
			}

			/**
			 * Gets the lines.
			 * @return The lines.
			 */
			public String[] getLines() {
				return lines;
			}

			@Override
			public String toString() {
				StringBuilder sb = getStringBuilder();
				return getType() + "(" + sb.toString().trim() + ")";
			}
		}
	}
}