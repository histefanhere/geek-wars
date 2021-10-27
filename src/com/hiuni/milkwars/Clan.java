package com.hiuni.milkwars;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Clan {

    private String name;
    private List<Player> members; // A list of clan members;
    private int kills; // A counter for how many times clan members have killed other clam members.
    private int captures; // A counter for how many times the clan has successfully captured the enemy flag.

    Clan(String name) {
        this.name = name;
        this.members = new ArrayList<Player>();
        this.kills = 0;
        this.captures = 0;
    }

    public boolean addMember(Player player) {
        // Adds a player to the clan if they are not already a part of it.
        if (this.members.contains(player)) {
            return false;
        } else {
            return this.members.add(player);
        }
    }

    public boolean removeMember(Player player) {
        // Removes a player from the clan if they are currently a member.
        return this.members.remove(player);
    }

    public String getName() {
        // Returns the name of the clan.
        return this.name; // Is this safe? would it be possible to accidentally rename the clan with this.
    }

    public int addKill() {
        // Increments the amount of kills the clan has and returns the new count.
        return this.kills++;
    }

    public int addCapture() {
        // Increment the amount of captures the clan has and returns the new count.
        return this.captures++;
    }

    public boolean save() {
        // Saves the clan data to file so that it can preserved when the server restarts.
        return false; // TODO Implement the save method.
    }

    public boolean load() {
        // Loads the clan data from file, uses the clan name as an identifier.
        return false; // TODO Implement the load method.
    }


}
