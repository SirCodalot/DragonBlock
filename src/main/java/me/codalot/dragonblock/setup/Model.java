package me.codalot.dragonblock.setup;

import me.codalot.dragonblock.files.ConfigFile;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public enum Model {

    // Auras
    AURA_NORMAL(Material.LEATHER_BOOTS, 500),
    AURA_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 501),
    AURA_SUPER_SAIYAN_GOD(Material.LEATHER_BOOTS, 502),
    AURA_SUPER_SAIAN_ROSE(Material.LEATHER_BOOTS, 503),
    AURA_KAIOKEN(Material.LEATHER_BOOTS, 504),
    AURA_ULTRA_INSTINCT(Material.LEATHER_BOOTS, 505),

    // Goku Models
    HAIR_GOKU_BASE(Material.LEATHER_BOOTS, 1000),

    HAIR_GOKU_SUPER_SAIYAN(Material.LEATHER_BOOTS, 1001),
    HAIR_GOKU_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 1002),
    HAIR_GOKU_SUPER_SAIYAN_THREE(Material.LEATHER_BOOTS, 1003),
    HAIR_GOKU_SUPER_SAIYAN_FOUR(Material.LEATHER_BOOTS, 1004),

    HAIR_GOKU_ULTRA_INSTINCT(Material.LEATHER_BOOTS, 1005),

    // Vegeta Models
    HAIR_VEGETA_BASE(Material.LEATHER_BOOTS, 1050),

    HAIR_VEGETA_SUPER_SAIYAN(Material.LEATHER_BOOTS, 1051),
    HAIR_VEGETA_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 1052),
    HAIR_VEGETA_SUPER_SAIYAN_FOUR(Material.LEATHER_BOOTS, 1053)

    ;


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
