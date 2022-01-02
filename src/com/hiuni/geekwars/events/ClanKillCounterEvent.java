package com.hiuni.geekwars.events;

import com.hiuni.geekwars.Announce;
import com.hiuni.geekwars.Clan;
import com.hiuni.geekwars.ClanMember;
import com.hiuni.geekwars.GeekWars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.NoSuchElementException;


public class ClanKillCounterEvent implements Listener {

    static long announcementCooldown = System.currentTimeMillis();

    @EventHandler
    public static void onPlayerDeath (PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (player.getKiller() == null) { return; }

        Clan friendlyClan = null;
        Clan enemyClan = null;
        ClanMember member = null;
        ClanMember killerMember = null;

        for (Clan clan : GeekWars.clans) {
            try {
                member = clan.getMember(player);
                friendlyClan = clan;
            }
            catch (NoSuchElementException e) {}

            try {
                killerMember = clan.getMember(killer);
                enemyClan = clan;
            }
            catch (NoSuchElementException e) {}
        }

        // Check if the players are in different clans.
        // And that they are both signed in.
        if (
                ( friendlyClan == null || enemyClan == null || friendlyClan.equals(enemyClan) ) ||
                ( !(member.isSignedIn() && killerMember.isSignedIn())) ) {
            return;
        }

//        event.setDeathMessage(Announce.formatPlayerName(player, friendlyClan) + " of the " +
//                friendlyClan.getName() + " clan died at the hands of " +
//                Announce.formatPlayerName(killer, enemyClan) + " and the " +
//                enemyClan.getName() + " clan.");

        enemyClan.addKill();

        long now = System.currentTimeMillis();
        if (now - announcementCooldown > 30 * 60 * 1000) {
            // Only send an announcement if there's been no kills in the last 30 minutes.
            Announce.sendToClan(friendlyClan,
                    "A member of the " + friendlyClan.getName() +
                            " has been killed.\nThis means war!",
                    ChatColor.YELLOW);
            Announce.sendToClan(enemyClan,
                    "Rally the troops; Your clan needs help!",
                    ChatColor.YELLOW);
        }
        announcementCooldown = now;

    }
}
