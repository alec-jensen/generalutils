package me.kidneybean.generalutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            for (Player loopPlayer : getOnlinePlayers()) {
                if (loopPlayer.hasPermission("generalutils.staffchat")) {
                    loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.staffchat-prefix")).replace("<sender>", ((Player) sender).getDisplayName())) + String.join(" ", args));
                }
            }
        } else {
            Bukkit.getLogger().warning("This command can only be executed by a player!");
        }
        return true;
    }
}
