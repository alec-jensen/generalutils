package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getPluginManager;

@CommandAlias("kickall")
public class KickallCommand extends BaseCommand
{
    @Default
    @CommandPermission("generalutils.kickall")
    public void onKickallCommand(CommandSender sender)
    {
        for (Player loopPlayer : Bukkit.getOnlinePlayers())
        {
            String PlaceholderFilled;
            if (getPluginManager().getPlugin("PlaceholderAPI") != null)
            {
                PlaceholderFilled = PlaceholderAPI.setPlaceholders(loopPlayer,
                        Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.kickall-message"));
            } else
            {
                PlaceholderFilled = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.kickall-message");
            }
            if (loopPlayer.hasPermission("generalutils.kickall.exempt"))
            {
                if (!Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getBoolean("kickall-exempt-enabled"))
                {
                    loopPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', PlaceholderFilled));
                } else
                {
                    return;
                }
            } else
            {
                loopPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', PlaceholderFilled));
            }
        }

        sender.sendMessage(ChatColor.RED + "All players have been kicked!");
    }
}
