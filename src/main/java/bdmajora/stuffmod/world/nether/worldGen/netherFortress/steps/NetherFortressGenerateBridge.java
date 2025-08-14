package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeStraight;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenerateBridge {
	private final FortressBlocks fortressBlocks;

	public NetherFortressGenerateBridge(FortressBlocks blocks) {
		this.fortressBlocks = blocks;
	}

	/**
	 * Generates a variable-length bridge and returns the Z coordinate of the last segment.
	 */
	public int generateBridge(World world, Random rand, int x, int y, int z) {
		int entranceWidth = 13;
		int bridgeWidth = 5;
		int bridgeLength = 19;

		int bridgeCount = 2 + rand.nextInt(9); // 2–10 bridges

		int bridgeX = x + (entranceWidth / 2) - (bridgeWidth / 2);
		int bridgeZ = z + 13;

		WorldFeatureNetherBridgeStraight bridge = new WorldFeatureNetherBridgeStraight(fortressBlocks);

		for (int i = 0; i < bridgeCount; i++) {
			bridge.place(world, rand, bridgeX, y, bridgeZ + (i * bridgeLength));
		}

		// Return the Z coordinate of the last bridge segment's start + length
		return bridgeZ + ((bridgeCount - 1) * bridgeLength);
	}
}
