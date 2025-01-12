package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("suicide")
public class SuicideCommand extends BaseCommand
{
    @Default
    @CommandPermission("generalutils.suicide")
    public void onSuicideCommand(CommandSender sender)
    {
        if (sender instanceof Player player)
        {
            player.setHealth(0);
        } else
        {
            Bukkit.getLogger().warning("This command can only be executed by a player!");
        }
    }
}
