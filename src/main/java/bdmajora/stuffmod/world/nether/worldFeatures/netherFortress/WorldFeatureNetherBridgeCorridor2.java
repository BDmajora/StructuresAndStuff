package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureNetherBridgeCorridor2 extends WorldFeature {

	private final FortressBlocks fb;

	public WorldFeatureNetherBridgeCorridor2(FortressBlocks blocks) {
		this.fb = blocks;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Floor
		fillWithBlocks(world, x, y, z, x + 4, y, z + 4, fb.brick.id(), fb.brick.id());

		// Interior air space
		fillWithBlocks(world, x, y + 2, z, x + 4, y + 5, z + 4, 0, 0);

		// Left wall + fences
		fillWithBlocks(world, x, y + 2, z, x, y + 5, z + 4, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x, y + 3, z + 1, x, y + 4, z + 1, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x, y + 3, z + 3, x, y + 4, z + 3, fb.fence.id(), fb.fence.id());

		// Right wall + partial fence
		fillWithBlocks(world, x + 4, y + 2, z, x + 4, y + 5, z, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 2, z + 4, x + 4, y + 5, z + 4, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 3, z + 4, x + 1, y + 4, z + 4, fb.fence.id(), fb.brick.id());
		fillWithBlocks(world, x + 3, y + 3, z + 4, x + 3, y + 4, z + 4, fb.fence.id(), fb.brick.id());

		// Ceiling
		fillWithBlocks(world, x, y + 6, z, x + 4, y + 6, z + 4, fb.brick.id(), fb.brick.id());

		// Optional loot chest
		if (random.nextInt(ModConfig.netherfort_chest_rarity) == 0) {
			if (random.nextBoolean()) {
				createTreasureChest(world, x + 1, y + 2, z + 2, random);
			} else {
				createTreasureChest(world, x + 2, y + 2, z + 3, random);
			}
		}

		// Support pillars downwards
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

	private void createTreasureChest(World world, int x, int y, int z, Random random) {
		world.setBlockAndMetadataWithNotify(x, y, z, 682, 0);
		// TODO: Populate loot from MapGenNetherBridge.nether_fortress_loot equivalent
	}
}
