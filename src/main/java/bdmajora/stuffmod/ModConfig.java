package bdmajora.stuffmod;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import com.google.common.io.Resources;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Block;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public abstract class ModConfig {
	private static final String MOD_CONFIG_PATH = FabricLoader.getInstance().getGameDir().toString() + "/config/stuff.toml";
	private static final CommentedConfig dconfig;
	private static final FileConfig tconfig;

	public static int starting_bl_id;
	public static int starting_entity_id;
	public static boolean mineshafts_enabled;
	public static boolean nether_forts_enabled;
	public static int mineshaft_rarity;
	public static int netherfort_rarity;
	public static int mineshaft_chest_rarity;
	public static int netherfort_chest_rarity;
	public static int fort_guard_spawn_weight;
	public static int fort_guard_pack_size;
	public static int cave_spider_score;
	public static int cave_spider_attack_str;
	public static Double cave_spider_move_speed;
	public static int cave_spider_max_health;
	public static int fort_skeleton_score;
	public static int fort_skeleton_attack_str;
	public static Double fort_skeleton_move_speed;
	public static int fort_skeleton_max_health;
	public static int fort_skeleton_ranged_chance;
	public static boolean blaze_variants;
	public static int blaze_attack_str;
	public static int blaze_score;
	public static Double blaze_move_speed;
	public static int blaze_max_health;
	public static boolean blaze_fireball_deflection;

	public ModConfig() {
	}

	public static <T> T get(String option) {
		T value = tconfig.get(option);
		if (value != null) {
			return value;
		} else {
			StuffMod.LOGGER.warn("You have a missing option in your config, so we fetched a value from the current default config.\nthis is likely due to an outdated config or an accidentally deleted line\nIf you would like to see all options, remove your current config and the mod will replace it.\nIf you have set options you wish to keep, you can back up the current config and manually merge it with the new version");
			StuffMod.LOGGER.warn("Option name: " + option);
			return dconfig.get(option);
		}
	}

	public static void initialize() {
		tconfig.load();

		starting_bl_id = get("starting_block_id") != null ? get("starting_block_id") : 1500;
		starting_entity_id = get("starting_item_id") != null ? get("starting_item_id") : 18000;

		mineshafts_enabled = Boolean.TRUE.equals(get("structure.mineshaft.enabled"));
		nether_forts_enabled = Boolean.TRUE.equals(get("structure.nether_fortress.enabled"));
		mineshaft_rarity = get("structure.mineshaft.rarity") != null ? get("structure.mineshaft.rarity") : 5;
		netherfort_rarity = get("structure.nether_fortress.rarity") != null ? get("structure.nether_fortress.rarity") : 5;
		mineshaft_chest_rarity = get("structure.mineshaft.chest_rarity") != null ? get("structure.mineshaft.chest_rarity") : 5;
		netherfort_chest_rarity = get("structure.nether_fortress.chest_rarity") != null ? get("structure.nether_fortress.chest_rarity") : 5;
		blaze_variants = Boolean.TRUE.equals(get("mob.blaze.variants"));

		fort_guard_spawn_weight = get("mob.fortress_guard_skeleton.spawn_weight") != null ? get("mob.fortress_guard_skeleton.spawn_weight") : 10;
		fort_guard_pack_size = get("mob.fortress_guard_skeleton.pack_size") != null ? get("mob.fortress_guard_skeleton.pack_size") : 4;
		cave_spider_score = get("mob.cave_spider.score_value") != null ? get("mob.cave_spider.score_value") : 10;
		cave_spider_attack_str = get("mob.cave_spider.attack_str") != null ? get("mob.cave_spider.attack_str") : 6;
		cave_spider_max_health = get("mob.cave_spider.max_health") != null ? get("mob.cave_spider.max_health") : 12;
		cave_spider_move_speed = get("mob.cave_spider.move_speed") != null ? get("mob.cave_spider.move_speed") : 0.25;

		fort_skeleton_score = get("mob.fortress_guard_skeleton.score_value") != null ? get("mob.fortress_guard_skeleton.score_value") : 10;
		fort_skeleton_attack_str = get("mob.fortress_guard_skeleton.attack_str") != null ? get("mob.fortress_guard_skeleton.attack_str") : 7;
		fort_skeleton_move_speed = get("mob.fortress_guard_skeleton.move_speed") != null ? get("mob.fortress_guard_skeleton.move_speed") : 0.3;
		fort_skeleton_max_health = get("mob.fortress_guard_skeleton.max_health") != null ? get("mob.fortress_guard_skeleton.max_health") : 20;
		fort_skeleton_ranged_chance = get("mob.fortress_guard_skeleton.ranged_chance") != null ? get("mob.fortress_guard_skeleton.ranged_chance") : 50;

		blaze_attack_str = get("mob.blaze.attack_str") != null ? get("mob.blaze.attack_str") : 8;
		blaze_score = get("mob.blaze.score_value") != null ? get("mob.blaze.score_value") : 15;
		blaze_move_speed = get("mob.blaze.move_speed") != null ? get("mob.blaze.move_speed") : 0.32;
		blaze_max_health = get("mob.blaze.max_health") != null ? get("mob.blaze.max_health") : 20;
		blaze_fireball_deflection = Boolean.TRUE.equals(get("mob.blaze.fireball_deflection"));

		// Register IDs
		UtilIdRegistrar.initIds(starting_bl_id, starting_entity_id);
	}

	static {
		try {
			String f_contents = Resources.toString(ModConfig.class.getResource("/assets/stuff/default_config.toml"), StandardCharsets.UTF_8);
			dconfig = (CommentedConfig)(new TomlParser()).parse(f_contents);
		} catch (IOException var1) {
			throw new RuntimeException(var1);
		}

		tconfig = FileConfig.builder(MOD_CONFIG_PATH).defaultResource("/assets/stuff/default_config.toml").sync().build();
	}
}
