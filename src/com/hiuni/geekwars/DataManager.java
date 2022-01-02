package com.hiuni.geekwars;

public class DataManager {

    private static boolean hasChanged = false;

    public static void registerChanges() {
        DataManager.hasChanged = true;
    }

    public static boolean save() {
        // Saves the clan data to file.

        if (!DataManager.hasChanged) {
//            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Geek-Wars] Clan data" +
//                    " has not changed, no reason to save to file.");
            return true;
        }

//        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Geek-Wars]" +
//                " Saving data to file.");

        FileManager.setup(GeekWars.getInstance(), "ClanData.yml");
        GeekWars.clans[0].save(FileManager.getConfig(), "cows");
        GeekWars.clans[1].save(FileManager.getConfig(), "sheep");
        FileManager.saveConfig();

        FileManager.setup(GeekWars.getInstance(), "SignData.yml");
        Sign.saveAll(FileManager.getConfig());
        FileManager.saveConfig();

        return true;
    }

    public static boolean load() {
        // Loads the data from file.
        // The return value from this doesn't really mean much yet lol.

        FileManager.setup(GeekWars.getInstance(), "ClanData.yml");
        GeekWars.clans[0].load(FileManager.getConfig(), "cows");
        GeekWars.clans[1].load(FileManager.getConfig(), "sheep");

        FileManager.setup(GeekWars.getInstance(), "SignData.yml");
        Sign.loadAll(FileManager.getConfig());

        DataManager.hasChanged = false;
        return true;
    }

}
