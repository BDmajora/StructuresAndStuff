package bdmajora.stuffmod.world.overworld.worldGen;

import net.minecraft.core.world.World;

public class DirtPillarGenLogic {

	/**
	 * Determines whether a DirtPillar structure should generate in this chunk.
	 * Currently set to a 1 in 50 chance.
	 *
	 * @param world The world to pull the RNG from
	 * @return true if generation should occur, false otherwise
	 */
	public static boolean shouldGenerate(World world) {
		return world.rand.nextInt(50) == 0;
	}
}
