package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.Clan;
import com.hiuni.milkwars.MilkWars;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TestMembersCommand {
    private final CommandAPICommand membersJoin = new CommandAPICommand("join")
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
                            String.format(ChatColor.GREEN + "Successfully added %s to the %s!", player.getName(), clan.getName())
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
            .withArguments(new PlayerArgument("player"))
            .executes((sender, args) -> {
                Player player = (Player) args[0];

                // Try to add the player to each clan
                for (Clan clan: MilkWars.clans) {
                    if (clan.removeMember(player)) {
                        sender.sendMessage(
                                String.format(ChatColor.GREEN + "Successfully removed %s from the %s!", player.getName(), clan.getName())
                        );
                        player.sendMessage(
                                String.format(ChatColor.GREEN + "Left the %s", clan.getName())
                        );
                        return;
                    }
                }

                // If we've got here, the player isn't in any clan
                CommandAPI.fail(ChatColor.RED + "You are not in any clan!");
            });

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("members")
                .withSubcommand(membersJoin)
                .withSubcommand(membersLeave);
    }
}
