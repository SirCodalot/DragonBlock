package me.codalot.dragonblock.game.fighters.cosmetics;

import me.codalot.dragonblock.game.fighters.components.Form;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class FighterAppearance implements ConfigurationSerializable {

    private Map<Form, AppearancePreset> forms;

    public FighterAppearance(Map<Form, AppearancePreset> forms) {
        this.forms = forms;
    }

    public static FighterAppearance deserialize(Map<String, Object> map) {
        Map<Form, AppearancePreset> forms = new HashMap<>();
        map.forEach((k, v) -> forms.put(Form.valueOf((k)), AppearancePreset.valueOf((String) v)));
        return new FighterAppearance(forms);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        forms.forEach((k, v) -> serialized.put(k.toString(), v.toString()));
        return serialized;
    }

    public Appearance getAppearance(Form form) {
        if (forms.containsKey(form))
            return forms.get(form).getAppearance();
        else
            return form.getAppearance();
    }

}
