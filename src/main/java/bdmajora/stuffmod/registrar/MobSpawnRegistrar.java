package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.entities.fortSkeleton.MobFortressGuardSkeleton;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.world.biome.Biomes;

public class MobSpawnRegistrar {

	public static void registerMobSpawns() {
		// Register Fortress Guard Skeleton spawn entry
		Biomes.NETHER_NETHER.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobFortressGuardSkeleton.class, ModConfig.fort_guard_spawn_weight));
	}
}
