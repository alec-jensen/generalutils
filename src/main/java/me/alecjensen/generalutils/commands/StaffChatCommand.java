package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import me.alecjensen.generalutils.GeneralUtils;
import me.alecjensen.generalutils.utils.SendMessagePAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getOnlinePlayers;

@CommandAlias("staffchat|sc")
public class StaffChatCommand extends BaseCommand
{
    @Dependency
    private GeneralUtils generalUtils;

    @Default
    @CommandPermission("generalutils.staffchat")
    public void onStaffChatCommand(Player sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You can't send an empty message!");
            return;
        }

        for (Player loopPlayer : getOnlinePlayers())
        {
            if (loopPlayer.hasPermission("generalutils.staffchat"))
            {
                SendMessagePAPI.sendMessagePAPI(loopPlayer,
                        ChatColor.translateAlternateColorCodes('&',
                                generalUtils.config.getString("messages.staffchat-prefix")).replace("<sender>",
                                sender.getDisplayName()) + String.join(" ", args));
            }
        }
    }
}
