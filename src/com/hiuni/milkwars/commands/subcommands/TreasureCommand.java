package com.hiuni.milkwars.commands.subcommands;

import com.hiuni.milkwars.Clan;
import com.hiuni.milkwars.Flag;
import com.hiuni.milkwars.MilkWars;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class TreasureCommand {
    private final CommandAPICommand treasureSetHome = new CommandAPICommand("sethome")
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
            .executesPlayer((player, args) -> {
                // Because of the requirement, we know the player
                // is a clan leader.

                Location location = player.getLocation();

                // Here we check the various requirements for the flag pole location, which are:
                // - has to be in the overworld
                // - has to be within 5k of spawn
                if (!location.getWorld().equals(Bukkit.getServer().getWorld("world"))) {
                    CommandAPI.fail("You can only set treasure homes in the overworld!");
                    return;
                }
                Location zero = new Location(Bukkit.getServer().getWorld("world"), 0, 0, 0);
                if (location.distance(zero) > 5000) {
                    CommandAPI.fail("Must be within 5k blocks of spawn!");
                    return;
                }

                // Get the location to create the flag pole at
                // Centered in the middle of the block
                location.setX(Location.locToBlock(location.getX()) + 0.5);
                location.setZ(Location.locToBlock(location.getZ()) + 0.5);
                location.setY(Location.locToBlock(location.getY()) + Flag.POLE_OFFSET);

                for (Clan clan: MilkWars.clans) {
                    if (clan.hasLeader(player)) {
                        clan.getFlag().setFlagPoleLocation(location);
                        clan.getFlag().returnToPole();
                        player.sendMessage(
                                ChatColor.GREEN + "Successfully set the treasure's home location!"
                        );
                        return;
                    }
                }

                CommandAPI.fail("Something has gone wrong!");
            });

    private final CommandAPICommand treasureSetLocation = new CommandAPICommand("setlocation")
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
            .executesPlayer(((player, args) -> {
                for (Clan clan: MilkWars.clans) {
                    if (clan.hasMember(player)) {

                        if (clan.getFlag().getFlagLocation() == null) {
                            CommandAPI.fail("Flag has not been created!");
                        }
                        else {
                            Location location = player.getLocation();
                            location.setY(Location.locToBlock(location.getY()) + Flag.POLE_OFFSET);
                            clan.getFlag().setFlagLocation(location);

                            player.sendMessage(
                                    ChatColor.GREEN + "Teleported the flag to you!"
                            );
                        }
                        return;
                    }
                }
            }));

    private final CommandAPICommand treasureActivate = new CommandAPICommand("activate")
            .withPermission(CommandPermission.OP)
            .withArguments(new MultiLiteralArgument("cows", "sheep", "all"))
            .executes((sender, args) -> {
                if (args[0] == "cows" || args[0] == "all") {
                    MilkWars.clans[0].getFlag().setActive(true);
                }
                if (args[0] == "sheep" || args[0] == "all") {
                    MilkWars.clans[1].getFlag().setActive(true);
                }

                switch ((String) args[0]) {
                    case "cows" -> sender.sendMessage(
                            ChatColor.GREEN + "Activated the " + MilkWars.clans[0].getName() + " treasure!"
                    );
                    case "sheep" -> sender.sendMessage(
                            ChatColor.GREEN + "Activated the " + MilkWars.clans[1].getName() + " treasure!"
                    );
                    case "all" -> sender.sendMessage(
                            ChatColor.GREEN + "Activated both the clans treasure!"
                    );
                }
            });

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("treasure")
                .withSubcommand(treasureSetHome)
                .withSubcommand(treasureSetLocation)
                .withSubcommand(treasureActivate);
    }
}
