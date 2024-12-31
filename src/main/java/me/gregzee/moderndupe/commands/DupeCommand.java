package me.gregzee.moderndupe.commands;

import me.gregzee.moderndupe.config.ConfigManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class DupeCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(ConfigManager.Messages.getOnlyPlayers());
			return true;
		}

		if (!player.hasPermission(ConfigManager.Permissions.getDupe())) {
			sender.sendMessage(ConfigManager.Messages.getNoPermission());
			return true;
		}

		if (args.length == 1) {

		} else if (args.length == 0) {
			
		} else {
			player.sendMessage(Component.text("").color(NamedTextColor.RED));
			return true;
		}


		return true;
	}
}
