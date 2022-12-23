package plugin.command;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.entity.player.link.TeleportManager.TeleportType;
import org.arios.game.node.item.Item;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.world.map.Location;
import org.arios.game.world.repository.Repository;
import org.arios.plugin.Plugin;
import org.arios.tools.StringUtils;
import org.arios.tools.npc.TestStats;

/**
 * The commands available during beta stage.
 * @author Emperor
 */
public final class BetaCommandPlugin extends CommandPlugin {

	@Override
	public boolean parse(Player player, String name, String[] args) {
		int id, amount;
		Player p;
		switch (name) {
		case "resetquest":
		case "reset_quest":
			if (args.length < 2) {
				player.debug("syntax error: name");
				return true;
			}
			name = "";
			for (int i = 1; i < args.length; i++) {
				name += (i == 1 ? "" : " ") + args[i];
			}
			name = StringUtils.formatDisplayName(name);
			if (player.getQuestRepository().getQuest(name) == null) {
				player.debug("error: invalid quest - " + name);
				return true;
			}
			player.getQuestRepository().getQuest(name).setStage(0);
			player.getQuestRepository().getQuest(name).setState(QuestState.NOT_STARTED);
			player.getQuestRepository().update(player);
			return true;
		case "clearquest":
			for (Quest quest : player.getQuestRepository().getQuests().values()) {
				quest.setStage(0);
				quest.setState(QuestState.NOT_STARTED);
			}
			player.getConfigManager().set(222, 0, true);
			player.getQuestRepository().update(player);
			return true;
		case "allquest":
			for (Quest quest : player.getQuestRepository().getQuests().values()) {
				quest.finish();
			}
			return true;
		case "setquest":
		case "setoquest":
			if (args.length < 2) {
				player.debug("syntax error: name");
				return true;
			}
			Player m = name.equals("setoquest") ? getTarget(args[args.length - 1]) : player;
			if (m == null || !m.isActive()) {
				player.sendMessage("Error! " + args[3] + " in invalid.");
				return true;
			}
			name = "";
			for (int i = 1; i < args.length - 1; i++) {
				name += (i == 1 ? "" : " ") + args[i];
			}
			Quest quest = null;
			for (Quest q : m.getQuestRepository().getQuests().values()) {
				if (q.getName().toLowerCase().equals(name.toLowerCase())) {
					quest = q;
					break;
				}
			}
			if (quest == null) {
				player.debug("error: invalid quest - " + name);
				return true;
			}
			int stage = toInteger(args[args.length - 1]);
			quest.setStage(stage);
			quest.setState(QuestState.STARTED);
			if (stage == 100) {
				quest.setState(QuestState.COMPLETED);
			}
			m.getPacketDispatch().sendMessage("quest=" + name + ", new stage=" + stage);
			m.getQuestRepository().update(player);
			break;
		case "empty":
			player.getInventory().clear();
			return true;
		case "itemn":
			if (args.length < 2) {
				player.debug("syntax error: item-name (optional) amount");
				return true;
			}
			String params = "";
			for (int i = 1; i < args.length; i++) {
				params += i == args.length - 1 ? args[i] : args[i] + " ";
			}
			for (int i = 0; i < ItemDefinition.getDefinitions().size(); i++) {
				ItemDefinition def1 = ItemDefinition.forId(i);
				if (def1 != null && def1.getName().equalsIgnoreCase(params.toLowerCase())) {
					player.getInventory().add(new Item(i, 1));
					player.getPacketDispatch().sendMessage("[item=" + def1.getId() + ", " + def1.getName() + "].");
					break;
				}
			}
			return true;
		case "item":
			id = args.length > 1 ? toInteger(args[1]) : 0;
			amount = args.length > 2 ? toInteger(args[2]) : 1;
			if (id > Cache.getItemDefinitionsSize()) {
				return true;
			}
			Item item = new Item(id, amount);
			int max = player.getInventory().getMaximumAdd(item);
			if (amount > max) {
				amount = max;
			}
			item.setAmount(amount);
			player.getInventory().add(item);
			return true;
		case "master":
			if (player.getDetails().getRights() != Rights.ADMINISTRATOR) {
				if (player.inCombat() || player.getLocks().isInteractionLocked() || player.getSkullManager().isWilderness()) {
					player.getPacketDispatch().sendMessage("You can't do that right now.");
					return true;
				}
			}
			for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
				player.getSkills().setLevel(i, 99);
				player.getSkills().setStaticLevel(i, 99);
			}
			player.getSkills().updateCombatLevel();
			player.getAppearance().sync();
			return true;
		case "runes":
			for (int i = 554; i < 567; i++) {
				player.getInventory().add(new Item(i, 50000));
			}
			player.getInventory().add(new Item(9075, 50000));
			return true;
		case "skill":
		case "oskill":
			if (player.getDetails().getRights() != Rights.ADMINISTRATOR) {
				if (player.inCombat() || player.getLocks().isInteractionLocked() || player.getSkullManager().isWilderness()) {
					player.getPacketDispatch().sendMessage("You can't do that right now.");
					return true;
				}
			}
			if (args.length < 3) {
				player.getPacketDispatch().sendMessage("Use as ::skill skillname/id level.");
				return true;
			}
			int skillId = -1;
			if (Character.isDigit(args[1].charAt(0))) {
				skillId = toInteger(args[1]);
			} else {
				for (id = 0; id < Skills.SKILL_NAME.length; id++) {
					String skill = Skills.SKILL_NAME[id];
					if (args[1].equals(skill.toLowerCase())) {
						skillId = id;
						break;
					}
				}
			}
			if (skillId < 0) {
				player.getPacketDispatch().sendMessage("Use as ::skill skillname/id level.");
				return true;
			}
			int level = Math.abs(toInteger(args[2]));
			if (level > 99) {
				level = 99;
			}
			p = name.equals("oskill") && args.length > 3 ? Repository.getPlayerByDisplay(args[3]) : player;
			if (p == null) {
				player.getPacketDispatch().sendMessage("Unable to set level for " + args[3] + ".");
				return true;
			}
			p.getSkills().setLevel(skillId, level);
			p.getSkills().setStaticLevel(skillId, level);
			p.getSkills().updateCombatLevel();
			p.getAppearance().sync();
			player.getPacketDispatch().sendMessage("Set " + p.getName() + "'s " + Skills.SKILL_NAME[skillId] + " level to " + args[2] + ".");
			return true;
		case "copy":
			Player target = Repository.getPlayerByDisplay(args[1]);
			if (target != null) {
				player.getInventory().copy(target.getInventory());
				player.getInventory().refresh();
				player.getSkills().copy(target.getSkills());
				player.getSkills().configure();
				player.getEquipment().copy(target.getEquipment());
				player.getEquipment().refresh();
				player.getAppearance().sync();
			}
			return true;
		case "to":
			if (player.getDetails().getRights() != Rights.ADMINISTRATOR) {
				if (player.inCombat() || player.getLocks().isTeleportLocked()) {
					player.getPacketDispatch().sendMessage("You can't teleport right now.");
					return true;
				}
			}
			Location destination = null;
			String place = getArgumentLine(args);
			for (Object[] data : ServerConstants.TELEPORT_DESTINATIONS) {
				for (int i = 1; i < data.length; i++) {
					if (place.equals(data[i])) {
						destination = (Location) data[0];
						break;
					}
				}
			}
			if (destination != null) {
				player.getTeleporter().send(destination, TeleportType.NORMAL);
			} else {
				player.getPacketDispatch().sendMessage("Could not locate teleport destination [name=" + place + "]!");
			}
			return true;
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
		case "maxmag":
			TestStats.setMaxedMagicAcc(player);
			return true;
		case "maxstr":
			TestStats.setMaxedMeleeStr(player);
			return true;
		}
		return false;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		link(CommandSet.BETA, CommandSet.ADMINISTRATOR);
		return this;
	}

}