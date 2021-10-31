package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.MilkWars;
import com.hiuni.milkwars.DataManager;
import com.hiuni.milkwars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class FileCommand extends SubCommand {

    private static MilkWars plugin;
    public void setPlugin(MilkWars plugin){
        FileCommand.plugin = plugin;
    }

    @Override
    public String getName() {
        return "file";
    }

    @Override
    public String getDescription() {
        return "Save or Load the clan data to/from file.";
    }

    @Override
    public String getSyntax() {
        return null; // TODO add getSyntax to all the commands.
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args[1].equalsIgnoreCase("save")) {
            if (DataManager.save()) {
                player.sendMessage(ChatColor.GREEN + "[Milk-Wars] Successfully saved clans.");
            } else {
                player.sendMessage(ChatColor.RED + "[Milk-Wars] Something went wrong, couldn't save the clans.");
            }
        } else if (args[1].equalsIgnoreCase("load")) {
            if (DataManager.load()) {
                player.sendMessage(ChatColor.GREEN + "[Milk-Wars] Successfully loaded clans.");
            } else {
                player.sendMessage(ChatColor.RED + "[Milk-Wars] Something went wrong, couldn't load the clans.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "[Milk-Wars] Usage: /clan file [save | load]");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
