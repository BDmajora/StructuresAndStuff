package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.WorldFeatureGenerationExtended;
import bdmajora.stuffmod.world.StructureWrapper;
import bdmajora.stuffmod.world.PieceRotation;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureNetherBridgeEntrance extends WorldFeatureGenerationExtended {
	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeEntrance(FortressBlocks blocks) {
		this.fb = blocks;

		// Define unrotated bounding box of this feature (13×14×13)
		StructureWrapper bounds = new StructureWrapper(
			0, 0, 0,  // min corner
			12, 13, 12 // max corner
		);
		setStructure(bounds);
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Apply rotation to base coordinates
		StructureWrapper area = getStructure();
		int minX = x + area.getMinX();
		int minY = y + area.getMinY();
		int minZ = z + area.getMinZ();

		// === Base platform & hollow interior ===
		fillWithBlocks(world, x,     y + 3, z,     x + 12, y + 4,  z + 12, fb.brick.id(), fb.brick.id()); // base layer
		fillWithBlocks(world, x,     y + 5, z,     x + 12, y + 13, z + 12, 0, 0); // hollow interior

		// Side walls
		fillWithBlocks(world, x,     y + 5, z,     x + 1,  y + 12, z + 12, fb.brick.id(), fb.brick.id()); // left wall
		fillWithBlocks(world, x + 11,y + 5, z,     x + 12, y + 12, z + 12, fb.brick.id(), fb.brick.id()); // right wall

		// Rear wall segments
		fillWithBlocks(world, x + 2, y + 5, z + 11, x + 4, y + 12, z + 12, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 8, y + 5, z + 11, x + 10,y + 12, z + 12, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 5, y + 9, z + 11, x + 7, y + 12, z + 12, fb.brick.id(), fb.brick.id());

		// Front wall segments
		fillWithBlocks(world, x + 2, y + 5, z,     x + 4, y + 12, z + 1, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 8, y + 5, z,     x + 10,y + 12, z + 1, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 5, y + 9, z,     x + 7, y + 12, z + 1, fb.brick.id(), fb.brick.id());

		// Inner ceiling band
		fillWithBlocks(world, x + 2, y + 11, z + 2, x + 10, y + 12, z + 10, fb.brick.id(), fb.brick.id());

		// Center front fence
		fillWithBlocks(world, x + 5, y + 8, z, x + 7, y + 8, z, fb.fence.id(), fb.fence.id());

		// Corner fence posts & roof detail
		for (int i = 1; i <= 11; i += 2) {
			fillWithBlocks(world, x + i, y + 10, z,     x + i, y + 11, z, fb.fence.id(), fb.fence.id()); // front fence posts
			fillWithBlocks(world, x + i, y + 10, z + 12,x + i, y + 11, z + 12, fb.fence.id(), fb.fence.id()); // rear fence posts
			fillWithBlocks(world, x,     y + 10, z + i, x,     y + 11, z + i, fb.fence.id(), fb.fence.id()); // left fence posts
			fillWithBlocks(world, x + 12,y + 10, z + i, x + 12,y + 11, z + i, fb.fence.id(), fb.fence.id()); // right fence posts

			placeBlock(world, fb.brick.id(), x + i, y + 13, z);      // roof front
			placeBlock(world, fb.brick.id(), x + i, y + 13, z + 12); // roof rear
			placeBlock(world, fb.brick.id(), x,     y + 13, z + i);  // roof left
			placeBlock(world, fb.brick.id(), x + 12,y + 13, z + i);  // roof right

			placeBlock(world, fb.fence.id(), x + i + 1, y + 13, z);
			placeBlock(world, fb.fence.id(), x + i + 1, y + 13, z + 12);
			placeBlock(world, fb.fence.id(), x,         y + 13, z + i + 1);
			placeBlock(world, fb.fence.id(), x + 12,    y + 13, z + i + 1);
		}

		// Extra top corner fences
		placeBlock(world, fb.fence.id(), x,     y + 13, z);
		placeBlock(world, fb.fence.id(), x,     y + 13, z + 12);
		placeBlock(world, fb.fence.id(), x + 12,y + 13, z);
		placeBlock(world, fb.fence.id(), x + 12,y + 13, z + 12);

		// Side mid-level fence detail
		for (int i = 3; i <= 9; i += 2) {
			fillWithBlocks(world, x + 1,  y + 7, z + i, x + 1,  y + 8, z + i, fb.fence.id(), fb.fence.id());
			fillWithBlocks(world, x + 11, y + 7, z + i, x + 11, y + 8, z + i, fb.fence.id(), fb.fence.id());
		}

		// Ground floor walkways
		fillWithBlocks(world, x + 4, y + 2, z,     x + 8, y + 2, z + 12, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x,     y + 2, z + 4, x + 12, y + 2, z + 8, fb.brick.id(), fb.brick.id());

		// Outer base corners
		fillWithBlocks(world, x + 4, y, z,     x + 8, y + 1, z + 3, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 4, y, z + 9, x + 8, y + 1, z + 12, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x,     y, z + 4, x + 3, y + 1, z + 8, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 9, y, z + 4, x + 12,y + 1, z + 8, fb.brick.id(), fb.brick.id());

		// Support pillars downwards (outer edges)
		for (int dx = 4; dx <= 8; dx++) {
			for (int dz = 0; dz <= 2; dz++) {
				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + 12 - dz);
			}
		}
		for (int dx = 0; dx <= 2; dx++) {
			for (int dz = 4; dz <= 8; dz++) {
				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
				fillDownwards(world, fb.brick.id(), x + 12 - dx, y - 1, z + dz);
			}
		}

		// Central lava pool
		fillWithBlocks(world, x + 5, y + 5, z + 5, x + 7, y + 5, z + 7, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 1, z + 6, x + 6, y + 4, z + 6, 0, 0); // air shaft
		placeBlock(world, fb.brick.id(), x + 6, y, z + 6); // base
		placeBlock(world, Blocks.FLUID_LAVA_FLOWING.id(), x + 6, y + 5, z + 6);

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

	private void placeBlock(World world, int blockId, int x, int y, int z) {
		world.setBlockAndMetadataWithNotify(x, y, z, blockId, 0);
	}
}
