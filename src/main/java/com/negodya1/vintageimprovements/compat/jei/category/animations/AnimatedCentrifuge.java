package com.negodya1.vintageimprovements.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.negodya1.vintageimprovements.VintageBlocks;
import com.negodya1.vintageimprovements.VintagePartialModels;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderBlock;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class AnimatedCentrifuge extends AnimatedKinetics {

	public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 0);
		matrixStack.translate(0, 0, 200);
		matrixStack.translate(2, 22, 0);
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f + 90));
		int scale = 25;

		blockElement(shaft(Direction.Axis.Y))
			.rotateBlock(0, getCurrentAngle(), 0)
			.scale(scale)
			.render(matrixStack);

		blockElement(VintageBlocks.CENTRIFUGE.getDefaultState())
			.rotateBlock(0, 0, 0)
			.scale(scale)
			.render(matrixStack);

		blockElement(VintagePartialModels.CENTRIFUGE_BEAMS)
			.rotateBlock(0, getCurrentAngle(), 0)
			.scale(scale)
			.render(matrixStack);

		blockElement(VintagePartialModels.BASIN)
				.rotateBlock(0, getCurrentAngle(), 0)
				.withRotationOffset(new Vec3(36d / 16d, 0, 0.5))
				.atLocal(-28d / 16d, 0, 0)
				.scale(scale)
				.render(matrixStack);

		blockElement(VintagePartialModels.BASIN)
				.rotateBlock(0, getCurrentAngle(), 0)
				.withRotationOffset(new Vec3(-20d / 16d, 0, 0.5))
				.atLocal(28d / 16d, 0, 0)
				.scale(scale)
				.render(matrixStack);

		blockElement(VintagePartialModels.BASIN)
				.rotateBlock(0, getCurrentAngle(), 0)
				.withRotationOffset(new Vec3(0.5, 0, 36d / 16d))
				.atLocal(0, 0, -28d / 16d)
				.scale(scale)
				.render(matrixStack);

		blockElement(VintagePartialModels.BASIN)
				.rotateBlock(0, getCurrentAngle(), 0)
				.withRotationOffset(new Vec3(0.5, 0, -20d / 16d))
				.atLocal(0, 0, 28d / 16d)
				.scale(scale)
				.render(matrixStack);

		matrixStack.popPose();
	}

}
