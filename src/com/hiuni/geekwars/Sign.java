package com.hiuni.geekwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.function.Supplier;


public class Sign {

    private final Location location;
    private final String[] rawString = {"","","",""};

    private static final List<Sign> existingSigns = new ArrayList<Sign>();

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

    private static final HashMap<Character, Supplier<String>> VARIABLEREPLACEMENTS = new HashMap<>(){{
        // I was going to use multiple character keys, but it's so much more efficient using
        // single character keys, not to mention much easier to implement.
        put('0', () -> Integer.toString(GeekWars.clans[0].getKills()));
        put('1', () -> Integer.toString(GeekWars.clans[0].getCaptures()));

        put('5', () -> Integer.toString(GeekWars.clans[1].getKills()));
        put('6', () -> Integer.toString(GeekWars.clans[1].getCaptures()));
    }};

    /**
     * Creates a Sign object that represents an ingame smart sign.
     * <p>
     *     Smart signs are simply signs that can be used to display Geek-War/clan related variables.
     *     for example these signs could display the amount of kills each clan has.
     *     These signs can also be formatted using the standard colour/format codes in spigot.
     *     Although you can use these signs simply to make fancy signs,
     *     this is not recommended as it causes unnecessary strain on the server
     *     (this would be *very* slight, but if we want all signs to make normal signs fancy then
     *     there are better ways of doing this).
     * </p><p>
     *     Smart signs are created by making a normal sign, but somewhere in the sign text
     *     the player must enter #GW, this will tell the server that we want this sign to
     *     be special.
     *     Formatting text in the sign is done with the `&` symbol, followed by the symbol
     *     that represents the colour/format that you want to apply. for example:
     *     `&7&l&nHello World`
     *     Will create the text: "Hello World" in white (&7), bold (&l) and underlined (&n).
     *     Putting variables in the sign is done using a hash, for example if the cows clan
     *     has 3 kills then: `&7Kills: #0`
     *     Will display: "Kills: 3" in white text.
     * </p><p>
     *     All signs are automatically added to an internal list of signs so we can keep
     *     keep track of them easier.
     * </p>
     *
     * @param location  The location of the sign, the easiest way to get this is with block.getLocation().
     * @param lines     The text that has been input to the sign, this is what will be used create the formatted text.
     */
    public Sign(Location location, String[] lines) {
        this.location = location;

        for (int i = 0; i < 4; i++) {
            this.rawString[i] = lines[i].replace("#GW","");
        }

        existingSigns.removeIf(otherSign -> otherSign.location.equals(this.location));

        this.update();
        Sign.existingSigns.add(this);
    }

    /**
     * Updates all the smart signs that we know of.
     * If we try to update a sign and find that it doesn't exist anymore (ie the sign has
     * recently been broken) then we remove the sign from the internal list of signs.
     */
    public static void updateAll(){
        //Bukkit.getConsoleSender().sendMessage("updating all signs");

        // We need to iterate over a copy of the existing signs list as we might modify it
        // And we don't want to be iterating over a changing list.
        ArrayList<Sign> signsCopy = new ArrayList<Sign>(existingSigns);

        for (Sign s : signsCopy) {
            //Bukkit.getConsoleSender().sendMessage(s.toString());
            s.update();
        }
    }

    /**
     * Updates the text on the sign.
     * If we try to update a sign which no longer exists then it is removed from the
     * internal list of signs.
     */
    public void update(){
        Block block = getBlock();
        if (!(block.getState() instanceof org.bukkit.block.Sign)) {
            // The sign no longer exists.
            existingSigns.remove(this);
            return;
        }

        org.bukkit.block.Sign s = getSign();
        for (int i = 0; i < 4; i++) {
            s.setLine(i, formatLine(rawString[i]));
        }
        // s.update();
        // Idk why this needs to be run like this,
        // but I spent a *really* long time trying to get this to work, and for some
        // reason this is the only way I could do it.
        // If you can explain why this works but s.update doesn't, please let me know.
        Bukkit.getScheduler().runTask(GeekWars.getInstance(), (Runnable) s::update);
    }

    /**
     * Formats a string.
     * Goes through a string and replaces the format codes/variable codes with the
     * respective values.
     * @param text  The raw string which needs to be formatted.
     * @return      Returns a coloured/formatted string with variable values in place.
     */
    private static String formatLine(String text) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {

            // For variable codes.
            if (charArray[i] == '#') {
                try {
                    // Try to replace the code with a variable.
                    String varStr = VARIABLEREPLACEMENTS.get(charArray[i + 1]).get();
                    sb.append(varStr);
                    i++;
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    // If there's no variable associated with the code,
                    // or we're at the end of the line, then we shouldn't interpret this as a code.
                    sb.append('#');
                }
            }

            // For format codes.
            else if (charArray[i] == '&') {
                try {
                    // Try to find the string to replace with.
                    String formatStr = FORMATREPLACEMENTS.get(charArray[i + 1]);
                    if (formatStr == null) {
                        // If there's no replacement then this isn't supposed to be a
                        // format code.
                        sb.append('&');
                        continue;
                    }
                    sb.append(formatStr);
                    i++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    // If this is placed at the end of a line then it's not a code.
                    sb.append('&');
                }
            }

            else {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }

    /**
     * Gets the location of the sign.
     * @return  the location of the sign.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Gets the block in the world.
     * @return  The block in the world at the location where the sign should be.
     */
    public Block getBlock() {
        return this.getLocation().getBlock();
    }

    /**
     * Gets the sign data from the world.
     * @return  The block state of the block, cast to a block.Sign object.
     */
    public org.bukkit.block.Sign getSign() {
        return (org.bukkit.block.Sign) getBlock().getState();
    }

    /**
     * Saves all the Signs to file.
     * @param config    The FileConfiguration object to add all the data to.
     */
    public static void saveAll(FileConfiguration config) {
        config.set("Signs", "");
        for (int i = 0; i < existingSigns.size(); i++) {
            existingSigns.get(i).save(config, "Signs." + Integer.toString(i));
        }
    }

    /**
     * Saves this sign to file.
     * @param config    The FileConfiguration object to add the data to.
     * @param keyPath   The keypath to save this sign object to,
     *                  not very important what this is, as long as it is unique.
     */
    private void save(FileConfiguration config, String keyPath) {
        config.set(keyPath + ".location", this.getLocation());
        config.set(keyPath + ".rawString", this.rawString);
    }

    /**
     * Loads all the data from file,
     * Takes all the data stored in file and creates sign objects for each of them.
     * @param config    The FileConfiguration object to pull the data from.
     */
    public static void loadAll(FileConfiguration config) {
        config.addDefault("Signs", new HashSet<String>(){});
        try {
            Set<String> keys = config.getConfigurationSection("Signs").getKeys(false);
            for (String key : keys) {
                new Sign(config.getLocation("Signs." + key + ".location"),
                        config.getStringList("Signs." + key + ".rawString").toArray(new String[2]));
            }
        } catch (NullPointerException e) {
//            Bukkit.getConsoleSender().sendMessage("[Geek-Wars] could not find any sign" +
//                    " data to load.");
        }
    }
}
