package me.killerkoda13.DigArena;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.killerkoda13.DigArena.ArenaUtils.Generator;
import me.killerkoda13.DigArena.EventListener.BlockEvent;
import me.killerkoda13.DigArena.FileUtils.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * Created by Alex on 7/17/2016.
 */
public class DigArena extends JavaPlugin {


    static Plugin plugin; /* Used to be pulled by one method to get local plugin instance*/

    public static Plugin getPlugin() {
        return plugin;
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

        if (cmd.getName().equalsIgnoreCase("generatearena")) {

            RewardManager manager = new RewardManager();
            Generator gen = new Generator(p, p.getItemInHand().getType());
            gen.generateArena();
            //manager.addReward(p.getItemInHand(), args[0].toString(), true);
            //  manager.listRewards(p);
            Material genMat = p.getItemInHand().getType();
            p.sendMessage("Testing Information:");

        } else if (cmd.getName().equalsIgnoreCase("additem")) {
            RewardManager manager = new RewardManager();
            manager.addReward(p.getItemInHand(), args[0]);
            p.sendMessage("Added item");
        }

        return true;
    }
}
