package com.maximde.customscorenumbers.bungeecord;

import com.maximde.customscorenumbers.bungeecord.command.ScoresCommand;
import com.maximde.customscorenumbers.bungeecord.utils.Metrics;
import com.maximde.customscorenumbers.shared.Config;
import com.maximde.customscorenumbers.shared.packet.PacketManager;
import io.github.retrooper.packetevents.bungee.factory.BungeePacketEventsBuilder;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

public final class CustomScoreNumbers extends Plugin {

    @Getter
    private Config scoresConfig;
    private PacketManager packetManager;

    private BungeeAudiences adventure;

    public @NonNull BungeeAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
        }
        return this.adventure;
    }


    @Override
    public void onEnable() {
        this.packetManager = new PacketManager(BungeePacketEventsBuilder.build(this));
        this.adventure = BungeeAudiences.create(this);
        this.scoresConfig = new Config();
        this.packetManager.load(scoresConfig);
        new Metrics(this, 21490);
        getProxy().getPluginManager().registerCommand(this, new ScoresCommand("customscores", this));
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
