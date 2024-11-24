package me.ohfoxzyy.linker.Managers;

<<<<<<< Updated upstream
=======
import me.ohfoxzyy.linker.Linker;
import me.ohfoxzyy.linker.Listeners.ConsoleListener;
>>>>>>> Stashed changes
import me.ohfoxzyy.linker.Listeners.DiscordListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscordBotManager extends ListenerAdapter {
    private static final Logger LOGGER = Logger.getLogger(DiscordBotManager.class.getName());
    private JDA jda;
    private HashMap<String, UUID> pendingLinks = new HashMap<>();
    private FileConfiguration linkedAccountsConfig;
    private File linkedAccountsFile;
    private final Map<TextChannel, BridgeConfig> bridgeChannels = new HashMap<>();
    private DiscordListener discordListener;

    public DiscordBotManager(JavaPlugin plugin) {
        try {
            String token = plugin.getConfig().getString("config.token");
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            jda = JDABuilder.createDefault(token).build();
            jda.addEventListener(this);
=======
=======
>>>>>>> Stashed changes

            if (token == null || token.isEmpty()) {
                throw new IllegalArgumentException("Discord bot token is not set in the config!");
            }

            jda = JDABuilder.createDefault(token)
                    .enableIntents(EnumSet.of(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT
                    ))
                    .addEventListeners(new DiscordListener(bridgeChannels))
<<<<<<< Updated upstream
=======
                    .addEventListeners(this)
>>>>>>> Stashed changes
                    .build();

            try {
                jda.awaitReady();
                LOGGER.info("Discord bot is fully initialized and ready!");
            } catch (InterruptedException e) {
                LOGGER.severe("Failed to initialize Discord bot: " + e.getMessage());
                Thread.currentThread().interrupt();
                throw new RuntimeException("Discord bot initialization interrupted", e);
            }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

            jda.updateCommands()
                    .addCommands(Commands.slash("link", "Link your Minecraft account")
                            .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "code", "Your 6-digit link code", true))
                    .queue();

            linkedAccountsFile = new File(plugin.getDataFolder(), "linked_accounts.yml");
            if (!linkedAccountsFile.exists()) {
                linkedAccountsFile.getParentFile().mkdirs();
                plugin.saveResource("linked_accounts.yml", false);
            }
            linkedAccountsConfig = YamlConfiguration.loadConfiguration(linkedAccountsFile);
            LOGGER.info(MessageManager.getInstance().getMessage("discord.account-saved", "{file}", linkedAccountsFile.getName()));

            loadBridgeConfigurations(plugin);
<<<<<<< Updated upstream
=======

            new ConsoleListener(this, Linker.getPlugin());
>>>>>>> Stashed changes

        } catch (Exception e) {
            String errorMessage = MessageManager.getInstance().getMessage("discord.loading-error");
            LOGGER.log(Level.SEVERE, errorMessage, e);
        }
    }

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    public void reload(JavaPlugin plugin) {
        loadBridgeConfigurations(plugin);
        discordListener.reload(bridgeChannels);
    }

    public void loadBridgeConfigurations(JavaPlugin plugin) {
<<<<<<< Updated upstream
        // Retrieve the bridge section from the configuration
        ConfigurationSection bridgeSection = plugin.getConfig().getConfigurationSection("bridge");

        if (bridgeSection != null) {
            // Clear any existing bridge channels
            bridgeChannels.clear();

            // Iterate over all keys in the bridge section
            for (String key : bridgeSection.getKeys(false)) {
                // Retrieve the configuration section for each channel
=======
        ConfigurationSection bridgeSection = plugin.getConfig().getConfigurationSection("bridge");

        if (bridgeSection != null) {
            bridgeChannels.clear();

            for (String key : bridgeSection.getKeys(false)) {
>>>>>>> Stashed changes
                ConfigurationSection channelConfig = bridgeSection.getConfigurationSection(key);
                if (channelConfig == null) {
                    LOGGER.warning("Configuration for bridge channel " + key + " is missing or invalid.");
                    continue;
                }

<<<<<<< Updated upstream
                // Retrieve configuration values, using default values where necessary
                String channelId = channelConfig.getString("channel-id");
                String discordRole = channelConfig.getString("discord-role", "");  // Default to empty string if missing
                String minecraftPermission = channelConfig.getString("minecraft-permission", ""); // Default to empty string if missing
=======
                String channelId = channelConfig.getString("channel-id");
                String discordRole = channelConfig.getString("discord-role", "");
                String minecraftPermission = channelConfig.getString("minecraft-permission", "");
>>>>>>> Stashed changes

                String minecraftPrefix = channelConfig.getString("prefix.minecraft", "**{name}**: *{message}*");
                String discordPrefix = channelConfig.getString("prefix.discord", "&b&l{name}:&f {message}");

                TextChannel discordChannel = jda.getTextChannelById(channelId);
                if (discordChannel != null) {
                    bridgeChannels.put(discordChannel, new BridgeConfig(discordRole, minecraftPermission, minecraftPrefix, discordPrefix));
                    LOGGER.info("Bridge for channel " + channelId + " loaded successfully.");
                } else {
                    LOGGER.warning("Channel ID " + channelId + " not found, disabling bridge: " + key);
                }
            }
        } else {
            LOGGER.warning("No bridge configuration found in the config.");
        }
    }

    public Map<TextChannel, BridgeConfig> getBridgeChannels() {
        return bridgeChannels;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("link")) {
            LOGGER.info("Received /link command");

            // Start processing
            event.deferReply().queue(); // Acknowledge to Discord that we are processing

            String code = event.getOption("code").getAsString();
<<<<<<< Updated upstream
            String receivedMessage = MessageManager.getInstance().getMessage("discord.link-received", "{code}", code, "{user}", event.getUser().getId());
            LOGGER.info(receivedMessage);
=======
            LOGGER.info("Processing link code: " + code);
>>>>>>> Stashed changes

            UUID playerUUID = pendingLinks.get(code);
            if (playerUUID != null) {
                String discordId = event.getUser().getId();
                saveLinkedAccount(playerUUID, discordId);

                String successMessage = MessageManager.getInstance().getMessage("discord.link-success");
<<<<<<< Updated upstream
                event.reply(successMessage).queue();
=======
                event.getHook().sendMessage(successMessage).queue();

>>>>>>> Stashed changes
                LOGGER.info("Linked Discord account " + discordId + " with player UUID: " + playerUUID);

                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null) {
                    player.sendMessage(successMessage);
                }

                pendingLinks.remove(code);
            } else {
                String errorMessage = MessageManager.getInstance().getMessage("discord.link-failure");
<<<<<<< Updated upstream
                event.reply(errorMessage).queue();
                LOGGER.warning(MessageManager.getInstance().getMessage("discord.link-invalid"));
=======
                event.getHook().sendMessage(errorMessage).queue();

                LOGGER.warning("Invalid link code: " + code);
>>>>>>> Stashed changes
            }
        }
    }

    public String generateLinkCode(UUID playerUUID) {
        if (isPlayerLinked(playerUUID)) {
            return MessageManager.getInstance().getMessage("discord.already-linked");
        }

        String code = String.valueOf((int) (Math.random() * 900000) + 100000);
        pendingLinks.put(code, playerUUID);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        String logMessage = MessageManager.getInstance().getMessage("discord.code-generated", "{code}", code, "{uuid}", playerUUID.toString());
        LOGGER.info(logMessage);
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        return code;
    }


    public boolean isPlayerLinked(UUID playerUUID) {
        return linkedAccountsConfig.contains(playerUUID.toString());
    }

    private void saveLinkedAccount(UUID playerUUID, String discordId) {
        linkedAccountsConfig.set(playerUUID.toString(), discordId);
        try {
            linkedAccountsConfig.save(linkedAccountsFile);
            String savedMessage = MessageManager.getInstance().getMessage("discord.account-saved", "{discordId}", discordId, "{uuid}", playerUUID.toString());
            LOGGER.info(savedMessage);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving linked account for player UUID: " + playerUUID, e);
        }
    }

    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
            LOGGER.info(MessageManager.getInstance().getMessage("discord.bot-shutdown"));
        }
        pendingLinks.clear();
        bridgeChannels.clear();
    }
}