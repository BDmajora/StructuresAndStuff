package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;

import java.util.Random;

public class NetherFortressPlacementHelper {

	// Minimum distance in chunks (X and Z separately)
	private static final int MIN_DISTANCE_X_CHUNKS = 8;   // 128 blocks apart in X
	private static final int MIN_DISTANCE_Z_CHUNKS = 16;  // 256 blocks apart in Z (longer for N-S clearance)

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
	 * Adds asymmetric spacing and random offset to avoid alignment and overlap.
	 */
	public static PlacementInfo getPlacementInfo(World world, int originChunkX, int originChunkZ) {
		// Determine region coords based on separate X/Z spacing
		int regionX = originChunkX / MIN_DISTANCE_X_CHUNKS;
		int regionZ = originChunkZ / MIN_DISTANCE_Z_CHUNKS;

		// Random per-region so fortresses are scattered within each spacing cell
		Random rand = new Random(world.getRandomSeed()
			^ ((long) regionX * 341873128712L)
			^ ((long) regionZ * 132897987541L));

		int candidateChunkX = regionX * MIN_DISTANCE_X_CHUNKS + rand.nextInt(MIN_DISTANCE_X_CHUNKS);
		int candidateChunkZ = regionZ * MIN_DISTANCE_Z_CHUNKS + rand.nextInt(MIN_DISTANCE_Z_CHUNKS);

		// Only generate if this chunk is the chosen one for its spacing cell
		if (originChunkX != candidateChunkX || originChunkZ != candidateChunkZ) {
			return null;
		}

		int x = originChunkX * 16 + 4;
		int z = originChunkZ * 16 + 4;
		int y = 176; // Fixed Nether fortress height (can tweak if needed)

		Biome biome = world.getBlockBiome(x, y, z);
		if (biome == null || biome.hasSurfaceSnow()) return null;

		return new PlacementInfo(x, y, z, rand);
	}
}
