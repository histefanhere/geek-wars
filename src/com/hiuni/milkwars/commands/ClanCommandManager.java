package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.commands.subcommands.MembersCommand;
import dev.jorel.commandapi.CommandAPICommand;

public class ClanCommandManager {
    public static void register() {
        new CommandAPICommand("clan")
                .withSubcommand(new MembersCommand().getCommand())
                .register();
    }
}
