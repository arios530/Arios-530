package org.arios.game.node.entity.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.global.shop.Shop;
import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.member.slayer.Task;
import org.arios.game.content.skill.member.slayer.Tasks;
import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.interaction.Interaction;
import org.arios.game.interaction.MovementPulse;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.BattleState;
import org.arios.game.node.entity.combat.CombatSpell;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.combat.CombatSwingHandler;
import org.arios.game.node.entity.combat.equipment.DefaultCombatSpell;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.combat.equipment.WeaponInterface.AttackStyle;
import org.arios.game.node.entity.npc.agg.AggressiveBehavior;
import org.arios.game.node.entity.npc.agg.AggressiveHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SpellBookManager;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.system.mysql.impl.ShopSQLHandler;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.map.build.DynamicRegion;
import org.arios.game.world.map.path.Pathfinder;
import org.arios.game.world.repository.Repository;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.npc.NPCFaceEntity;
import org.arios.game.world.update.flag.npc.NPCFaceLocation;
import org.arios.game.world.update.flag.npc.NPCForceChat;
import org.arios.game.world.update.flag.npc.NPCSwitchId;
import org.arios.parser.npc.NPCConfiguration;
import org.arios.tools.RandomFunction;

/**
 * Represents a non-player character.
 * @author Emperor
 */
public class NPC extends Entity {

	/**
	 * The non-player character's id.
	 */
	private int id;

	/**
	 * The original NPC id.
	 */
	private final int originalId;

	/**
	 * The NPC definitions.
	 */
	private NPCDefinition definition;

	/**
	 * If the NPC walks;
	 */
	private boolean walks;

	/**
	 * If the NPC is aggressive.
	 */
	private boolean aggressive;

	/**
	 * Represents if the NPC will respawn.
	 */
	private boolean respawn = true;

	/**
	 * Represents if the NPC is hidden.
	 */
	private boolean hidden = false;

	/**
	 * The movement path of the NPC.
	 */
	protected Location[] movementPath;

	/**
	 * The walking radius.
	 */
	protected int walkRadius = 11;

	/**
	 * The movement index.
	 */
	protected int movementIndex;

	/**
	 * The respawn tick.
	 */
	private int respawnTick;

	/**
	 * If the NPC walks bound to a path.
	 */
	protected boolean pathBoundMovement;

	/**
	 * The current dialogue.
	 */
	protected Player dialoguePlayer;

	/**
	 * The aggressive handler of the NPC.
	 */
	protected AggressiveHandler aggressiveHandler;

	/**
	 * The walk back radius.
	 */
	protected int nextWalk;

	/**
	 * The children NPCs.
	 */
	private NPC[] children;

	/**
	 * The amount of slayer experience received when killing this NPC.
	 */
	private double slayerExperience;

	/**
	 * The slayer task.
	 */
	private Task task;

	/**
	 * If the npc can never walk.
	 */
	private boolean neverWalks;

	/**
	 * The shop of this npc.
	 */
	private Shop shop;

	/**
	 * Constructs a new {@code NPC} {@code Object}.
	 * @param id The NPC id.
	 */
	protected NPC(int id) {
		this(id, null);
	}

	/**
	 * Constructs a new {@code NPC} {@code Object}.
	 * @param id The NPC id.
	 * @param location The location.
	 */
	protected NPC(int id, Location location, Direction direction) {
		super(NPCDefinition.forId(id).getName(), location);
		this.id = id;
		this.originalId = id;
		this.definition = NPCDefinition.forId(id);
		super.size = definition.size;
		super.direction = direction;
		super.interaction = new Interaction(this);
	}

	/**
	 * Constructs a new {@code NPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	protected NPC(int id, Location location) {
		this(id, location, Direction.SOUTH);
	}

	/**
	 * Creates a new NPC object.
	 * @param id The NPC id.
	 * @param location The location.
	 * @param direction the dir.
	 * @param objects the objects.
	 * @return The NPC object.
	 */
	public static NPC create(int id, Location location, Direction direction, Object... objects) {
		NPC n = AbstractNPC.forId(id);
		if (n != null) {
			n = ((AbstractNPC) n).construct(id, location, objects);
		}
		if (n == null) {
			n = new NPC(id, location, direction);
		}
		return n;
	}

