package me.codalot.dragonblock.game.players.components;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.game.players.Fighter;
import me.codalot.dragonblock.game.players.skin.Skin;
import me.codalot.dragonblock.setup.Model;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Appearance {

    private Model hairModel;
    private Color hairColor;
    private ItemStack hairItem;

    private Model auraModel;
    private Color auraColor;
    private ItemStack auraItem;

    private Skin skin;

    public Appearance(Model hairModel, Color hairColor, Model auraModel, Color auraColor, Skin skin) {
        this.hairModel = hairModel;
        this.hairColor = hairColor;
        hairItem = hairModel == null ? null : hairModel.get(hairColor);

        this.auraModel = auraModel;
        this.auraColor = auraColor;
        auraItem = auraModel == null ? null : auraModel.get(auraColor);

        this.skin = skin;
    }

    public Appearance() {
        this(null, null, null, null, null);
    }

    public void apply(Fighter fighter) {
        // TODO auras and hair

        fighter.getPlayer().getEquipment().setHelmet(hairItem == null ? null : hairItem.clone());

        fighter.getSkin().setSkin(skin);
    }
}
