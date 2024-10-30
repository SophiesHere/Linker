package me.ohfoxzyy.linker;

import me.ohfoxzyy.linker.Commands.LinkerCommand;
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import me.ohfoxzyy.linker.Commands.LinkCommand;
import me.ohfoxzyy.linker.Tabcompletor.LinkerTab;
import org.bukkit.plugin.java.JavaPlugin;

public final class Linker extends JavaPlugin {
    private DiscordBotManager discordBotManager;

    @Override
    public void onEnable() {
        getLogger().info("Saving...");
        this.saveDefaultConfig();
        getLogger().info("Saved!");

        getLogger().info("Enabling Managers");
        String token = getConfig().getString("config.token");
        new DiscordBotManager(this, token);
        getLogger().info("Enabled Managers");

        getLogger().info("Enabling Commands");
        getCommand("link").setExecutor(new LinkCommand(discordBotManager));
        getCommand("linker").setExecutor(new LinkerCommand(this, discordBotManager));
        getLogger().info("Enabled Commands");

        getLogger().info("Enabling Tabcompletors");
        getCommand("linker").setTabCompleter(new LinkerTab());
        getLogger().info("Enabled Tabcompletors");

        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("Made by (Discord) @sophieshere");
        getLogger().info("Version: " + JavaPlugin.getPlugin(Linker.class).getDescription().getVersion());
        getLogger().info("Authors: " + JavaPlugin.getPlugin(Linker.class).getDescription().getAuthors());
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling bot");
        if (discordBotManager != null) {
            discordBotManager.shutdown();
        }
        getLogger().info("Disabled bot");
    }
}
