package me.gregzee.moderndupe.util;

import org.bukkit.inventory.ItemStack;
import su.nightexpress.excellentcrates.CratesAPI;

public final class DupeUtil {

	/**
	 * Determines if the given item is a crate key
	 * @param item the item to check
	 * @return {@code true} if the item is a key; {@code false} otherwise
	 */
	public static boolean isCrateKey(ItemStack item) {
		if (item == null) {
			return false;
		}

		return CratesAPI.getKeyManager().isKey(item);
	}
}
