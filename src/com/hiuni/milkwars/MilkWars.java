package com.hiuni.milkwars;

import com.hiuni.milkwars.commands.ClanCommandManager;
import com.hiuni.milkwars.commands.subcommands.FileCommand;
import com.hiuni.milkwars.commands.subcommands.TreasureCommand;
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

        new FileCommand().setPlugin(this);

        // Register the clan command
        ClanCommandManager.register();

        CommandAPI.onEnable(this);

        // Register the flag's event listeners
        for (Clan clan: clans) {
            getServer().getPluginManager().registerEvents(clan.getFlag(), this);
        }

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
