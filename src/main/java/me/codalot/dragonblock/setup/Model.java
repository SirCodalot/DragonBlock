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
    AURA_NORMAL(Material.LEATHER_BOOTS, 1),
    AURA_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 1001),
    AURA_SUPER_SAIYAN_GOD(Material.LEATHER_BOOTS, 1002),
    AURA_SUPER_SAIAN_ROSE(Material.LEATHER_BOOTS, 1003),
    AURA_KAIOKEN(Material.LEATHER_BOOTS, 1004),
    AURA_ULTRA_INSTINCT(Material.LEATHER_BOOTS, 1005),

    // Goku Models
    HAIR_GOKU_BASE(Material.LEATHER_BOOTS, 100),

    HAIR_GOKU_SUPER_SAIYAN(Material.LEATHER_BOOTS, 101),
    HAIR_GOKU_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 102),
    HAIR_GOKU_SUPER_SAIYAN_THREE(Material.LEATHER_BOOTS, 103),
    HAIR_GOKU_SUPER_SAIYAN_FOUR(Material.LEATHER_BOOTS, 104),

    HAIR_GOKU_ULTRA_INSTINCT(Material.LEATHER_BOOTS, 105),

    // Vegeta Models
    HAIR_VEGETA_BASE(Material.LEATHER_BOOTS, 1050),

    HAIR_VEGETA_SUPER_SAIYAN(Material.LEATHER_BOOTS, 1051),
    HAIR_VEGETA_SUPER_SAIYAN_TWO(Material.LEATHER_BOOTS, 1052),
    HAIR_VEGETA_SUPER_SAIYAN_FOUR(Material.LEATHER_BOOTS, 1053),

    // Attacks
    KAMEHAMEHA_BODY(Material.LEATHER_BOOTS, 50),
    KAMEHAMEHA_HEAD(Material.LEATHER_BOOTS, 51),


    // Charge Animations
    HANDS_FORWARD(Material.CROSSBOW, 1);


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
