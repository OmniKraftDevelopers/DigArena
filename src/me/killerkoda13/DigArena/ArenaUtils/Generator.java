package me.killerkoda13.DigArena.ArenaUtils;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.killerkoda13.DigArena.DigArena;
import me.killerkoda13.DigArena.FileUtils.RewardManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 7/22/2016.
 */
public class Generator {


    Player player;
    Plugin plugin;
    Material material;
    ArrayList<Block> rewardBlocks = new ArrayList<Block>();

    public Generator(Player player, Material mat) {
        this.player = player;
        this.material = mat;
        this.plugin = DigArena.getPlugin();

    }

    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    public void generateArena() {
        RewardManager manager = new RewardManager();

        for (ProtectedRegion r : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
            List<Block> blocks = blocksFromTwoPoints(new Location(player.getWorld(), r.getMaximumPoint().getBlockX(), r.getMaximumPoint().getBlockY(), r.getMaximumPoint().getBlockZ()), new Location(player.getWorld(), r.getMinimumPoint().getBlockX(), r.getMinimumPoint().getBlockY(), r.getMinimumPoint().getBlockZ()));
            for (Block block : blocks) {
                Random rand = new Random();
                int value = rand.nextInt(800);
                if (value > 798) {
                    if (block.getRelative(BlockFace.DOWN).getType() != material || block.getRelative(BlockFace.UP).getType() != material || block.getRelative(BlockFace.EAST).getType() != material || block.getRelative(BlockFace.WEST).getType() != material || block.getRelative(BlockFace.NORTH).getType() != material || block.getRelative(BlockFace.SOUTH).getType() != material) {
                        block.setType(player.getItemInHand().getType());
                    } else {
                        block.setType(Material.STAINED_GLASS);
                        block.setMetadata("reward", new FixedMetadataValue(plugin, "reward"));
                        rewardBlocks.add(block);
                    }
                } else {
                    block.setType(material);
                }
            }
            manager.generateRewards(rewardBlocks);

        }
    }
}
