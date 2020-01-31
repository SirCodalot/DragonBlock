package me.codalot.dragonblock.game.fighters.components;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.utils.PacketUtils;
import me.codalot.dragonblock.utils.TimeUtils;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class Sight {

    private static final int UPDATE_GAP = 20;
    private static final double SIGHT_RADIUS = 35;

    @Setter private Location location;

    private Set<UUID> inSight;
    private Set<UUID> entered;
    private Set<UUID> left;

    private int lastUpdate;

    public Sight(Location location) {
        this.location = location;

        inSight = new HashSet<>();
        entered = new HashSet<>();
        left = new HashSet<>();
    }

    public void update(Consumer<Player> entered, Consumer<Player> left) {
        if (!isOutdated()) {
            if (entered != null)
                getPlayersSet(this.entered).forEach(entered::accept);
            if (left != null)
                getPlayersSet(this.left).forEach(entered::accept);

            return;
        }

        this.entered.clear();
        this.left.clear();

        Set<UUID> currentSight = new HashSet<>();
        for (Entity entity : location.getWorld().getNearbyEntities(location, SIGHT_RADIUS, SIGHT_RADIUS, SIGHT_RADIUS)) {
            if (!(entity instanceof Player))
                continue;

            currentSight.add(entity.getUniqueId());

            if (!inSight.contains(entity.getUniqueId())) {
                this.entered.add(entity.getUniqueId());
                if (entered != null)
                    entered.accept((Player) entity);
            }
        }

        for (UUID uuid : inSight) {
            Player player = Bukkit.getPlayer(uuid);
            if (currentSight.contains(uuid))
                continue;

            if (left != null)
                left.accept(player);

            this.left.add(uuid);
        }

        inSight = currentSight;
    }

    public void update() {
        update(null, null);
    }

    public boolean isOutdated() {
        return TimeUtils.hasTimePassed(lastUpdate, UPDATE_GAP);
    }

    public void broadcast(Packet... packets) {
        for (UUID uuid : inSight) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                PacketUtils.sendPackets(player, packets);
        }
    }

    public void broadcast(String... messages) {
        for (UUID uuid : inSight) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                player.sendMessage(messages);
        }
    }

    private static Set<Player> getPlayersSet(Collection<UUID> uuids) {
        Set<Player> players = new HashSet<>();
        uuids.forEach(uuid -> players.add(Bukkit.getPlayer(uuid)));
        players.remove(null);
        return players;
    }
}
