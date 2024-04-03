package com.maximde.customscorenumbers.bukkit.command;

import com.maximde.customscorenumbers.bukkit.CustomScoreNumbers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ScoresCommand implements CommandExecutor {

    private final CustomScoreNumbers customScoreNumbers;
    public ScoresCommand(CustomScoreNumbers customScoreNumbers) {
        this.customScoreNumbers = customScoreNumbers;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission(customScoreNumbers.getScoresConfig().getPermission("commands"))) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return false;
        }
        if(args.length != 1) {
            sendCommands(sender);
            return false;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            customScoreNumbers.getScoresConfig().reload();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
            return false;
        }
        sendCommands(sender);
        return false;
    }

    private void sendCommands(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Commands:\n" +
                "- /customscores reload (Reload the config)");
    }
}
