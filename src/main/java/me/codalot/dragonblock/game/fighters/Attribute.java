package me.codalot.dragonblock.game.fighters;

public enum Attribute {

    MAX_HEALTH,
    MAX_KI,
    MAX_STAMINA,
    BASIC_ATTACKS,
    STRIKE_SUPERS,
    KI_BLAST_SUPERS;

    public static int getMaxHealth(int level) {
        return 100 + level * level * 3;
    }

    public static int getMaxKi(int level) {
        return 30 + level * 5;
    }

    public static int getMaxStamina(int level) {
        return 100 + level * 3;
    }

}
