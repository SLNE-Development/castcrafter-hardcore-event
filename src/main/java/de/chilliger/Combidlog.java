package de.chilliger;

import de.chilliger.command.PurgeCommand;
import de.chilliger.leaveprotection.LeaveProtectionConfig;
import de.chilliger.leaveprotection.LeaveProtectionListener;
import de.chilliger.listener.JoinAndQuitListener;
import de.chilliger.listener.PlayerDeathListener;
import de.chilliger.manager.GameManager;
import de.chilliger.utils.PlaceholderImp;
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
    @Getter
    private static GameManager gameManager;

    @Override
    public void onEnable() {

        // register events
        Bukkit.getPluginManager().registerEvents(new LeaveProtectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinAndQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);

        // register variables
        instance = this;
        gameManager = new GameManager();
        leaveProtectionConfig = new LeaveProtectionConfig();
        new PurgeCommand();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) new PlaceholderImp().register();

    }

    @Override
    public void onDisable() {
        gameManager.saveToConfig();
        leaveProtectionConfig.saveToConfig();
        // Plugin shutdown logic
    }

}
