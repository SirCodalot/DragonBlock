package me.codalot.dragonblock.players;

import lombok.Getter;
import me.codalot.dragonblock.files.YamlFile;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.FighterData;
import me.codalot.dragonblock.game.fighters.FighterPreset;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
        ejectFighter(false);
        fighter = new Fighter(uuid, data);
    }

    public void ejectFighter(boolean reskin) {
        if (fighter == null)
            return;

        if (reskin)
            fighter.getSkin().resetSkin();

        fighter.unload();
        fighter = null;

        resetPlayer();
    }

    public void ejectFighter() {
        ejectFighter(true);
    }

    private void resetPlayer() {
        Player player = getPlayer();

        player.getInventory().setHelmet(null);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGliding(false);
        player.setFlySpeed(.1f);
        player.setExp(0);
        player.setLevel(0);
        player.setHealth(20);
        player.setFoodLevel(20);
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
