package me.killerkoda13.DigArena;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by Alex on 7/17/2016.
 */
public class DigArena extends JavaPlugin {


    static Plugin plugin; /* Used to be pulled by one method to get local plugin instance*/

    public static Plugin getPlugin() {
        return plugin;
    } /** pulls local plugin instance*/

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "DigArena plugin enabled!");
        plugin = this; /* Initialize local plugin instance*/
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


        if(cmd.getName().equalsIgnoreCase("test"))
        {
            p.sendMessage("Test");
        }

        return true;
    }
}
