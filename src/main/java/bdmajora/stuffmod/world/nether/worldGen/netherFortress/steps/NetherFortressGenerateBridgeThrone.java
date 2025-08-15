package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeThrone;
import net.minecraft.core.world.World;

import java.util.Random;

/**
 * Places the Nether Fortress throne room feature and
 * provides coordinates for possible continuation/attachment.
 */
public class NetherFortressGenerateBridgeThrone {

	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeThrone(FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;
		this.rotation = rotation;
	}

	/**
	 * Holds placement success and coordinates for where the next piece should attach.
	 */
	public static class ThroneResult {
		public final boolean placed;
		public final int nextX;
		public final int nextY;
		public final int nextZ;

		public ThroneResult(boolean placed, int nextX, int nextY, int nextZ) {
			this.placed = placed;
			this.nextX = nextX;
			this.nextY = nextY;
			this.nextZ = nextZ;
		}
	}

	/**
	 * Places the throne room and returns coordinates for the next structure piece.
	 *
	 * @param x The X coordinate where the throne room starts
	 * @param y The Y coordinate where the throne room starts
	 * @param z The Z coordinate where the throne room starts
	 * @return ThroneResult with placement status and next-piece start coords
	 */
	public ThroneResult placeThrone(World world, Random rand, int x, int y, int z) {
		// Adjust offsets if needed for alignment — these values depend on rotation.
		int throneX = x + 3;
		int throneY = y + 4;
		int throneZ = z + 26;

		boolean placed = new WorldFeatureNetherBridgeThrone(fortressBlocks)
			.place(world, rand, throneX, throneY, throneZ);

		// For chaining: assume next piece continues straight ahead
		// Throne is 7 blocks deep (z + 0 → z + 6), so we place next start just beyond it.
		int nextX = throneX;
		int nextY = throneY;
		int nextZ = throneZ + 7; // adjust if rotated

		return new ThroneResult(placed, nextX, nextY, nextZ);
	}
}
