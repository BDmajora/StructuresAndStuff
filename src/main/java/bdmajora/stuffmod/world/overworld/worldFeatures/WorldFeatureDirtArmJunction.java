package bdmajora.stuffmod.world.overworld.worldFeatures;

import bdmajora.stuffmod.world.overworld.blockPickRand.RandomPillarBlockPicker;
import net.minecraft.core.world.World;

import java.util.Random;

/**
 * Places a three-way junction where bridges or pillars connect from three directions.
 * Rotation determines which three directions connect:
 * 0 = North, South, East
 * 1 = North, South, West
 * 2 = North, East, West
 * 3 = South, East, West
 */
public class WorldFeatureDirtArmJunction {

	private static final int METADATA = 0;
	private static final int ARM_Y = 160;

	private final int rotation;

	/**
	 * @param rotation junction orientation (0 to 3) indicating which 3 directions connect
	 */
	public WorldFeatureDirtArmJunction(int rotation) {
		this.rotation = rotation;
	}

	/**
	 * Places the junction in the world at (x, y, z).
	 * x,z should be chunk center coordinates (chunkX << 4 + 8).
	 */
	public boolean place(World world, Random random, int x, int y, int z) {
		// Pick a single block type for the whole junction
		int blockId = RandomPillarBlockPicker.getRandomBlock(random);

		// Place center block as the main junction node
		placeBlock(world, x, y, z, blockId);

		// Place arms in the three connected directions based on rotation
		switch (rotation) {
			case 0: // North, South, East
				placeArmNorth(world, x, y, z, blockId);
				placeArmSouth(world, x, y, z, blockId);
				placeArmEast(world, x, y, z, blockId);
				break;
			case 1: // North, South, West
				placeArmNorth(world, x, y, z, blockId);
				placeArmSouth(world, x, y, z, blockId);
				placeArmWest(world, x, y, z, blockId);
				break;
			case 2: // North, East, West
				placeArmNorth(world, x, y, z, blockId);
				placeArmEast(world, x, y, z, blockId);
				placeArmWest(world, x, y, z, blockId);
				break;
			case 3: // South, East, West
				placeArmSouth(world, x, y, z, blockId);
				placeArmEast(world, x, y, z, blockId);
				placeArmWest(world, x, y, z, blockId);
				break;
			default:
				// Invalid rotation - do nothing
				return false;
		}

		return true;
	}

	private void placeBlock(World world, int x, int y, int z, int blockId) {
		world.setBlockAndMetadataWithNotify(x, y, z, blockId, METADATA);
	}

	private void placeArmNorth(World world, int x, int y, int z, int blockId) {
		for (int dz = 1; dz <= 7; dz++) {
			world.setBlockAndMetadataWithNotify(x, y, z - dz, blockId, METADATA);
		}
	}

	private void placeArmSouth(World world, int x, int y, int z, int blockId) {
		for (int dz = 1; dz <= 7; dz++) {
			world.setBlockAndMetadataWithNotify(x, y, z + dz, blockId, METADATA);
		}
	}

	private void placeArmEast(World world, int x, int y, int z, int blockId) {
		for (int dx = 1; dx <= 7; dx++) {
			world.setBlockAndMetadataWithNotify(x + dx, y, z, blockId, METADATA);
		}
	}

	private void placeArmWest(World world, int x, int y, int z, int blockId) {
		for (int dx = 1; dx <= 7; dx++) {
			world.setBlockAndMetadataWithNotify(x - dx, y, z, blockId, METADATA);
		}
	}
}
