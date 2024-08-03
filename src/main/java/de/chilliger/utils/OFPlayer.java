package de.chilliger.utils;


import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class OFPlayer {
    @Getter
    private final UUID uuid;
    @Getter
    private final double playerHealth;
    @Getter
    private final float playerFallDistance;
    @Getter
    private final int playerFireTicks;
    @Getter
    private final int playerExp;

    //inventory data
    @Getter
    private final ItemStack[] content;

    //location data
    @Getter
    private Location location;


    public OFPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.playerHealth = player.getHealth();
        this.playerFallDistance = player.getFallDistance();
        this.playerFireTicks = player.getFireTicks();
        this.playerExp = player.getTotalExperience();
        this.content = player.getInventory().getContents();
        this.location = player.getLocation();
    }

    public OFPlayer(UUID uuid, double playerHealth, float playerFallDistance, int playerFireTicks, int playerExp, ItemStack[] content, Location location) {
        this.uuid = uuid;
        this.playerHealth = playerHealth;
        this.playerFallDistance = playerFallDistance;
        this.playerFireTicks = playerFireTicks;
        this.playerExp = playerExp;
        this.content = content;
        this.location = location;
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }
}
