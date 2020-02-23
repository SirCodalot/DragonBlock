package me.codalot.dragonblock.game.fighters.process.types.attacks.ki;

import me.codalot.dragonblock.game.combat.attacks.KiAttackData;
import me.codalot.dragonblock.game.fighters.Fighter;

public class KiProjectileProcess extends KiAttackProcess {

    public KiProjectileProcess(Fighter fighter, KiAttackData data) {
        super(fighter, data);
    }

    @Override
    protected void onAttack() {
        if (timer == 0) {
            fighter.setCharging(false);
            addStand();
        }

        if (stands.isEmpty())
            end();
        else {
            stands.getLast().setVelocity(direction.clone().multiply(data.getSpeed()));

            if (stands.getLast().getDistanceTraveled() >= data.getDistance())
                stands.getLast().remove();
        }
    }

}
