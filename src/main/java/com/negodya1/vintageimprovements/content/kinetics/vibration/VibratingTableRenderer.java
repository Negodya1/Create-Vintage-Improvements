package com.negodya1.vintageimprovements.content.kinetics.vibration;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.VintagePartialModels;
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
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class VibratingTableRenderer extends SafeBlockEntityRenderer<VibratingTableBlockEntity> {

	public VibratingTableRenderer(BlockEntityRendererProvider.Context context) {}

	@Override
	protected void renderSafe(VibratingTableBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderTable(be, partialTicks, ms, buffer, light);
		renderItems(be, partialTicks, ms, buffer, light, overlay);

		if (Backend.canUseInstancing(be.getLevel()))
			return;

		renderShaft(be, ms, buffer, light, overlay);
	}

	protected void renderTable(VibratingTableBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light) {
		BlockState blockState = be.getBlockState();

		float offset = 0.0f;
		if (be.haveRecipe()) offset = be.getRenderedHeadOffset(partialTicks);

		SuperByteBuffer superBuffer = CachedBufferer.partialFacing(VintagePartialModels.VIBRATING_TABLE, blockState, blockState.getValue(HORIZONTAL_FACING)); //.rotateCentered(Direction.UP, blockState.getValue(HORIZONTAL_FACING) == Direction.WEST || blockState.getValue(HORIZONTAL_FACING) == Direction.NORTH ? 0 : (180*(float)Math.PI/180f));
		superBuffer.translate(.0, offset, .0);

		superBuffer.color(0xFFFFFF)
			.light(light)
			.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
	}

	protected void renderShaft(VibratingTableBlockEntity be, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(be), ms, buffer.getBuffer(RenderType.solid()), light);
	}

	protected void renderItems(VibratingTableBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		boolean input = !be.inputInv.getStackInSlot(0).isEmpty();
		boolean output = !be.outputInv.getStackInSlot(0).isEmpty();

		if (!input && !output) return;

		float offset = 0.0f;
		if (be.haveRecipe()) offset = be.getRenderedHeadOffset(partialTicks);

		if (input) {
			if (!output) {
				boolean alongZ = (be.getBlockState().getValue(HORIZONTAL_FACING) == Direction.NORTH || be.getBlockState().getValue(HORIZONTAL_FACING) == Direction.SOUTH);
				ms.pushPose();

				for (int i = 0; i < be.inputInv.getSlots(); i++) {
					ItemStack stack = be.inputInv.getStackInSlot(i);
					if (stack.isEmpty())
						continue;

					ItemRenderer itemRenderer = Minecraft.getInstance()
							.getItemRenderer();
					BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
					boolean blockItem = modelWithOverrides.isGui3d();

					ms.translate(.5, (blockItem ? .925f : 14f / 16f) + offset, .5);

					ms.scale(.5f, .5f, .5f);
					if (alongZ)
						ms.mulPose(Vector3f.YP.rotationDegrees(90));
					ms.mulPose(Vector3f.XP.rotationDegrees(90));
					itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, ms, buffer, 0);
					break;
				}

				ms.popPose();
			}
			else {
				ms.pushPose();

				for (int i = 0; i < be.inputInv.getSlots(); i++) {
					ItemStack stack = be.inputInv.getStackInSlot(i);
					if (stack.isEmpty())
						continue;

					ItemRenderer itemRenderer = Minecraft.getInstance()
							.getItemRenderer();
					BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
					boolean blockItem = modelWithOverrides.isGui3d();

					ms.translate(.33, (blockItem ? .925f : 14f / 16f) + offset, .33);

					ms.scale(.5f, .5f, .5f);
					ms.mulPose(Vector3f.XP.rotationDegrees(90));
					itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, ms, buffer, 0);

					ms.mulPose(Vector3f.XP.rotationDegrees(-90));
					ms.translate(0, (blockItem ? -.925f : -14f / 16f), 0);
					break;
				}

				for (int i = 0; i < be.outputInv.getSlots(); i++) {
					ItemStack stack = be.outputInv.getStackInSlot(i);
					if (stack.isEmpty())
						continue;

					ItemRenderer itemRenderer = Minecraft.getInstance()
							.getItemRenderer();
					BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
					boolean blockItem = modelWithOverrides.isGui3d();

					ms.translate(.66, (blockItem ? .925f : 14f / 16f), .66);
					ms.mulPose(Vector3f.XP.rotationDegrees(90));

					itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, ms, buffer, 0);
					break;
				}

				ms.popPose();
			}
		}
		else if (output) {
			boolean alongZ = (be.getBlockState().getValue(HORIZONTAL_FACING) == Direction.NORTH || be.getBlockState().getValue(HORIZONTAL_FACING) == Direction.SOUTH);
			ms.pushPose();

			for (int i = 0; i < be.outputInv.getSlots(); i++) {
				ItemStack stack = be.outputInv.getStackInSlot(i);
				if (stack.isEmpty())
					continue;

				ItemRenderer itemRenderer = Minecraft.getInstance()
						.getItemRenderer();
				BakedModel modelWithOverrides = itemRenderer.getModel(stack, be.getLevel(), null, 0);
				boolean blockItem = modelWithOverrides.isGui3d();

				ms.translate(.5, (blockItem ? .925f : 14f / 16f) + offset, .5);

				ms.scale(.5f, .5f, .5f);
				if (alongZ)
					ms.mulPose(Vector3f.YP.rotationDegrees(90));
				ms.mulPose(Vector3f.XP.rotationDegrees(90));
				itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, ms, buffer, 0);
				break;
			}

			ms.popPose();
		}
	}

	protected SuperByteBuffer getRotatedModel(KineticBlockEntity be) {
		return CachedBufferer.block(KineticBlockEntityRenderer.KINETIC_BLOCK,
			getRenderedBlockState(be));
	}

	protected BlockState getRenderedBlockState(KineticBlockEntity be) {
		return KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(be));
	}

}
