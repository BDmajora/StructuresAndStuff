package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.StructureWrapper;
import bdmajora.stuffmod.world.WorldFeatureGenerationExtended;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;

import java.util.Random;

import static net.betterthanadventure.utils.BiomeFinder.seed;

public class WorldFeatureNetherBridgeEnd extends WorldFeatureGenerationExtended {
	private final FortressBlocks fb;
	private final int fillSeed;
	private final int rotation;

	public WorldFeatureNetherBridgeEnd(FortressBlocks blocks, int rotation) {
		this.fb = blocks;
		this.rotation = ((rotation % 360) + 360) % 360; // normalize
		this.fillSeed = Math.toIntExact(seed);

		// define unrotated bounding box (width=5, height=6, depth=8)
		setStructure(new StructureWrapper(0, 0, 0, 4, 5, 7));
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		Random randomSeed = new Random((long) fillSeed);

		// Random heights for sections along z axis at different y levels
		for (int dx = 0; dx <= 4; dx++) {
			for (int dy = 3; dy <= 4; dy++) {
				int dzHeight = randomSeed.nextInt(8);
				fillWithBlocksRot(world, x, y, z, dx, dy, 0, dx, dy, dzHeight, fb.brick.id(), fb.brick.id());
			}
		}

		int height = randomSeed.nextInt(8);
		fillWithBlocksRot(world, x, y, z, 0, 5, 0, 0, 5, height, fb.brick.id(), fb.brick.id());

		height = randomSeed.nextInt(8);
		fillWithBlocksRot(world, x, y, z, 4, 5, 0, 4, 5, height, fb.brick.id(), fb.brick.id());

		for (int dx = 0; dx <= 4; dx++) {
			int dzHeight = randomSeed.nextInt(5);
			fillWithBlocksRot(world, x, y, z, dx, 2, 0, dx, 2, dzHeight, fb.brick.id(), fb.brick.id());
		}

		for (int dx = 0; dx <= 4; dx++) {
			for (int dy = 0; dy <= 1; dy++) {
				int dzHeight = randomSeed.nextInt(3);
				fillWithBlocksRot(world, x, y, z, dx, dy, 0, dx, dy, dzHeight, fb.brick.id(), fb.brick.id());
			}
		}

		return true;
	}

	// Rotation helper
	private int[] rotate(int dx, int dz) {
		switch (rotation) {
			case 90:  return new int[]{-dz, dx};
			case 180: return new int[]{-dx, -dz};
			case 270: return new int[]{dz, -dx};
			default:  return new int[]{dx, dz};
		}
	}

	// Rotation-aware fill
	private void fillWithBlocksRot(World world, int ox, int oy, int oz,
								   int minX, int minY, int minZ,
								   int maxX, int maxY, int maxZ,
								   int blockId, int meta) {
		for (int dx = minX; dx <= maxX; dx++) {
			for (int dy = minY; dy <= maxY; dy++) {
				for (int dz = minZ; dz <= maxZ; dz++) {
					int[] r = rotate(dx, dz);
					world.setBlockAndMetadataWithNotify(
						ox + r[0], oy + dy, oz + r[1],
						blockId, meta
					);
				}
			}
		}
	}
}
