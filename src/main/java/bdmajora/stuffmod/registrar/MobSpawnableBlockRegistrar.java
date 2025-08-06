package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.Util;
import bdmajora.stuffmod.blocks.ModBlockTags;

import java.util.Iterator;
import java.util.List;

public class MobSpawnableBlockRegistrar {

	public static void registerFortressGuardSkeletonSpawnBlocks() {
		// Register spawnable blocks for the Fortress Guard Skeleton
		List<String> spawn_blocks = (List) ModConfig.get("mob.fortress_guard_skeleton.spawnable_blocks");
		Iterator var2 = spawn_blocks.iterator();

		while (var2.hasNext()) {
			String key = (String) var2.next();
			ModBlockTags.SPAWNS_FORTRESS_SKELETONS.tag(Util.try_retrieve_key_or_err(key));
		}
	}
}
