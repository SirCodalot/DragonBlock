package me.codalot.dragonblock.files;

import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.utils.CollectionUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@SuppressWarnings({"WeakerAccess", "unused"})
public class YamlFile extends YamlConfiguration {

    protected String name;

    protected File folder;
    protected File file;

    protected DragonBlock plugin;

    public YamlFile(DragonBlock plugin, String name, File folder) {
        this.name = name + ".yml";
        this.folder = folder;
        this.plugin = plugin;

        load();
    }

    @SuppressWarnings("all")
    public void load() {
        file = new File(folder, name);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            options().indent(2);
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        save();
        load();
    }

    public String getColoredString(String key, String def) {
        String string = getString(key);
        return string == null ? def : ChatColor.translateAlternateColorCodes('&', string);
    }

    public String getColoredString(String key) {
        return getColoredString(key, null);
    }

    public List<String> getColoredStringlist(String key) {
        List<String> list = new ArrayList<>();

        if (!contains(key))
            return list;

        getStringList(key).forEach(line -> list.add(ChatColor.translateAlternateColorCodes('&', line)));

        return list;
    }

    public List<String> getColoredStringList(String key) {

        if (get(key) instanceof String) {
            List<String> list = new ArrayList<>();
            list.add(getColoredString(key));
            return list;
        } else if (get(key) instanceof List) {
            List<String> list = new ArrayList<>();
            getStringList(key).forEach(line -> list.add(ChatColor.translateAlternateColorCodes('&', line)));
            return list;
        }

        return null;
    }

    @SuppressWarnings("all")
    public ItemStack getItem(String path, ItemStack def) {
        if (!contains(path))
            return def;

        try {
            Material material = Material.matchMaterial(getString(path + ".material"));
            int amount = getInt(path + ".amount", 1);
            int model = getInt(path + ".model", 0);
            int durability = getInt(path + ".durability", 0);
            boolean glow = getBoolean(path + ".glow", false);
            boolean flags = getBoolean(path + ".flags", true);
            String name = getColoredString(path + ".name");
            List<String> lore = getColoredStringlist(path + ".lore");

            ItemStack item = new ItemStack(material);
            item.setAmount(amount);
            item.setDurability((short) durability);

            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(model);
            meta.setDisplayName(name);
            meta.setLore(lore);

            if (glow)
                meta.addEnchant(Enchantment.DURABILITY, 1, true);

            if (flags)
                meta.addItemFlags(ItemFlag.values());

            item.setItemMeta(meta);
            return item;
        } catch (Exception ignored) {}

        return null;
    }

    public ItemStack getItem(String path) {
        return getItem(path, null);
    }

    @SuppressWarnings("all")
    public Map<String, Object> getMap(String key, Map<String, Object> def) {
        return get(key) == null ? def : CollectionUtils.toMap(getConfigurationSection(key), true);
    }

    public Map<String, Object> getMap(String key) {
        return getMap(key, new HashMap<>());
    }

    @SuppressWarnings("all")
    public Map<String, Object> asMap() {
        return CollectionUtils.toMap(getConfigurationSection(""), true);
    }

    public void set(Map<String, Object> map) {
        map.forEach(this::set);
    }
}