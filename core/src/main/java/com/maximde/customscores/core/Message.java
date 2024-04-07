package com.maximde.customscores.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Message {
    private static MiniMessage minimessage = MiniMessage.miniMessage();
    public static Component get(String message) {
        return minimessage.deserialize(message);
    }
    public static String getString(String message) {
        return ((TextComponent)minimessage.deserialize(message)).content();
    }
}
