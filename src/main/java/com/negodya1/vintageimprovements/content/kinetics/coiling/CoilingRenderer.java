package com.negodya1.vintageimprovements.content.kinetics.coiling;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.VintagePartialModels;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.contraptions.render.ContraptionRenderDispatcher;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CoilingRenderer extends SafeBlockEntityRenderer<CoilingBlockEntity> {

	public CoilingRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(CoilingBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderParts(be, ms, buffer, light);
		renderSpring(be, partialTicks, ms, buffer, light, overlay);
		FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);

		if (Backend.canUseInstancing(be.getLevel()))
			return;

		renderShaft(be, ms, buffer, light, overlay);
	}

	protected void renderParts(CoilingBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light) {
		float speed = -Math.abs(be.getSpeed());
		float time = AnimationTickHolder.getRenderTime(be.getLevel());
		float angle = ((time * speed * 6 / 10f) % 360);

		BlockState blockState = be.getBlockState();
		PartialModel partial = VintagePartialModels.COILING_WHEEL;

		SuperByteBuffer superBuffer = CachedBufferer.partial(partial, blockState);
		rotateWheel(superBuffer, angle, blockState.getValue(HORIZONTAL_FACING));

		superBuffer.color(0xFFFFFF)
			.light(light)
			.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
	}

	private SuperByteBuffer rotateWheel(SuperByteBuffer buffer, float angle, Direction facing) {
		float pivotX = 5 / 16f;
		float pivotY = 10.5f / 16f;
		float pivotZ = 11.5f / 16f;
		buffer.rotateCentered(Direction.UP, AngleHelper.rad(AngleHelper.horizontalAngle(facing.getCounterClockWise())));
		buffer.translate(pivotX, pivotY, pivotZ);
		buffer.rotate(Direction.EAST, AngleHelper.rad(angle));
		buffer.translate(-pivotX, -pivotY, -pivotZ);
		return buffer;
	}

	private SuperByteBuffer rotateSpring(SuperByteBuffer buffer, float angle, Direction facing) {
		float pivotX = 17 / 16f;
		float pivotY = 9.5f / 16f;
		float pivotZ = 7.5f / 16f;
		buffer.rotateCentered(Direction.UP, AngleHelper.rad(AngleHelper.horizontalAngle(facing.getCounterClockWise())));
		buffer.translate(pivotX, pivotY, pivotZ);
		buffer.rotate(Direction.EAST, AngleHelper.rad(angle));
		buffer.translate(-pivotX, -pivotY, -pivotZ);
		return buffer;
	}

	protected void renderShaft(CoilingBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(be), ms, buffer.getBuffer(RenderType.solid()), light);
	}

	protected void renderSpring(CoilingBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		if (!be.inventory.isEmpty()) {
			ms.pushPose();

			boolean moving = be.inventory.recipeDuration != 0;
			float offset = moving ? (float) (be.inventory.remainingTime) / be.inventory.recipeDuration : 0;
			float processingSpeed = Mth.clamp(Math.abs(be.getSpeed()) / 32, 1, 128);
			if (moving) {
				offset = Mth
						.clamp(offset + ((-partialTicks + .5f) * processingSpeed)
								/ be.inventory.recipeDuration, 0.05f, 0.75f);
				if (!be.inventory.appliedRecipe)
					offset += 1;
				offset /= 2;
			}

			if (be.getSpeed() == 0)
				offset = .5f;
			offset = 0.3f - offset;

			for (int i = 0; i < be.inventory.getSlots(); i++) {
				BlockState blockState = be.getBlockState();
				PartialModel partial = VintagePartialModels.COILING_SPRING;

				SuperByteBuffer superBuffer = CachedBufferer.partial(partial, blockState);
				float speed = -Math.abs(be.getSpeed());
				float time = AnimationTickHolder.getRenderTime(be.getLevel());
				float angle = ((time * speed * 6 / 10f) % 360);

				rotateSpring(superBuffer, angle, blockState.getValue(HORIZONTAL_FACING));

				superBuffer.rotateCentered(Direction.UP, (180*(float)Math.PI/180f));

				superBuffer.translate(offset, 0, 0);

				superBuffer.color(0xFFFFFF)
						.light(light)
						.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
				break;
			}

			ms.popPose();
		}
	}

	protected SuperByteBuffer getRotatedModel(KineticBlockEntity be) {
		return CachedBufferer.partial(AllPartialModels.SHAFT_HALF, getRenderedBlockState(be));
	}

	protected BlockState getRenderedBlockState(KineticBlockEntity be) {
		return KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(be));
	}

	public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
		ContraptionMatrices matrices, MultiBufferSource buffer) {
		BlockState state = context.state;
		Direction facing = state.getValue(CoilingBlock.HORIZONTAL_FACING);

		Vec3 facingVec = Vec3.atLowerCornerOf(context.state.getValue(CoilingBlock.HORIZONTAL_FACING)
			.getNormal());
		facingVec = context.rotation.apply(facingVec);

		Direction closestToFacing = Direction.getNearest(facingVec.x, facingVec.y, facingVec.z);

		boolean horizontal = closestToFacing.getAxis()
			.isHorizontal();
		boolean backwards = VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite());
		boolean moving = context.getAnimationSpeed() != 0;
		boolean shouldAnimate =
			(context.contraption.stalled && horizontal) || (!context.contraption.stalled && !backwards && moving);

		SuperByteBuffer superBuffer;
		if (shouldAnimate)
			superBuffer = CachedBufferer.partial(VintagePartialModels.GRINDER_BELT_ACTIVE, state);
		else
			superBuffer = CachedBufferer.partial(VintagePartialModels.GRINDER_BELT_INACTIVE, state);

		superBuffer.transform(matrices.getModel())
			.centre()
			.rotateY(AngleHelper.horizontalAngle(facing))
			.rotateX(AngleHelper.verticalAngle(facing));

		if (!CoilingBlock.isHorizontal(state)) {
			superBuffer.rotateZ(0);
		}

		superBuffer.unCentre()
			.light(matrices.getWorld(), ContraptionRenderDispatcher.getContraptionWorldLight(context, renderWorld))
			.renderInto(matrices.getViewProjection(), buffer.getBuffer(RenderType.cutoutMipped()));
	}

}
