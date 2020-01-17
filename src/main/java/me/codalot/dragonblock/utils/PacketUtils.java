package me.codalot.dragonblock.utils;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtils {

    public static EntityPlayer getNmsPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static void sendPackets(Player player, Packet... packets) {
        PlayerConnection connection = getNmsPlayer(player).playerConnection;

        for (Packet packet : packets)
            connection.sendPacket(packet);
    }

    public static void broadcast(Packet... packets) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> sendPackets(player, packets));
    }

}
