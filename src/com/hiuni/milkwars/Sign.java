package com.hiuni.milkwars;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sign {

    private Location location;
    private String rawString[];

    private static List<Sign> existingSigns = new ArrayList<Sign>();

    private static List<Material> signTypes = Arrays.asList(
            Material.ACACIA_WALL_SIGN,
            Material.WARPED_WALL_SIGN,
            Material.BIRCH_WALL_SIGN,
            Material.CRIMSON_WALL_SIGN,
            Material.OAK_WALL_SIGN,
            Material.DARK_OAK_WALL_SIGN,
            Material.JUNGLE_WALL_SIGN,
            Material.SPRUCE_WALL_SIGN,

            Material.ACACIA_SIGN,
            Material.WARPED_SIGN,
            Material.BIRCH_SIGN,
            Material.CRIMSON_SIGN,
            Material.OAK_SIGN,
            Material.DARK_OAK_SIGN,
            Material.JUNGLE_SIGN,
            Material.SPRUCE_SIGN
    );

    public Sign(Location location, String[] lines) {
        this.location = location;
        this.rawString = lines;

        existingSigns.removeIf(otherSign -> otherSign.location.equals(this.location));

        this.update();
        Sign.existingSigns.add(this);

        Bukkit.getConsoleSender().sendMessage(existingSigns.toString());
    }

    public void update(){
        Block block = getBlock();
        if (!signTypes.contains(block.getType())) {
            // The sign no longer exists.
            Bukkit.getConsoleSender().sendMessage("This block is no longer a sign.");
            existingSigns.remove(this);
            return;
        }

        org.bukkit.block.Sign s = getSign();
        s.setEditable(true);
        for (int i = 0; i < 4; i++) {
            s.setLine(i, format(rawString[i]));
            Bukkit.getConsoleSender().sendMessage(s.getLine(i));
        }

        //s.update();
        // Idk why this needs to be run like this,
        // but I spent a *really* long time trying to get this to work, and for some
        // reason this is the only way I could do it.
        // If you can explain why this works but s.update doesn't, please let me know.
        Bukkit.getScheduler().runTask(MilkWars.getInstance(), (Runnable) s::update);
    }

    private static String format(String text) {
        return text.replace("#MW", "this is a test");
    }

    public Location getLocation() {
        return this.location;
    }

    public Block getBlock() {
        return this.getLocation().getBlock();
    }

    public org.bukkit.block.Sign getSign() {
        return (org.bukkit.block.Sign) getBlock().getState();
    }
}
