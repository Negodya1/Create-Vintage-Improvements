package com.negodya1.vintageimprovements.content.kinetics.centrifuge;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

public class CentrifugeInstance extends SingleRotatingInstance<CentrifugeBlockEntity> {

	public CentrifugeInstance(MaterialManager materialManager, CentrifugeBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Instancer<RotatingData> getModel() {return getRotatingMaterial().getModel(shaft());}
}
