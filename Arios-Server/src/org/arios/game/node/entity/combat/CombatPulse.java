package org.arios.game.node.entity.combat;

import org.arios.game.interaction.MovementPulse;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.equipment.ArmourSet;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.tools.RandomFunction;

/**
 * The combat-handling pulse implementation.
 * @author Emperor
 */
public final class CombatPulse extends Pulse {

	/**
	 * The entity.
	 */
	private Entity entity;

	/**
	 * The victim.
	 */
	private Entity victim;

	/**
	 * The current combat style used.
	 */
	private CombatStyle style = CombatStyle.MELEE;

	/**
	 * The temporary combat swing handler.
	 */
	private CombatSwingHandler temporaryHandler;

	/**
	 * The current combat swing handler.
	 */
	private CombatSwingHandler handler = style.getSwingHandler();

	/**
	 * The last victim.
	 */
	private Entity lastVictim;

	/**
	 * The tick value of when we can start another hit-cycle.
	 */
	private int nextAttack = -1;

	/**
	 * The combat time out counter.
	 */
	private int combatTimeOut;

	/**
	 * The movement handling pulse.
	 */
	private MovementPulse movement;

	/**
	 * Constructs a new {@code CombatPulse} {@code Object}.
	 * @param source The attacking entity.
	 * @param victim The node being attacked.
	 */
	public CombatPulse(Entity source) {
		super(1, source, null);
		this.entity = source;
		this.movement = new MovementPulse(source, null) {
			@Override
			public boolean pulse() {
				return false;
			}
		};
	}

	@Override
	public boolean pulse() {
		if (victim == null || DeathTask.isDead(entity) || DeathTask.isDead(victim)) {
			return true;
		}
		if (!entity.getViewport().getRegion().isActive() || !victim.getViewport().getRegion().isActive()) {
			return true;
		}
		if (!interactable()) {
			if (entity.getWalkingQueue().isMoving()) {
				return false;
			}
			return combatTimeOut++ > entity.getProperties().getCombatTimeOut();
		}
		combatTimeOut = 0;
		entity.face(victim);
		if (nextAttack <= GameWorld.getTicks()) {
			final Entity v = victim;
			CombatSwingHandler handler = temporaryHandler;
			if (handler == null) {
				handler = entity.getSwingHandler(true);
			}
			if (!swing(entity, victim, handler)) {
				temporaryHandler = null;
				updateStyle();
				return false;
			}
			int speed = entity.getProperties().getAttackSpeed();
			if (entity instanceof Player && handler.getType() == CombatStyle.MAGIC) {
				speed = 5;
			} else if (entity.getProperties().getAttackStyle().getStyle() == WeaponInterface.STYLE_RAPID) {
				speed--;
			}
			setNextAttack(speed);
			temporaryHandler = null;
			setCombatFlags(v);
		}
		return (victim != null && victim.getSkills().getLifepoints() < 1) || entity.getSkills().getLifepoints() < 1;
	}

