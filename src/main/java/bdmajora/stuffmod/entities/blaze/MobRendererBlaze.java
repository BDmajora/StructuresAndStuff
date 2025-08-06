//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.blaze;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.lwjgl.opengl.GL11;

public class MobRendererBlaze extends MobRenderer<MobBlaze> {
	public MobRendererBlaze(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	public void renderPreview(Tessellator tessellator, MobBlaze entity, double x, double y, double z, float yaw, float partialTick) {
		this.translateModel(entity, 0.0, Math.sin((double) Minecraft.getMinecraft().thePlayer.tickCount / 8.0) * 0.04 + 0.1, 0.0);
		if (Minecraft.getMinecraft().thePlayer.tickCount % 100 < 40) {
			this.renderFire(tessellator, entity, x, y, z, partialTick);
		}

		super.renderPreview(tessellator, entity, x, y, z, yaw, partialTick);
	}

	private void renderFire(Tessellator tessellator, MobBlaze entity, double x, double y, double z, float partialTick) {
		GL11.glDisable(GL11.GL_LIGHTING);
		IconCoordinate texture = TextureRegistry.getTexture("minecraft:block/fire");
		texture.parentAtlas.bind();
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		float scale = entity.bbWidth * 1.4F;
		GL11.glScalef(scale, scale, scale);
		float height = entity.bbHeight;
		float offset = (float) (entity.y - entity.bb.minY);
		GL11.glRotatef(-this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.0F, -0.3F + ((int) height) * 0.02F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if (LightmapHelper.isLightmapEnabled()) {
			int lightmapCoord = entity.getLightmapCoord(partialTick);
			lightmapCoord = LightmapHelper.setBlocklightValue(lightmapCoord, 15);
			LightmapHelper.setLightmapCoord(lightmapCoord);
		}

		float depth = 0.0F;
		int count = 0;
		tessellator.startDrawingQuads();

		while (height > 0.0F) {
			double uMin = texture.getIconUMin();
			double uMax = texture.getIconUMax();
			double vMin = texture.getIconVMin();
			double vMax = texture.getIconVMax();
			if (count / 2 % 2 == 0) {
				double temp = uMax;
				uMax = uMin;
				uMin = temp;
			}

			tessellator.addVertexWithUV(0.5F, -offset, depth, uMax, vMax);
			tessellator.addVertexWithUV(-0.5F, -offset, depth, uMin, vMax);
			tessellator.addVertexWithUV(-0.5F, 1.4F - offset, depth, uMin, vMin);
			tessellator.addVertexWithUV(0.5F, 1.4F - offset, depth, uMax, vMin);
			height -= 0.45F;
			offset -= 0.45F;
			depth += 0.03F;
			count++;
		}

		tessellator.draw();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
