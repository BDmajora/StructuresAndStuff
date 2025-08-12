package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNetherBridgeCorridor5 extends WorldFeature {
	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeCorridor5(FortressBlocks blocks) {
		this.fb = blocks;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Floor (5×5)
		fillWithBlocks(world, x, y, z, x + 4, y, z + 4, fb.brick.id(), fb.brick.id());

		// Interior air space (hollow middle)
		fillWithBlocks(world, x, y + 2, z, x + 4, y + 5, z + 4, 0, 0);

		// Left wall (full brick)
		fillWithBlocks(world, x, y + 2, z, x, y + 5, z + 4, fb.brick.id(), fb.brick.id());

		// Right wall (full brick)
		fillWithBlocks(world, x + 4, y + 2, z, x + 4, y + 5, z + 4, fb.brick.id(), fb.brick.id());

		// Fences on left wall
		fillWithBlocks(world, x, y + 3, z + 1, x, y + 4, z + 1, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 3, z + 3, x, y + 4, z + 3, fb.fence.id(), fb.fence.id());

		// Fences on right wall
		fillWithBlocks(world, x + 4, y + 3, z + 1, x + 4, y + 4, z + 1, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 4, y + 3, z + 3, x + 4, y + 4, z + 3, fb.fence.id(), fb.fence.id());

		// Ceiling
		fillWithBlocks(world, x, y + 6, z, x + 4, y + 6, z + 4, fb.brick.id(), fb.brick.id());

		// Support pillars downwards
		for (int dx = 0; dx <= 4; dx++) {
			for (int dz = 0; dz <= 4; dz++) {
				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
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

	private void fillDownwards(World world, int blockId, int x, int y, int z) {
		while (y >= 0 && world.isAirBlock(x, y, z)) {
			world.setBlockAndMetadataWithNotify(x, y, z, blockId, 0);
			y--;
		}
	}
}
