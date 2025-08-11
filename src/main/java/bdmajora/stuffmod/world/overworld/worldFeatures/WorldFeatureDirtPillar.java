package bdmajora.stuffmod.world.overworld.worldFeatures;

import bdmajora.stuffmod.world.overworld.blockPickRand.RandomPillarBlockPicker;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureDirtPillar extends WorldFeature {

	private static final int ARM_Y = 160;
	private static final int MIN_ABOVE_ARMS = 10; // how much higher the pillar should be than the arms

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Only place in the Overworld
		if (world.dimension.id != 0) {
			return false;
		}

		// Get chunk coordinates
		int chunkX = x >> 4;
		int chunkZ = z >> 4;

		// Chunk center
		int centerX = (chunkX << 4) + 8;
		int centerZ = (chunkZ << 4) + 8;

		// Find ground level at chunk center
		int groundY = getHighestSolidBlock(world, centerX, centerZ);
		if (groundY <= 0) {
			return false; // Safety check
		}

		// Random base pillar height between 15 and 40 blocks
		int height = 15 + random.nextInt(26); // 15–40 inclusive

		// Ensure pillar goes at least 10 blocks above the arms
		int minHeightNeeded = (ARM_Y + MIN_ABOVE_ARMS) - groundY;
		if (height < minHeightNeeded) {
			height = minHeightNeeded;
		}

		// Pick a random block type for both the pillar and arms
		int blockId = RandomPillarBlockPicker.getRandomBlock(random);

		// Place the pillar
		for (int i = 0; i < height; i++) {
			int blockY = groundY + i;
			world.setBlockAndMetadataWithNotify(centerX, blockY, centerZ, blockId, 0);
		}

		// Arms always at Y=160
		placeArms(world, centerX, centerZ, blockId);

		return true;
	}

	private void placeArms(World world, int centerX, int centerZ, int blockId) {
		// EAST arm
		for (int dx = 1; dx <= 7; dx++) {
			world.setBlockAndMetadataWithNotify(centerX + dx, ARM_Y, centerZ, blockId, 0);
		}
		// WEST arm
		for (int dx = 1; dx <= 8; dx++) {
			world.setBlockAndMetadataWithNotify(centerX - dx, ARM_Y, centerZ, blockId, 0);
		}
		// SOUTH arm
		for (int dz = 1; dz <= 7; dz++) {
			world.setBlockAndMetadataWithNotify(centerX, ARM_Y, centerZ + dz, blockId, 0);
		}
		// NORTH arm
		for (int dz = 1; dz <= 8; dz++) {
			world.setBlockAndMetadataWithNotify(centerX, ARM_Y, centerZ - dz, blockId, 0);
		}
	}

	private int getHighestSolidBlock(World world, int x, int z) {
		int y = world.getHeightBlocks(); // Max height
		while (y > 0 && world.isAirBlock(x, y, z)) {
			y--;
		}
		return y;
	}
}
