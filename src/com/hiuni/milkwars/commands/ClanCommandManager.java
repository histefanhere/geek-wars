package com.hiuni.milkwars.commands;

import com.hiuni.milkwars.commands.subcommands.FileCommand;
import com.hiuni.milkwars.commands.subcommands.MembersCommand;
import com.hiuni.milkwars.commands.subcommands.SetChatColourCommand;
import com.hiuni.milkwars.commands.subcommands.TreasureCommand;
import dev.jorel.commandapi.CommandAPICommand;

public class ClanCommandManager {
    public static void register() {
        new CommandAPICommand("clan")
                .withSubcommand(new MembersCommand().getCommand())
                .withSubcommand(new FileCommand().getCommand())
                .withSubcommand(new SetChatColourCommand().getCommand())
                .withSubcommand(new TreasureCommand().getCommand())
                .register();
    }
}
