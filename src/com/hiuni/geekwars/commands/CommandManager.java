package com.hiuni.geekwars.commands;

import com.hiuni.geekwars.Clan;
import com.hiuni.geekwars.GeekWars;
import com.hiuni.geekwars.commands.subcommands.ClanCommand;
import com.hiuni.geekwars.commands.subcommands.SetCommandBlockCommand;
import com.hiuni.geekwars.commands.subcommands.SettingsCommand;
import com.hiuni.geekwars.commands.subcommands.TreasureCommand;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import org.bukkit.entity.Player;

public class CommandManager {
    public static void register() {
        new CommandAPICommand("geekwars")
                .withAliases("gw")
                .withPermission(CommandPermission.OP)
                .withSubcommand(new ClanCommand().getCommand())
                .withSubcommand(new SettingsCommand().getCommand())
                .register();

        new CommandAPICommand("clan")
                .withAliases("cl")
                .withRequirement((sender -> {
                    Player player = (Player) sender;

                    // Check if player is in either clan, and if they're a leader
                    for (Clan clan: GeekWars.clans) {
                        if (clan.hasLeader(player)) {
                            return true;
                        }
                    }
                    return false;
                }))
                .withSubcommand(new TreasureCommand().getLeadersCommand())
                .withSubcommand(new ClanCommand().getLeadersMembersCommand())
                .withSubcommand(new SetCommandBlockCommand().getLeadersCommand())
                .register();
    }
}
