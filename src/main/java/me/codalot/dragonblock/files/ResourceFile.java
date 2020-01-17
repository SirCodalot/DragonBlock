package me.codalot.dragonblock.files;

import me.codalot.dragonblock.DragonBlock;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class ResourceFile extends YamlFile {

    public ResourceFile(DragonBlock plugin, String name) {
        super(plugin, name, plugin.getDataFolder());
    }

    @Override
    @SuppressWarnings("all")
    public void load() {
        file = new File(folder, name);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        try {
            load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

}
