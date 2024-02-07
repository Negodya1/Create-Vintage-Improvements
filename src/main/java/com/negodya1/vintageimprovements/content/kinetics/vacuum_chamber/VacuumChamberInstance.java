package com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.negodya1.vintageimprovements.VintagePartialModels;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogInstance;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

public class VacuumChamberInstance extends EncasedCogInstance implements DynamicInstance {

	private final OrientedData mixerPole;
	private final VacuumChamberBlockEntity mixer;

	public VacuumChamberInstance(MaterialManager materialManager, VacuumChamberBlockEntity blockEntity) {
		super(materialManager, blockEntity, false);
		this.mixer = blockEntity;


		mixerPole = getOrientedMaterial()
				.getModel(VintagePartialModels.VACUUM_PIPE, blockState)
				.createInstance();


		float renderedHeadOffset = getRenderedHeadOffset();

		transformPole(renderedHeadOffset);
	}

	@Override
	protected Instancer<RotatingData> getCogModel() {
		return materialManager.defaultSolid()
			.material(AllMaterialSpecs.ROTATING)
			.getModel(VintagePartialModels.VACUUM_COG, blockEntity.getBlockState());
	}

	@Override
	public void beginFrame() {

		float renderedHeadOffset = getRenderedHeadOffset();

		transformPole(renderedHeadOffset);
	}

	private void transformPole(float renderedHeadOffset) {
		mixerPole.setPosition(getInstancePosition())
				.nudge(0, -renderedHeadOffset, 0);
	}

	private float getRenderedHeadOffset() {
		return mixer.getRenderedHeadOffset(AnimationTickHolder.getPartialTicks());
	}

	@Override
	public void updateLight() {
		super.updateLight();

		relight(pos, mixerPole);
	}

	@Override
	public void remove() {
		super.remove();
		mixerPole.delete();
	}
}
