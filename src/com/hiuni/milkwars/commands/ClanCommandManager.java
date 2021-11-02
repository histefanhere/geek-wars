package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.commands.subcommands.MembersCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// If we ever end up needing a lot more commands, it'd be cool to use this:
// https://github.com/aikar/commands
// Or maybe this is simpler/better:
// https://github.com/JorelAli/CommandAPI

public class ClanCommandManager implements TabExecutor {
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public ClanCommandManager() {
        subcommands.add(new MembersCommand());
    }

    // This method gets called when any "/clan <subcommand> ..." command is executed
    // We need to perform the subcommand's actions
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

    // This method gets called when the command prompt requests a list of strings to suggest to the player
    // for tab completion
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // If all they have is "/clan " suggest them the names of the subcommands
        if (args.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (SubCommand subcommand : subcommands) {
                subcommandsArguments.add(subcommand.getName());
            }

            return subcommandsArguments;
        }

        // Else if they have more arguments pass it onto the corresponding subcommand
        // to handle the tab completion suggestions
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
