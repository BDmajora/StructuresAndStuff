package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeStraight;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenerateBridgeNorth {
	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeNorth(FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;

		// TEMP: Ignore passed rotation and hard-code for testing
		this.rotation = 0; // 0 = north, 90 = east, 180 = south, 270 = west
	}

	/**
	 * Generates a variable-length bridge going north and returns the Z coordinate of the last segment.
	 */
	public int generateBridge(World world, Random rand, int x, int y, int z) {
		int entranceWidth = 13;
		int bridgeWidth = 5;
		int bridgeLength = 19;

		int bridgeCount = 2 + rand.nextInt(9); // 2–10 bridges

		int bridgeX = x + (entranceWidth / 2) - (bridgeWidth / 2);
		int bridgeZ = z + 13; // NORTH offset

		WorldFeatureNetherBridgeStraight bridge = new WorldFeatureNetherBridgeStraight(fortressBlocks, rotation);

		for (int i = 0; i < bridgeCount; i++) {
			bridge.place(world, rand, bridgeX, y, bridgeZ + (i * bridgeLength));
		}

		return bridgeZ + ((bridgeCount - 1) * bridgeLength);
	}
}
