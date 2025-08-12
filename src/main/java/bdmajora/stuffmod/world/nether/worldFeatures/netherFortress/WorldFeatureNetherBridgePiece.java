package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public abstract class WorldFeatureNetherBridgePiece extends WorldFeature {
	protected final FortressBlocks fb;

	protected WorldFeatureNetherBridgePiece(FortressBlocks blocks) {
		this.fb = blocks;
	}

	protected static boolean isAboveGround(int minY) {
		return minY > 10;
	}

	// Abstract method for subclasses to implement actual structure placement
	@Override
	public abstract boolean place(World world, Random random, int x, int y, int z);

	protected void fillWithBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int blockId, int meta) {
		for (int bx = minX; bx <= maxX; bx++) {
			for (int by = minY; by <= maxY; by++) {
				for (int bz = minZ; bz <= maxZ; bz++) {
					world.setBlockAndMetadataWithNotify(bx, by, bz, blockId, meta);
				}
			}
		}
	}

	protected void fillDownwards(World world, int blockId, int x, int y, int z) {
		while (y >= 0 && world.isAirBlock(x, y, z)) {
			world.setBlockAndMetadataWithNotify(x, y, z, blockId, 0);
			y--;
		}
	}

	protected void createTreasureChest(World world, int x, int y, int z, Random random) {
		world.setBlockAndMetadataWithNotify(x, y, z, 682, 0);
		// TODO: Populate loot
	}
}
