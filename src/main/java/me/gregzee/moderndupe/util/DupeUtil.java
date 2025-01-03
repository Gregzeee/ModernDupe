package me.gregzee.moderndupe.util;

import me.gregzee.moderndupe.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
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

	/**
	 *
	 * @param player
	 * @return
	 */
	private boolean hasShulkerBoxInHand(Player player) {
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		return itemInMainHand != null && itemInMainHand.getType() == Material.SHULKER_BOX;
	}

	private ShulkerBox getShulkerBoxFromItemStack(ItemStack itemStack) {
		if (itemStack == null) {
			return null;
		}

		if (!itemStack.hasItemMeta()) {
			return null;
		}

		if (!(itemStack.getItemMeta() instanceof BlockStateMeta)) {
			return null;
		}

		BlockState blockState = (BlockState) ((BlockStateMeta) itemStack.getItemMeta()).getBlockState();

		if (blockState instanceof ShulkerBox) {
			return (ShulkerBox) blockState;
		}

		return null;
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
	 * Multiplies the item inside the player's main hand once
	 * @param player the player to multiply the item for
	 */
	public void dupe(Player player) {
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (itemInMainHand == null) {
			return;
		}

		// Get the ShulkerBox if the item is a Shulker Box
		ShulkerBox shulkerBox = getShulkerBoxFromItemStack(itemInMainHand);

		if (shulkerBox != null) {
			if (containsBlacklistedItemsInShulker(shulkerBox)) {
				player.sendMessage(ConfigManager.Messages.getCantDupe());
				return;
			}

			addToInventory(player, itemInMainHand, 1);
		} else if (!isBlacklisted(itemInMainHand)) {
			addToInventory(player, itemInMainHand, 1);
		} else {
			player.sendMessage(ConfigManager.Messages.getCantDupe());
		}
	}

	/**
	 * Multiplies the item inside a player's main hand by count
	 * @param player the player to multiply items for
	 * @param count the amount of times to multiply
	 */
	public void dupe(Player player, int count) {
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (itemInMainHand == null) {
			return;
		}

		ShulkerBox shulkerBox = getShulkerBoxFromItemStack(itemInMainHand);

		if (shulkerBox != null) {
			if (containsBlacklistedItemsInShulker(shulkerBox)) {
				player.sendMessage(ConfigManager.Messages.getCantDupe());
				return;
			}
			addToInventory(player, itemInMainHand, count);
		} else if (!isBlacklisted(itemInMainHand)) {
			addToInventory(player, itemInMainHand, count);
		} else {
			player.sendMessage(ConfigManager.Messages.getCantDupe());
		}
	}
}
