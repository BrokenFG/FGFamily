package ru.brfg.fgfamily;

import com.earth2me.essentials.Essentials;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.brfg.fgfamily.Commands.CmdMarry;
import ru.brfg.fgfamily.utlis.BConfig;
import ru.brfg.fgfamily.utlis.Config;
import ru.brfg.fgfamily.utlis.Lang;
import ru.brfg.fgfamily.utlis.SQL;

public final class FGFamily
extends JavaPlugin {
    private static FGFamily instance;
    public BConfig bConfig;
    public BConfig langConfig;
    public Config config;
    public Economy economy;
    public Essentials essentials;
    public MarryManager marryManager;

    public static FGFamily getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.marryManager = new MarryManager(this);
        this.bConfig = new BConfig(this, "config.yml");
        this.langConfig = new BConfig(this, "lang.yml");
        this.config = new Config(this.bConfig);
        Lang.setConfig(this.langConfig);
        RegisteredServiceProvider provider = this.getServer().getServicesManager().getRegistration(Economy.class);
        this.economy = (Economy)provider.getProvider();
        this.essentials = (Essentials)this.getServer().getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginCommand("marry").setExecutor(new CmdMarry(this));
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        SQL.createTables();
    }

    public void onDisable() {
    }
}

