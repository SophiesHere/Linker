package me.ohfoxzyy.linker.Commands;

import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class LinkerCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private DiscordBotManager discordBotManager;

    public LinkerCommand(JavaPlugin plugin, DiscordBotManager discordBotManager) {
        this.plugin = plugin;
        this.discordBotManager = discordBotManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();

            if (discordBotManager != null) {
                discordBotManager.shutdown();
            }

            String token = plugin.getConfig().getString("config.token");
            discordBotManager = new DiscordBotManager(plugin, token);

            sender.sendMessage("Configuration reloaded and Discord bot restarted.");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
            sender.sendMessage("Version: " + plugin.getDescription().getVersion());
            sender.sendMessage("Authors: " + plugin.getDescription().getAuthors());
            return true;
        }

        return false;
    }
}
