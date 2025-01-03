package me.gregzee.moderndupe.config;

import lombok.Getter;
import me.gregzee.moderndupe.ModernDupe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.*;

@Getter
public final class ConfigManager {

	private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().character('&').build();

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

	/**
	 * Class for holding all messages related to the plugin
	 */
	@Getter
	public static final class Messages {

		@Getter
		private static Component reloadSuccessful = serializer.deserialize("&aConfiguration reload was successful!");

		@Getter
		private static Component reloadFailed = serializer.deserialize("&cConfiguration reload was unsuccessful. Check console for more details");

		@Getter
		private static Component noPermission = serializer.deserialize("&cYou don't have permission to use this command!");

		@Getter
		private static Component exceededMaxDupeCount = serializer.deserialize("&cYou've exceeded the maximum moderndupe count. Please try a lower amount!");

		@Getter
		private static Component onlyPlayers = serializer.deserialize("&cOnly players can use this command!");

		@Getter
		private static Component cantDupe = serializer.deserialize("&cYou can't dupe that item, because it contains a blacklisted item!");

		@Getter
		private static Component invalidDupeCount = serializer.deserialize("&cYou entered an invalid dupe count!");
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

		Messages.reloadSuccessful = serializer.deserialize(config.getString("messages.reloadSuccessful"));
		Messages.reloadFailed = serializer.deserialize(config.getString("messages.reloadFailed"));
		Messages.noPermission = serializer.deserialize(config.getString("messages.noPermission"));
		Messages.exceededMaxDupeCount = serializer.deserialize(config.getString("messages.exceededMaxDupeCount"));
		Messages.onlyPlayers = serializer.deserialize(config.getString("messages.onlyPlayers"));
		Messages.cantDupe = serializer.deserialize(config.getString("messages.cantDupe"));
	}
}
