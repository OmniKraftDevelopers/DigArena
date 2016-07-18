package me.killerkoda13.DigArena.FileUtils;

import me.killerkoda13.DigArena.DigArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Alex on 7/18/2016.
 */
public class RewardManager {

    BufferedWriter writer;
    File rewardFolder = new File(DigArena.getPlugin().getDataFolder() + "/rewards/");

    public RewardManager() {
        if (!rewardFolder.exists()) {
            rewardFolder.mkdirs();
        }
    }

    public boolean addReward(ItemStack stack, String ID, boolean recurring) {
        File file = new File(rewardFolder + "/" + ID + "'" + recurring + "'" + ".txt");
        if (file.exists()) {
            return false;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String serial = SerializerUtil.itemTo64(stack);
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(serial);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true; //TODO made this return a bool so that in the future if it returns false I can return error message to player
        }
    }

    public void listRewards(Player player) {
        player.sendMessage(ChatColor.GREEN + "Rewards:      Recurring:");
        for (File file : rewardFolder.listFiles()) {
            String name = file.getName().split("'")[0];
            player.sendMessage(ChatColor.GOLD + "ID:" + name + " | " + file.getName().split("'")[1].split("'")[0]);

        }
    }

    public void getReward(String ID, Player p) {
        ArrayList<String> ids = new ArrayList<String>();
        for (File file : rewardFolder.listFiles()) {
            ids.add(file.getName().split("'")[0]);
        }

        if (ids.contains(ID)) {
            for (File file : rewardFolder.listFiles()) {
                if (file.getName().contains(ID)) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        String whole = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                whole += line;
                            }
                            ItemStack stack = SerializerUtil.itemFrom64(whole);
                            p.getInventory().addItem(stack);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "No item with this id was found.");
                }
            }
        }

    }


}
