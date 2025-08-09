package bdmajora.stuffmod;

import bdmajora.stuffmod.blocks.StuffBlocks;
import bdmajora.stuffmod.items.StuffItems;
import bdmajora.stuffmod.registrar.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Properties;

public class StuffMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
	public static final String MOD_ID = "stuffmod";
	public static final Logger LOGGER = LoggerFactory.getLogger("stuffmod");

	public StuffMod() {}

	@Override
	public void onInitialize() {
		ModConfig.initialize();
		StuffBlocks.initBlocks();
		LOGGER.info("Structures and Stuff initialized.");
	}

	@Override
	public void beforeGameStart() {
		EntityRegistrar.registerEntities();
	}

	@Override
	public void afterGameStart() {
		MobSpawnableBlockRegistrar.registerFortressGuardSkeletonSpawnBlocks();
		MobSpawnRegistrar.registerMobSpawns();
		SoundRegistrar.registerSounds();
		MobInfoRegistrar.registerMobInfo();
	}

	@Override
	public void onRecipesReady() {
		RecipeRegistrar.registerRecipes();
	}

	public void initNamespaces() {}
}
