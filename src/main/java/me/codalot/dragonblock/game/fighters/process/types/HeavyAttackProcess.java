package me.codalot.dragonblock.game.fighters.process.types;

import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.combat.DamageData;
import me.codalot.dragonblock.game.fighters.combat.DamageType;
import me.codalot.dragonblock.game.fighters.process.FighterProcess;
import org.bukkit.util.Vector;

public class HeavyAttackProcess extends FighterProcess {

    private boolean charged;

    public HeavyAttackProcess(Fighter fighter) {
        super(20, fighter);
        charged = false;
    }

    @Override
    protected void onStart() {
        fighter.setCharging(true);
    }

    @Override
    protected void onProgress() {
        if (interrupted) {
            end();
            return;
        }

        if (charged) {
            end();

            Fighter target = fighter.getFighterInSight(3);
            if (target == null)
                return;

            fighter.playHandAnimation();

            if (!new DamageData(target, fighter, DamageType.PHYSICAL, fighter.getAttackStrength() * 5).execute())
                return;

            target.setStamina(target.getStamina() - 15);

            Vector velocity = fighter.getPlayer().getEyeLocation().getDirection().clone();
            velocity.normalize().multiply(10);

            target.getPlayer().setVelocity(velocity);
            target.setFlying(false);

        } else
            charged = true;
    }

    @Override
    protected void onEnd() {
        fighter.setCharging(false);
    }

    @Override
    public boolean isInterruptable() {
        return true;
    }
}
