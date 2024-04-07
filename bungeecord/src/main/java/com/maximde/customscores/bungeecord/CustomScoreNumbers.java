package com.maximde.customscores.bungeecord;

import com.github.retrooper.packetevents.PacketEvents;
import com.maximde.customscores.bungeecord.command.ScoresCommand;
import com.maximde.customscores.bungeecord.utils.Metrics;
import com.maximde.customscores.core.Config;
import com.maximde.customscores.core.packet.PacketManager;
import io.github.retrooper.packetevents.bungee.factory.BungeePacketEventsBuilder;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

public final class CustomScoreNumbers extends Plugin {

    @Getter
    private Config scoresConfig;

    private BungeeAudiences adventure;

    public @NonNull BungeeAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
        }
        return this.adventure;
    }

    private PacketManager packetManager;
    @Override
    public void onLoad() {
        this.packetManager = new PacketManager(BungeePacketEventsBuilder.build(this));
    }

    @Override
    public void onEnable() {
        this.adventure = BungeeAudiences.create(this);
        this.scoresConfig = new Config();
        this.packetManager.load(scoresConfig);
        new Metrics(this, 21513);
        getProxy().getPluginManager().registerCommand(this, new ScoresCommand("customscores", this));
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        PacketEvents.getAPI().terminate();
    }
}
