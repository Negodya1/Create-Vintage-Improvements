package com.negodya1.vintageimprovements.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.negodya1.vintageimprovements.VintageBlocks;
import com.negodya1.vintageimprovements.VintagePartialModels;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

import net.minecraft.util.Mth;

public class AnimatedVacuumChamber extends AnimatedKinetics {

	public void draw(PoseStack matrixStack, int xOffset, int yOffset, boolean mode) {
		int scale = 23;

		draw(matrixStack, xOffset, yOffset);

		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 200);
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f));

		if (!mode)
			blockElement(VintagePartialModels.VACUUM_CHAMBER_ARROWS)
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(matrixStack);
		else
			blockElement(VintagePartialModels.VACUUM_CHAMBER_ARROWS)
					.atLocal(0, 0, 0)
					.rotateBlock(0, 0, 180)
					.scale(scale)
					.render(matrixStack);

		matrixStack.popPose();
	}

	public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 200);
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f));
		int scale = 23;

		blockElement(VintagePartialModels.VACUUM_COG)
			.rotateBlock(0, getCurrentAngle() * 2, 0)
			.atLocal(0, 0, 0)
			.scale(scale)
			.render(matrixStack);

		blockElement(VintageBlocks.VACUUM_CHAMBER.getDefaultState())
			.atLocal(0, 0, 0)
			.scale(scale)
			.render(matrixStack);

		float animation = ((Mth.sin(AnimationTickHolder.getRenderTime() / 32f) + 1) / 5) + .5f;

		animation = Mth.clamp(animation, 0, 11.2f / 16f);

		blockElement(VintagePartialModels.VACUUM_PIPE)
			.atLocal(0, animation, 0)
			.scale(scale)
			.render(matrixStack);

		blockElement(AllBlocks.BASIN.getDefaultState())
			.atLocal(0, 1.65, 0)
			.scale(scale)
			.render(matrixStack);

		matrixStack.popPose();
	}

}
