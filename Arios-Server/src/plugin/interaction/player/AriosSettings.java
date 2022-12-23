package plugin.interaction.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.DefaultContext;
import org.arios.net.packet.context.DisplayModelContext;
import org.arios.net.packet.context.DisplayModelContext.ModelType;
import org.arios.net.packet.out.AriosSettingsPacket;
import org.arios.net.packet.out.DisplayModel;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;

/**
 * Handles the barrows puzzle.
 * @author Emperor
 */
public final class AriosSettings extends ComponentPlugin {

     /**
     * Sends the settings
     *
     * @param player
     */
    public static void sendSettings(Player player) {
        PacketRepository.send(AriosSettingsPacket.class, new DefaultContext(player, true));
    }

    @Override
    public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
        return false;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ComponentDefinition.put(836, this);
		return this;
    }
}