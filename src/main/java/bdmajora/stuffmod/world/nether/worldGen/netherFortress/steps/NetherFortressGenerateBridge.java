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

	public boolean generateBridge(World world, Random rand, int x, int y, int z) {
		int entranceWidth = 13; // from x to x+12
		int bridgeWidth = 5;    // change if different
		int bridgeLength = 19;  // known from WorldFeatureNetherBridgeStraight

		// Decide how many bridge segments to place (at least 1)
		int bridgeCount = 2 + rand.nextInt(7); // 1–3 bridges
		boolean allPlaced = true;

		// Center-align the bridge with the entrance
		int bridgeX = x + (entranceWidth / 2) - (bridgeWidth / 2);
		int bridgeZ = z + 13; // forward from entrance

		WorldFeatureNetherBridgeStraight bridge = new WorldFeatureNetherBridgeStraight(fortressBlocks);

		// Place bridges back-to-back
		for (int i = 0; i < bridgeCount; i++) {
			boolean placed = bridge.place(world, rand, bridgeX, y, bridgeZ + (i * bridgeLength));
			allPlaced &= placed; // require all to succeed
		}

		return allPlaced;
	}
}
