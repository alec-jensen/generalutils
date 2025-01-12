package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

@CommandAlias("generalutils|gutils|gu")
public class GeneralUtilsCommand extends BaseCommand
{
    private final GeneralUtils generalUtils;

    public GeneralUtilsCommand(GeneralUtils generalUtils)
    {
        this.generalUtils = generalUtils;
    }

    @Default
    @Subcommand("info")
    public void onInfoCommand(CommandSender sender)
    {
        PluginDescriptionFile pdf = this.generalUtils.getDescription();
        if (sender instanceof Player player)
        {
            player.sendMessage(ChatColor.GREEN + "GeneralUtils plugin by Alec Jensen\nVersion: " + pdf.getVersion() + "\nConfig version: " + Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("config-version"));
        } else
        {
            generalUtils.getLogger().info(ChatColor.GREEN + "\nGeneralUtils plugin by Alec Jensen\nVersion: " + pdf.getVersion() + "\nConfig version: " + Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("config-version"));
        }
    }

    @Subcommand("reload")
    @CommandPermission("generalutils.reload")
    public void onReloadCommand(CommandSender sender)
    {
        generalUtils.reloadConfig();

        // code to enable/disable custom ban command (NOT WORKING)
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
        if (sender instanceof Player player)
        {
            if (player.hasPermission("generalutils.reload"))
            {
                player.sendMessage(ChatColor.GREEN + "Successfully reloaded config!");
            }
        } else if (sender instanceof ConsoleCommandSender)
        {
            generalUtils.getLogger().info(ChatColor.GREEN + "Successfully reloaded config!");
        }
    }
}
