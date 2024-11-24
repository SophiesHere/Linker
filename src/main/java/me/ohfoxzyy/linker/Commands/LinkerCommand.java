package me.ohfoxzyy.linker.Commands;

import me.ohfoxzyy.linker.Linker;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import me.ohfoxzyy.linker.Listeners.ChatListener;
>>>>>>> Stashed changes
=======
import me.ohfoxzyy.linker.Listeners.ChatListener;
>>>>>>> Stashed changes
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import me.ohfoxzyy.linker.Managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;

import java.io.File;
>>>>>>> Stashed changes
=======
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.io.File;
>>>>>>> Stashed changes

public class LinkerCommand implements CommandExecutor {
    private final Linker plugin;
    private DiscordBotManager discordBotManager;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private LinkCommand linkCommand;

    public LinkerCommand(Linker plugin, DiscordBotManager discordBotManager, LinkCommand linkCommand) {
=======
    private final LinkCommand linkCommand;

    public LinkerCommand(Linker plugin, DiscordBotManager discordBotManager, LinkCommand linkCommand, ChatListener chatListener) {
>>>>>>> Stashed changes
=======
    private final LinkCommand linkCommand;

    public LinkerCommand(Linker plugin, DiscordBotManager discordBotManager, LinkCommand linkCommand, ChatListener chatListener) {
>>>>>>> Stashed changes
        this.plugin = plugin;
        this.discordBotManager = discordBotManager;
        this.linkCommand = linkCommand;
    }

    @Override
<<<<<<< Updated upstream
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
<<<<<<< Updated upstream
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
=======
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }
>>>>>>> Stashed changes

        return switch (args[0].toLowerCase()) {
            case "reload" -> handleReload(sender);
            case "info" -> handleInfo(sender);
            default -> {
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use '/linker info' or '/linker reload'.");
                yield false;
            }
        };
    }

<<<<<<< Updated upstream
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
=======
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                return handleReload(sender);

            case "info":
                return handleInfo(sender);

            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use '/linker info' or '/linker reload'.");
                return false;
        }
=======
    private boolean handleReload(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading plugin...");

        HandlerList.unregisterAll(plugin);
        plugin.reloadConfig();
        reloadLinkedAccountsConfig();

        if (discordBotManager != null) {
            discordBotManager.shutdown();
        }

        try {
            discordBotManager = new DiscordBotManager(plugin);
            linkCommand.setDiscordBotManager(discordBotManager);

            sender.sendMessage(ChatColor.GREEN + "Discord bot reinitialized successfully.");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Error: Discord bot token is not set. Please check the configuration.");
            plugin.getLogger().severe("Error initializing Discord bot: " + e.getMessage());
            return false;
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Error initializing Discord bot. Please check the logs.");
            plugin.getLogger().severe("Error initializing Discord bot: " + e.getMessage());
            return false;
        }

        MessageManager.getInstance().loadMessages(plugin);

        if (discordBotManager != null) {
            discordBotManager.loadBridgeConfigurations(plugin);
        }

        ChatListener chatListener = new ChatListener(discordBotManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(chatListener, plugin);

        String reloadMessage = MessageManager.getInstance().getMessage("minecraft.reload", sender.getName());
        sender.sendMessage(reloadMessage);
        plugin.getLogger().info("Plugin reloaded successfully.");
        return true;
    }

    private void reloadLinkedAccountsConfig() {
        File linkedAccountsFile = new File(plugin.getDataFolder(), "linked_accounts.yml");
        if (linkedAccountsFile.exists()) {
            FileConfiguration linkedAccountsConfig = YamlConfiguration.loadConfiguration(linkedAccountsFile);
            plugin.getLogger().info("Linked accounts reloaded.");
        }
    }

    private boolean handleInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "-=-=-==- INFORMATION -==-=-=-");
        sender.sendMessage(ChatColor.GOLD + "Version: " + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GOLD + "Authors: " + plugin.getDescription().getAuthors());
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Usage:");
        sender.sendMessage(ChatColor.GOLD + "/linker reload" + ChatColor.WHITE + " - Reload the plugin configuration and bot.");
        sender.sendMessage(ChatColor.GOLD + "/linker info" + ChatColor.WHITE + " - View plugin information.");
>>>>>>> Stashed changes
    }

    private boolean handleReload(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading plugin...");

        HandlerList.unregisterAll(plugin);
        plugin.reloadConfig();
        reloadLinkedAccountsConfig();

        if (discordBotManager != null) {
            discordBotManager.shutdown();
>>>>>>> Stashed changes
        }

        try {
            discordBotManager = new DiscordBotManager(plugin);
            linkCommand.setDiscordBotManager(discordBotManager);

            sender.sendMessage(ChatColor.GREEN + "Discord bot reinitialized successfully.");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Error: Discord bot token is not set. Please check the configuration.");
            plugin.getLogger().severe("Error initializing Discord bot: " + e.getMessage());
            return false;
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Error initializing Discord bot. Please check the logs.");
            plugin.getLogger().severe("Error initializing Discord bot: " + e.getMessage());
            return false;
        }

        MessageManager.getInstance().loadMessages(plugin);

        if (discordBotManager != null) {
            discordBotManager.loadBridgeConfigurations(plugin);
        }

        ChatListener chatListener = new ChatListener(discordBotManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(chatListener, plugin);

        String reloadMessage = MessageManager.getInstance().getMessage("minecraft.reload", sender.getName());
        sender.sendMessage(reloadMessage);
        plugin.getLogger().info("Plugin reloaded successfully.");
        return true;
    }

    private void reloadLinkedAccountsConfig() {
        File linkedAccountsFile = new File(plugin.getDataFolder(), "linked_accounts.yml");
        if (linkedAccountsFile.exists()) {
            FileConfiguration linkedAccountsConfig = YamlConfiguration.loadConfiguration(linkedAccountsFile);
            plugin.getLogger().info("Linked accounts reloaded.");
        }
    }

    private boolean handleInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "-=-=-==- INFORMATION -==-=-=-");
        sender.sendMessage(ChatColor.GOLD + "Version: " + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GOLD + "Authors: " + plugin.getDescription().getAuthors());
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Usage:");
        sender.sendMessage(ChatColor.GOLD + "/linker reload" + ChatColor.WHITE + " - Reload the plugin configuration and bot.");
        sender.sendMessage(ChatColor.GOLD + "/linker info" + ChatColor.WHITE + " - View plugin information.");
    }
}