package me.codalot.dragonblock.game.fighters.process.types.attacks.ki;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.combat.attacks.KiAttackData;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.combat.DamageData;
import me.codalot.dragonblock.game.fighters.combat.DamageType;
import me.codalot.dragonblock.game.fighters.components.Sight;
import me.codalot.dragonblock.game.fighters.process.FighterProcess;
import me.codalot.dragonblock.game.fighters.process.flags.Interruptable;
import me.codalot.dragonblock.game.models.ModelStand;
import me.codalot.dragonblock.utils.VectorUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public abstract class KiAttackProcess extends FighterProcess implements Interruptable {

    protected KiAttackData data;

    protected Fighter target;

    protected int charging;
    protected int timer;
    protected double damage;

    protected Deque<ModelStand> stands;

    protected Vector direction;
    protected Location original;
    protected Location current;
    protected int distance;

    public KiAttackProcess(Fighter fighter, KiAttackData data) {
        super(1, fighter);

        this.data = data;

        target = fighter.getTarget();

        charging = data.getChargeDuration();

        stands = new ArrayDeque<>();

        if (fighter.hasTarget())
            direction = fighter.getTarget().getPlayer().getEyeLocation().toVector()
                    .subtract(fighter.getPlayer().getEyeLocation().toVector()).normalize();
        else
            direction = fighter.getPlayer().getLocation().getDirection().clone().normalize();
        original = fighter.getPlayer().getEyeLocation().clone().setDirection(direction);
        current = original.clone();
        damage = data.getDamage() * fighter.getKiStrength();
    }

    @Override
    protected void onStart() {
        fighter.setCharging(true);
        data.getAnimation().start(fighter);
    }

    @Override
    protected void onProgress() {
        if (isCharging()) {
            charging--;

            if (!isCharging())
                data.getAnimation().end(fighter);
            return;
        }

        onAttack();

        if (timer % 5 == 0)
            damage();

        if (distance >= data.getDistance())
            end();

        timer++;

        stands.forEach(ModelStand::update);
    }

    protected abstract void onAttack();

    @Override
    protected void onEnd() {
        if (isCharging())
            fighter.setCharging(false);

        for (ModelStand stand : new HashSet<>(stands))
            stand.remove();
        stands.clear();
    }

    public boolean isCharging() {
        return charging > 0;
    }

    @Override
    public boolean isInterruptable() {
        return isCharging();
    }

    protected void move() {
        if (target != null && data.getAccuracy() > 0) {
            direction = VectorUtils.lerp(direction, current, target.getPlayer().getLocation(), data.getAccuracy());
            current = current.setDirection(direction);
        }
        current = current.add(direction.clone());
        distance++;
    }

    protected boolean damage() {
        final double radius = data.getRadius();
        boolean end = false;
        Set<Fighter> damaged = new HashSet<>();

        for (ModelStand stand : stands) {
            Location location = stand.getStand().getEyeLocation();

            if (!location.getBlock().isPassable())
                location.getWorld().createExplosion(location, (float) (damage / 3), false);

            for (Entity entity : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {
                Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(entity);
                if (fighter == null || fighter == this.fighter)
                    continue;

                damaged.add(fighter);

                if (stand == stands.getLast())
                    end = true;
            }
        }

        damaged.forEach(target ->
                new DamageData(target, fighter, DamageType.KI, damage).execute());

        return end;
    }

    protected void addStand() {
        if (!stands.isEmpty())
            stands.getLast().setItem(data.getModel().getBody());

        Location location = current.clone().add(0, -1.5, 0);
        stands.add(new ModelStand(data.getModel().getHead(), location, new Sight(location)));
    }

    protected void removeStand() {
        if (stands.isEmpty())
            return;

        ModelStand stand = stands.getFirst();
        stand.remove();
    }
}
