package com.hiuni.milkwars;

import com.hiuni.milkwars.commands.ClanCommandManager;
import com.hiuni.milkwars.events.ClanKillCounterEvent;
import com.hiuni.milkwars.events.SaveOnWorldSave;
import com.hiuni.milkwars.commands.subcommands.FileCommand;
import com.hiuni.milkwars.commands.subcommands.TreasureCommand;
import com.hiuni.milkwars.events.SignCreateEvent;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MilkWars extends JavaPlugin {

    private static MilkWars instance;
    public static MilkWars getInstance() {
        return MilkWars.instance;
    }

    public static Clan[] clans = new Clan[2];

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig());
    }

    @Override
    public void onEnable() {
        MilkWars.instance = this;

        // Create the clans.
        clans[0] = new Clan(
                0,
                "Milk Drinkers",
                ChatColor.DARK_GRAY + "[MD] "
        );
        clans[1] = new Clan(
                1,
                "Wool Wearers", // WIP name.
                ChatColor.WHITE + "[WW] "
        );

        // Load the data, and set event for auto saving.
        DataManager.setPlugin(this);
        DataManager.load();
        getServer().getPluginManager().registerEvents(new SaveOnWorldSave(), this);

        // For manually saving and loading the data.
        new FileCommand().setPlugin(this);

        // Event for clan kill counter.
        getServer().getPluginManager().registerEvents(new ClanKillCounterEvent(), this);

        // Register the clan command
        ClanCommandManager.register();

        CommandAPI.onEnable(this);

        // Register the flag's event listeners
        for (Clan clan: clans) {
            getServer().getPluginManager().registerEvents(clan.getFlag(), this);
        }

        // Register the sign event listener.
        getServer().getPluginManager().registerEvents(new SignCreateEvent(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        DataManager.save();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Plugin has been disabled");
    }

}
