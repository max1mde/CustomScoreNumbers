package com.maximde.customscorenumbers.command;

import com.maximde.customscorenumbers.CustomScoreNumbers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ScoresTabCompleter implements TabCompleter {

    private final CustomScoreNumbers customScoreNumbers;
    public ScoresTabCompleter(CustomScoreNumbers customScoreNumbers) {
        this.customScoreNumbers = customScoreNumbers;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission(customScoreNumbers.getScoresConfig().getPermission("commands"))) {
                return new ArrayList<>();
            }
        }
        List<String> completions = new ArrayList<>();
        if (args.length == 1) completions.add("reload");
        return completions;
    }
}
