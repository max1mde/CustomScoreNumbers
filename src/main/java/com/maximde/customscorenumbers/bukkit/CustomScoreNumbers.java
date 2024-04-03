package com.maximde.customscorenumbers.bukkit;

import com.github.retrooper.packetevents.event.PacketListener;
import com.maximde.customscorenumbers.bukkit.command.ScoresCommand;
import com.maximde.customscorenumbers.bukkit.command.ScoresTabCompleter;
import com.maximde.customscorenumbers.shared.Config;
import com.maximde.customscorenumbers.shared.packet.PacketManager;
import io.github.retrooper.packetevents.bstats.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomScoreNumbers extends JavaPlugin implements PacketListener {

    @Getter
    private Config scoresConfig;
    private PacketManager packetManager;
    @Override
    public void onLoad() {
        this.packetManager = new PacketManager(SpigotPacketEventsBuilder.build(this));
    }

    @Override
    public void onEnable() {
        this.scoresConfig = new Config();
        this.packetManager.load(scoresConfig);
        new Metrics(this, 21490);
        getCommand("customscores").setExecutor(new ScoresCommand(this));
        getCommand("customscores").setTabCompleter(new ScoresTabCompleter(this));
    }

}
