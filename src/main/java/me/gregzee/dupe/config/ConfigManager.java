package me.gregzee.dupe.config;

import lombok.Getter;
import me.gregzee.dupe.Dupe;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

@Getter
public final class ConfigManager {

	@Getter
	public static final class Permissions {

		@Getter
		private static String dupe = "dupe.use";

		@Getter
		private static String reload = "dupe.reload";

	}

	@Getter
	private static ArrayList<Material> blacklist;

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
		private static String exceededMaxDupeCount = "&cYou've exceeded the maximum dupe count. Please try a lower amount!";

		@Getter
		private static String onlyPlayers = "&cOnly players can use this command!";
	}

	public static void load() {
		Dupe instance = Dupe.getInstance();
		FileConfiguration config = instance.getConfig();

		Permissions.dupe = config.getString("permissions.dupe");
		Permissions.reload = config.getString("permissions.reload");

		for (String item : config.getStringList("blacklist")) {
			Material material = Material.getMaterial(item);
			blacklist.add(material);
		}

		maxDupeCount = config.getInt("maxDupeCount");

		Messages.reloadSuccessful = config.getString("messages.reloadSuccessful");
		Messages.reloadFailed = config.getString("messages.reloadFailed");
		Messages.noPermission = config.getString("messages.noPermission");
		Messages.exceededMaxDupeCount = config.getString("messages.exceededMaxDupeCount");
		Messages.onlyPlayers = config.getString("messages.onlyPlayers");
	}

}
