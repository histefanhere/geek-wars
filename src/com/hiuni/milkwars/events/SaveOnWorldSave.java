package com.hiuni.milkwars.events;

import com.hiuni.milkwars.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;


public class SaveOnWorldSave implements Listener {
    @EventHandler
    public static void onWorldSaveEvent (WorldSaveEvent event) {
        DataManager.save();
    }
}
