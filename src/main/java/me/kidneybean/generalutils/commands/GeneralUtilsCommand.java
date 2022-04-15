package me.kidneybean.generalutils.commands;

import me.kidneybean.generalutils.GeneralUtils;
import me.kidneybean.generalutils.files.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

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
                player.sendMessage(ChatColor.GREEN + "GeneralUtils plugin by kidney bean\nVersion: " + pdf.getVersion() + "\nConfig version: " + Config.getConfig().getString("config-version"));
            } else {
                generalUtils.getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by kidney bean\nVersion: " + pdf.getVersion() + "\nConfig version: " + Config.getConfig().getString("config-version"));
            }
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            Config.reloadConfig();
            if (sender instanceof Player player) {
                if (player.hasPermission("generalutils.reload")) {
                    player.sendMessage(ChatColor.GREEN + "Successfully reloaded config!");
                } else {
                    player.sendMessage(Config.permissionMessage());
                }
            } else if (sender instanceof ConsoleCommandSender) {
                generalUtils.getLogger().info(ChatColor.GREEN + "Successfully reloaded config!");
            }
            return true;
        }
        return true;
    }
}
