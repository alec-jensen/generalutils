package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.alecjensen.generalutils.GeneralUtils;
import me.alecjensen.generalutils.utils.SendMessagePAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@CommandAlias("mute")
public class MuteCommand extends BaseCommand implements Listener
{
    private final GeneralUtils generalUtils;
    FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();

    public MuteCommand(GeneralUtils generalUtils)
    {
        this.generalUtils = generalUtils;
    }

    @Default
    @CommandPermission("generalutils.mute")
    @CommandCompletion("@players")
    public void onMuteCommand(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You must specify someone to mute!");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null)
        {
            sender.sendMessage(ChatColor.RED + "Not a valid player!");
            return;
        }
        sender.sendMessage(ChatColor.RED + "Player muted!");

        SendMessagePAPI.sendMessagePAPI(targetPlayer,
                config.getString("messages.mute-message").replace("<sender>",
                        sender.getName()).replace("<player>", targetPlayer.getDisplayName()));

        NamespacedKey key = new NamespacedKey(generalUtils, "muted");

        targetPlayer.getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");
    }

    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event)
    {
        NamespacedKey key = new NamespacedKey(generalUtils, "muted");
        PersistentDataContainer container = event.getPlayer().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING))
        {
            String foundValue = container.get(key, PersistentDataType.STRING);
            if (Objects.equals(foundValue, "true"))
            {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
            }
        }
    }
}
