package com.maximde.customscores.core.packet.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.score.ScoreFormat;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerScoreboardObjective;
import com.maximde.customscores.core.Config;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

public class PacketSendListener implements PacketListener {

    private final Config config;

    public PacketSendListener(Config config) {
        this.config = config;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.SCOREBOARD_OBJECTIVE) return;
        WrapperPlayServerScoreboardObjective objective = new WrapperPlayServerScoreboardObjective(event);
        if (this.config.isInvisibleScore()) {
            objective.setScoreFormat(ScoreFormat.blankScore());
        } else {
            objective.setScoreFormat(ScoreFormat.styledScore(Style.style(TextColor.fromHexString(this.config.getScoreColor()))));
        }
        event.markForReEncode(true);
    }
}
