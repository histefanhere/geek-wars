package com.hiuni.milkwars.events;

import com.hiuni.milkwars.Clan;
import com.hiuni.milkwars.MilkWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ClanKillCounterEvent implements Listener {
    @EventHandler
    public static void onPlayerDeath (PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (player.getKiller() == null) { return; }

        Clan friendlyClan = null;
        Clan enemyClan = null;
        for (Clan clan : MilkWars.clans) {
            if (clan.hasMember(player)) {
                friendlyClan = clan;
            }
            if (clan.hasMember(killer)) {
                enemyClan = clan;
            }
        }

        // Check if the players are in different clans.
        if (friendlyClan == null || enemyClan == null || friendlyClan.equals(enemyClan)) {
            return;
        }

        event.setDeathMessage(player.getName() + " of the " + friendlyClan.getName() +
                " clan died at the hands of " + killer.getName() + " and the " +
                enemyClan.getName() + " clan.");
        enemyClan.addKill();

    }
}
