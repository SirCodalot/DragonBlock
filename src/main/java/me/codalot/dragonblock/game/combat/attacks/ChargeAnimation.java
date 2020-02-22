package me.codalot.dragonblock.game.combat.attacks;

import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.setup.Model;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum  ChargeAnimation {

    NONE(f -> {}, f -> {}),
    HAND_UP(f -> {}, f -> {}),
    HANDS_FORWARD(fighter -> {
        ItemStack item = Model.HANDS_FORWARD.get();
        CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
        List<ItemStack> projectiles = new ArrayList<>();
        projectiles.add(new ItemStack(Material.ARROW));
        meta.setChargedProjectiles(projectiles);
        item.setItemMeta(meta);
        fighter.getPlayer().getInventory().setItemInMainHand(item);
    }, fighter -> {
        fighter.getPlayer().getInventory().setItemInMainHand(null);
    });

    private Consumer<Fighter> start;
    private Consumer<Fighter> end;

    ChargeAnimation(Consumer<Fighter> start, Consumer<Fighter> end) {
        this.start = start;
        this.end = end;
    }

    public void start(Fighter fighter) {
        start.accept(fighter);
    }

    public void end(Fighter fighter) {
        end.accept(fighter);
    }

}
