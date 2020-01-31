package me.codalot.dragonblock.game.fighters.cosmetics;

import lombok.Getter;
import org.bukkit.Color;

@Getter
public enum ColorPallet {

    HAIR_BASE_SAIYAN(28, 28, 28),
    HAIR_SUPER_SAIYAN(255, 250, 107),
    HAIR_SUPER_SAIYAN_TWO(255, 239, 20),
    HAIR_SUPER_SAIYAN_FOUR(28, 28, 28),
    HAIR_SUPER_SAIYAN_GOD(250, 22, 53),
    HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN(15, 183, 255),
    HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION(33, 77, 235),
    HAIR_SUPER_SAIYAN_ROSE(224, 126, 140),
    HAIR_SAIYAN_ULTRA_INSTINCT(237, 237, 237),

    AURA_NORMAL(237, 237, 237),
    AURA_SUPER_SAIYAN(HAIR_SUPER_SAIYAN),
    AURA_SUPER_SAIYAN_THREE(HAIR_SUPER_SAIYAN_TWO),
    AURA_SUPER_SAIYAN_FOUR(255, 239, 20),
    AURA_SUPER_SAIYAN_GOD_SUPER_SAIYAN(HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN),
    AURA_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION(HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION);

    private Color color;

    ColorPallet(Color color) {
        this.color = color;
    }

    ColorPallet(int red, int green, int blue) {
        this(Color.fromRGB(red, green, blue));
    }

    ColorPallet(ColorPallet other) {
        this.color = other.color;
    }

}
