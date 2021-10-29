package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.MilkWars;
import com.hiuni.milkwars.commands.subcommands.FileCommand;
import com.hiuni.milkwars.commands.subcommands.MembersCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// If we ever end up needing a lot more commands, it'd be cool to use this:
// https://github.com/aikar/commands

public class ClanCommandManager implements TabExecutor {
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public ClanCommandManager(MilkWars plugin) {
        subcommands.add(new MembersCommand());
        FileCommand fileCommand = new FileCommand();
        fileCommand.setPlugin(plugin);
        subcommands.add(fileCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0){
                for (SubCommand subcommand : subcommands) {
                    if (args[0].equalsIgnoreCase(subcommand.getName())) {
                        subcommand.perform(player, args);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (SubCommand subcommand : subcommands) {
                subcommandsArguments.add(subcommand.getName());
            }

            return subcommandsArguments;
        }
        else if (args.length >= 2) {
            for (SubCommand subcommand : subcommands) {
                if (args[0].equalsIgnoreCase(subcommand.getName())) {
                    return subcommand.getSubcommandArguments((Player) sender, args);
                }
            }
        }

        return new ArrayList<>();
    }
}
