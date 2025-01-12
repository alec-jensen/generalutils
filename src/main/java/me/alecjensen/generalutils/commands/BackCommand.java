package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

@CommandAlias("back")
public class BackCommand extends BaseCommand implements Listener
{
    private static BackCommand instance;
    HashMap<UUID, Location> backLocations = new HashMap<>();
    HashMap<UUID, Boolean> teleportsFromSleep = new HashMap<>();
    @Dependency
    private GeneralUtils generalUtils;

    public BackCommand()
    {
        instance = instance == null ? this : instance;
    }

    public static BackCommand getInstance()
    {
        return instance;
    }

    @Default
    @CommandPermission("generalutils.back")
    public void onBackCommand(CommandSender sender)
    {
        if (sender instanceof Player player)
        {
            if (generalUtils.getConfig().getString("back-enabled").equals("true"))
            {
                if (backLocations.containsKey(player.getUniqueId()))
                {
                    Location location = backLocations.get(player.getUniqueId());
                    player.teleport(location);
                } else
                {
                    player.sendMessage("You haven't teleported yet!");
                }
            }
        } else
        {
            generalUtils.getLogger().warning("This command can only be executed by a player!");
        }
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();
        if (teleportsFromSleep.containsKey(player.getUniqueId()))
        {
            if (teleportsFromSleep.get(player.getUniqueId()))
            {
                teleportsFromSleep.remove(player.getUniqueId());
                return;
            }
        }

        Location from = event.getFrom();
        backLocations.put(player.getUniqueId(), from);
    }

    @EventHandler
    public void PlayerBedLeaveEvent(PlayerBedLeaveEvent event)
    {
        teleportsFromSleep.put(event.getPlayer().getUniqueId(), true);
    }
}
