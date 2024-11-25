package com.maximde.customscores.core.packet;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.maximde.customscores.core.Config;
import com.maximde.customscores.core.packet.listeners.PacketSendListener;


public class PacketManager {

    public PacketManager(PacketEventsAPI<?> api) {
        PacketEvents.setAPI(api);
        PacketEvents.getAPI().load();
    }

    public void load(Config config) {
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
                .checkForUpdates(false)
                .bStats(true);
        PacketEvents.getAPI().getEventManager().registerListener(new PacketSendListener(config),
                PacketListenerPriority.LOW);
        PacketEvents.getAPI().init();
    }
}
