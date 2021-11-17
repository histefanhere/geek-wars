package com.hiuni.milkwars.events;

import com.hiuni.milkwars.Sign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreateEvent implements Listener {
    @EventHandler
    public static void onSignChangeEvent(SignChangeEvent event) {
        for (String line : event.getLines()) {
            if (line.contains("#MW")) {
                // If the sign contains the string "#MW" then we want it to be formatted.
                new Sign(event.getBlock().getLocation(), event.getLines());
                return;
            }
        }
    }
}
