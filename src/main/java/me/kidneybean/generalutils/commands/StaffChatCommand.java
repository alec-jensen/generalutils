package me.kidneybean.generalutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPluginManager;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You can't send an empty message!");
            return true;
        }

        if (sender instanceof Player) {
            for (Player loopPlayer : getOnlinePlayers()) {
                if (loopPlayer.hasPermission("generalutils.staffchat")) {
                    if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(),
                                        Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.staffchat-prefix")).replace("<sender>",
                                        ((Player) sender).getDisplayName())) + String.join(" ", args));
                    } else {
                        loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.staffchat-prefix")).replace("<sender>",
                                        ((Player) sender).getDisplayName()) + String.join(" ", args));
                    }
                }
            }
        } else {
            Bukkit.getLogger().warning("This command can only be executed by a player!");
        }
        return true;
    }
}
