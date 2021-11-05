package com.hiuni.milkwars;

import com.hiuni.milkwars.commands.ClanCommandManager;
import com.hiuni.milkwars.events.SaveOnWorldSave;
import com.hiuni.milkwars.commands.subcommands.FileCommand;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MilkWars extends JavaPlugin {

    public static Clan[] clans = new Clan[2];

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig());
    }

    @Override
    public void onEnable() {

        // Create the clans.
        clans[0] = new Clan("Milk Drinkers", ChatColor.DARK_GRAY + "[MD] ");
        clans[1] = new Clan("Wool Wearers", ChatColor.WHITE + "[WW] ");

        // Load the data, and set event for auto saving.
        DataManager.setPlugin(this);
        DataManager.load();
        getServer().getPluginManager().registerEvents(new SaveOnWorldSave(), this);

        // For manually saving and loading the data.
        new FileCommand().setPlugin(this);

        // Register the clan command
        ClanCommandManager.register();

        CommandAPI.onEnable(this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        DataManager.save();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Plugin has been disabled");
    }

}
