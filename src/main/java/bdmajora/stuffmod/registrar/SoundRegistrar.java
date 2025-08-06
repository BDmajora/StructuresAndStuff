package bdmajora.stuffmod.registrar;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.sound.SoundTypes;

public class SoundRegistrar {

	public static void registerSounds() {
		// Handle sound registrations for server environment
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
			SoundTypes.register("btm:mob.blaze.breathe");
			SoundTypes.register("btm:mob.blaze.hit");
			SoundTypes.register("btm:mob.blaze.death");
			SoundTypes.register("minecraft:disc_pig_step");
		}
	}
}
