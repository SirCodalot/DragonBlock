package me.codalot.dragonblock.game.players.skin;

import com.mojang.authlib.GameProfile;
import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.players.Fighter;
import me.codalot.dragonblock.utils.PacketUtils;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Getter
public class SkinHandler {

    private static final DragonBlock PLUGIN = DragonBlock.getInstance();

    private Fighter fighter;

    private Skin original;
    private Skin current;

    public SkinHandler(Fighter fighter) {
        this.fighter = fighter;

        original = new Skin(fighter.getPlayer());
        current = null;
    }

    public boolean hasSkin() {
        return current != null;
    }

    public void resetSkin() {
        setSkin(original);
    }

    public void setSkin(Skin skin) {
        if (skin == null)
            skin = original;

        if (!skin.equals(original)) {
            if (skin.equals(current))
                return;

            current = skin;
        } else if (current == null)
            return;
        else
            current = null;

        CraftPlayer cPlayer = (CraftPlayer) fighter.getPlayer();
        GameProfile profile = cPlayer.getProfile();

        profile.getProperties().removeAll("textures");
        profile.getProperties().putAll("textures", skin.getProperties());

        refreshSkin();
    }

    private void refreshSkin() {
        refreshSelf();
        refreshOthers();
    }

    private void refreshSelf() {
        Player player = fighter.getPlayer();
        EntityPlayer ePlayer = ((CraftPlayer) player).getHandle();

        try {
            // Creating Information Packets
            PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ePlayer);
            PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ePlayer);

            // Creating Respawn Packet
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(
                    ePlayer.getWorldServer().worldProvider.getDimensionManager(),
                    ePlayer.getWorld().getDifficulty().a(),
                    ePlayer.getWorld().getWorldData().getType(),
                    EnumGamemode.valueOf(player.getGameMode().toString())
            );

            // Creating Position Packet
            Location location = player.getLocation();
            PacketPlayOutPosition positionPacket = new PacketPlayOutPosition(
                    location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(),
                    new HashSet<>(Arrays.asList(PacketPlayOutPosition.EnumPlayerTeleportFlags.values())),
                    ePlayer.getId()
            );

            // Saving Player Information
            boolean flying = player.isFlying();
            int level = player.getLevel();
            float xp = player.getExp();
            double maxHealth = player.getMaxHealth();
            double health = player.getHealth();
            int slot = player.getInventory().getHeldItemSlot();

            // Removing and then Respawning the Player
            PacketUtils.sendPackets(player, removePacket, respawnPacket);

            // Setting the Player's Information
            player.setFlying(flying);
            player.teleport(location);
            player.updateInventory();
            player.setLevel(level);
            player.setExp(xp);
            player.setMaxHealth(maxHealth);
            player.setHealth(health);
            player.getInventory().setHeldItemSlot(slot);

            // Adding the Player and Positioning it
            PacketUtils.sendPackets(player, addPacket, positionPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshOthers() {
        Player player = fighter.getPlayer();
        List<Player> canSee = new ArrayList<>();
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player))
                continue;

            if (player1.canSee(player)) {
                canSee.add(player1);
                player1.hidePlayer(PLUGIN, player);
            }
        }
        for (Player player1 : canSee) {
            if (player1.equals(player))
                continue;

            player1.showPlayer(PLUGIN, player);
        }
    }
}
