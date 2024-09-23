package de.chilliger.listener;

import de.chilliger.utils.CombiedLogPermission;
import de.chilliger.utils.Utils;
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
        Utils.bannPlayer(player.getUniqueId());
    }
}
