package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeStraight;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenerateBridgeEast {
	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeEast(FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;
		this.rotation = 90; // East
	}

	/**
	 * Generates a variable-length bridge going east and returns the X coordinate of the last segment.
	 */
	public int generateBridge(World world, Random rand, int x, int y, int z) {
		int entranceWidth = 13;
		int bridgeWidth = 5;
		int bridgeLength = 19;

		int bridgeCount = 2 + rand.nextInt(3); // 2–10 bridges

		int bridgeX = x + 28; // EAST offset
		int bridgeZ = z - 6;

		WorldFeatureNetherBridgeStraight bridge = new WorldFeatureNetherBridgeStraight(fortressBlocks, rotation);

		for (int i = 0; i < bridgeCount; i++) {
			bridge.place(world, rand, bridgeX + (i * bridgeLength), y, bridgeZ);
		}

		return bridgeX + ((bridgeCount - 1) * bridgeLength);
	}
}
