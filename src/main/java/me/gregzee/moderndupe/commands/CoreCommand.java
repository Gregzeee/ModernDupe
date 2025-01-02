package me.gregzee.moderndupe.commands;

import me.gregzee.moderndupe.ModernDupe;
import me.gregzee.moderndupe.config.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CoreCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (args.length != 1) {
			sender.sendMessage(Component.text("Incorrect usage! /dupem <reload>"));
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission(ConfigManager.Permissions.getReload())) {
				sender.sendMessage(ConfigManager.Messages.getNoPermission());
				return true;
			}

			try {
				ConfigManager.load();
				sender.sendMessage(ConfigManager.Messages.getReloadSuccessful());
				return true;
			} catch (Exception exception) {
				ModernDupe.getInstance().getLogger().severe(exception.getMessage());
				sender.sendMessage(ConfigManager.Messages.getReloadFailed());
				return true;
			}
		}
		return true;
	}

	// List of arguments to tab complete
	private static final String[] COMMANDS = { "reload" };

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			return new ArrayList<>();
		}

		List<String> completions = new ArrayList<>();
		List<String> suggestions = new ArrayList<>();

		if (args.length == 1) {
			completions.addAll(Arrays.asList(COMMANDS));
		}

		StringUtil.copyPartialMatches(args[0], completions, suggestions);
		suggestions.sort(String.CASE_INSENSITIVE_ORDER);

		return suggestions;
	}
}
