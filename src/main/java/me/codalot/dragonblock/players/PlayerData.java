package me.codalot.dragonblock.players;

import lombok.Getter;
import me.codalot.dragonblock.files.YamlFile;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.FighterData;
import me.codalot.dragonblock.game.fighters.FighterPreset;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class PlayerData implements ConfigurationSerializable {

    private YamlFile file;

    private UUID uuid;
    private String name;

    private PlayerConnection connection;

    private Set<FighterPreset> fighters;
    private FighterData data;

    private Fighter fighter;

    public PlayerData(YamlFile file, UUID uuid) {
        this.file = file;
        this.uuid = uuid;
        OfflinePlayer player = getOfflinePlayer();

        name = player.getName();

        connection = new PlayerConnection(this);

        fighters = new HashSet<>();
        if (file.contains("fighters"))
            file.getStringList("fighters").forEach(fighter -> fighters.add(FighterPreset.valueOf(fighter)));
        data = new FighterData(file.getMap("data", new HashMap<>()));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();

        List<String> fighters = new ArrayList<>();
        this.fighters.forEach(fighter -> fighters.add(fighter.toString()));
        serialized.put("fighters", fighters);
        serialized.put("data", data.serialize());

        return serialized;
    }

    public void possessFighter(FighterData data) {
        ejectFighter();
        fighter = new Fighter(uuid, data);
    }

    public void ejectFighter() {
        if (fighter == null)
            return;

        fighter.unload();
        fighter = null;

        getPlayer().getInventory().setHelmet(null);
    }

    public void save() {
        file.set(serialize());
        file.save();
    }

    public void onJoin() {
        connection.addToPipeline();
    }

    public void onQuit() {
        save();

        ejectFighter();
        connection.removeFromPipeline();
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }
}
