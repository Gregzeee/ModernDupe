package me.gregzee.moderndupe;

import lombok.Getter;
import me.gregzee.moderndupe.commands.CoreCommand;
import me.gregzee.moderndupe.commands.DupeCommand;
import me.gregzee.moderndupe.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModernDupe extends JavaPlugin {

    @Getter
    private static ModernDupe instance;
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
        getLogger().info("Disabling plugin 'ModernDupe'...");
        saveDefaultConfig();
    }

    private void registerCommands() {
        this.getCommand("dupem").setExecutor(new CoreCommand());
        this.getCommand("dupem").setTabCompleter(new CoreCommand());
        this.getCommand("moderndupe").setExecutor(new DupeCommand());
    }

    private void registerListeners() {

    }
}
