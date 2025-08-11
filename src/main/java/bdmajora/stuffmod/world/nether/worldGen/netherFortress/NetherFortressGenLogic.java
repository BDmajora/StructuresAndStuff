package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import net.minecraft.core.world.World;
import java.util.Random;

public class NetherFortressGenLogic {

	/**
	 * Determines whether a Nether Fortress structure should generate in this chunk.
	 *
	 * @param world The world instance (for seed)
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the chunk
	 * @param chanceDenominator The 1-in-N chance denominator (e.g. 6 = ~16.6% chance)
	 * @return true if generation should occur, false otherwise
	 */
	public static boolean shouldGenerate(World world, int chunkX, int chunkZ, int chanceDenominator) {
		Random rand = new Random(world.getRandomSeed() ^ ((long) chunkX << 32) ^ chunkZ);
		return rand.nextInt(chanceDenominator) == 0;
	}
}
