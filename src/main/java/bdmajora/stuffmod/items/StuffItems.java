package bdmajora.stuffmod.items;

import bdmajora.stuffmod.UtilIdRegistrar;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

import static bdmajora.stuffmod.StuffMod.MOD_ID;

public class StuffItems {
	// Items

	//Music Discs
	public static Item recordPigStep = recordPigStep = new ItemBuilder(MOD_ID)
		.setStackSize(1)
		.build(new ItemDiscMusicAccessor("record.Disc", UtilIdRegistrar.nextIdItem(), UtilIdRegistrar.nextIdItem(), "disc_pig_step", "Lena Raine", "minecraft" + ":disc_pig_step"));

	public static void register() {
	}
}
