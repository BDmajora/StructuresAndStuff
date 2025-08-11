package bdmajora.stuffmod.world.overworld.worldFeatures;

import bdmajora.stuffmod.world.overworld.blockPickRand.RandomPillarBlockPicker;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

/**
 * Places an L-shaped arm (right angle) at ARM_Y level.
 * The arm extends from the chunk center to edges along two perpendicular directions.
 * Rotation values:
 * 0 = East then South
 * 1 = South then West
 * 2 = West then North
 * 3 = North then East
 */
public class WorldFeatureDirtArmTurn extends WorldFeature {

	private static final int ARM_Y = 160;

	// Rotation: 0-3 clockwise steps
	private final int rotation;

	public WorldFeatureDirtArmTurn(int rotation) {
		if (rotation < 0 || rotation > 3)
			throw new IllegalArgumentException("Rotation must be 0,1,2 or 3");
		this.rotation = rotation;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		if (world.dimension.id != 0) {
			return false; // Only in Overworld
		}

		int chunkX = (x >> 4) << 4;
		int chunkZ = (z >> 4) << 4;
		int centerX = chunkX + 8;
		int centerZ = chunkZ + 8;

		// Pick a random block ID for the entire L-shape
		int blockId = RandomPillarBlockPicker.getRandomBlock(random);

		// Based on rotation, place the arms
		switch (rotation) {
			case 0: // East then South
				for (int dx = 0; dx < 8; dx++) {
					world.setBlockAndMetadataWithNotify(centerX + dx, ARM_Y, centerZ, blockId, 0);
				}
				for (int dz = 0; dz < 8; dz++) {
					world.setBlockAndMetadataWithNotify(centerX, ARM_Y, centerZ + dz, blockId, 0);
				}
				break;

			case 1: // South then West
				for (int dz = 0; dz < 8; dz++) {
					world.setBlockAndMetadataWithNotify(centerX, ARM_Y, centerZ + dz, blockId, 0);
				}
				for (int dx = 0; dx < 8; dx++) {
					world.setBlockAndMetadataWithNotify(centerX - dx, ARM_Y, centerZ, blockId, 0);
				}
				break;

			case 2: // West then North
				for (int dx = 0; dx < 8; dx++) {
					world.setBlockAndMetadataWithNotify(centerX - dx, ARM_Y, centerZ, blockId, 0);
				}
				for (int dz = 0; dz < 8; dz++) {
					world.setBlockAndMetadataWithNotify(centerX, ARM_Y, centerZ - dz, blockId, 0);
				}
				break;

			case 3: // North then East
				for (int dz = 0; dz < 8; dz++) {
					world.setBlockAndMetadataWithNotify(centerX, ARM_Y, centerZ - dz, blockId, 0);
				}
				for (int dx = 0; dx < 8; dx++) {
					world.setBlockAndMetadataWithNotify(centerX + dx, ARM_Y, centerZ, blockId, 0);
				}
				break;
		}

		return true;
	}
}
