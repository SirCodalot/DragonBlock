package me.codalot.dragonblock.game.fighters.components;

import lombok.Getter;
import me.codalot.dragonblock.game.fighters.cosmetics.Appearance;
import me.codalot.dragonblock.game.fighters.cosmetics.AppearancePreset;
import org.apache.commons.lang.StringUtils;

@Getter
public enum Form {

    // Saiyan Forms
    SAIYAN_ULTRA_INSTINCT(250, AppearancePreset.EMPTY_ULTRA_INSTINCT.getAppearance()),

    SUPER_SAIYAN_ROSE(250, AppearancePreset.EMPTY_ROSE.getAppearance()),

    SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION(250, AppearancePreset.EMPTY_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION.getAppearance()),
    SUPER_SAIYAN_GOD_SUPER_SAIYAN(250, AppearancePreset.EMPTY_SUPER_SAIYAN_GOD_SUPER_SAIYAN.getAppearance(), SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION),
    SUPER_SAIYAN_GOD(250, AppearancePreset.EMPTY_SUPER_SAIYAN_GOD.getAppearance(), SUPER_SAIYAN_GOD_SUPER_SAIYAN),

    SUPER_SAIYAN_FOUR(250, AppearancePreset.EMPTY_SUPER_SAIYAN_FOUR.getAppearance()),
    SUPER_SAIYAN_THREE(250, AppearancePreset.EMPTY_SUPER_SAIYAN_THREE.getAppearance(), SUPER_SAIYAN_FOUR),
    SUPER_SAIYAN_TWO(250, AppearancePreset.EMPTY_SUPER_SAIYAN_TWO.getAppearance(), SUPER_SAIYAN_THREE),
    SUPER_SAIYAN(250, AppearancePreset.EMPTY_SUPER_SAIYAN.getAppearance(), SUPER_SAIYAN_TWO),

    GOLDEN_GREAT_APE(SUPER_SAIYAN_FOUR),
    GREAT_APE(GOLDEN_GREAT_APE),

    BASE_SAIYAN(250, AppearancePreset.EMPTY_BASE.getAppearance(), GREAT_APE, SUPER_SAIYAN, SUPER_SAIYAN_GOD, SUPER_SAIYAN_ROSE, SAIYAN_ULTRA_INSTINCT)

    ;

    private String name;
    private Appearance appearance;

    private int requiredKi;

    private Form[] next;

    private boolean base;

    Form(int requiredKi, Appearance appearance, Form... next) {
        this.name = StringUtils.capitalize(toString().toLowerCase().replace("_", " "));
        this.appearance = appearance;

        this.requiredKi = requiredKi;

        this.next = next;

        base = name().startsWith("BASE_");
    }

    Form(Form... next) {
        this(0, null, next);
    }

    public boolean turnsInto(Form form) {
        if (form.isBase())
            return true;

        for (Form next : this.next)
            if (next == form)
                return true;
        return false;
    }

}
