package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeCrossing;
import net.minecraft.core.world.World;

import java.util.Random;

/**
 * Places the Nether Fortress bridge crossing feature (north-facing) and
 * provides coordinates for where East/West bridges should attach.
 */
public class NetherFortressGenerateBridgeCrossing {

	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeCrossing(FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;
		this.rotation = rotation;
	}

	/**
	 * Holds both placement success and coordinates for side bridge attachment.
	 */
	public static class CrossingResult {
		public final boolean placed;
		public final int attachX;
		public final int attachY;
		public final int attachZ;

		public CrossingResult(boolean placed, int attachX, int attachY, int attachZ) {
			this.placed = placed;
			this.attachX = attachX;
			this.attachY = attachY;
			this.attachZ = attachZ;
		}
	}

	/**
	 * Places the north-facing bridge crossing and returns side bridge attachment coordinates.
	 *
	 * @param x The X coordinate from which to start
	 * @param y The Y coordinate from which to start
	 * @param endZ The Z coordinate where the main north bridge ended
	 * @return CrossingResult with placement status and side bridge anchor coords
	 */
	public CrossingResult placeCrossing(World world, Random rand, int x, int y, int endZ) {
		int crossingX = x + 3; // align with main north bridge
		int crossingY = y + 3;
		int crossingZ = endZ + 19; // forward offset for north

		boolean placed = new WorldFeatureNetherBridgeCrossing(fortressBlocks)
			.place(world, rand, crossingX, crossingY, crossingZ);

		// The crossing piece center can serve as side bridge anchor
		int sideAttachX = crossingX;
		int sideAttachY = crossingY;
		int sideAttachZ = crossingZ;

		return new CrossingResult(placed, sideAttachX, sideAttachY, sideAttachZ);
	}
}
