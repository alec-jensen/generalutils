package me.kidneybean.generalutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getPluginManager;

public class KickallCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("generalutils.kickall")) {
                for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
                    String PlaceholderFilled;
                    if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        PlaceholderFilled = PlaceholderAPI.setPlaceholders(loopPlayer,
                                Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.kickall-message"));
                    } else {
                        PlaceholderFilled = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.kickall-message");
                    }
                    if (loopPlayer.hasPermission("generalutils.kickall.exempt")) {
                        if (!Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getBoolean("kickall-exempt-enabled")) {
                            loopPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', PlaceholderFilled));
                        } else {
                            return true;
                        }
                    } else {
                        loopPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', PlaceholderFilled));
                    }
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
                String PlaceholderFilled;
                if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    PlaceholderFilled = PlaceholderAPI.setPlaceholders(loopPlayer,
                            Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.kickall-message"));
                } else {
                    PlaceholderFilled = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.kickall-message");
                }
                if (loopPlayer.hasPermission("generalutils.kickall.exempt")) {
                    if (!Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getBoolean("kickall-exempt-enabled")) {
                        loopPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', PlaceholderFilled));
                    } else {
                        return true;
                    }
                } else {
                    loopPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&', PlaceholderFilled));
                }
            }
        }
        return true;
    }
}
