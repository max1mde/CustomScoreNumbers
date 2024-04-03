package com.maximde.customscorenumbers.bungeecord.command;

import com.maximde.customscorenumbers.bungeecord.CustomScoreNumbers;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;


public class ScoresCommand extends Command implements TabExecutor {

    private final CustomScoreNumbers customScoreNumbers;
    public ScoresCommand(String name, CustomScoreNumbers customScoreNumbers) {
        super(name);
        this.customScoreNumbers = customScoreNumbers;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission(customScoreNumbers.getScoresConfig().getPermission("commands"))) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return;
        }
        if(args.length != 1) {
            sendCommands(sender);
            return;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            customScoreNumbers.getScoresConfig().reload();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
            return;
        }
        sendCommands(sender);
        return;
    }

    private void sendCommands(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Commands:\n" +
                "- /customscores reload (Reload the config)");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer player) {
            if(!player.hasPermission(customScoreNumbers.getScoresConfig().getPermission("commands"))) {
                return new ArrayList<>();
            }
        }
        List<String> completions = new ArrayList<>();
        if (args.length == 1) completions.add("reload");
        return completions;
    }
}
