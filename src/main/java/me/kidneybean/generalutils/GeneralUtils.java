package me.kidneybean.generalutils;

import me.kidneybean.generalutils.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class GeneralUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginDescriptionFile pdf = getDescription();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()){
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        } else {
            reloadConfig();
            String key;
            if (getConfig().contains(key = "back-enabled")) {
                getConfig().set(key, true);
            }
            if (getConfig().contains(key = "kickall-exempt-enabled")) {
                getConfig().set(key, true);
            }
            if (getConfig().contains(key = "messages.permission-message")) {
                getConfig().set(key, "You don't have permission to use this!");
            }
            if (getConfig().contains(key = "messages.kickall-message")) {
                getConfig().set(key, "Kickall command was used!");
            }
            getConfig().set("config-version", null);
            saveConfig();
        }

        Objects.requireNonNull(getServer().getPluginCommand("generalutils")).setExecutor(new GeneralUtilsCommand(this));
        Objects.requireNonNull(getServer().getPluginCommand("generalutils")).setTabCompleter(new InfoTabCompleter());
        Objects.requireNonNull(getServer().getPluginCommand("bring")).setExecutor(new BringCommand(this));
        Objects.requireNonNull(getServer().getPluginCommand("to")).setExecutor(new ToCommand(this));
        BackCommand backCommand = new BackCommand();
        Objects.requireNonNull(getServer().getPluginCommand("back")).setExecutor(backCommand);
        getServer().getPluginManager().registerEvents(backCommand, this);
        Objects.requireNonNull(getServer().getPluginCommand("kickall")).setExecutor(new KickallCommand());

        getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by kidney bean\nVersion: " + pdf.getVersion() + "\nConfig version: " + getConfig().getString("config-version"));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GREEN + "Goodbye!");
    }
}
