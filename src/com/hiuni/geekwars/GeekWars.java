package com.hiuni.geekwars;

import com.hiuni.geekwars.commands.CommandManager;
import com.hiuni.geekwars.events.ClanKillCounterEvent;
import com.hiuni.geekwars.events.PlayerConsumeEvent;
import com.hiuni.geekwars.events.SaveOnWorldSave;
import com.hiuni.geekwars.events.SignCreateEvent;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GeekWars extends JavaPlugin {

    private static GeekWars instance;
    public static GeekWars getInstance() {
        return GeekWars.instance;
    }

    public static Clan[] clans = new Clan[2];

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig());
    }

    @Override
    public void onEnable() {
        GeekWars.instance = this;

        // Create the clans.
        clans[0] = new Clan(
                0,
                "Milk Drinkers",
                ChatColor.DARK_GRAY + "[MD] "
        );
        clans[1] = new Clan(
                1,
                "Wool Weavers",
                ChatColor.WHITE + "[WW] "
        );

        // Load the data from file
        DataManager.load();

        // Register the commands
        CommandManager.register();
        CommandAPI.onEnable(this);

        // Register all the event listeners to Bukkit
        Listener[] listeners = {
                new SaveOnWorldSave(),
                new ClanKillCounterEvent(),
                clans[0].getFlag(),
                clans[1].getFlag()
        };
        for (Listener woman: listeners) {
            getServer().getPluginManager().registerEvents(woman, this);
        }

        // Register the sign event listener.
        getServer().getPluginManager().registerEvents(new SignCreateEvent(), this);

        // Register the item consume listener.
        getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Geek-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        DataManager.save();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Geek-Wars] Plugin has been disabled");
    }

}
