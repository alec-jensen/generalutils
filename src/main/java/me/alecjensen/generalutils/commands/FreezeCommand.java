package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@CommandAlias("freeze")
public class FreezeCommand extends BaseCommand implements Listener
{
    private static FreezeCommand instance;
    private final HashMap<UUID, Boolean> frozenPlayers;
    FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();

    public FreezeCommand(HashMap<UUID, Boolean> frozenPlayers)
    {
        instance = instance == null ? this : instance;
        this.frozenPlayers = frozenPlayers;
    }

    public static FreezeCommand getInstance()
    {
        return instance;
    }

    @Default
    @CommandPermission("generalutils.freeze")
    @CommandCompletion("@players")
    public void onFreezeCommand(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You must specify a player!");
            return;
        }
        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(ChatColor.RED + "That is not a valid player!");
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer.hasPermission("generalutils.freeze.bypass"))
        {
            sender.sendMessage(ChatColor.RED + "You cannot freeze this player!");
            return;
        }
        targetPlayer.setWalkSpeed(0);
        targetPlayer.setFlySpeed(0);
        targetPlayer.setAllowFlight(true); // Prevents player from being kicked if they are in the air.
        frozenPlayers.put(targetPlayer.getUniqueId(), true);
        targetPlayer.sendMessage(ChatColor.RED + "You have been frozen!");
        sender.sendMessage(ChatColor.RED + "You have frozen " + targetPlayer.getName() + "!");
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event)
    {
        if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent event)
    {
        if (config.getBoolean("freeze-command.negate-damage"))
        {
            if (frozenPlayers.get(event.getEntity().getUniqueId()) != null)
            {
                event.setCancelled(true);
                if (event.getDamageSource().getCausingEntity() instanceof Player attacker)
                {
                    attacker.sendMessage(ChatColor.RED + "This player is frozen!");
                }

            }
        }

        if (config.getBoolean("freeze-command.negate-attack"))
        {
            Entity cause = event.getDamageSource().getCausingEntity();

            if (cause == null) return;

            if (frozenPlayers.get(cause.getUniqueId()) != null)
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
    {
        if (config.getBoolean("freeze-command.stop-commands"))
        {
            if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null)
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event)
    {
        if (config.getBoolean("freeze-command.stop-break"))
        {
            if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null)
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event)
    {
        if (config.getBoolean("freeze-command.stop-place"))
        {
            if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null)
            {
                event.setCancelled(true);
            }
        }
    }
}
