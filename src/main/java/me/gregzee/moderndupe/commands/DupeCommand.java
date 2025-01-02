package me.gregzee.moderndupe.commands;

import me.gregzee.moderndupe.config.ConfigManager;
import me.gregzee.moderndupe.util.DupeUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class DupeCommand implements CommandExecutor {

	private final DupeUtil dupeUtil = new DupeUtil();

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
			int count;

			try {
				count = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				player.sendMessage(ConfigManager.Messages.getInvalidDupeCount());
				return true;
			}

			dupeUtil.dupe(player, count);

		} else if (args.length == 0) {
			dupeUtil.dupe(player);
		} else {
			player.sendMessage(Component.text("Invalid usage! Use: /dupe <count>").color(NamedTextColor.RED));
			return true;
		}

		return true;
	}
}
