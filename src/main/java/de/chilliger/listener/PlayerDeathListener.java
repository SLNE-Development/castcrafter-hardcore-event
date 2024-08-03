package de.chilliger.listener;

import de.chilliger.utils.CombiedLogPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (CombiedLogPermission.BYPASS.hasPerm(player)) return;
        player.ban("Du bist gestorben!", new Date(1000), "Du bist gestorben!");
    }
}
