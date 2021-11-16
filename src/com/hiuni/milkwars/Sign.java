package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Sign {

    private Location location;
    private String rawString[];

    private static final List<Sign> existingSigns = new ArrayList<Sign>();

    private static final List<Material> signTypes = Arrays.asList(
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

    private static final HashMap<Character, String> FORMATREPLACEMENTS = new HashMap<>(){{
        put('0', ChatColor.BLACK.toString());
        put('1', ChatColor.DARK_BLUE.toString());
        put('2', ChatColor.DARK_GREEN.toString());
        put('3', ChatColor.DARK_AQUA.toString());
        put('4', ChatColor.DARK_RED.toString());
        put('5', ChatColor.DARK_PURPLE.toString());
        put('6', ChatColor.GOLD.toString());
        put('7', ChatColor.GRAY.toString());
        put('8', ChatColor.DARK_GRAY.toString());
        put('9', ChatColor.BLUE.toString());
        put('a', ChatColor.GREEN.toString());
        put('b', ChatColor.AQUA.toString());
        put('c', ChatColor.RED.toString());
        put('d', ChatColor.LIGHT_PURPLE.toString());
        put('e', ChatColor.YELLOW.toString());
        put('f', ChatColor.WHITE.toString());

        put('k', ChatColor.MAGIC.toString());
        put('l', ChatColor.BOLD.toString());
        put('m', ChatColor.STRIKETHROUGH.toString());
        put('n', ChatColor.UNDERLINE.toString());
        put('o', ChatColor.ITALIC.toString());
        put('r', ChatColor.RESET.toString());
    }};

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
        if (!signTypes.contains(block.getType())) { // TODO could maybe use (state instanceof Sign) instead.
            // The sign no longer exists.
            Bukkit.getConsoleSender().sendMessage("This block is no longer a sign.");
            existingSigns.remove(this);
            return;
        }

        org.bukkit.block.Sign s = getSign();
        s.setEditable(true);
        for (int i = 0; i < 4; i++) {
            s.setLine(i, formatLine(rawString[i]));
        }

        //s.update();
        // Idk why this needs to be run like this,
        // but I spent a *really* long time trying to get this to work, and for some
        // reason this is the only way I could do it.
        // If you can explain why this works but s.update doesn't, please let me know.
        Bukkit.getScheduler().runTask(MilkWars.getInstance(), (Runnable) s::update);
    }

    private static String formatLine(String text) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {

            // For format codes.
            if (charArray[i] == '&') {
                try {
                    String formatStr = FORMATREPLACEMENTS.get(charArray[i + 1]);
                    if (formatStr == null) {
                        sb.append('&');
                    }
                    sb.append(formatStr);
                    i++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    sb.append('&');
                }
            }

            // For variable codes.
            if (charArray[i] == '#') {
                
            }

            else {
                sb.append(charArray[i]);
            }
        }
        Bukkit.getConsoleSender().sendMessage(sb.toString());
        return sb.toString();
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
