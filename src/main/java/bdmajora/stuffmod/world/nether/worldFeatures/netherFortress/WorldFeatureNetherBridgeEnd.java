package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

import static net.betterthanadventure.utils.BiomeFinder.seed;

public class WorldFeatureNetherBridgeEnd extends WorldFeature {
	private final FortressBlocks fb;
	private final int fillSeed;

	public WorldFeatureNetherBridgeEnd(FortressBlocks blocks) {
		this.fb = blocks;
		this.fillSeed = Math.toIntExact(seed);
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		Random randomSeed = new Random((long) fillSeed);

		// Random heights for sections along z axis at different y levels
		for (int dx = 0; dx <= 4; dx++) {
			for (int dy = 3; dy <= 4; dy++) {
				int dzHeight = randomSeed.nextInt(8);
				fillWithBlocks(world, x + dx, y + dy, z, x + dx, y + dy, z + dzHeight, fb.brick.id(), fb.brick.id());
			}
		}

		int height = randomSeed.nextInt(8);
		fillWithBlocks(world, x, y + 5, z, x, y + 5, z + height, fb.brick.id(), fb.brick.id());

		height = randomSeed.nextInt(8);
		fillWithBlocks(world, x + 4, y + 5, z, x + 4, y + 5, z + height, fb.brick.id(), fb.brick.id());

		for (int dx = 0; dx <= 4; dx++) {
			int dzHeight = randomSeed.nextInt(5);
			fillWithBlocks(world, x + dx, y + 2, z, x + dx, y + 2, z + dzHeight, fb.brick.id(), fb.brick.id());
		}

		for (int dx = 0; dx <= 4; dx++) {
			for (int dy = 0; dy <= 1; dy++) {
				int dzHeight = randomSeed.nextInt(3);
				fillWithBlocks(world, x + dx, y + dy, z, x + dx, y + dy, z + dzHeight, fb.brick.id(), fb.brick.id());
			}
		}

		return true;
	}

	private void fillWithBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int blockId, int meta) {
		for (int bx = minX; bx <= maxX; bx++) {
			for (int by = minY; by <= maxY; by++) {
				for (int bz = minZ; bz <= maxZ; bz++) {
					world.setBlockAndMetadataWithNotify(bx, by, bz, blockId, meta);
				}
			}
		}
	}
}
