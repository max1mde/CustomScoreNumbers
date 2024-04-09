package com.maximde.customscores.spigot;

import com.github.retrooper.packetevents.PacketEvents;
import com.maximde.customscores.core.Config;
import com.maximde.customscores.core.packet.PacketManager;
import com.maximde.customscores.spigot.commands.ScoresCommand;
import com.maximde.customscores.spigot.commands.ScoresTabCompleter;
import io.github.retrooper.packetevents.bstats.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomScoreNumbers extends JavaPlugin {

    @Getter
    private Config scoresConfig;
    private PacketManager packetManager;
    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
    @Override
    public void onLoad() {
        this.packetManager = new PacketManager(SpigotPacketEventsBuilder.build(this));
    }

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        this.scoresConfig = new Config();
        this.packetManager.load(scoresConfig);
        new Metrics(this, 21490);
        getCommand("customscores").setExecutor(new ScoresCommand(this));
        getCommand("customscores").setTabCompleter(new ScoresTabCompleter(this));
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

}
