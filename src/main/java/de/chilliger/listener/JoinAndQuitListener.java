package de.chilliger.listener;


import de.chilliger.Combidlog;
import de.chilliger.leaveprotection.LeaveProtection;
import de.chilliger.utils.CombiedLogPermission;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Get the LeaveProtection instance for the player
        LeaveProtection leaveProtection = Combidlog.getLeaveProtectionConfig().getLeaveProtection(player);

        // If no LeaveProtection instance exists for the player, exit the method
        if (leaveProtection != null)givPlayerSuff(player, leaveProtection);



    }

    public void givPlayerSuff(Player player, LeaveProtection leaveProtection) {
        // Load the NPC (Non-Player Character) from the LeaveProtection instance
        NPC npc = leaveProtection.loadNPC();

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


}
