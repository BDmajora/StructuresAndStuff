package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.LargeStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenStep1;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenStep2;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.NetherFortressGenStep3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

public class NetherFortressGenerator extends LargeStructureGenerator {

	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;
	private final NetherFortressGenStep1 step1Placer;
	private final NetherFortressGenStep2 step2Placer;
	private final NetherFortressGenStep3 step3Placer;

	public NetherFortressGenerator(int range) {
		setRange(range);
		step1Placer = new NetherFortressGenStep1(fortressBlocks);
		step2Placer = new NetherFortressGenStep2(fortressBlocks);
		step3Placer = new NetherFortressGenStep3(fortressBlocks);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		NetherFortressPlacementHelper.PlacementInfo info = NetherFortressPlacementHelper.getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return;

		boolean startPlaced = step1Placer.placeStep1(world, info.rand, info.x, info.y, info.z);
		if (!startPlaced) return;

		boolean step2Placed = step2Placer.placeStep2(world, info.rand, info.x, info.y, info.z);
		if (!step2Placed) return;

		step3Placer.placeStep3(world, info.rand, info.x, info.y, info.z);
	}
}
