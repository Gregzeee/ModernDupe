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
		private static String reloadSuccessful = "";

		@Getter
		private static String reloadFailed = "";

		@Getter
		private static String noPermission = "";

		@Getter
		private static String exceededMaxDupeCount = "";
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
	}

}
