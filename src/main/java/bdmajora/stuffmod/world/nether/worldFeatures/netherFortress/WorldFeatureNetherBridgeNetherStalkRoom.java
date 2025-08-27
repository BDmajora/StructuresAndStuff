//package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;
//
//import bdmajora.stuffmod.ModConfig;
//import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
//import net.minecraft.core.block.Blocks;
//import net.minecraft.core.world.World;
//import net.minecraft.core.world.generate.feature.WorldFeature;
//
//import java.util.Random;
//
//public class WorldFeatureNetherBridgeNetherStalkRoom extends WorldFeature {
//	private final FortressBlocks fb;
//
//	public WorldFeatureNetherBridgeNetherStalkRoom(FortressBlocks blocks) {
//		this.fb = blocks;
//	}
//
//	@Override
//	public boolean place(World world, Random random, int x, int y, int z) {
//		// Dimensions from decompiled class: 13 wide × 14 tall × 13 deep
//		int width = 13;
//		int height = 14;
//		int depth = 13;
//
//		// --- Floor layer (y+3 to y+4 solid brick, hollow interior above) ---
//		fillWithBlocks(world, x, y + 3, z, x + width - 1, y + 4, z + depth - 1, fb.brick.id(), fb.brick.id());
//		fillWithBlocks(world, x, y + 5, z, x + width - 1, y + height - 1, z + depth - 1, 0, 0); // interior air
//
//		// --- Outer wall segments ---
//		fillWithBlocks(world, x, y + 5, z, x + 1, y + height - 2, z + depth - 1, fb.brick.id(), fb.brick.id());  // Left wall
//		fillWithBlocks(world, x + width - 2, y + 5, z, x + width - 1, y + height - 2, z + depth - 1, fb.brick.id(), fb.brick.id()); // Right wall
//
//		fillWithBlocks(world, x + 2, y + 5, z + depth - 2, x + 4, y + height - 2, z + depth - 1, fb.brick.id(), fb.brick.id()); // Rear left wall
//		fillWithBlocks(world, x + 8, y + 5, z + depth - 2, x + 10, y + height - 2, z + depth - 1, fb.brick.id(), fb.brick.id()); // Rear right wall
//		fillWithBlocks(world, x + 5, y + 9, z + depth - 2, x + 7, y + height - 2, z + depth - 1, fb.brick.id(), fb.brick.id()); // Rear middle pillar
//
//		fillWithBlocks(world, x + 2, y + 5, z, x + 4, y + height - 2, z + 1, fb.brick.id(), fb.brick.id()); // Front left wall
//		fillWithBlocks(world, x + 8, y + 5, z, x + 10, y + height - 2, z + 1, fb.brick.id(), fb.brick.id()); // Front right wall
//		fillWithBlocks(world, x + 5, y + 9, z, x + 7, y + height - 2, z + 1, fb.brick.id(), fb.brick.id()); // Front middle pillar
//
//		// --- Fence decorations around upper edges ---
//		for (int col = 1; col <= width - 2; col += 2) {
//			fillWithBlocks(world, x + col, y + 10, z, x + col, y + 11, z, fb.fence.id(), fb.fence.id());
//			fillWithBlocks(world, x + col, y + 10, z + depth - 1, x + col, y + 11, z + depth - 1, fb.fence.id(), fb.fence.id());
//			fillWithBlocks(world, x, y + 10, z + col, x, y + 11, z + col, fb.fence.id(), fb.fence.id());
//			fillWithBlocks(world, x + width - 1, y + 10, z + col, x + width - 1, y + 11, z + col, fb.fence.id(), fb.fence.id());
//		}
//
//		// --- Soul sand crop beds ---
//		fillWithBlocks(world, x + 3, y + 4, z + 4, x + 4, y + 4, z + 8, Blocks.SOULSAND.id(), Blocks.SOULSAND.id());
//		fillWithBlocks(world, x + 8, y + 4, z + 4, x + 9, y + 4, z + 8, Blocks.SOULSAND.id(), Blocks.SOULSAND.id());
//
//		// Nether wart / placeholder mushrooms
//		fillWithBlocks(world, x + 3, y + 5, z + 4, x + 4, y + 5, z + 8, Blocks.MUSHROOM_RED.id(), Blocks.MUSHROOM_RED.id());
//		fillWithBlocks(world, x + 8, y + 5, z + 4, x + 9, y + 5, z + 8, Blocks.MUSHROOM_RED.id(), Blocks.MUSHROOM_RED.id());
//
//		// --- Central stairs (rising from z=4 to z=10) ---
//		int stairMeta = 3; // Facing north-south depending on layout
//		for (int sy = 0; sy <= 6; sy++) {
//			int stairZ = z + 4 + sy;
//			for (int sx = x + 5; sx <= x + 7; sx++) {
//				world.setBlockAndMetadataWithNotify(sx, y + 5 + sy, stairZ, fb.stair.id(), stairMeta);
//			}
//		}
//
//		// --- Base support pillars ---
//		for (int dx = 0; dx < width; dx++) {
//			for (int dz = 0; dz < depth; dz++) {
//				fillDownwards(world, fb.brick.id(), x + dx, y + 2, z + dz);
//			}
//		}
//
//		// Optional loot chest
//		if (random.nextInt(ModConfig.netherfort_chest_rarity) == 0) {
//			int chestX = x + 6;
//			int chestZ = z + 6;
//			createTreasureChest(world, chestX, y + 5, chestZ, random);
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
//	private void createTreasureChest(World world, int x, int y, int z, Random random) {
//		world.setBlockAndMetadataWithNotify(x, y, z, 682, 0);
//		// TODO: Add loot table population
//	}
//}
