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
    private String rawString[];

    private static List<Sign> signs;

    public Sign(Location location, String[] lines) {
        this.location = location;
        this.rawString = lines;

        Sign.signs.add(this);
    }

}
