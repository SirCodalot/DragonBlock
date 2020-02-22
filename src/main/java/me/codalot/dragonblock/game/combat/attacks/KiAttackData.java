package me.codalot.dragonblock.game.combat.attacks;

import lombok.Getter;
import me.codalot.dragonblock.setup.Model;

@Getter
public enum KiAttackData {

    DEBUG_KAMEHAMEHA(
            new AttackModel(Model.KAMEHAMEHA_HEAD.get(), Model.KAMEHAMEHA_BODY.get(), Model.KAMEHAMEHA_BODY.get()),
            ChargeAnimation.HANDS_FORWARD,
            10, 1,
            50, 1, 100, 40,
            0.01
    );

    private final AttackModel model;
    private final ChargeAnimation animation;

    private final double damage;
    private final double radius;

    private final double distance;
    private final int speed;
    private final int duration;
    private final int chargeDuration;

    private final double accuracy;

    KiAttackData(
            AttackModel model, ChargeAnimation animation,
            double damage, double radius,
            double distance, int speed, int duration, int chargeDuration,
            double accuracy
    ) {
        this.model = model;
        this.animation = animation;

        this.damage = damage;
        this.radius = radius;

        this.distance = distance;
        this.speed = speed;
        this.duration = duration;
        this.chargeDuration = chargeDuration;

        this.accuracy = accuracy;
    }

}
