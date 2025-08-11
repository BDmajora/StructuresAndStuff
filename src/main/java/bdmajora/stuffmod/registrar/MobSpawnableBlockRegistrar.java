package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.Utils.Util;
import bdmajora.stuffmod.blocks.ModBlockTags;

import java.util.Arrays;
import java.util.List;

public class MobSpawnableBlockRegistrar {

	public static void registerFortressGuardSkeletonSpawnBlocks() {
		List<String> spawn_blocks = Arrays.asList(
			"stuffmod:block/fortress_brick",
			"stuffmod:block/fortress_brick_stairs",
			"stuffmod:block/fortress_brick_fence"
			// Add more hardcoded blocks here as needed
		);

		for (String key : spawn_blocks) {
			ModBlockTags.SPAWNS_FORTRESS_SKELETONS.tag(Util.try_retrieve_key_or_err(key));
		}
	}
}
