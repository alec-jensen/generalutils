package me.alecjensen.generalutils.commands;

import me.alecjensen.generalutils.utils.SendMessagePAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPluginManager;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
        Player player = (Player) sender;
        for (int i=0; i < 255; i++) {
            for (Player loopPlayer : getOnlinePlayers()) {
                loopPlayer.sendMessage("           ");
            }
        }
        for (Player loopPlayer : getOnlinePlayers()) {
            SendMessagePAPI.sendMessagePAPI(loopPlayer, ChatColor.translateAlternateColorCodes('&',
                    config.getString("messages.clearchat-message").replace("<sender>",
                            player.getDisplayName()).replace("<player>", loopPlayer.getDisplayName())));
        }
        return true;
    }
}
