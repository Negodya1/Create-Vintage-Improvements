package com.negodya1.vintageimprovements.infrastructure.config;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.infrastructure.config.CRecipes;

public class VCRecipes extends ConfigBase {

	public final ConfigGroup recipes = group(0, "recipes",
			Comments.recipes);
	public final ConfigGroup grinder = group(1, "grinder",
			Comments.grinder);
	public final ConfigBool destroyOnWrongGrinderSpeed =
			b(false, "destroyOnWrongGrinderSpeed", Comments.destroyOnWrongGrinderSpeed);
	public final ConfigInt lowSpeedValue =
			i(16, 1, 256, "lowSpeedValue", Comments.lowSpeedValue);
	public final ConfigInt mediumSpeedValue =
			i(64, 1, 256, "mediumSpeedValue", Comments.mediumSpeedValue);
	public final ConfigBool allowSandpaperPolishingOnGrinder =
			b(true, "allowSandpaperPolishingOnGrinder", Comments.allowSandpaperPolishingOnGrinder);
	public final ConfigInt speedLimitsForSandpaperPolishingRecipes =
			i(1, 0, 3, "speedLimitsForSandpaperPolishingRecipes", Comments.speedLimitsForSandpaperPolishingRecipes);

	public final ConfigGroup vibratingTable = group(1, "vibratingTable",
			Comments.vibratingTable);
	public final ConfigBool allowUnpackingOnVibratingTable =
			b(true, "allowUnpackingOnVibratingTable", Comments.allowUnpackingOnVibratingTable);
	public final ConfigBool allowVibratingLeaves =
			b(true, "allowVibratingLeaves", Comments.allowVibratingLeaves);

	public final ConfigGroup curvingPress = group(1, "curvingPress",
			Comments.curvingPress);
	public final ConfigBool allowAutoCurvingRecipes =
			b(true, "allowAutoCurvingRecipes", Comments.allowAutoCurvingRecipes);

	@Override
	public String getName() {
		return "recipes";
	}

	private static class Comments {
		static String destroyOnWrongGrinderSpeed = "Destroy item, when it inserted in grinder with wrong recipe speed.";
		static String lowSpeedValue = "Low speed value for grinder crafts, speed_limits = 1.";
		static String mediumSpeedValue = "Medium speed value for grinder crafts, speed_limits = 2.";
		static String allowSandpaperPolishingOnGrinder = "Allows sandpaper crafts on belt grinder, when recipes collides belt grinder recipe have priority.";
		static String speedLimitsForSandpaperPolishingRecipes = "Works only when \"allowSandpaperPolishingOnGrinder\" is true. Defines speed limits for sandpaper recipes on belt grinder.";
		static String allowUnpackingOnVibratingTable = "Allows unpacking storage blocks crafts on vibrating table.";
		static String allowVibratingLeaves = "Allows vibrating leaves on vibrating table to get matching drops.";
		static String allowAutoCurvingRecipes = "Allows automatic recognize recipes for curving press";
		static String grinder = "Grinder settings";
		static String vibratingTable = "Vibrating Table settings";
		static String curvingPress = "Curving Press settings";
		static String recipes = "Recipes configs";
	}

}
