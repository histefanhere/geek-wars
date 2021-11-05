package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.MilkWars;
import com.hiuni.milkwars.DataManager;
//import com.hiuni.milkwars.commands.SubCommand;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import org.bukkit.ChatColor;

public class FileCommand {

    private static MilkWars plugin;
    public void setPlugin(MilkWars plugin){
        FileCommand.plugin = plugin;
    }

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("file")
                .withPermission(CommandPermission.OP)
                .withArguments(new MultiLiteralArgument("save", "load"))
                .executes((sender, args) -> {
                    switch ((String) args[0]) {
                        case "save":
                            if (DataManager.save()) {
                                sender.sendMessage(ChatColor.GREEN + "[Milk-Wars] Successfully saved clans.");
                            } else {
                                sender.sendMessage(ChatColor.RED + "[Milk-Wars] Something went wrong, couldn't save the clans.");
                            }
                            break;
                        case "load":
                            if (DataManager.load()) {
                                sender.sendMessage(ChatColor.GREEN + "[Milk-Wars] Successfully loaded clans.");
                            } else {
                                sender.sendMessage(ChatColor.RED + "[Milk-Wars] Something went wrong, couldn't load the clans.");
                            }
                    }
                });
    }
}
