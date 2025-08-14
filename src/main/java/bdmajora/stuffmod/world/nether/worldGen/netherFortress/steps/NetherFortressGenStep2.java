package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeStraight;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenStep2 {
	private final FortressBlocks fortressBlocks;

	public NetherFortressGenStep2(FortressBlocks blocks) {
		this.fortressBlocks = blocks;
	}

	public boolean placeStep2(World world, Random rand, int x, int y, int z) {
		int entranceWidth = 13; // from x to x+12
		int bridgeWidth = 5;    // change if different

		// Center-align the bridge with the entrance
		int bridgeX = x + (entranceWidth / 2) - (bridgeWidth / 2);
		int bridgeZ = z + 13; // forward from entrance

		return new WorldFeatureNetherBridgeStraight(fortressBlocks)
			.place(world, rand, bridgeX, y, bridgeZ);
	}

}
