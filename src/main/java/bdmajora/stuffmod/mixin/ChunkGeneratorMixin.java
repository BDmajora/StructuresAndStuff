////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package bdmajora.stuffmod.mixins;
//
//import net.minecraft.core.world.World;
//import net.minecraft.core.world.chunk.Chunk;
//import net.minecraft.core.world.generate.chunk.ChunkGenerator;
//import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkGeneratorNether;
//import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkGeneratorOverworld;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(
//	value = {ChunkGenerator.class},
//	remap = false
//)
//public class ChunkGeneratorMixin {
//	@Unique
//	private final MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
//	@Unique
//	private final MapGenNetherBridge fortGenerator = new MapGenNetherBridge();
//	@Shadow
//	@Final
//	protected World world;
//
//	public ChunkGeneratorMixin() {
//	}
//
//	@Inject(
//		method = {"generate"},
//		at = {@At("TAIL")}
//	)
//	public final void generate(int chunkX, int chunkZ, CallbackInfoReturnable<Chunk> cir) {
//		if (isOverworldGenerator()) {
//			this.mineshaftGenerator.generate(this.world.getChunkProvider(), this.world, chunkX, chunkZ);
//		} else if (isNetherGenerator()) {
//			this.fortGenerator.generate(this.world.getChunkProvider(), this.world, chunkX, chunkZ);
//		}
//	}
//
//	@Inject(
//		method = {"decorate"},
//		at = {@At("HEAD")}
//	)
//	public final void decorate(Chunk chunk, CallbackInfo ci) {
//		if (isOverworldGenerator()) {
//			this.mineshaftGenerator.generateStructuresInChunk(this.world, this.world.rand, chunk.xPosition, chunk.zPosition);
//		} else if (isNetherGenerator()) {
//			this.fortGenerator.generateStructuresInChunk(this.world, this.world.rand, chunk.xPosition, chunk.zPosition);
//		}
//	}
//
//	@Unique
//	private boolean isOverworldGenerator() {
//		return (Object) this instanceof ChunkGeneratorOverworld;
//	}
//
//	@Unique
//	private boolean isNetherGenerator() {
//		return (Object) this instanceof ChunkGeneratorNether;
//	}
//
//
//}
