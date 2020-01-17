package me.codalot.dragonblock.listeners.types;

import me.codalot.dragonblock.DragonBlock;
import me.codalot.dragonblock.game.players.Fighter;
import me.codalot.dragonblock.game.players.components.MoveState;
import me.codalot.dragonblock.listeners.HandledListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

@SuppressWarnings("unused")
public class FighterListener extends HandledListener {

    public FighterListener(DragonBlock plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event);

        if (fighter == null)
            return;

        fighter.setFlying(event.isFlying());
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerSwapHandItem(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
        DragonBlock.getInstance().getFighters().getFighter(event).toggleCharge();
    }

    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        Fighter fighter = DragonBlock.getInstance().getFighters().getFighter(event.getEntity());
        if (fighter != null && fighter.getState() == MoveState.DASH)
            event.setCancelled(true);
    }

}
