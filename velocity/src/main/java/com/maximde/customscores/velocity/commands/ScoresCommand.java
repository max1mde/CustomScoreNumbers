package com.maximde.customscores.velocity.commands;


import com.maximde.customscores.core.Commands;
import com.maximde.customscores.core.Constants;
import com.maximde.customscores.core.Message;
import com.maximde.customscores.velocity.CustomScoreNumbers;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class ScoresCommand implements SimpleCommand {
    private final CustomScoreNumbers customScoreNumbers;
    public ScoresCommand(CustomScoreNumbers customScoreNumbers) {
        this.customScoreNumbers = customScoreNumbers;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        if(!sender.hasPermission(customScoreNumbers.getConfig().getPermission("commands"))) {
            sender.sendMessage(getMessage("<red>No permission!"));
            return;
        }
        if(args.length != 1) {
            sendCommands(sender);
            return;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            customScoreNumbers.getConfig().reload();
            sender.sendMessage(getMessage("<green>com.maximde.customscores.core.Config reloaded!"));
            return;
        }
        sendCommands(sender);
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        List<String> completions = new ArrayList<>();
        String[] args = invocation.arguments();
        if (args.length == 0) {
            completions.add("reload");
        }
        return completions;
    }

    private void sendCommands(CommandSource sender) {
        sender.sendMessage(getMessage("<b>com.maximde.customscores.core.Commands</b>"));
        for (Commands value : Commands.values()) {
            sender.sendMessage(getMessage("<white>- /"+Constants.GLOBAL_COMMAND_NAME+" "+value.name+" <gray>("+value.description+")"));
        }
        sender.sendMessage(getMessage("<b>com.maximde.customscores.core.Commands</b>"));
    }

    private Component getMessage(String message) {
        return Constants.PREFIX.append(Message.get(message));
    }
}
