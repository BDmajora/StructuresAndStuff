//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobSpider;
import net.minecraft.core.world.pathfinder.PathFinder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
	value = {PathFinder.class},
	remap = false
)
public class PathFinderMixin {
	public PathFinderMixin() {
	}

	@WrapOperation(
		method = {"isFree"},
		at = {@At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/block/material/Material;blocksMotion()Z"
		)}
	)
	private boolean isFree(Material instance, Operation<Boolean> original, @Local(argsOnly = true) Entity e) {
		return e instanceof MobSpider && instance == Material.web ? false : (Boolean)original.call(new Object[]{instance});
	}
}
