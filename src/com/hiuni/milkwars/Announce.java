package com.hiuni.milkwars;

public class Announce {

    // Send to clan.
    public void sendToClan (Clan clan, String message) {
        // Sends a message to all signed in members of a clan.
        sendToClan(clan, message, true);
    }
    public void sendToClan (Clan clan, String message, boolean activeOnly) {
        // Sends a message to clan members. If activeOnly = true then is sent to all
        // signed in members of the clan, otherwise is sent all members including
        // signed out members.
        for (ClanMember member : clan.getAllMembers()) {
            if (member.isSignedIn() || !(member.isSignedIn() || activeOnly)) {
                member.sendMessage(message);
            }
        }
    }

    // Send to clan leaders.

    // Send to all.

}
