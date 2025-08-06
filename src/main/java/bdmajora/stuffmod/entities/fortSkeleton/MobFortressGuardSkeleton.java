//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.fortSkeleton;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.blocks.ModBlockTags;
import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobZombie;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobFortressGuardSkeleton extends MobZombie {
	private static final ItemStack meleeItem;
	private static final ItemStack rangedItem;
	private int ranged = 0;

	public MobFortressGuardSkeleton(World world) {
		super(world);
		this.textureIdentifier = NamespaceID.getPermanent("btm", "fort_skeleton");
		this.fireImmune = true;
		this.scoreValue = ModConfig.fort_skeleton_score;
		this.moveSpeed = ModConfig.fort_skeleton_move_speed.floatValue();
		if (this.random.nextInt(ModConfig.fort_skeleton_ranged_chance) == 0) {
			this.ranged = 1;
		}

		this.mobDrops.clear();
		this.mobDrops.add(new WeightedRandomLootObject(Items.COAL.getDefaultStack(), 0, 1));
		this.mobDrops.add(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 0, 2));
		this.mobDrops.add(new WeightedRandomLootObject(Items.AMMO_ARROW.getDefaultStack(), 0, 2));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(15, this.attackTime, Integer.class);
		this.entityData.define(16, this.ranged, Integer.class);
		this.entityData.define(17, 0, Integer.class);
	}

	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("ranged", this.ranged == 1);
	}

	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		this.ranged = tag.getBoolean("ranged") ? 1 : 0;
	}

	public void onLivingUpdate() {
		if (this.world.isClientSide) {
			this.attackTime = this.entityData.getInt(15);
			this.ranged = this.entityData.getInt(16);
		} else {
			this.entityData.set(16, this.ranged);
			this.entityData.set(15, this.attackTime);
		}

		super.onLivingUpdate();
	}

	public int getMaxHealth() {
		return ModConfig.fort_skeleton_max_health;
	}

	protected void rangedAttackEntity(@NotNull Entity entity, float distance) {
		if (distance < 10.0F) {
			double d = entity.x - this.x;
			double d1 = entity.z - this.z;
			if (this.attackTime == 0) {
				if (!this.world.isClientSide) {
					ProjectileArrow arrow = new ProjectileArrow(this.world, this, false, 0);
					arrow.remainingFireTicks = 300;
					arrow.maxFireTicks = this.remainingFireTicks;
					double d2 = entity.y + (double)entity.getHeadHeight() - 0.2 - arrow.y;
					float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
					this.world.playSoundAtEntity((Entity)null, this, "random.bow", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
					arrow.setHeading(d, d2 + (double)f1, d1, 0.6F, 12.0F);
					this.world.entityJoinedWorld(arrow);
					if (this.random.nextInt(15) == 0) {
						this.ranged = 0;
						this.setBurning(true);
						++this.moveSpeed;
					}
				}

				this.attackTime = 25;
			}

			this.yRot = (float)(Math.atan2(d1, d) * 180.0 / Math.PI) - 90.0F;
			this.hasAttacked = true;
		}

	}

	protected void meleeAttackEntity(@NotNull Entity entity, float distance) {
		if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackTime = 15;
			entity.hurt(this, ModConfig.fort_skeleton_attack_str, DamageType.COMBAT);
			entity.fireHurt();
		}

	}

	protected void attackEntity(@NotNull Entity entity, float distance) {
		if (this.ranged == 1) {
			this.rangedAttackEntity(entity, distance);
		} else {
			this.meleeAttackEntity(entity, distance);
		}

	}

	public boolean isOnFire() {
		return this.getBurning();
	}

	public boolean getBurning() {
		return (this.entityData.getInt(17) & 1) != 0;
	}

	public void setBurning(boolean burning) {
		int b = this.entityData.getInt(17);
		if (burning) {
			b |= 1;
		} else {
			b &= -2;
		}

		this.entityData.set(17, b);
	}

	public boolean canSpawnHere() {
		assert this.world != null;

		return ModBlockTags.SPAWNS_FORTRESS_SKELETONS.appliesTo(this.world.getBlock((int)this.x, (int)this.y - 1, (int)this.z)) && this.world.checkIfAABBIsClear(this.bb) && this.world.getCubes(this, this.bb).isEmpty() && !this.world.getIsAnyLiquid(this.bb);
	}

	public int getMaxSpawnedInChunk() {
		return ModConfig.fort_guard_pack_size;
	}

	public String getLivingSound() {
		return "mob.skeleton";
	}

	protected String getHurtSound() {
		return "mob.skeletonhurt";
	}

	protected String getDeathSound() {
		return "mob.skeletonhurt";
	}

	public @Nullable ItemStack getHeldItem() {
		return this.ranged == 1 ? rangedItem : meleeItem;
	}

	static {
		meleeItem = Items.TOOL_SWORD_STONE.getDefaultStack();
		rangedItem = Items.TOOL_BOW.getDefaultStack();
	}
}
