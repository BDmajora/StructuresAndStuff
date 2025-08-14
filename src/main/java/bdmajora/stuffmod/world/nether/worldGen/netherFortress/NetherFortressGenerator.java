package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressStart;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenerateBridge;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenerateBridgeEnd;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

public class NetherFortressGenerator extends LargeStructureGenerator {

	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;
	private final NetherFortressStart startPlacer;
	private final NetherFortressGenerateBridge bridgePlacer;
	private final NetherFortressGenerateBridgeEnd bridgeEndPlacer;

	public NetherFortressGenerator(int range) {
		setRange(range);
		startPlacer = new NetherFortressStart(fortressBlocks);
		bridgePlacer = new NetherFortressGenerateBridge(fortressBlocks);
		bridgeEndPlacer = new NetherFortressGenerateBridgeEnd(fortressBlocks);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		NetherFortressPlacementHelper.PlacementInfo info = NetherFortressPlacementHelper.getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return;

		boolean startPlaced = startPlacer.placeStart(world, info.rand, info.x, info.y, info.z);
		if (!startPlaced) return;

		boolean bridgePlaced = bridgePlacer.generateBridge(world, info.rand, info.x, info.y, info.z);
		if (!bridgePlaced) return;

		bridgeEndPlacer.placeBridgeEnd(world, info.rand, info.x, info.y, info.z);
	}
}
