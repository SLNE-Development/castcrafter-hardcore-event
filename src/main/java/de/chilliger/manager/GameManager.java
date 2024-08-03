package de.chilliger.manager;

import de.chilliger.config.FileConfig;
import de.chilliger.leaveprotection.LeaveProtection;
import de.chilliger.leaveprotection.LeaveProtectionConfig;
import de.chilliger.utils.OFPlayer;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.*;

public class GameManager {


    public List<OFPlayer> offlinePlayers;
    //TODO: save in config
    @Getter
    private boolean purge;

    private final FileConfig players;
    private final FileConfig config;


    public GameManager() {
        this.offlinePlayers = new ArrayList<>();
        this.purge = false;
        this.config = new FileConfig("settings.yml");
        this.players = new FileConfig("players.yml");
        purge = (config.getBoolean("purge") ? purge : false);
        loadFromConfig();
    }

    public boolean containsOfflinePlayer(UUID uuid) {
        return offlinePlayers.stream().anyMatch(ofPlayer -> ofPlayer.getUuid().equals(uuid));
    }




    public boolean addOfflinePlayer(OFPlayer player) {
        if (containsOfflinePlayer(player.getUuid())) return false;
        System.out.println("add: " + player.getOfflinePlayer().getName());
        return offlinePlayers.add(player);
    }

    public OFPlayer getPlayer(Player player) {
        return offlinePlayers.stream()
                .filter(ofPlayer -> ofPlayer.getUuid().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    public boolean removeOfflinePlayer(UUID uuid) {
        return offlinePlayers.removeIf(ofPlayer -> ofPlayer.getUuid().equals(uuid));
    }

    public void startPurge() {
        purge = true;
        if (offlinePlayers.isEmpty()) return;
        for (OFPlayer player : offlinePlayers) {
            LeaveProtection leaveProtection = new LeaveProtection(player);
            leaveProtection.create();
            offlinePlayers.remove(player);

        }
    }

    public void stopPurge() {
        purge = false;
        for (LeaveProtection leaveProtection : LeaveProtectionConfig.leaveProtections) {
            if (!leaveProtection.isOfPlayer()) continue;
            offlinePlayers.add(leaveProtection.getOfplayer());
            leaveProtection.destroy();

        }
    }


    // Method to save offline players to a config file
   /* public void saveToConfig() {
        config.set("players", null); // Clear existing entries
        for (OFPlayer player : offlinePlayers) {
            String basePath = "players." + player.getUuid();
            config.set(basePath + ".health", player.getPlayerHealth());
            config.set(basePath + ".fallDistance", player.getPlayerFallDistance());
            config.set(basePath + ".fireTicks", player.getPlayerFireTicks());
            config.set(basePath + ".exp", player.getPlayerExp());
            config.set(basePath + ".location", player.getLocation());
            config.set(basePath + ".inventory", player.getContent());
        }
        config.save();
    }

    // Method to load offline players from a config file
    public void loadFromConfig() {
        if (!config.contains("players")) return;
        for (String key : config.getConfigurationSection("players").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            double health = config.getDouble("players." + key + ".health");
            float fallDistance = (float) config.getDouble("players." + key + ".fallDistance");
            int fireTicks = config.getInt("players." + key + ".fireTicks");
            int exp = config.getInt("players." + key + ".exp");
            Location location = config.getLocation("players." + key + ".location");
            ItemStack[] content = (config.getList("players." + key + ".inventory")).toArray(new ItemStack[0]);

            OFPlayer ofPlayer = new OFPlayer(uuid, health, fallDistance, fireTicks, exp, content, location);
            offlinePlayers.add(ofPlayer);
        }
    }

    */



    // Method to save offline players to a config file
    public void saveToConfig() {
        config.set("purge", purge);
        config.save();

        players.set("players", null); // Clear existing entries
        for (OFPlayer player : offlinePlayers) {
            String basePath = "players." + player.getUuid();
            players.set(basePath + ".health", player.getPlayerHealth());
            players.set(basePath + ".fallDistance", player.getPlayerFallDistance());
            players.set(basePath + ".fireTicks", player.getPlayerFireTicks());
            players.set(basePath + ".exp", player.getPlayerExp());
            players.set(basePath + ".location", player.getLocation());

            // Serialize inventory to a string
            String encodedInventory = encodeInventory(player.getContent());
            players.set(basePath + ".inventory", encodedInventory);
        }
        players.save();
    }

    // Method to load offline players from a config file
    public void loadFromConfig() {
        if (!players.contains("players")) return;
        for (String key : players.getConfigurationSection("players").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            double health = players.getDouble("players." + key + ".health");
            float fallDistance = (float) players.getDouble("players." + key + ".fallDistance");
            int fireTicks = players.getInt("players." + key + ".fireTicks");
            int exp = players.getInt("players." + key + ".exp");
            Location location = players.getLocation("players." + key + ".location");

            // Deserialize inventory from a string
            String encodedInventory = players.getString("players." + key + ".inventory");
            ItemStack[] content = decodeInventory(encodedInventory);

            OFPlayer ofPlayer = new OFPlayer(uuid, health, fallDistance, fireTicks, exp, content, location);
            offlinePlayers.add(ofPlayer);
        }
    }

    public static String encodeInventory(ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeInt(items.length);
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack[] decodeInventory(String base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            int length = dataInput.readInt();
            ItemStack[] items = new ItemStack[length];
            for (int i = 0; i < length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }
            return items;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}



