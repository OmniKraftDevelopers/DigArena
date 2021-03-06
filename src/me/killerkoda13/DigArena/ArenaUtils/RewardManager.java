package me.killerkoda13.DigArena.ArenaUtils;

import me.killerkoda13.DigArena.DigArena;
import me.killerkoda13.DigArena.FileUtils.SerializerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 7/18/2016.
 */
public class RewardManager {


    ArrayList<String> used = new ArrayList<String>();
    BufferedWriter writer;
    File rewardFolder = new File(DigArena.getPlugin().getDataFolder() + "/rewards/");

    public RewardManager() {
        if (!rewardFolder.exists()) {
            rewardFolder.mkdirs();
        }
    }


    public boolean removeReward(String ID) {
        File file = new File(rewardFolder + "/" + ID.toUpperCase());
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public boolean addReward(ItemStack stack, String ID) {
        File file = new File(rewardFolder + "/" + ID.toUpperCase());
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
        player.sendMessage(ChatColor.GREEN + "Rewards:");
        for (File file : rewardFolder.listFiles()) {
            String name = file.getName();
            player.sendMessage(ChatColor.GOLD + "ID:" + name);
        }
    }

    public void generateRewards(ArrayList<Block> rewardBlocks) {
        /**
         * "Step by step for mental note so that I don't screw this up again" - koda on his 3rd damn try writing this...
         *  "yeah nope. Each reward will be put in a chest only one time. That's it. No more." - koda realizing that this is bs
         *
         *
         *  Step 1: Grab list of ids and pop them into an arraylist because a regular list is for srubs.
         *  Step 2: loop through each block in the rewardBlocks object, generating a random number the size of the arraylist
         *          then get that index and add metadata of what reward should be inside said block. Then add the reward id to list of already used ones.
         */


        //Step one
        ArrayList<String> rewards = new ArrayList<String>();
        for (File file : rewardFolder.listFiles()) {
            System.out.println(file.getName());
            rewards.add(file.getName());
        }

        //Step two
        for (Block block : rewardBlocks) {
            boolean invalid = false;
            while (invalid == false) {
                int index = new Random().nextInt(rewards.size());
                if (used.contains(rewards.get(index))) {
                    return;
                } else {
                    System.out.println(rewards.get(index));
                    block.setMetadata("reward_id", new FixedMetadataValue(DigArena.getPlugin(), rewards.get(index)));
                    used.add(rewards.get(index));
                    invalid = true;

                }
            }
            invalid = false;
        }
        used.clear();
        rewards.clear();
    }

    public ItemStack getReward(String ID) {
        ArrayList<String> ids = new ArrayList<String>();
        ItemStack stack = new ItemStack(Material.AIR);
        for (File file : rewardFolder.listFiles()) {
            ids.add(file.getName());
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
                            stack = SerializerUtil.itemFrom64(whole);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return stack;
                }
            }
        } else {
            return null;
        }
        return stack;
    }


}
