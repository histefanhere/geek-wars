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
                }
                // They're already a part of the clan!
                else {
                    CommandAPI.fail("Player is already a member of the clan!");
                }
            });

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("members")
                .withSubcommand(membersJoin);
    }
}
