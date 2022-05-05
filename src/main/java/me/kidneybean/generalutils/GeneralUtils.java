package me.kidneybean.generalutils;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.kidneybean.generalutils.commands.*;
import me.kidneybean.generalutils.utils.InfoTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

public final class GeneralUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        int pluginId = 14930;
        Metrics metrics = new Metrics(this, pluginId);

        PluginDescriptionFile pdf = getDescription();

        // Updating the config.

        try {
            YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Registering commands and events with the server.

        Objects.requireNonNull(getServer().getPluginCommand("generalutils")).setExecutor(new GeneralUtilsCommand(this));
        Objects.requireNonNull(getServer().getPluginCommand("generalutils")).setTabCompleter(new InfoTabCompleter());
        Objects.requireNonNull(getServer().getPluginCommand("bring")).setExecutor(new BringCommand(this));
        Objects.requireNonNull(getServer().getPluginCommand("to")).setExecutor(new ToCommand(this));
        BackCommand backCommand = new BackCommand();
        Objects.requireNonNull(getServer().getPluginCommand("back")).setExecutor(backCommand);
        getServer().getPluginManager().registerEvents(backCommand, this);
        Objects.requireNonNull(getServer().getPluginCommand("kickall")).setExecutor(new KickallCommand());
        Objects.requireNonNull(getServer().getPluginCommand("staffchat")).setExecutor(new StaffChatCommand());

        // Checking if PAPI is installed

        if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI is installed! PAPI functionality will work.");
        } else {
            getLogger().warning("PlaceholderAPI not installed! PAPI functionality will not work.");
        }

        getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by kidney bean\nVersion: " + pdf.getVersion() + "\nConfig version: " + getConfig().getString("config-version"));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GREEN + "Goodbye!");
    }
}
