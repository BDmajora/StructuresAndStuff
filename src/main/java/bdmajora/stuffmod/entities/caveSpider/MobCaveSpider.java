//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.caveSpider;

import bdmajora.stuffmod.ModConfig;
import net.minecraft.core.entity.monster.MobSpider;
import net.minecraft.core.world.World;

public class MobCaveSpider extends MobSpider {
	public MobCaveSpider(World world) {
		super(world);
		this.setSize(0.7F, 0.45F);
		this.scoreValue = ModConfig.cave_spider_score;
		this.attackStrength = ModConfig.cave_spider_attack_str;
		this.moveSpeed = ModConfig.cave_spider_move_speed.floatValue();
	}

	public int getMaxHealth() {
		return ModConfig.cave_spider_max_health;
	}
}
