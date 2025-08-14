package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;

import java.util.Random;

public class NetherFortressPlacementHelper {

	// Minimum distance in chunks (X and Z separately)
	private static final int MIN_DISTANCE_X_CHUNKS = 8;   // 128 blocks apart in X
	private static final int MIN_DISTANCE_Z_CHUNKS = 8;  // 256 blocks apart in Z (longer for N-S clearance)
	private static final int MAX_CANDIDATES = 3; // Try up to 3 candidate chunks per spacing cell

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
	 * Ensures at least one candidate per spacing cell, adds slight random offsets,
	 * avoids overlap but does not kill entire cells if first candidate fails.
	 */
	public static PlacementInfo getPlacementInfo(World world, int originChunkX, int originChunkZ) {
		int regionX = originChunkX / MIN_DISTANCE_X_CHUNKS;
		int regionZ = originChunkZ / MIN_DISTANCE_Z_CHUNKS;

		Random rand = new Random(world.getRandomSeed()
			^ ((long) regionX * 341873128712L)
			^ ((long) regionZ * 132897987541L));

		// Try a few candidate chunks in this region
		for (int attempt = 0; attempt < MAX_CANDIDATES; attempt++) {
			int candidateChunkX = regionX * MIN_DISTANCE_X_CHUNKS + rand.nextInt(MIN_DISTANCE_X_CHUNKS);
			int candidateChunkZ = regionZ * MIN_DISTANCE_Z_CHUNKS + rand.nextInt(MIN_DISTANCE_Z_CHUNKS);

			if (originChunkX == candidateChunkX && originChunkZ == candidateChunkZ) {
				int x = originChunkX * 16 + 4;
				int z = originChunkZ * 16 + 4;
				int y = 176; // Fixed Nether fortress height

				Biome biome = world.getBlockBiome(x, y, z);
				if (biome != null) { // just skip null, no snow check
					return new PlacementInfo(x, y, z, rand);
				}
			}
		}

		// None of the candidates worked, return null
		return null;
	}
}
