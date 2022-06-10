package me.kidneybean.generalutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import static org.bukkit.Bukkit.getName;

public class DynamicCommands {

    private static Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        String v = Bukkit.getVersion();
        Bukkit.getLogger().info(v);
        Field objectField = field.equals("commandMap") ? clazz.getDeclaredField(field) : field.equals("knownCommands") ? clazz.getSuperclass().getDeclaredField(field) : clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public static void unRegisterBukkitCommand(PluginCommand cmd) {
        try {
            Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Object map = getPrivateField(commandMap, "knownCommands");
            @SuppressWarnings("unchecked")
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
            knownCommands.remove(cmd.getName());
            for (String alias : cmd.getAliases()) {
                if (knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(getName())) {
                    knownCommands.remove(alias);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerCommand(Plugin plugin, CommandExecutor executor, String... aliases) throws NoSuchFieldException, IllegalAccessException {
        PluginCommand command = getCommand(aliases[0], plugin);

        command.setAliases(Arrays.asList(aliases));
        SimpleCommandMap commandMap = (SimpleCommandMap) getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
        commandMap.register(plugin.getDescription().getName(), command);

        command.setExecutor(executor);
    }

    private static PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }

    private static Method syncCommands;

    static {
        try {
            syncCommands = Bukkit.getServer().getClass().getDeclaredMethod("syncCommands");
        } catch (Exception e) {
            e.printStackTrace();
        }
        syncCommands.setAccessible(true);
    }

    public static void syncCommands() throws InvocationTargetException, IllegalAccessException {
        syncCommands.invoke(Bukkit.getServer());
    }
}