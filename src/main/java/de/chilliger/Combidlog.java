package de.chilliger;

import de.chilliger.leaveprotection.LeaveProtectionConfig;
import de.chilliger.leaveprotection.LeaveProtectionListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@AllArgsConstructor
public final class Combidlog extends JavaPlugin {

    @Getter
    private static LeaveProtectionConfig leaveProtectionConfig;
    @Getter
    private static Combidlog instance;

    @Override
    public void onEnable() {
        // register events
        Bukkit.getPluginManager().registerEvents(new LeaveProtectionListener(), this);

        // register variables
        instance = this;
        leaveProtectionConfig = new LeaveProtectionConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
