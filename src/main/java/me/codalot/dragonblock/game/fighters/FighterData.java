package me.codalot.dragonblock.game.fighters;

import lombok.Getter;
import me.codalot.dragonblock.game.Ability;
import me.codalot.dragonblock.game.fighters.components.Form;
import me.codalot.dragonblock.game.fighters.cosmetics.AppearancePreset;
import me.codalot.dragonblock.game.fighters.cosmetics.FighterAppearance;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

@Getter
public class FighterData implements ConfigurationSerializable {

    private FighterAppearance appearance;

    private Set<Ability> abilities;

    private Set<Form> unlockedForms;
    private Form baseForm;

    private int attributePoints;
    private Map<Attribute, Integer> attributes;

    private int level;
    private int experience;

    public FighterData(
            FighterAppearance appearance,
            Set<Ability> abilities,
            Set<Form> unlockedForms,
            Form baseForm,
            int attributePoints,
            Map<Attribute, Integer> attributes,
            int level,
            int experience
    ) {
        this.appearance = appearance;

        this.abilities = abilities;

        this.unlockedForms = unlockedForms;
        this.baseForm = baseForm;

        this.attributePoints = attributePoints;
        this.attributes = attributes;

        this.level = level;
        this.experience = experience;
    }

    @SuppressWarnings("unchecked")
    public FighterData(Map<String, Object> map) {
        appearance = new FighterAppearance((Map<Form, AppearancePreset>) map.getOrDefault("appearance", new HashMap<>()));

        abilities = new HashSet<>();
        ((List<String>) map.getOrDefault("abilities", new ArrayList<>())).forEach(name -> abilities.add(Ability.valueOf(name)));

        unlockedForms = new HashSet<>();
        for (String form : (List<String>) map.getOrDefault("unlocked-forms", new ArrayList<>()))
            unlockedForms.add(Form.valueOf(form));
        baseForm = map.containsKey("base-form") ? Form.valueOf((String) map.get("base-form")) : Form.BASE_SAIYAN;

        attributePoints = (int) map.getOrDefault("attribute-points", 0);
        attributes = new HashMap<>();
        Map<String, Object> attributeData = (Map<String, Object>) map.getOrDefault("attributes", new HashMap<>());
        for (Attribute attribute : Attribute.values())
            attributes.put(attribute, (int) attributeData.getOrDefault(attribute.toString(), 0));

        level = (int) map.getOrDefault("level", 1);
        experience = (int) map.getOrDefault("experience", 0);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();

        serialized.put("appearance", appearance.serialize());

        List<String> abilities = new ArrayList<>();
        this.abilities.forEach(ability -> abilities.add(ability.toString()));
        serialized.put("abilities", abilities);

        List<String> unlockedForms = new ArrayList<>();
        this.unlockedForms.forEach(form -> unlockedForms.add(form.toString()));
        serialized.put("unlocked-forms", unlockedForms);
        serialized.put("base-form", baseForm.toString());

        serialized.put("level", level);
        serialized.put("experience", experience);

        serialized.put("attribute-points", attributePoints);

        return serialized;
    }

    public int getMaxHealth() {
        return Attribute.getMaxHealth(attributes.get(Attribute.MAX_HEALTH));
    }


    public int getMaxKi() {
        return Attribute.getMaxKi(attributes.get(Attribute.MAX_KI));
    }

    public int getMaxStamina() {
        return Attribute.getMaxStamina(attributes.get(Attribute.MAX_STAMINA));
    }

}
