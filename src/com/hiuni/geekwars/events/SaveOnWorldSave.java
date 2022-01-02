package com.hiuni.geekwars.events;

import com.hiuni.geekwars.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;


public class SaveOnWorldSave implements Listener {
    @EventHandler
    public static void onWorldSaveEvent (WorldSaveEvent event) {
        if (event.getWorld().getName().equalsIgnoreCase("world")) {
            DataManager.save();
        }
    }
}
