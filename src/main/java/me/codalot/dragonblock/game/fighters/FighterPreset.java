package me.codalot.dragonblock.game.fighters;

import lombok.Getter;
import me.codalot.dragonblock.game.Ability;
import me.codalot.dragonblock.game.fighters.components.Form;
import me.codalot.dragonblock.game.fighters.cosmetics.AppearancePreset;
import me.codalot.dragonblock.game.fighters.cosmetics.FighterAppearance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public enum FighterPreset {

    // Goku Characters
    GOKU_Z(
            new FighterAppearance(new HashMap<Form, AppearancePreset>() {{
                put(Form.BASE_SAIYAN, AppearancePreset.GOKU_Z_BASE);
                put(Form.SUPER_SAIYAN, AppearancePreset.GOKU_Z_SUPER_SAIYAN);
                put(Form.SUPER_SAIYAN_TWO, AppearancePreset.GOKU_Z_SUPER_SAIYAN);
                put(Form.SUPER_SAIYAN_THREE, AppearancePreset.GOKU_Z_SUPER_SAIYAN_THREE);
            }}),
            new HashSet<Ability>() {{
                // TODO add abilities
            }},
            new HashSet<Form>() {{
                add(Form.BASE_SAIYAN);
                add(Form.SUPER_SAIYAN);
                add(Form.SUPER_SAIYAN_TWO);
                add(Form.SUPER_SAIYAN_THREE);
            }},
            Form.BASE_SAIYAN,
            new HashMap<Attribute, Integer>() {{ // TODO change
                put(Attribute.MAX_HEALTH, 15);
                put(Attribute.MAX_KI, 15);
                put(Attribute.MAX_STAMINA, 15);
                put(Attribute.BASIC_ATTACKS, 15);
                put(Attribute.STRIKE_SUPERS, 15);
                put(Attribute.KI_BLAST_SUPERS, 15);
            }}
    ),
    GOKU_SUPER(
            new FighterAppearance(new HashMap<Form, AppearancePreset>() {{
                put(Form.BASE_SAIYAN, AppearancePreset.GOKU_SUPER_BASE);
                put(Form.SUPER_SAIYAN_GOD, AppearancePreset.GOKU_SUPER_SUPER_SAIYAN_GOD);
                put(Form.SUPER_SAIYAN_GOD_SUPER_SAIYAN, AppearancePreset.GOKU_SUPER_SUPER_SAIYAN_GOD_SUPER_SAIYAN);
                put(Form.SAIYAN_ULTRA_INSTINCT, AppearancePreset.GOKU_SUPER_ULTRA_INSTINCT);
            }}),
            new HashSet<Ability>() {{
                // TODO add abilities
            }},
            new HashSet<Form>() {{
                add(Form.BASE_SAIYAN);
                add(Form.SUPER_SAIYAN_GOD);
                add(Form.SUPER_SAIYAN_GOD_SUPER_SAIYAN);
                add(Form.SAIYAN_ULTRA_INSTINCT);
            }},
            Form.BASE_SAIYAN,
            new HashMap<Attribute, Integer>() {{ // TODO change
                put(Attribute.MAX_HEALTH, 15);
                put(Attribute.MAX_KI, 15);
                put(Attribute.MAX_STAMINA, 15);
                put(Attribute.BASIC_ATTACKS, 15);
                put(Attribute.STRIKE_SUPERS, 15);
                put(Attribute.KI_BLAST_SUPERS, 15);
            }}
    ),
    GOKU_GT(
            new FighterAppearance(new HashMap<Form, AppearancePreset>() {{
                put(Form.BASE_SAIYAN, AppearancePreset.GOKU_GT_BASE);
                put(Form.SUPER_SAIYAN, AppearancePreset.GOKU_GT_SUPER_SAIYAN);
                put(Form.SUPER_SAIYAN_TWO, AppearancePreset.GOKU_GT_SUPER_SAIYAN_TWO);
                put(Form.SUPER_SAIYAN_THREE, AppearancePreset.GOKU_GT_SUPER_SAIYAN_THREE);
                put(Form.SUPER_SAIYAN_FOUR, AppearancePreset.GOKU_GT_SUPER_SAIYAN_FOUR);
            }}),
            new HashSet<Ability>() {{
                // TODO add abilities
            }},
            new HashSet<Form>() {{
                add(Form.BASE_SAIYAN);
                add(Form.SUPER_SAIYAN);
                add(Form.SUPER_SAIYAN_TWO);
                add(Form.SUPER_SAIYAN_THREE);
                add(Form.SUPER_SAIYAN_FOUR);
            }},
            Form.BASE_SAIYAN,
            new HashMap<Attribute, Integer>() {{ // TODO change
                put(Attribute.MAX_HEALTH, 15);
                put(Attribute.MAX_KI, 15);
                put(Attribute.MAX_STAMINA, 15);
                put(Attribute.BASIC_ATTACKS, 15);
                put(Attribute.STRIKE_SUPERS, 15);
                put(Attribute.KI_BLAST_SUPERS, 15);
            }}
    )
    ;

    private FighterData data;

    FighterPreset(
            FighterAppearance appearance,
            Set<Ability> abilities,
            Set<Form> unlockedForms,
            Form baseForm,
            Map<Attribute, Integer> attributes
    ) {
        data = new FighterData(appearance, abilities, unlockedForms, baseForm, 0, attributes, 100, 0);
    }

}
