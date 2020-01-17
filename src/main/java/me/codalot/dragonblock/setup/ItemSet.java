package me.codalot.dragonblock.setup;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class ItemSet extends HashSet<ItemStack> {

    public ItemSet(ItemStack... items) {
        super();

        for (ItemStack item : items)
            add(item);
    }

    public void drop(Location location) {
        forEach(item -> location.getWorld().dropItemNaturally(location, item));
    }

    private ItemStack[] cloneItems() {
        ItemStack[] items = new ItemStack[size()];

        int i = 0;
        for (ItemStack item : this) {
            items[i] = item.clone();
            i++;
        }

        return items;
    }

    @Override
    public ItemSet clone() {
        return new ItemSet(cloneItems());
    }
}
