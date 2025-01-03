package me.gregzee.moderndupe.util;

import me.gregzee.moderndupe.config.ConfigManager;
import net.kyori.adventure.text.Component;
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
	private boolean isCrateKey(ItemStack item) {
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

	private boolean hasShulkerBoxInHand(Player player) {
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		return itemInMainHand != null && itemInMainHand.getType() == Material.SHULKER_BOX;
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

	/**
	 * Utility method to add multiple of a certain item into a players inventory
	 * @param player the player to add the items to
	 * @param item the item to add to the players inventory
	 * @param count the amount of the given item to put into the players inventory
	 */
	private void addToInventory(Player player, ItemStack item, int count) {
		for (int i = 0; i < count; i++) {
			player.getInventory().addItem(item);
		}
	}

	/**
	 * Multiplies the item inside the players main hand once
	 * @param player the player to multiply the item for
	 */
	public void dupe(Player player) {
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (itemInMainHand == null) {
			return;
		}

		if (hasShulkerBoxInHand(player) && !containsBlacklistedItemsInShulker((ShulkerBox) itemInMainHand.getItemMeta())) {
			player.getInventory().addItem(itemInMainHand);
		} else if (!isBlacklisted(itemInMainHand)) {
			player.getInventory().addItem(itemInMainHand);
		}

		player.sendMessage(ConfigManager.Messages.getCantDupe());
	}

	/**
	 * Multiplies the item inside a players main hand by count
	 * @param player the player to multiply items for
	 * @param count the amount of times to multiply
	 */
	public void dupe(Player player, int count) {
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (hasShulkerBoxInHand(player) && !containsBlacklistedItemsInShulker((ShulkerBox) itemInMainHand.getItemMeta())) {
			addToInventory(player, itemInMainHand, count);
		} else if (!isBlacklisted(itemInMainHand)) {
			addToInventory(player, itemInMainHand, count);
		}

		player.sendMessage(ConfigManager.Messages.getCantDupe());
	}
}
