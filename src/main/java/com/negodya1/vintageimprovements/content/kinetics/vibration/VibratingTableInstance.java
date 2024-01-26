package com.negodya1.vintageimprovements.content.kinetics.vibration;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

public class VibratingTableInstance extends SingleRotatingInstance<VibratingTableBlockEntity> {

	public VibratingTableInstance(MaterialManager materialManager, VibratingTableBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		return getRotatingMaterial().getModel(shaft());
	}
}
