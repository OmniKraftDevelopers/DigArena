package me.killerkoda13.DigArena.Utilities.WorldGuard;

/**
 * Created by Alex on 7/17/2016.
 */

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import me.killerkoda13.DigArena.DigArena;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtilities {

    public static boolean inRegion(Location loc) {
        WorldGuardPlugin guard = getWorldGuard();
        Vector v = BukkitUtil.toVector(loc);
        RegionManager manager = guard.getRegionManager(loc.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(v);
        return set.size() > 0;
    }

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = DigArena.getPlugin().getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }
}
