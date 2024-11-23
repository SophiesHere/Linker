package me.ohfoxzyy.linker.Listeners;

import me.ohfoxzyy.linker.Managers.BridgeConfig;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;

public class DiscordListener extends ListenerAdapter implements Listener {

    private Map<TextChannel, BridgeConfig> bridgeChannels;

    public DiscordListener(Map<TextChannel, BridgeConfig> bridgeChannels) {
        this.bridgeChannels = bridgeChannels;
    }

    public void reload(Map<TextChannel, BridgeConfig> bridgeChannels) {
        this.bridgeChannels = bridgeChannels;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        TextChannel discordChannel = event.getChannel().asTextChannel();
        String message = event.getMessage().getContentRaw();
        String discordUser = event.getAuthor().getName();

        BridgeConfig bridgeConfig = bridgeChannels.get(discordChannel);
        if (bridgeConfig != null) {
            String prefix = bridgeConfig.getDiscordPrefix();
            String formattedMessage = formatMessage(prefix, discordUser, message);

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (bridgeConfig.getMinecraftPermission().isEmpty() || player.hasPermission(bridgeConfig.getMinecraftPermission())) {
                    player.sendMessage(formattedMessage);
                }
            }
        }
    }

    public String formatMessage(String message, String name, String chatMessage) {
        message = message.replace("{name}", name).replace("{message}", chatMessage);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
