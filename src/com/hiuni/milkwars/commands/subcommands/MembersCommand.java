package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.Clan;
import com.hiuni.milkwars.ClanMember;
import com.hiuni.milkwars.MilkWars;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.Collator;
import java.util.Collection;
import java.util.TreeSet;

public class MembersCommand {
    private final CommandAPICommand membersList = new CommandAPICommand("list")
            .withArguments(new MultiLiteralArgument("cows", "sheep"))
            .executes((sender, args) -> {
                int clanIndex = 0;
                switch ((String) args[0]) {
                    case "cows" -> clanIndex = 0;
                    case "sheep" -> clanIndex = 1;
                }
                Clan clan = MilkWars.clans[clanIndex];

                int clanSize = clan.getAllMembers().size();
                if (clanSize == 0) {
                    sender.sendMessage(String.format(ChatColor.YELLOW + "The %s is empty", clan.getName()));
                    return;
                }

                // Tree sets are automatically sorted. Handy!
                Collection<String> names = new TreeSet<>(Collator.getInstance());
                String out = ChatColor.YELLOW + "Members of the " + clan.getName() + " (" +
                        ChatColor.GOLD + "leaders" + ChatColor.YELLOW + "):\n";

                for (ClanMember clanMember: clan.getAllMembers()) {
                    if (clanMember.isLeader()) {
                        names.add(ChatColor.GOLD + clanMember.getName());
                    }
                    else {
                        names.add(ChatColor.YELLOW + clanMember.getName());
                    }
                }

                String delimiter = ChatColor.YELLOW + ", ";
                if (clanSize > 15) {
                    delimiter = "\n";
                }
                out += String.join(delimiter, names);

                sender.sendMessage(out);
            });

    private final CommandAPICommand membersJoin = new CommandAPICommand("join")
            .withPermission(CommandPermission.OP)
            .withArguments(new PlayerArgument("player"))
            .withArguments(new MultiLiteralArgument("cows", "sheep"))
            .executes((sender, args) -> {
                Player player = (Player) args[0];

                int clanIndex = 0;
                switch ((String) args[1]) {
                    case "cows" -> clanIndex = 0;
                    case "sheep" -> clanIndex = 1;
                }
                Clan clan = MilkWars.clans[clanIndex];
                Clan oppositeClan = MilkWars.clans[1 - clanIndex];

                // First test if they're a part of the sheep clan...
                if (oppositeClan.hasMember(player)) {
                    CommandAPI.fail("Cannot be a member of both clans!");
                    return;
                }

                // Try to add them to the clan...
                if (clan.addMember(player)) {
                    sender.sendMessage(
                            String.format(
                                    ChatColor.GREEN + "Successfully added %s to the %s!",
                                    player.getName(),
                                    clan.getName()
                            )
                    );
                    player.sendMessage(
                            String.format(ChatColor.GREEN + "Welcome to the %s!", clan.getName())
                    );
                    return;
                }

                // They're already a part of the clan!
                CommandAPI.fail("Player is already a member of the clan!");
            });

    private final CommandAPICommand membersLeave = new CommandAPICommand("leave")
            .withPermission(CommandPermission.OP)
            .withArguments(new PlayerArgument("player"))
            .executes((sender, args) -> {
                Player player = (Player) args[0];

                // Try to add the player to each clan
                for (Clan clan: MilkWars.clans) {
                    if (clan.removeMember(player)) {
                        sender.sendMessage(
                                String.format(
                                        ChatColor.GREEN + "Successfully removed %s from the %s!",
                                        player.getName(),
                                        clan.getName()
                                )
                        );
                        player.sendMessage(
                                String.format(ChatColor.GREEN + "Left the %s", clan.getName())
                        );
                        return;
                    }
                }

                // If we've got here, the player isn't in any clan
                CommandAPI.fail("Player isn't a part of a clan!");
            });

    private final CommandAPICommand membersPromote = new CommandAPICommand("promote")
            .withPermission(CommandPermission.OP)
            .withArguments(new PlayerArgument("player"))
            .executes((sender, args) -> {
               Player player = (Player) args[0];

                // Try and promote the player in both clans
                for (Clan clan: MilkWars.clans) {
                    if (clan.promote(player)) {
                        sender.sendMessage(ChatColor.GREEN + "Promoted Successfully");
                        player.sendMessage(
                                String.format(ChatColor.GREEN + "You are now a leader of the %s!", clan.getName())
                        );
                        return;
                    }
                }

                // If we've got here, the player isn't in any clan
                CommandAPI.fail("Player must be a normal member of a clan to be promoted");
            });

    private final CommandAPICommand membersDemote = new CommandAPICommand("demote")
            .withPermission(CommandPermission.OP)
            .withArguments(new PlayerArgument("player"))
            .executes((sender, args) -> {
                Player player = (Player) args[0];

                // Try and demote the player in both clans
                for (Clan clan: MilkWars.clans) {
                    if (clan.demote(player)) {
                        sender.sendMessage(ChatColor.GREEN + "Demoted Successfully");
                        player.sendMessage(
                                String.format(ChatColor.GREEN + "You have been demoted from the %s!", clan.getName())
                        );
                        return;
                    }
                }

                // If we've got here, the player isn't a leader of a clan
                CommandAPI.fail("Player must be a leader of a clan to be demoted");
            });

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("members")
                .withSubcommand(membersList)
                .withSubcommand(membersJoin)
                .withSubcommand(membersLeave)
                .withSubcommand(membersPromote)
                .withSubcommand(membersDemote);
    }
}
