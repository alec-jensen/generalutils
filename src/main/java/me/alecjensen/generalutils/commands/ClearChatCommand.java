package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.alecjensen.generalutils.utils.SendMessagePAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getOnlinePlayers;

@CommandAlias("clearchat")
public class ClearChatCommand extends BaseCommand
{
    @Default
    @CommandPermission("generalutils.clearchat")
    public void onClearChatCommand(Player player)
    {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
        for (int i = 0; i < 255; i++)
        {
            for (Player loopPlayer : getOnlinePlayers())
            {
                loopPlayer.sendMessage("           ");
            }
        }
        for (Player loopPlayer : getOnlinePlayers())
        {
            SendMessagePAPI.sendMessagePAPI(loopPlayer, ChatColor.translateAlternateColorCodes('&',
                    config.getString("messages.clearchat-message").replace("<sender>",
                            player.getDisplayName()).replace("<player>", loopPlayer.getDisplayName())));
        }
    }
}
