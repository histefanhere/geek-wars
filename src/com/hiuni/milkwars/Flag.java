package com.hiuni.milkwars;

import dev.dbassett.skullcreator.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class Flag implements Listener {

    // Always only have TWO heads uncommented
    private static final String[] HEADS = {
//            "5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5", // Base Cow
            "8d103358d8f1bdaef1214bfa77c4da641433186bd4bc44d857c16811476fe", // Golden Cow
//            "651c03b5e659275ed85ca2c8ad988c755ccfba45aff3f6d8d6f2738b0967cccd", // Milk Bucket

//            "f31f9ccc6b3e32ecf13b8a11ac29cd33d18c95fc73db8a66c5d657ccb8be70", // Base Sheep
            "2513d5d588af9c9e98dbf9ea57a7a1598740f21bcce133b9f9aacc67d4faa", // Golden Sheep
//            "1646a62fec2dd97f5e4aa329ae446c6011a8e8e2d817aeeb8fdc11cc54ec2ebe", // Spool Of Wool
//            "3faf4c29f1e7405f4680c5c2b03ef9384f1aecfe2986ad50138c605fefff2f15", // Wool Block
    };

    private UUID flagId;
    private Location flagLocation;
    private Location poleLocation;
    private UUID wearer;

    private int clanId;

    private static final double PLAYER_OFFSET = 1.2;
    private static final double GROUND_OFFSET = -1.1;
    public static final double POLE_OFFSET = -0.8;

    Flag(int clanId) {
        this.clanId = clanId;

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(MilkWars.getInstance(), this::repeatingTask, 0, 1L);
    }

    private void repeatingTask() {
        if (wearer != null) {
            return;
        }
        if (flagLocation == null) {
            return;
        }

        Entity ent = Bukkit.getEntity(flagId);
        if (ent == null) {
            return;
        }

        flagLocation.setYaw(Location.normalizeYaw(flagLocation.getYaw() + 0.8f));
        ent.teleport(flagLocation);
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

        DataManager.registerChanges();
    }

    /*
    Gets the location of the flag
     */
    public Location getFlagLocation() {
        return flagLocation;
    }

    /*
    Sets the location of the flag pole (the home base location).
     */
    public void setFlagPoleLocation(Location location) {
        poleLocation = location;

        DataManager.registerChanges();
    }

    /*
    Gets the location of the flag pole (the home base location).
     */
    public Location getFlagPoleLocation() {
        return poleLocation;
    }

    /*
    We need to listen to the EntitiesLoad event to delete any old flags in the world.
    We do this by checking their custom name, and then their UUID.
     */
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

    /*
    Called when an armor stand is manipulated in the world (right-clicked).
    This is how players interact with the flag.
     */
    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
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

        // If this flag is being worn, it should not be able to be interacted with
        if (wearer != null) {
            return;
        }

        // Some player has right-clicked the flag entity. The behaviour of this depends on what clan they're a part of
        for (Clan clan: MilkWars.clans) {
            if (clan.hasMember(player)) {
                DataManager.registerChanges();

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
                                otherClan.addCapture();

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

    /*
    The flag needs to follow the wearer when they move.
     */
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
    If the wearer of the flag disconnects from the server they need to drop the flag.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().getUniqueId().equals(wearer)) {
            // The wearer has quit the server! We need to drop the flag
            dropFlag();
        }
    }

    /*
    If the wearer of the flag gets kicked from the server they need to drop the flag.
     */
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.getPlayer().getUniqueId().equals(wearer)) {
            // The wearer has kick the server! We need to drop the flag
            dropFlag();
        }
    }

    /*
    If the wearer of the flag dies they need to drop the flag.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getUniqueId().equals(wearer)) {
            // The wearer has died! We need to drop the flag
            dropFlag();
        }
    }

    /*
    Prevents the wearer of the flag from flying away on an elytra
     */
    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity().getUniqueId().equals(wearer)) {
            if (event.isGliding()) {
                // The wearer of the flag has tried to glide! Not today my friend, not today.
                event.setCancelled(true);
            }
        }
    }

    /*
    Prevents the wearer of the flag from teleporting by any means
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // For some reason the flag isn't able to teleport through the portal.
        // If it turns out there's some way to make it do so, that needs to be
        // listened for in an event similar to this and cancelled.

        if (event.getPlayer().getUniqueId().equals(wearer)) {
            event.setCancelled(true);
        }
    }

    /*
    Make the wearer drop the flag.
     */
    private void dropFlag() {
        // TODO: broadcast or something?

        Location location = getFlagLocation();
        location.setY(location.getY() - PLAYER_OFFSET + GROUND_OFFSET);
        wearer = null;
        setFlagLocation(location);
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
        location.setY(location.getY() + PLAYER_OFFSET);

        setFlagLocation(location);
    }

    /*
    Sets the location of the flag. If we're unable to find the flag entity,
    simply create a new one! The old flag will get deleted once it's loaded in again.
     */
    private void setFlagLocation(Location location) {
        flagLocation = location;

        // We've set a null location, it's pretty obvious we DON'T want to teleport the
        // flag entity to "null" in this case...
        if (location == null) {
            return;
        }

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

                // We should just make sure here, while we've got the flag,
                // that the marker status is correctly set
                ((ArmorStand) entity).setMarker(wearer != null);

                entity.teleport(location);
            }
        }

        DataManager.registerChanges();
    }

    /*
    Creates a new flag (armor stand) entity with all the required attributes at a specified location.
     */
    private void createNewFlag(Location location) {
        ArmorStand stand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location, EntityType.ARMOR_STAND);

        // Set the helmet of the armor stand to a specific player head
        String url = "http://textures.minecraft.net/texture/" + HEADS[clanId];
        ItemStack stack = SkullCreator.itemFromUrl(url);
        stand.getEquipment().setHelmet(stack);

        stand.setCustomName(getEntityName());

        stand.setGravity(false);
        stand.setInvisible(true);
        stand.setInvulnerable(true);

        // If there's a wearer, make it a marker
        stand.setMarker(wearer != null);

        flagId = stand.getUniqueId();
    }

    /*
    Saves the flag data to file
     */
    public void save(FileConfiguration config, String keyPath) {
        // turns out, "null" doesn't like being made into a string
        if (flagId != null) {
            config.set(keyPath + ".id", flagId.toString());
        }
        else {
            config.set(keyPath + ".id", null);
        }

        // These locations are stored in the data file as Location objects
        config.set(keyPath + ".location.flag", getFlagLocation());
        config.set(keyPath + ".location.pole", getFlagPoleLocation());

        // turns out, "null" doesn't like being made into a string
        if (wearer != null) {
            config.set(keyPath + ".wearer", wearer.toString());
        }
        else {
            config.set(keyPath + ".wearer", null);
        }
    }

    /*
    Loads the flag data to file
     */
    public void load(FileConfiguration config, String keyPath) {
        // These locations are stored in the data file as location objects, so we can get it directly!
        setFlagLocation(config.getLocation(keyPath + ".location.flag"));
        setFlagPoleLocation(config.getLocation(keyPath + ".location.pole"));

        String configId = config.getString(keyPath + ".id");
        if (configId == null) {
            flagId = null;
        }
        else {
            flagId = UUID.fromString(configId);
        }

        String configWearer = config.getString(keyPath + ".wearer");
        if (configWearer == null) {
            wearer = null;
        }
        else {
            wearer = UUID.fromString(configWearer);
            teleportToWearer();
        }
    }
}
