package me.codalot.dragonblock.utils;

import com.google.common.collect.Maps;
import net.minecraft.server.v1_15_R1.*;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PacketUtils {

    public static EntityPlayer getNmsPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static void sendPackets(Player player, Packet... packets) {
        PlayerConnection connection = getNmsPlayer(player).playerConnection;

        for (Packet packet : packets)
            connection.sendPacket(packet);
    }

    public static void broadcast(Collection<? extends Player> players, Packet... packets) {
        players.forEach(player -> sendPackets(player, packets));
    }

    public static void broadcast(Packet... packets) {
        broadcast(Bukkit.getOnlinePlayers(), packets);
    }

    public static void broadcast(Location location, Packet... packets) {
        Set<Player> players = new HashSet<>();
        location.getWorld().getNearbyEntities(location, 40, 40, 40).forEach(entity -> {
            if (entity instanceof Player)
                players.add((Player) entity);
        });

        broadcast(players, packets);
    }

    public static void updatePlayer(Player player, Player target) {
        EntityPlayer eTarget = getNmsPlayer(target);
        sendPackets(player, new PacketPlayOutEntityMetadata(eTarget.getId(), eTarget.getDataWatcher(), true));
    }

    public static DataWatcher getGlowWatcher(Player player, boolean glow) {
        EntityPlayer ePlayer = getNmsPlayer(player);
        DataWatcher watcher = ePlayer.getDataWatcher();
        DataWatcher cloned = new DataWatcher(ePlayer);

        Map<Integer, DataWatcher.Item<?>> currentMap = ReflectionUtils.getField(watcher, "entries");
        final Map<Integer, DataWatcher.Item<?>> newMap = new Int2ObjectOpenHashMap<>();

        for (Integer integer : currentMap.keySet())
            newMap.put(integer, currentMap.get(integer).d());

        DataWatcher.Item item = newMap.get(0);
        byte initialBitMask = (Byte) item.b();
        byte bitMaskIndex = (byte) 7;
        if (glow) {
            item.a((byte) (initialBitMask | 1 << bitMaskIndex));
        } else {
            item.a((byte) (initialBitMask & ~(1 << bitMaskIndex)));
        }
        newMap.put(0, item);

        ReflectionUtils.setField(DataWatcher.class, cloned, "entries", newMap);

        return cloned;
    }

}
