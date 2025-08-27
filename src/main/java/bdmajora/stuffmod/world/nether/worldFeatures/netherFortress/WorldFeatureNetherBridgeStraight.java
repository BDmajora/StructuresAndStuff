package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.StructureWrapper;
import bdmajora.stuffmod.world.WorldFeatureGenerationExtended;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureNetherBridgeStraight extends WorldFeatureGenerationExtended {
	private final FortressBlocks fb;
	private final int rotation; // 0, 90, 180, 270

	public WorldFeatureNetherBridgeStraight(FortressBlocks blocks, int rotation) {
		this.fb = blocks;
		this.rotation = ((rotation % 360) + 360) % 360; // normalize

		// define unrotated bounding box
		setStructure(new StructureWrapper(0, 0, 0, 4, 9, 18));
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Base corridor floor (width=5, height=10, depth=19 in original)
		fillWithBlocksRot(world, x, y, z, 0, 3, 0, 4, 4, 18, fb.brick.id(), fb.brick.id()); // main side walls
		fillWithBlocksRot(world, x, y, z, 1, 5, 0, 3, 7, 18, 0, 0); // air space inside top
		fillWithBlocksRot(world, x, y, z, 0, 5, 0, 0, 5, 18, fb.brick.id(), fb.brick.id()); // left top beam
		fillWithBlocksRot(world, x, y, z, 4, 5, 0, 4, 5, 18, fb.brick.id(), fb.brick.id()); // right top beam

		// Lower floor sections
		fillWithBlocksRot(world, x, y, z, 0, 2, 0, 4, 2, 5, fb.brick.id(), fb.brick.id()); // front floor
		fillWithBlocksRot(world, x, y, z, 0, 2, 13, 4, 2, 18, fb.brick.id(), fb.brick.id()); // back floor
		fillWithBlocksRot(world, x, y, z, 0, 0, 0, 4, 1, 3, fb.brick.id(), fb.brick.id()); // front lower base
		fillWithBlocksRot(world, x, y, z, 0, 0, 15, 4, 1, 18, fb.brick.id(), fb.brick.id()); // back lower base

		// Support pillars downwards (front and back edges)
		for (int dx = 0; dx <= 4; dx++) {
			for (int dz = 0; dz <= 2; dz++) {
				fillDownwardsRot(world, fb.brick.id(), x, y - 1, z, dx, dz);
				fillDownwardsRot(world, fb.brick.id(), x, y - 1, z, dx, 18 - dz);
			}
		}

		// Fences (matching vanilla design)
		fillWithBlocksRot(world, x, y, z, 0, 1, 1, 0, 4, 1, fb.fence.id(), fb.fence.id());
		fillWithBlocksRot(world, x, y, z, 0, 3, 4, 0, 4, 4, fb.fence.id(), fb.fence.id());
		fillWithBlocksRot(world, x, y, z, 0, 3, 14, 0, 4, 14, fb.fence.id(), fb.fence.id());
		fillWithBlocksRot(world, x, y, z, 0, 1, 17, 0, 4, 17, fb.fence.id(), fb.fence.id());

		fillWithBlocksRot(world, x, y, z, 4, 1, 1, 4, 4, 1, fb.fence.id(), fb.fence.id());
		fillWithBlocksRot(world, x, y, z, 4, 3, 4, 4, 4, 4, fb.fence.id(), fb.fence.id());
		fillWithBlocksRot(world, x, y, z, 4, 3, 14, 4, 4, 14, fb.fence.id(), fb.fence.id());
		fillWithBlocksRot(world, x, y, z, 4, 1, 17, 4, 4, 17, fb.fence.id(), fb.fence.id());

		return true;
	}

	// Rotates dx, dz based on the rotation setting
	private int[] rotate(int dx, int dz) {
		switch (rotation) {
			case 90:  return new int[]{-dz, dx};
			case 180: return new int[]{-dx, -dz};
			case 270: return new int[]{dz, -dx};
			default:  return new int[]{dx, dz};
		}
	}

	// Fill method that accounts for rotation
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

	// Fill downwards with rotation
	private void fillDownwardsRot(World world, int blockId, int ox, int oy, int oz, int dx, int dz) {
		int[] r = rotate(dx, dz);
		int wx = ox + r[0];
		int wz = oz + r[1];
		int wy = oy;
		while (wy >= 0 && world.isAirBlock(wx, wy, wz)) {
			world.setBlockAndMetadataWithNotify(wx, wy, wz, blockId, 0);
			wy--;
		}
	}
}
