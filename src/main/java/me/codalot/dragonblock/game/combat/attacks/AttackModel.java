package me.codalot.dragonblock.game.combat.attacks;

import lombok.Getter;
import me.codalot.dragonblock.setup.Model;
import org.bukkit.inventory.ItemStack;

@Getter
public class AttackModel {

    private final ItemStack head;
    private final ItemStack body;
    private final ItemStack charge;

    public AttackModel(ItemStack head, ItemStack body, ItemStack charge) {
        this.head = head;
        this.body = body;
        this.charge = charge;
    }

}
