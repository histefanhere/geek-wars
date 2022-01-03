package com.hiuni.geekwars.events;

import com.hiuni.geekwars.Clan;
import com.hiuni.geekwars.GeekWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.UUID;

public class PlayerConsumeEvent implements Listener {
    @EventHandler
    public static void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.MILK_BUCKET)) {
            // The player drank milk.
            Player player = event.getPlayer();
            for (Clan c : GeekWars.clans) {
                UUID flagBearer = c.getFlag().getWearer();
                if (flagBearer != null && flagBearer.equals(player.getUniqueId())) {
                    // Player is carrying a flag.
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.YELLOW + "[Geek-wars] You may not drink milk while carrying the flag");
                    return;
                }
            }
        }
    }
}
