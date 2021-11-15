package com.hiuni.milkwars;

import dev.jorel.commandapi.arguments.ChatArgument;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Announce {

    // Send to clan.
    public static void sendToClan (Clan clan, String message, ChatColor color) {
        // Sends a message to all signed in members of a clan.
        sendToClan(clan, message, color, false);
    }
    public static void sendToClan (Clan clan, String message, ChatColor color, boolean activeOnly) {
        // Sends a message to clan members. If activeOnly = true then is sent to all
        // signed in members of the clan, otherwise is sent all members including
        // signed out members.
        clanSend(clan, message, color, activeOnly, false);
    }

    // Send to clan leaders.
    public static void sendToLeaders(Clan clan, String message, ChatColor color) {
        // Sends the message to all signed in clan leaders.
        sendToLeaders(clan, message, color, false);
    }
    public static void sendToLeaders(Clan clan, String message, ChatColor color, boolean activeOnly) {
        // Sends the message to all clan leaders, If activeOnly = true then only sends to all
        // Signed in members, otherwise sends to all leaders including signed out ones.
        clanSend(clan, message, color, activeOnly, true);
    }

    public static void memberSend(ClanMember member, String message, ChatColor color) {
        // Sends the message to the member formatted correctly.
        member.sendMessage(color + "[Milk-wars] " + message);
    }

    public static void clanSend(Clan clan, String message, ChatColor color,
                          boolean activeOnly, boolean leaderOnly) {
        for (ClanMember member : clan.getAllMembers()) {
            if (
                    (member.isSignedIn() || !(member.isSignedIn() || activeOnly)) &&
                    (member.isLeader() || !(member.isLeader() || leaderOnly))
            ) { // Could use clan.getLeaders, but I think this might be more efficient, no biggie either way lol.
                memberSend(member, message, color);
            }
        }
    }

    public static void sendToAll(String message, ChatColor color) {
        sendToAll(message, color, false, false);
    }

    public static void sendToAll(String message, ChatColor color, boolean activeOnly) {
        sendToAll(message, color, activeOnly, false);
    }

    public static void sendToAll(String message, ChatColor color, boolean activeOnly, boolean leaderOnly) {
        for (Clan clan : MilkWars.clans) {
            clanSend(clan, message, color, activeOnly, leaderOnly);
        }
    }


    public static String formatPlayerName(Player player, Clan clan) { // TODO clan not required, get from player.
        // TODO make this use the player color, for some reason there's no way to get this colour without
        // using their team, and there's no good way to get their team either >:(.
        // It should also use prefix, but until I can get the colour in a neat way I don't want to
        // use the prefix as it breaks the colours.
        // This really shouldn't be as difficult as it is lol.
        return player.getDisplayName();
    }
}
