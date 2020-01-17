package me.codalot.dragonblock.game.players.components;

import lombok.Getter;
import me.codalot.dragonblock.setup.Model;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;

@Getter
public enum Form {

    SUPER_SAIYAN_GOD_SUPER_SAIAYN_EVOLUTION,
    SUPER_SAIYAN_GOD_SUPER_SAIYAN(SUPER_SAIYAN_GOD_SUPER_SAIAYN_EVOLUTION),
    SUPER_SAIYAN_GOD(SUPER_SAIYAN_GOD_SUPER_SAIYAN),

    SUPER_SAIYAN_FOUR(),

    SUPER_SAIYAN_THREE(),
    SUPER_SAIYAN_TWO(SUPER_SAIYAN_THREE),
    SUPER_SAIYAN(new Appearance(Model.HAIR_SUPER_SAIYAN, Color.YELLOW, Model.AURA_NORMAL, Color.YELLOW, null), SUPER_SAIYAN_TWO),

    BASE(SUPER_SAIYAN, SUPER_SAIYAN_GOD);

    private String name;
    private Appearance appearance;

    private Form[] next;

    Form(Appearance appearance, Form... next) {
        this.name = StringUtils.capitalize(toString().toLowerCase().replace("_", " "));
        this.appearance = appearance;
        this.next = next;
    }

    Form(Form... next) {
        this(new Appearance(), next);
    }

    public boolean turnsInto(Form form) {
        for (Form next : this.next)
            if (next == form)
                return true;
        return false;
    }

}
