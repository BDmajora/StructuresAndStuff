package bdmajora.stuffmod.world.nether.worldFeatures.netherFortress;

import bdmajora.stuffmod.world.StructureWrapper;
import bdmajora.stuffmod.world.WorldFeatureGenerationExtended;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureNetherBridgeThrone extends WorldFeatureGenerationExtended {
	private final FortressBlocks fb;
	private boolean hasSpawner;

	public WorldFeatureNetherBridgeThrone(FortressBlocks blocks) {
		this.fb = blocks;

		// Define unrotated bounding box of this feature (7x9x9: x=0..6, y=0..8, z=0..8)
		setStructure(new StructureWrapper(0, 0, 0, 6, 8, 8));
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		// Clear interior space
		fillWithBlocks(world, x, y + 2, z, x + 6, y + 7, z + 7, 0, 0);

		// Floor & stepped throne platform
		fillWithBlocks(world, x + 1, y, z, x + 5, y + 1, z + 7, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 2, z + 1, x + 5, y + 2, z + 7, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 3, z + 2, x + 5, y + 3, z + 7, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 4, z + 3, x + 5, y + 4, z + 7, fb.brick.id(), fb.brick.id());

		// Side pillars
		fillWithBlocks(world, x + 1, y + 2, z, x + 1, y + 4, z + 2, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 5, y + 2, z, x + 5, y + 4, z + 2, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 1, y + 5, z + 2, x + 1, y + 5, z + 3, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 5, y + 5, z + 2, x + 5, y + 5, z + 3, fb.brick.id(), fb.brick.id());

		// Rear wall
		fillWithBlocks(world, x, y + 5, z + 3, x, y + 5, z + 8, fb.brick.id(), fb.brick.id());
		fillWithBlocks(world, x + 6, y + 5, z + 3, x + 6, y + 5, z + 8, fb.brick.id(), fb.brick.id());

		// Throne fence details
		world.setBlockAndMetadataWithNotify(x + 1, y + 6, z + 3, fb.fence.id(), 0);
		fillWithBlocks(world, x + 1, y + 5, z + 8, x + 5, y + 5, z + 8, fb.brick.id(), fb.brick.id());
		world.setBlockAndMetadataWithNotify(x + 5, y + 6, z + 3, fb.fence.id(), 0);
		fillWithBlocks(world, x, y + 6, z + 3, x, y + 6, z + 8, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 2, y + 8, z + 8, x + 4, y + 8, z + 8, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 6, y + 6, z + 3, x + 6, y + 6, z + 8, fb.fence.id(), fb.fence.id());
		fillWithBlocks(world, x + 1, y + 6, z + 8, x + 5, y + 7, z + 8, fb.fence.id(), fb.fence.id());

		// Mob spawner (only once)
		if (!hasSpawner) {
			hasSpawner = true;
			int spawnerX = x + 3;
			int spawnerY = y + 5;
			int spawnerZ = z + 3;
			world.setBlockWithNotify(spawnerX, spawnerY, spawnerZ, Blocks.MOBSPAWNER.id());
			TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(spawnerX, spawnerY, spawnerZ);
			if (spawner != null) {
				spawner.setMobId("Blaze");
			}
		}

		// Support pillars downwards
		for (int dx = 0; dx <= 6; dx++) {
			for (int dz = 0; dz <= 6; dz++) {
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
