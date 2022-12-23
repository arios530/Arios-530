package org.arios.game.node.entity.player.link.spawn;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SavedData;
import org.arios.game.node.entity.player.link.SpellBookManager;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.game.world.repository.Repository;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ChildPositionContext;
import org.arios.net.packet.out.RepositionChild;
import org.arios.parser.item.ItemConfiguration;
import org.arios.parser.player.SavingModule;

/**
 * Handles the spawn data for the spawn world.
 * @author Vexia
 */
public class SpawnData implements SavingModule {

	/**
	 * The kill streak messages.
	 */
	private static final String[] KILLSTREAKS = new String[] { "rampage", "massacre", "frenzy", "annihilation", "decimation", "butchery", "extermination", "genocide", "carnage", "slaughter", "bloodshed", "assassination", "obliteration" };

	/**
	 * The current title.
	 */
	private PKTitle title = PKTitle.NOOB;

	/**
	 * The tutorial stage for the spawn server.
	 */
	private int tutorialStage;

	/**
	 * The pk points.
	 */
	private int pkPoints;

	/**
	 * The amount of kills.
	 */
	private int kills;

	/**
	 * The ammount of deaths.
	 */
	private int deaths;

	/**
	 * The skill streak.
	 */
	private int killStreak;

	/**
	 * Constructs a new {@Code SpawnData} {@Code Object}
	 */
	public SpawnData() {
		/**
		 * empty.
		 */
	}

	@Override
	public void save(ByteBuffer buffer) {
		SavedData.save(buffer, tutorialStage, 1);
		SavedData.save(buffer, pkPoints, 2);
		SavedData.save(buffer, kills, 3);
		SavedData.save(buffer, deaths, 4);
		SavedData.save(buffer, killStreak, 5);
		SavedData.save(buffer, title.ordinal(), 6);
		buffer.put((byte) 0);
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		while ((opcode = buffer.get()) != 0) {
			switch (opcode) {
			case 1:
				tutorialStage = buffer.getInt();
				break;
			case 2:
				pkPoints = buffer.getInt();
				break;
			case 3:
				kills = buffer.getInt();
				break;
			case 4:
				deaths = buffer.getInt();
				break;
			case 5:
				killStreak = buffer.getInt();
				break;
			case 6:
				title = PKTitle.values()[buffer.getInt()];
				break;
			}
		}
	}

	/**
	 * Draws the stats tab.
	 * @param player the player.
	 */
	public void drawStatsTab(Player player) {
		if (GameWorld.isEconomyWorld()) {
			return;
		}
		for (int i = 20; i < 153; i++) {
			if (i == 32) {
				continue;
			}
			player.getPacketDispatch().sendInterfaceConfig(274, i, true);
		}
		sendString(player, "Players online: " + Repository.getPlayers().size(), 10, null);
		sendString(player, "Information:", 13, null);
		sendString(player, "Arios credits: " + player.getDetails().getPortal().getShop().getCredits(), 14);
		sendString(player, "PK Points: " + player.getSavedData().getSpawnData().getPkPoints(), 15);
		sendString(player, "Arios Kills: " + getKills(), 16);
		sendString(player, "Arios Deaths: " + getDeaths(), 17);
		sendString(player, "Arios KDR: " + getKdr(), 18);
		sendString(player, "Title: " + "<col=" + getTitle().getTitleColor() + ">" + getTitle().getName(), 19);
		sendString(player, "PK Packages:", 32);
		PacketRepository.send(RepositionChild.class, new ChildPositionContext(player, 274, 60, 10, 298));
		PacketRepository.send(RepositionChild.class, new ChildPositionContext(player, 274, 33, 10, 300));
		PacketRepository.send(RepositionChild.class, new ChildPositionContext(player, 274, 34, 11, 314));
		int index = 0;
		for (int i = 0; i < PKPackage.values().length; i++) {
			if (21 + i == 32) {
				sendString(player, PKPackage.values()[i].getName(), 33);
				index += 2;
				continue;
			}
			sendString(player, PKPackage.values()[i].getName(), 21 + index);
			index++;
		}
		PacketRepository.send(RepositionChild.class, new ChildPositionContext(player, 274, 32, 10, 119));
	}

	/**
	 * Sends the string.
	 * @param player the player.
	 * @param string the string.
	 * @param child the child.
	 * @param color the color.
	 */
	private void sendString(Player player, String string, int child, String color) {
		player.getPacketDispatch().sendInterfaceConfig(274, child, false);
		player.getPacketDispatch().sendString((color != null ? color : "") + string, 274, child);
	}

	/**
	 * Sends the string.
	 * @param player the player.
	 * @param string the string.
	 * @param child the child.
	 */
	private void sendString(Player player, String string, int child) {
		sendString(player, string, child, "<col=FE9A2E>");
	}

