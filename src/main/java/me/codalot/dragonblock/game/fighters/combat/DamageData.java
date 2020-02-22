package me.codalot.dragonblock.game.fighters.combat;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.events.combat.FighterDamageEvent;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.utils.EventUtils;

@Getter
public class DamageData {

    private Fighter damaged;
    private Fighter damager;

    private DamageType type;
    @Setter private double force;

    public DamageData(Fighter damaged, Fighter damager, DamageType type, double force) {
        this.damaged = damaged;
        this.damager = damager;
        this.type = type;
        this.force = force;
    }

    public DamageData(Fighter damaged, DamageType type, double force) {
        this(damaged, null, type, force);
    }

    public DamageData(Fighter damaged, double force) {
        this(damaged, DamageType.SPECIAL, force);
    }

    public boolean execute() {
        FighterDamageEvent event = new FighterDamageEvent(this);
        EventUtils.callEvent(event);

        if (event.isCancelled())
            return false;

        damaged.damage(force);
        damaged.playDamageAnimation();
        damaged.resetCombo();
        damaged.interrupt();

        if (damager != null) {
            damager.addCombo();
        }

        return true;
    }

}
