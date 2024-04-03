package com.maximde.customscorenumbers.shared;

public enum Commands {
    RELOAD("reload", "Reload the config");

    public final String name;

    public final String description;

    Commands(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
