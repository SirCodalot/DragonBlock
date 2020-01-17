package me.codalot.dragonblock.setup;

import me.codalot.dragonblock.files.ConfigFile;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public enum Model {

    AURA_SMALL(Material.LEATHER_BOOTS, 1, Color.WHITE),
    AURA_NORMAL(Material.LEATHER_BOOTS, 2, Color.WHITE),

    HAIR_SUPER_SAIYAN(Material.LEATHER_BOOTS, 100, Color.YELLOW);

    private ItemStack base;

    Model() {
        base = ConfigFile.MODELS.getFile().getItem(toString().toLowerCase());
    }

    Model(Material material, int data) {
        base = new ItemStack(material);
        ItemMeta meta = base.getItemMeta();
        meta.setCustomModelData(data);
        meta.addItemFlags(ItemFlag.values());
        base.setItemMeta(meta);
    }

    Model(Material material, int data, Color color) {
        this(material, data);
        LeatherArmorMeta meta = (LeatherArmorMeta) base.getItemMeta();
        meta.setColor(color);
        base.setItemMeta(meta);
    }

    public ItemStack get() {
        return base.clone();
    }

    public ItemStack get(Color color) {
        ItemStack item = get();
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(color);

        item.setItemMeta(meta);
        return item;
    }

}
