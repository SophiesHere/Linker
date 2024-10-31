package me.ohfoxzyy.linker.Commands;

import me.ohfoxzyy.linker.Linker;
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import me.ohfoxzyy.linker.Managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LinkerCommand implements CommandExecutor {
    private final Linker plugin;
    private DiscordBotManager discordBotManager;
    private LinkCommand linkCommand;

    public LinkerCommand(Linker plugin, DiscordBotManager discordBotManager, LinkCommand linkCommand) {
        this.plugin = plugin;
        this.discordBotManager = discordBotManager;
        this.linkCommand = linkCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();

            if (discordBotManager != null) {
                discordBotManager.shutdown();
            }

            discordBotManager = new DiscordBotManager(plugin);
            linkCommand.setDiscordBotManager(discordBotManager);

            MessageManager.getInstance().loadMessages(plugin);

            String configreload = MessageManager.getInstance().getMessage("minecraft.reload", sender.getName());
            sender.sendMessage(configreload);
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(ChatColor.YELLOW + "-=-=-==- INFORMATION -==-=-=-");
            sender.sendMessage(ChatColor.GOLD + "Version: " + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.GOLD + "Authors: " + plugin.getDescription().getAuthors());
            return true;
        }

        return false;
    }
}