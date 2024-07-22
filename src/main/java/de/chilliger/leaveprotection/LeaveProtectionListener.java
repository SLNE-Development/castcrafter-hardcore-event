package de.chilliger.leaveprotection;

import de.chilliger.Combidlog;
import de.chilliger.utils.CombiedLogPermission;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import java.util.Arrays;
import java.util.Date;

public class LeaveProtectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
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
        LeaveProtection leaveProtection = new LeaveProtection(player);
        leaveProtection.create();

        // Clear Inventory
        player.getInventory().clear();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        // Get the player from the event
        Player player = event.getPlayer();

        // Get the LeaveProtection instance for the player
        LeaveProtection leaveProtection = Combidlog.getLeaveProtectionConfig().getLeaveProtection(player);

        // If no LeaveProtection instance exists for the player, exit the method
        if (leaveProtection == null) return;

        // Load the NPC (Non-Player Character) from the LeaveProtection instance
        NPC npc = leaveProtection.loadNPC();

        // Get the player's inventory
        PlayerInventory playerInventory = player.getInventory();

        // Get the NPC's inventory and equipment
        Inventory npcInventory = npc.getOrAddTrait(Inventory.class);
        Equipment npcEquipment = npc.getOrAddTrait(Equipment.class);

        // Return all items from the NPC's inventory to the player
        Arrays.stream(npcInventory.getContents())
                .filter(itemStack -> itemStack != null && !itemStack.getType().isAir())
                .forEach(playerInventory::addItem);

        // Return all items from the NPC's equipment to the player
        Arrays.stream(npcEquipment.getEquipment())
                .filter(itemStack -> itemStack != null && !itemStack.getType().isAir())
                .forEach(playerInventory::addItem);

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

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (CombiedLogPermission.BYPASS.hasPerm(player)) return;
        player.ban("Du bist gestorben!", new Date(-1), "Du bist gestorben!");
    }

    @EventHandler(ignoreCancelled = true)
    public void onNPCDeath(NPCDeathEvent event) {

        //get leave protection
        LeaveProtection leaveProtection = Combidlog.getLeaveProtectionConfig().getLeaveProtection(event.getNPC().getUniqueId());

        //null check
        if (leaveProtection == null) return;

        //set drop xp
        event.setDroppedExp(0);

        // Drop items
        leaveProtection.onKill();

    }
}
