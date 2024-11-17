package com.maximde.customscores.velocity;

import com.github.retrooper.packetevents.PacketEvents;
import com.google.inject.Inject;
import com.maximde.customscores.core.Config;
import com.maximde.customscores.core.Constants;
import com.maximde.customscores.core.packet.PacketManager;
import com.maximde.customscores.velocity.commands.ScoresCommand;
import com.maximde.customscores.velocity.utils.Metrics;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
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
@Getter
public class CustomScoreNumbers {
    private final Logger logger;
    private final ProxyServer server;
    private final PluginContainer pluginContainer;
    private final Path dataDirectory;
    private final Metrics.Factory metricsFactory;
    private final Config config;
    private final PacketManager packetManager;
    @Inject
    public CustomScoreNumbers(Logger logger, ProxyServer proxyServer, PluginContainer pluginContainer, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
        this.logger = logger;
        this.server = proxyServer;
        this.pluginContainer = pluginContainer;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;
        this.config = new Config();
        packetManager = new PacketManager(VelocityPacketEventsBuilder.build(server, pluginContainer, logger, dataDirectory));
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Metrics metrics = metricsFactory.make(this, 21514);
        packetManager.load(config);
        server.getCommandManager().register(Constants.GLOBAL_COMMAND_NAME, new ScoresCommand(this));
    }

    @Subscribe
    public void onProxyStop(ProxyShutdownEvent event) {
        PacketEvents.getAPI().terminate();
    }
}