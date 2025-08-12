package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeEntrance;
import net.minecraft.core.world.World;

import java.util.Random;

public class NetherFortressGenStep1 {

	private final FortressBlocks fortressBlocks;

	public NetherFortressGenStep1(FortressBlocks fortressBlocks) {
		this.fortressBlocks = fortressBlocks;
	}

	/**
	 * Places the Nether Fortress entrance feature.
	 * @return true if placement succeeded, false otherwise.
	 */
	public boolean placeStep1(World world, Random rand, int x, int y, int z) {
		return new WorldFeatureNetherBridgeEntrance(fortressBlocks).place(world, rand, x, y, z);
	}
}
