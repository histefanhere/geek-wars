package com.hiuni.milkwars;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Clan {

    private String name;
    private List<Player> members; // A list of clan members;
    private List<Player> leaders; // The leaders of the clan, should not have double ups with members.
    private int kills; // A counter for how many times clan members have killed other clam members.
    private int captures; // A counter for how many times the clan has successfully captured the enemy flag.

    Clan(String name) {
        this.name = name;
        this.members = new ArrayList<Player>();
        this.leaders = new ArrayList<Player>();
        this.kills = 0;
        this.captures = 0;
    }

    public boolean addMember(Player player) {
        // Adds a player to the clan if they are not already a part of it.
        if (this.hasMember(player)) {
            return false;
        } else {
            return this.members.add(player);
        }
    }

    public boolean addLeader(Player player) {
        // Makes player a leader of the clan.
        if (this.leaders.contains(player)) {
            return false;
        } else if (this.members.contains(player)) {
            this.members.remove(player); // If they're already a member then remove them from the member list.
        }
        return this.leaders.add(player); // and add them to the leaders list.
    }

    public boolean removeMember(Player player) {
        // Removes a player from the clan if they are currently a member.
        if (this.members.contains(player)) {
            return this.members.remove((player));
        } else if (this.leaders.contains(player)) {
            return this.leaders.remove(player);
            // Should a leader be able to be removed, or should they have to be demoted to member first?
        }
        return false; // If we couldn't find the player to remove.
    }

    public boolean demoteLeader(Player player) {
        // Demote a player from leader to member.
        if (!this.leaders.contains(player)) {
            return false;
        }
        this.leaders.remove(player);
        this.members.add(player);
        return true;
    }

    public List<Player> getMembers() {
        // Returns a list of members (without leaders).
        return members;
    }

    public List<Player> getLeaders() {
        // Returns a list of clan leaders.
        return leaders;
    }

    public List<Player> getAllMembers() {
        // Returns a list of all the clan members (leaders and normal members).
        List<Player> allMembers = new ArrayList<Player>();
        allMembers.addAll(this.getMembers());
        allMembers.addAll(this.getLeaders());
        return allMembers;
    }

    public boolean hasMember(Player player) {
        // Returns true if player is a member (or leader) of this clan.
        return (this.members.contains(player) || this.leaders.contains(player));
    }

    public boolean hasLeader(Player player) {
        // Returns true if the player is a leader of the clan.
        return this.leaders.contains(player);
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
        return false; // TODO Implement the save method.
    }

    public boolean load() {
        // Loads the clan data from file, uses the clan name as an identifier.
        return false; // TODO Implement the load method.
    }


}
