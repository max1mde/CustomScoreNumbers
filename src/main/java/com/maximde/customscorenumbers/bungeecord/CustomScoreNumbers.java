package com.maximde.customscorenumbers.bungeecord;

import com.maximde.customscorenumbers.bungeecord.command.ScoresCommand;
import com.maximde.customscorenumbers.bungeecord.utils.Metrics;
import com.maximde.customscorenumbers.shared.Config;
import com.maximde.customscorenumbers.shared.packet.PacketManager;
import io.github.retrooper.packetevents.bungee.factory.BungeePacketEventsBuilder;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public final class CustomScoreNumbers extends Plugin {

    @Getter
    private Config scoresConfig;
    private PacketManager packetManager;

    @Override
    public void onLoad() {
        this.packetManager = new PacketManager(BungeePacketEventsBuilder.build(this));
    }

    @Override
    public void onEnable() {
        this.scoresConfig = new Config();
        this.packetManager.load(scoresConfig);
        new Metrics(this, 21490);
        getProxy().getPluginManager().registerCommand(this, new ScoresCommand("customscores", this));
    }
}
