//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.mixin;

import net.minecraft.core.block.BlockLogicCobweb;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobSpider;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
	value = {BlockLogicCobweb.class},
	remap = false
)
public class BlockCobwebMixin {
	public BlockCobwebMixin() {
	}

	@Inject(
		method = {"onEntityCollidedWithBlock"},
		at = {@At("HEAD")},
		cancellable = true
	)
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
		if (entity instanceof MobSpider) {
			ci.cancel();
		}

	}
}
