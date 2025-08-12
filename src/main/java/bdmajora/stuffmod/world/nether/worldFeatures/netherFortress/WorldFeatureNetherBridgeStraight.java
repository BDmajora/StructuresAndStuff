package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNetherBridgeStraight extends WorldFeature {
	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeStraight(FortressBlocks blocks) {
		this.fb = blocks;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Base corridor floor (width=5, height=10, depth=19 in original)
		fillWithBlocks(world, x, y + 3, z, x + 4, y + 4, z + 18, fb.brick.id(), fb.brick.id()); // main side walls
		fillWithBlocks(world, x + 1, y + 5, z, x + 3, y + 7, z + 18, 0, 0); // air space inside top
		fillWithBlocks(world, x, y + 5, z, x, y + 5, z + 18, fb.brick.id(), fb.brick.id()); // left top beam
		fillWithBlocks(world, x + 4, y + 5, z, x + 4, y + 5, z + 18, fb.brick.id(), fb.brick.id()); // right top beam

		// Lower floor sections
		fillWithBlocks(world, x, y + 2, z, x + 4, y + 2, z + 5, fb.brick.id(), fb.brick.id()); // front floor
		fillWithBlocks(world, x, y + 2, z + 13, x + 4, y + 2, z + 18, fb.brick.id(), fb.brick.id()); // back floor
		fillWithBlocks(world, x, y, z, x + 4, y + 1, z + 3, fb.brick.id(), fb.brick.id()); // front lower base
		fillWithBlocks(world, x, y, z + 15, x + 4, y + 1, z + 18, fb.brick.id(), fb.brick.id()); // back lower base

		// Support pillars downwards (front and back edges)
		for (int dx = 0; dx <= 4; dx++) {
			for (int dz = 0; dz <= 2; dz++) {
				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + 18 - dz);
			}
		}

		// Fences (matching vanilla design)
		fillWithBlocks(world, x, y + 1, z + 1, x, y + 4, z + 1, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 3, z + 4, x, y + 4, z + 4, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 3, z + 14, x, y + 4, z + 14, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 1, z + 17, x, y + 4, z + 17, fb.fence.id(), fb.fence.id());

		fillWithBlocks(world, x + 4, y + 1, z + 1, x + 4, y + 4, z + 1, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 4, y + 3, z + 4, x + 4, y + 4, z + 4, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 4, y + 3, z + 14, x + 4, y + 4, z + 14, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 4, y + 1, z + 17, x + 4, y + 4, z + 17, fb.fence.id(), fb.fence.id());

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

	private void fillDownwards(World world, int blockId, int x, int y, int z) {
		while (y >= 0 && world.isAirBlock(x, y, z)) {
			world.setBlockAndMetadataWithNotify(x, y, z, blockId, 0);
			y--;
		}
	}
}
