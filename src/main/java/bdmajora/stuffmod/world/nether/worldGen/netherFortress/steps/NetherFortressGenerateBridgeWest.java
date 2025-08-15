package bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldFeatures.netherFortress.WorldFeatureNetherBridgeStraight;
import net.minecraft.core.world.World;

import java.util.Random;

/**
 * Generates a variable-length westward bridge and returns the final segment's coordinates.
 */
public class NetherFortressGenerateBridgeWest {

	private final FortressBlocks fortressBlocks;
	private final int rotation;

	public NetherFortressGenerateBridgeWest(FortressBlocks blocks, int rotation) {
		this.fortressBlocks = blocks;
		this.rotation = 90; // West uses 90° rotation for block orientation
	}

	/**
	 * Holds both placement success and coordinates of the final bridge segment.
	 */
	public static class BridgeResult {
		public final boolean placedAny;
		public final int endX;
		public final int endY;
		public final int endZ;

		public BridgeResult(boolean placedAny, int endX, int endY, int endZ) {
			this.placedAny = placedAny;
			this.endX = endX;
			this.endY = endY;
			this.endZ = endZ;
		}
	}

	/**
	 * Generates a variable-length bridge going west and returns the coordinates of the last segment.
	 */
	public BridgeResult generateBridge(World world, Random rand, int x, int y, int z) {
		final int bridgeLength = 19;
		int bridgeCount = 2 + rand.nextInt(3); // 2–4 segments

		int startX = x + 2; // WEST offset
		int startZ = z + 20;

		WorldFeatureNetherBridgeStraight bridge = new WorldFeatureNetherBridgeStraight(fortressBlocks, rotation);

		boolean placedAny = false;
		for (int i = 0; i < bridgeCount; i++) {
			boolean placed = bridge.place(world, rand, startX - (i * bridgeLength), y, startZ);
			placedAny = placedAny || placed;
		}

		int endX = startX - ((bridgeCount - 1) * bridgeLength);
		return new BridgeResult(placedAny, endX, y, startZ);
	}
}
