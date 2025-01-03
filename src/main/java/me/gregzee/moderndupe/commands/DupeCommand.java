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
import java.util.Map;

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
			int maxCount;

			maxCount = getMaxDupeCount(player);

			try {
				count = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				player.sendMessage(ConfigManager.Messages.getInvalidDupeCount());
				return true;
			}

			if (count > maxCount) {
				player.sendMessage(ConfigManager.Messages.getExceededMaxDupeCount());
			}

			dupeUtil.dupe(player, count);
			return true;

		} else if (args.length == 0) {
			dupeUtil.dupe(player);
			return true;
		} else {
			player.sendMessage(Component.text("Invalid usage! Use: /dupe <count>").color(NamedTextColor.RED));
			return true;
		}
	}

	/**
	 * Utility method to check how much the given player is allowed to dupe at once
	 * @param player the player to check the max dupe count of
	 * @return the max amount the player is allowed to dupe
	 */
	private int getMaxDupeCount(Player player) {
		int maxCount = 0;

		for (Map.Entry<String, Object> entry : ConfigManager.getDupeCountLimits().entrySet()) {
			String permission = entry.getKey();
			int count = (int) entry.getValue();

			if (player.hasPermission(permission)) {
				maxCount = Math.max(maxCount, count);
			}
		}

		return maxCount;
	}
}
