package me.gregzee.moderndupe.util;

import me.gregzee.moderndupe.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.strassburger.lifestealz.LifeStealZ;
import org.strassburger.lifestealz.api.LifeStealZAPI;
import su.nightexpress.excellentcrates.CratesAPI;
import java.util.Set;

public final class DupeUtil {

	/**
	 * Determines if the given item is a crate key
	 * @param item the item to check
	 * @return {@code true} if the item is a key; {@code false} otherwise
	 */
	private static boolean isCrateKey(ItemStack item) {
		if (item == null) {
			return false;
		}

		return CratesAPI.getKeyManager().isKey(item);
	}

	/**
	 * Determines if the given item is a custom item from LifestealZ
	 * @param item the item to check
	 * @return {@code true} if the item is a custom item from LifestealZ; {@code false} otherwise
	 */
	private boolean isLifestealZItem(ItemStack item) {
		if (item == null) {
			return false;
		}

		LifeStealZAPI lifeStealZ = LifeStealZ.getAPI();

		// Get the items custom ID
		String customID = lifeStealZ.getCustomItemID(item);

		// Get the list of custom items that are from LifeStealZ
		Set<String> customItemIDs = lifeStealZ.getCustomItemIDs();

		return customItemIDs.contains(customID);
	}

	/**
	 * Determines if the given item is blacklisted
	 * @param item the item to check
	 * @return {@code true} if the item is blacklisted; {@code false} otherwise
	 */
	private boolean isBlacklisted(ItemStack item) {
		// Check if the item is null
		if (item == null) {
			return false;
		}

		// Check if the item is in the blacklist
		if (ConfigManager.getBlacklist().contains(item.getType())) {
			return true;
		// Check if the item is a crate key
		} else if (isCrateKey(item)) {
			return true;
		// Check if the item is a LifeStealZ Item
		} else {
			return isLifestealZItem(item);
		}
	}

	/**
	 * Loops through the contents of the given shulker box and checks for any items that are blacklisted
	 * @param shulkerBox the shulker box of which to check the contents of
	 * @return {@code true} if a blacklisted item was found; {@code false} otherwise
	 */
	private boolean containsBlacklistedItemsInShulker(ShulkerBox shulkerBox) {

		// Get the entire contents of the shulker
		ItemStack[] contents = shulkerBox.getInventory().getContents();

		// Loop through the shulkerbox contents
		for (ItemStack item : contents) {

			if (item == null) continue;

			if (isBlacklisted(item)) {
				return true;
			}
		}

		return false;
	}

	public static void dupe(Player player) {

	}

	public static void dupe(Player player, int count) {

	}

	private boolean hasShulkerBoxInHand(Player player) {
		return true;
	}

}
