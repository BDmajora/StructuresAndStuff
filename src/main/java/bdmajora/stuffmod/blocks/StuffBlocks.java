package bdmajora.stuffmod.blocks;

import bdmajora.stuffmod.UtilIdRegistrar;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import turniplabs.halplibe.helper.BlockBuilder;
import net.minecraft.core.item.block.ItemBlockSlab;

public class StuffBlocks {


	//Nether
	public static Block<BlockLogic> nether_brick;
	public static Block<BlockLogicFence> nether_brick_fence;
	public static Block<BlockLogicStairs> nether_brick_stairs;
	public static Block<BlockLogicSlab> nether_brick_slabs;

	public static void initBlocks() {

		BlockBuilder nether_brick_builder = (new BlockBuilder("btm"))
			.setHardness(3.0F)
			.setResistance(10.0F)
			.addTags(new Tag[]{BlockTags.MINEABLE_BY_PICKAXE});

		// Initialize blocks
		nether_brick = nether_brick_builder.clone().addTags(new Tag[]{BlockTags.FENCES_CONNECT})
			.build("brick.fortress", "fortress_brick", UtilIdRegistrar.nextIdBlock(), (b) -> new BlockLogic(b, Material.stone));

		nether_brick_slabs = nether_brick_builder.clone().setUseInternalLight().setVisualUpdateOnMetadata()
			.setBlockItem(ItemBlockSlab::new)
			.build("slab.brick.fortress", "fortress_brick_slab", UtilIdRegistrar.nextIdBlock(), (b) -> new BlockLogicSlab(b, nether_brick));

		nether_brick_stairs = nether_brick_builder.clone().setUseInternalLight().setVisualUpdateOnMetadata()
			.build("stairs.brick.fortress", "fortress_brick_stairs", UtilIdRegistrar.nextIdBlock(), (b) -> new BlockLogicStairs(b, nether_brick));

		nether_brick_fence = nether_brick_builder.clone().addTags(new Tag[]{BlockTags.FENCES_CONNECT})
			.build("fence.brick.fortress", "fortress_brick_fence", UtilIdRegistrar.nextIdBlock(), BlockLogicStoneFence::new);

		// Add blocks to tags
		BlockTags.NETHER_MOBS_SPAWN.tag(nether_brick);
	}
}
