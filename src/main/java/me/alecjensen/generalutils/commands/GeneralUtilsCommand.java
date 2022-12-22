package me.alecjensen.generalutils.commands;

import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import static org.bukkit.Bukkit.getServer;

public class GeneralUtilsCommand implements CommandExecutor {
    private final GeneralUtils generalUtils;

    public GeneralUtilsCommand(GeneralUtils generalUtils) {
        this.generalUtils = generalUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            PluginDescriptionFile pdf = this.generalUtils.getDescription();
            if (sender instanceof Player player) {
                player.sendMessage(ChatColor.GREEN + "GeneralUtils plugin by Alec Jensen\nVersion: " + pdf.getVersion() + "\nConfig version: " + Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("config-version"));
            } else {
                generalUtils.getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by Alec Jensen\nVersion: " + pdf.getVersion() + "\nConfig version: " + Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("config-version"));
            }
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
            Bukkit.getPluginManager().getPlugin("GeneralUtils").reloadConfig();
            // if (config.getBoolean("ban-utils.custom-ban.enabled")) {
            //     try {
            //         DynamicCommands dynamicCommands = new DynamicCommands();
            //         dynamicCommands.registerCommand(generalUtils, generalUtils.banUtils, "ban");
            //         DynamicCommands.syncCommands();
            //     } catch (Exception e) {
            //         e.printStackTrace();
            //     }
            // } else {
            //     PluginCommand cmd = getServer().getPluginCommand("ban");
            //     DynamicCommands.unRegisterBukkitCommand(cmd);
            //     try {
            //         DynamicCommands.syncCommands();
            //     } catch (Exception e) {
            //         e.printStackTrace();
            //     }
            // }
            if (sender instanceof Player player) {
                if (player.hasPermission("generalutils.reload")) {
                    player.sendMessage(ChatColor.GREEN + "Successfully reloaded config!");
                }
            } else if (sender instanceof ConsoleCommandSender) {
                generalUtils.getLogger().info(ChatColor.GREEN + "Successfully reloaded config!");
            }
            return true;
        }
        return true;
    }
}
