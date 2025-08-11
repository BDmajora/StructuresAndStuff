package bdmajora.stuffmod.mixin;

import bdmajora.stuffmod.world.overworld.worldGen.DirtPillarGenLogic;
import bdmajora.stuffmod.world.overworld.worldGen.DirtPillarGenerator;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.provider.IChunkProvider;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkGeneratorNether;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkGeneratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChunkGenerator.class, remap = false)
public abstract class MixinChunkGenerator {

	@Shadow @Final
	protected World world;

	// Reuse the generator instance for efficiency
	private final DirtPillarGenerator generator = new DirtPillarGenerator();

	@Inject(method = "decorate", at = @At("TAIL"))
	private void injectDirtPillars(Chunk chunk, CallbackInfo ci) {
		if (isOverworldGenerator()) {
			if (!DirtPillarGenLogic.shouldGenerate(world)) {
				return; // skip most of the time
			}

			IChunkProvider chunkProvider = world.getChunkProvider();

			int originChunkX = chunk.xPosition;
			int originChunkZ = chunk.zPosition;

			generator.generate(chunkProvider, world, originChunkX, originChunkZ);
		}
	}


	@Unique
	private boolean isOverworldGenerator() {
		return (Object) this instanceof ChunkGeneratorOverworld;
	}

	@Unique
	@SuppressWarnings("unused")
	private boolean isNetherGenerator() {
		return (Object) this instanceof ChunkGeneratorNether;
	}
}
