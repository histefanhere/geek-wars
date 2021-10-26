package com.unitato.milkwars.commands;

import com.unitato.milkwars.MilkWars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class JoinTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("jointeam")) {
            try {
                if (MilkWars.inTeam(MilkWars.Teams.SHEEP, player) || MilkWars.inTeam(MilkWars.Teams.COW, player)) {
                    // If the player is already in a team.
                    player.sendMessage("§4You are already in a team.");
                    return true;
                }
                Boolean success = false;
                if (args[0].equalsIgnoreCase("cow")) {
                    // Add player to team cow.
                    success = MilkWars.addToTeam(MilkWars.Teams.COW, player);
                } else if (args[0].equalsIgnoreCase("sheep")) {
                    // Add player to team sheep.
                    success = MilkWars.addToTeam(MilkWars.Teams.SHEEP, player);
                } else {
                    // player didn't enter a valid team.
                    player.sendMessage("§4Sorry, " + args[0] + " is not one of the teams,\n" +
                            "use Cow or Sheep instead.");
                    return true;
                }
                if (success) {
                    player.sendMessage("§2Successfully joined team, welcome aboard comrade!");
                } else{
                    player.sendMessage("§4Something went wrong, please let an admin know.");
                }
            }
            catch (Exception e) { // Should probably be more specific.

            }

        }

        return true;
    }
}