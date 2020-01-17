package me.codalot.dragonblock.game.models;

import lombok.Getter;
import me.codalot.dragonblock.game.players.components.Sight;
import me.codalot.dragonblock.utils.PacketUtils;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

@Getter
public class ModelStand {

    private Sight sight;

    private EntityArmorStand nmsStand;
    private ArmorStand stand;

    private ItemStack item;

    private PacketPlayOutSpawnEntityLiving spawnPacket;
    private PacketPlayOutEntityMetadata dataPacket;
    private PacketPlayOutEntityEquipment equipPacket;
    private PacketPlayOutEntityDestroy destroyPacket;

    public ModelStand(ItemStack item, Location location, Sight sight) {
        this.sight = sight;
        this.item = item;

        createEntity(location);
        createPackets();

        sight.broadcast(spawnPacket, dataPacket, equipPacket);
    }

    public ModelStand(ItemStack item, Location location) {
        this(item, location, new Sight(location));
    }


    private void createEntity(Location location) {
        nmsStand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ());
        stand = (ArmorStand) nmsStand.getBukkitEntity();

        nmsStand.setMarker(true);
        nmsStand.setSilent(true);
        nmsStand.setInvulnerable(true);
        nmsStand.setInvisible(true);
    }

    private void createPackets() {
        spawnPacket = new PacketPlayOutSpawnEntityLiving(nmsStand);
        dataPacket = new PacketPlayOutEntityMetadata(nmsStand.getId(), nmsStand.getDataWatcher(), true);
        equipPacket = new PacketPlayOutEntityEquipment(nmsStand.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(item));
        destroyPacket = new PacketPlayOutEntityDestroy(nmsStand.getId());
    }

    public void update() {
        sight.update(
                (player) -> PacketUtils.sendPackets(player, spawnPacket, dataPacket, equipPacket),
                (player) -> PacketUtils.sendPackets(player, destroyPacket)
        );
    }

    public void setItem(ItemStack item) {
        this.item = item;
        equipPacket = new PacketPlayOutEntityEquipment(nmsStand.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(item));
        sight.broadcast(dataPacket, equipPacket);
    }

    public void remove() {
        sight.broadcast(destroyPacket);
        stand.remove();
    }

}
