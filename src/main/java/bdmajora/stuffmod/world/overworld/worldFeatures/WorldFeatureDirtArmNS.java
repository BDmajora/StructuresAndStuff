package bdmajora.stuffmod.world.overworld.worldFeatures;

import bdmajora.stuffmod.world.overworld.blockPickRand.RandomPillarBlockPicker;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureDirtArmNS extends WorldFeature {

	private static final int ARM_Y = 160;

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Only place in the Overworld
		if (world.dimension.id != 0) {
			return false;
		}

		// Get chunk center
		int chunkX = (x >> 4) << 4; // chunk origin X
		int chunkZ = (z >> 4) << 4; // chunk origin Z
		int centerX = chunkX + 8;
		int centerZ = chunkZ + 8;

		// Pick a random block ID for the entire arm
		int blockId = RandomPillarBlockPicker.getRandomBlock(random);

		// Place blocks from north edge to south edge of chunk
		for (int dz = 0; dz < 16; dz++) {
			int currentZ = chunkZ + dz;
			world.setBlockAndMetadataWithNotify(centerX, ARM_Y, currentZ, blockId, 0);
		}

		return true;
	}
}
