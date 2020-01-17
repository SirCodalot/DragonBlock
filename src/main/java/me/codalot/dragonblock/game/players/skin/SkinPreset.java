package me.codalot.dragonblock.game.players.skin;

import lombok.Getter;

@Getter
public enum SkinPreset {

    // TODO enter real values

    SUPER_SAIYAN_FOUR("", "");

    private Skin skin;

    SkinPreset(String texture, String signature) {
        skin = new Skin(texture, signature);
    }

}
