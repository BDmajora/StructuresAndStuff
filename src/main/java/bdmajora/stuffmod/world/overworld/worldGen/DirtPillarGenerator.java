package bdmajora.stuffmod.world.overworld.worldGen;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.overworld.worldFeatures.WorldFeatureDirtPillar;
import bdmajora.stuffmod.world.overworld.worldFeatures.WorldFeatureDirtArmNS;
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

		// --- FIRST PASS: Generate only pillars and mark them with -1 ---

		for (int chunkX = originChunkX - range; chunkX <= originChunkX + range; chunkX++) {
			for (int chunkZ = originChunkZ - range; chunkZ <= originChunkZ + range; chunkZ++) {
				if (pillarsGenerated >= MAX_PILLARS_PER_GENERATE) {
					// Stop generating pillars once max reached
					break;
				}

				long key = chunkKey(chunkX, chunkZ);
				int checks = pillarChecks.getOrDefault(key, 0);
				if (checks >= MAX_CHECKS_PER_CHUNK) continue;

				// Increment checks count for this chunk only for pillars, not arms
				pillarChecks.put(key, checks + 1);

				// Skip uneven chunks
				if ((chunkX % 2 != 0) || (chunkZ % 2 != 0)) {
					continue;
				}

				// Skip if neighbor has pillar (-1)
				boolean neighborHasPillar = false;
				for (int dx = -1; dx <= 1 && !neighborHasPillar; dx++) {
					for (int dz = -1; dz <= 1 && !neighborHasPillar; dz++) {
						long neighborKey = chunkKey(chunkX + dx, chunkZ + dz);
						if (pillarChecks.getOrDefault(neighborKey, 0) == -1) {
							neighborHasPillar = true;
						}
					}
				}
				if (neighborHasPillar) continue;

				// Chance check (deterministic RNG)
				Random rand = new Random(baseSeed ^ (chunkX * 341873128712L) ^ (chunkZ * 132897987541L));
				if (rand.nextInt(CHANCE_DENOMINATOR) != 0) {
					continue; // skip without marking pillar present
				}

				int blockX = chunkX * 16;
				int blockZ = chunkZ * 16;
				int x = blockX + rand.nextInt(16) + 8;
				int z = blockZ + rand.nextInt(16) + 8;
				int y = world.getHeightValue(x, z);

				Biome biome = world.getBlockBiome(x, y, z);
				if (!biome.hasSurfaceSnow()) {
					boolean placed = new WorldFeatureDirtPillar().place(world, new Random(rand.nextLong()), x, y, z);
					if (placed) {
						pillarsGenerated++;
						pillarChecks.put(key, -1); // mark pillar present
					}
				}
			}
		}

		// --- SECOND PASS: After all pillars generated ---
		// Connect pillars by checking a 5x5 grid around each pillar chunk for others,
		// and build arms (bridges) east-west and north-south if gaps between 1 to 5 chunks.

		WorldFeatureDirtArmNS northSouthArm = new WorldFeatureDirtArmNS();

		// Copy keys of all pillars to avoid concurrent modification
		Map<Long, Integer> pillarsSnapshot = new HashMap<>();
		for (Map.Entry<Long, Integer> entry : pillarChecks.entrySet()) {
			if (entry.getValue() == -1) { // only pillar chunks
				pillarsSnapshot.put(entry.getKey(), entry.getValue());
			}
		}

		for (long key : pillarsSnapshot.keySet()) {
			int chunkX = (int)(key >> 32);
			int chunkZ = (int) key;

			// Search a 5x5 grid around this pillar
			for (int dx = -5; dx <= 5; dx++) {
				for (int dz = -5; dz <= 5; dz++) {
					if (dx == 0 && dz == 0) continue; // skip self

					long neighborKey = chunkKey(chunkX + dx, chunkZ + dz);
					if (pillarChecks.getOrDefault(neighborKey, 0) == -1) {
						int gapX = Math.abs(dx) - 1;
						int gapZ = Math.abs(dz) - 1;

						// Connect east-west gaps 1 to 5 chunks only when dz == 0 (same Z)
						if (dz == 0 && gapX >= 0 && gapX < 5) {
							// Place arms between chunkX and chunkX+dx (east-west)
							int startX = Math.min(chunkX, chunkX + dx) + 1;
							int endX = Math.max(chunkX, chunkX + dx);
							for (int fillX = startX; fillX < endX; fillX++) {
								placeEastWestArmInChunk(world, chunkZ, fillX);
							}
						}

						// Connect north-south gaps 1 to 5 chunks only when dx == 0 (same X)
						if (dx == 0 && gapZ >= 0 && gapZ < 5) {
							int startZ = Math.min(chunkZ, chunkZ + dz) + 1;
							int endZ = Math.max(chunkZ, chunkZ + dz);
							for (int fillZ = startZ; fillZ < endZ; fillZ++) {
								placeNorthSouthArmInChunk(world, chunkX, fillZ, northSouthArm);
							}
						}
					}
				}
			}
		}
	}

	// Places an east-west dirt arm across a chunk at ARM_Y level
	private void placeEastWestArmInChunk(World world, int chunkZ, int chunkX) {
		int chunkOriginX = chunkX << 4;
		int centerZ = (chunkZ << 4) + 8;

		for (int dx = 0; dx < 16; dx++) {
			world.setBlockAndMetadataWithNotify(chunkOriginX + dx, ARM_Y, centerZ, 220, 0);
		}
	}

	// Places a north-south dirt arm across a chunk at ARM_Y level using WorldFeatureDirtArmNS
	private void placeNorthSouthArmInChunk(World world, int chunkX, int chunkZ, WorldFeatureDirtArmNS armFeature) {
		int centerX = (chunkX << 4) + 8;
		int chunkOriginZ = chunkZ << 4;

		// The armFeature places dirt blocks from north to south across the chunk center line
		armFeature.place(world, new Random(), centerX, ARM_Y, chunkOriginZ + 8);
	}

	private long chunkKey(int chunkX, int chunkZ) {
		return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
	}
}
