package com.hiuni.milkwars;

import com.hiuni.milkwars.commands.JoinTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MilkWars extends JavaPlugin {

    // Lists to store which player is in which team.
    private static List<Player> cows = new ArrayList<Player>();
    private static List<Player> sheep = new ArrayList<Player>();
    public enum Teams { COW, SHEEP }

    public static boolean addToTeam (Teams team, Player player) {
        // Adds the player to team cow or sheep.
        if (team == Teams.COW) {
            return cows.add(player);
        } else if (team == Teams.SHEEP) {
            return sheep.add(player);
        } else {
            return false;
        }
    }
    public static boolean inTeam(Teams team, Player player) {
        // Checks if the player is in the provided team.
        if (team == Teams.COW) {
            return cows.contains(player);
        } else if (team == Teams.SHEEP) {
            return sheep.contains(player);
        } else {
            return false;
        }
    }

    @Override
    public void onEnable() {
        getCommand("jointeam").setExecutor(new JoinTeam());

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Milk-Wars] Plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Milk-Wars] Plugin has been disabled");
    }

}
