//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.blaze;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.entities.blazeFireball.ProjectileBlazeFireball;
import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

public class MobBlaze extends MobMonster {
	private final int attackStrength;
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	private int skin_variant = 0;
	private int field_40128_g;

	public MobBlaze(World world) {
		super(world);
		this.fireImmune = true;
		this.attackStrength = ModConfig.blaze_attack_str;
		this.scoreValue = ModConfig.blaze_score;
		this.moveSpeed = (float) ModConfig.blaze_move_speed;
		int r = this.random.nextInt(3000);
		if (r == 0) {
			this.skin_variant = 3;
		} else if (r < 1000) {
			this.skin_variant = this.random.nextBoolean() ? 1 : 2;
		} else {
			this.skin_variant = 0;
		}

		this.mobDrops.clear();
		this.mobDrops.add(new WeightedRandomLootObject(Blocks.FIRE.asItem().getDefaultStack(), 0, 2));
	}

	public int getMaxHealth() {
		return ModConfig.blaze_max_health;
	}

	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(16, 0, Integer.class);
	}

	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.skin_variant = tag.getInteger("sb_skinvar");
	}

	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("sb_skinvar", this.skin_variant);
	}

	public boolean hurt(Entity attacker, int i, DamageType type) {
		return super.hurt(attacker, i, type);
	}

	public String getEntityTexture() {
		return ModConfig.blaze_variants ? "/assets/stuffmod/textures/entity/blaze/fire" + this.skin_variant + ".png" : this.getDefaultEntityTexture();
	}

	public @NotNull String getDefaultEntityTexture() {
		return "/assets/stuffmod/textures/entity/blaze/fire0.png";
	}

	public String getLivingSound() {
		return "stuffmod:mob.blaze.breathe";
	}

	protected String getHurtSound() {
		return "stuffmod:mob.blaze.hit";
	}

	protected String getDeathSound() {
		return "stuffmod:mob.blaze.death";
	}

	public float getBrightness(float partialTick) {
		return 0.75F;
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.world.isClientSide) {
			if (this.isInWaterOrRain()) {
				this.hurt((Entity)null, 1, DamageType.DROWN);
			}

			--this.heightOffsetUpdateTime;
			if (this.heightOffsetUpdateTime <= 0) {
				this.heightOffsetUpdateTime = 100;
				this.heightOffset = 0.5F + (float)this.random.nextGaussian() * 3.0F;
			}

			if (this.target != null && this.target.y + (double)this.target.getHeadHeight() > this.y + (double)this.getHeadHeight() + (double)this.heightOffset) {
				this.yd += (0.3 - this.yd) * 0.3;
			}
		}

		if (!this.onGround && this.yd < 0.0) {
			this.yd *= 0.6;
		}

		if (this.random.nextInt(24) == 0 && this.world.isClientSide) {
			this.world.playSoundAtEntity((Entity)null, this, "fire.fire", 0.8F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
		}

		if (this.target != null) {
			this.lookAt(this.target, 30.0F, 30.0F);
		}

		for(int var1 = 0; var1 < 2; ++var1) {
			this.world.spawnParticle("largesmoke", this.x + (this.random.nextDouble() - 0.5) * (double)this.bbWidth, this.y + this.random.nextDouble() * (double)this.bbHeight, this.z + (this.random.nextDouble() - 0.5) * (double)this.bbWidth, 0.0, 0.0, 0.0, 0);
		}

	}

	protected void attackEntity(Entity entity, float distance) {
		if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackTime = 20;
			entity.hurt(this, this.attackStrength, DamageType.FIRE);
		} else if (distance < 30.0F) {
			double x_diff = entity.x - this.x;
			double y_diff = entity.bb.minY + (double)(entity.bbHeight / 2.0F) - (this.y + (double)(this.bbHeight / 2.0F));
			double z_diff = entity.z - this.z;
			if (this.attackTime == 0) {
				++this.field_40128_g;
				if (this.field_40128_g == 1) {
					this.attackTime = 60;
					this.setBurning(true);
				} else if (this.field_40128_g <= 4) {
					this.attackTime = 6;
				} else {
					this.attackTime = 100;
					this.field_40128_g = 0;
					this.setBurning(false);
				}

				if (this.field_40128_g > 1) {
					float half_sqrt_dist = MathHelper.sqrt_float(distance) * 0.5F;
					if (!Global.isServer) {
						this.world.playSoundAtEntity((Entity)null, this, "mob.ghast.fireball", 1.0F, this.random.nextFloat() * 0.2F + 0.7F);
					}

					ProjectileBlazeFireball fireball = new ProjectileBlazeFireball(this.world, this, x_diff + this.random.nextGaussian() * (double)half_sqrt_dist, y_diff, z_diff + this.random.nextGaussian() * (double)half_sqrt_dist);
					fireball.owner = this;
					fireball.y = this.y + (double)(this.bbHeight / 2.0F) + 0.5;
					this.world.entityJoinedWorld(fireball);
				}
			}

			this.newRotationYaw = (double)((float)(Math.atan2(z_diff, x_diff) * 180.0 / 3.1415927410125732) - 90.0F);
			this.hasAttacked = true;
		}

	}

	protected void causeFallDamage(float f) {
	}

	public boolean isOnFire() {
		return this.getBurning();
	}

	public boolean getBurning() {
		return (this.entityData.getInt(16) & 1) != 0;
	}

	public void setBurning(boolean burning) {
		int b = this.entityData.getInt(16);
		if (burning) {
			b |= 1;
		} else {
			b &= -2;
		}

		this.entityData.set(16, b);
	}

	public boolean canSpawnHere() {
		int z = (int)Math.floor(this.z);
		int y = (int)Math.floor(this.y);
		int x = (int)Math.floor(this.x);
		if (this.world.getBlockId(x, y, z) != 0) {
			return false;
		} else {
			int blockLight = this.world.getSavedLightValue(LightLayer.Block, x, y, z);
			if (blockLight > 11) {
				return false;
			} else {
				return this.world.checkIfAABBIsClear(this.bb) && this.world.getCubes(this, this.bb).isEmpty() && !this.world.getIsAnyLiquid(this.bb);
			}
		}
	}
}
