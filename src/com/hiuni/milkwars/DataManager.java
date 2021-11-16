package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DataManager {

    private static boolean hasChanged = false;

    public static void registerChanges() {
        DataManager.hasChanged = true;
    }

    public static boolean save() {
        // Saves the clan data to file.

        if (!DataManager.hasChanged) {
//            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Clan data" +
//                    " has not changed, no reason to save to file.");
            return true;
        }

//        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars]" +
//                " Saving data to file.");

        FileManager.setup(MilkWars.getInstance(), "ClanData.yml");
        MilkWars.clans[0].save(FileManager.getConfig(), "cows");
        MilkWars.clans[1].save(FileManager.getConfig(), "sheep");
        return FileManager.saveConfig();
    }

    public static boolean load() {
        // Loads the data from file.
        // The return value from this doesn't really mean much yet lol.

        FileManager.setup(MilkWars.getInstance(), "ClanData.yml");
        MilkWars.clans[0].load(FileManager.getConfig(), "cows");
        MilkWars.clans[1].load(FileManager.getConfig(), "sheep");
        DataManager.hasChanged = false;
        return true;
    }

}
