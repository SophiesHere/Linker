package me.ohfoxzyy.linker.Managers;

import me.ohfoxzyy.linker.Linker;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager {
    private static final MessageManager instance = new MessageManager();

    private File file;
    private FileConfiguration config;

    private MessageManager() {
    }

    public void loadMessages(Linker plugin) {
        file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            try {
                plugin.saveResource("messages.yml", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(String key, String... placeholders) {
        String message = config.getString(key, "Message not found!");

        if (placeholders.length > 0) {
            for (int i = 0; i < placeholders.length; i++) {
                message = message.replace("{" + i + "}", placeholders[i]);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static MessageManager getInstance() {
        return instance;
    }
}
