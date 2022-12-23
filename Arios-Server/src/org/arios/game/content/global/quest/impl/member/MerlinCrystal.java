package org.arios.game.content.global.quest.impl.member;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the merlin crystal quest.
 * @author Splinter
 */
public final class MerlinCrystal extends Quest {

	/**
	 * The name of the quest.
	 */
	public static final String NAME = "Merlin's Crystal";

	/**
	 * Constructs a new {@code MerlinCrystal} {@code Object}.
	 * @param player the player.
	 */
	public MerlinCrystal(Player player) {
		super(player);
	}

	/**
	 * Quest stages: 0 - not started 10 - spoke to king arthur 20 - spoke to sir
	 * kay/gawain 30 - after speaking to lancelot 40 - after speaking to arnhein
	 * 50 - after defeating sir mordred 60 - after talking to lady of the lake
	 * 70 - obtained excalibur 80 - after learning the incantation 90 - after
	 * defeating the spirit 95 - freeing merlin (not done) 100 - quest complete
	 */
	@Override
	public void update() { // <col=FF0000>QUEST COMPLETE!</col>
		super.clear();
		switch (stage) {
		case 0:// not started
			line("<blue>I can start this quest by speaking to <red>King Arthur<blue> at<n> <red>Camelot Castle<blue>, just <red>North West of Catherby<n><blue>I must be able to defeat a <red>level 37 enemy", 11);
			break;
		case 10:// after talking to arthur
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<br><br><br><blue> I should ask the <red>other Knights<blue> if they have any <red>advice<blue> for<n><blue>me on how I should go about doing this.", 11);
			break;
		case 20:// upon talking to kay/gawain
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><blue>Gawain told me it was the work of <red>Morgan Le Faye<blue>.", 11);
			break;
		case 30:// after talking to lancelot
		case 40:// after talking to arnheim
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><blue>There might be a way to enter with a <red>delivery by sea...", 11);
			break;
		case 50:// defeated mordred
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><str>There might be a way to enter with a delivery by sea...<n><blue>I have broken into <red>Keep Le Faye<blue> and slain <red>Sir Mordred<blue>.<n>" + "<red>Morgan Le Faye<blue> disclosed the secret of how to free <red>Merlin<blue>.<n><blue> I'll need the sword <red>Excalibur<blue> and a <red>lit black candle<blue> first." + "<n><blue>According to Morgan, the <red>Lady of the Lake<blue> has the<n><red>sword<blue> that can be used to free Merlin.", 11);
			break;
		case 60:// talking to lady of the lake
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><str>There might be a way to enter with a delivery by sea...<n><str>I have broken into Keep Le Faye and slain Sir Mordred.<n>" + "<str>Morgan Le Faye disclosed the secret of how to free Merlin.<n><str>I'll need the sword Excalibur and a lit black candle.<n>" + "<red>The Lady of the Lake<blue> told me she had the <red>Excalibur<n><blue> but I'd have to meet her in the <red>jewellery store" + "<blue> in <red>Port Sarim<n><blue>before she'd give it to me.", 11);
			break;
		case 70:// after actually obtaining excalibur
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><str>There might be a way to enter with a delivery by sea...<n><str>I have broken into Keep Le Faye and slain Sir Mordred.<n>" + "<str>Morgan Le Faye disclosed the secret of how to free Merlin.<n><str>I'll need the sword Excalibur and a lit black candle.<n>" + "<str>The Lady of the Lake told me she had the Excalibur<n> <str>but I'd have to visit the jewellery store" + " in Port Sarim first.</str><n>" + "<blue>I have the sword <red>Excalibur<blue> and can free <red>Merlin<blue> from the crystal.<n>" + "<blue>I must now memorize an incantation inscribed on a <red>Chaos altar<n><blue>that is located somewhere in the world in order to banish<n>" + "<blue>the spirit." + "<n><blue>I will also need to find some <red>bat bones<blue> and drop them<n><blue>on the magical symbol to the <red>North East of Camelot<blue>.<n>" + "<blue>after I have learned the incantation.", 11);
			break;
		case 80:// after reading the incantation
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><str>There might be a way to enter with a delivery by sea...<n><str>I have broken into Keep Le Faye and slain Sir Mordred.<n>" + "<str>Morgan Le Faye disclosed the secret of how to free Merlin.<n><str>I'll need the sword Excalibur and a lit black candle.<n>" + "<str>The Lady of the Lake told me she had the Excalibur<n> <str>but I'd have to visit the jewellery store" + " in Port Sarim first.<n>" + "<str>I have the sword Excalibur and can free Merlin from the crystal.<n>" + "<str>I must now memorize an incantation inscribed on a Chaos altar<n><str>that is located somewhere in the world in order to banish<n>" + "<str>the spirit.<n>" + "<blue> I managed to find the <red>Chaos Altar<blue> that Morgan described.<n>" + "<blue>The incantation is 'Snarthon Candtrick Termanto'." + "<n><blue>I now need to find some <red>bat bones<blue> and drop them<n><blue>on the magical symbol to the <red>North East of Camelot<blue>.", 11);
			break;
		case 90:
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><str>There might be a way to enter with a delivery by sea...<n><str>I have broken into Keep Le Faye and slain Sir Mordred.<n>" + "<str>Morgan Le Faye disclosed the secret of how to free Merlin.<n><str>I'll need the sword Excalibur and a lit black candle.<n>" + "<str>The Lady of the Lake told me she had the Excalibur<n> <str>but I'd have to visit the jewellery store" + " in Port Sarim first.<n>" + "<str>I have the sword Excalibur and can free Merlin from the crystal.<n>" + "<str>I must now memorize an incantation inscribed on a Chaos altar<n><str>that is located somewhere in the world in order to banish<n>" + "<str>the spirit.<n><blue>Now is your time to free <red>Merlin!", 11);
			break;
		case 99:
			line("<str>I spoke to King Arthur and he said I would be worthy of <n><str>becoming a Knight of the Round Table if I could free Merlin<n><str>from a giant crystal that he has been trapped in.</str>" + "<n><str>Gawain told me it was the work of Morgan Le Faye.<n><str>I told Lancelot of Gawain's suspicions, and he told me that<n><str>Mordred's Fortress is not completely inpenetrable." + "<n><str>There might be a way to enter with a delivery by sea...<n><str>I have broken into Keep Le Faye and slain Sir Mordred.<n>" + "<str>Morgan Le Faye disclosed the secret of how to free Merlin.<n><str>I'll need the sword Excalibur and a lit black candle.<n>" + "<str>The Lady of the Lake told me she had the Excalibur<n> <str>but I'd have to visit the jewellery store" + " in Port Sarim first.<n>" + "<str>I have the sword Excalibur and can free Merlin from the crystal.<n>" + "<str>I must now memorize an incantation inscribed on a Chaos altar<n><str>that is located somewhere in the world in order to banish<n>" + "<str>the spirit.<n><blue>Speak to <red>King Arthur<blue> for a reward.", 11);
			break;
		case 100:
			line("<str>You helped King Arthur free Merlin from the crystal.<n><n><col=FF0000>QUEST COMPLETE!</col>", 11);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("6 Quest Points", 277, 8+ 2);
		player.getPacketDispatch().sendString("Excalibur", 277, 9+ 2);
		player.getPacketDispatch().sendItemZoomOnInterface(35, 235, 277, 3+ 2);
		player.getQuestRepository().update(player);
	}

	@Override
	public boolean hasRequirements() {
		return true;
	}

	@Override
	public int[] getConfig() {
		return QuestState.getForConfig(this, 14, 1, 7);
	}
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	@Override
	public int getIndex() {
		return 87;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getQuestPoints() {
		return 6;
	}

}
