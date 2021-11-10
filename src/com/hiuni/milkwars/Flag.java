package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class Flag implements Listener {

    // TODO: Instead of using the UUIDs, use the Base64 encoding of these skins
    public static final UUID[] HEADS = {
            UUID.fromString("f159b274-c22e-4340-b7c1-52abde147713"),
            UUID.fromString("dfaad551-4e7e-45a1-a6f7-c6fc5ec823ac")
    };

    // TODO: save/load flagId from file
    private static UUID flagId = null;
    private Location flagLocation;
    private Location poleLocation;

    private int clanId;

    Flag(int clanId) {
        this.clanId = clanId;
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

    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity ent: event.getEntities()) {
//            Bukkit.getServer().getConsoleSender().sendMessage(ent.getUniqueId().toString());
            String customName = ent.getCustomName();
            if (customName != null && customName.equals(getEntityName())) {
                // We've found a flag from this clan!
                // If it's not the latest one, we can safely get rid of it
                if (!ent.getUniqueId().equals(flagId)) {
                    ent.remove();
                }
            }
        }
    }

    /*
    Gets the name of the flag entity, since this depends on the clan the flag's in.
     */
    private String getEntityName() {
        return String.format(ChatColor.GOLD + "%d_clan_flag", clanId);
    }

    /*
    Sets the location of the flag. If we're unable to find the flag entity,
    simply create a new one! The old flag will get deleted once it's loaded in again.
     */
    private void setFlagLocation(Location location) {
        flagLocation = location;

        if (flagId == null) {
            // The flag entity doesn't even exist yet! Create one now
            createNewFlag(location);
        }
        else {
            Entity entity = Bukkit.getEntity(flagId);
            if (entity == null) {
                // We failed to find the flag entity, so who knows where it could be
                // in the world. No worries though! We'll just create a new one and
                // let the old one get deleted when it's loaded in again.
                createNewFlag(location);
            } else {
                // We've found the entity somewhere, just teleport it to it's location
                entity.teleport(location);
            }
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
        meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(HEADS[clanId]));
        stack.setItemMeta(meta);
        stand.getEquipment().setHelmet(stack);

        stand.setCustomName(getEntityName());

        // DEBUGGING, probably don't want this visible in the real release
        stand.setCustomNameVisible(true);

        // TODO: Invulnerability, no gravity, etc.

        flagId = stand.getUniqueId();
    }
}
