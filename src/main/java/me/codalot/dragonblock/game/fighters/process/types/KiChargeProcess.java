package me.codalot.dragonblock.game.fighters.process.types;

import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.process.FighterProcess;

public class KiChargeProcess extends FighterProcess {

    public KiChargeProcess(Fighter fighter) {
        super(20, fighter);
    }

    @Override
    protected void onStart() {
        fighter.activateAura();
        if (!fighter.setCharging(true))
            end();
    }

    @Override
    protected void onProgress() {
        fighter.setKi(fighter.getKi() + 5);
    }

    @Override
    protected void onEnd() {
        fighter.deactivateAura();
        fighter.setCharging(false);
    }
}
