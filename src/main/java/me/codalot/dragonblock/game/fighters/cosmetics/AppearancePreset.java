package me.codalot.dragonblock.game.fighters.cosmetics;

import lombok.Getter;
import me.codalot.dragonblock.game.fighters.skin.SkinPreset;
import me.codalot.dragonblock.setup.Model;

@Getter
public enum  AppearancePreset {

    EMPTY(new Appearance(null, null, null,null, null)),

    // Empty Saiyan Appearances
    EMPTY_BASE(new Appearance(
            Model.HAIR_GOKU_BASE, ColorPallet.HAIR_BASE_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_NORMAL.getColor(),
            null
    )),

    EMPTY_GREAT_APE(),
    EMPTY_GOLDEN_GREAT_APE(),

    EMPTY_SUPER_SAIYAN(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN.getColor(),
            null
    )),
    EMPTY_SUPER_SAIYAN_TWO(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_TWO, ColorPallet.HAIR_SUPER_SAIYAN_TWO.getColor(),
            Model.AURA_SUPER_SAIYAN_TWO, null,
            null
    )),
    EMPTY_SUPER_SAIYAN_THREE(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_THREE, ColorPallet.HAIR_SUPER_SAIYAN_TWO.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_THREE.getColor(),
            null
    )),
    EMPTY_SUPER_SAIYAN_FOUR(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_FOUR, ColorPallet.HAIR_SUPER_SAIYAN_FOUR.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_FOUR.getColor(),
            null
    )),

    EMPTY_SUPER_SAIYAN_GOD(new Appearance(
            Model.HAIR_GOKU_BASE, ColorPallet.HAIR_SUPER_SAIYAN_GOD.getColor(),
            Model.AURA_SUPER_SAIYAN_GOD, null,
            null
    )),
    EMPTY_SUPER_SAIYAN_GOD_SUPER_SAIYAN(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_GOD_SUPER_SAIYAN.getColor(),
            null
    )),
    EMPTY_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION.getColor(),
            null
    )),

    EMPTY_ROSE(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN_ROSE.getColor(),
            Model.AURA_SUPER_SAIAN_ROSE, null,
            null
    )),

    EMPTY_ULTRA_INSTINCT(new Appearance(
            Model.HAIR_GOKU_ULTRA_INSTINCT, ColorPallet.HAIR_SAIYAN_ULTRA_INSTINCT.getColor(),
            Model.AURA_ULTRA_INSTINCT, null,
            null
    )),

    // Goku Appearances
    GOKU_Z_BASE(new Appearance(
            Model.HAIR_GOKU_BASE, ColorPallet.HAIR_BASE_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_NORMAL.getColor(),
            SkinPreset.GOKU_Z_BASE.getSkin()
    )),
    GOKU_Z_SUPER_SAIYAN(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN.getColor(),
            SkinPreset.GOKU_Z_SUPER_SAIYAN.getSkin()
    )),
    GOKU_Z_SUPER_SAIYAN_TWO(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_TWO, ColorPallet.HAIR_SUPER_SAIYAN_TWO.getColor(),
            Model.AURA_SUPER_SAIYAN_TWO, null,
            SkinPreset.GOKU_Z_SUPER_SAIYAN.getSkin()
    )),
    GOKU_Z_SUPER_SAIYAN_THREE(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_THREE, ColorPallet.HAIR_SUPER_SAIYAN_TWO.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_THREE.getColor(),
            SkinPreset.GOKU_Z_SUPER_SAIYAN_THREE.getSkin()
    )),

    GOKU_GT_BASE(new Appearance(
            Model.HAIR_GOKU_BASE, ColorPallet.HAIR_BASE_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_NORMAL.getColor(),
            SkinPreset.GOKU_GT_BASE.getSkin()
    )),
    GOKU_GT_SUPER_SAIYAN(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN.getColor(),
            SkinPreset.GOKU_GT_SUPER_SAIYAN.getSkin()
    )),
    GOKU_GT_SUPER_SAIYAN_TWO(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_TWO, ColorPallet.HAIR_SUPER_SAIYAN_TWO.getColor(),
            Model.AURA_SUPER_SAIYAN_TWO, null,
            SkinPreset.GOKU_GT_SUPER_SAIYAN.getSkin()
    )),
    GOKU_GT_SUPER_SAIYAN_THREE(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_THREE, ColorPallet.HAIR_SUPER_SAIYAN_TWO.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_THREE.getColor(),
            SkinPreset.GOKU_GT_SUPER_SAIYAN_THREE.getSkin()
    )),
    GOKU_GT_SUPER_SAIYAN_FOUR(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN_FOUR, ColorPallet.HAIR_SUPER_SAIYAN_FOUR.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_FOUR.getColor(),
            SkinPreset.GOKU_GT_SUPER_SAIYAN_FOUR.getSkin()
    )),

    GOKU_SUPER_BASE(new Appearance(
            Model.HAIR_GOKU_BASE, ColorPallet.HAIR_BASE_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_NORMAL.getColor(),
            SkinPreset.GOKU_SUPER_BASE.getSkin()
    )),
    GOKU_SUPER_SUPER_SAIYAN_GOD(new Appearance(
            Model.HAIR_GOKU_BASE, ColorPallet.HAIR_SUPER_SAIYAN_GOD.getColor(),
            Model.AURA_SUPER_SAIYAN_GOD, null,
            SkinPreset.GOKU_SUPER_SUPER_SAIYAN_GOD.getSkin()
    )),
    GOKU_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN(new Appearance(
            Model.HAIR_GOKU_SUPER_SAIYAN, ColorPallet.HAIR_SUPER_SAIYAN_GOD_SUPER_SAIYAN.getColor(),
            Model.AURA_NORMAL, ColorPallet.AURA_SUPER_SAIYAN_GOD_SUPER_SAIYAN.getColor(),
            SkinPreset.GOKU_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN.getSkin()
    )),
    GOKU_SUPER_ULTRA_INSTINCT(new Appearance(
            Model.HAIR_GOKU_ULTRA_INSTINCT, ColorPallet.HAIR_SAIYAN_ULTRA_INSTINCT.getColor(),
            Model.AURA_ULTRA_INSTINCT, null,
            SkinPreset.GOKU_SUPER_ULTRA_INSTINCT.getSkin()
    )),

    // Vegeta Appearances
    VEGETA_Z_BASE(),
    VEGETA_Z_GREAT_APE(),
    VEGETA_Z_SUPER_SAIYAN(),

    VEGETA_GT_BASE(),
    VEGETA_GT_SUPER_SAIYAN_FOUR(),

    VEGETA_SUPER_BASE(),
    VEGETA_SUPER_SUPER_SAIYAN_GOD(),
    VEGETA_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN(),
    VEGETA_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN_EVOLUTION()
    ;

    private Appearance appearance;

    AppearancePreset(Appearance appearance) {
        this.appearance = appearance;
    }

    AppearancePreset() {
        appearance = null;
    }

}