	/**
	 * Executes a combat swing.
	 * @param entity The entity.
	 * @param victim The victim.
	 * @param handler The combat swing handler.
	 * @return {@code True} if successfully started the swing.
	 */
	public static boolean swing(final Entity entity, final Entity victim, final CombatSwingHandler handler) {
		final BattleState state = new BattleState(entity, victim);
		ArmourSet set = handler.getArmourSet(entity);
		entity.getProperties().setArmourSet(set);
		int delay = handler.swing(entity, victim, state);
		if (delay < 0) {
			return false;
		}
		if (victim == null) {
			entity.faceTemporary(victim, 1);// face back to entity.
		}
		handler.adjustBattleState(entity, victim, state);
		handler.visualize(entity, victim, state);
		if (delay - 1 < 1) {
			handler.visualizeImpact(entity, victim, state);
		}
		handler.visualizeAudio(entity, victim, state);
		if (set != null && set.effect(entity, victim, state)) {
			set.visualize(entity, victim);
		}
		GameWorld.submit(new Pulse(delay - 1, entity, victim) {
			boolean impact;

			@Override
			public boolean pulse() {
				if (DeathTask.isDead(victim)) {
					return true;
				}
				if (impact || getDelay() == 0) {
					if (state.getEstimatedHit() != 0 && victim instanceof NPC && entity instanceof Player) {
						NPC n = victim.asNpc();
						Audio audio = n.getAudio(1);
						if (audio != null) {
							entity.asPlayer().getAudioManager().send(audio, true);
						}
					} else if (state.getEstimatedHit() != 0 && victim instanceof Player) {
						int[] sounds = new int[] { 516, 517, 518 };
						Audio audio = new Audio(sounds[RandomFunction.random(sounds.length)]);
						audio.send(victim.asPlayer(), true);
					}
					handler.impact(entity, victim, state);
					handler.onImpact(entity, victim, state);
					return true;
				}
				setDelay(1);
				impact = true;
				handler.visualizeImpact(entity, victim, state);
				return false;
			}
		});
		return true;
	}

	/**
	 * Sets the "in combat" flag for the victim and handles closing.
	 * @param victim The victim.
	 */
	public void setCombatFlags(Entity victim) {
		if (entity instanceof Player) {
			Player p = ((Player) entity);
			p.getInterfaceManager().close();
			p.getInterfaceManager().closeChatbox();
		}
		if (victim instanceof Player) {
			if (entity instanceof Player && ((Player) entity).getSkullManager().isWilderness()) {
				((Player) entity).getSkullManager().checkSkull(victim);
			}
			((Player) victim).getInterfaceManager().closeChatbox();
			((Player) victim).getInterfaceManager().close();
		}
		if (!victim.getPulseManager().isMovingPulse()) {
			victim.getPulseManager().clear("combat");
		}
		victim.setAttribute("combat-time", System.currentTimeMillis() + 10000);
		victim.setAttribute("combat-attacker", entity);
	}

	/**
	 * Checks if the mover can interact with the victim.
	 * @return {@code True} if so.
	 */
	private boolean interactable() {
		if (victim == null) {
			return false;
		}
		if (entity instanceof NPC && victim instanceof Player && ((NPC) entity).isHidden((Player) victim)) {
			stop();
			return false;
		}
		if (victim instanceof NPC && entity instanceof Player && ((NPC) victim).isHidden((Player) entity)) {
			stop();
			return false;
		}
		if (entity instanceof NPC && !entity.asNpc().canStartCombat(victim)) {
			stop();
			return false;
		}
		InteractionType type = canInteract();
		if (type == InteractionType.STILL_INTERACT) {
			return true;
		}
		if (entity == null || victim == null || entity.getLocks().isMovementLocked()) {
			return false;
		}
		movement.findPath();
		return type == InteractionType.MOVE_INTERACT;
	}

	/**
	 * Sets the combat style.
	 */
	public void updateStyle() {
		if (entity == null) {
			return;
		}
		if (entity instanceof Player) {
			Player p = (Player) entity;
			if (p.getProperties().getSpell() != null) {
				style = CombatStyle.MAGIC;
				return;
			}
			if (p.getProperties().getAutocastSpell() != null) {
				style = CombatStyle.MAGIC;
				return;
			}
			switch (p.getProperties().getAttackStyle().getBonusType()) {
			case WeaponInterface.BONUS_MAGIC:
				style = CombatStyle.MAGIC;
				break;
			case WeaponInterface.BONUS_RANGE:
				style = CombatStyle.RANGE;
				break;
			default:
				style = CombatStyle.MELEE;
				break;
			}
		}
	}

	/**
	 * Attacks the node.
	 * @param victim The victim node.
	 */
	public void attack(Node victim) {
		if (victim == null) {
			return;
		}
		if (entity.getLocks().isInteractionLocked()) {
			return;
		}
		if (victim == this.victim && isAttacking()) {
			return;
		}
		if (victim instanceof NPC) {
			((NPC) victim).getWalkingQueue().reset();
		}
		setVictim(victim);
		entity.onAttack((Entity) victim);
		entity.getPulseManager().run(this);
	}

