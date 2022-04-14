package me.kidneybean.generalutils.files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Config {
    private static File file;
    private static FileConfiguration configFile;

    // Finds or generates the plugin configuration file.
    public static void setup() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("GeneralUtils");
        if (plugin == null) {
            Bukkit.getLogger().severe(ChatColor.RED + "GeneralUtils was not found!");
            return;
        }

        file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // oof
            }
        }
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return configFile;
    }

    public static void saveConfig() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning(ChatColor.GOLD + "Couldn't save config file.");
        }
    }

    public static void reloadConfig() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public static String permissionMessage() {
        return getConfig().getString("permission-message");
    }
}
