package bdmajora.stuffmod.world.overworld.worldGen;

import bdmajora.stuffmod.world.LargeStructureGenerator;
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
		// Seed random for consistent generation per chunk
		for (int chunkX = originChunkX - range; chunkX <= originChunkX + range; chunkX++) {
			for (int chunkZ = originChunkZ - range; chunkZ <= originChunkZ + range; chunkZ++) {
				if (pillarsGenerated >= MAX_PILLARS_PER_GENERATE) {
					return; // stop once max reached
				}

				long key = chunkKey(chunkX, chunkZ);
				int checks = pillarChecks.getOrDefault(key, 0);
				if (checks >= MAX_CHECKS_PER_CHUNK) continue;

				// Increment checks count
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

				// Chance check
				// Use deterministic RNG per chunk for consistency
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
	}

	private long chunkKey(int chunkX, int chunkZ) {
		return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
	}
}
