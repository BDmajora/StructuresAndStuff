//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.caveSpider;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.core.entity.monster.MobSpider;
import org.lwjgl.opengl.GL11;

public class MobRendererCaveSpider extends MobRenderer<MobCaveSpider> {
	public MobRendererCaveSpider() {
		super(new ModelCaveSpider(0.0F), 1.0F);
		this.setArmorModel(new ModelCaveSpider(0.01F));
		this.shadowSize = 0.42F;
	}

	protected boolean setSpiderEyeBrightness(MobSpider spider, int renderPass, float partialTick) {
		if (renderPass == 0) {
			this.bindTexture("/assets/minecraft/textures/entity/spider/eyes/" + spider.getTextureReference() + ".png");
			float brightness = spider.getBrightness(1.0F);
			if (LightmapHelper.isLightmapEnabled()) {
				LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
			}

			float f1 = (1.0F - brightness) * 0.5F;
			GL11.glEnable(3042);
			GL11.glDisable(3008);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
			return true;
		} else {
			return false;
		}
	}

	protected float getMaxDeathRotation(MobSpider entity) {
		return 180.0F;
	}

	protected boolean prepareArmor(MobSpider entity, int renderPass, float partialTick) {
		return this.setSpiderEyeBrightness(entity, renderPass, partialTick);
	}
}
