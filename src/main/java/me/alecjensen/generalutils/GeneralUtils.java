package me.alecjensen.generalutils;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.alecjensen.generalutils.commands.*;
import me.alecjensen.generalutils.commands.*;
import me.alecjensen.generalutils.utils.DynamicCommands;
import me.alecjensen.generalutils.utils.InfoTabCompleter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getPluginManager;

public final class GeneralUtils extends JavaPlugin {

    private static GeneralUtils instance;

    public static GeneralUtils getInstance() {
        return GeneralUtils.instance;
    }

    public BanUtils banUtils = new BanUtils();

    public HashMap<UUID, Boolean> frozenDict = new HashMap<>();

    @Override
    public void onEnable() {
        GeneralUtils.instance = this;

        // bStats setup

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

        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();

        // Registering commands and events with the server.

        getServer().getPluginCommand("generalutils").setExecutor(new GeneralUtilsCommand(this));
        getServer().getPluginCommand("generalutils").setTabCompleter(new InfoTabCompleter());
        getServer().getPluginCommand("bring").setExecutor(new BringCommand(this));
        getServer().getPluginCommand("to").setExecutor(new ToCommand(this));
        BackCommand backCommand = new BackCommand();
        getServer().getPluginCommand("back").setExecutor(backCommand);
        getServer().getPluginManager().registerEvents(backCommand, this);
        getServer().getPluginCommand("kickall").setExecutor(new KickallCommand());
        getServer().getPluginCommand("staffchat").setExecutor(new StaffChatCommand());
        getServer().getPluginCommand("announce").setExecutor(new AnnounceCommand());
        getServer().getPluginCommand("ban").setExecutor(banUtils);
        getServer().getPluginManager().registerEvents(banUtils, this);
        getServer().getPluginCommand("suicide").setExecutor(new SuicideCommand());
        getServer().getPluginCommand("clearchat").setExecutor(new ClearChatCommand());
        FreezeCommand freezeCommand = new FreezeCommand(frozenDict);
        getServer().getPluginCommand("freeze").setExecutor(freezeCommand);
        getServer().getPluginManager().registerEvents(freezeCommand, this);
        getServer().getPluginCommand("unfreeze").setExecutor(new UnfreezeCommand(frozenDict));
        MuteCommand muteCommand = new MuteCommand(this);
        getServer().getPluginCommand("mute").setExecutor(muteCommand);
        getServer().getPluginManager().registerEvents(muteCommand, this);
        UnmuteCommand unmuteCommand = new UnmuteCommand(this);
        getServer().getPluginCommand("unmute").setExecutor(unmuteCommand);
        getServer().getPluginManager().registerEvents(unmuteCommand, this);


        // Unregister ban if custom-ban is disabled in the config.

        if (!config.getBoolean("ban-utils.custom-ban.enabled")) {
            PluginCommand cmd = this.getCommand("ban");
            DynamicCommands.unRegisterBukkitCommand(cmd);
        }

        // Checking if PAPI is installed

        if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI is installed! PAPI functionality will work.");
        } else {
            getLogger().warning("PlaceholderAPI not installed! PAPI functionality will not work.");
        }

        getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by Alec Jensen\nVersion: " + pdf.getVersion() + "\nConfig version: " + getConfig().getString("config-version"));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.GREEN + "Goodbye!");
    }
}
