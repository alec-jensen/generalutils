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
        boolean previousBanCommandState = generalUtils.config.getBoolean("ban-utils.custom-ban.enabled");

        generalUtils.reloadConfig();

        // TODO: Register or unregister the BanUtils command based on the config value

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
