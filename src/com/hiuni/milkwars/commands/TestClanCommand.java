package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.commands.subcommands.TestMembersCommand;
import dev.jorel.commandapi.CommandAPICommand;

public class TestClanCommand {

    public static void register() {
        new CommandAPICommand("tclan")
                .withSubcommand(new TestMembersCommand().getCommand())
                .register();
    }
}
