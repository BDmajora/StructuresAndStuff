package bdmajora.stuffmod.world.nether.blockPicking.netherFortress;

import java.util.ArrayList;

import bdmajora.stuffmod.blocks.StuffBlocks;
import net.minecraft.core.block.Block;
import org.jetbrains.annotations.NotNull;

public class FortressBlocks {
	private static final ArrayList<FortressBlocks> choices = new ArrayList<>();
	public static String spawner_mob;
	public Block<?> brick;
	public Block<?> fence;
	public Block<?> stair;

	// ✅ Static default instance
	public static final FortressBlocks DEFAULT = new FortressBlocks(
		StuffBlocks.nether_brick,
		StuffBlocks.nether_brick_fence,
		StuffBlocks.nether_brick_stairs
	);

	public FortressBlocks(@NotNull Block<?> brick, @NotNull Block<?> fence, @NotNull Block<?> stair) {
		this.brick = StuffBlocks.nether_brick;
		this.fence = StuffBlocks.nether_brick_fence;
		this.stair = StuffBlocks.nether_brick_stairs;
		choices.add(this);
	}
}
