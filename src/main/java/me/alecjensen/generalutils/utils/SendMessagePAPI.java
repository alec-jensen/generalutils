package me.alecjensen.generalutils.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getPluginManager;

public class SendMessagePAPI {
    public static void sendMessagePAPI(Player player, String message) {
        if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(player, message)));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
