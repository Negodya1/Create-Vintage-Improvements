package com.negodya1.vintageimprovements;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class VintagePartialModels {

	public static final PartialModel

		GRINDER_BELT_ACTIVE = block("belt_grinder/belt_active"),
		GRINDER_BELT_INACTIVE = block("belt_grinder/belt_inactive"),
		GRINDER_BELT_REVERSED = block("belt_grinder/belt_reversed"),
		COILING_WHEEL = block("spring_coiling_machine/coiling_part_wheel"),
		COILING_SPRING = block("spring_coiling_machine/coiling_part_spring");

	private static PartialModel block(String path) {
		return new PartialModel(VintageImprovements.asResource("block/" + path));
	}

	private static PartialModel entity(String path) {
		return new PartialModel(VintageImprovements.asResource("entity/" + path));
	}

	public static void init() {
		// init static fields
	}

}
