package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.commands.subcommands.ClanCommand;
import com.hiuni.milkwars.commands.subcommands.SettingsCommand;
import com.hiuni.milkwars.commands.subcommands.TreasureCommand;
import dev.jorel.commandapi.CommandAPICommand;

public class CommandManager {
    public static void register() {
        new CommandAPICommand("milkwars")
                .withAliases("mw")
                .withSubcommand(new ClanCommand().getCommand())
                .withSubcommand(new SettingsCommand().getCommand())
                .withSubcommand(new TreasureCommand().getCommand())
                .register();
    }
}
