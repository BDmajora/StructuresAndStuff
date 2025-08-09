//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod;

import bdmajora.stuffmod.registrar.BlockModelRegistrar;
import bdmajora.stuffmod.registrar.EntityModelRegistrar;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import turniplabs.halplibe.util.ModelEntrypoint;

public class StuffModelEntrypoint implements ModelEntrypoint {

	public StuffModelEntrypoint() {
	}

	public void initBlockModels(BlockModelDispatcher dispatcher) {
		BlockModelRegistrar.registerBlockModels();
	}

	public void initItemModels(ItemModelDispatcher dispatcher) {
	}

	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		EntityModelRegistrar.registerEntityModels(dispatcher);
	}

	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
	}

	public void initBlockColors(BlockColorDispatcher dispatcher) {
	}
}
