package me.codalot.dragonblock.game.fighters;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.fighters.components.*;
import me.codalot.dragonblock.game.fighters.cosmetics.Appearance;
import me.codalot.dragonblock.game.fighters.process.FighterProcess;
import me.codalot.dragonblock.game.fighters.process.ProcessHandler;
import me.codalot.dragonblock.game.fighters.process.types.HeavyAttackProcess;
import me.codalot.dragonblock.game.fighters.process.types.TransformationProcess;
import me.codalot.dragonblock.game.fighters.skin.SkinHandler;
import me.codalot.dragonblock.managers.types.FighterManager;
import me.codalot.dragonblock.utils.MathUtils;
import me.codalot.dragonblock.utils.PacketUtils;
import me.codalot.dragonblock.utils.TimeUtils;
import me.codalot.dragonblock.utils.VectorUtils;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.UUID;

@Getter
public class Fighter {

    private UUID uuid;
    private FighterData data;

    private ProcessHandler processes;

    private Sight sight;
    private SkinHandler skin;
    private PlayerModels models;
    private MoveState state;

    private Form form;

    @Setter private Fighter target;

    @Setter private int health;
    @Setter private int ki;
    @Setter private int stamina;

    private BossBar healthBar;

    private boolean aura;

    private int combo;
    private int comboCooldown;

    public Fighter(UUID uuid, FighterData data) {
        this.uuid = uuid;
        this.data = data;

        processes = new ProcessHandler();

        sight = new Sight(getPlayer().getLocation());
        skin = new SkinHandler(this);
        models = new PlayerModels(this);
        resetState();

        setForm(data.getBaseForm());

        health = data.getMaxHealth();
        ki = data.getMaxKi();
        stamina = data.getMaxStamina();

        healthBar = Bukkit.createBossBar(ChatColor.RED + "Enemy Helath", BarColor.RED, BarStyle.SOLID);

        aura = false;

        combo = 0;
        comboCooldown = 0;

        getPlayer().setAllowFlight(true);
        FighterManager.getFighters().put(uuid, this);
    }

    public void unload() {
        deactivateAura();
        skin.resetSkin();
        FighterManager.getFighters().remove(uuid);
    }

    public void update() {
        updateLevels();
        updateCombo();

        updateUI();

        sight.setLocation(getPlayer().getLocation());

        updateCharge();
        updateFlight();

        processes.update();
    }

    private void updateCombo() {
        if (comboCooldown <= 0)
            combo = 0;
    }

    private void updateLevels() {
        if (state != MoveState.DASH && TimeUtils.every(40))
            stamina += 10;

        health = MathUtils.clamp(health, 0, data.getMaxHealth());
        ki = MathUtils.clamp(ki, 0, data.getMaxKi());
        stamina = MathUtils.clamp(stamina, 0, data.getMaxStamina());
    }

    private void updateUI() {
        Player player = getPlayer();

        player.setLevel(data.getLevel());
        player.setExp((float) getHealthScale());
        player.setHealth(getKiScale() * 20);
        player.setFoodLevel((int) (getStaminaScale() * 20));

        healthBar.setProgress(getHealthScale());
    }

    private void updateCharge() {
        if (state != MoveState.CHARGE)
            return;

        getPlayer().setVelocity(new Vector(0, 0, 0));
        if (!getPlayer().isOnGround())
            getPlayer().setGravity(false);
    }

    private void updateFlight() {
        Player player = getPlayer();

        player.setAllowFlight(true);

        if (isCharging())
            return;

        if (isFlying() && player.isOnGround())
            setFlying(false);

        if (!isFlying()) {
            player.setFlying(false);
            return;
        }

        if (player.isSprinting() && !player.isSneaking()) {
            player.setGliding(true);
            player.setFlying(false);
            state = MoveState.DASH;

            Vector velocity = getPlayer().getEyeLocation().getDirection().clone().multiply(2);
//            velocity = lerpTowardsTarget(velocity);
            getPlayer().setVelocity(VectorUtils.lerp(player.getVelocity(), velocity, 0.5));

            updateCollisionExplosion();
        } else {
            getPlayer().setFlying(true);
            state = MoveState.FLY;
        }
    }

    private Vector lerpTowardsTarget(Vector velocity) {
        if (target == null)
            return velocity;

        return VectorUtils.lerp(
                velocity,
                target.getPlayer().getLocation().toVector().subtract(getPlayer().getLocation().toVector())
                        .normalize().multiply(velocity.length()),
                0.7);
    }

    private Block getBlockInFront() {
        Vector direction = getPlayer().getVelocity().normalize();
        Location location = getPlayer().getLocation().clone();

        for (int i = 0; i < getPlayer().getVelocity().length() + 1; i++) {
            location = location.add(direction);
            Block current = location.getBlock();

            if (current.isPassable())
                continue;

            return current;
        }

        return null;
    }

