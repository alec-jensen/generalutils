package me.alecjensen.generalutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

public class MuteCommand implements CommandExecutor, Listener {
    private final GeneralUtils generalUtils;

    public MuteCommand(GeneralUtils generalUtils) {
        this.generalUtils = generalUtils;
    }

    FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must specify someone to mute!");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Not a valid player!");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Player muted!");

        if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
            targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(targetPlayer,
                            config.getString("messages.clearchat-message").replace("<sender>",
                                    "ServerConsole").replace("<player>", targetPlayer.getDisplayName()))));
        } else {
            targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            config.getString("messages.clearchat-message").replace("<sender>",
                                    "ServerConsole").replace("<player>", targetPlayer.getDisplayName())));
        }


        NamespacedKey key = new NamespacedKey(generalUtils, "muted");

        targetPlayer.getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");

        return true;
    }

    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        NamespacedKey key = new NamespacedKey(generalUtils, "muted");
        PersistentDataContainer container = event.getPlayer().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            String foundValue = container.get(key, PersistentDataType.STRING);
            if (Objects.equals(foundValue, "true")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
            }
        }
    }
}
