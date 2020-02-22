package me.codalot.dragonblock.listeners.types;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.events.packets.PacketPlayOutEvent;
import me.codalot.dragonblock.game.combat.attacks.KiAttackData;
import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.combat.DamageData;
import me.codalot.dragonblock.game.fighters.combat.DamageType;
import me.codalot.dragonblock.game.fighters.components.MoveState;
import me.codalot.dragonblock.game.fighters.process.types.attacks.ki.KiBeamProcess;
import me.codalot.dragonblock.listeners.HandledListener;
import me.codalot.dragonblock.utils.PacketUtils;
import me.codalot.dragonblock.utils.ReflectionUtils;
import net.minecraft.server.v1_15_R1.DataWatcher;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityMetadata;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class FighterListener extends HandledListener {

    public FighterListener(DragonBlock plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event);
        if (fighter == null)
            return;

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK:
            case LEFT_CLICK_AIR:
                updateTarget(fighter);
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                fighter.heavyAttack();
                break;
        }
    }

    private void updateTarget(Fighter fighter) {
        if (!fighter.hasTarget())
            fighter.findTarget();
        else if (fighter.getPlayer().isSneaking())
            fighter.findTarget();
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event);
        if (fighter == null)
            return;

        fighter.heavyAttack();
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event);

        if (fighter == null)
            return;

        if (event.isFlying())
            fighter.setFlying(event.isFlying());
        else if (!fighter.getPlayer().isSneaking())
            fighter.vanish();
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerSwapHandItem(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
//        DragonBlock.getInstance().getFighters().getFighter(event).toggleCharge();
        // TODO KI Charge
    }

    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event.getEntity());
        if (fighter != null && fighter.getState() == MoveState.DASH)
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityToggleSwim(EntityToggleSwimEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event.getEntity());
        if (fighter != null && fighter.getState() == MoveState.CHARGE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event.getEntity());
        if (fighter != null)
            event.setCancelled(true);
    }

    @EventHandler
    @SuppressWarnings("all")
    public void onPacketPlayOut(PacketPlayOutEvent event) {
        Fighter player = event.getData().getFighter();
        if (player == null)
            return;

        if (!(event.getPacket() instanceof PacketPlayOutEntityMetadata))
            return;

        int targetId = ReflectionUtils.getField(event.getPacket(), "a");
        Fighter target = DragonBlock.getInstance().getFighters().getFighter(targetId);
        if (target == null)
            return;

        if (player.getTarget() != target)
            return;

        event.setPacket(new PacketPlayOutEntityMetadata(targetId, PacketUtils.getGlowWatcher(target.getPlayer(), true), false));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Fighter damaged = DragonBlock.getInstance().getFighters().getFighter(event.getEntity());
        Fighter damager = DragonBlock.getInstance().getFighters().getFighter(event.getDamager());

        if (damaged == null || damager == null)
            return;

        if (damager.isCharging())
            return;

        new DamageData(damaged, damager, DamageType.PHYSICAL, damager.getAttackStrength()).execute();
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (event.isSneaking())
            return;
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event);
        if (fighter == null)
            return;

        fighter.getProcesses().addAndStart(new KiBeamProcess(fighter, KiAttackData.DEBUG_KAMEHAMEHA));
    }

}
