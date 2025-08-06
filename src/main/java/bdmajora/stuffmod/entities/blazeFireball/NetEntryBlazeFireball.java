//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.blazeFireball;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.ProjectileFireball;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetEntryBlazeFireball implements IVehicleEntry<ProjectileFireball>, ITrackedEntry<ProjectileFireball> {
	public NetEntryBlazeFireball() {
	}

	public int getTrackingDistance() {
		return 128;
	}

	public int getPacketDelay() {
		return 1;
	}

	public boolean sendMotionUpdates() {
		return true;
	}

	public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, ProjectileFireball object) {
	}

	public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag tag) {
		return new ProjectileBlazeFireball(world, x, y, z, xd, yd, zd);
	}

	public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, ProjectileFireball trackedObject) {
		return new PacketAddEntity(trackedObject, -1, -1, trackedObject.xd, trackedObject.yd, trackedObject.zd);
	}

	public @NotNull Class<? extends ProjectileFireball> getAppliedClass() {
		return ProjectileBlazeFireball.class;
	}
}
