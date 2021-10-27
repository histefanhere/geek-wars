package com.hiuni.milkwars;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MilkWars extends JavaPlugin {

    public static Clan[] clans = new Clan[2];

    @Override
    public void onEnable() {

        clans[0] = new Clan("Milk Drinkers");
        clans[1] = new Clan("Wool Wearers"); // WIP name.

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Plugin has been disabled");
    }

}
