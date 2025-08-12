package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeEntrance;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeStraight;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeEnd;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

import java.util.Random;

public class NetherFortressGenerator extends LargeStructureGenerator {

	// Use the static default FortressBlocks instance
	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;

	public NetherFortressGenerator(int range) {
		setRange(range);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		Random rand = new Random(world.getRandomSeed()
			^ ((long) originChunkX * 341873128712L)
			^ ((long) originChunkZ * 132897987541L));

		// Pick the start position in block coords
		int x = originChunkX * 16 + 4;
		int z = originChunkZ * 16 + 4;
		int y = findGroundLevel(world, x, z);

		// Biome safety check (avoid snowy biomes, though Nether usually won't have)
		Biome biome = world.getBlockBiome(x, y, z);
		if (biome.hasSurfaceSnow()) return;

		// Step 1: Entrance
		new WorldFeatureNetherBridgeEntrance(fortressBlocks).place(world, rand, x, y, z);

		// Step 2: Straight Bridge Piece (offset by 13 blocks forward from entrance)
		int bridgeX = x;
		int bridgeZ = z + 13; // Assuming forward in +Z direction
		int bridgeY = findGroundLevel(world, bridgeX, bridgeZ);
		new WorldFeatureNetherBridgeStraight(fortressBlocks).place(world, rand, bridgeX, bridgeY, bridgeZ);

		// Step 3: Bridge End (offset by another 13 blocks forward from piece)
		int endX = bridgeX;
		int endZ = bridgeZ + 13;
		int endY = findGroundLevel(world, endX, endZ);
		new WorldFeatureNetherBridgeEnd(fortressBlocks).place(world, rand, endX, endY, endZ);
	}

	private int findGroundLevel(World world, int x, int z) {
		// Scan downward from top Nether terrain limit until solid ground is found
		for (int y = 120; y > 30; y--) {
			if (!world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z)) {
				return y + 1;
			}
		}
		return 64; // Fallback
	}
}
