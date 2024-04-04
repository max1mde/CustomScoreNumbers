package com.maximde.customscorenumbers.velocity;

import com.github.retrooper.packetevents.PacketEvents;
import com.maximde.customscorenumbers.shared.Config;
import com.maximde.customscorenumbers.shared.Constants;
import com.maximde.customscorenumbers.shared.packet.PacketManager;
import com.maximde.customscorenumbers.velocity.commands.ScoresCommand;
import com.maximde.customscorenumbers.velocity.utils.Metrics;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.retrooper.packetevents.velocity.factory.VelocityPacketEventsBuilder;
import lombok.Getter;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "custmoscores",
        name = "CustomScoreNumbers",
        version = "1.2.0",
        description = "Customize the scoreboard sidebar numbers",
        authors = {"MaximDe"}
)
public class CustomScoreNumbers {
    @Inject
    private Logger logger;
    @Inject @Getter
    private ProxyServer server;
    @Inject
    private PluginContainer pluginContainer;
    @Inject @DataDirectory
    private Path dataDirectory;
    @Getter
    private Config config;
    @Inject
    private Metrics.Factory metricsFactory;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Metrics metrics = metricsFactory.make(this, 21514);
        this.config = new Config();
        PacketManager packetManager = new PacketManager(VelocityPacketEventsBuilder.build(server, pluginContainer, logger, dataDirectory));
        packetManager.load(config);
        server.getCommandManager().register(Constants.GLOBAL_COMMAND_NAME, new ScoresCommand(this));
    }

    @Subscribe
    public void onProxyStop(ProxyShutdownEvent event) {
        PacketEvents.getAPI().terminate();
    }
}