package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNetherBridgeCorridor4 extends WorldFeature {
	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeCorridor4(FortressBlocks blocks) {
		this.fb = blocks;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Floor
		fillWithBlocks(world, x, y, z, x + 8, y, z + 8, fb.brick.id(), fb.brick.id());

		// Clear interior
		fillWithBlocks(world, x, y + 2, z, x + 8, y + 5, z + 8, 0, 0);

		// Ceiling (partial)
		fillWithBlocks(world, x, y + 6, z, x + 8, y + 6, z + 5, fb.brick.id(), fb.brick.id());

		// Side walls & fences
		fillWithBlocks(world, x, y + 2, z, x + 2, y + 5, z, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 2, z, x + 8, y + 5, z, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 3, z, x + 1, y + 4, z, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 7, y + 3, z, x + 7, y + 4, z, fb.fence.id(), fb.fence.id());

		// Mid floor section
		fillWithBlocks(world, x, y + 2, z + 4, x + 8, y + 2, z + 8, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 1, z + 4, x + 2, y + 2, z + 4, 0, 0);
		fillWithBlocks(world, x + 6, y + 1, z + 4, x + 7, y + 2, z + 4, 0, 0);

		// Fence along back edge
		fillWithBlocks(world, x, y + 3, z + 8, x + 8, y + 3, z + 8, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 3, z + 6, x, y + 3, z + 7, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 8, y + 3, z + 6, x + 8, y + 3, z + 7, fb.fence.id(), fb.fence.id());

		// Vertical supports
		fillWithBlocks(world, x, y + 3, z + 4, x, y + 5, z + 5, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 8, y + 3, z + 4, x + 8, y + 5, z + 5, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 3, z + 5, x + 2, y + 5, z + 5, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 3, z + 5, x + 7, y + 5, z + 5, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 4, z + 5, x + 1, y + 5, z + 5, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 7, y + 4, z + 5, x + 7, y + 5, z + 5, fb.fence.id(), fb.fence.id());

		// Support pillars downward
		for (int dx = 0; dx <= 5; dx++) {
			for (int dz = 0; dz <= 8; dz++) {
				fillDownwards(world, fb.brick.id(), x + dz, y - 1, z + dx);
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
