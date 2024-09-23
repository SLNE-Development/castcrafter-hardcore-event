package de.chilliger.leaveprotection;

import de.chilliger.Combidlog;
import de.chilliger.utils.Utils;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.IntStream;

@Getter
public class LeaveProtection {

    // NPC
    private NPC npc;

    //Player data
    private final UUID playerId;
    private final String playerName;
    private final double playerHealth;
    private final float playerFallDistance;
    private final int playerFireTicks;
    private final int playerExp;

    //inventory data
    private final ItemStack[] content;

    //location data
    private Location location;


    public LeaveProtection(Player player) {
        this.playerId = player.getUniqueId();
        this.playerName = player.getName();
        this.playerHealth = player.getHealth();
        this.playerFallDistance = player.getFallDistance();
        this.playerFireTicks = player.getFireTicks();
        this.playerExp = player.getTotalExperience();
        this.location = player.getLocation();
        this.content = player.getInventory().getContents();

        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, playerName);

        spawnNPC(npc);
    }


    public LeaveProtection(OfflinePlayer player, boolean spawn, int id) {

        this.playerFallDistance = Utils.getPlayerFallDistance(player);
        this.playerFireTicks = Utils.getPlayerFireTicks(player);
        this.playerExp = Utils.getPlayerExp(player);
        this.playerHealth = Utils.getPlayerHealth(player);

        this.content = Utils.getPlayerInventory(player);

        this.location = player.getLocation();
        this.playerId = player.getUniqueId();
        this.playerName = player.getName();

        if (spawn) {
            this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, playerName);
            spawnNPC(npc);
        } else {
            this.npc = CitizensAPI.getNPCRegistry().getById(id);
        }


    }


    public NPC loadNPC() {
        // Call the loadNPC method with the default value of spawn = true
        return loadNPC(true);
    }

    public NPC loadNPC(boolean spawn) {
        // Get the existing NPC instance
        NPC npc = this.npc;

        // If the NPC instance already exists, return it
        if (npc != null) return npc;

        // If the spawn flag is true, spawn a new NPC and return it
        if (spawn) return spawnNPC();

        // If the spawn flag is false, return null
        return null;
    }

    private @Nullable NPC spawnNPC() {
        // Check if the location and its world are valid
        if (location == null || location.getWorld() == null) {
            // If not, destroy the LeaveProtection instance and return null
            destroy();
            return null;
        }

        // Create a new NPC with the EntityType.PLAYER and the player's name
        if (npc == null) this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, playerName);


        // Equip the NPC with the player's inventory and equipment
        equipNPC(npc);

        // Return the newly created NPC
        return npc;
    }

    private void spawnNPC(NPC npc) {
        //null check
        if (location == null || location.getWorld() == null) {
            destroy();
            System.out.println("null");
            return;
        }

        //equip npc
        equipNPC(npc);

        //spawn npc
        npc.spawn(location);
        System.out.println(playerName + " spawned");

        //set data
        setData();
    }

    public void setData() {
        //set owner
        npc.getOrAddTrait(Owner.class).setOwner(playerId);


        // Check if the NPC entity exists
        if (npc.getEntity() == null) return;

        // If the NPC entity is an instance of Player
        if (npc.getEntity() instanceof Player player) {
            // Set the player's health
            player.setHealth(playerHealth);

            // Set the player's fall distance
            player.setFallDistance(playerFallDistance);

            // Set the player's fire ticks
            player.setFireTicks(playerFireTicks);

            // Set the player's total experience
            player.setTotalExperience(playerExp);
        }
    }

    public void equipNPC(@NotNull NPC npc) {
        // Set skin
        npc.getOrAddTrait(SkinTrait.class).setSkinName(playerName, true);

        // set armor
        if (content.length > 39 && content[39] != null)
            npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, content[39]);
        if (content.length > 38 && content[38] != null)
            npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, content[38]);
        if (content.length > 37 && content[37] != null)
            npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, content[37]);
        if (content.length > 36 && content[36] != null)
            npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, content[36]);


        // Set hands
        if (content.length > 0 && content[0] != null)
            npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, content[0]);
        if (content.length > 40 && content[40] != null)
            npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, content[40]);


        // Set other equipment slots
        IntStream.range(0, 36)
                .filter(i -> content[i] != null && content[i].getType() != Material.AIR)
                .forEach(i -> npc.getOrAddTrait(Inventory.class).setItem(i, content[i]));


        // Set NPC properties
        npc.setProtected(false);

        //setOwner
        npc.getOrAddTrait(Owner.class).setOwner(playerId);

        //setName
        npc.setName(playerName);

        //setNoDamageTicks
    }

    public void create() {
        Combidlog.getLeaveProtectionConfig().addLeaveProtection(this);
    }

    public void destroy() {
        NPC npc = loadNPC(false);
        if (npc != null) {
            npc.destroy();
        }

        Combidlog.getLeaveProtectionConfig().removeLeaveProtection(this);
    }


    public void onKill() {
        // Get the stored location
        Location location = this.location;

        // Load the NPC
        NPC npc = loadNPC();
        if (npc != null) {
            // If the NPC exists, get its stored location
            location = npc.getStoredLocation();
        }

        // Check if the location and its world are valid
        if (location == null || location.getWorld() == null) {
            // If not, destroy the LeaveProtection instance and exit
            destroy();
            return;
        }

        // Drop all items from the NPC's inventory
        for (ItemStack itemStack : npc.getOrAddTrait(Inventory.class).getContents()) {
            if (itemStack != null && !itemStack.getType().isAir()) {
                location.getWorld().dropItemNaturally(location, itemStack);
            }
        }

        // Drop all items from the NPC's equipment
        for (ItemStack itemStack : npc.getOrAddTrait(Equipment.class).getEquipment()) {
            if (itemStack != null && !itemStack.getType().isAir()) {
                location.getWorld().dropItemNaturally(location, itemStack);
            }
        }

        // Spawn experience orbs based on the player's experience
        location.getWorld().spawn(location, ExperienceOrb.class).setExperience((int) (playerExp / 10d * 3));

        // Log a message with the player's name and location
        System.out.println("Player " + playerName + " was killed at " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());

        // Ban the player with a specific reason and date
        Utils.bannPlayer(this.playerId);

        // Destroy the LeaveProtection instance
        destroy();
    }
}
