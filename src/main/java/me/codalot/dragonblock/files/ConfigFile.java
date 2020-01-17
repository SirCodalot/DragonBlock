package me.codalot.dragonblock.files;

import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;

@Getter
public enum ConfigFile {

    MODELS("models");

    private ResourceFile file;

    ConfigFile(String name) {
        file = new ResourceFile(DragonBlock.getInstance(), name);
    }

    public static void reload() {
        for (ConfigFile file : values())
            file.file.reload();
    }
}