	/**
	 * Creates a new NPC object.
	 * @param id the id.
	 * @param location the location.
	 * @return the npc object.
	 */
	public static NPC create(int id, Location location, Object... objects) {
		return create(id, location, Direction.SOUTH, objects);
	}

	@Override
	public void init() {
		super.init();
		getProperties().setSpawnLocation(getLocation());
		initConfig();
		Repository.getNpcs().add(this);
		RegionManager.move(this);
		if (getViewport().getRegion().isActive()) {
			Repository.addRenderableNPC(this);
		}
		interaction.setDefault();
		configure();
		setDefaultBehavior();
		if (definition.childNPCIds != null) {
			children = new NPC[definition.childNPCIds.length];
			for (int i = 0; i < children.length; i++) {
				NPC npc = children[i] = new NPC(definition.childNPCIds[i]);
				npc.interaction.setDefault();
				npc.index = index;
				npc.size = size;
			}
		}
		if (definition.hasAction("trade")) {
			shop = ShopSQLHandler.getShops().get(getId());
			if (shop != null) {
				shop = shop.copy();
			}
		}
	}

	@Override
	public void clear() {
		super.clear();
		Repository.removeRenderableNPC(this);
		Repository.getNpcs().remove(this);
		getViewport().setCurrentPlane(null);
		// getViewport().setRegion(null);
	}

