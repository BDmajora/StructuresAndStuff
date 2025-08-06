//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.entities.blaze;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ModelBlaze extends ModelBase {
	public Map<String, Cube> cubes;

	public ModelBlaze() {
		this(0.0F);
	}

	public ModelBlaze(float expansion) {
		this.cubes = new HashMap();
		int yOff = 4;
		Cube h = new Cube(0, 0);
		h.addBox(-4.0F, -6.0F, -4.0F, 8, 8, 8, expansion);
		h.setRotationPoint(0.0F, (float)yOff, 0.0F);
		this.cubes.put("head", h);

		for(int i = 0; i < 12; ++i) {
			Cube c = new Cube(0, 16);
			c.addBox(0.0F, 16.0F, 0.0F, 2, 8, 2);
			c.setRotationPoint(0.0F, 24.0F, 0.0F);
			this.cubes.put("upperBodyParts" + i, c);
		}

	}

	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		Iterator var7 = this.cubes.values().iterator();

		while(var7.hasNext()) {
			Cube c = (Cube)var7.next();
			c.render(scale);
		}

	}

	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		((Cube)this.cubes.get("head")).setRotationAngle(headPitch / 57.295776F, headYaw / 57.295776F, 0.0F);
	}

	public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
		float t = 7.0E-4F * ((float)mob.tickCount + partialTick);
		if (mob.tickCount == 0) {
			t = 7.0E-4F * ((float)Minecraft.getMinecraft().thePlayer.tickCount * 0.8F + partialTick);
		}

		int i;
		Cube c;
		float angle;
		float distance;
		float height;
		for(i = 0; i < 4; ++i) {
			c = (Cube)this.cubes.get("upperBodyParts" + i);
			angle = 9.0F;
			distance = (float)Math.toRadians((double)(90.0F * (float)i));
			height = -13.0F;
			c.setRotationPoint(MathHelper.cos(t * 360.0F + distance) * angle, height + MathHelper.cos(((float)i * 2.0F + t * 20.0F) * 14.32F), MathHelper.sin(t * 360.0F + distance) * angle);
		}

		for(i = 4; i < 8; ++i) {
			c = (Cube)this.cubes.get("upperBodyParts" + i);
			angle = (float)Math.toRadians((double)(90.0F * (float)i + 45.0F));
			distance = 7.0F;
			height = -8.0F;
			c.setRotationPoint(MathHelper.sin(t * 108.0F + angle) * distance, height + MathHelper.cos(((float)i * 2.0F + t * 20.0F) * 14.32F), MathHelper.cos(t * 108.0F + angle) * distance);
		}

		for(i = 8; i < 12; ++i) {
			c = (Cube)this.cubes.get("upperBodyParts" + i);
			angle = (float)Math.toRadians((double)(90.0F * (float)i + 27.0F));
			distance = 5.0F;
			height = 0.0F;
			c.setRotationPoint(MathHelper.cos(t * 108.0F + angle) * distance, height + MathHelper.cos(((float)i * 1.5F + t * 20.0F) * 14.32F), MathHelper.sin(t * 108.0F + angle) * distance);
		}

	}
}
