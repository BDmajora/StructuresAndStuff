package bdmajora.stuffmod.world.overworld.worldGen;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.overworld.worldFeatures.WorldFeatureDirtArmJunction;
import bdmajora.stuffmod.world.overworld.worldFeatures.WorldFeatureDirtArmNS;
import bdmajora.stuffmod.world.overworld.worldFeatures.WorldFeatureDirtArmTurn;
import bdmajora.stuffmod.world.overworld.worldFeatures.WorldFeatureDirtPillar;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DirtPillarGenerator extends LargeStructureGenerator {

	private static final int CHANCE_DENOMINATOR = 4; // 25% chance
	private static final int MAX_PILLARS_PER_GENERATE = 5;
	private static final int MAX_CHECKS_PER_CHUNK = 2;

	private static final int ARM_Y = 160;
	private int pillarsGenerated;

	// Map tracking number of times each chunk was checked per world
	// Values: 0..MAX_CHECKS for normal, -1 = pillar present, -2 = bridge present
	private static final Map<World, Map<Long, Integer>> worldPillarChecks = new HashMap<>();

	public DirtPillarGenerator() {
		setRange(8);
	}

	public DirtPillarGenerator(int range) {
		setRange(range);
	}

	public void setRange(int range) {
		if (range < 0) throw new IllegalArgumentException("Range cannot be negative");
		this.range = range;
	}

	@Override
	public void generate(net.minecraft.core.world.chunk.provider.IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		pillarsGenerated = 0;
		worldPillarChecks.putIfAbsent(world, new HashMap<>());
		Map<Long, Integer> pillarChecks = worldPillarChecks.get(world);

		long baseSeed = world.getRandomSeed();

		// deterministic RNG for this origin chunk (keeps behavior reproducible)
		Random rand = new Random(baseSeed ^ ((long) originChunkX * 341873128712L) ^ ((long) originChunkZ * 132897987541L));

		// respect per-chunk check limit like before for the origin
		long originKey = chunkKey(originChunkX, originChunkZ);
		int checks = pillarChecks.getOrDefault(originKey, 0);
		if (checks >= MAX_CHECKS_PER_CHUNK) return;
		pillarChecks.put(originKey, checks + 1);

		// keep the same spawn-chance gate (CHANCE_DENOMINATOR)
		if (rand.nextInt(CHANCE_DENOMINATOR) != 0) {
			return;
		}

		// Decide how many pillars this single connected structure will have (5-7)
		int targetPillars = 5 + rand.nextInt(3);
		int placedPillars = 0;

		// Place the first pillar in the origin chunk (center-ish)
		int x = originChunkX * 16 + 8;
		int z = originChunkZ * 16 + 8;
		int y = world.getHeightValue(x, z);

		Biome biome = world.getBlockBiome(x, y, z);
		if (biome.hasSurfaceSnow()) return;

		boolean firstPlaced = new WorldFeatureDirtPillar().place(world, new Random(rand.nextLong()), x, y, z);
		if (!firstPlaced) return;

		pillarsGenerated++;
		placedPillars++;
		pillarChecks.put(originKey, -1); // mark pillar chunk

		// Keep a tiny local set of chunks used by THIS structure
		java.util.HashSet<Long> localUsed = new java.util.HashSet<>();
		localUsed.add(originKey);

		// arm feature instance used for north-south arms
		WorldFeatureDirtArmNS northSouthArm = new WorldFeatureDirtArmNS();

		int currentChunkX = originChunkX;
		int currentChunkZ = originChunkZ;
		// track previous direction to avoid immediate backtracking
		int previousDir = -1; // -1 = none, 0=N,1=E,2=S,3=W

		while (placedPillars < targetPillars) {
			// gather all valid (dir, length) options
			java.util.ArrayList<int[]> validOptions = new java.util.ArrayList<>();
			for (int dir = 0; dir < 4; dir++) {
				if (previousDir != -1 && ((previousDir + 2) % 4) == dir) continue;

				int dx = 0, dz = 0;
				if (dir == 0) dz = -1;
				else if (dir == 1) dx = 1;
				else if (dir == 2) dz = 1;
				else dx = -1;

				for (int lengthChunks = 1; lengthChunks <= 3; lengthChunks++) {
					int targetChunkX = currentChunkX + dx * lengthChunks;
					int targetChunkZ = currentChunkZ + dz * lengthChunks;
					long targetKey = chunkKey(targetChunkX, targetChunkZ);

					if (pillarChecks.getOrDefault(targetKey, 0) < 0) continue;
					if (localUsed.contains(targetKey)) continue;
					if (pillarChecks.getOrDefault(targetKey, 0) >= MAX_CHECKS_PER_CHUNK) continue;

					boolean pathBlocked = false;
					for (int i = 1; i <= lengthChunks; i++) {
						int midX = currentChunkX + dx * i;
						int midZ = currentChunkZ + dz * i;
						long midKey = chunkKey(midX, midZ);
						if (pillarChecks.getOrDefault(midKey, 0) == -1) {
							pathBlocked = true;
							break;
						}
						if (localUsed.contains(midKey)) {
							pathBlocked = true;
							break;
						}
					}
					if (pathBlocked) continue;

					// biome check at exact aligned location
					int tx, tz;
					if (dir == 0 || dir == 2) { // N/S -> keep X constant
						tx = currentChunkX * 16 + 8;
						tz = targetChunkZ * 16 + 8;
					} else { // E/W -> keep Z constant
						tx = targetChunkX * 16 + 8;
						tz = currentChunkZ * 16 + 8;
					}
					int ty = world.getHeightValue(tx, tz);
					Biome tBiome = world.getBlockBiome(tx, ty, tz);
					if (tBiome.hasSurfaceSnow()) continue;

					validOptions.add(new int[]{dir, lengthChunks});
				}
			}

			if (validOptions.isEmpty()) break;

			int[] choice = validOptions.get(rand.nextInt(validOptions.size()));
			int dir = choice[0];
			int lengthChunks = choice[1];
			int dx = 0, dz = 0;
			if (dir == 0) dz = -1;
			else if (dir == 1) dx = 1;
			else if (dir == 2) dz = 1;
			else dx = -1;

			int targetChunkX = currentChunkX + dx * lengthChunks;
			int targetChunkZ = currentChunkZ + dz * lengthChunks;
			long targetKey = chunkKey(targetChunkX, targetChunkZ);

			// exact aligned pillar coords
			int tx, tz;
			if (dir == 0 || dir == 2) { // N/S
				tx = currentChunkX * 16 + 8;
				tz = targetChunkZ * 16 + 8;
			} else { // E/W
				tx = targetChunkX * 16 + 8;
				tz = currentChunkZ * 16 + 8;
			}
			int ty = world.getHeightValue(tx, tz);

			// place bridges in a perfectly straight line
			for (int i = 1; i <= lengthChunks; i++) {
				int midChunkX = currentChunkX + dx * i;
				int midChunkZ = currentChunkZ + dz * i;
				long midKey = chunkKey(midChunkX, midChunkZ);

				if (dx != 0) {
					placeEastWestArmInChunk(world, midChunkZ, midChunkX);
				} else {
					placeNorthSouthArmInChunk(world, midChunkX, midChunkZ, northSouthArm);
				}
				pillarChecks.putIfAbsent(midKey, -2);
				localUsed.add(midKey);
			}

			boolean placed = new WorldFeatureDirtPillar().place(world, new Random(rand.nextLong()), tx, ty, tz);
			if (!placed) break;

			pillarChecks.put(targetKey, -1);
			localUsed.add(targetKey);
			pillarsGenerated++;
			placedPillars++;

			currentChunkX = targetChunkX;
			currentChunkZ = targetChunkZ;
			previousDir = dir;
		}
	}



	private void placeEastWestArmInChunk(World world, int chunkZ, int chunkX) {
		int chunkOriginX = chunkX << 4;
		int centerZ = (chunkZ << 4) + 8;

		for (int dx = 0; dx < 16; dx++) {
			world.setBlockAndMetadataWithNotify(chunkOriginX + dx, ARM_Y, centerZ, 220, 0);
		}
	}

	private void placeNorthSouthArmInChunk(World world, int chunkX, int chunkZ, WorldFeatureDirtArmNS armFeature) {
		int centerX = (chunkX << 4) + 8;
		int chunkOriginZ = chunkZ << 4;

		armFeature.place(world, new Random(), centerX, ARM_Y, chunkOriginZ + 8);
	}

	private void placeTurnArmInChunk(World world, int chunkX, int chunkZ, WorldFeatureDirtArmTurn turnFeature) {
		int centerX = (chunkX << 4) + 8;
		int centerZ = (chunkZ << 4) + 8;

		turnFeature.place(world, new Random(), centerX, ARM_Y, centerZ);
	}

	private int determineTurnRotation(int dx, int dz) {
		if (dx == 1 && dz == 1) return 0;  // East then South
		if (dx == 1 && dz == -1) return 3; // North then East
		if (dx == -1 && dz == -1) return 2; // West then North
		if (dx == -1 && dz == 1) return 1;  // South then West
		return 0;
	}

	private long chunkKey(int chunkX, int chunkZ) {
		return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
	}
}

