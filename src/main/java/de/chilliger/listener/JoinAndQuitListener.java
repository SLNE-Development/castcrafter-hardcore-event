package de.chilliger.listener;


import de.chilliger.Combidlog;
import de.chilliger.leaveprotection.LeaveProtection;
import de.chilliger.leaveprotection.LeaveProtectionConfig;
import de.chilliger.utils.CombiedLogPermission;
import de.chilliger.utils.OFPlayer;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.checkerframework.checker.units.qual.C;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

public class JoinAndQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // Get player
        Player player = event.getPlayer();

        //pass if has bypass permission
        if (CombiedLogPermission.BYPASS.hasPerm(player)) return;


        // Leave vehicle
        if (player.getVehicle() != null) {
            player.leaveVehicle();
        }

        // Spawn leaveProtection
        if (Combidlog.getGameManager().isPurge()) {
            LeaveProtection leaveProtection = new LeaveProtection(player);
            leaveProtection.create();
        } else {
            Combidlog.getGameManager().addOfflinePlayer(new OFPlayer(player));
        }

        // Clear Inventory
        player.getInventory().clear();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        OFPlayer ofplayer = Combidlog.getGameManager().getPlayer(event.getPlayer());

        if (ofplayer != null) {
            givPlayerStuff(player, ofplayer);
            return;
        }
        // Get the LeaveProtection instance for the player
        LeaveProtection leaveProtection = Combidlog.getLeaveProtectionConfig().getLeaveProtection(player);

        // If no LeaveProtection instance exists for the player, exit the method
        if (leaveProtection != null) {
            givPlayerSuff(player, leaveProtection);
        }


    }

    public void givPlayerSuff(Player player, LeaveProtection leaveProtection) {
        // Load the NPC (Non-Player Character) from the LeaveProtection instance
        NPC npc = leaveProtection.loadNPC();

        // Get the player's inventory
        PlayerInventory playerInventory = player.getInventory();

        // Get the NPC's inventory and equipment
        Inventory npcInventory = npc.getOrAddTrait(Inventory.class);
        Equipment npcEquipment = npc.getOrAddTrait(Equipment.class);

        // Return all items from the NPC's inventory to the player
        player.getInventory().setHelmet(npcEquipment.get(Equipment.EquipmentSlot.HELMET));
        player.getInventory().setChestplate(npcEquipment.get(Equipment.EquipmentSlot.CHESTPLATE));
        player.getInventory().setLeggings(npcEquipment.get(Equipment.EquipmentSlot.LEGGINGS));
        player.getInventory().setBoots(npcEquipment.get(Equipment.EquipmentSlot.BOOTS));
        player.getInventory().setItemInOffHand(npcEquipment.get(Equipment.EquipmentSlot.HAND));
        player.getInventory().setItemInOffHand(npcEquipment.get(Equipment.EquipmentSlot.OFF_HAND));


        for (int i = 0; i < npcInventory.getContents().length; i++) {
            if (npcInventory.getContents()[i] == null || npcInventory.getContents()[i].getType().isAir()) continue;
            player.getInventory().setItem(i, npcInventory.getContents()[i]);
        }


      /*  Arrays.stream(npcInventory.getContents())
                .filter(itemStack -> itemStack != null && !itemStack.getType().isAir())
                .forEach(playerInventory::addItem);

       */


        // If the NPC is a player
        if (npc.getEntity() instanceof Player npcPlayer) {
            // Set the player's experience, health, fire ticks, and fall distance to the NPC player's values
            player.setTotalExperience(npcPlayer.getTotalExperience());
            player.setHealth(npcPlayer.getHealth());
            player.setFireTicks(npcPlayer.getFireTicks());
            player.setFallDistance(npcPlayer.getFallDistance());
        } else {
            // Set the player's experience, health, fire ticks, and fall distance to the stored values from the LeaveProtection instance
            player.setTotalExperience(leaveProtection.getPlayerExp());
            player.setHealth(leaveProtection.getPlayerHealth());
            player.setFireTicks(leaveProtection.getPlayerFireTicks());
            player.setFallDistance(leaveProtection.getPlayerFallDistance());
        }

        // Set the player's damage cooldown to 0
        player.setNoDamageTicks(0);

        // Teleport the player to the NPC's position or the stored location from the LeaveProtection instance
        player.teleport(npc != null ? npc.getStoredLocation() : leaveProtection.getLocation());

        // Destroy the LeaveProtection instance
        leaveProtection.destroy();

    }

    public void givPlayerStuff(Player player, OFPlayer ofPlayer) {


        ItemStack[] content = ofPlayer.getContent();

        if (content.length > 39 && content[39] != null) player.getInventory().setHelmet(content[39]);
        if (content.length > 38 && content[38] != null) player.getInventory().setChestplate(content[38]);
        if (content.length > 37 && content[37] != null) player.getInventory().setLeggings(content[37]);
        if (content.length > 36 && content[36] != null) player.getInventory().setBoots(content[36]);


        // Set hands
        if (content.length > 0 && content[0] != null) player.getInventory().setItemInMainHand(content[0]);
        if (content.length > 40 && content[40] != null) player.getInventory().setItemInOffHand(content[40]);


        // Set other equipment slots
        IntStream.range(0, 36)
                .filter(i -> content[i] != null && content[i].getType() != Material.AIR)
                .forEach(i -> player.getInventory().setItem(i, content[i]));

        player.setTotalExperience(ofPlayer.getPlayerExp());
        player.setHealth(ofPlayer.getPlayerHealth());
        player.setFireTicks(ofPlayer.getPlayerFireTicks());
        player.setFallDistance(ofPlayer.getPlayerExp());

        // Set the player's damage cooldown to 0
        player.setNoDamageTicks(0);

        // Teleport the player to the NPC's position or the stored location from the LeaveProtection instance

        player.teleport(ofPlayer.getLocation());

        // Destroy the LeaveProtection instance
        Combidlog.getGameManager().removeOfflinePlayer(player.getUniqueId());

    }

}
