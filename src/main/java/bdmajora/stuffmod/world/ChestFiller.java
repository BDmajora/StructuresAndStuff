package bdmajora.stuffmod.world;

import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;

import java.util.Random;

/**
 * ChestFiller is a simple loot provider that can populate a structure chest.
 * This baseline implementation fills all slots with the same item, but the
 * design allows you to expand with weighted loot, random selections, etc.
 */
public class ChestFiller {

	public ChestFiller() {
	}

	/**
	 * Fills a chest with loot. Extend this method to add custom/randomized loot logic.
	 *
	 * @param random Random generator
	 * @param chest  The chest tile entity to populate
	 */
	public void fillChest(Random random, TileEntityChest chest) {
		for (int i = 0; i < chest.getContainerSize(); ++i) {
			// Example: Fill each slot with an explosive charge.
			chest.setItem(i, new ItemStack(Items.AMMO_CHARGE_EXPLOSIVE));
		}
	}
}
