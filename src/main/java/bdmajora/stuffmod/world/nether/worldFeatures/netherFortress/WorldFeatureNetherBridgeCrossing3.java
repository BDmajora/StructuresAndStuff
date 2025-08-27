//package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;
//
//import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
//import net.minecraft.core.world.World;
//import net.minecraft.core.world.generate.feature.WorldFeature;
//
//import java.util.Random;
//
//public class WorldFeatureNetherBridgeCrossing3 extends WorldFeature {
//	private final FortressBlocks fb;
//
//	public WorldFeatureNetherBridgeCrossing3(FortressBlocks blocks) {
//		this.fb = blocks;
//	}
//
//	@Override
//	public boolean place(World world, Random random, int x, int y, int z) {
//		// Large 19x10x19 footprint; y=64+ (relative to y param)
//
//		// Large floor strips
//		fillWithBlocks(world, x + 7, y + 3, z, x + 11, y + 4, z + 18, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x, y + 3, z + 7, x + 18, y + 4, z + 11, fb.brick.id(), fb.brick.id());
//
//		// Hollow interior air spaces
//		fillWithBlocks(world, x + 8, y + 5, z, x + 10, y + 7, z + 18, 0, 0);
//		fillWithBlocks(world, x, y + 5, z + 8, x + 18, y + 7, z + 10, 0, 0);
//
//		// Small floor platforms on y+5 level
//		fillWithBlocks(world, x + 7, y + 5, z, x + 7, y + 5, z + 7, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 7, y + 5, z + 11, x + 7, y + 5, z + 18, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 11, y + 5, z, x + 11, y + 5, z + 7, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 11, y + 5, z + 11, x + 11, y + 5, z + 18, fb.brick.id(), fb.brick.id());
//
//		fillWithBlocks(world, x, y + 5, z + 7, x + 7, y + 5, z + 7, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 11, y + 5, z + 7, x + 18, y + 5, z + 7, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x, y + 5, z + 11, x + 7, y + 5, z + 11, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 11, y + 5, z + 11, x + 18, y + 5, z + 11, fb.brick.id(), fb.brick.id());
//
//		// Walls and platforms at y=2 and y=0-1 levels
//		fillWithBlocks(world, x + 7, y + 2, z, x + 11, y + 2, z + 5, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 7, y + 2, z + 13, x + 11, y + 2, z + 18, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 7, y, z, x + 11, y + 1, z + 3, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 7, y, z + 15, x + 11, y + 1, z + 18, fb.brick.id(), fb.brick.id());
//
//		// Support pillars downward front and back
//		for (int dx = 7; dx <= 11; dx++) {
//			for (int dz = 0; dz <= 2; dz++) {
//				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
//				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + 18 - dz);
//			}
//		}
//
//		// Side platforms at y=2
//		fillWithBlocks(world, x, y + 2, z + 7, x + 5, y + 2, z + 11, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 13, y + 2, z + 7, x + 18, y + 2, z + 11, fb.brick.id(), fb.brick.id());
//
//		// Side platforms at y=0-1
//		fillWithBlocks(world, x, y, z + 7, x + 3, y + 1, z + 11, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x + 15, y, z + 7, x + 18, y + 1, z + 11, fb.brick.id(), fb.brick.id());
//
//		// Support pillars downward left and right side
//		for (int dx = 0; dx <= 2; dx++) {
//			for (int dz = 7; dz <= 11; dz++) {
//				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
//				fillDownwards(world, fb.brick.id(), x + 18 - dx, y - 1, z + dz);
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
