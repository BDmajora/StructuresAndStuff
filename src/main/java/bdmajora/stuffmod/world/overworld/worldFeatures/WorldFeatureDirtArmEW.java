package bdmajora.stuffmod.world.overworld.worldFeatures;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureDirtArmEW extends WorldFeature {

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

		// Place dirt blocks from west edge to east edge of chunk
		for (int dx = 0; dx < 16; dx++) {
			int currentX = chunkX + dx;
			world.setBlockAndMetadataWithNotify(currentX, ARM_Y, centerZ, 220, 0);
		}

		return true;
	}
}
