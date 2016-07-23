package me.killerkoda13.DigArena.FileUtils;

import org.bukkit.plugin.Plugin;

/**
 * Created by Alex on 7/22/2016.
 */
public class ConfigManager {


    Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    //TODO work on this more and add additional configs for generation

    public void loadConfiguration() {
        String generator = "Settings.Generator.RandomSeed";
        String prevalence = "Settings.Generator.Prevalence";
        plugin.getConfig().addDefault(generator, "800");
        plugin.getConfig().addDefault(prevalence, "798");

        plugin.getConfig().options().copyDefaults(true);
        //Save the config whenever you manipulate it
        plugin.saveConfig();
    }
}
