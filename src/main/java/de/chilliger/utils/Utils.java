package de.chilliger.utils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class Utils {

    public static double getPlayerHealth(OfflinePlayer player) {
        return getPlayerData(player, "Health", 20.0);
    }

    public static float getPlayerFallDistance(OfflinePlayer player) {
        return getPlayerData(player, "FallDistance", 0.0f);
    }

    public static int getPlayerFireTicks(OfflinePlayer player) {
        return getPlayerData(player, "Fire", 0);
    }

    public static int getPlayerExp(OfflinePlayer player) {
        return getPlayerData(player, "XpLevel", 0);
    }


    public static ItemStack[] getPlayerInventory(OfflinePlayer player) {
        ItemStack[] inventory = new ItemStack[36];
        inventory[0] = new ItemStack(Material.DIAMOND_SWORD);
        return inventory;
    }


  /* public static ItemStack[] getPlayerInventory(OfflinePlayer player) {
        File playerDataFile = new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata/" + player.getUniqueId() + ".dat");
        ItemStack[] inventory = new ItemStack[36];

        if (playerDataFile.exists()) {
            try {
                NBTFile nbtFile = new NBTFile(playerDataFile);
                if (nbtFile.hasKey("Inventory")) {
                    NBTCompound inventoryCompound = nbtFile.getCompound("Inventory");
                    if (inventoryCompound != null) {
                        int index = 0;
                        for (String key : inventoryCompound.getKeys()) {
                            if (inventoryCompound.hasKey(key)) {
                                NBTCompound itemCompound = inventoryCompound.getCompound(key);
                                if (itemCompound != null) {
                                    ItemStack item = ItemStack.deserialize((Map<String, Object>) itemCompound);
                                    if (item != null) {
                                        if (index < inventory.length) {
                                            inventory[index++] = item;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return inventory;
    }


   */



    public static void bannPlayer(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        System.out.println("BANN");
        System.out.println("PLAYER: " + offlinePlayer.getName());
        System.out.println("BANN");

    }


    private static <T> T getPlayerData(OfflinePlayer player, String key, T defaultValue) {
        File playerDataFile = new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata/" + player.getUniqueId() + ".dat");
        if (playerDataFile.exists()) {
            try {
                NBTFile nbtFile = new NBTFile(playerDataFile);
                if (nbtFile.hasKey(key)) {
                    if (defaultValue instanceof Double) {
                        return (T) Double.valueOf(nbtFile.getDouble(key));
                    } else if (defaultValue instanceof Float) {
                        return (T) Float.valueOf(nbtFile.getFloat(key));
                    } else if (defaultValue instanceof Integer) {
                        return (T) Integer.valueOf(nbtFile.getInteger(key));
                    }
                }
            } catch (NoClassDefFoundError e) {
                // Wenn die NBTFile-Klasse nicht gefunden wird, gib den Standardwert zurück
                System.out.println(e.getMessage());
                return defaultValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

}
