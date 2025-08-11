package bdmajora.stuffmod;
import bdmajora.stuffmod.Utils.ConfigHandlerExtended;
import bdmajora.stuffmod.Utils.UtilIdRegistrar;

import java.util.Properties;

public abstract class ModConfig {
	public static ConfigHandlerExtended config;

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
	public static double cave_spider_move_speed;
	public static int cave_spider_max_health;
	public static int fort_skeleton_score;
	public static int fort_skeleton_attack_str;
	public static double fort_skeleton_move_speed;
	public static int fort_skeleton_max_health;
	public static int fort_skeleton_ranged_chance;
	public static boolean blaze_variants;
	public static int blaze_attack_str;
	public static int blaze_score;
	public static double blaze_move_speed;
	public static int blaze_max_health;
	public static boolean blaze_fireball_deflection;

	private static void handleConfig() {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "1500");
		prop.setProperty("starting_item_id", "18000");
		prop.setProperty("structure.mineshaft.enabled", "true");
		prop.setProperty("structure.nether_fortress.enabled", "true");
		prop.setProperty("structure.mineshaft.rarity", "5");
		prop.setProperty("structure.nether_fortress.rarity", "5");
		prop.setProperty("structure.mineshaft.chest_rarity", "5");
		prop.setProperty("structure.nether_fortress.chest_rarity", "5");

		prop.setProperty("mob.blaze.variants", "false");
		prop.setProperty("mob.blaze.attack_str", "8");
		prop.setProperty("mob.blaze.score_value", "15");
		prop.setProperty("mob.blaze.move_speed", "0.32");
		prop.setProperty("mob.blaze.max_health", "20");
		prop.setProperty("mob.blaze.fireball_deflection", "false");

		prop.setProperty("mob.fortress_guard_skeleton.spawn_weight", "10");
		prop.setProperty("mob.fortress_guard_skeleton.pack_size", "4");
		prop.setProperty("mob.fortress_guard_skeleton.score_value", "10");
		prop.setProperty("mob.fortress_guard_skeleton.attack_str", "7");
		prop.setProperty("mob.fortress_guard_skeleton.move_speed", "0.3");
		prop.setProperty("mob.fortress_guard_skeleton.max_health", "20");
		prop.setProperty("mob.fortress_guard_skeleton.ranged_chance", "50");

		prop.setProperty("mob.cave_spider.score_value", "10");
		prop.setProperty("mob.cave_spider.attack_str", "6");
		prop.setProperty("mob.cave_spider.move_speed", "0.25");
		prop.setProperty("mob.cave_spider.max_health", "12");

		config = new ConfigHandlerExtended("stuffmod", prop);
		config.updateConfig();
	}

	public static void initialize() {
		handleConfig();

		starting_bl_id = config.getInt("starting_block_id");
		starting_entity_id = config.getInt("starting_item_id");

		mineshafts_enabled = config.getBoolean("structure.mineshaft.enabled");
		nether_forts_enabled = config.getBoolean("structure.nether_fortress.enabled");
		mineshaft_rarity = config.getInt("structure.mineshaft.rarity");
		netherfort_rarity = config.getInt("structure.nether_fortress.rarity");
		mineshaft_chest_rarity = config.getInt("structure.mineshaft.chest_rarity");
		netherfort_chest_rarity = config.getInt("structure.nether_fortress.chest_rarity");

		blaze_variants = config.getBoolean("mob.blaze.variants");
		blaze_attack_str = config.getInt("mob.blaze.attack_str");
		blaze_score = config.getInt("mob.blaze.score_value");
		blaze_move_speed = config.getDouble("mob.blaze.move_speed");
		blaze_max_health = config.getInt("mob.blaze.max_health");
		blaze_fireball_deflection = config.getBoolean("mob.blaze.fireball_deflection");

		fort_guard_spawn_weight = config.getInt("mob.fortress_guard_skeleton.spawn_weight");
		fort_guard_pack_size = config.getInt("mob.fortress_guard_skeleton.pack_size");
		fort_skeleton_score = config.getInt("mob.fortress_guard_skeleton.score_value");
		fort_skeleton_attack_str = config.getInt("mob.fortress_guard_skeleton.attack_str");
		fort_skeleton_move_speed = config.getDouble("mob.fortress_guard_skeleton.move_speed");
		fort_skeleton_max_health = config.getInt("mob.fortress_guard_skeleton.max_health");
		fort_skeleton_ranged_chance = config.getInt("mob.fortress_guard_skeleton.ranged_chance");

		cave_spider_score = config.getInt("mob.cave_spider.score_value");
		cave_spider_attack_str = config.getInt("mob.cave_spider.attack_str");
		cave_spider_move_speed = config.getDouble("mob.cave_spider.move_speed");
		cave_spider_max_health = config.getInt("mob.cave_spider.max_health");

		UtilIdRegistrar.initIds(starting_bl_id, starting_entity_id);
	}
}
