package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.entities.blaze.MobBlaze;
import bdmajora.stuffmod.entities.caveSpider.MobCaveSpider;
import bdmajora.stuffmod.entities.fortSkeleton.MobFortressGuardSkeleton;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;


public class MobInfoRegistrar {

	public static void registerMobInfo() {
		// Register MobCaveSpider info
		MobInfoRegistry.register(MobCaveSpider.class, "mob.stuffmod.cave_spider.name", "mob.stuffmod.cave_spider.desc",
			ModConfig.cave_spider_max_health, ModConfig.cave_spider_score,
			new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(Items.STRING.getDefaultStack(), 0.66F, 1, 2)});

		// Register MobFortressGuardSkeleton info
		MobInfoRegistry.register(MobFortressGuardSkeleton.class, "mob.stuffmod.fort_skeleton.name", "mob.stuffmod.fort_skeleton.desc",
			ModConfig.fort_skeleton_max_health, ModConfig.fort_skeleton_score,
			new MobInfoRegistry.MobDrop[]{
				new MobInfoRegistry.MobDrop(new ItemStack(Items.BONE), 0.66F, 1, 2),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.AMMO_ARROW), 0.66F, 1, 2),
				new MobInfoRegistry.MobDrop(new ItemStack(Items.COAL), 0.5F, 0, 1)
			});

		// Register MobBlaze info
		MobInfoRegistry.register(MobBlaze.class, "mob.stuffmod.blaze.name", "mob.stuffmod.blaze.desc",
			ModConfig.blaze_max_health, ModConfig.blaze_score,
			new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(Blocks.FIRE.asItem().getDefaultStack(), 0.66F, 1, 2)});
	}
}
