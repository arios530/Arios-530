package plugin.command;

import org.arios.ServerConstants;
import org.arios.game.component.Component;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.entity.player.link.IronmanMode;
import org.arios.game.node.entity.player.link.RunScript;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;
import org.arios.plugin.Plugin;
import org.arios.tools.StringUtils;

import java.util.Arrays;

/**
 * Handles a player command.
 * @author Vexia
 */
public final class PlayerCommandPlugin extends CommandPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		link(CommandSet.PLAYER);
		return this;
	}

	@Override
	public boolean parse(Player player, String name, String[] arguments) {
		switch (name) {
		case "teleports":
		case "destinations":
			player.getInterfaceManager().close();
			player.getPacketDispatch().sendString("<u>Teleport destinations</u>", 239, 1);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ServerConstants.TELEPORT_DESTINATIONS.length; i++) {
				sb.append(ServerConstants.TELEPORT_DESTINATIONS[i][1]);
				if (i != ServerConstants.TELEPORT_DESTINATIONS.length - 1) {
					sb.append(", ");
				}
			}
			player.getPacketDispatch().sendString("<br>" + sb.toString(), 239, 2);
			player.getPacketDispatch().sendString("", 239, 3);
			player.getPacketDispatch().sendString("", 239, 4);
			player.getPacketDispatch().sendString("", 239, 5);
			player.getInterfaceManager().openComponent(239);
			return true;
		case "zoom":
			try {
				if (arguments.length > 1) {
					final int zoom = Integer.parseInt(arguments[1]);
					int[] zoom_levels = new int[]{
						0, 300, 600, 900, 1200, 1500
					};
					if (Arrays.stream(zoom_levels).anyMatch(zoom_level -> zoom == zoom_level)) {
						player.getGlobalData().setZoom(zoom);
					}
				}
			} catch (Exception e) {
				//We already send the error message client-sided.
			}
			return true;
		case "bank":// The players want OSRS content, let's give it to em
			if (!player.isAdmin()) {
				player.sendChat("Hey, everyone, I just tried to do something very silly!");
			}
			break;
		case "filter":
			if (player.getAttribute("chat_filter") == null) {
				player.sendMessage("You have toggled your game filter. You will no longer recieve <u>any</u> game messages.");
				player.sendMessage("Yell messages also count as game messages.");
				player.setAttribute("chat_filter", true);
			} else {
				player.removeAttribute("chat_filter");
				player.sendMessage("<col=004C00>You have toggled your game filter. You will now recieve game messages and yells.");
			}
			break;
		case "players":
			int count = Repository.getPlayers().size();
			int ironCount = 0;
			int ultIronCount = 0;
			for (Player p : Repository.getPlayers()) {
				if (p.getIronmanManager().checkRestriction(IronmanMode.ULTIMATE)) {
					ultIronCount++;
				}
				if (p.getIronmanManager().checkRestriction(IronmanMode.STANDARD)) {
					ironCount++;
				}
			}
			int regular = count - ironCount - ultIronCount;
			String proper = regular > 1 ? "s" : "";
			if (count == 1) {
				player.getPacketDispatch().sendMessage("There is 1 active player.");
			} else {
				player.getPacketDispatch().sendMessage("There are " + count + " active players: " + regular + " regular player" + proper + ", " + ironCount + " iron, and " + ultIronCount + " ultimate iron.");
			}
			return player.getRights() == Rights.REGULAR_PLAYER;
		case "yell":
			if (!player.isDonator() && !player.isAdmin()) {
				player.getPacketDispatch().sendMessages("Join clan chat \"" + GameWorld.getName() + "\" to talk globally, or become a donator to have access to", "this benefit.");
				return true;
			}
			if (player.getDetails().getPortal().isMuted()) {
				player.getPacketDispatch().sendMessage("You have been " + (player.getDetails().getPortal().isPermMute() ? "permanently" : "temporarily") + " muted due to breaking a rule.");
				return true;
			}
			if (player.getAttribute("yell-delay", 0.0) > GameWorld.getTicks()) {
				player.sendMessages("You have yelled in the last " + player.getDonatorType().getCooldown() + " seconds. Upgrade to an extreme donator to have", "unlimited yelling abilities.");
				return true;
			}
			String text = getArgumentLine(arguments);
			int length = text.length();
			if (length > 100) {
				length = 100;
			}
			if (text.length() >= 2) {
				if (Character.isLowerCase(text.charAt(0))) {
					text = Character.toUpperCase(text.charAt(0)) + text.substring(1, length);
				}
				text = getYellPrefix(player) + text + "</col>";
				for (Player p : Repository.getPlayers()) {
					if (p.isActive()) {
						p.getPacketDispatch().sendMessage(text);
					}
				}
				if (player.getDonatorType().getCooldown() > 0 && !player.isStaff()) {
					player.setAttribute("yell-delay", (int) GameWorld.getTicks() + (player.getDonatorType().getCooldown() / 0.6));
				}
			} else {
				player.getPacketDispatch().sendMessage("Your message was too short.");
			}
			return true;
		case "togglenews":
			player.getSavedData().getGlobalData().setDisableNews(!player.getSavedData().getGlobalData().isDisableNews());
			player.sendMessage("<col=FF0000>" + (player.getSavedData().getGlobalData().isDisableNews() ? "You will no longer see news notifications." : "You will now see news notifications."));
			return true;
		case "commands":
		case "command":
		case "commandlist":
			sendCommands(player);
			return true;
		case "quests":
			sendQuests(player);
			return true;
		case "reply":
                if (player.getInterfaceManager().isOpened()) {
                    player.sendMessage("Please finish what you're doing first.");
                    return true;
                }
                if (player.getAttributes().containsKey("replyTo")) {
                    player.setAttribute("keepDialogueAlive", true);
                    final String replyTo = (String) player.getAttribute("replyTo", "").replaceAll("_", " ");
                    player.setAttribute("runscript", new RunScript() { //the client does this by itself
                        @Override
                        public boolean handle() {
                            //CommunicationInfo.sendMessage(player, replyTo.toLowerCase(), (String) getValue());
                            //player.removeAttribute("keepDialogueAlive");
                            //System.out.println(player.getName());
                            return true;
                        }
                    });
                    player.getDialogueInterpreter().sendMessageInput(StringUtils.formatDisplayName(replyTo));
                } else {
                    player.getPacketDispatch().sendMessage("You have not recieved any recent messages to which you can reply.");
                }
                return true;
            case "xinter":
                player.getInterfaceManager().close();
                return true;
		}
		return false;
	}

	/**
	 * Sends commands.
	 * @param player the player.
	 */
	private void sendCommands(Player player) {
		if (player.getInterfaceManager().isOpened()) {
			player.sendMessage("Finish what you're currently doing.");
			return;
		}
		player.getInterfaceManager().close();
		player.getPacketDispatch().sendString("<u>" + GameWorld.getName() + " commands</u>", 239, 1);
		player.getPacketDispatch().sendString("::filter (completely toggles game messages)<br>::players (shows player count)<br>::doublexp (claims double xp)<br>::shop opens up a dialogue so you can use credits<br>::togglenews toggles the news broadcasts.", 239, 2);
		player.getPacketDispatch().sendString("", 239, 3);
		player.getPacketDispatch().sendString("", 239, 4);
		player.getPacketDispatch().sendString("", 239, 5);
		player.getInterfaceManager().openComponent(239);
	}

	/**
	 * Sends the quests.
	 * @param player the player.
	 */
	private void sendQuests(Player player) {
		player.getInterfaceManager().open(new Component(275));
		for (int i = 0; i < 257; i++) {
			player.getPacketDispatch().sendString("", 275, i);
		}
		String red = "<col=8A0808>";
		player.getPacketDispatch().sendString("<col=8A0808>" + "Available Quests" + "</col>", 275, 2);
		StringBuilder builder = new StringBuilder("");
		for (Quest q : player.getQuestRepository().getQuests().values()) {
			if (q.isInDevelopment()) {
				continue;
			}
			boolean completed = q.getState().equals(QuestState.COMPLETED);
			builder.append(completed ? red + "<str> " + q.getName() + " <br><br>" : red + " " + q.getName() + " <br><br>");
		}
		player.getPacketDispatch().sendString(builder.toString(), 275, 12);
	}

	/**
	 * Gets the yell prefix for the given player.
	 * @param player The player.
	 * @return The prefix used in yell.
	 */
	private static String getYellPrefix(Player player) {
		String color = "<col=800080>";
		StringBuilder sb = new StringBuilder("[");
		if (player.getDetails().getRights().isVisible(player)) {
			Rights right = player.getAttribute("visible_rank", player.getDetails().getRights());
			switch (right) {
			case ADMINISTRATOR:
				color = "<col=FFFF00>";
				break;
			case PLAYER_MODERATOR:
				color = "<col=81819B>";
				break;
			default:
				break;
			}
		}
		if (player.isDonator() && !player.isStaff()) {
			color = "<col=" + player.getDonatorType().getColor() + ">";
		}
		int icon = Rights.getChatIcon(player);
		if (icon > 0) {
			sb.append("<img=").append(icon - 1).append(">");
		}
		sb.append(color).append(player.getUsername()).append("</col>");
		sb.append("]: ").append(color);
		return sb.toString();
	}
}
