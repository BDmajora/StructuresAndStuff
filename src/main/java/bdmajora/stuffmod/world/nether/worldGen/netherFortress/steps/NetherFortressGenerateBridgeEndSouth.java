package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeEnd;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenerateBridgeEndSouth {
	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeEndSouth (FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;
		this.rotation = rotation;
	}

	/**
	 * Places the Nether Fortress bridge end feature.
	 * @param endZ the Z coordinate where the bridge ends
	 */
	public boolean placeBridgeEnd(World world, Random rand, int x, int y, int endZ) {
		int endX = x + 8; // use same base X as bridge start (rotation handles actual position)
		int endY = y;
		endZ-= 1; // offset forward in local coords
		return new WorldFeatureNetherBridgeEnd(fortressBlocks, rotation)
			.place(world, rand, endX, endY, endZ);
	}
}
