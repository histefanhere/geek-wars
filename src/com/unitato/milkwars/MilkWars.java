package com.unitato.milkwars;

import org.bukkit.plugin.java.JavaPlugin;

public class MilkWars extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§2[Milk-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§4[Milk-Wars] Plugin has been disabled.");
    }

}
