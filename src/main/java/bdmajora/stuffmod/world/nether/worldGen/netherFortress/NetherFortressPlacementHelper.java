package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;

import java.util.Random;

public class NetherFortressPlacementHelper {

	private static final int MIN_DISTANCE_CHUNKS = 8; // 8 chunks = 128 blocks

	public static class PlacementInfo {
		public final int x, y, z;
		public final Random rand;

		public PlacementInfo(int x, int y, int z, Random rand) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.rand = rand;
		}
	}

	/**
	 * Calculates start position and RNG for Nether fortress generation in a chunk.
	 * Returns null if biome check fails or too close to previous candidate.
	 */
	public static PlacementInfo getPlacementInfo(World world, int originChunkX, int originChunkZ) {
		// Simple spacing: only run for chunks that fall on a coarse grid
		if ((originChunkX % MIN_DISTANCE_CHUNKS != 0) || (originChunkZ % MIN_DISTANCE_CHUNKS != 0)) {
			return null;
		}

		Random rand = new Random(world.getRandomSeed()
			^ ((long) originChunkX * 341873128712L)
			^ ((long) originChunkZ * 132897987541L));

		int x = originChunkX * 16 + 4;
		int z = originChunkZ * 16 + 4;
		int y = 176; // Fixed Nether fortress height

		Biome biome = world.getBlockBiome(x, y, z);
		if (biome == null || biome.hasSurfaceSnow()) return null;

		return new PlacementInfo(x, y, z, rand);
	}
}
