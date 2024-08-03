package de.chilliger.config;

import de.chilliger.Combidlog;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileConfig {

    private final File file;
    private final YamlConfiguration yamlConfiguration;

    private final Map<String, Object> defaults;

    public FileConfig(String filename) {
        if (!filename.endsWith(".yml")) {
            filename = filename + ".yml";
        }
        this.file = new File(Combidlog.getInstance().getDataFolder(), filename);
        createFiles();

        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        this.defaults = new HashMap<>();
    }

    public FileConfig addDefault(String configPath, Object value) {
        defaults.put(configPath, value);
        return this;
    }

    public void saveDefaults() {
        yamlConfiguration.options().copyDefaults(true);
        yamlConfiguration.addDefaults(defaults);
        save();
    }

    public void set(String configPath, Object object) {
        yamlConfiguration.set(configPath, object);
    }

    public boolean getBoolean(String configPath) {
        return yamlConfiguration.getBoolean(configPath);
    }

    public int getInt(String configPath) {
        return yamlConfiguration.getInt(configPath);
    }

    public double getDouble(String configPath) {
        return yamlConfiguration.getDouble(configPath);
    }

    public String getString(String configPath) {
        return yamlConfiguration.getString(configPath);
    }

    public Location getLocation(String configPath) {
        return yamlConfiguration.getLocation(configPath);
    }

    public ItemStack getItemStack(String configPath) {
        return yamlConfiguration.getItemStack(configPath);
    }

    public List<String> getStringList(String configPath) {
        return yamlConfiguration.getStringList(configPath);
    }

    public List<?> getList(String configPath) {
        return yamlConfiguration.getList(configPath);
    }

    public Object get(String configPath) {
        return yamlConfiguration.get(configPath);
    }

    public boolean contains(String configPath) {
        return yamlConfiguration.contains(configPath);
    }

    public boolean isSet(String configPath) {
        return yamlConfiguration.isSet(configPath);
    }

    public ConfigurationSection getConfigurationSection(String configPath) {
        return yamlConfiguration.getConfigurationSection(configPath);
    }

    public String toString() {
        return yamlConfiguration.saveToString();
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFiles() {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
