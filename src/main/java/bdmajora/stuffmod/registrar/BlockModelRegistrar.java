package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.blocks.StuffBlocks;
import net.minecraft.client.render.block.model.BlockModelFence;
import net.minecraft.client.render.block.model.BlockModelSlab;
import net.minecraft.client.render.block.model.BlockModelStairs;
import net.minecraft.client.render.block.model.BlockModelStandard;
import turniplabs.halplibe.helper.ModelHelper;

public class BlockModelRegistrar {

	public static void registerBlockModels() {
		//Minecraft 1.0 (The Adventure Update)


		ModelHelper.setBlockModel(StuffBlocks.nether_brick, () -> {
			return (new BlockModelStandard(StuffBlocks.nether_brick)).setAllTextures(0, "btm:block/nether_bricks");
		});
		ModelHelper.setBlockModel(StuffBlocks.nether_brick_slabs, () -> {
			return (new BlockModelSlab(StuffBlocks.nether_brick_slabs)).setAllTextures(0, "btm:block/nether_bricks");
		});
		ModelHelper.setBlockModel(StuffBlocks.nether_brick_stairs, () -> {
			return (new BlockModelStairs(StuffBlocks.nether_brick_stairs)).setAllTextures(0, "btm:block/nether_bricks");
		});
		ModelHelper.setBlockModel(StuffBlocks.nether_brick_fence, () -> {
			return (new BlockModelFence(StuffBlocks.nether_brick_fence)).setAllTextures(0, "btm:block/nether_bricks");
		});
	}
}
