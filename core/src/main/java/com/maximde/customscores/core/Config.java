package com.maximde.customscores.core;

import lombok.Getter;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;

@Getter
public class Config {

    private final YamlFile yaml = new YamlFile("plugins/CustomScoreNumbers/config.yml");
    private boolean invisibleScore;
    private String scoreColor;

    public Config() {
        reload();
        setDefaults();
        initVars();
    }

    private void setDefaults() {
        yaml.addDefault("ScoreNumbers.Invisible", true);
        yaml.addDefault("ScoreNumbers.Color", "#04FFF9");
        save();
    }

    private void initVars() {
        this.invisibleScore = yaml.getBoolean("ScoreNumbers.Invisible");
        this.scoreColor = yaml.getString("ScoreNumbers.Color");
    }

    public void reload() {
        try {
            if (!yaml.exists()) yaml.createNewFile();
            yaml.load();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
        initVars();
    }

    private void save() {
        try {
            yaml.save();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getPermission(String permission) {
        return "CustomScores" + "." + permission;
    }
}
