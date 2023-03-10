package org.arios.net.packet.context;

import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.Context;

/**
 * The context for ClientScript configurations.
 *
 * @author Torchic
 */
public class CSConfigContext implements Context {

    /**
     * The player reference.
     */
    private Player player;

    /**
     * The value
     */
    private int value;

    /**
     * The id.
     */
    private int id;

    /**
     * Constructs a new {@Code CSConfigContext} {@Code Object}
     *
     * @param player The player.
     * @param value  The config value.
     * @param id     The id.
     */
    public CSConfigContext(Player player, int id, int value) {
        this.player = player;
        this.value = value;
        this.id = id;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player.
     *
     * @param player The player.
     * @return This context instance.
     */
    public Context setPlayer(Player player) {
        this.player = player;
        return this;
    }

    /**
     * Get the config value.
     *
     * @return The config value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the config id.
     *
     * @return The config id.
     */
    public int getId() {
        return id;
    }

}