	/**
	 * Initializes the configurations.
	 */
	public void initConfig() {
		int defaultLevel = definition.getCombatLevel() / 2;
		getProperties().setCombatLevel(definition.getCombatLevel());
		getSkills().setStaticLevel(Skills.ATTACK, definition.getConfiguration(NPCConfiguration.ATTACK_LEVEL, defaultLevel));
		getSkills().setStaticLevel(Skills.STRENGTH, definition.getConfiguration(NPCConfiguration.STRENGTH_LEVEL, defaultLevel));
		getSkills().setStaticLevel(Skills.DEFENCE, definition.getConfiguration(NPCConfiguration.DEFENCE_LEVEL, defaultLevel));
		getSkills().setStaticLevel(Skills.RANGE, definition.getConfiguration(NPCConfiguration.RANGE_LEVEL, defaultLevel));
		getSkills().setStaticLevel(Skills.MAGIC, definition.getConfiguration(NPCConfiguration.MAGIC_LEVEL, defaultLevel));
		getSkills().setStaticLevel(Skills.HITPOINTS, definition.getConfiguration(NPCConfiguration.LIFEPOINTS, defaultLevel));
		int lp = getSkills().getMaximumLifepoints();
		if (this instanceof Familiar) {
			lp = getAttribute("hp", lp);
		}
		getSkills().setLevel(Skills.HITPOINTS, lp);
		aggressive = definition.getConfiguration(NPCConfiguration.AGGRESSIVE, aggressive);
		Animation anim = null;
		for (int i = 0; i < 3; i++) {
			if (definition.getCombatAnimations()[i] != null) {
				getProperties().setAttackAnimation(anim = definition.getCombatAnimations()[i]);
				break;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (definition.getCombatAnimations()[i] == null) {
				definition.getCombatAnimations()[i] = anim;
			}
		}
		if (definition.getCombatAnimations()[3] != null) {
			getProperties().setDefenceAnimation(definition.getCombatAnimations()[3]);
		}
		if (definition.getCombatAnimations()[4] != null) {
			getProperties().setDeathAnimation(definition.getCombatAnimations()[4]);
		}
		if (definition.getCombatAnimations()[1] != null) {
			getProperties().setMagicAnimation(definition.getCombatAnimations()[1]);
		}
		if (definition.getCombatAnimations()[2] != null) {
			getProperties().setRangeAnimation(definition.getCombatAnimations()[2]);
		}
		definition.initCombatGraphics(definition.getConfigurations());
		getProperties().setBonuses(definition.getConfiguration(NPCConfiguration.BONUSES, new int[15]));
		getProperties().setAttackSpeed(definition.getConfiguration(NPCConfiguration.ATTACK_SPEED, 5));
	}

	/**
	 * Opens the shop for a player.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public boolean openShop(Player player) {
		if (shop == null) {
			shop = ShopSQLHandler.getShops().get(getId());
			if (shop == null) {
				return false;
			}
		}
		if (getName().contains("assistant")) {
			NPC n = RegionManager.getNpc(this, getId() - 1);
			if (n != null) {
				return n.openShop(player);
			}
		}
		shop.open(player);
		return true;
	}

	/**
	 * Checks if the NPC is currently invisible.
	 * @return {@code True} if so.
	 */
	public boolean isInvisible() {
		if (isHidden()) {
			return true;
		}
		if (!isActive()) {
			return true;
		}
		return getRespawnTick() > GameWorld.getTicks();
	}

	/**
	 * Checks if the NPC is hidden for the player.
	 * @param player The player.
	 * @return {@code True} if so.
	 */
	public boolean isHidden(Player player) {
		return isInvisible();
	}

	/**
	 * Sets the default aggressive behavior of the NPC.
	 */
	public void setDefaultBehavior() {
		if (aggressive && definition.getCombatLevel() > 0) {
			aggressiveHandler = new AggressiveHandler(this, AggressiveBehavior.DEFAULT);
			aggressiveHandler.setRadius(definition.getConfiguration(NPCConfiguration.AGGRESSIVE_RADIUS, 4));
		}
	}

	/**
	 * Configures the movement path.
	 * @param movementPath The movement path.
	 */
	public void configureMovementPath(Location... movementPath) {
		this.movementPath = movementPath;
		this.movementIndex = 0;
		this.pathBoundMovement = true;
	}

	@Override
	public void checkImpact(BattleState state) {
		super.checkImpact(state);
		Entity entity = state.getAttacker();
		if (task != null && entity instanceof Player && task.getLevel() > entity.getSkills().getStaticLevel(Skills.SLAYER)) {
			state.neutralizeHits();
		}
	}

	@Override
	public boolean isAttackable(Entity entity, CombatStyle style) {
		if ((entity instanceof Player && !definition.hasAction("attack")) || isInvisible()) {
			return false;
		}
		if (task != null && entity instanceof Player && task.getLevel() > entity.getSkills().getStaticLevel(Skills.SLAYER)) {
			((Player) entity).getPacketDispatch().sendMessage("You need a higher slayer level to know how to wound this monster.");
		}
		return super.isAttackable(entity, style);
	}

	@Override
	public int getDragonfireProtection(boolean fire) {
		return 0;
	}

	@Override
	public void tick() {
		if (!getViewport().getRegion().isActive()) {
			onRegionInactivity();
			return;
		}
		if (respawnTick > GameWorld.getTicks()) {
			return;
		}
		handleTickActions();
		super.tick();
	}

	/**
	 * Handles the automatic actions of the NPC.
	 */
	public void handleTickActions() {
		if (!getLocks().isInteractionLocked()) {
			if (!pathBoundMovement && walkRadius > 0 && !getLocation().withinDistance(getProperties().getSpawnLocation(), walkRadius)) {
				getPulseManager().run(new MovementPulse(this, getProperties().getSpawnLocation(), Pathfinder.SMART) {
					@Override
					public boolean pulse() {
						return true;
					}
				}, "movement");
				if (aggressiveHandler != null) {
					aggressiveHandler.setPauseTicks(walkRadius + 1);
				}
				nextWalk = GameWorld.getTicks() + walkRadius + 1;
				return;
			}
			if (aggressive && aggressiveHandler != null && aggressiveHandler.selectTarget()) {
				return;
			}
			if (getProperties().getCombatPulse().isAttacking()) {
				return;
			}
		}
		if (!getLocks().isMovementLocked()) {
			if (dialoguePlayer == null || !dialoguePlayer.isActive() || !dialoguePlayer.getInterfaceManager().hasChatbox()) {
				dialoguePlayer = null;
				if (walks && !getPulseManager().hasPulseRunning() && !getProperties().getCombatPulse().isAttacking() && !getProperties().getCombatPulse().isInCombat() && nextWalk < GameWorld.getTicks()) {
					setNextWalk();
					Location l = getMovementDestination();
					if (canMove(l)) {
						Pathfinder.find(this, l, true, Pathfinder.DUMB).walk(this);
					}
				}
			}
		}
		if (shop != null) {
			if (shop.getLastRestock() < GameWorld.getTicks()) {
				shop.restock();
				shop.setLastRestock(GameWorld.getTicks() + 100);
			}
		}
	}

	/**
	 * Sets the next walk.
	 */
	public void setNextWalk() {
		nextWalk = GameWorld.getTicks() + 5 + RandomFunction.randomize(10);
	}

	/**
	 * Called when the region goes inactive.
	 */
	public void onRegionInactivity() {
		getWalkingQueue().reset();
		getPulseManager().clear();
		getUpdateMasks().reset();
		Repository.removeRenderableNPC(this);
		if (getViewport().getRegion() instanceof DynamicRegion) {
			clear();
		}
	}

	@Override
	public boolean isPoisonImmune() {
		return definition.getConfiguration(NPCConfiguration.POISON_IMMUNE, false);
	}

	@Override
	public void finalizeDeath(Entity killer) {
		super.finalizeDeath(killer);
		if (getZoneMonitor().handleDeath(killer)) {
			return;
		}
		if (task != null && killer instanceof Player && ((Player) killer).getSlayer().getTask() == task) {
			killer.getSkills().addExperience(Skills.SLAYER, getDefinition().getConfiguration(NPCConfiguration.SLAYER_EXP, (double) getSkills().getStaticLevel(Skills.HITPOINTS)), true);
			((Player) killer).getSlayer().decrementAmount(1);
			if (!((Player) killer).getSlayer().hasTask()) {
				((Player) killer).getSlayer().clear();
				((Player) killer).getPacketDispatch().sendMessage("You've completed your task; return to a Slayer master.");
			}
		}
		setRespawnTick(GameWorld.getTicks() + definition.getConfiguration(NPCConfiguration.RESPAWN_DELAY, 17));
		Player p = killer == null || !(killer instanceof Player) ? null : (Player) killer;
		handleDrops(p, killer);
		if (!isRespawn()) {
			clear();
		}
	}

	/**
	 * Handles the drops of the npc.
	 * @param p the p.
	 * @param killer the killer.
	 */
	public void handleDrops(Player p, Entity killer) {
		if (getAttribute("disable:drop", false)) {
			return;
		}
		if (killer instanceof Player && p != null && p.getIronmanManager().isIronman() && getImpactHandler().getImpactLog().size() > 1) {
			return;
		}
		if (definition == null || definition.getDropTables() == null) {
			return;
		}
		definition.getDropTables().drop(this, killer);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public boolean face(Entity entity) {
		if (entity == null) {
			if (getUpdateMasks().unregisterSynced(NPCFaceEntity.getOrdinal())) {
				return getUpdateMasks().register(new NPCFaceEntity(entity));
			}
			return true;
		}
		return getUpdateMasks().register(new NPCFaceEntity(entity), true);
	}

	@Override
	public boolean faceLocation(Location location) {
		if (location == null) {
			getUpdateMasks().unregisterSynced(NPCFaceLocation.getOrdinal());
			return true;
		}
		return getUpdateMasks().register(new NPCFaceLocation(location), true);
	}

	@Override
	public boolean sendChat(String string) {
		return getUpdateMasks().register(new NPCForceChat(string));
	}

	@Override
	public CombatSwingHandler getSwingHandler(boolean swing) {
		return getProperties().getCombatPulse().getStyle().getSwingHandler();
	}

	/**
	 * Gets an audio piece.
	 * @param index the index.
	 * @return the audio.
	 */
	public Audio getAudio(int index) {
		Audio[] audios = getDefinition().getConfiguration(NPCConfiguration.COMBAT_AUDIO, null);
		if (audios != null) {
			Audio audio = audios[index];
			if (audio != null && audio.getId() != 0) {
				return audio;
			}
		}
		return null;
	}

	/**
	 * Configures the NPC. <br> <b>Override this instead of {@link #init()} when
	 * creating a sub class.</b>
	 */
	public void configure() {
		int[] bonus = definition.getConfiguration(NPCConfiguration.BONUSES, new int[3]);
		int highest = 0;
		int index = 0;
		for (int i = 0; i < 3; i++) {
			if (bonus[i] > highest) {
				highest = bonus[i];
				index = i;
			}
		}
		getProperties().setAttackStyle(new AttackStyle(WeaponInterface.STYLE_CONTROLLED, index));
		CombatStyle style = getDefinition().getConfiguration(NPCConfiguration.COMBAT_STYLE);
		if (style == CombatStyle.RANGE) {
			getProperties().getCombatPulse().setStyle(style);
		} else if (style == CombatStyle.MAGIC) {
			getProperties().getCombatPulse().setStyle(style);
			getProperties().setAutocastSpell(new DefaultCombatSpell(this));
			int spell = definition.getConfiguration("spell_id", -1);
			if (spell != -1) {
				getProperties().setSpell((CombatSpell) SpellBookManager.SpellBook.MODERN.getSpell(spell));
				getProperties().setAutocastSpell((CombatSpell) SpellBookManager.SpellBook.MODERN.getSpell(spell));
			}
		}
		task = Tasks.forId(getId());
	}

	/**
	 * Method used to check if the NPC can attack the victim. This method is
	 * used for aggressive behavior.
	 * @param killer the killer.
	 * @return <code>True</code> if so.
	 */
	public boolean canAttack(final Entity victim) {
		return true;
	}

	@Override
	public boolean hasProtectionPrayer(CombatStyle style) {
		return false;
	}

	/**
	 * Checks if the npc should ignore victim attack restrictions(i.e You can't
	 * attack this npc.")
	 * @param victim the victim.
	 * @return {@code True} if ignore.
	 */
	public boolean isIgnoreAttackRestrictions(Entity victim) {
		return false;
	}

	@Override
	public String toString() {
		return "NPC " + id + ", " + getLocation() + ", " + getIndex();
	}

	/**
	 * Transforms this NPC.
	 * @param id The new NPC id.
	 */
	public void transform(int id) {
		this.id = id;
		this.definition = NPCDefinition.forId(id);
		super.name = definition.getName();
		super.size = definition.size;
		super.interaction = new Interaction(this);
		initConfig();
		interaction.setDefault();
		if (id == originalId) {
			getUpdateMasks().unregisterSynced(NPCSwitchId.getOrdinal());
		}
		getUpdateMasks().register(new NPCSwitchId(id), id != originalId);
	}

	/**
	 * Transforms this NPC back to original.
	 */
	public void reTransform() {
		if (originalId == this.id) {
			return;
		}
		transform(originalId);
	}

	/**
	 * Gets the movement destination of the NPC.
	 * @return The movement destination.
	 */
	protected Location getMovementDestination() {
		if (!pathBoundMovement || movementPath == null || movementPath.length < 1) {
			return getProperties().getSpawnLocation().transform(-5 + RandomFunction.random(getWalkRadius()), -5 + RandomFunction.random(getWalkRadius()), 0);
		}
		Location l = movementPath[movementIndex++];
		if (movementIndex == movementPath.length) {
			movementIndex = 0;
		}
		return l;
	}

	/**
	 * Checks if the npc can start combat.
	 * @param victim the victim.
	 * @return {@code True} if so.
	 */
	public boolean canStartCombat(Entity victim) {
		return true;
	}

	/**
	 * Gets the definition.
	 * @return The definition.
	 */
	public NPCDefinition getDefinition() {
		return definition;
	}

	/**
	 * Sets the definition.
	 * @param definition The definition to set.
	 */
	public void setDefinition(NPCDefinition definition) {
		this.definition = definition;
	}

	/**
	 * Get the id.
	 * @return The non-player character's id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id.
	 * @param id The non-player character's id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the walks.
	 * @return The walks.
	 */
	public boolean isWalks() {
		return walks;
	}

	/**
	 * Sets the walks.
	 * @param walks The walks to set.
	 */
	public void setWalks(boolean walks) {
		this.walks = walks;
	}

	/**
	 * Gets the aggressive.
	 * @return The aggressive.
	 */
	public boolean isAggressive() {
		return aggressive;
	}

	/**
	 * Sets the aggressive.
	 * @param aggressive The aggressive to set.
	 */
	public void setAggressive(boolean aggressive) {
		this.aggressive = aggressive;
	}

	/**
	 * Sets the name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the movementPath.
	 * @return The movementPath.
	 */
	public Location[] getMovementPath() {
		return movementPath;
	}

	/**
	 * @return the respawn.
	 */
	public boolean isRespawn() {
		return respawn;
	}

	/**
	 * @param respawn the respawn to set.
	 */
	public void setRespawn(boolean respawn) {
		this.respawn = respawn;
	}

	/**
	 * Gets the pathBoundMovement.
	 * @return The pathBoundMovement.
	 */
	public boolean isPathBoundMovement() {
		return pathBoundMovement;
	}

	/**
	 * Sets the pathBoundMovement.
	 * @param pathBoundMovement The pathBoundMovement.
	 */
	public void setPathBoundMovement(boolean pathBoundMovement) {
		this.pathBoundMovement = pathBoundMovement;
	}

	/**
	 * Gets the dialoguePlayer.
	 * @return The dialoguePlayer.
	 */
	public Player getDialoguePlayer() {
		return dialoguePlayer;
	}

	/**
	 * Sets the dialoguePlayer.
	 * @param activeDialogue The dialoguePlayer.
	 */
	public void setDialoguePlayer(Player dialoguePlayer) {
		this.dialoguePlayer = dialoguePlayer;
	}

	/**
	 * @return the hidden.
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden the hidden to set.
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Gets the aggressive handler.
	 * @return The aggressive handler.
	 */
	public AggressiveHandler getAggressiveHandler() {
		return aggressiveHandler;
	}

	/**
	 * Sets the aggressive handler.
	 * @param handler The handler.
	 */
	public void setAggressiveHandler(AggressiveHandler handler) {
		this.aggressiveHandler = handler;
	}

	/**
	 * Gets the respawnTick.
	 * @return The respawnTick.
	 */
	public int getRespawnTick() {
		return respawnTick;
	}

	/**
	 * Sets the walking radius.
	 * @param radius The walking radius.
	 */
	public void setWalkRadius(int radius) {
		this.walkRadius = radius;
	}

	/**
	 * Gets the walking radius.
	 * @return the walking radius.
	 */
	public int getWalkRadius() {
		return walkRadius;
	}

	/**
	 * Sets the respawnTick.
	 * @param respawnTick The respawnTick to set.
	 */
	public void setRespawnTick(int respawnTick) {
		this.respawnTick = respawnTick;
	}

	/**
	 * Gets the originalId.
	 * @return The originalId.
	 */
	public int getOriginalId() {
		return originalId;
	}

	/**
	 * Gets the currently shown NPC.
	 * @param player The player to check for.
	 * @return The shown NPC.
	 */
	public NPC getShownNPC(Player player) {
		if (children == null) {
			return this;
		}
		int npcId = definition.getChildNPC(player).getId();
		for (NPC npc : children) {
			if (npc.getId() == npcId) {
				return npc;
			}
		}
		return this;
	}

	/**
	 * Gets the shop.
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * Sets the bashop.
	 * @param shop the shop to set.
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}

	/**
	 * Gets the slayerExperience.
	 * @return The slayerExperience.
	 */
	public double getSlayerExperience() {
		return slayerExperience;
	}

	/**
	 * Sets the slayerExperience.
	 * @param slayerExperience The slayerExperience to set.
	 */
	public void setSlayerExperience(double slayerExperience) {
		this.slayerExperience = slayerExperience;
	}

	/**
	 * Gets the task.
	 * @return The task.
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 * @param task The task to set.
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Gets the neverWalks.
	 * @return the neverWalks
	 */
	public boolean isNeverWalks() {
		return neverWalks;
	}

	/**
	 * Sets the baneverWalks.
	 * @param neverWalks the neverWalks to set.
	 */
	public void setNeverWalks(boolean neverWalks) {
		this.neverWalks = neverWalks;
	}

}