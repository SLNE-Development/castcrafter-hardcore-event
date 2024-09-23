package de.chilliger.leaveprotection;

import de.chilliger.Combidlog;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LeaveProtectionConfig {


    // Static list to store all LeaveProtection instances
    public static final List<LeaveProtection> leaveProtections = new ArrayList<>();

    public LeaveProtectionConfig() {
        Bukkit.getScheduler().runTaskLater(Combidlog.getInstance(), LeaveProtectionConfig::loadNPCS, 4 * 20);
    }

    public static void loadNPCS() {
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            System.out.println("regester: " + npc.getName());
            System.out.println("regester: " + npc.getId());
            if (npc.getOrAddTrait(Owner.class) == null) continue;
            UUID id = npc.getOrAddTrait(Owner.class).getOwnerId();
            System.out.println("UUID: " + id);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(id);
            System.out.println("regester: " + offlinePlayer.getName());
            LeaveProtection leaveProtection = new LeaveProtection(offlinePlayer, false, npc.getId());

            leaveProtection.create();
        }
    }

    /**
     * Retrieves the LeaveProtection instance associated with the given entity UUID.
     *
     * @param entityId The UUID of the entity
     * @return The LeaveProtection instance, or null if not found
     */
    public LeaveProtection getLeaveProtection(UUID entityId) {
        return leaveProtections.stream().filter(lp -> lp.getNpc().getUniqueId().equals(entityId)).findFirst().orElse(null);
    }

    /**
     * Retrieves the LeaveProtection instance associated with the given player.
     *
     * @param player The player object
     * @return The LeaveProtection instance, or null if not found
     */
    public LeaveProtection getLeaveProtection(Player player) {
        return leaveProtections.stream().filter(lp -> lp.getPlayerId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    /**
     * Adds a LeaveProtection instance to the list.
     *
     * @param leaveProtection The LeaveProtection instance to add
     */
    public void addLeaveProtection(LeaveProtection leaveProtection) {
        leaveProtections.add(leaveProtection);
    }

    /**
     * Removes a LeaveProtection instance from the list.
     *
     * @param leaveProtection The LeaveProtection instance to remove
     */
    public void removeLeaveProtection(LeaveProtection leaveProtection) {
        leaveProtections.remove(leaveProtection);
    }

}
