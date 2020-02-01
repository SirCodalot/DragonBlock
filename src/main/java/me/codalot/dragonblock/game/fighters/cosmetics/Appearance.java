package me.codalot.dragonblock.game.fighters.cosmetics;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.skin.Skin;
import me.codalot.dragonblock.setup.Model;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


@Getter
public class Appearance {

    private ItemStack hair;
    private ItemStack aura;
    @Setter private Skin skin;

    public Appearance(Model hairModel, Color hairColor, Model auraModel, Color auraColor, Skin skin) {
        setHair(hairModel, hairColor);
        setAura(auraModel, auraColor);
        this.skin = skin;
    }

    public Appearance(ItemStack hair, ItemStack aura, Skin skin) {
        this.hair = hair;
        this.aura = aura;
        this.skin = skin;
    }

    public void setHair(Model model, Color color) {
        hair = model == null ? null : model.get(color);
    }

    public void setAura(Model model, Color color) {
        aura = model == null ? null : model.get(color);
    }

    public Color getHairColor() {
        return hair == null ? Color.BLACK : ((LeatherArmorMeta) hair.getItemMeta()).getColor();
    }

    public Color getAuraColor() {
        return aura == null ? Color.BLACK :  ((LeatherArmorMeta) aura.getItemMeta()).getColor();
    }

    public void apply(Fighter fighter) {
        fighter.getPlayer().getEquipment().setHelmet(hair == null ? null : hair.clone());
        if (fighter.getModels().amount("aura") != 0)
            fighter.getModels().set(aura, "aura");
        fighter.getSkin().setSkin(skin);
    }

    @Override
    protected Appearance clone() {
        return new Appearance(hair.clone(), aura.clone(), skin);
    }
}
