package de.chilliger.manager;

import de.chilliger.config.FileConfig;
import de.chilliger.leaveprotection.LeaveProtection;
import de.chilliger.leaveprotection.LeaveProtectionConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import java.util.*;

public class GameManager {

    @Getter
    private boolean purge;

    private final FileConfig config;


    public GameManager() {
        this.purge = false;
        this.config = new FileConfig("settings.yml");
        purge = (config.getBoolean("purge") ? purge : false);
    }


    public void startPurge() {
        purge = true;

        if (Arrays.stream(Bukkit.getOfflinePlayers()).toList().isEmpty()) return;

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.isOnline()) continue;
            LeaveProtection leaveProtection = new LeaveProtection(player, true, 0);
            leaveProtection.create();

        }

    }

    public void stopPurge() {
        purge = false;
        for (LeaveProtection leaveProtection : LeaveProtectionConfig.leaveProtections) {
            leaveProtection.destroy();
        }
    }


    // Method to save offline players to a config file
    public void saveToConfig() {
        config.set("purge", purge);
        config.save();
    }


}



