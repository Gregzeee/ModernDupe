package me.gregzee.moderndupe;

import lombok.Getter;
import me.gregzee.moderndupe.commands.CoreCommand;
import me.gregzee.moderndupe.commands.DupeCommand;
import me.gregzee.moderndupe.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModernDupe extends JavaPlugin {

    @Getter
    private static ModernDupe instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        ConfigManager.load();

        registerCommands();

        getLogger().info("Enabled plugin 'ModernDupe'");

    }

    @Override
    public void onDisable() {
        saveConfig();

        getLogger().info("Disabled plugin 'ModernDupe'");
    }

    private void registerCommands() {
        this.getCommand("moderndupe").setExecutor(new CoreCommand());
        this.getCommand("moderndupe").setTabCompleter(new CoreCommand());
        this.getCommand("dupe").setExecutor(new DupeCommand());
    }
}
