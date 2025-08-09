package bdmajora.stuffmod.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

import java.util.Random;

public abstract class LargeStructureGenerator {

	protected int range;  // Configurable range of chunks to generate around origin
	protected Random rand = new Random();
	protected World worldObj;

	// Limit to avoid excessive chunk processing in one generate call
	private static final int MAX_CHUNKS_PER_GENERATE = 100;

	public LargeStructureGenerator() {
		this(8);  // Default range 8 chunks
	}

	public LargeStructureGenerator(int range) {
		if (range < 0) throw new IllegalArgumentException("Range cannot be negative");
		this.range = range;
	}

	/**
	 * Starts generating structures in and around the given chunk coordinates.
	 *
	 * Uses a simpler seeding method inspired by MapGenBase:
	 *  - Seeds RNG once with world seed
	 *  - Generates two longs (worldXSeedFactor, worldZSeedFactor)
	 *  - Uses chunk coords multiplied by them to seed per chunk
	 */
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		this.worldObj = world;

		// Seed once with world seed
		this.rand.setSeed(world.getRandomSeed());
		long worldXSeedFactor = this.rand.nextLong();
		long worldZSeedFactor = this.rand.nextLong();

		int chunksProcessed = 0;

		// Iterate chunks in square area around origin within configured range
		outer:
		for (int z = originChunkZ - range; z <= originChunkZ + range; z++) {
			for (int x = originChunkX - range; x <= originChunkX + range; x++) {
				if (chunksProcessed >= MAX_CHUNKS_PER_GENERATE) {
					break outer;
				}

				// Only run on new chunks (skip already populated ones)
				Chunk chunk = world.getChunkFromChunkCoords(x, z);
				if (chunk.isTerrainPopulated) {
					continue;
				}

				// Simpler per-chunk seed logic from MapGenBase
				long seedX = (long) x * worldXSeedFactor;
				long seedZ = (long) z * worldZSeedFactor;
				this.rand.setSeed(seedX ^ seedZ ^ world.getRandomSeed());

				// Delegate to subclass for chunk-specific generation logic
				recursiveGenerate(world, x, z, originChunkX, originChunkZ);

				chunksProcessed++;
			}
		}
	}

	/**
	 * Default implementation does nothing.
	 * Subclasses can override for chunk-specific generation logic if needed.
	 */
	protected void recursiveGenerate(World world, int chunkX, int chunkZ, int originChunkX, int originChunkZ) {
		// Default no-op
	}

	/**
	 * Allows changing the generation range at runtime.
	 *
	 * @param range Number of chunks to process around origin in each direction
	 */
	public void setRange(int range) {
		if (range < 0) throw new IllegalArgumentException("Range cannot be negative");
		this.range = range;
	}

	/**
	 * @return Current generation range
	 */
	public int getRange() {
		return this.range;
	}
}
