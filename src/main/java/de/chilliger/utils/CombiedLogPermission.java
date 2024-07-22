package de.chilliger.utils;

import org.bukkit.entity.Player;

public enum CombiedLogPermission {
    BYPASS;


    private final String permission;

    CombiedLogPermission() {
        this.permission = ("combidlog.plugin." + name().replace("_", ".")).toLowerCase();
    }

    CombiedLogPermission(String permission) {
        this.permission = ("combidlog.plugin." + permission).toLowerCase();
    }

    public boolean hasPerm(Player player) {
        return player.hasPermission(permission);
    }


    public String toString() {
        return permission;
    }
}
