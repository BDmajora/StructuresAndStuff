package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.ModConfig;
import bdmajora.stuffmod.entities.blaze.MobBlaze;
import bdmajora.stuffmod.entities.blazeFireball.NetEntryBlazeFireball;
import bdmajora.stuffmod.entities.blazeFireball.ProjectileBlazeFireball;
import bdmajora.stuffmod.entities.caveSpider.MobCaveSpider;
import bdmajora.stuffmod.entities.fortSkeleton.MobFortressGuardSkeleton;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;

public class EntityRegistrar {
	public static void registerEntities() {
		int id = ModConfig.starting_entity_id;
		EntityHelper.createEntity(MobCaveSpider.class, NamespaceID.getPermanent("btm", "cave_spider"), "mob.btm.cave_spider.name", "CaveSpider", id++);
		EntityHelper.createEntity(MobFortressGuardSkeleton.class, NamespaceID.getPermanent("btm", "fort_skeleton"), "mob.btm.fort_skeleton.name", "FortSkeleton", id++);
		EntityHelper.createEntity(MobBlaze.class, NamespaceID.getPermanent("btm", "blaze"), "mob.btm.blaze.name", "Blaze", id++);
		EntityHelper.createEntity(ProjectileBlazeFireball.class, NamespaceID.getPermanent("btm", "blaze_fireball"), "projectile.btm.blaze_fireball.name", "BlazeFireball", id++);
		NetEntityHandler.registerNetworkEntry(new NetEntryBlazeFireball(), id);
	}
}
