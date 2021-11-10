package com.hiuni.milkwars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class Flag implements Listener {

    // TODO: Instead of using the UUIDs, use the Base64 encoding of these skins
    public static final UUID[] HEADS = {
            UUID.fromString("279c8a78-609d-49a3-a328-647b7485132e"),
            UUID.fromString("e13a5e6a-587f-4f5e-81b0-709bb75ee2db")
//            UUID.fromString("f159b274-c22e-4340-b7c1-52abde147713"), // COW
//            UUID.fromString("dfaad551-4e7e-45a1-a6f7-c6fc5ec823ac") // SHEEP
    };

    // TODO: save/load flagId from file
    private UUID flagId = null;
    private Location flagLocation;
    private Location poleLocation;

    // The UUID of the player that's carrying the flag
    private UUID wearer = null;

    private int clanId;

    Flag(int clanId) {
        this.clanId = clanId;
    }

    /*
    Get the UUID of the player carrying the flag.
    Returns null if the flag is not being carried.
     */
    public UUID getWearer() {
        return wearer;
    }

    /*
    Brings the flag back to the flag pole location.
     */
    public void returnToPole() {
        wearer = null;
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
        wearer = null;
        setFlagLocation(poleLocation);
    }

    public Location getFlagPoleLocation() {
        // Gets the location of the flag pole (the home base location)
        return poleLocation;
    }

    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity ent: event.getEntities()) {
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

    @EventHandler
    public void onPlayerInteractEntity(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!entity.getUniqueId().equals(flagId)) {
            return;
        }

        // Cancel the event, since if it's a flag we don't want to be able to take things off it / put things on
        event.setCancelled(true);

        // Some player has right-clicked the flag entity. The behaviour of this depends on what clan they're a part of
        for (Clan clan: MilkWars.clans) {
            if (clan.hasMember(player)) {
                if (clan.getClanId() == clanId) {
                    // They're a part of the same clan as the flag.

                    // Are they carrying a flag?
                    for (Clan otherClan: MilkWars.clans) {
                        if (otherClan.getClanId() == clan.getClanId()) {
                            continue;
                        }

                        if (otherClan.getFlag().getWearer() == player.getUniqueId()) {
                            // A player has clicked on their flag while carrying the enemies flag.
                            // If the flag is at its pole location, that means there's been a capture!
                            if (flagLocation == poleLocation) {
                                // CAAAAPPPPPTUUURRREEEE!!!!!

                                // TODO: broadcast or something?
                                player.sendMessage(
                                        ChatColor.GOLD + "Congratulations on capturing the enemy treasure!"
                                );
                                clan.addCapture();

                                // The flag they're carrying needs to be returned to the enemy base
                                otherClan.getFlag().returnToPole();

                                return;
                            }
                        }
                    }

                    // If we've got here, either the player isn't carrying an enemy flag
                    // Or they are and the flag isn't at its pole.
                    // In either case, clicking the flag should return it to its pole.
                    if (flagLocation != poleLocation) {
                        returnToPole();
                        // TODO: broadcast or something?
                        player.sendMessage(
                                ChatColor.GREEN + "Returned the treasure back to the guild hall!"
                        );
                        return;
                    }
                } else {
                    // They're a part of the opposite clan! They're now carrying the flag
                    wearer = player.getUniqueId();
                    teleportToWearer();

                    // TODO: broadcast or something?
                    player.sendMessage(
                            ChatColor.GREEN + "You've retrieved the enemy treasure! " +
                                    "Return it to your guild hall for a capture!"
                    );
                }
            }
        }

        // If we've got to here, the player isn't a part of any clan. We'll just ignore the event then
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (wearer == null) {
            return;
        }

        if (event.getPlayer().getUniqueId().equals(wearer)) {
            teleportToWearer();
        }
    }

    /*
    Gets the name of the flag entity, since this depends on the clan the flag's in.
     */
    private String getEntityName() {
        return String.format(ChatColor.GOLD + "%d_clan_flag", clanId);
    }

    /*
    Teleport the flag to the wearer (the player carrying the flag)
     */
    private void teleportToWearer() {
        Player player = Bukkit.getPlayer(wearer);

        if (player == null) {
            // I have no idea how this could happen but might as well check
            return;
        }

        Location location = player.getLocation();

        // Put the flag two blocks above the player
        location.setY(location.getY() + 1.5);

        setFlagLocation(location);
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
//        stand.setCustomNameVisible(true);

        stand.setGravity(false);
        stand.setInvisible(true);
        stand.setInvulnerable(true);

        flagId = stand.getUniqueId();
    }
}
