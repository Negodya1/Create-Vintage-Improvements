package com.negodya1.vintageimprovements.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.negodya1.vintageimprovements.VintageBlocks;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.VintagePartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class AnimatedHelve extends AnimatedKinetics {

	public void draw(GuiGraphics graphics, int xOffset, int yOffset, boolean mode) {
		int scale = 23;

		draw(graphics, xOffset, yOffset);

		PoseStack matrixStack = graphics.pose();
		matrixStack.pushPose();
		matrixStack.translate(xOffset - 6, yOffset + 56, 0);
		matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

		if (!mode)
			blockElement(Blocks.SMITHING_TABLE.defaultBlockState())
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(graphics);
		else
			blockElement(Blocks.ANVIL.defaultBlockState())
					.atLocal(0, 0, 0)
					.scale(scale)
					.render(graphics);

		matrixStack.popPose();
	}

	@Override
	public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
		PoseStack matrixStack = graphics.pose();
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 0);
		matrixStack.translate(0, 0, 200);
		matrixStack.translate(2, 22, 0);
		matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f + 180));
		int scale = 25;

		matrixStack.translate(0, 0, -12);

		blockElement(shaft(Direction.Axis.X))
			.rotateBlock(getCurrentAngle(), 0, 0)
			.scale(scale)
			.render(graphics);

		blockElement(VintageBlocks.HELVE_KINETIC.getDefaultState())
			.scale(scale)
			.render(graphics);

		matrixStack.translate(0, 0, -24);

		blockElement(VintagePartialModels.HELVE_HAMMER)
			.rotateBlock(-getHammerAngle(getCurrentAngle()), 0, 0)
			.withRotationOffset(new Vec3(0.5, 0.5, 1.5))
			.scale(scale)
			.render(graphics);

		matrixStack.popPose();
	}

	private float getHammerAngle(float curAngle) {
		if (curAngle <= 350) return -curAngle / 14;
		return (360f - curAngle) * -2.5f;
	}
}
