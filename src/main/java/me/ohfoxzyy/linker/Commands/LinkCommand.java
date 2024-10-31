package me.ohfoxzyy.linker.Commands;

import me.ohfoxzyy.linker.Managers.DiscordBotManager;
import me.ohfoxzyy.linker.Managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {
    private DiscordBotManager discordBotManager;

    public LinkCommand(DiscordBotManager discordBotManager) {
        this.discordBotManager = discordBotManager;
    }

    public void setDiscordBotManager(DiscordBotManager discordBotManager) {
        this.discordBotManager = discordBotManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (discordBotManager.isPlayerLinked(player.getUniqueId())) {
                String alreadyLinked = MessageManager.getInstance().getMessage("minecraft.already-linked");
                player.sendMessage(alreadyLinked);
                return true;
            }

            String linkCode = discordBotManager.generateLinkCode(player.getUniqueId());
            String linkMessage1 = MessageManager.getInstance().getMessage("minecraft.link-code1").replace("{code}", linkCode);
            player.sendMessage(linkMessage1);

            String linkMessage2 = MessageManager.getInstance().getMessage("minecraft.link-code2");
            player.sendMessage(linkMessage2);

            return true;
        } else {
            String onlyPlayers = MessageManager.getInstance().getMessage("minecraft.only-player", sender.getName());
            sender.sendMessage(onlyPlayers);
            return false;
        }
    }
}