package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.Clan;
import com.hiuni.milkwars.MilkWars;
import com.hiuni.milkwars.commands.subcommands.ClanCommand;
import com.hiuni.milkwars.commands.subcommands.SettingsCommand;
import com.hiuni.milkwars.commands.subcommands.TreasureCommand;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import org.bukkit.entity.Player;

public class CommandManager {
    public static void register() {
        new CommandAPICommand("milkwars")
                .withAliases("mw")
                .withPermission(CommandPermission.OP)
                .withSubcommand(new ClanCommand().getCommand())
                .withSubcommand(new SettingsCommand().getCommand())
                .register();

        new CommandAPICommand("clan")
                .withAliases("cl")
                .withRequirement((sender -> {
                    Player player = (Player) sender;

                    // Check if player is in either clan, and if they're a leader
                    for (Clan clan: MilkWars.clans) {
                        if (clan.hasLeader(player)) {
                            return true;
                        }
                    }
                    return false;
                }))
                .withSubcommand(new TreasureCommand().getLeadersCommand())
                .register();
    }
}
