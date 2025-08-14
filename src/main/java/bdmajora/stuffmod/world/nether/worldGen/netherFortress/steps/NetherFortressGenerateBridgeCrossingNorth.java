package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeCrossing;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenerateBridgeCrossingNorth {
	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeCrossingNorth(FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;
		this.rotation = rotation;
	}

	/**
	 * Places the Nether Fortress bridge crossing feature (north-facing).
	 * @param endZ the Z coordinate where the bridge ends
	 */
	public boolean placeCrossing(World world, Random rand, int x, int y, int endZ) {
		int crossingX = x + 3; // same base X as BridgeEndNorth
		int crossingY = y + 3;
		endZ += 19; // forward offset for north
		return new WorldFeatureNetherBridgeCrossing(fortressBlocks)
			.place(world, rand, crossingX, crossingY, endZ);
	}
}
