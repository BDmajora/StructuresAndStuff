package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.*;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

import java.util.Random;

/**
 * Fortress generator that chains steps by passing forward the exact
 * coordinates produced by the previous step—without introducing any
 * new shared base types in your codebase.
 */
public class NetherFortressGenerator extends LargeStructureGenerator {

	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;

	private final NetherFortressStart startPlacer;
	private final NetherFortressGenerateBridgeNorth bridgePlacerNorth;
	private final NetherFortressGenerateBridgeEndSouth bridgeEndPlacerSouth;
	private final NetherFortressGenerateBridgeCrossing crossingPlacerNorth;
	private final NetherFortressGenerateBridgeWest bridgePlacerWest;
	private final NetherFortressGenerateBridgeEast bridgePlacerEast;
	private final NetherFortressGenerateBridgeThrone throneRoomPlacer;

	// Local-only coord holder to pass precise positions between steps.
	private static final class Coord {
		final int x, y, z;
		Coord(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }
	}

	public NetherFortressGenerator(int range) {
		setRange(range);

		startPlacer = new NetherFortressStart(fortressBlocks);

		// The rotations you already use:
		bridgePlacerNorth = new NetherFortressGenerateBridgeNorth(fortressBlocks, 0);
		bridgeEndPlacerSouth = new NetherFortressGenerateBridgeEndSouth(fortressBlocks, 180);
		crossingPlacerNorth = new NetherFortressGenerateBridgeCrossing(fortressBlocks, 0);
		bridgePlacerWest = new NetherFortressGenerateBridgeWest(fortressBlocks, 90);
		bridgePlacerEast = new NetherFortressGenerateBridgeEast(fortressBlocks, 90);

		// Throne room — facing north (rotation 0)
		throneRoomPlacer = new NetherFortressGenerateBridgeThrone(fortressBlocks, 0);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		NetherFortressPlacementHelper.PlacementInfo info =
			NetherFortressPlacementHelper.getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return;

		Random rand = info.rand;

		// Anchor: the start piece is your origin for attachments.
		Coord start = new Coord(info.x, info.y, info.z);

		// 1) Place START
		boolean startPlaced = startPlacer.placeStart(world, rand, start.x, start.y, start.z);
		if (!startPlaced) return;

		// 2) SOUTH — entrance capping/end
		bridgeEndPlacerSouth.placeBridgeEnd(world, rand, start.x, start.y, start.z);

		// 3) NORTH — main bridge from START
		int endZOfNorthBridge = bridgePlacerNorth.generateBridge(world, rand, start.x, start.y, start.z);
		Coord northBridgeEnd = new Coord(start.x, start.y, endZOfNorthBridge);

		// 4) CROSSING at end of north bridge
		crossingPlacerNorth.placeCrossing(world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z);

		// 5a) WEST bridge from crossing
		bridgePlacerWest.generateBridge(world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z);

		// 5b) EAST bridge from crossing
		bridgePlacerEast.generateBridge(world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z);

		// 6) THRONE ROOM directly north of crossing
		throneRoomPlacer.placeThrone(
			world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z
		);

		// Done
	}
}
