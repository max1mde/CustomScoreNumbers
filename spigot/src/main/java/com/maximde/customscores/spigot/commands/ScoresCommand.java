package com.maximde.customscores.spigot.commands;


import com.maximde.customscores.core.Commands;
import com.maximde.customscores.core.Constants;
import com.maximde.customscores.core.Message;
import com.maximde.customscores.spigot.CustomScoreNumbers;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
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
            sender.spigot().sendMessage(getMessage("<red>No permission!"));
            return false;
        }
        if(args.length != 1) {
            sendCommands(sender);
            return false;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            customScoreNumbers.getScoresConfig().reload();
            sender.spigot().sendMessage(getMessage("<green>Config reloaded!"));
            return false;
        }
        sendCommands(sender);
        return false;
    }

    private void sendCommands(CommandSender sender) {
        sender.spigot().sendMessage(getMessage("<b>Commands</b>"));
        for (Commands value : Commands.values()) {
            sender.spigot().sendMessage(getMessage("<white>- /customscores "+value.name+" <gray>("+value.description+")"));
        }
        sender.spigot().sendMessage(getMessage("<b>Commands</b>"));
    }

    private BaseComponent[] getMessage(String message) {
        return BungeeComponentSerializer.get().serialize(Constants.PREFIX.append(Message.get(message)));
    }
}
