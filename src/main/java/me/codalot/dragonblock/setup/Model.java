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

    HAIR_NORMAL(Material.LEATHER_BOOTS, 100, Color.BLACK),

    HAIR_SUPER_SAIYAN(Material.LEATHER_BOOTS, 100, Color.fromRGB(255, 250, 107)),
    HAIR_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 100, Color.fromRGB(255, 239, 20)),
    HAIR_SUPER_SAIYAN_THREE(Material.LEATHER_BOOTS, 101, Color.fromRGB(255, 239, 20)),
    HAIR_SUPER_SAIYAN_FOUR(Material.LEATHER_BOOTS, 102, Color.BLACK),

    HAIR_SUPER_SAIYAN_GOD(Material.LEATHER_BOOTS, 100, Color.fromRGB(250, 22, 53)),
    HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN(Material.LEATHER_BOOTS, 100, Color.fromRGB(15, 183, 255)),
    HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION(Material.LEATHER_BOOTS, 100, Color.fromRGB(33, 77, 235));

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

        if (color != null)
            meta.setColor(color);

        item.setItemMeta(meta);
        return item;
    }

}
