package me.codalot.dragonblock.game.fighters.process.types;

import me.codalot.dragonblock.game.fighters.Fighter;
import me.codalot.dragonblock.game.fighters.components.Form;
import me.codalot.dragonblock.game.fighters.process.FighterProcess;
import me.codalot.dragonblock.utils.ColorUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TransformationProcess extends FighterProcess {

    private Form form;

    private Color from;
    private Color to;

    private TransformationState state;
    private int timer;
    private int fullTimer;

    private Set<FallingBlock> blocks;

    public TransformationProcess(Fighter fighter, Form form) {
        super(2, fighter);

        this.form = form;

        from = fighter.getAppearance().getHairColor();
        to = form.getAppearance().getHairColor();

        state = TransformationState.PRE_TRANSFORMATION;
        timer = 20 * form.getRequiredKi() / fighter.getKi();
        fullTimer = timer;
        // Timer = 20 is around 2 seconds because 20 * 2 is 40 ticks

        blocks = new HashSet<>();
    }

    @Override
    protected void onStart() {
        fighter.setCharging(true);

        Player player = fighter.getPlayer();

        if (!player.isOnGround())
            player.setGravity(false);

        player.setWalkSpeed(0);
        player.setFlySpeed(0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 0, true, false));
        fighter.activateAura();
    }

    @Override
    protected void onProgress() {
        timer--;

        switch (state) {
            case PRE_TRANSFORMATION:
                if (blocks.size() < 10)
                    createFloatingBlock();
                for (int i = 0; i < 3; i++)
                    raiseFallingBlocks();
                spawnParticle();
                changeHair();

                if (timer <= 0)
                    transform();
                break;

            case POST_TRANSFORMATION:
                if (timer <= 0)
                    end();
                break;
        }
    }

    @Override
    protected void onEnd() {
        Player player = fighter.getPlayer();

        player.setGravity(true);
        player.setWalkSpeed(.2f);
        player.setFlySpeed(.2f);
        player.removePotionEffect(PotionEffectType.JUMP);
        fighter.resetState();

        blocks.forEach(block -> block.setGravity(true));

        fighter.setCharging(false);
        fighter.deactivateAura();
    }

    private Block getNearbyBlock() {
        Location playerLocation = fighter.getPlayer().getLocation();
        int x = playerLocation.getBlockX() + ThreadLocalRandom.current().nextInt(-5, 5);
        int z = playerLocation.getBlockZ() + ThreadLocalRandom.current().nextInt(-5, 5);
        int y = playerLocation.getBlockY() - 2;

        Block block = playerLocation.getWorld().getBlockAt(x, y, z);
        Block above;
        int limit = 5;
        while (!(above = block.getRelative(BlockFace.UP)).isEmpty()) {
            block = above;
            limit--;
            if (limit < 0)
                return null;
        }

        return block;
    }

    private void spawnParticle() {
        Location playerLocation = fighter.getPlayer().getLocation();
        Block block = getNearbyBlock();
        if (block == null)
            return;

        if (ThreadLocalRandom.current().nextBoolean())
            playerLocation.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0,1,0), 100, block.getBlockData());
        else
            playerLocation.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, block.getX(), block.getY() + 1, block.getZ(), 0, 0, 0.1, 0, 1);
    }

    private void createFloatingBlock() {
        Location playerLocation = fighter.getPlayer().getLocation();
        Block block = getNearbyBlock();
        if (block == null)
            return;

        BlockData data = block.getBlockData();
        block.setType(Material.AIR);
        FallingBlock fallingBlock = playerLocation.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), data);
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setInvulnerable(true);

        blocks.add(fallingBlock);
    }

    private void raiseFallingBlocks() {
        Vector velocity = new Vector(0, 0.03, 0);
        blocks.forEach(block -> block.setVelocity(velocity));
    }

    private void changeHair() {
        ItemStack hair = fighter.getPlayer().getInventory().getHelmet();
        if (hair == null || !(hair.getItemMeta() instanceof LeatherArmorMeta))
            return;

        double progress = 1 - (((double) timer) / ((double) fullTimer));
        Color color = ColorUtils.lerp(from, to, progress);

        LeatherArmorMeta meta = (LeatherArmorMeta) hair.getItemMeta();
        meta.setColor(color);
        hair.setItemMeta(meta);
    }

    private void transform() {
        timer = 10; // equals to 1 second
        state = TransformationState.POST_TRANSFORMATION;
        fighter.setForm(form);
    }

    private enum TransformationState {
        PRE_TRANSFORMATION,
        POST_TRANSFORMATION;
    }
}