	/**
	 * Sets the victim.
	 * @param victim The victim.
	 */
	public void setVictim(Node victim) {
		super.addNodeCheck(1, victim);
		movement.setLast(null);
		movement.setDestination(victim);
		this.victim = (Entity) victim;
		this.combatTimeOut = 0;
	}

	/**
	 * Sets the next attack.
	 * @param ticks The amount of ticks.
	 */
	public void setNextAttack(int ticks) {
		nextAttack = GameWorld.getTicks() + ticks;
	}

	/**
	 * Delays the next attack.
	 * @param The amount of ticks to delay the next attack with.
	 */
	public void delayNextAttack(int ticks) {
		nextAttack += ticks;
	}

	/**
	 * Gets the next attack tick.
	 * @return The next attack tick.
	 */
	public int getNextAttack() {
		return nextAttack;
	}

	/**
	 * Checks if we can fight with the victim.
	 * @return {@code True} if so.
	 */
	public InteractionType canInteract() {
		if (victim == null) {
			return InteractionType.NO_INTERACT;
		}
		if (temporaryHandler != null) {
			return temporaryHandler.canSwing(entity, victim);
		}
		return entity.getSwingHandler(false).canSwing(entity, victim);
	}

	@Override
	public void start() {
		super.start();
		entity.face(victim);
	}

	@Override
	public void stop() {
		super.stop();
		entity.setAttribute("combat-stop", GameWorld.getTicks());
		this.lastVictim = victim;
		super.addNodeCheck(1, victim = null);
		entity.face(victim);
		entity.getProperties().setSpell(null);
	}

	@Override
	public boolean removeFor(String pulse) {
		if (isAttacking()) {
			pulse = pulse.toLowerCase();
			if (pulse.startsWith("interaction:attack")) {
				if (victim.hashCode() == Integer.parseInt(pulse.replace("interaction:attack:", ""))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if this entity is attacking.
	 * @return {@code True} if so.
	 */
	public boolean isAttacking() {
		return victim != null && victim.isActive() && isRunning();
	}

	/**
	 * If the entity has an attacker.
	 * @return {@code True} if so.
	 */
	public boolean isInCombat() {
		Entity entity = this.entity.getAttribute("combat-attacker");
		return entity != null && entity.getProperties().getCombatPulse().isAttacking();
	}

	/**
	 * Gets the current victim.
	 * @return The victim.
	 */
	public Entity getVictim() {
		return victim;
	}

	/**
	 * Gets the style.
	 * @return The style.
	 */
	public CombatStyle getStyle() {
		return style;
	}

	/**
	 * Sets the style.
	 * @param style The style to set.
	 */
	public void setStyle(CombatStyle style) {
		this.style = style;
	}

	/**
	 * @return the temporaryHandler.
	 */
	public CombatSwingHandler getTemporaryHandler() {
		return temporaryHandler;
	}

	/**
	 * @param temporaryHandler the temporaryHandler to set.
	 */
	public void setTemporaryHandler(CombatSwingHandler temporaryHandler) {
		this.temporaryHandler = temporaryHandler;
	}

	/**
	 * @return the lastVictim.
	 */
	public Entity getLastVictim() {
		return lastVictim;
	}

	/**
	 * @param lastVictim the lastVictim to set.
	 */
	public void setLastVictim(Entity lastVictim) {
		this.lastVictim = lastVictim;
	}

	/**
	 * Gets the handler.
	 * @return The handler.
	 */
	public CombatSwingHandler getHandler() {
		return handler;
	}

	/**
	 * Sets the handler.
	 * @param handler The handler to set.
	 */
	public void setHandler(CombatSwingHandler handler) {
		this.handler = handler;
	}

}