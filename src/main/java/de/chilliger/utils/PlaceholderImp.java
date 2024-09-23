package de.chilliger.utils;

import de.chilliger.Combidlog;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderImp extends PlaceholderExpansion {

    private final Combidlog plugin;

    public PlaceholderImp() {
        this.plugin = Combidlog.getInstance();
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return plugin.getDescription().getName();
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("bandplayers")) {
            return Bukkit.getBannedPlayers().size() + "";
            // return Bukkit.getBanList(BanList.Type.PROFILE).getBanEntries().size() + "";
        }
        if (params.equalsIgnoreCase("purge")) {
            return Combidlog.getGameManager().isPurge() + "";
        }
        return null;
    }
}