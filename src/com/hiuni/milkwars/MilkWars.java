package com.hiuni.milkwars;

import com.hiuni.milkwars.commands.ClanCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MilkWars extends JavaPlugin {

    public static Clan[] clans = new Clan[2];

    @Override
    public void onEnable() {
        clans[0] = new Clan("Milk Drinkers");
        clans[1] = new Clan("Wool Wearers"); // WIP name.

        getCommand("clan").setExecutor(new ClanCommandManager(this));

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Plugin has been disabled");
    }

    public boolean save() {
        // Should probably move this somewhere else, but it's fine for now.
        FileManager.setup(this, "ClanData.yml");
        clans[0].save(FileManager.getConfig(), "cows");
        clans[1].save(FileManager.getConfig(), "sheep");
        return FileManager.saveConfig();
    }

    public boolean load() {
        // And of course move this aswell.
        FileManager.setup(this, "ClanData.yml");
        clans[0].load(FileManager.getConfig(), "cows");
        clans[1].load(FileManager.getConfig(), "sheep");
        return true;
    }

}
