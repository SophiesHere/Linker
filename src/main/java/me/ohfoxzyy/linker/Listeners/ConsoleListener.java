package me.ohfoxzyy.linker.Listeners;

import me.ohfoxzyy.linker.Linker;
import me.ohfoxzyy.linker.Managers.BridgeConfig;
import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ConsoleListener implements Listener {

    private final DiscordBotManager discordBotManager;
    private final Linker plugin;
    private final Map<TextChannel, StringBuilder> channelLogs = new HashMap<>();
    private final Map<TextChannel, Message> lastMessages = new HashMap<>();

    public ConsoleListener(DiscordBotManager discordBotManager, Linker plugin) {
        this.discordBotManager = discordBotManager;
        this.plugin = plugin;
        setupConsoleListener();
    }

    private void setupConsoleListener() {
        int maxLines = plugin.getConfig().getInt("console.max-lines", 15);

        Bukkit.getLogger().addHandler(new java.util.logging.Handler() {
            @Override
            public void publish(java.util.logging.LogRecord record) {
                if (record != null && record.getMessage() != null) {
                    handleConsoleOutput(record.getMessage(), maxLines);
                }
            }

            @Override
            public void flush() {}

            @Override
            public void close() throws SecurityException {}
        });
    }

    private void handleConsoleOutput(String message, int maxLines) {
        Map<TextChannel, BridgeConfig> bridgeChannels = discordBotManager.getBridgeChannels();

        for (Map.Entry<TextChannel, BridgeConfig> entry : bridgeChannels.entrySet()) {
            TextChannel discordChannel = entry.getKey();
            BridgeConfig bridgeConfig = entry.getValue();

            if (bridgeConfig != null && plugin.getConfig().getBoolean("bridge." + discordChannel.getId() + ".console-channel", false)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            sendConsoleMessage(discordChannel, message, maxLines);
                        } catch (Exception e) {
                            plugin.getLogger().severe("Failed to send console message to Discord: " + e.getMessage());
                        }
                    }
                }.runTaskAsynchronously(plugin);
            }
        }
    }

    private void sendConsoleMessage(TextChannel discordChannel, String newMessage, int maxLines) {
        channelLogs.putIfAbsent(discordChannel, new StringBuilder());
        StringBuilder logBuffer = channelLogs.get(discordChannel);
        logBuffer.append(newMessage).append("\n");

        if (maxLines > 0) {
            String[] lines = logBuffer.toString().split("\n");
            if (lines.length > maxLines) {
                logBuffer.setLength(0);
                for (int i = lines.length - maxLines; i < lines.length; i++) {
                    logBuffer.append(lines[i]).append("\n");
                }
            }
        }

        if (lastMessages.containsKey(discordChannel)) {
            lastMessages.get(discordChannel).editMessage("```\n" + logBuffer.toString() + "```").queue();
        } else {
            discordChannel.sendMessage("```\n" + logBuffer.toString() + "```").queue(message -> lastMessages.put(discordChannel, message));
        }
    }
}
