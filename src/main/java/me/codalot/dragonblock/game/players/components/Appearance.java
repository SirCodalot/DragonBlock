package me.codalot.dragonblock.game.players.components;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.game.players.Fighter;
import me.codalot.dragonblock.game.players.skin.Skin;
import me.codalot.dragonblock.setup.Model;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
        if (hairColor == null && hairItem.getItemMeta() instanceof LeatherArmorMeta)
            this.hairColor = ((LeatherArmorMeta) hairItem.getItemMeta()).getColor();


        this.auraModel = auraModel;
        this.auraColor = auraColor;
        auraItem = auraModel == null ? null : auraModel.get(auraColor);
        if (auraColor == null && auraItem.getItemMeta() instanceof LeatherArmorMeta)
            this.auraColor = ((LeatherArmorMeta) auraItem.getItemMeta()).getColor();

        this.skin = skin;
    }

    public Appearance() {
        this(null, null, null, null, null);
    }

    public void apply(Fighter fighter, Appearance def) {
        ItemStack hair = hairItem == null && def != null ? def.getHairItem() : hairItem;
        ItemStack aura = auraItem == null && def != null ? def.getAuraItem() : auraItem;

        fighter.getPlayer().getEquipment().setHelmet(hair == null ? null : hair.clone());
        if (fighter.getModels().amount("aura") != 0)
            fighter.getModels().set(aura, "aura");
        fighter.getSkin().setSkin(skin);
    }

    public void apply(Fighter fighter) {
        apply(fighter, null);
    }
}
