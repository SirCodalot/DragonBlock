package me.codalot.dragonblock.events.packets;

import me.codalot.dragonblock.players.PlayerData;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.event.HandlerList;

public class PacketPlayOutEvent extends PacketEvent {

    private static final HandlerList handlers = new HandlerList();

    public PacketPlayOutEvent(PlayerData data, Packet packet) {
        super(data, packet);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
