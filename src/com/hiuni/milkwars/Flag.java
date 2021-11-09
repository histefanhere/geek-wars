package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class Flag {

    // TODO: Instead of using the UUIDs, use the Base64 encoding of these skins
    public static final UUID COW_HEAD = UUID.fromString("f159b274-c22e-4340-b7c1-52abde147713");
    public static final UUID SHEEP_HEAD = UUID.fromString("dfaad551-4e7e-45a1-a6f7-c6fc5ec823ac");

    // TODO: save/load flagId from file
    private static UUID flagId = null;

    private UUID flagHead;

    private Location flagLocation;
    private Location poleLocation;

    Flag(UUID flagHead) {
        this.flagHead = flagHead;
    }

    /*
    Brings the flag back to the flag pole location.
     */
    public void returnToPole() {
        setFlagLocation(poleLocation);
    }

    /*
    Gets the location of the flag
     */
    public Location getFlagLocation() {
        return flagLocation;
    }

    /*
    Sets the location of the flag pole (the home base location).
    This method will also bring the flag back to the flag pole.
     */
    public void setFlagPoleLocation(Location location) {
        poleLocation = location;
        setFlagLocation(poleLocation);
    }

    public Location getFlagPoleLocation() {
        // Gets the location of the flag pole (the home base location)
        return poleLocation;
    }

    /*
    Sets the location of the flag. If we're unable to find the flag entity,
    simply create a new one! The old flag will get deleted once it's loaded in again.
    */
    private void setFlagLocation(Location location) {
        flagLocation = location;

        Entity entity = Bukkit.getEntity(flagId);
        if (entity == null) {
            // We failed to find the flag entity, so who knows where it could be
            // in the world. No worries though! We'll just create a new one and
            // let the old one get deleted when it's loaded in again.
            createNewFlag(location);
        }
        else {
            entity.teleport(location);
        }
    }

    /*
    Creates a new flag (armor stand) entity with all the required attributes at a specified location.
     */
    private void createNewFlag(Location location) {
        ArmorStand stand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location, EntityType.ARMOR_STAND);

        // Set the helmet of the armor stand to a specific player head
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(flagHead));
        stack.setItemMeta(meta);
        stand.getEquipment().setHelmet(stack);

        stand.setCustomName(ChatColor.GOLD + "clan_flag");

        // DEBUGGING, probably don't want this visible in the real release
        stand.setCustomNameVisible(true);

        // TODO: Invulnerability, no gravity, etc.

        flagId = stand.getUniqueId();
    }
}
