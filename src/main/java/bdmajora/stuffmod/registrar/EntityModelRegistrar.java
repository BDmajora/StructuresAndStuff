package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.entities.blaze.MobBlaze;
import bdmajora.stuffmod.entities.blaze.MobRendererBlaze;
import bdmajora.stuffmod.entities.blaze.ModelBlaze;
import bdmajora.stuffmod.entities.blazeFireball.ProjectileBlazeFireball;
import bdmajora.stuffmod.entities.caveSpider.MobCaveSpider;
import bdmajora.stuffmod.entities.caveSpider.MobRendererCaveSpider;
import bdmajora.stuffmod.entities.fortSkeleton.MobFortressGuardSkeleton;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.model.ModelSkeleton;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.ModelHelper;

public class EntityModelRegistrar {

	public static void registerEntityModels(EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(MobCaveSpider.class, () -> {
			EntityRenderer<?> er = new MobRendererCaveSpider();
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(MobFortressGuardSkeleton.class, () -> {
			EntityRenderer<?> er = new MobRendererBiped(new ModelSkeleton(), 0.5F);
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(MobBlaze.class, () -> {
			EntityRenderer<?> er = new MobRendererBlaze(new ModelBlaze(), 0.0F);
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(ProjectileBlazeFireball.class, () -> {
			EntityRenderer<?> er = (new EntityRendererSprite(Items.AMMO_FIREBALL)).setScale(1.0F);
			er.init(dispatcher);
			return er;
		});
	}
}
