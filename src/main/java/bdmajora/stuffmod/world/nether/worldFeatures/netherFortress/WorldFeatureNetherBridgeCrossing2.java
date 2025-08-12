package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNetherBridgeCrossing2 extends WorldFeature {
	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeCrossing2(FortressBlocks blocks) {
		this.fb = blocks;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Floor 5x5 footprint at y level
		fillWithBlocks(world, x, y, z, x + 4, y, z + 4, fb.brick.id(), fb.brick.id());

		// Hollow interior air space from y+2 to y+5
		fillWithBlocks(world, x, y + 2, z, x + 4, y + 5, z + 4, 0, 0);

		// Walls on each side at y+2 to y+5
		fillWithBlocks(world, x, y + 2, z, x, y + 5, z, fb.brick.id(), fb.brick.id());           // West wall front
		fillWithBlocks(world, x + 4, y + 2, z, x + 4, y + 5, z, fb.brick.id(), fb.brick.id()); // East wall front
		fillWithBlocks(world, x, y + 2, z + 4, x, y + 5, z + 4, fb.brick.id(), fb.brick.id()); // West wall back
		fillWithBlocks(world, x + 4, y + 2, z + 4, x + 4, y + 5, z + 4, fb.brick.id(), fb.brick.id()); // East wall back

		// Ceiling layer at y+6
		fillWithBlocks(world, x, y + 6, z, x + 4, y + 6, z + 4, fb.brick.id(), fb.brick.id());

		// Support pillars downward under footprint
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
