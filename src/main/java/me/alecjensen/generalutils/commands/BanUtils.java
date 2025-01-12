package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.alecjensen.generalutils.GeneralUtils;
import me.alecjensen.generalutils.utils.SendMessagePAPI;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.Arrays;

import static org.bukkit.Bukkit.getOnlinePlayers;

@CommandAlias("ban")
public class BanUtils extends BaseCommand implements Listener
{
    private static BanUtils instance;

    @Dependency
    private GeneralUtils generalUtils;

    public BanUtils()
    {
        instance = instance == null ? this : instance;
    }

    public static BanUtils getInstance()
    {
        return instance;
    }

    @Default
    @CommandPermission("generalutils.ban")
    @CommandCompletion("@players")
    public void onCommand(CommandSender sender, String[] args)
    {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You must specify a player!");
            return;
        }
        String reason;
        if (args.length == 1)
        {
            if (config.getBoolean("ban-utils.custom-ban.require-reason"))
            {
                sender.sendMessage(ChatColor.RED + "You must provide a reason!");
                return;
            } else
            {
                reason = config.getString("ban-utils.custom-ban.default-reason");
            }
        } else
        {
            reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        }

        Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null)
        {
            target.kickPlayer(reason);
        }

        sender.sendMessage(ChatColor.GREEN + "Successfully banned " + args[0] + " for " + reason + "!");
    }

    @EventHandler
    public void banUtils(PlayerKickEvent event)
    {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
        if (event.getPlayer().isBanned())
        {
            if (config.getBoolean("ban-utils.ban-lightning-effect"))
            {
                event.getPlayer().getWorld().strikeLightningEffect(event.getPlayer().getLocation());
                for (Player loopPlayer : getOnlinePlayers())
                {
                    loopPlayer.playSound(loopPlayer.getLocation(), "entity.lightning_bolt.thunder", 1.0f, 1.0f);
                }
            }
            if (config.getBoolean("ban-utils.announce-bans"))
            {
                for (Player loopPlayer : getOnlinePlayers())
                {
                    SendMessagePAPI.sendMessagePAPI(loopPlayer, ChatColor.translateAlternateColorCodes('&',
                            config.getString("messages.ban-message").replace("<banned>",
                                    event.getPlayer().getDisplayName()).replace("<reason>", event.getReason())));
                }
            }
        }
    }
}
