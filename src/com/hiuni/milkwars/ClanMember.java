package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClanMember {
    private String name;
    private UUID uuid;
    private boolean signedIn;
    private boolean leader;

    ClanMember (String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.signedIn = true;
        this.leader = false;
    }

    public String getName() {
        // Returns the name of the member.
        return name;
    }

    public UUID getUuid() {
        // Returns the UUID of the member.
        return uuid;
    }

    public boolean isSignedIn() {
        // Returns if the user is signed in or not.
        return signedIn;
    }

    public boolean signIn() {
        // Signs the user in, making them active in the clan.
        if (this.isSignedIn()) {
            return false;
        }
        this.signedIn = true;
        return true;
    }

    public boolean signOut() {
        // Signs the user out, making them inactive in the clan.
        if (!this.isSignedIn()) {
            return false;
        }
        this.signedIn = false;
        return true;
    }

    public boolean isLeader() {
        return leader;
    }

    public boolean promote() {
        // Promotes the member to a leader.
        if (this.isLeader()) {
            return false;
        }
        this.leader = true;
        return true;
    }

    public boolean demote() {
        // Demotes the user from leader to normal member.
        if (!this.isLeader()) {
            return false;
        }
        this.leader = false;
        return true;
    }

    public boolean isPlayer(Player player) {
        Bukkit.getConsoleSender().sendMessage(player.getUniqueId().toString());
        Bukkit.getConsoleSender().sendMessage(this.uuid.toString());
        return player.getUniqueId() == this.uuid;
    }

    public void save (FileConfiguration config, String keyPath) {
        // Saves the member to the config file.
        keyPath = keyPath + "." + getUuid().toString();
        config.set(keyPath + ".name", getName());
        config.set(keyPath + ".signedIn", isSignedIn());
        config.set(keyPath + ".leader", isLeader());
    }

    public static ClanMember load(FileConfiguration config, String keyPath, String uuid) {
        // Loads data from config to the member.

        // Need to put default values here.
        //config.setDefaults();

        keyPath = keyPath + "." + uuid;

        ClanMember member = new ClanMember(
                config.getString(keyPath + ".name"),
                UUID.fromString(uuid)
        );
        member.signedIn = config.getBoolean(keyPath + ".signedIn");
        member.leader = config.getBoolean(keyPath + ".leader");

        return member;
    }

}
