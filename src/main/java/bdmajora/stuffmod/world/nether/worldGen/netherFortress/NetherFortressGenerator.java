package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressStart;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenerateBridgeNorth;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenerateBridgeSouth;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenerateBridgeEndNorth;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenerateBridgeEndSouth;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

public class NetherFortressGenerator extends LargeStructureGenerator {

	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;
	private final NetherFortressStart startPlacer;
	private final NetherFortressGenerateBridgeNorth bridgePlacerNorth;
	private final NetherFortressGenerateBridgeSouth bridgePlacerSouth;
	private final NetherFortressGenerateBridgeEndNorth bridgeEndPlacerNorth;
	private final NetherFortressGenerateBridgeEndSouth bridgeEndPlacerSouth;

	public NetherFortressGenerator(int range) {
		setRange(range);
		startPlacer = new NetherFortressStart(fortressBlocks);
		bridgePlacerNorth = new NetherFortressGenerateBridgeNorth(fortressBlocks, 0);
		bridgePlacerSouth = new NetherFortressGenerateBridgeSouth(fortressBlocks, 0);
		bridgeEndPlacerNorth = new NetherFortressGenerateBridgeEndNorth(fortressBlocks, 0);
		bridgeEndPlacerSouth = new NetherFortressGenerateBridgeEndSouth(fortressBlocks, 180);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		NetherFortressPlacementHelper.PlacementInfo info = NetherFortressPlacementHelper.getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return;

		boolean startPlaced = startPlacer.placeStart(world, info.rand, info.x, info.y, info.z);
		if (!startPlaced) return;

		// North bridge
		int bridgeEndZNorth = bridgePlacerNorth.generateBridge(world, info.rand, info.x, info.y, info.z);
		bridgeEndPlacerNorth.placeBridgeEnd(world, info.rand, info.x, info.y, bridgeEndZNorth);

		// South bridge
		int bridgeEndZSouth = bridgePlacerSouth.generateBridge(world, info.rand, info.x, info.y, info.z);
		bridgeEndPlacerSouth.placeBridgeEnd(world, info.rand, info.x, info.y, bridgeEndZSouth);
	}
}
