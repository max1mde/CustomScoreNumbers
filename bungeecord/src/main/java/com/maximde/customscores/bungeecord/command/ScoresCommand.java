package com.maximde.customscores.bungeecord.command;


import com.maximde.customscores.bungeecord.CustomScoreNumbers;
import com.maximde.customscores.core.Commands;
import com.maximde.customscores.core.Constants;
import com.maximde.customscores.core.Message;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
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
        if(!sender.hasPermission(customScoreNumbers.getScoresConfig().getPermission("com.maximde.customscores.velocity.commands"))) {
            sender.sendMessage(getMessage("<red>No permission!"));
            return;
        }
        if(args.length != 1) {
            sendCommands(sender);
            return;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            customScoreNumbers.getScoresConfig().reload();
            sender.sendMessage(getMessage("<green>com.maximde.customscores.core.Config reloaded!"));
            return;
        }
        sendCommands(sender);
        return;
    }

    private void sendCommands(CommandSender sender) {
        sender.sendMessage(getMessage("<b>com.maximde.customscores.core.Commands</b>"));
        for (Commands value : Commands.values()) {
            sender.sendMessage(getMessage("<white>- /customscores "+value.name+" <gray>("+value.description+")"));
        }
        sender.sendMessage(getMessage("<b>com.maximde.customscores.core.Commands</b>"));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer player) {
            if(!player.hasPermission(customScoreNumbers.getScoresConfig().getPermission("com.maximde.customscores.velocity.commands"))) {
                return new ArrayList<>();
            }
        }
        List<String> completions = new ArrayList<>();
        if (args.length == 1) completions.add("reload");
        return completions;
    }

    private BaseComponent[] getMessage(String message) {
        return BungeeComponentSerializer.get().serialize(Constants.PREFIX.append(Message.get(message)));
    }
}
