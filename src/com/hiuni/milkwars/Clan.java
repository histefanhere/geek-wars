package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Clan {

    private String name;
    private List<ClanMember> members; // A list of clan members;
    private int kills; // A counter for how many times clan members have killed other clam members.
    private int captures; // A counter for how many times the clan has successfully captured the enemy flag.

    Clan(String name) {
        this.name = name;
        this.members = new ArrayList<ClanMember>();
        this.kills = 0;
        this.captures = 0;

    }

    public boolean addMember(Player player) {
        // Adds a player to the clan if they are not already a part of it.
        if (this.hasMember(player)) {
            return false;
        } else {
            return this.members.add(new ClanMember(player.getName(), player.getUniqueId()));
        }
    }

    public boolean promote(Player player) { // Replaces addLeader method, does basically the same thing. Player must already be a member when used.
        // Makes player a leader of the clan.
        for (ClanMember member : this.members) {
            if (member.isPlayer(player)) {
                return member.promote();
            }
        }
        return false;
    }

    public boolean demote(Player player) { // Replaced the demoteLeader method, does the same thing.
        // Demoted the member from a leader to a normal member.
        for (ClanMember member : this.members) {
            if (member.isPlayer(player)) {
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

    public int addKill() {
        // Increments the amount of kills the clan has and returns the new count.
        return this.kills++;
    }

    public int getKills() {
        // Returns the amount of times an enemy clan member has been killed by someone in this clan.
        return this.kills;
    }

    public int addCapture() {
        // Increment the amount of captures the clan has and returns the new count.
        return this.captures++;
    }

    public int getCaptures() {
        // Returns the amount of times this clan has successfully stolen another clans flag.
        return this.captures;
    }

    public boolean save() {
        // Saves the clan data to file so that it can preserved when the server restarts.
        return false;
    }

    public boolean load() {
        // Loads the clan data from file, uses the clan name as an identifier.
        return false;
    }


}
