//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.sound.SoundRepository;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class StuffClientEntrypoint implements ClientModInitializer, ClientStartEntrypoint {
	public StuffClientEntrypoint() {
	}

	public void onInitializeClient() {
	}

	public void beforeClientStart() {
	}

	public void afterClientStart() {
		SoundRepository.registerNamespace("stuffmod");
	}
}
