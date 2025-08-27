//package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;
//
//import bdmajora.stuffmod.ModConfig;
//import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
//import net.minecraft.core.world.World;
//import net.minecraft.core.world.generate.feature.WorldFeature;
//
//import java.util.Random;
//
//public class WorldFeatureNetherBridgeCorridor3 extends WorldFeature {
//	private final FortressBlocks fb;
//
//	public WorldFeatureNetherBridgeCorridor3(FortressBlocks blocks) {
//		this.fb = blocks;
//	}
//
//	@Override
//	public boolean place(World world, Random random, int x, int y, int z) {
//		int stairMeta = 2; // original used getMetadataWithOffset(..., 2)
//
//		for (int i = 0; i <= 9; i++) {
//			int minHeight = Math.max(1, 7 - i);
//			int maxHeight = Math.min(Math.max(minHeight + 5, 14 - i), 13);
//
//			// Floor
//			fillWithBlocks(world, x, y, z + i, x + 4, y + minHeight, z + i, fb.brick.id(), fb.brick.id());
//
//			// Interior air space
//			fillWithBlocks(world, x + 1, y + minHeight + 1, z + i, x + 3, y + maxHeight - 1, z + i, 0, 0);
//
//			// Stairs (only up to i = 6)
//			if (i <= 6) {
//				world.setBlockAndMetadataWithNotify(x + 1, y + minHeight + 1, z + i, fb.stair.id(), stairMeta);
//				world.setBlockAndMetadataWithNotify(x + 2, y + minHeight + 1, z + i, fb.stair.id(), stairMeta);
//				world.setBlockAndMetadataWithNotify(x + 3, y + minHeight + 1, z + i, fb.stair.id(), stairMeta);
//			}
//
//			// Ceiling
//			fillWithBlocks(world, x, y + maxHeight, z + i, x + 4, y + maxHeight, z + i, fb.brick.id(), fb.brick.id());
//
//			// Walls
//			fillWithBlocks(world, x, y + minHeight + 1, z + i, x, y + maxHeight - 1, z + i, fb.brick.id(), fb.brick.id());
//			fillWithBlocks(world, x + 4, y + minHeight + 1, z + i, x + 4, y + maxHeight - 1, z + i, fb.brick.id(), fb.brick.id());
//
//			// Fences every other segment
//			if ((i & 1) == 0) {
//				fillWithBlocks(world, x, y + minHeight + 2, z + i, x, y + minHeight + 3, z + i, fb.fence.id(), fb.fence.id());
//				fillWithBlocks(world, x + 4, y + minHeight + 2, z + i, x + 4, y + minHeight + 3, z + i, fb.fence.id(), fb.fence.id());
//			}
//
//			// Pillars downwards for support
//			for (int j = 0; j <= 4; j++) {
//				fillDownwards(world, fb.brick.id(), x + j, y - 1, z + i);
//			}
//		}
//
//		return true;
//	}
//
//	private void fillWithBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int blockId, int meta) {
//		for (int bx = minX; bx <= maxX; bx++) {
//			for (int by = minY; by <= maxY; by++) {
//				for (int bz = minZ; bz <= maxZ; bz++) {
//					world.setBlockAndMetadataWithNotify(bx, by, bz, blockId, meta);
//				}
//			}
//		}
//	}
//
//	private void fillDownwards(World world, int blockId, int x, int y, int z) {
//		while (y >= 0 && world.isAirBlock(x, y, z)) {
//			world.setBlockAndMetadataWithNotify(x, y, z, blockId, 0);
//			y--;
//		}
//	}
//}
