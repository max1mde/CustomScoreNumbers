package com.maximde.customscorenumbers;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class Config {

    private final File file = new File("plugins/CustomScoreNumbers", "config.yml");
    private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);


    private boolean invisibleScore;
    private String scoreColor;

    public Config() {
        setIfNot("ScoreNumbers.Invisible", true);
        setIfNot("ScoreNumbers.Color", "#04FFF9");
        saveConfig();
        initValues();
    }


    private void initValues() {
        this.invisibleScore = cfg.getBoolean("ScoreNumbers.Invisible");
        this.scoreColor = cfg.getString("ScoreNumbers.Color");
    }

    public void reload() {
        this.cfg = YamlConfiguration.loadConfiguration(file);
        initValues();
    }

    public String getPermission(String permission) {
        return "CustomScores" + "." + permission;
    }

    @SneakyThrows
    public void saveConfig() {
        cfg.save(file);
    }

    private void setIfNot(String path, Object value) {
        if(!cfg.isSet(path)) setValue(path, value);
    }

    public void setValue(String path, Object value) {
        this.cfg.set(path, value);
        saveConfig();
        reload();
    }

    public Object getValue(String path) {
        return this.cfg.get(path);
    }

}


