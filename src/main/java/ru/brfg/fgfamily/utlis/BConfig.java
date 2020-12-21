package ru.brfg.fgfamily.utlis;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.brfg.fgfamily.utlis.FileUtils;

public class BConfig {
    private JavaPlugin main;
    private String name;
    private FileConfiguration fileConfiguration;
    private File file;

    public BConfig(JavaPlugin main, String name) {
        this.main = main;
        this.name = name;
        this.load();
    }

    private void load() {
        File file;
        if (!this.main.getDataFolder().exists()) {
            FileUtils.mkdir(this.main.getDataFolder());
        }
        if (!(file = new File(this.main.getDataFolder(), this.name)).exists()) {
            FileUtils.copy(this.main.getResource(this.name), file);
        }
        this.file = file;
        this.fileConfiguration = YamlConfiguration.loadConfiguration((File)file);
        this.fileConfiguration.options().copyDefaults(true);
    }

    public void save() {
        try {
            this.fileConfiguration.options().copyDefaults(true);
            this.fileConfiguration.save(this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.fileConfiguration;
    }
}

