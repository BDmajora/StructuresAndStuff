package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeEnd;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenStep3 {
	private final FortressBlocks fortressBlocks;

	public NetherFortressGenStep3(FortressBlocks blocks) {
		this.fortressBlocks = blocks;
	}

	public boolean placeStep3(World world, Random rand, int x, int y, int z) {
		int endX = x + 4;
		int endZ = z + 32; // 13 blocks offset from Step 2 bridge end
		int endY = y;
		return new WorldFeatureNetherBridgeEnd(fortressBlocks).place(world, rand, endX, endY, endZ);
	}
}
