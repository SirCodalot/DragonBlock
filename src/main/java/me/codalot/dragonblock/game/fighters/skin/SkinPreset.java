package me.codalot.dragonblock.game.fighters.skin;

import lombok.Getter;

@Getter
public enum SkinPreset {

    // Goku Skins
    GOKU_Z_BASE("", ""),
    GOKU_Z_SUPER_SAIYAN("", ""),
    GOKU_Z_SUPER_SAIYAN_THREE("", ""),

    GOKU_GT_BASE("", ""),
    GOKU_GT_SUPER_SAIYAN("", ""),
    GOKU_GT_SUPER_SAIYAN_THREE("", ""),
    GOKU_GT_SUPER_SAIYAN_FOUR("", ""),

    GOKU_SUPER_BASE("", ""),
    GOKU_SUPER_SUPER_SAIYAN_GOD("", ""),
    GOKU_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN("", ""),
    GOKU_SUPER_ULTRA_INSTINCT("", ""),

    // Vegeta Skins
    VEGETA_Z_BASE("", ""),
    VEGETA_Z_GREAT_APE("", ""),
    VEGETA_Z_SUPER_SAIYAN("", ""),

    VEGETA_GT_BASE("", ""),
    VEGETA_GT_SUPER_SAIYAN_FOUR("", ""),

    VEGETA_SUPER_BASE("", ""),
    VEGETA_SUPER_SUPER_SAIYAN_GOD("", ""),
    VEGETA_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN("", ""),
    VEGETA_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION("", ""),

    // Krillin Skins
    KRILLIN_Z("", ""),

    // Yamcha Skins
    YAMCHA_Z("", ""),

    // Tien Skins
    TIEN_Z("", ""),

    // Piccolo Skins
    PICCOLO_DEMON_KING("", ""),
    PICCOLO("", "")

    ;

    private Skin skin;

    SkinPreset(String texture, String signature) {
        skin = new Skin(texture, signature);
    }

}
