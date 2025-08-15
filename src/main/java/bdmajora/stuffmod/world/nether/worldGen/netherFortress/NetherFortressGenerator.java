package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.*;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

import java.util.Random;

public class NetherFortressGenerator extends LargeStructureGenerator {

	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;
	private final NetherFortressStart startPlacer;
	private final NetherFortressGenerateBridgeNorth bridgePlacerNorth;
	private final NetherFortressGenerateBridgeSouth bridgePlacerSouth;
	private final NetherFortressGenerateBridgeEndNorth bridgeEndPlacerNorth;
	private final NetherFortressGenerateBridgeEndSouth bridgeEndPlacerSouth;
	private final NetherFortressGenerateBridgeCrossingNorth crossingPlacerNorth;
	private final NetherFortressGenerateBridgeCrossingSouth crossingPlacerSouth;
	private final NetherFortressGenerateBridgeWest bridgePlacerWest;
	private final NetherFortressGenerateBridgeEast bridgePlacerEast;

	public NetherFortressGenerator(int range) {
		setRange(range);
		startPlacer = new NetherFortressStart(fortressBlocks);
		bridgePlacerNorth = new NetherFortressGenerateBridgeNorth(fortressBlocks, 0);
		bridgePlacerSouth = new NetherFortressGenerateBridgeSouth(fortressBlocks, 0);
		bridgeEndPlacerNorth = new NetherFortressGenerateBridgeEndNorth(fortressBlocks, 0);
		bridgeEndPlacerSouth = new NetherFortressGenerateBridgeEndSouth(fortressBlocks, 180);
		crossingPlacerNorth = new NetherFortressGenerateBridgeCrossingNorth(fortressBlocks, 0);
		crossingPlacerSouth = new NetherFortressGenerateBridgeCrossingSouth(fortressBlocks, 0);
		bridgePlacerWest = new NetherFortressGenerateBridgeWest(fortressBlocks, 90);
		bridgePlacerEast = new NetherFortressGenerateBridgeEast(fortressBlocks, 90);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		NetherFortressPlacementHelper.PlacementInfo info =
			NetherFortressPlacementHelper.getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return;

		boolean startPlaced = startPlacer.placeStart(world, info.rand, info.x, info.y, info.z);
		if (!startPlaced) return;

		// North bridge
		int bridgeEndZNorth = bridgePlacerNorth.generateBridge(world, info.rand, info.x, info.y, info.z);
		if (shouldPlaceCrossing(info.rand)) {
			crossingPlacerNorth.placeCrossing(world, info.rand, info.x, info.y, bridgeEndZNorth);
			// Add West and East bridges from the crossing
			bridgePlacerWest.generateBridge(world, info.rand, info.x, info.y, bridgeEndZNorth);
			bridgePlacerEast.generateBridge(world, info.rand, info.x, info.y, bridgeEndZNorth);
		} else {
			bridgeEndPlacerNorth.placeBridgeEnd(world, info.rand, info.x, info.y, bridgeEndZNorth);
		}

		// South bridge
		int bridgeEndZSouth = bridgePlacerSouth.generateBridge(world, info.rand, info.x, info.y, info.z);
		if (shouldPlaceCrossing(info.rand)) {
			crossingPlacerSouth.placeCrossing(world, info.rand, info.x, info.y, bridgeEndZSouth);
			// Add West and East bridges from the crossing
			bridgePlacerWest.generateBridge(world, info.rand, info.x, info.y, bridgeEndZSouth);
			bridgePlacerEast.generateBridge(world, info.rand, info.x, info.y, bridgeEndZSouth);
		} else {
			bridgeEndPlacerSouth.placeBridgeEnd(world, info.rand, info.x, info.y, bridgeEndZSouth);
		}
	}

	private boolean shouldPlaceCrossing(Random rand) {
		// 50% chance — tweak as needed
		return rand.nextBoolean();
	}
}
