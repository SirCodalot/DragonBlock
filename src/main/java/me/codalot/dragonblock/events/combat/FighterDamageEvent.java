package me.codalot.dragonblock.events.combat;

import lombok.Getter;
import lombok.Setter;
import me.codalot.dragonblock.game.fighters.combat.DamageData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class FighterDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private DamageData data;
    private boolean cancelled;

    public FighterDamageEvent(DamageData data) {
        this.data = data;
        cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
