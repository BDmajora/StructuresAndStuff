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

		// --- FIRST PASS: Generate only pillars and mark them with -1 ---

		for (int chunkX = originChunkX - range; chunkX <= originChunkX + range; chunkX++) {
			for (int chunkZ = originChunkZ - range; chunkZ <= originChunkZ + range; chunkZ++) {
				if (pillarsGenerated >= MAX_PILLARS_PER_GENERATE) {
					break;
				}

				long key = chunkKey(chunkX, chunkZ);
				int checks = pillarChecks.getOrDefault(key, 0);
				if (checks >= MAX_CHECKS_PER_CHUNK) continue;

				pillarChecks.put(key, checks + 1);

				if ((chunkX % 2 != 0) || (chunkZ % 2 != 0)) {
					continue;
				}

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

				Random rand = new Random(baseSeed ^ (chunkX * 341873128712L) ^ (chunkZ * 132897987541L));
				if (rand.nextInt(CHANCE_DENOMINATOR) != 0) {
					continue;
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
						pillarChecks.put(key, -1); // Mark pillar chunk
					}
				}
			}
		}

		// --- SECOND PASS: Connect pillars with arms ---
		WorldFeatureDirtArmNS northSouthArm = new WorldFeatureDirtArmNS();

		Map<Long, Integer> pillarsSnapshot = new HashMap<>();
		for (Map.Entry<Long, Integer> entry : pillarChecks.entrySet()) {
			if (entry.getValue() == -1) {
				pillarsSnapshot.put(entry.getKey(), entry.getValue());
			}
		}

		// Collect bridge chunks to add after iteration to avoid concurrent modification
		Map<Long, Integer> newBridgeChunks = new HashMap<>();

		for (long key : pillarsSnapshot.keySet()) {
			int chunkX = (int) (key >> 32);
			int chunkZ = (int) key;

			for (int dx = -5; dx <= 5; dx++) {
				for (int dz = -5; dz <= 5; dz++) {
					if (dx == 0 && dz == 0) continue;

					long neighborKey = chunkKey(chunkX + dx, chunkZ + dz);
					if (pillarChecks.getOrDefault(neighborKey, 0) == -1) {
						int gapX = Math.abs(dx) - 1;
						int gapZ = Math.abs(dz) - 1;

						// East-West gaps (same Z)
						if (dz == 0 && gapX >= 0 && gapX < 5) {
							int startX = Math.min(chunkX, chunkX + dx) + 1;
							int endX = Math.max(chunkX, chunkX + dx);
							for (int fillX = startX; fillX < endX; fillX++) {
								placeEastWestArmInChunk(world, chunkZ, fillX);
								newBridgeChunks.put(chunkKey(fillX, chunkZ), -2); // Mark bridge chunk
							}
						}

						// North-South gaps (same X)
						if (dx == 0 && gapZ >= 0 && gapZ < 5) {
							int startZ = Math.min(chunkZ, chunkZ + dz) + 1;
							int endZ = Math.max(chunkZ, chunkZ + dz);
							for (int fillZ = startZ; fillZ < endZ; fillZ++) {
								placeNorthSouthArmInChunk(world, chunkX, fillZ, northSouthArm);
								newBridgeChunks.put(chunkKey(chunkX, fillZ), -2); // Mark bridge chunk
							}
						}

						// Diagonal neighbors: right-angle turns
						if (gapX == 0 && gapZ == 0 && dx != 0 && dz != 0) {
							int rotation = determineTurnRotation(dx, dz);
							WorldFeatureDirtArmTurn turn = new WorldFeatureDirtArmTurn(rotation);
							placeTurnArmInChunk(world, chunkX + dx, chunkZ + dz, turn);
							newBridgeChunks.put(chunkKey(chunkX + dx, chunkZ + dz), -2); // Mark bridge chunk
						}
					}
				}
			}
		}

		// Add all new bridge chunks after iteration
		for (Map.Entry<Long, Integer> entry : newBridgeChunks.entrySet()) {
			pillarChecks.putIfAbsent(entry.getKey(), entry.getValue());
		}

		// --- JUNCTION PASS: Place three-way junctions where exactly three cardinal neighbors are connectors ---
		// Iterate over a snapshot to avoid concurrent modification
		Map<Long, Integer> checksSnapshot = new HashMap<>(pillarChecks);
		for (Map.Entry<Long, Integer> entry : checksSnapshot.entrySet()) {
			long key = entry.getKey();
			int value = entry.getValue();

			// only consider chunks that are not already a pillar or bridge
			if (value >= 0) {
				int chunkX = (int) (key >> 32);
				int chunkZ = (int) key;

				boolean north = pillarChecks.getOrDefault(chunkKey(chunkX, chunkZ - 1), 0) < 0;
				boolean south = pillarChecks.getOrDefault(chunkKey(chunkX, chunkZ + 1), 0) < 0;
				boolean east  = pillarChecks.getOrDefault(chunkKey(chunkX + 1, chunkZ), 0) < 0;
				boolean west  = pillarChecks.getOrDefault(chunkKey(chunkX - 1, chunkZ), 0) < 0;

				int connectedCount = (north ? 1 : 0) + (south ? 1 : 0) + (east ? 1 : 0) + (west ? 1 : 0);

				if (connectedCount == 3) {
					int rotation;
					// rotation mapping per WorldFeatureDirtArmJunction:
					// 0 = North, South, East
					// 1 = North, South, West
					// 2 = North, East, West
					// 3 = South, East, West
					if (!west)      rotation = 0; // missing west -> N,S,E
					else if (!east) rotation = 1; // missing east -> N,S,W
					else if (!south) rotation = 2; // missing south -> N,E,W
					else             rotation = 3; // missing north -> S,E,W

					WorldFeatureDirtArmJunction junction = new WorldFeatureDirtArmJunction(rotation);
					junction.place(world, new Random(), (chunkX << 4) + 8, ARM_Y, (chunkZ << 4) + 8);
					pillarChecks.put(chunkKey(chunkX, chunkZ), -2); // mark as bridge/junction
				}
			}
		}

		// --- THIRD PASS: Connect bridges to other bridges and pillars ---
		Map<Long, Integer> bridgesSnapshot = new HashMap<>();
		for (Map.Entry<Long, Integer> entry : pillarChecks.entrySet()) {
			if (entry.getValue() == -2) {
				bridgesSnapshot.put(entry.getKey(), entry.getValue());
			}
		}

		for (long key : bridgesSnapshot.keySet()) {
			int chunkX = (int) (key >> 32);
			int chunkZ = (int) key;

			for (int dx = -5; dx <= 5; dx++) {
				for (int dz = -5; dz <= 5; dz++) {
					if (dx == 0 && dz == 0) continue;

					long neighborKey = chunkKey(chunkX + dx, chunkZ + dz);

					// Connect bridge chunks with other bridge chunks
					if (pillarChecks.getOrDefault(neighborKey, 0) == -2) {
						int gapX = Math.abs(dx) - 1;
						int gapZ = Math.abs(dz) - 1;

						if (dz == 0 && gapX >= 0 && gapX < 5) {
							int startX = Math.min(chunkX, chunkX + dx) + 1;
							int endX = Math.max(chunkX, chunkX + dx);
							for (int fillX = startX; fillX < endX; fillX++) {
								placeEastWestArmInChunk(world, chunkZ, fillX);
								pillarChecks.putIfAbsent(chunkKey(fillX, chunkZ), -2);
							}
						}

						if (dx == 0 && gapZ >= 0 && gapZ < 5) {
							int startZ = Math.min(chunkZ, chunkZ + dz) + 1;
							int endZ = Math.max(chunkZ, chunkZ + dz);
							for (int fillZ = startZ; fillZ < endZ; fillZ++) {
								placeNorthSouthArmInChunk(world, chunkX, fillZ, northSouthArm);
								pillarChecks.putIfAbsent(chunkKey(chunkX, fillZ), -2);
							}
						}

						if (gapX == 0 && gapZ == 0 && dx != 0 && dz != 0) {
							int rotation = determineTurnRotation(dx, dz);
							WorldFeatureDirtArmTurn turn = new WorldFeatureDirtArmTurn(rotation);
							placeTurnArmInChunk(world, chunkX + dx, chunkZ + dz, turn);
							pillarChecks.putIfAbsent(neighborKey, -2);
						}
					}

					// Connect bridges to adjacent pillars
					if (pillarChecks.getOrDefault(neighborKey, 0) == -1) {
						// East-West neighbor?
						if (dz == 0 && Math.abs(dx) == 1) {
							placeEastWestArmInChunk(world, chunkZ, chunkX);
						}
						// North-South neighbor?
						if (dx == 0 && Math.abs(dz) == 1) {
							placeNorthSouthArmInChunk(world, chunkX, chunkZ, northSouthArm);
						}
						// Diagonal neighbor?
						if (Math.abs(dx) == 1 && Math.abs(dz) == 1) {
							int rotation = determineTurnRotation(dx, dz);
							WorldFeatureDirtArmTurn turn = new WorldFeatureDirtArmTurn(rotation);
							placeTurnArmInChunk(world, chunkX, chunkZ, turn);
						}
					}
				}
			}
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