    private double getSpeed(Block block) {
        Vector velocity = getPlayer().getVelocity();
        Location location = getPlayer().getLocation();

        if (block.getX() == location.getBlockX() && block.getZ() == location.getBlockZ())
            return Math.abs(velocity.getY());

        if (block.getY() == location.getBlockY())
            return Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getZ(), 2));

        return 0;
    }

    private void updateCollisionExplosion() {
        Block block = getBlockInFront();

        if (block == null)
            return;

        double speed = getSpeed(block);
        if (speed< 1.9)
            return;

        block.getWorld().createExplosion(block.getLocation(), (float) (3 * speed / 1.9), false);
        setFlying(false);
    }

    public boolean setCharging(boolean enable) {
        if (state == MoveState.FATIGUE)
            return false;

        if (enable) {
            state = MoveState.CHARGE;

            getPlayer().setWalkSpeed(.000001f);
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 0, true, false));
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1, true, false));
        } else {
            getPlayer().setGravity(true);
            getPlayer().setWalkSpeed(.2f);
            getPlayer().removePotionEffect(PotionEffectType.JUMP);
            getPlayer().removePotionEffect(PotionEffectType.SLOW);

            state = getPlayer().isFlying() ? MoveState.FLY : MoveState.NORMAL;
        }

        return true;
    }

    public boolean isCharging() {
        return state == MoveState.CHARGE;
    }

    public void setFlying(boolean enable) {
        if (isCharging())
            return;

        if (enable) {
            state = MoveState.FLY;
            getPlayer().setFlying(true);
        } else {
            state = MoveState.NORMAL;
            getPlayer().setFlying(false);
        }
    }

    public Appearance getAppearance() {
        return data.getAppearance().getAppearance(form);
    }

    public boolean isFlying() {
        return state == MoveState.FLY || state == MoveState.DASH;
    }

    public void resetState() {
        state = getPlayer().isOnGround() ? MoveState.NORMAL : MoveState.FLY;
    }

    public void setForm(Form form) {
        this.form = form;
        getAppearance().apply(this);
    }

    public void transform(Form form) {
        if (isCharging())
            return;

        if (form == null || this.form == form)
            return;

        if (!data.getUnlockedForms().contains(form))
            return;

        if (!this.form.turnsInto(form))
            return;

        if (processes.<TransformationProcess>getProcessCount() != 0)
            return;

        if (form == data.getBaseForm()) {
            setForm(form);
            return;
        }

        processes.addAndStart(new TransformationProcess(this, form));
    }

    public void activateAura() {
        if (aura)
            return;

        models.set(getAppearance().getAura(), "aura");
        aura = true;
    }

    public void deactivateAura() {
        if (!aura)
            return;

        models.remove("aura");
        aura = false;
    }

    public void resetAura() {
        deactivateAura();
        activateAura();
    }

    private void updateTargetMetadata() {
        if (target != null)
            PacketUtils.updatePlayer(getPlayer(), target.getPlayer());
    }

    private void setTarget(Fighter target) {
        updateTargetMetadata();
        if (this.target != null)
            this.target.getHealthBar().removePlayer(getPlayer());
        this.target = target;
        updateTargetMetadata();
        if (target != null)
            target.getHealthBar().addPlayer(getPlayer());
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void findTarget() {
        Location location = getPlayer().getEyeLocation().clone();
        Location current = location.clone();
        Vector direction = location.getDirection().clone().normalize();

        double distance;
        while ((distance = current.distance(location)) < 50) {
            for (Entity entity : current.getWorld().getNearbyEntities(current, distance / 2, distance / 2, distance / 2)) {
                Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(entity);
                if (fighter != null && fighter != this) {
                    setTarget(fighter);
                    return;
                }
            }

            current = current.add(direction);
        }

        setTarget(null);
    }

    public void vanish() {
        if (target == null)
            return;

        Vector direction = target.getPlayer().getLocation().getDirection().clone().normalize().setY(0).multiply(2);
        Location location = target.getPlayer().getLocation().clone().subtract(direction);

        if (stamina < 50)
            return;
        stamina -= 50;

        getPlayer().teleport(location);
    }

//    public void punch(Hand hand) {
//        getPlayer().hasLineOfSight()
//    }

    public void clearTarget() {
        setTarget(null);
    }

    public double getHealthScale() {
        return (double) health / data.getMaxHealth();
    }

    public double getKiScale() {
        return (double) ki / data.getMaxKi();
    }

    public double getStaminaScale() {
        return (double) stamina / data.getMaxStamina();
    }

    public double getComboMultiplier() {
        return 1 + (combo / 10.0);
    }

    public double getAttackStrength() {
        double attributeStrength = Attribute.getBasicAttackStrength(data.getAttributes().get(Attribute.BASIC_ATTACKS));
        double comboMultiplier = getComboMultiplier();

        return attributeStrength * comboMultiplier;
    }

    public double getKiStrength() {
        return Attribute.getKiBlastSupersStrength(data.getAttributes().get(Attribute.KI_BLAST_SUPERS));
    }

    public void damage(double force) {
        health -= force;
        // TODO check death
    }

    public void addCombo() {
        combo++;
        comboCooldown = 40;
    }

    public void resetCombo() {
        combo = 0;
        comboCooldown = 0;
    }

    @SuppressWarnings("all")
    public Fighter getFighterInSight(double range) {
        RayTraceResult ray = getPlayer().getWorld().rayTraceEntities(
                getPlayer().getEyeLocation(),
                getPlayer().getEyeLocation().getDirection(),
                range,
                0.1,
                entity -> entity != getPlayer()
        );

        return DragonBlock.getInstance().getFighters().getFighter(ray == null ? null : ray.getHitEntity());
    }

    public void heavyAttack() {
        if (isCharging())
            return;

        processes.addAndStart(new HeavyAttackProcess(this));
    }

    public void interrupt() {
        processes.interrupt();
    }

    private void playAnimation(int id) {
        PacketUtils.broadcast(getPlayer().getLocation(),
                new PacketPlayOutAnimation(PacketUtils.getNmsPlayer(getPlayer()), id));
    }

    public void playDamageAnimation() {
        playAnimation(1);
    }

    public void playHandAnimation() {
        playAnimation(0);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
