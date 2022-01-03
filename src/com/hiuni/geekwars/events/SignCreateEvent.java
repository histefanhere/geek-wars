package com.hiuni.geekwars.events;

import com.hiuni.geekwars.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreateEvent implements Listener {
    @EventHandler
    public static void onSignChangeEvent(SignChangeEvent event) {
        for (String line : event.getLines()) {
            if (line.contains("#GW")) {
                // If the sign contains the string "#GW" then we want it to be formatted.
                new Sign(event.getBlock().getLocation(), event.getLines());
                return;
            }
        }
    }
}
