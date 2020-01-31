package me.codalot.dragonblock.game.fighters.components;

import me.codalot.dragonblock.game.models.ModelStand;
import me.codalot.dragonblock.game.fighters.Fighter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerModels {

    private Fighter fighter;

    private Map<ModelStand, String> models;

    public PlayerModels(Fighter fighter) {
        this.fighter = fighter;
        models = new HashMap<>();
    }

    public void update() {

    }

    public int amount(String tag) {
        int count = 0;
        for (ModelStand model : models.keySet())
            if (models.get(model).equalsIgnoreCase(tag))
                count++;
        return count;
    }

    public void add(ItemStack item, String tag) {
        fighter.getSight().update();

        ModelStand model = new ModelStand(item, fighter.getPlayer().getLocation(), fighter.getSight());
        fighter.getPlayer().addPassenger(model.getStand());
        models.put(model, tag);
    }

    public void remove(String tag) {
        if (models.isEmpty())
            return;

        Set<ModelStand> stands = new HashSet<>(models.keySet());
        for (ModelStand model : stands)
            if (models.get(model).equalsIgnoreCase(tag)) {
                models.remove(model);
                model.remove();
            }
    }

    public void set(ItemStack item, String tag) {
        remove(tag);
        add(item, tag);
    }


}
