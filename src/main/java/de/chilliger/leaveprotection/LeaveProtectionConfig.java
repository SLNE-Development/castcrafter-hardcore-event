package de.chilliger.leaveprotection;

import de.chilliger.Combidlog;
import de.chilliger.config.FileConfig;
import de.chilliger.utils.OFPlayer;
import lombok.Getter;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class LeaveProtectionConfig {

    private final FileConfig config;

    //TODO: save in config
    public LeaveProtectionConfig() {
        this.config = new FileConfig("leaveProtection.yml");
        Bukkit.getScheduler().runTaskLater(Combidlog.getInstance(), this::loadFromConfig, 20);
    }

    // Static list to store all LeaveProtection instances
    public static final List<LeaveProtection> leaveProtections = new ArrayList<>();

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


    public void saveToConfig() {
        config.set("leaveProtection", null);
        for (LeaveProtection player : leaveProtections) {
            String basePath = "players." + player.getPlayerId();
            config.set(basePath + ".npc", player.getNpc().getId());
            config.set(basePath + ".ofplayer", player.isOfPlayer());

            config.set(basePath + ".playerName", player.getPlayerName());
            config.set(basePath + ".playerHealth", player.getPlayerHealth());
            config.set(basePath + ".playerFallDistance", player.getPlayerFallDistance());
            config.set(basePath + ".playerFireTicks", player.getPlayerFireTicks());
            config.set(basePath + ".playerExp", player.getPlayerExp());
        }
        config.save();
    }

    // Method to load offline players from a config file
    public void loadFromConfig() {
        if (!config.contains("players")) return;

        for (String key : config.getConfigurationSection("players").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            int npcId = config.getInt("players." + key + ".npc");

            String playerName = config.getString("players." + key + ".playerName");
            double playerHealth = config.getDouble("players." + key + ".playerHealth");
            float playerFallDistance = Float.parseFloat(config.getString("players." + key + ".playerFallDistance"));
            int playerFireTicks = config.getInt("players." + key + ".playerFireTicks");
            int playerExp = config.getInt("players." + key + ".playerExp");


            boolean isOfPlayer = config.getBoolean("players." + key + ".ofplayer");

            LeaveProtection leaveProtection = new LeaveProtection(npcId, uuid, isOfPlayer, playerName, playerHealth, playerFallDistance, playerFireTicks, playerExp);
            leaveProtections.add(leaveProtection);
        }
    }
}
