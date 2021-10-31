package com.hiuni.milkwars;

import org.bukkit.plugin.java.JavaPlugin;

public class DataManager {

    private static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin plugin) {
        DataManager.plugin = plugin;
    }

    public static boolean save() {
        // Saves the clan data to file.

        // Should probably make sure plugin has been set before trying.
        FileManager.setup(DataManager.plugin, "ClanData.yml");
        MilkWars.clans[0].save(FileManager.getConfig(), "cows");
        MilkWars.clans[1].save(FileManager.getConfig(), "sheep");
        return FileManager.saveConfig();
    }

    public static boolean load() {
        // Loads the data from file.
        // The return value from this doesn't really mean anything yet lol.

        FileManager.setup(DataManager.plugin, "ClanData.yml");
        MilkWars.clans[0].load(FileManager.getConfig(), "cows");
        MilkWars.clans[1].load(FileManager.getConfig(), "sheep");
        return true;
    }

}
