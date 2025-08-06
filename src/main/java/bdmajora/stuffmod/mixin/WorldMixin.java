//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobSpider;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
	value = {World.class},
	remap = false
)
public class WorldMixin {
	public WorldMixin() {
	}

	@WrapOperation(
		method = {"getCubes"},
		at = {@At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/entity/Entity;collidesWithBlock(Lnet/minecraft/core/block/Block;I)Z"
		)}
	)
	public boolean getCubes(Entity e, Block<?> block, int metadata, Operation<Boolean> original) {
		return e instanceof MobSpider && block.equals(Blocks.COBWEB) ? false : (Boolean)original.call(new Object[]{e, block, metadata});
	}
}
