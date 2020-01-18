package me.codalot.dragonblock.game.players;

import lombok.Getter;
import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.*;
import me.codalot.dragonblock.game.players.components.*;
import me.codalot.dragonblock.game.players.process.ProcessHandler;
import me.codalot.dragonblock.game.players.process.types.TransformationProcess;
import me.codalot.dragonblock.game.players.skin.SkinHandler;
import me.codalot.dragonblock.managers.types.FighterManager;
import me.codalot.dragonblock.setup.Model;
import me.codalot.dragonblock.utils.MathUtils;
import me.codalot.dragonblock.utils.VectorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

@Getter
public class Fighter implements ConfigurationSerializable {

    private UUID uuid;

    private ProcessHandler processes;

    private MoveState state;
    private Sight sight;
    private SkinHandler skin;
    private PlayerModels models;

    private Appearance originalAppearance;
    private Appearance currentAppearance;

    private Set<Ability> abilities;

    private Set<Form> unlockedForms;
    private Form form;

    private int attributePoints;
    private Map<Attribute, Integer> attributes;

    private int level;
    private int experience;

    private int health;
    private int ki;
    private int stamina;

    @SuppressWarnings("unchecked")
    public Fighter(UUID uuid, Map<String, Object> data) {
        this.uuid = uuid;

        processes = new ProcessHandler();

        resetState();
        sight = new Sight(getPlayer().getLocation());
        skin = new SkinHandler(this);
        models = new PlayerModels(this);

        originalAppearance = new Appearance(Model.HAIR_NORMAL, null, Model.AURA_SMALL, Color.WHITE, skin.getOriginal());
        currentAppearance = null;
        Bukkit.getScheduler().runTaskLater(DragonBlock.getInstance(), () ->
                getAppearance().apply(this), 5);

        abilities = new HashSet<>();
        ((List<String>) data.getOrDefault("abilities", new ArrayList<>())).forEach(name -> abilities.add(Ability.valueOf(name)));

        unlockedForms = new HashSet<>();
        unlockedForms.add(Form.BASE);
        for (String form : (List<String>) data.getOrDefault("unlocked-forms", new ArrayList<>()))
            unlockedForms.add(Form.valueOf(form));
        form = data.containsKey("form") ? Form.valueOf((String) data.get("form")) : Form.BASE;

        attributePoints = (int) data.getOrDefault("attribute-points", 0);
        attributes = new HashMap<>();
        Map<String, Object> attributeData = (Map<String, Object>) data.getOrDefault("attributes", new HashMap<>());
        for (Attribute attribute : Attribute.values())
            attributes.put(attribute, (int) attributeData.getOrDefault(attribute.toString(), 0));

        level = (int) data.getOrDefault("level", 1);
        experience = (int) data.getOrDefault("experience", 0);

        health = (int) data.getOrDefault("health", getMaxHealth());
        ki = (int) data.getOrDefault("ki", getMaxKi());
        stamina = (int) data.getOrDefault("stamina", getMaxStamina());

        // Debugging Code starts here // TODO

        abilities.add(Ability.FLIGHT);

        // Debug end

        FighterManager.getFighters().put(uuid, this);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();

        List<String> abilities = new ArrayList<>();
        this.abilities.forEach(ability -> abilities.add(ability.toString()));
        serialized.put("abilities", abilities);

        List<String> unlockedForms = new ArrayList<>();
        this.unlockedForms.forEach(form -> unlockedForms.add(form.toString()));
        serialized.put("unlocked-forms", unlockedForms);
        serialized.put("form", form.toString());

        serialized.put("level", level);
        serialized.put("experience", experience);

        serialized.put("health", health);
        serialized.put("ki", ki);
        serialized.put("stamina", stamina);

        serialized.put("attribute-points", attributePoints);

        Map<String, Object> attributes = new HashMap<>();
        this.attributes.forEach((k, v) -> attributes.put(k.toString(), v));
        serialized.put("attributes", attributes);

        return serialized;
    }

    public void update() {
        updateLevels();
        updateUI();

        sight.setLocation(getPlayer().getLocation());

        updateCharge();
        updateFlight();

        processes.update();
    }

    private void updateLevels() {
        health = MathUtils.clamp(health, 0, getMaxHealth());
        ki = MathUtils.clamp(ki, 0, getMaxKi());
        stamina = MathUtils.clamp(stamina, 0, getMaxStamina());
    }

    private void updateUI() {
        double healthScale = ((double) health) / getMaxHealth();
        double kiScale = ((double) ki) / getMaxKi();
        double staminaScale = ((double) stamina) / getMaxStamina();

        Player player = getPlayer();

        player.setLevel(level);
        player.setExp((float) healthScale);
        player.setHealth(kiScale * 20);
        player.setFoodLevel((int) (staminaScale * 20));
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
            getPlayer().setVelocity(velocity);

            if (player.getVelocity().getY() < -1.9 && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).isPassable())
                player.getWorld().createExplosion(player.getLocation().clone().add(0, -1, 0), (float) (5 * Math.abs(player.getVelocity().getY()) / 1.9), false);
        } else {
            getPlayer().setFlying(true);
            state = MoveState.FLY;
        }
    }

    public void unload() {
        processes.endAll();
        FighterManager.getFighters().remove(uuid);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setCharging(boolean enable) {
        if (state == MoveState.TRANSFORM)
            return;

        if (enable) {
            if (state != MoveState.CHARGE)
                models.set(currentAppearance == null ? originalAppearance.getAuraItem() : currentAppearance.getAuraItem(), "aura");

            state = MoveState.CHARGE;

            getPlayer().setWalkSpeed(0);
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 0, true, false));
        } else {
            getPlayer().setGravity(true);
            getPlayer().setWalkSpeed(.2f);
            getPlayer().removePotionEffect(PotionEffectType.JUMP);
            state = getPlayer().isFlying() ? MoveState.FLY : MoveState.NORMAL;

            models.remove("aura");
        }
    }

    public void resetState() {
        state = getPlayer().isOnGround() ? MoveState.NORMAL : MoveState.FLY;
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

    public boolean isFlying() {
        return state == MoveState.FLY || state == MoveState.DASH;
    }

    public Appearance getAppearance() {
        return currentAppearance == null ? originalAppearance : currentAppearance;
    }

    public void setAppearance(Appearance appearance) {
        currentAppearance = appearance;
        currentAppearance.apply(this, originalAppearance);
    }

    public int getMaxHealth() {
        return Attribute.getMaxHealth(attributes.get(Attribute.MAX_HEALTH));
    }

    public int getMaxKi() {
        return Attribute.getMaxHealth(attributes.get(Attribute.MAX_KI));
    }

    public int getMaxStamina() {
        return Attribute.getMaxHealth(attributes.get(Attribute.MAX_STAMINA));
    }

    public void setForm(Form form) {
        this.form = form;
        setAppearance(form.getAppearance() == null ? originalAppearance : form.getAppearance());
    }

    public void transform(Form form) {
        if (isCharging())
            return;

        if (form == null || this.form == form)
            return;

        if (!unlockedForms.contains(form))
            return;

        if (!this.form.turnsInto(form))
            return;

        if (processes.<TransformationProcess>getProcessCount() != 0)
            return;

        if (form == Form.BASE) {
            setForm(form);
            return;
        }

        state = MoveState.TRANSFORM;
        processes.addAndStart(new TransformationProcess(this, form));
    }

}
