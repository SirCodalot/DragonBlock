package me.codalot.dragonblock.game.players.skin;

import com.mojang.authlib.properties.Property;
import lombok.Getter;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

@Getter
public class Skin {

    private Collection<Property> properties;

    public Skin(String value, String signature) {
        properties = new HashSet<>();
        properties.add(new Property("textures", value, signature));
    }

    public Skin(Player player) {
        properties = ((CraftPlayer) player).getHandle().getProfile().getProperties().get("textures");
    }
}
