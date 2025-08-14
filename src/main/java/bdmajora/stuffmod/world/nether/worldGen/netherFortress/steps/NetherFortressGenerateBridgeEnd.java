package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeEnd;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenerateBridgeEnd {
	private final FortressBlocks fortressBlocks;

	public NetherFortressGenerateBridgeEnd(FortressBlocks blocks) {
		this.fortressBlocks = blocks;
	}

	/**
	 * Places the Nether Fortress bridge end feature.
	 * @param endZ the Z coordinate where the bridge ends
	 */
	public boolean placeBridgeEnd(World world, Random rand, int x, int y, int endZ) {
		int endX = x + 4; // keep centered relative to bridge
		int endY = y;
		endZ += 19; // hard-coded offset forward
		return new WorldFeatureNetherBridgeEnd(fortressBlocks).place(world, rand, endX, endY, endZ);
	}
}
