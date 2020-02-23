package me.codalot.dragonblock.game.fighters.process.types.attacks.ki;

import me.codalot.dragonblock.game.combat.attacks.KiAttackData;
import me.codalot.dragonblock.game.fighters.Fighter;

public class KiBeamProcess extends KiAttackProcess {

    public KiBeamProcess(Fighter fighter, KiAttackData data) {
        super(fighter, data);
    }

    @Override
    protected void onAttack() {
        if (timer % data.getSpeed() == 0) {
            if (!interrupted && distance < data.getDistance()) {
                move();
                addStand();
            }

            if (timer > data.getDuration())
                removeStand();
        }
    }

    @Override
    protected void onEnd() {
        super.onEnd();
        fighter.setCharging(false);
    }
}
