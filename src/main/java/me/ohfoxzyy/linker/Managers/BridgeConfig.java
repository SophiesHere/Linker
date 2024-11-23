package me.ohfoxzyy.linker.Managers;

public class BridgeConfig {
    private final String discordRole;
    private final String minecraftPermission;
    private final String minecraftPrefix;
    private final String discordPrefix;


    public BridgeConfig(String discordRole, String minecraftPermission, String minecraftPrefix, String discordPrefix) {
        this.discordRole = discordRole;
        this.minecraftPermission = minecraftPermission;
        this.minecraftPrefix = minecraftPrefix;
        this.discordPrefix = discordPrefix;
    }

    public String getDiscordRole() {
        return discordRole;
    }

    public String getMinecraftPermission() {
        return minecraftPermission;
    }

    public String getMinecraftPrefix() {
        return minecraftPrefix;
    }

    public String getDiscordPrefix() {
        return discordPrefix;
    }
}
