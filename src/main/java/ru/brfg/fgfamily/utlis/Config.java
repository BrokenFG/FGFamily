package ru.brfg.fgfamily.utlis;

import org.bukkit.configuration.file.FileConfiguration;
import ru.brfg.fgfamily.utlis.BConfig;

public class Config {
    static FileConfiguration config;
    static Config cfg;
    public static int marryPrice;
    public static int divorcePrice;
    public static String host;
    public static String port;
    public static String database;
    public static String user;
    public static String password;
    public static String marry;

    public Config(BConfig config) {
        cfg = this;
        this.setConfig(config);
    }

    public void setConfig(BConfig config) {
        Config.config = config.getConfig();
        this.loadConfig();
    }

    public static Config getInstance() {
        return cfg;
    }

    public void loadConfig() {
        marryPrice = config.getInt("MarryPrice", marryPrice);
        divorcePrice = config.getInt("DivorcePrice", divorcePrice);
        host = config.getString("sql.host");
        port = config.getString("sql.port");
        database = config.getString("sql.database");
        user = config.getString("sql.user");
        password = config.getString("sql.password");
        marry = config.getString("marry", marry);
    }

    static {
        marryPrice = 2500;
        divorcePrice = 10000;
        marry = "\u2764";
    }
}

