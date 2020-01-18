package me.codalot.dragonblock.game.players.components;

import lombok.Getter;
import me.codalot.dragonblock.game.players.skin.SkinPreset;
import me.codalot.dragonblock.setup.Model;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;

@Getter
public enum Form {

    SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION(150, new Appearance(Model.HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION, null, Model.AURA_NORMAL, Color.BLUE, null)),
    SUPER_SAIYAN_GOD_SUPER_SAIYAN(150, new Appearance(Model.HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN, null, Model.AURA_NORMAL, Color.AQUA, null), SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION),
    SUPER_SAIYAN_GOD(150, new Appearance(Model.HAIR_SUPER_SAIYAN_GOD, null, Model.AURA_NORMAL, Color.ORANGE, null), SUPER_SAIYAN_GOD_SUPER_SAIYAN),

    SUPER_SAIYAN_FOUR(150, new Appearance(Model.HAIR_SUPER_SAIYAN_FOUR, null, Model.AURA_NORMAL, Color.RED, SkinPreset.SUPER_SAIYAN_FOUR.getSkin())),

    SUPER_SAIYAN_THREE(150, new Appearance(Model.HAIR_SUPER_SAIYAN_THREE, null, Model.AURA_NORMAL, Color.YELLOW, null), SUPER_SAIYAN_FOUR),
    SUPER_SAIYAN_TWO(150, new Appearance(Model.HAIR_SUPER_SAIYAN_TWO, null, Model.AURA_NORMAL, Color.YELLOW, null), SUPER_SAIYAN_THREE),
    SUPER_SAIYAN(150, new Appearance(Model.HAIR_SUPER_SAIYAN, null, Model.AURA_NORMAL, Color.YELLOW, null), SUPER_SAIYAN_TWO),

    BASE(SUPER_SAIYAN, SUPER_SAIYAN_GOD);

    private String name;
    private Appearance appearance;

    private int requiredKi;

    private Form[] next;

    Form(int requiredKi, Appearance appearance, Form... next) {
        this.name = StringUtils.capitalize(toString().toLowerCase().replace("_", " "));
        this.appearance = appearance;

        this.requiredKi = requiredKi;

        this.next = next;
    }

    Form(Form... next) {
        this(0, null, next);
    }

    public boolean turnsInto(Form form) {
        if (form == BASE)
            return true;

        for (Form next : this.next)
            if (next == form)
                return true;
        return false;
    }

}
