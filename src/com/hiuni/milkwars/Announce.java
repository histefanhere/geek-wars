package com.hiuni.milkwars;

import org.bukkit.ChatColor;

public class Announce { //TODO These should all be static lol.

    // Send to clan.
    public static void sendToClan (Clan clan, String message, ChatColor color) {
        // Sends a message to all signed in members of a clan.
        sendToClan(clan, message, color, true);
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
        sendToLeaders(clan, message, color, true);
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



}
