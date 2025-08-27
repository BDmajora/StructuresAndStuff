package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.StructureWrapper;
import bdmajora.stuffmod.world.WorldFeatureGenerationExtended;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureNetherBridgeCrossing extends WorldFeatureGenerationExtended {
	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeCrossing(FortressBlocks blocks) {
		this.fb = blocks;

		// define unrotated bounding box (width=7, height=8, depth=7)
		setStructure(new StructureWrapper(0, 0, 0, 6, 7, 6));
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Floor 7x7
		fillWithBlocks(world, x, y, z, x + 6, y, z + 6, fb.brick.id(), fb.brick.id());

		// Add sub-floor layer at y+1 so walls sit flush with interior floor
		fillWithBlocks(world, x, y + 1, z, x + 6, y + 1, z + 6, fb.brick.id(), fb.brick.id());

		// Interior air space hollowed from y+2 to y+7
		fillWithBlocks(world, x, y + 2, z, x + 6, y + 7, z + 6, 0, 0);

		// Walls
		fillWithBlocks(world, x, y + 2, z, x + 1, y + 6, z, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x, y + 2, z + 6, x + 1, y + 6, z + 6, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 5, y + 2, z, x + 6, y + 6, z, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 5, y + 2, z + 6, x + 6, y + 6, z + 6, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x, y + 2, z, x, y + 6, z + 1, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x, y + 2, z + 5, x, y + 6, z + 6, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 2, z, x + 6, y + 6, z + 1, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 2, z + 5, x + 6, y + 6, z + 6, fb.brick.id(), fb.brick.id());

		// Fence decorations on stairs and edges
		fillWithBlocks(world, x + 2, y + 5, z, x + 4, y + 5, z, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 2, y + 6, z, x + 4, y + 6, z, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 2, y + 5, z + 6, x + 4, y + 5, z + 6, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 2, y + 6, z + 6, x + 4, y + 6, z + 6, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x, y + 5, z + 2, x, y + 5, z + 4, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 6, z + 2, x, y + 6, z + 4, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 5, z + 2, x + 6, y + 5, z + 4, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 6, y + 6, z + 2, x + 6, y + 6, z + 4, fb.brick.id(), fb.brick.id());

		// Support pillars downwards (start at y so they connect to floor)
		for (int dx = 0; dx <= 6; dx++) {
			for (int dz = 0; dz <= 6; dz++) {
				fillDownwards(world, fb.brick.id(), x + dx, y, z + dz);
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
