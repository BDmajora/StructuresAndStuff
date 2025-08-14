package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import net.minecraft.core.world.World;

public class NetherFortressGenLogic {

	/**
	 * Determines whether a Nether Fortress structure should generate in this chunk.
	 * Currently set to a very rare chance, adjust the denominator as needed.
	 *
	 * @param world The world to pull the RNG from
	 * @return true if generation should occur, false otherwise
	 */
	public static boolean shouldGenerate(World world) {
		// For example, 1-in-6 chance; change 6 to adjust frequency
		return world.rand.nextInt(3) == 0;
	}
}
