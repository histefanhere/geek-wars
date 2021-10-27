package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.MilkWars;
import com.hiuni.milkwars.commands.SubCommand;
import org.bukkit.ChatColor;
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
        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("list")) {
                if (args.length == 3) {
                    if (args[2].equalsIgnoreCase("cows")) {
//                        TODO: List members of cow clan
                        player.sendMessage("Listing members of cow clan...");
                    }
                    else if (args[2].equalsIgnoreCase("sheep")) {
//                        TODO: List members of sheep clan
                        player.sendMessage("Listing members in sheep clan...");
                    }
                    else {
                        player.sendMessage("Usage: /clan members list [cows | sheep]");
                    }
                }
                else {
//                    TODO: list the players in the players clan
                    player.sendMessage("Listing members in your clan...");
                }
            }

            else if (args[1].equalsIgnoreCase("join")) {
                if (args.length == 3) {
                    if (args[2].equalsIgnoreCase("cows")) {
                        joinClan(player, 0);
                    }
                    else if (args[2].equalsIgnoreCase("sheep")) {
                        joinClan(player, 1);
                    }
                    else {
                        player.sendMessage("Usage: /clan members join <cows | sheep>");
                    }
                }
                else {
                    player.sendMessage("Usage: /clan members join <cows | sheep>");
                }
            }

            else if (args[1].equalsIgnoreCase("leave")) {
                if (MilkWars.clans[0].removeMember(player)) {
                    player.sendMessage(
                            ChatColor.YELLOW + "Left the " + MilkWars.clans[0].getName()
                    );
                } else if (MilkWars.clans[1].removeMember(player)) {
                    player.sendMessage(
                            ChatColor.YELLOW + "Left the " + MilkWars.clans[1].getName()
                    );
                }
//                Player is in no clan
                else {
                    player.sendMessage(
                            ChatColor.RED + "You are not in any clan!"
                    );
                }
            }

            else {
                player.sendMessage("Usage: /clan members <list | join | leave>");
            }
        }
        else {
            player.sendMessage("Usage: /clan members <list | join | leave>");
        }
    }

    private void joinClan(Player player, int clan) {
        int oppositeClan = 1 - clan;
//        First test if they're a part of the sheep clan...
        if (MilkWars.clans[oppositeClan].hasMember(player)) {
            player.sendMessage(ChatColor.RED + "You cannot be a member of both clans!");
            return;
        }
//        Try to add them to the clan...
        if (MilkWars.clans[clan].addMember(player)) {
            player.sendMessage(
                    ChatColor.GREEN + "Welcome to the " + MilkWars.clans[clan].getName() + "!"
            );
        }
//        They're already a part of the clan!
        else {
            player.sendMessage(
                    ChatColor.RED + "You are already in the " + MilkWars.clans[clan].getName() + "!"
            );
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
