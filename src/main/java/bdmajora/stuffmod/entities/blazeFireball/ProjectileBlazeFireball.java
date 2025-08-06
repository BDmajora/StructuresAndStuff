//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.blazeFireball;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.ProjectileFireball;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import java.lang.reflect.Field;

public class ProjectileBlazeFireball extends ProjectileFireball {
	public ProjectileBlazeFireball(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}

	public ProjectileBlazeFireball(World world, Mob owner, double Vx, double Vy, double Vz) {
		super(world, owner, Vx, Vy, Vz);
		this.damage = 6;
		this.setSize(0.3125F, 0.3125F);
	}

	public ProjectileBlazeFireball(World world, double x, double y, double z, double Vx, double Vy, double Vz) {
		super(world, x, y, z, Vx, Vy, Vz);
		this.damage = 6;
		this.setSize(0.3125F, 0.3125F);
	}



	public void onHit(HitResult result) {
		if (this.tickCount > 5) {
			if (!this.world.isClientSide) {
				if (result.entity != null) {
					// Access the fireImmune field via reflection
					boolean fireImmune = false;
					try {
						// Reflectively access the 'fireImmune' field in the Entity class
						Field fireImmuneField = Entity.class.getDeclaredField("fireImmune");
						fireImmuneField.setAccessible(true); // Make the private field accessible
						fireImmune = fireImmuneField.getBoolean(result.entity);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Apply damage based on fireImmune
					result.entity.hurt(this.owner, fireImmune ? this.damage / 2 : this.damage, DamageType.FIRE);
					result.entity.fireHurt();
				} else {
					int x = result.x;
					int y = result.y;
					int z = result.z;
					switch (result.side) {
						case BOTTOM:
							--y;
							break;
						case TOP:
							++y;
							break;
						case NORTH:
							--z;
							break;
						case SOUTH:
							++z;
							break;
						case WEST:
							--x;
							break;
						case EAST:
							++x;
					}

					if (this.world.isAirBlock(x, y, z)) {
						this.world.setBlockWithNotify(x, y, z, Blocks.FIRE.id());
					}
				}
			}

			this.remove();
		}
	}


	public void tick() {
		this.world.spawnParticle("flame", this.x, this.y, this.z, this.xd * 0.05000000074505806, this.yd * 0.05000000074505806 - 0.10000000149011612, this.zd * 0.05000000074505806, 0);
		this.world.spawnParticle("flame", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05000000074505806, this.yd * 0.05000000074505806 - 0.10000000149011612, this.zd * 0.05000000074505806, 0);
		super.tick();
	}

	public boolean hurt(Entity entity, int i, DamageType type) {
		this.markHurt();
		if (entity != null) {
			Vec3 lookAngle = entity.getLookAngle();
			if (entity instanceof Mob) {
				this.owner = (Mob) entity;
			}

			if (lookAngle != null) {
				double velocity = MathHelper.sqrt(lookAngle.x * lookAngle.x + lookAngle.y * lookAngle.y + lookAngle.z * lookAngle.z);
				if (velocity != 0.0) {
					this.xd = lookAngle.x / velocity;
					this.yd = lookAngle.y / velocity;
					this.zd = lookAngle.z / velocity;
				} else {
					this.xd = 0.0;
					this.yd = 0.0;
					this.zd = 0.0;
				}
			}

			return true;
		}
		return false;
	}



}
