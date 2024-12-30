package me.gregzee.dupe;

import lombok.Getter;
import me.gregzee.dupe.commands.CoreCommand;
import me.gregzee.dupe.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dupe extends JavaPlugin {

    @Getter
    private static Dupe instance;
    public ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling plugin 'Dupe'...");
        saveDefaultConfig();
    }

    private void registerCommands() {
        this.getCommand("dupe").setExecutor(new CoreCommand());
        this.getCommand("dupe").setTabCompleter(new CoreCommand());
    }

    private void registerListeners() {

    }
}
