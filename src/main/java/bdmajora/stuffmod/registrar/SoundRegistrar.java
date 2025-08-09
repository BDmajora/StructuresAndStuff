package bdmajora.stuffmod.registrar;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.sound.SoundTypes;

public class SoundRegistrar {

	public static void registerSounds() {
		// Handle sound registrations for server environment
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
			SoundTypes.register("stuffmod:mob.blaze.breathe");
			SoundTypes.register("stuffmod:mob.blaze.hit");
			SoundTypes.register("stuffmod:mob.blaze.death");
			SoundTypes.register("stuffmod:mob.disc.disc_pig_step");
		}
	}
}
