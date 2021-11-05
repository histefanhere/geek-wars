package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Clan {

    private String name;
    private String prefix;
    private List<ClanMember> members; // A list of clan members;
    private int kills; // A counter for how many times clan members have killed other clam members.
    private int captures; // A counter for how many times the clan has successfully captured the enemy flag.

    Clan(String name, String prefix) {

        // Remember if you add anything that needs to persist across server restarts then add it to the save/load methods.
        this.name = name;
        this.prefix = prefix;
        this.members = new ArrayList<ClanMember>();
        this.kills = 0;
        this.captures = 0;

    }

//    private static JavaPlugin plugin;
//
//    public static void setPlugin(JavaPlugin plugin) {
//        // Sets the that this is operating in.
//        Clan.plugin = plugin;
//    }
//
//    public static JavaPlugin getPlugin() {
//        return Clan.plugin;
//    }


    public boolean addMember(Player player) {
        // Adds a player to the clan if they are not already a part of it.
        if (this.hasMember(player)) {
            return false;
        } else {
            DataManager.registerChanges(); // Allows the system to know that changes haven't been saved to disk.
            return this.members.add(new ClanMember(player.getName(), player.getUniqueId()));
        }
    }

    public boolean promote(Player player) { // Replaces addLeader method, does basically the same thing. Player must already be a member when used.
        // Makes player a leader of the clan.
        for (ClanMember member : this.members) {
            if (member.isPlayer(player)) {
                DataManager.registerChanges();
                return member.promote();
            }
        }
        return false;
    }

    public boolean demote(Player player) { // Replaced the demoteLeader method, does the same thing.
        // Demoted the member from a leader to a normal member.
        for (ClanMember member : this.members) {
            if (member.isPlayer(player)) {
                DataManager.registerChanges();
                return member.demote();
            }
        }
        return false;
    }

    public boolean removeMember(Player player) {
        // Removes a player from the clan if they are currently a member.
        for (ClanMember member : this.members) {
            if (member.isPlayer(player)) {
                this.members.remove(member);
                DataManager.registerChanges();
                return true;
                // Could probably use "members.removeif(member -> (member.isPlayer(player)))",
                // but this way we don't need to search the whole list if we find one early,
                // since we know there will only be one occurrence (probably a better object to use tbh).
            }
        }
        return false; // If we couldn't find the player to remove.
    }

    public List<ClanMember> getMembers() {
        // Returns a list of members (without leaders).
        Predicate<ClanMember> ifNotLeader = member -> !member.isLeader();
        return members.stream().filter(ifNotLeader).collect(Collectors.toList()); // I'm like 90% sure this will work.
    }

    public List<ClanMember> getLeaders() {
        // Returns a list of clan leaders.
        Predicate<ClanMember> ifLeader = ClanMember::isLeader;
        return members.stream().filter(ifLeader).collect(Collectors.toList()); // I'm like 90% sure this will work.
    }

    public List<ClanMember> getAllMembers() {
        // Returns a list of all the clan members (leaders and normal members).
        return this.members;
    }

    public boolean hasMember(Player player) {
        // Returns true if player is a member (or leader) of this clan.
        for (ClanMember member : this.members) {
            Bukkit.getConsoleSender().sendMessage(player.getUniqueId().toString());
            if (member.isPlayer(player)) {
                return true;
            }
        }
        return false; // If we couldn't find this member in the clan.
    }

    public boolean hasLeader(Player player) {
        // Returns true if the player is a leader of the clan.
        for (ClanMember member : this.members) {
            if (member.isPlayer(player) && member.isLeader()) {
                return true;
            }
        }
        return false; // If we couldn't find this member in the clan.
    }

    public String getName() {
        // Returns the name of the clan.
        return this.name; // Is this safe? would it be possible to accidentally rename the clan with this.
    }

    public String getPrefix() {
        // Returns the prefix of the clan.
        return this.prefix;
    }

    public int addKill() {
        // Increments the amount of kills the clan has and returns the new count.
        DataManager.registerChanges();
        return this.kills++;
    }

    public int getKills() {
        // Returns the amount of times an enemy clan member has been killed by someone in this clan.
        return this.kills;
    }

    public int addCapture() {
        // Increment the amount of captures the clan has and returns the new count.
        DataManager.registerChanges();
        return this.captures++;
    }

    public int getCaptures() {
        // Returns the amount of times this clan has successfully stolen another clans flag.
        return this.captures;
    }

    public void save(FileConfiguration config, String keyPath) {
        // Saves the clan data to file so that it can preserved when the server restarts.
        config.set(keyPath + ".name", getName());
        config.set(keyPath + ".kills", getKills());
        config.set(keyPath + ".captures", getCaptures());
        config.set(keyPath + ".prefix", getPrefix());
        for (ClanMember member : members) {
            member.save(config, keyPath + ".members");
        }
    }

    public void load(FileConfiguration config, String keyPath) {
        // Loads the clan data from config.

        config.addDefault(keyPath + ".name", "ErrorLoadingClanName");
        config.addDefault(keyPath + ".kills", 0);
        config.addDefault(keyPath + ".captures", 0);
        config.addDefault(keyPath + ".prefix", "[FailedToLoad] ");

        this.name = config.getString(keyPath + ".name");
        this.kills = config.getInt(keyPath + ".kills");
        this.captures = config.getInt(keyPath + ".captures");
        this.prefix = config.getString(keyPath + ".prefix");

        try {
            Set<String> uuids = config.getConfigurationSection(keyPath + ".members").getKeys(false);
            for (String key : uuids) {
                // The keys of each member is their uuid as a string.
                // The load method creates a new member from the data found in config.
                ClanMember member = ClanMember.load(config, keyPath + ".members", key);
                members.add(member);
            }
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage("[Milk-Wars] Could not find any member data to load" +
                    "for clan: " + this.name);
        }
    }
}
