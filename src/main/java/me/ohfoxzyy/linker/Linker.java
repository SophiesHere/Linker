package me.ohfoxzyy.linker;

import me.ohfoxzyy.linker.Commands.LinkerCommand;
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import me.ohfoxzyy.linker.Commands.LinkCommand;
import me.ohfoxzyy.linker.Managers.MessageManager;
import me.ohfoxzyy.linker.Tabcompletor.LinkerTab;
import me.ohfoxzyy.linker.Managers.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Linker extends JavaPlugin {
    private DiscordBotManager discordBotManager;
    private LinkCommand linkCommand;

    private static Linker instance;

    @Override
    public void onEnable() {
        int pluginId = 23775;
        Metrics metrics = new Metrics(this, pluginId);

        instance = this;
        getLogger().warning("If this is your first time enabling the plugin, getting an error is totally normal!");
        getLogger().info("Saving...");
        this.saveDefaultConfig();
        getLogger().info("Saved!");

        getLogger().info("Enabling Managers");
        MessageManager.getInstance().loadMessages(this);
        discordBotManager = new DiscordBotManager(this);
        getLogger().info("Enabled Managers");

        getLogger().info("Enabling Commands");
        linkCommand = new LinkCommand(discordBotManager);
        LinkerCommand linkerCommand = new LinkerCommand(this, discordBotManager, linkCommand);
        getCommand("link").setExecutor(linkCommand);
        getCommand("linker").setExecutor(linkerCommand);
        getLogger().info("Enabled Commands");

        getLogger().info("Enabling Tabcompletors");
        getCommand("linker").setTabCompleter(new LinkerTab());
        getLogger().info("Enabled Tabcompletors");

        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("Made by (Discord) @sophieshere");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("Authors: " + getDescription().getAuthors());
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    @Override
    public void onDisable() {
        getLogger().warning("Disabling bot, you might get a error, but you can safely ignore it.");
        if (discordBotManager != null) {
            discordBotManager.shutdown();
        }
        getLogger().info("Disabled bot");
    }

    public static Linker getPlugin() {
        return instance;
    }
}