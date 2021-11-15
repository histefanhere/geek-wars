package com.hiuni.milkwars;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class Sign {

    private Location location;
    private String rawString;

    static List<Sign> signs;

    Sign(Location location) {
        this.location = location;
        //this.rawString = getSign().something lol ;
    }

    private BlockData getSignData() {
        Block block;
        try {
            block = Bukkit.getWorld("world").getBlockAt(this.location);
        }
        catch (NullPointerException e) {
            // The sign no longer exists, we should delete the sign object.
            // TODO: delete the sign object if the block has been removed.
            // Java doesn't have destructors, which is kinda a pain in the ass in this case lol.
            throw new IllegalArgumentException("There is no sign at this location.");
        }

        // need to test if this is actually a sign and then delete it if it's not.
        Bukkit.getConsoleSender().sendMessage(block.getBlockData().getAsString());

        return block.getBlockData();
    }

}
