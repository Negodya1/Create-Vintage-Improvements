package com.negodya1.vintageimprovements.content.kinetics.coiling;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.HorizontalHalfShaftInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CoilingInstance extends HorizontalHalfShaftInstance<CoilingBlockEntity> {

	public CoilingInstance(MaterialManager materialManager, CoilingBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		BlockState referenceState = blockState.rotate(blockEntity.getLevel(), blockEntity.getBlockPos(), Rotation.CLOCKWISE_180);
		Direction facing = referenceState.getValue(BlockStateProperties.HORIZONTAL_FACING);
		return getRotatingMaterial().getModel(AllPartialModels.SHAFT_HALF, referenceState, facing);
	}
}
