package me.killerkoda13.DigArena.Utilities;

/**
 * Created by Alex on 7/17/2016.
 */

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.killerkoda13.DigArena.DigArena;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtilities {


    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = DigArena.getPlugin().getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }
}
