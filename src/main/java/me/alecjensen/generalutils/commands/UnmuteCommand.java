package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

@CommandAlias("unmute")
public class UnmuteCommand extends BaseCommand
{
    private final GeneralUtils generalUtils;

    public UnmuteCommand(GeneralUtils generalUtils)
    {
        this.generalUtils = generalUtils;
    }

    @Default
    @CommandPermission("generalutils.unmute")
    @CommandCompletion("@players")
    public void onUnmuteCommand(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You must specify someone to unmute!");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null)
        {
            sender.sendMessage(ChatColor.RED + "Not a valid player!");
            return;
        }
        sender.sendMessage(ChatColor.RED + "Player unmuted!");

        targetPlayer.sendMessage(ChatColor.RED + "You have been unmuted!");

        NamespacedKey key = new NamespacedKey(generalUtils, "muted");

        targetPlayer.getPersistentDataContainer().set(key, PersistentDataType.STRING, "false");
    }
}
