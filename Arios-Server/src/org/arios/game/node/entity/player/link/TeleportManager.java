package org.arios.game.node.entity.player.link;

import org.arios.ServerConstants;
import org.arios.game.content.ame.AntiMacroHandler;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.LostCity;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.impl.Animator.Priority;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;

/**
 * Handles the entity teleport.
 * @author SonicForce41
 */
public class TeleportManager {

	/**
	 * The wildy teleport type.
	 */
	public static final int WILDY_TELEPORT = 1 << 16 | 8;

	/**
	 * The entity being handled.
	 */
	private final Entity entity;

	/**
	 * The last teleport of this <b>Entity</b>
	 */
	private Pulse lastTeleport;

	/**
	 * The current teleport of this <b>Entity</b>
	 */
	private Pulse currentTeleport;

	/**
	 * The current teleport type.
	 */
	private int teleportType;

	/**
	 * Constructs a new {@code Teleporter.java} {@Code Object}.
	 * @param entity the Entity
	 */
	public TeleportManager(Entity entity) {
		this.entity = entity;
		lastTeleport = TeleportType.HOME.getPulse(entity, ServerConstants.getHomeLocation());
	}

	/**
	 * Sends the teleport.
	 * @param location the Location.
	 * @param type the NodeType.
	 * @return {@code True} if the player successfully started teleporting.
	 */
	public boolean send(Location location) {
		return send(location, entity instanceof Player ? getType((Player) entity) : TeleportType.NORMAL, 0);
	}

	/**
	 * Sends the teleport.
	 * @param location the Location.
	 * @param type the NodeType.
	 * @return {@code True} if the player successfully started teleporting.
	 */
	public boolean send(Location location, TeleportType type) {
		return send(location, type, 0);
	}

	/**
	 * Sends the teleport.
	 * @param location the Location.
	 * @param type the NodeType.
	 * @param teleportType The teleporting type. (0=spell, 1=item, 2=object,
	 * 3=npc -1= force)
	 * @return {@code True} if the player successfully started teleporting.
	 */
	public boolean send(Location location, TeleportType type, int teleportType) {
		if (teleportType != -1 && entity.isTeleBlocked()) {
			if (entity.isPlayer()) {
				entity.asPlayer().sendMessage("A magical force has stopped you from teleporting.");
			}
			return false;
		}
		if ((teleportType != WILDY_TELEPORT && type != TeleportType.OBELISK) && !entity.getZoneMonitor().teleport(teleportType, null)) {
			return false;
		}
		if (teleportType != -1) {
			if (entity instanceof Player) {
				Player p = (Player) entity;
				p.getDialogueInterpreter().close();
			}
		}
		this.teleportType = teleportType;
		entity.getWalkingQueue().reset();
		lastTeleport = currentTeleport;
		currentTeleport = type.getPulse(entity, location);
		entity.getPulseManager().clear();
		if (type == TeleportType.HOME) {
			entity.getPulseManager().run(type.getPulse(entity, location));
		} else {
			entity.lock(12);
			entity.getImpactHandler().setDisabledTicks(teleportType == -1 ? 5 : 12);
			GameWorld.submit(currentTeleport);
		}
		if (entity instanceof Player) {
			((Player) entity).getInterfaceManager().close();
		}
		return true;
	}

	/**
	 * Fires a random event.
	 * @param entity The entity teleporting.
	 * @param location The destination lcoation.
	 */
	public static void fireRandom(Entity entity, Location location) {
		if (entity instanceof Player && entity.getTeleporter().getTeleportType() == 0) {
			Player p = (Player) entity;
			if (p.getAntiMacroHandler().getChanceRatio(Skills.MAGIC) > AntiMacroHandler.FIRE_RATIO / 2) {
				// p.getAntiMacroHandler().fireEvent("Lost and found",
				// location);
			}
		}
	}

	/**
	 * Gets the entity.
	 * @return the Entity
	 */
	public final Entity getEntity() {
		return entity;
	}

	/**
	 * Gets the last teleport pulse.
	 * @return the Pulse
	 */
	public final Pulse getLastTeleport() {
		return lastTeleport;
	}

