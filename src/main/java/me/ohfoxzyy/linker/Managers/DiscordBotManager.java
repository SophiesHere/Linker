package me.ohfoxzyy.linker.Managers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscordBotManager extends ListenerAdapter {
    private static final Logger LOGGER = Logger.getLogger(DiscordBotManager.class.getName());
    private JDA jda;
    private HashMap<String, UUID> pendingLinks = new HashMap<>();
    private FileConfiguration linkedAccountsConfig;
    private File linkedAccountsFile;

    public DiscordBotManager(JavaPlugin plugin) {
        try {
            String token = plugin.getConfig().getString("config.token");
            jda = JDABuilder.createDefault(token).build();
            jda.addEventListener(this);

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
            LOGGER.info("Linked accounts configuration loaded successfully.");

        } catch (Exception e) {
            String errorMessage = MessageManager.getInstance().getMessage("discord.loading-error");
            LOGGER.log(Level.SEVERE, errorMessage, e);
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("link")) {
            String code = event.getOption("code").getAsString();
            String receivedMessage = MessageManager.getInstance().getMessage("discord.link-received", "{code}", code, "{user}", event.getUser().getId());
            LOGGER.info(receivedMessage);

            UUID playerUUID = pendingLinks.get(code);
            if (playerUUID != null) {
                String discordId = event.getUser().getId();
                saveLinkedAccount(playerUUID, discordId);

                String successMessage = MessageManager.getInstance().getMessage("discord.link-success");
                event.reply(successMessage).queue();
                LOGGER.info("Linked Discord account " + discordId + " with player UUID: " + playerUUID);

                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null) {
                    player.sendMessage(successMessage);
                }

                pendingLinks.remove(code);
            } else {
                String errorMessage = MessageManager.getInstance().getMessage("discord.link-failure");
                event.reply(errorMessage).queue();
                LOGGER.warning(MessageManager.getInstance().getMessage("discord.link-invalid"));
            }
        }
    }

    public String generateLinkCode(UUID playerUUID) {
        String code = String.valueOf((int) (Math.random() * 900000) + 100000);
        pendingLinks.put(code, playerUUID);
        String logMessage = MessageManager.getInstance().getMessage("discord.code-generated", "{code}", code, "{uuid}", playerUUID.toString());
        LOGGER.info(logMessage);
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
    }
}
