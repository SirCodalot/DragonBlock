package me.codalot.dragonblock.players;

import io.netty.channel.*;
import me.codalot.dragonblock.events.packets.PacketPlayInEvent;
import me.codalot.dragonblock.events.packets.PacketPlayOutEvent;
import me.codalot.dragonblock.utils.EventUtils;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;

@ChannelHandler.Sharable
public class PlayerConnection extends ChannelDuplexHandler {

    private PlayerData data;

    public PlayerConnection(PlayerData data) {
        this.data = data;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketPlayInEvent event = new PacketPlayInEvent(data, (Packet) msg);
        EventUtils.callEvent(event);

        if (event.isCancelled())
            return;

        super.channelRead(ctx, event.getPacket());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        PacketPlayOutEvent event = new PacketPlayOutEvent(data, (Packet) msg);
        EventUtils.callEvent(event);

        if (event.isCancelled())
            return;

        super.write(ctx, event.getPacket(), promise);
    }

    public void addToPipeline() {
        if (!data.isOnline())
            return;

        getPlayerPipeline(data).addBefore("packet_handler", data.getUuid().toString(), this);
    }

    public void removeFromPipeline() {
        if (!data.isOnline())
            return;

        try {
            getPlayerPipeline(data).remove(this);
        } catch (Exception ignored) { }
    }

    private static ChannelPipeline getPlayerPipeline(PlayerData data) {
        return ((CraftPlayer) data.getPlayer()).getHandle().playerConnection.networkManager.channel.pipeline();
    }

}
