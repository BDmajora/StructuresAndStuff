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
	private final NetherFortressGenerateBridgeEndNorth bridgeEndPlacerNorth;
	private final NetherFortressGenerateBridgeEndSouth bridgeEndPlacerSouth;
	private final NetherFortressGenerateBridgeCrossing crossingPlacerNorth;
	private final NetherFortressGenerateBridgeWest bridgePlacerWest;
	private final NetherFortressGenerateBridgeEast bridgePlacerEast;

	public NetherFortressGenerator(int range) {
		setRange(range);
		startPlacer = new NetherFortressStart(fortressBlocks);
		bridgePlacerNorth = new NetherFortressGenerateBridgeNorth(fortressBlocks, 0);
		bridgeEndPlacerNorth = new NetherFortressGenerateBridgeEndNorth(fortressBlocks, 0);
		bridgeEndPlacerSouth = new NetherFortressGenerateBridgeEndSouth(fortressBlocks, 180);
		crossingPlacerNorth = new NetherFortressGenerateBridgeCrossing(fortressBlocks, 0);
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

		// SOUTH — place end piece only, at entrance
		bridgeEndPlacerSouth.placeBridgeEnd(world, info.rand, info.x, info.y, info.z);

		// NORTH — place the original first north bridge
		int bridgeEndZNorth = bridgePlacerNorth.generateBridge(world, info.rand, info.x, info.y, info.z);

		// Place the crossing for this first north bridge
		crossingPlacerNorth.placeCrossing(world, info.rand, info.x, info.y, bridgeEndZNorth);

		// Add side bridges from this crossing
		bridgePlacerWest.generateBridge(world, info.rand, info.x, info.y, bridgeEndZNorth);
		bridgePlacerEast.generateBridge(world, info.rand, info.x, info.y, bridgeEndZNorth);

		// NO further north bridges — remove any looped continuation
	}


	private boolean shouldPlaceCrossing(Random rand) {
		return rand.nextBoolean(); // 50% chance
	}
}
