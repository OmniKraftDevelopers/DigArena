package me.killerkoda13.DigArena.EventListener;

import me.killerkoda13.DigArena.DigArena;
import me.killerkoda13.DigArena.FileUtils.RewardManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Alex on 7/17/2016.
 */
public class BlockEvent implements Listener {

    RewardManager manager = new RewardManager();

    public Block isRewardVisible(Block b) {
        if (b.getRelative(BlockFace.DOWN).hasMetadata("reward")) {
            return b.getRelative(BlockFace.DOWN);
        } else if (b.getRelative(BlockFace.UP).hasMetadata("reward")) {
            return b.getRelative(BlockFace.UP);
        } else if (b.getRelative(BlockFace.NORTH).hasMetadata("reward")) {
            return b.getRelative(BlockFace.NORTH);
        } else if (b.getRelative(BlockFace.SOUTH).hasMetadata("reward")) {
            return b.getRelative(BlockFace.SOUTH);
        } else if (b.getRelative(BlockFace.EAST).hasMetadata("reward")) {
            return b.getRelative(BlockFace.EAST);
        } else if (b.getRelative(BlockFace.WEST).hasMetadata("reward")) {
            return b.getRelative(BlockFace.WEST);
        } else {
            return null;
        }

    }

    @EventHandler
    public void BreakBlockE(BlockBreakEvent event) {

        event.getPlayer().sendMessage(String.valueOf(event.getBlock().hasMetadata("reward")));
        if (isRewardVisible(event.getBlock()) != null) {
            Block block = isRewardVisible(event.getBlock());
            ItemStack stack = manager.getReward(block.getMetadata("reward_id").get(0).asString());
            block.setType(Material.CHEST);
            Chest chest = (Chest) block.getState();
            chest.getInventory().addItem(stack);
            block.removeMetadata("reward", DigArena.getPlugin());


            //chest.getInventory().
        }
    }

}
