package me.ohfoxzyy.linker.Listeners;

import me.ohfoxzyy.linker.Linker;
import me.ohfoxzyy.linker.Managers.BridgeConfig;
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChatListener implements Listener {

    private final DiscordBotManager discordBotManager;
    private final Linker plugin;

    public ChatListener(DiscordBotManager discordBotManager, Linker plugin) {
        this.discordBotManager = discordBotManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        Map<TextChannel, BridgeConfig> bridgeChannels = discordBotManager.getBridgeChannels();

        for (Map.Entry<TextChannel, BridgeConfig> entry : bridgeChannels.entrySet()) {
            TextChannel discordChannel = entry.getKey();
            BridgeConfig bridgeConfig = entry.getValue();

            if (player.hasPermission(bridgeConfig.getMinecraftPermission())) {
                String prefix = bridgeConfig.getMinecraftPrefix();
                String formattedMessage = formatMessage(prefix, player.getName(), message);

                try {
                    discordChannel.sendMessage(formattedMessage).queue();
                } catch (Exception e) {
                    plugin.getLogger().severe("Failed to send message to Discord: " + e.getMessage());
                }
            }
        }
    }

    private String formatMessage(String prefix, String name, String chatMessage) {
        return prefix.replace("{name}", name).replace("{message}", chatMessage);
    }
}

