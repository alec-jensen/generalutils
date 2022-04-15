package me.kidneybean.generalutils;

import me.kidneybean.generalutils.commands.*;
import me.kidneybean.generalutils.files.Config;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GeneralUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginDescriptionFile pdf = getDescription();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Config.setup();
        Config.getConfig().options().copyDefaults(true);
        Config.saveConfig();
        Objects.requireNonNull(getServer().getPluginCommand("generalutils")).setExecutor(new GeneralUtilsCommand(this));
        Objects.requireNonNull(getServer().getPluginCommand("generalutils")).setTabCompleter(new InfoTabCompleter());
        Objects.requireNonNull(getServer().getPluginCommand("bring")).setExecutor(new BringCommand(this));
        Objects.requireNonNull(getServer().getPluginCommand("to")).setExecutor(new ToCommand(this));
        BackCommand backCommand = new BackCommand();
        Objects.requireNonNull(getServer().getPluginCommand("back")).setExecutor(backCommand);
        getServer().getPluginManager().registerEvents(backCommand, this);
        Objects.requireNonNull(getServer().getPluginCommand("kickall")).setExecutor(new KickallCommand());

        getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by kidney bean\nVersion: " + pdf.getVersion() + "\nConfig version: " + Config.getConfig().getString("config-version"));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GREEN + "Goodbye!");
        Config.saveConfig();
    }
}
