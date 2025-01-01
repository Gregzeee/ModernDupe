package me.gregzee.moderndupe.config;

import lombok.Getter;
import me.gregzee.moderndupe.ModernDupe;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
public final class ConfigManager {

	@Getter
	public static final class Permissions {

		@Getter
		private static String dupe = "moderndupe.use";

		@Getter
		private static String reload = "moderndupe.reload";

	}

	// Use a set for blacklist due to it using O(1) time complexity for lookups
	@Getter
	private static Set<Material> blacklist = new HashSet<>();

	@Getter
	private static int maxDupeCount = 5;

	@Getter
	public static final class Messages {

		@Getter
		private static String reloadSuccessful = "&aConfiguration reload was successful!";

		@Getter
		private static String reloadFailed = "&cConfiguration reload was unsuccessful. Check console for more details";

		@Getter
		private static String noPermission = "&cYou don't have permission to use this command!";

		@Getter
		private static String exceededMaxDupeCount = "&cYou've exceeded the maximum moderndupe count. Please try a lower amount!";

		@Getter
		private static String onlyPlayers = "&cOnly players can use this command!";
	}

	public static void load() {
		ModernDupe instance = ModernDupe.getInstance();
		FileConfiguration config = instance.getConfig();

		Permissions.dupe = config.getString("permissions.dupe");
		Permissions.reload = config.getString("permissions.reload");


		for (String item : config.getStringList("blacklist")) {
			Material material = Material.getMaterial(item.toUpperCase());

			if (material != null) {
				blacklist.add(material);

			} else {
				instance.getLogger().warning("Invalid material in blacklist: " + item);

			}


		}

		maxDupeCount = config.getInt("maxDupeCount");

		Messages.reloadSuccessful = config.getString("messages.reloadSuccessful");
		Messages.reloadFailed = config.getString("messages.reloadFailed");
		Messages.noPermission = config.getString("messages.noPermission");
		Messages.exceededMaxDupeCount = config.getString("messages.exceededMaxDupeCount");
		Messages.onlyPlayers = config.getString("messages.onlyPlayers");
	}

	/**
	 * Method for logging all config values to console
	 */
	public static void debug() {
		ModernDupe instance = ModernDupe.getInstance();

		// Log Permissions
		instance.getLogger().info("Permissions:");
		instance.getLogger().info("  Dupe Permission: " + Permissions.dupe);
		instance.getLogger().info("  Reload Permission: " + Permissions.reload);

		// Log Blacklist
		instance.getLogger().info("Blacklist:");
		if (blacklist.isEmpty()) {
			instance.getLogger().info("  None");
		} else {
			blacklist.forEach(material -> instance.getLogger().info("  - " + material.name()));
		}

		// Log maxDupeCount
		instance.getLogger().info("Max Dupe Count: " + maxDupeCount);

		// Log Messages
		instance.getLogger().info("Messages:");
		instance.getLogger().info("  Reload Successful: " + Messages.reloadSuccessful);
		instance.getLogger().info("  Reload Failed: " + Messages.reloadFailed);
		instance.getLogger().info("  No Permission: " + Messages.noPermission);
		instance.getLogger().info("  Exceeded Max Dupe Count: " + Messages.exceededMaxDupeCount);
		instance.getLogger().info("  Only Players: " + Messages.onlyPlayers);
	}

}
