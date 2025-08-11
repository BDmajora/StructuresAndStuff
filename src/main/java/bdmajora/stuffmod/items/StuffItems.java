package bdmajora.stuffmod.items;

import bdmajora.stuffmod.Utils.UtilIdRegistrar;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;
import bdmajora.stuffmod.StuffMod;

public class StuffItems {

	public static Item recordPigStep;

	public static void register() {
		recordPigStep = new ItemBuilder(StuffMod.MOD_ID)
			.setStackSize(1)
			.build(new ItemDiscMusicAccessor(
				"record.Disc",
				UtilIdRegistrar.nextIdItem(),
				UtilIdRegistrar.nextIdItem(),
				StuffMod.MOD_ID + ":disc_pig_step",
				"Lena Raine",
				"minecraft:disc_pig_step"
			));
	}
}
