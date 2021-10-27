package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.MilkWars;
import com.hiuni.milkwars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LeadersCommand extends SubCommand {

    @Override
    public String getName() {
        return "leaders";
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
        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("list")) {
                if (args.length == 3) {
                    if (args[2].equalsIgnoreCase("cows")) {
//                        TODO: List leaders of cow clan
                        player.sendMessage("Listing leaders of cow clan...");
                    }
                    else if (args[2].equalsIgnoreCase("sheep")) {
//                        TODO: List leaders of sheep clan
                        player.sendMessage("Listing leaders in sheep clan...");
                    }
                    else {
                        player.sendMessage("Usage: /clan leaders list <cows | sheep>");
                    }
                }
                else {
//                    TODO: list the leaders in the players clan
                    player.sendMessage("Listing leaders in your clan...");
                }
            }

            else if (args[1].equalsIgnoreCase("add")) {
                if (args.length == 3) {
//                    TODO: promote args[2] to a leader of their clan
//                  player.sendMessage("Usage: /clan leaders add <player>");
                }
                else {
                    player.sendMessage("Usage: /clan leaders add <player>");
                }
            }

            else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
//                    TODO: remove args[2] from being a leader of their clan
//                    player.sendMessage("Usage: /clan leaders remove <player>");
                }
                else {
                    player.sendMessage("Usage: /clan leaders remove <player>");
                }
            }

            else {
                player.sendMessage("Usage: /clan leaders <list | add | remove>");
            }
        }
        else {
            player.sendMessage("Usage: /clan leaders <list | add | remove>");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        List<String> arguments = new ArrayList<>();

        if (args.length == 2) {
            arguments.add("list");
            arguments.add("add");
            arguments.add("remove");
        }

        else if (args.length == 3) {
            switch (args[1].toLowerCase()) {
                case "list":
                    arguments.add("cows");
                    arguments.add("sheep");
                    break;
                case "add":
                case "remove":
//                    TODO: list online users
                    arguments.add("allonlineusers");
                    break;
                default:
                    break;
            }
        }

        return arguments;
    }
}
