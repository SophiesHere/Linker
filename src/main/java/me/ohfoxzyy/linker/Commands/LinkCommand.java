package me.ohfoxzyy.linker.Commands;

import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {
    private DiscordBotManager discordBotManager;

    public LinkCommand(DiscordBotManager discordBotManager) {
        this.discordBotManager = discordBotManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (discordBotManager.isPlayerLinked(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "Your account is already linked to Discord.");
                return true;
            }

            String linkCode = discordBotManager.generateLinkCode(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW + "Your link code is: " + ChatColor.GOLD + linkCode);
            player.sendMessage(ChatColor.YELLOW + "Use this code in the Discord server to link your account!");

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return false;
        }
    }
}
