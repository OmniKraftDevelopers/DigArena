package me.killerkoda13.DigArena;

import me.killerkoda13.DigArena.ArenaUtils.Generator;
import me.killerkoda13.DigArena.EventListener.BlockEvent;
import me.killerkoda13.DigArena.ArenaUtils.RewardManager;
import me.killerkoda13.DigArena.FileUtils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    }



    /**
     * pulls local plugin instance
     */



    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "DigArena plugin enabled!");
        ConfigManager manager = new ConfigManager(this);
        manager.loadConfiguration();
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

        if (cmd.getName().equalsIgnoreCase("DigArena")) {

            if (args.length == 0) /** Show help menu*/ {
                p.sendMessage(ChatColor.GRAY + "Dig arena help menu");
                p.sendMessage(ChatColor.GREEN + "/digarena generate <WorldGuard_Region>");
                p.sendMessage(ChatColor.GREEN + "/digarena additem <id>");
                p.sendMessage(ChatColor.GREEN + "/digarena remitem <id>");
                p.sendMessage(ChatColor.GREEN + "/digarena list");
            } else {
                switch (args[0].toString().toLowerCase()) {
                    case "generate":
                        if (args.length == 1) {
                            p.sendMessage(ChatColor.RED + "Please enter region name");
                        } else if (args.length == 2) {
                            Generator gen = new Generator(p, p.getItemInHand().getType(), args[1].toString());
                            gen.generateArena();
                            p.sendMessage(ChatColor.GREEN + "Arena generated with " + Generator.rewardAmount + " chests");
                        }

                        p.sendMessage("Generated arena");
                        break;
                    case "additem":
                        if (p.getItemInHand().getType() != Material.AIR) {
                            if (args.length == 1) {
                                p.sendMessage(ChatColor.RED + "Please enter reward item ID");
                            } else if (args.length == 2) {

                                RewardManager manager = new RewardManager();
                                boolean passed = manager.addReward(p.getItemInHand(), args[1]);
                                if (passed == true) {
                                    p.sendMessage(ChatColor.GREEN + "Successfully added reward with ID " + args[1].toUpperCase());
                                } else {
                                    p.sendMessage(ChatColor.RED + "Failed to add item to reward list. Likely ID is already used.");
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Cannot create reward with material type AIR");
                        }
                        break;
                    case "removeitem":
                        RewardManager manager = new RewardManager();
                        if (args.length == 1) {
                            boolean passed = manager.removeReward(args[1]);
                            if (passed == true) {
                                p.sendMessage(ChatColor.GREEN + args[1] + " successfully removed");
                            } else {
                                p.sendMessage(ChatColor.RED + args[1] + " was unable to be removed. Likely item ID is invalid");
                            }
                        }
                        break;
                    case "list":
                        RewardManager manager1 = new RewardManager();
                        manager1.listRewards(p);
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "Incorrect sub command. Try doing /digarena for more help.");
                        break;
                }
            }
        }
        return true;
    }


}
