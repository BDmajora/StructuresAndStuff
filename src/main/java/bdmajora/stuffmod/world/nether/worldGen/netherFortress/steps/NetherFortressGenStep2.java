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
		int bridgeX = x;
		int bridgeZ = z + 13; // Assuming forward in +Z direction
		int bridgeY = y;
		return new WorldFeatureNetherBridgeStraight(fortressBlocks).place(world, rand, bridgeX, bridgeY, bridgeZ);
	}
}
