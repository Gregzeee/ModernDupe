package me.gregzee.dupe.commands;

import me.gregzee.dupe.config.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Level;

public final class CoreCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (args.length != 1) {
			sender.sendMessage(Component.text("Incorrect usage! /dupem <reload>"));
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			try {
				ConfigManager.load();
				sender.sendMessage(Component.text(ConfigManager.Messages.getReloadSuccessful()));
				return true;
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
				sender.sendMessage(Component.text(ConfigManager.Messages.getReloadFailed()));
				return true;
			}
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		return List.of();
	}
}
