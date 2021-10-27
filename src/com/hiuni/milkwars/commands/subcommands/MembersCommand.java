package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.commands.SubCommand;
import org.bukkit.entity.Player;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MembersCommand extends SubCommand {

    @Override
    public String getName() {
        return "members";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage("members stuff");

        if (args.length >= 2) {

            if (args[1].equalsIgnoreCase("list")) {
                player.sendMessage("listing all members");
                return;
            }

            else if (args[1].equalsIgnoreCase("join")) {
                player.sendMessage("joining");
                return;
            }

            else if (args[1].equalsIgnoreCase("leave")) {
                player.sendMessage("leaving");
                return;
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        List<String> arguments = new ArrayList<>();

        if (args.length == 2) {
            arguments.add("list");
            arguments.add("join");
            arguments.add("leave");
        }

        else if (args.length == 3) {
            switch (args[1].toLowerCase()) {
                case "list":
                case "join":
//                    clan names
                    arguments.add("cows");
                    arguments.add("sheep");
                    break;
                case "leave":
                default:
                    break;
            }
        }

        return arguments;
    }
}
