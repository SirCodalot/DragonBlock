package me.codalot.dragonblock.events.packets;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.players.PlayerData;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@Getter
public abstract class PacketEvent extends Event implements Cancellable {

    protected PlayerData data;
    @Setter protected Packet packet;
    @Setter protected boolean cancelled;

    public PacketEvent(PlayerData data, Packet packet) {
        this.data = data;
        this.packet = packet;
        cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
