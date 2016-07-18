package me.killerkoda13.DigArena;

import me.killerkoda13.DigArena.EventListener.BlockEvent;
import me.killerkoda13.DigArena.FileUtils.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Alex on 7/17/2016.
 */
public class DigArena extends JavaPlugin {


    static Plugin plugin; /* Used to be pulled by one method to get local plugin instance*/

    public static Plugin getPlugin() {
        return plugin;
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

    /**
     * pulls local plugin instance
     */

    public void loadConfiguration() {
        String generator = "Settings.Generator.RandomSeed";
        String prevalence = "Settings.Generator.Prevalence";
        getConfig().addDefault(generator, "800");
        getConfig().addDefault(prevalence, "798");

        getConfig().options().copyDefaults(true);
        //Save the config whenever you manipulate it
        saveConfig();
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "DigArena plugin enabled!");
        loadConfiguration();
        plugin = this; /* Initialize local plugin instance*/
        getServer().getPluginManager().registerEvents(new BlockEvent(), this);

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "DigArena plugin disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
        } else {
            Bukkit.getLogger().log(Level.INFO, "Cannot run this command from console");
        }

        if(cmd.getName().equalsIgnoreCase("test")) {

            RewardManager manager = new RewardManager();
            manager.addReward(p.getItemInHand(), args[0].toString(), true);
            manager.listRewards(p);
//            p.sendMessage("Testing Information:");
//            for (ProtectedRegion r : WGBukkit.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
//                List<Block> blocks = blocksFromTwoPoints(new Location(p.getWorld(), r.getMaximumPoint().getBlockX(), r.getMaximumPoint().getBlockY(), r.getMaximumPoint().getBlockZ()), new Location(p.getWorld(), r.getMinimumPoint().getBlockX(), r.getMinimumPoint().getBlockY(), r.getMinimumPoint().getBlockZ()));
//                for (Block block : blocks) {
//                    Random rand = new Random();
//                    int value = rand.nextInt(800);
//                    if (value > Integer.parseInt(args[0].toString())) {
//                        if (block.getRelative(BlockFace.DOWN).getType() != Material.GLASS || block.getRelative(BlockFace.UP).getType() != Material.GLASS || block.getRelative(BlockFace.EAST).getType() != Material.GLASS || block.getRelative(BlockFace.WEST).getType() != Material.GLASS || block.getRelative(BlockFace.NORTH).getType() != Material.GLASS || block.getRelative(BlockFace.SOUTH).getType() != Material.GLASS) {
//                            block.setType(Material.GLASS);
//                        } else {
//                            block.setType(Material.STAINED_GLASS);
//                            block.setMetadata("reward", new FixedMetadataValue(plugin, "reward"));
//                        }
//                    } else {
//                        block.setType(p.getItemInHand().getType());
//                    }
//                }
//                p.sendMessage("Changed: " + blocks.size());
//                p.sendMessage("Name: " + r.getId());
            //}
        }

        return true;
    }
}
