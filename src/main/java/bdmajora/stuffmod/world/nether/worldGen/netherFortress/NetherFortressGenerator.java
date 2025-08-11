package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherStartPiece;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeCorridor;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeCorridor2;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NetherFortressGenerator extends LargeStructureGenerator {

	public static final int CHANCE_DENOMINATOR = 6; // ~16.6% chance per origin chunk
	private static final int MAX_CHECKS_PER_CHUNK = 1;

	private static final Map<World, Map<Long, Integer>> worldChecks = new HashMap<>();

	private final FortressBlocks fb;

	public NetherFortressGenerator(FortressBlocks blocks) {
		this.fb = blocks;
		setRange(8);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		worldChecks.putIfAbsent(world, new HashMap<>());
		Map<Long, Integer> checks = worldChecks.get(world);

		long originKey = chunkKey(originChunkX, originChunkZ);
		if (checks.getOrDefault(originKey, 0) >= MAX_CHECKS_PER_CHUNK) return;
		checks.put(originKey, checks.getOrDefault(originKey, 0) + 1);

		// Fortress spawn chance
		if (!NetherFortressGenLogic.shouldGenerate(world, originChunkX, originChunkZ, CHANCE_DENOMINATOR)) return;

		Random rand = new Random(world.getRandomSeed() ^ ((long) originChunkX << 32) ^ originChunkZ);

		// Position
		int x = originChunkX * 16 + 4;
		int z = originChunkZ * 16 + 4;
		int y = 176; // fixed Nether fortress height

		// Place start piece
		boolean startPlaced = new WorldFeatureNetherStartPiece(fb).place(world, new Random(rand.nextLong()), x, y, z);
		if (!startPlaced) return;

		// Mark chunk as having fortress
		checks.put(originKey, -1);

		// Corridor length
		int corridorLength = 5;

		// Pick corridor type randomly (50/50 chance)
		boolean useCorridor2 = rand.nextBoolean();
		if (useCorridor2) {
			WorldFeatureNetherBridgeCorridor2 corridor = new WorldFeatureNetherBridgeCorridor2(fb);
			placeCorridors(world, rand, corridor, x, y, z, corridorLength);
		} else {
			WorldFeatureNetherBridgeCorridor corridor = new WorldFeatureNetherBridgeCorridor(fb);
			placeCorridors(world, rand, corridor, x, y, z, corridorLength);
		}
	}

	private void placeCorridors(World world, Random rand, Object corridorFeature, int x, int y, int z, int length) {
		// For each direction, create a new Random instance for consistency
		if (corridorFeature instanceof WorldFeatureNetherBridgeCorridor) {
			WorldFeatureNetherBridgeCorridor corridor = (WorldFeatureNetherBridgeCorridor) corridorFeature;
			corridor.place(world, new Random(rand.nextLong()), x - 2, y, z - length - 1); // North
			corridor.place(world, new Random(rand.nextLong()), x - 2, y, z + length + 1); // South
			corridor.place(world, new Random(rand.nextLong()), x - length - 1, y, z - 2); // West
			corridor.place(world, new Random(rand.nextLong()), x + length + 1, y, z - 2); // East
		} else if (corridorFeature instanceof WorldFeatureNetherBridgeCorridor2) {
			WorldFeatureNetherBridgeCorridor2 corridor = (WorldFeatureNetherBridgeCorridor2) corridorFeature;
			corridor.place(world, new Random(rand.nextLong()), x - 2, y, z - length - 1); // North
			corridor.place(world, new Random(rand.nextLong()), x - 2, y, z + length + 1); // South
			corridor.place(world, new Random(rand.nextLong()), x - length - 1, y, z - 2); // West
			corridor.place(world, new Random(rand.nextLong()), x + length + 1, y, z - 2); // East
		}
	}

	private long chunkKey(int chunkX, int chunkZ) {
		return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
	}
}
