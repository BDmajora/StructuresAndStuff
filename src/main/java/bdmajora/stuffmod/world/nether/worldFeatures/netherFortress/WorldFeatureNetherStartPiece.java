//package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;
//
//import bdmajora.stuffmod.ModConfig;
//import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
//import net.minecraft.core.world.World;
//import net.minecraft.core.world.generate.feature.WorldFeature;
//
//import java.util.Random;
//
//public class WorldFeatureNetherStartPiece extends WorldFeature {
//	private final FortressBlocks fb;
//
//	public WorldFeatureNetherStartPiece(FortressBlocks blocks) {
//		this.fb = blocks;
//	}
//
//	@Override
//	public boolean place(World world, Random random, int x, int y, int z) {
//		// Base platform 13×7×13
//		fillWithBlocks(world, x, y, z, x + 12, y, z + 12, fb.brick.id(), fb.brick.id());
//
//		// Outer walls (2 blocks high above floor)
//		fillWithBlocks(world, x, y + 1, z, x + 12, y + 2, z, fb.brick.id(), fb.brick.id()); // north
//		fillWithBlocks(world, x, y + 1, z + 12, x + 12, y + 2, z + 12, fb.brick.id(), fb.brick.id()); // south
//		fillWithBlocks(world, x, y + 1, z, x, y + 2, z + 12, fb.brick.id(), fb.brick.id()); // west
//		fillWithBlocks(world, x + 12, y + 1, z, x + 12, y + 2, z + 12, fb.brick.id(), fb.brick.id()); // east
//
//		// Corner towers
//		for (int dx : new int[]{0, 12}) {
//			for (int dz : new int[]{0, 12}) {
//				fillWithBlocks(world, x + dx, y + 3, z + dz, x + dx, y + 6, z + dz, fb.brick.id(), fb.brick.id());
//			}
//		}
//
//		// Openings for corridor connections
//		clearOpening(world, x + 6, y + 1, z); // north
//		clearOpening(world, x + 6, y + 1, z + 12); // south
//		clearOpening(world, x, y + 1, z + 6); // west
//		clearOpening(world, x + 12, y + 1, z + 6); // east
//
//		// Ceiling
//		fillWithBlocks(world, x, y + 6, z, x + 12, y + 6, z + 12, fb.brick.id(), fb.brick.id());
//
//		// Optional central loot chest
//		if (random.nextInt(ModConfig.netherfort_chest_rarity) == 0) {
//			createTreasureChest(world, x + 6, y + 1, z + 6, random);
//		}
//
//		// Support pillars
//		for (int dx = 0; dx <= 12; dx++) {
//			for (int dz = 0; dz <= 12; dz++) {
//				fillDownwards(world, fb.brick.id(), x + dx, y - 1, z + dz);
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
//
//	private void clearOpening(World world, int x, int y, int z) {
//		fillWithBlocks(world, x - 1, y, z, x + 1, y + 3, z, 0, 0);
//	}
//
//	private void createTreasureChest(World world, int x, int y, int z, Random random) {
//		world.setBlockAndMetadataWithNotify(x, y, z, 682, 0);
//		// TODO: Populate loot
//	}
//}
