package com.negodya1.vintageimprovements.content.kinetics.helve_hammer;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

public class HelveInstance extends SingleRotatingInstance<HelveKineticBlockEntity> {

	public HelveInstance(MaterialManager materialManager, HelveKineticBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		return getRotatingMaterial().getModel(shaft());
	}
}
