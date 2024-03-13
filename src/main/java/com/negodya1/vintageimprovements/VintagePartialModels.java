package com.negodya1.vintageimprovements;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;

public class VintagePartialModels {

	public static final PartialModel

		GRINDER_BELT_ACTIVE = block("belt_grinder/belt_active"),
		GRINDER_BELT_INACTIVE = block("belt_grinder/belt_inactive"),
		GRINDER_BELT_REVERSED = block("belt_grinder/belt_reversed"),
		COILING_WHEEL = block("spring_coiling_machine/coiling_part_wheel"),
		COILING_SPRING = block("spring_coiling_machine/coiling_part_spring"),
		VACUUM_COG = block("vacuum_chamber/cog"),
		VACUUM_PIPE = block("vacuum_chamber/head"),
		VIBRATING_TABLE = block("vibrating_table/head"),
		CENTRIFUGE_BEAMS = block("centrifuge/head"),
		BASIN = block("centrifuge/basin"),
		CURVING_HEAD = block("curving_press/head"),
		GRINDER_BELT_ACTIVE_RED = block("belt_grinder/belt_active_red"),
		GRINDER_BELT_INACTIVE_RED = block("belt_grinder/belt_inactive_red"),
		GRINDER_BELT_REVERSED_RED = block("belt_grinder/belt_reversed_red"),
		GRINDER_BELT_ACTIVE_DIAMOND = block("belt_grinder/belt_active_diamond"),
		GRINDER_BELT_INACTIVE_DIAMOND = block("belt_grinder/belt_inactive_diamond"),
		GRINDER_BELT_REVERSED_DIAMOND = block("belt_grinder/belt_reversed_diamond"),
		GRINDER_BELT_ACTIVE_IRON = block("belt_grinder/belt_active_iron"),
		GRINDER_BELT_INACTIVE_IRON = block("belt_grinder/belt_inactive_iron"),
		GRINDER_BELT_REVERSED_IRON = block("belt_grinder/belt_reversed_iron"),
		GRINDER_BELT_ACTIVE_OBSIDIAN = block("belt_grinder/belt_active_obsidian"),
		GRINDER_BELT_INACTIVE_OBSIDIAN = block("belt_grinder/belt_inactive_obsidian"),
		GRINDER_BELT_REVERSED_OBSIDIAN = block("belt_grinder/belt_reversed_obsidian"),
		VACUUM_CHAMBER_ARROWS = block("vacuum_chamber/arrows"),
		HELVE_HAMMER = block("helve_hammer/head");

	private static PartialModel block(String path) {
		return new PartialModel(VintageImprovements.asResource("block/" + path));
	}

	public static void init() {
		// init static fields
	}

}