	/**
	 * Shouts spawn info of the player.
	 * @param p the player.
	 * @param button the button.
	 */
	public void handleButton(Player p, int button) {
		if (GameWorld.isEconomyWorld()) {
			return;
		}
		if (button == 18) {
			p.sendChat("<col=FF0000>My KDR is: " + p.getSavedData().getSpawnData().getKdr() + "!");
		} else if (button == 16) {
			p.sendChat("<col=FF0000>I have killed " + p.getSavedData().getSpawnData().getKills() + " Arios player" + (p.getSavedData().getSpawnData().getKills() != 1 ? "s" : "") + "!");
		} else if (button == 17) {
			p.sendChat("<col=FF0000>I have been killed " + p.getSavedData().getSpawnData().getDeaths() + " time" + (p.getSavedData().getSpawnData().getDeaths() != 1 ? "s" : "") + "!");
		} else if (button == 15) {
			p.sendChat("<col=FF0000>PK Points: " + p.getSavedData().getSpawnData().getPkPoints() + "!");
		} else if (button == 14) {
			p.sendChat("<col=FF0000>Arios Credits: " + p.getDetails().getPortal().getShop().getCredits() + "!");
		} else if (button == 19) {
			PKTitle[] titles = PKTitle.getTitles(p);
			PKTitle t = title;
			if (title.ordinal() >= titles.length - 1) {
				t = titles[0];
			} else {
				t = titles[title.ordinal() + 1];
			}
			this.title = t;
			p.getAppearance().sync();
			drawStatsTab(p);
		} else if (button >= 21) {
			int index = button - 21;
			if (button > 32) {
				index--;
			}
			Player player = p;
			if (!player.canSpawn()) {
				return;
			}
			if (index < PKPackage.values().length) {
				PKPackage pkPackage = PKPackage.values()[index];
				if (pkPackage != null) {
					if (pkPackage.getType() == 0 && (!player.getBank().hasSpaceFor(player.getEquipment()) || !player.getBank().hasSpaceFor(player.getInventory()))) {
						player.sendMessage("You don't have enough bank space to do that.");
						return;
					}
					if (pkPackage == PKPackage.PURE) {
						p.getSkills().setLevel(Skills.DEFENCE, 1);
						p.getSkills().setStaticLevel(Skills.DEFENCE, 1);
						p.getSkills().setLevel(Skills.PRAYER, 52);
						p.getSkills().setStaticLevel(Skills.PRAYER, 52);
					} else if (pkPackage.getType() == 0) {
						for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
							player.getSkills().setLevel(i, 99);
							player.getSkills().setStaticLevel(i, 99);
						}
					}
					if (pkPackage.getType() == 0) {
						player.getBank().addAll(player.getEquipment());
						player.getBank().addAll(player.getInventory());
						player.getEquipment().clear();
						player.getInventory().clear();
						for (Item item : pkPackage.getItems()) {
							if ((item.getDefinition().hasAction("wear") || item.getDefinition().hasAction("wield")) && player.getEquipment().get(item.getDefinition().getConfiguration(ItemConfiguration.EQUIP_SLOT, -1)) == null) {
								player.getEquipment().add(item, true, false);
							} else {
								player.getInventory().add(item);
							}
						}
					} else {
						p.getInventory().add(pkPackage.getItems());
					}
					if (pkPackage.getSpellBook() != -1) {
						player.getSpellBookManager().setSpellBook(SpellBookManager.SpellBook.values()[pkPackage.getSpellBook()]);
						player.getSpellBookManager().update(player);
					}
					if (pkPackage == PKPackage.VENGEANCE_RUNE || pkPackage == PKPackage.BARRAGE_RUNE || pkPackage == PKPackage.ENTANGE_RUNE) {
						player.getSpellBookManager().setSpellBook(SpellBook.LUNAR);
						player.getSpellBookManager().update(player);
					}
					p.getSkills().updateCombatLevel();
					p.getAppearance().sync();
					p.sendMessage("You opened the " + pkPackage.getName() + " package!");
				}
			}
		}
	}

	/**
	 * Called when the player killer has killed another player.
	 * @param killer
	 * @param killed
	 */
	public void onDeath(Player killer, Player killed) {
		if (GameWorld.isEconomyWorld()) {
			return;
		}
		if (killer.getDetails().getInfo().getIp().equals(killed.getDetails().getInfo().getIp()) || killed.getDetails().getInfo().getMac().equals(killer.getDetails().getInfo().getMac())) {
			killer.sendMessage("You can't kill someone from your own computer address.");
			return;
		}
		SpawnData killedInfo = killed.getSavedData().getSpawnData();
		int increment = getStreakPoints(killer);
		if (killedInfo.getKillStreak() > 4) {
			increment += getStreakPoints(killed);
			Repository.sendNews(killer.getUsername() + " has ended " + killed.getUsername() + "'s killstreak of " + killedInfo.getKillStreak() + "!");
		}
		incrementKills();
		incrementStreak();
		killedInfo.setKillStreak(0);
		killedInfo.incrementDeaths();
		incrementPkPoints(increment);
		PKTitle.checkTitle(killer);
		drawStatsTab(killer);
		killedInfo.drawStatsTab(killed);
		killer.sendMessage("<col=FF0000>You have killed " + killed.getUsername() + "! Your Arios PK Points have increased by " + increment + ".");
		killer.sendMessage("<col=FF0000>You are now on a " + killStreak + " killstreak!");
		if (killStreak > 4) {
			Repository.sendNews(getStreakMessage(killer, killed));
		}
		PKScoreBoard.check(killer);
	}

	/**
	 * Gets the streak points.
	 * @param killed the killed player.
	 * @return the points.
	 */
	private int getStreakPoints(Player killed) {
		int streak = killed.getSavedData().getSpawnData().getKillStreak();
		int points = 1;
		if (streak > 3) {
			return streak;
		}
		return points;
	}

	/**
	 * Gets a kill streak message.
	 * @param killer the killer.
	 * @param killed the killed.
	 * @return the kill streak message.no1 readd him
	 */
	private String getStreakMessage(Player killer, Player killed) {
		int streak = getKillStreak();
		String message = "killstreak";
		if (streak > 5) {
			int index = streak - 6;
			if (index > KILLSTREAKS.length - 1) {
				return killer.getUsername() + " is unstoppable! " + (killer.getAppearance().isMale() ? "He" : "She") + " is on a killstreak of " + streak + "!";
			} else {
				message = "kill " + KILLSTREAKS[index];
			}
		}
		return killer.getUsername() + " is on a " + streak + " " + message + "! Kill " + (killer.getAppearance().isMale() ? "him" : "her") + " to gain " + streak + " PKP!";
	}

	/**
	 * Increments the kill streak.
	 */
	public void incrementStreak() {
		killStreak++;
	}

	/**
	 * Increments the deaths.
	 */
	public void incrementDeaths() {
		deaths++;
	}

	/**
	 * Increments the kills.
	 */
	public void incrementKills() {
		kills++;
	}

	/**
	 * Increments the pk points.
	 * @param increment the increment.
	 */
	public void incrementPkPoints(int increment) {
		setPkPoints(getPkPoints() + increment);
	}

	/**
	 * Checks if the tutorial is completed.
	 * @return {@code True} if so.
	 */
	public boolean hasCompletedTutorial() {
		return getTutorialStage() > 2;
	}

	/**
	 * Increments the tutorial stage.
	 */
	public void incrementTutorialStage() {
		setTutorialStage(getTutorialStage() + 1);
	}

	/**
	 * Gets the kdr.
	 * @return the kdr.
	 */
	public String getKdr() {
		return new DecimalFormat().format(deaths == 0 ? kills : (double) ((double) kills / (double) deaths));
	}

	/**
	 * Gets the tutorialStage.
	 * @return the tutorialStage
	 */
	public int getTutorialStage() {
		return tutorialStage;
	}

	/**
	 * Sets the batutorialStage.
	 * @param tutorialStage the tutorialStage to set.
	 */
	public void setTutorialStage(int tutorialStage) {
		this.tutorialStage = tutorialStage;
	}

	/**
	 * Gets the pkPoints.
	 * @return the pkPoints
	 */
	public int getPkPoints() {
		return pkPoints;
	}

	/**
	 * Sets the bapkPoints.
	 * @param pkPoints the pkPoints to set.
	 */
	public void setPkPoints(int pkPoints) {
		this.pkPoints = pkPoints;
	}

	/**
	 * Gets the title.
	 * @return the title
	 */
	public PKTitle getTitle() {
		return title;
	}

	/**
	 * Sets the batitle.
	 * @param title the title to set.
	 */
	public void setTitle(PKTitle title) {
		this.title = title;
	}

	/**
	 * Gets the kills.
	 * @return the kills
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * Sets the bakills.
	 * @param kills the kills to set.
	 */
	public void setKills(int kills) {
		this.kills = kills;
	}

	/**
	 * Gets the deaths.
	 * @return the deaths
	 */
	public int getDeaths() {
		return deaths;
	}

	/**
	 * Decrements the points.
	 */
	public void decrementPoints(int decrement) {
		pkPoints -= decrement;
	}

	/**
	 * Sets the badeaths.
	 * @param deaths the deaths to set.
	 */
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	/**
	 * Gets the killStreak.
	 * @return the killStreak
	 */
	public int getKillStreak() {
		return killStreak;
	}

	/**
	 * Sets the bakillStreak.
	 * @param killStreak the killStreak to set.
	 */
	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

}
