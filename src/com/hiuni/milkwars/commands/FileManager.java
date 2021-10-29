package com.hiuni.milkwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FileManager {
    static FileConfiguration fileConfig;
    static File file;
    static String fileName;

    public static boolean setup(JavaPlugin plugin, String ymlName) {
        file = new File(plugin.getDataFolder(), ymlName);

        if (!file.exists()) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "[Milk-Wars] Could not find " + ymlName + " Creating new file.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe("[Milk-Wars] Failed to create new file.");
                return false;
            }
        }
        fileName = ymlName;
        fileConfig = YamlConfiguration.loadConfiguration(file);
        return true;
    }

    public static FileConfiguration getConfig() {
        return fileConfig;
    }

    public static boolean saveConfig() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe("[Milk-Wars] Failed to save " + fileName);
            return false;
        }
        return true;
    }

    public static void reloadConfig() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }
}
