package me.codalot.dragonblock.game.fighters;

import com.sun.corba.se.spi.ior.IdentifiableBase;
import lombok.Getter;
import me.codalot.dragonblock.game.fighters.components.Form;
import me.codalot.dragonblock.game.fighters.components.MoveState;
import me.codalot.dragonblock.game.fighters.components.PlayerModels;
import me.codalot.dragonblock.game.fighters.components.Sight;
import me.codalot.dragonblock.game.fighters.cosmetics.Appearance;
import me.codalot.dragonblock.game.fighters.process.ProcessHandler;
import me.codalot.dragonblock.game.fighters.process.types.TransformationProcess;
import me.codalot.dragonblock.game.fighters.skin.SkinHandler;
import me.codalot.dragonblock.managers.types.FighterManager;
import me.codalot.dragonblock.utils.MathUtils;
import me.codalot.dragonblock.utils.VectorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

    private int health;
    private int ki;
    private int stamina;

    private boolean aura;

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

        aura = false;

        getPlayer().setAllowFlight(true);
        FighterManager.getFighters().put(uuid, this);
    }

    public void unload() {
        deactivateAura();
        processes.endAll();
        FighterManager.getFighters().remove(uuid);
    }

    public void update() {
        updateLevels();
        updateUI();

        sight.setLocation(getPlayer().getLocation());

        updateCharge();
        updateFlight();

        processes.update();
    }

    public void updateLevels() {
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
            getPlayer().setVelocity(VectorUtils.lerp(player.getVelocity(), velocity, 0.5));

            updateCollisionExplosion();
        } else {
            getPlayer().setFlying(true);
            state = MoveState.FLY;
        }
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

    public void setCharging(boolean enable) {
        if (state == MoveState.TRANSFORM)
            return;

        if (enable) {
            if (state != MoveState.CHARGE)
                activateAura();

            state = MoveState.CHARGE;

            getPlayer().setWalkSpeed(0);
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 0, true, false));
        } else {
            getPlayer().setGravity(true);
            getPlayer().setWalkSpeed(.2f);
            getPlayer().removePotionEffect(PotionEffectType.JUMP);
            state = getPlayer().isFlying() ? MoveState.FLY : MoveState.NORMAL;

            deactivateAura();
        }
    }

    public void toggleCharge() {
        setCharging(!isCharging());
    }

    public boolean isCharging() {
        return state == MoveState.CHARGE;
    }

    public void setFlying(boolean enable) {
        if (state == MoveState.CHARGE || state == MoveState.TRANSFORM)
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

        state = MoveState.TRANSFORM;
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

    public double getHealthScale() {
        return (double) health / data.getMaxHealth();
    }

    public double getKiScale() {
        return (double) health / data.getMaxHealth();
    }

    public double getStaminaScale() {
        return (double) health / data.getMaxHealth();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
