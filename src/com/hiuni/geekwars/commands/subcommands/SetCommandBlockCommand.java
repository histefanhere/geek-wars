package com.hiuni.geekwars.commands.subcommands;

import com.hiuni.geekwars.Clan;
import com.hiuni.geekwars.GeekWars;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;

import java.util.Objects;

public class SetCommandBlockCommand {
    public CommandAPICommand getLeadersCommand() {
        return new CommandAPICommand("setcommandblock")
                .withArguments(new MultiLiteralArgument("signin", "signout"))
                .executesPlayer((player, args) -> {

                    // The player is a leader, so we know they're in a clan
                    for (Clan clan: GeekWars.clans) {
                        if (clan.hasLeader(player)) {
                            Location location = player.getLocation();

                            if (!Objects.equals(location.getWorld(), Bukkit.getServer().getWorld("world"))) {
                                CommandAPI.fail("You can only spawn command blocks in the overworld!");
                                return;
                            }

                            // Firstly we want to delete the previous command block, if one existed
                            Location prevLocation;
                            if (((String) args[0]).equals("signin")) {
                                prevLocation = clan.getSignInCommandBlock();
                            }
                            else {
                                prevLocation = clan.getSignOutCommandBlock();
                            }
                            if (prevLocation != null) {
                                prevLocation.getBlock().setType(Material.AIR);
                            }

                            // Now we set the new location
                            if (((String) args[0]).equals("signin")) {
                                clan.setSignInCommandBlock(location);
                            }
                            else {
                                clan.setSignOutCommandBlock(location);
                            }
                            location.getBlock().setType(Material.COMMAND_BLOCK);
                            CommandBlock block = (CommandBlock) location.getBlock().getState();
                            block.setCommand("mw clan members " + (String) args[0] + " @p");
                            block.update();

                            player.sendMessage(ChatColor.GREEN + "Spawned command block!");
                            return;

                        }
                    }
                    CommandAPI.fail("Something's gone wrong!");
                });
    }
}
