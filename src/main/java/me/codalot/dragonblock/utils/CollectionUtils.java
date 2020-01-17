package me.codalot.dragonblock.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.StringUtil;

import java.util.*;

public class CollectionUtils {

    public static Map<String, Object> toMap(ConfigurationSection section, boolean deep) {
        Map<String, Object> map = new HashMap<>();

        for (String key : section.getKeys(false)) {
            Object value = section.get(key);

            if (deep && value instanceof ConfigurationSection)
                map.put(key, toMap((ConfigurationSection) value, true));
            else
                map.put(key, value);

        }

        return map;
    }

    public static List<String> getMatches(String word, Collection<String> options) {
        List<String> matches = new ArrayList<>();
        StringUtil.copyPartialMatches(word.toLowerCase(), options, matches);
        return matches;
    }

    @SuppressWarnings("all")
    public static String[] removeFirst(int amount, String[] args) {
        if (args.length <= amount)
            return new String[0];

        String[] newArgs = new String[args.length - amount];

        for (int i = amount; i < args.length; i++) {
            newArgs[i - amount] = args[i];
        }

        return newArgs;
    }

    public static String[] removeFirst(String[] args) {
        return removeFirst(1, args);
    }

}
