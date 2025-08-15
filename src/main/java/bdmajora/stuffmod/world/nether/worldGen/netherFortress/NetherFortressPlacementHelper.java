package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NetherFortressPlacementHelper {

	// How often to roll for a fortress (smaller = more frequent)
	private static final int CANDIDATE_DISTANCE_CHUNKS = 8; // 128 blocks
	// Minimum distance between two fortresses (in chunks)
	private static final int MIN_GAP_CHUNKS = 24; // 384 blocks

	// Store generated fortress chunk coords
	private static final Set<int[]> GENERATED_LOCATIONS = new HashSet<>();

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

	public static boolean shouldGenerateHere(World world, int originChunkX, int originChunkZ) {
		PlacementInfo info = getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return false;

		// Check for proximity to existing fortress locations
		for (int[] coords : GENERATED_LOCATIONS) {
			int dx = coords[0] - originChunkX;
			int dz = coords[1] - originChunkZ;
			if (Math.abs(dx) < MIN_GAP_CHUNKS && Math.abs(dz) < MIN_GAP_CHUNKS) {
				return false; // too close to another fortress
			}
		}

		// Store this fortress location
		GENERATED_LOCATIONS.add(new int[]{originChunkX, originChunkZ});
		return true;
	}

	public static PlacementInfo getPlacementInfo(World world, int originChunkX, int originChunkZ) {
		// Determine region coords
		int regionX = originChunkX / CANDIDATE_DISTANCE_CHUNKS;
		int regionZ = originChunkZ / CANDIDATE_DISTANCE_CHUNKS;

		// Deterministic random for this region
		Random rand = new Random(world.getRandomSeed()
			^ ((long) regionX * 341873128712L)
			^ ((long) regionZ * 132897987541L));

		// Pick exactly 1 candidate position inside this region
		int candidateChunkX = regionX * CANDIDATE_DISTANCE_CHUNKS + rand.nextInt(CANDIDATE_DISTANCE_CHUNKS);
		int candidateChunkZ = regionZ * CANDIDATE_DISTANCE_CHUNKS + rand.nextInt(CANDIDATE_DISTANCE_CHUNKS);

		if (originChunkX == candidateChunkX && originChunkZ == candidateChunkZ) {
			int x = originChunkX * 16 + 4;
			int z = originChunkZ * 16 + 4;
			int y = 176; // Fixed height

			Biome biome = world.getBlockBiome(x, y, z);
			if (biome != null) {
				return new PlacementInfo(x, y, z, rand);
			}
		}
		return null;
	}
}
