package com.maximde.customscorenumbers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.score.ScoreFormat;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import com.maximde.customscorenumbers.command.ScoresCommand;
import com.maximde.customscorenumbers.command.ScoresTabCompleter;
import io.github.retrooper.packetevents.bstats.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomScoreNumbers extends JavaPlugin implements PacketListener {

    @Getter
    private Config scoresConfig;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(false)
                .bStats(false);
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        this.scoresConfig = new Config();
        PacketEvents.getAPI().getEventManager().registerListener(this,
                PacketListenerPriority.LOW);
        PacketEvents.getAPI().init();
        new Metrics(this, 21490);
        getCommand("customscores").setExecutor(new ScoresCommand(this));
        getCommand("customscores").setTabCompleter(new ScoresTabCompleter(this));
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.SCOREBOARD_OBJECTIVE) return;
        WrapperPlayServerScoreboardObjective objective = new WrapperPlayServerScoreboardObjective(event);
        if (this.scoresConfig.isInvisibleScore()) {
            objective.setScoreFormat(ScoreFormat.blankScore());
        } else {
            objective.setScoreFormat(ScoreFormat.styledScore(Style.style(TextColor.fromHexString(this.scoresConfig.getScoreColor()))));
        }
        event.markForReEncode(true);
    }
}
