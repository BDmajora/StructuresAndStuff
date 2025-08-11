package bdmajora.stuffmod.mixin;

import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.NetherFortressGenLogic;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.NetherFortressGenerator;
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

	// Reuse generator instances for efficiency
	private final DirtPillarGenerator dirtPillarGenerator = new DirtPillarGenerator();
	private final NetherFortressGenerator netherFortressGenerator =
		new NetherFortressGenerator(FortressBlocks.DEFAULT);

	@Inject(method = "decorate", at = @At("TAIL"))
	private void injectCustomStructures(Chunk chunk, CallbackInfo ci) {
		IChunkProvider chunkProvider = world.getChunkProvider();
		int originChunkX = chunk.xPosition;
		int originChunkZ = chunk.zPosition;

		// Overworld dirt pillars
		if (isOverworldGenerator()) {
			if (!DirtPillarGenLogic.shouldGenerate(world)) {
				return; // skip most of the time
			}
			dirtPillarGenerator.generate(chunkProvider, world, originChunkX, originChunkZ);
		}

		// Nether fortress
		if (isNetherGenerator()) {
			if (!NetherFortressGenLogic.shouldGenerate(world, originChunkX, originChunkZ, NetherFortressGenerator.CHANCE_DENOMINATOR)) {
				return; // skip most of the time
			}
			netherFortressGenerator.generate(chunkProvider, world, originChunkX, originChunkZ);
		}


	}

	@Unique
	private boolean isOverworldGenerator() {
		return (Object) this instanceof ChunkGeneratorOverworld;
	}

	@Unique
	private boolean isNetherGenerator() {
		return (Object) this instanceof ChunkGeneratorNether;
	}
}