	/**
	 * Gets the current teleport pulse.
	 * @return the Pulse
	 */
	public final Pulse getCurrentTeleport() {
		return currentTeleport;
	}

	/**
	 * Represents a NodeType for Teleporter
	 * @author SonicForce41
	 */
	public enum TeleportType {

		/**
		 * The value types
		 */
		NORMAL(new TeleportSettings(8939, 8941, 1576, 1577)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							if (entity instanceof Player) {
								entity.asPlayer().getAudioManager().send(200);
							}
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
							entity.graphics(new Graphics(getSettings().getStartGfx()));
						} else if (delay == 3) {
							entity.getProperties().setTeleportLocation(Location.create(location));
							fireRandom(entity, location);
						} else if (delay == 4) {
							if (entity instanceof Player) {
								entity.asPlayer().getAudioManager().send(201);
							}
							entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
							entity.graphics(new Graphics(getSettings().getEndGfx()));
							return true;
						}
						delay++;
						return false;
					}

					@Override
					public void stop() {
						super.stop();
						entity.unlock();
						entity.lock(4);
					}
				};
			}
		},
		ANCIENT(new TeleportSettings(1979, -1, 392, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
							entity.graphics(new Graphics(getSettings().getStartGfx()));
						} else if (delay == 4) {
							entity.getProperties().setTeleportLocation(Location.create(location));
							fireRandom(entity, location);
						} else if (delay == 5) {
							entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
							entity.graphics(new Graphics(getSettings().getEndGfx()));
							return true;
						}
						delay++;
						return false;
					}

					@Override
					public void stop() {
						super.stop();
						entity.getAnimator().forceAnimation(new Animation(-1));
						entity.graphics(new Graphics(-1));
						entity.unlock();
					}
				};
			}
		},
		LUNAR(new TeleportSettings(1816, -1, 747, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							entity.graphics(new Graphics(getSettings().getStartGfx(), 120));
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
						} else if (delay == 3) {
							entity.getProperties().setTeleportLocation(Location.create(location));
							fireRandom(entity, location);
						} else if (delay == 4) {
							entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
							entity.graphics(new Graphics(getSettings().getEndGfx()));
							return true;
						}
						delay++;
						return false;
					}

					@Override
					public void stop() {
						super.stop();
						entity.getAnimator().forceAnimation(new Animation(-1));
						entity.graphics(new Graphics(-1));
						entity.unlock();
					}
				};
			}
		},
		TELETABS(new TeleportSettings(4731, -1, 678, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							if (entity instanceof Player) {
								entity.asPlayer().getAudioManager().send(979);
							}
							entity.getAnimator().forceAnimation(new Animation(4069));
						} else if (delay == 2) {
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote(), Priority.HIGH));
							entity.graphics(new Graphics(getSettings().getStartGfx()));
						} else if (delay == 3) {
							entity.getProperties().setTeleportLocation(Location.create(location));
						} else if (delay == 4) {
							entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote()));
							entity.graphics(new Graphics(getSettings().getEndGfx()));
							return true;
						}
						delay++;
						return false;
					}

					@Override
					public void stop() {
						super.stop();
						entity.getAnimator().forceAnimation(new Animation(-1));
						entity.graphics(new Graphics(-1));
						entity.unlock();
						entity.lock(2);
					}
				};
			}
		},
		HOME(new TeleportSettings(4847, 4857, 800, 804)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int count;
					Player player;

					@Override
					public boolean pulse() {
						switch (count) {
						case 1:
							player.getPacketDispatch().sendAnimation(4847);
							player.getPacketDispatch().sendGraphic(800);
							entity.asPlayer().getAudioManager().send(193);
							break;
						case 6:
							player.getPacketDispatch().sendGraphic(801);
							player.getPacketDispatch().sendAnimation(4850);
							entity.asPlayer().getAudioManager().send(194);
							break;
						case 8:
							player.getPacketDispatch().sendGraphic(802);
							player.getPacketDispatch().sendAnimation(4853);
							break;
						case 12:
							// player.getPacketDispatch().sendGraphic(803);
							// //this graphic is now somehow wrong..?
							player.getPacketDispatch().sendAnimation(4855);
							entity.asPlayer().getAudioManager().send(195);
							break;
						case 16:
							player.getPacketDispatch().sendGraphic(804);
							player.getPacketDispatch().sendAnimation(4857);
							entity.asPlayer().getAudioManager().send(196);
							break;
						case 20:
							player.getProperties().setTeleportLocation(location);
							if (!player.isDonator()) {
								player.getSavedData().getGlobalData().setHomeTeleportDelay(System.currentTimeMillis() + 1800000);
							}
							return true;
						}
						count++;
						return false;
					}

					@Override
					public void start() {
						player = ((Player) entity);
						if (TutorialSession.getExtension(player).getStage() < TutorialSession.MAX_STAGE) {
							stop();
							return;
						}
						if (player.getSavedData().getGlobalData().getHomeTeleportDelay() > System.currentTimeMillis() && !player.isDonator()) {
							long milliseconds = player.getSavedData().getGlobalData().getHomeTeleportDelay() - System.currentTimeMillis();
							int minutes = (int) Math.round(milliseconds / 60000);
							if (minutes > 30) {
								player.getSavedData().getGlobalData().setHomeTeleportDelay(System.currentTimeMillis() + 1800000);
								milliseconds = player.getSavedData().getGlobalData().getHomeTeleportDelay() - System.currentTimeMillis();
								minutes = (int) Math.round(milliseconds / 60000);
							}
							if (minutes != 0) {
								player.getPacketDispatch().sendMessage("You need to wait another " + minutes + " " + (minutes == 1 ? "minute" : "minutes") + " to cast this spell.");
								stop();
								return;
							}
						}
						super.start();
					}

					@Override
					public void stop() {
						super.stop();
						entity.getAnimator().forceAnimation(new Animation(-1));
						player.graphics(new Graphics(-1));
					}
				};
			}
		},
		OBELISK(new TeleportSettings(8939, 8941, -1, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							entity.lock();
							entity.getAnimator().forceAnimation(new Animation(1816));
						} else if (delay == 3) {
							entity.getProperties().setTeleportLocation(Location.create(location));
						} else if (delay == 4) {
							entity.getAnimator().forceAnimation(Animation.RESET);
							entity.unlock();
							return true;
						}
						delay++;
						return false;
					}
				};
			}
		},
		TELE_OTHER(new TeleportSettings(1816, -1, 342, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							if (entity instanceof Player) {
								entity.asPlayer().getAudioManager().send(199);
							}
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
							entity.graphics(new Graphics(getSettings().getStartGfx()));
						} else if (delay == 3) {
							entity.getProperties().setTeleportLocation(Location.create(location));
						} else if (delay == 4) {
							entity.getAnimator().forceAnimation(new Animation(-1));
							entity.unlock();
							return true;
						}
						delay++;
						return false;
					}
				};
			}
		},
		FAIRY_RING(new TeleportSettings(-1, -1, -1, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				entity.graphics(Graphics.create(569));
				return new Pulse(1, entity) {
					int delay;

					@Override
					public boolean pulse() {
						switch (++delay) {
						case 2:
							entity.animate(Animation.create(3265));
							break;
						case 4:
							Quest quest = null;
							if (entity instanceof Player) {
								quest = ((Player) entity).getQuestRepository().getQuest(LostCity.NAME);
							}
							if (quest != null) {
								if (quest.getStage() == 21) {
									quest.finish();
								}
							}
							entity.animate(Animation.create(3266));
							entity.getProperties().setTeleportLocation(location);
							entity.unlock();
							entity.lock(2);
							return true;
						}
						return false;
					}

				};
			}
		},
		PURO_PURO(new TeleportSettings(6601, 1118, -1, -1)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
							entity.graphics(new Graphics(1118));
						} else if (delay == 9) {
							entity.getProperties().setTeleportLocation(Location.create(location));
							entity.getAnimator().forceAnimation(new Animation(-1));
							entity.unlock();
							return true;
						}
						delay++;
						return false;
					}
				};
			}
		},
		ECTOPHIAL(new TeleportSettings(8939, 8941, 1587, 1588)) {
			@Override
			public Pulse getPulse(final Entity entity, final Location location) {
				return new Pulse(1) {
					int delay = 0;

					@Override
					public boolean pulse() {
						if (delay == 0) {
							if (entity instanceof Player) {
								entity.asPlayer().getAudioManager().send(200);
							}
							entity.getAnimator().forceAnimation(new Animation(getSettings().getStartEmote()));
							entity.graphics(new Graphics(getSettings().getStartGfx()));
						} else if (delay == 3) {
							entity.getProperties().setTeleportLocation(Location.create(location));
							fireRandom(entity, location);
						} else if (delay == 4) {
							if (entity instanceof Player) {
								entity.asPlayer().getAudioManager().send(201);
							}
							entity.getAnimator().forceAnimation(new Animation(getSettings().getEndEmote(), Priority.HIGH));
							entity.graphics(new Graphics(getSettings().getEndGfx()));
							return true;
						}
						delay++;
						return false;
					}

					@Override
					public void stop() {
						super.stop();
						entity.unlock();
						entity.lock(4);
					}
				};
			}
		};

		/**
		 * The NodeSettings
		 */
		private TeleportSettings settings;

		/**
		 * @param entity
		 * @param location
		 * @return the Pulse
		 */
		public abstract Pulse getPulse(final Entity entity, final Location location);

		/**
		 * Constructs a new {@code Teleporter.java} {@Code Object}.
		 * @param settings the NodeSettings
		 */
		TeleportType(TeleportSettings settings) {
			this.settings = settings;
		}

		/**
		 * Gets the NodeSettings
		 * @return the NodeSettings
		 */
		public final TeleportSettings getSettings() {
			return settings;
		}
	}

	/**
	 * Represents teleport node settings
	 * @author SonicForce41
	 */
	static class TeleportSettings {

		/**
		 * The start animation.
		 */
		private int startAnim;

		/**
		 * The end animation.
		 */
		private int endAnim;

		/**
		 * The start graphics.
		 */
		private int startGFX;

		/**
		 * The end gfx.
		 */
		private int endGFX;

		/**
		 * Constructs a new {@code Teleporter.java} {@code Object}.
		 * @param startAnim the start animation.
		 * @param endAnim the end animation.
		 * @param startGfx the start graphics.
		 * @param endGfx the end graphiics.
		 * @param type the end type.
		 */
		public TeleportSettings(int startAnim, int endAnim, int startGfx, int endGfx) {
			this.startAnim = startAnim;
			this.endAnim = endAnim;
			this.startGFX = startGfx;
			this.endGFX = endGfx;
		}

		/**
		 * @return the anim.
		 */
		public final int getStartEmote() {
			return startAnim;
		}

		/**
		 * @return the anim.
		 */
		public final int getEndEmote() {
			return endAnim;
		}

		/**
		 * @return the start graphics.
		 */
		public final int getStartGfx() {
			return startGFX;
		}

		/**
		 * @return the end gfx.
		 */
		public final int getEndGfx() {
			return endGFX;
		}
	}

	/**
	 * Gets the teleporting type for the player depending on his/her current
	 * spellbook.
	 * @param player The player.
	 * @return The teleport type.
	 */
	public static TeleportType getType(Player player) {
		switch (player.getSpellBookManager().getSpellBook()) {
		case 193:
			return TeleportType.ANCIENT;
		case 430:
			return TeleportType.LUNAR;
		}
		return TeleportType.NORMAL;
	}

	/**
	 * Gets the teleportType.
	 * @return The teleportType.
	 */
	public int getTeleportType() {
		return teleportType;
	}

	/**
	 * Sets the teleportType.
	 * @param teleportType The teleportType to set.
	 */
	public void setTeleportType(int teleportType) {
		this.teleportType = teleportType;
	}
}
