package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DataManager {

    private static JavaPlugin plugin = null;

    private static boolean hasChanged = false;

    public static void setPlugin(JavaPlugin plugin) {
        DataManager.plugin = plugin;
    }

    public static void registerChanges() {
        DataManager.hasChanged = true;
    }

    public static boolean save() {
        // Saves the clan data to file.

        if (DataManager.plugin == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Could not" +
                    " save clan data, plugin has not been set.");
            return false;
        }

        if (!DataManager.hasChanged) {
//            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Clan data" +
//                    " has not changed, no reason to save to file.");
            return true;
        }

//        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars]" +
//                " Saving data to file.");

        FileManager.setup(DataManager.plugin, "ClanData.yml");
        MilkWars.clans[0].save(FileManager.getConfig(), "cows");
        MilkWars.clans[1].save(FileManager.getConfig(), "sheep");
        return FileManager.saveConfig();
    }

    public static boolean load() {
        // Loads the data from file.
        // The return value from this doesn't really mean much yet lol.

        if (DataManager.plugin == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Could not" +
                    " load clan data, plugin has not been set.");
            return false;
        }

        FileManager.setup(DataManager.plugin, "ClanData.yml");
        MilkWars.clans[0].load(FileManager.getConfig(), "cows");
        MilkWars.clans[1].load(FileManager.getConfig(), "sheep");
        DataManager.hasChanged = false;
        return true;
    }

}
