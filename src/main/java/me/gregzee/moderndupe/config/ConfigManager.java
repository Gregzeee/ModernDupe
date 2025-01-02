package me.gregzee.moderndupe.config;

import lombok.Getter;
import me.gregzee.moderndupe.ModernDupe;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.HashSet;
import java.util.Set;

@Getter
public final class ConfigManager {

	/**
	 * Class for holding all permissions related to the plugin
	 */
	@Getter
	public static final class Permissions {

		@Getter
		private static String dupe = "moderndupe.use";

		@Getter
		private static String reload = "moderndupe.reload";

	}

	@Getter
	private static Set<Material> blacklist = new HashSet<>();

	@Getter
	private static int maxDupeCount = 5;

	/**
	 * Class for holding all messages related to the plugin
	 */
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

		@Getter
		private static String cantDupe = "&cYou can't dupe that item, because it contains a blacklisted item!";
	}

	/**
	 * Loads config values from the config file into memory.
	 */
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
		Messages.cantDupe = config.getString("messages.cantDupe");
	}
}
