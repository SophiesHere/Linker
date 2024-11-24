package me.ohfoxzyy.linker;

import me.ohfoxzyy.linker.Commands.LinkCommand;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import me.ohfoxzyy.linker.Managers.MessageManager;
=======
import me.ohfoxzyy.linker.Commands.LinkerCommand;
import me.ohfoxzyy.linker.Listeners.ChatListener;
=======
import me.ohfoxzyy.linker.Commands.LinkerCommand;
import me.ohfoxzyy.linker.Listeners.ChatListener;
import me.ohfoxzyy.linker.Listeners.ConsoleListener;
>>>>>>> Stashed changes
import me.ohfoxzyy.linker.Listeners.DiscordListener;
import me.ohfoxzyy.linker.Managers.BridgeConfig;
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import me.ohfoxzyy.linker.Managers.MessageManager;
import me.ohfoxzyy.linker.Managers.Metrics;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import me.ohfoxzyy.linker.Tabcompletor.LinkerTab;
import me.ohfoxzyy.linker.Managers.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public final class Linker extends JavaPlugin {
    private DiscordBotManager discordBotManager;
    private LinkCommand linkCommand;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
    private Map<String, BridgeConfig> channels;
    private ChatListener chatListener;
>>>>>>> Stashed changes

    private static Linker instance;
=======
    private Map<String, BridgeConfig> channels;
    private ChatListener chatListener;
    private ConsoleListener consoleListener;

    private static volatile Linker instance;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        LinkerCommand linkerCommand = new LinkerCommand(this, discordBotManager, linkCommand);
        getCommand("link").setExecutor(linkCommand);
        getCommand("linker").setExecutor(linkerCommand);
=======
=======
>>>>>>> Stashed changes
        LinkerCommand linkerCommand = new LinkerCommand(this, discordBotManager, linkCommand, chatListener);
        getCommand("link").setExecutor(linkCommand);
        getCommand("linker").setExecutor(linkerCommand);
        getCommand("linker").setTabCompleter(new LinkerTab());
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        getLogger().info("Enabled Commands");

        getLogger().info("Enabling Listeners");
        chatListener = new ChatListener(discordBotManager, this);
<<<<<<< Updated upstream
        getServer().getPluginManager().registerEvents(chatListener, this);
=======
        consoleListener = new ConsoleListener(discordBotManager, this);
        getServer().getPluginManager().registerEvents(chatListener, this);
        getServer().getPluginManager().registerEvents(consoleListener, this);
>>>>>>> Stashed changes
        getServer().getPluginManager().registerEvents(new DiscordListener(discordBotManager.getBridgeChannels()), this);
        getLogger().info("Enabled Listeners");


        getLogger().info("Checking for new versions...");
        checkForUpdate();

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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======

    private static final String GITHUB_API_URL = "https://api.github.com/repos/SophiesHere/Linker/releases/latest";
    private static final String CURRENT_VERSION = "v1.2.1";
=======

    private static final String GITHUB_API_URL = "https://api.github.com/repos/SophiesHere/Linker/releases/latest";
    private static final String CURRENT_VERSION = "v1.2.2";
>>>>>>> Stashed changes

    public void checkForUpdate() {
        new Thread(() -> {
            try {
                URL url = new URL(GITHUB_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Linker");

                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    String latestVersion = jsonResponse.getString("tag_name");

                    if (isNewVersionAvailable(latestVersion, CURRENT_VERSION)) {
                        getLogger().warning("A new version (" + latestVersion + ") is available!");
                    } else {
                        getLogger().info("You are on the latest version. Nice!");
                    }
                } else {
                    getLogger().warning("Failed to check for updates. Response code: " + connection.getResponseCode());
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean isNewVersionAvailable(String latestVersion, String currentVersion) {
        return latestVersion.compareTo(currentVersion) > 0;
    }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}