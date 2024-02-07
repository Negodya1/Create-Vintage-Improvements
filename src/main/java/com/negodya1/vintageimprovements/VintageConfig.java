package com.negodya1.vintageimprovements;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = VintageImprovements.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VintageConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue DESTROY_ON_WRONG_GRINDER_SPEED = BUILDER
            .comment("Destroy item, when it inserted in grinder with wrong recipe speed")
            .define("destroyOnWrongGrinderSpeed", false);
    private static final ForgeConfigSpec.IntValue LOW_SPEED_VALUE = BUILDER
            .comment("Low speed value for grinder crafts, speed_limits = 1")
            .defineInRange("lowSpeedValue", 16, 1, 256);
    private static final ForgeConfigSpec.IntValue MEDIUM_SPEED_VALUE = BUILDER
            .comment("Medium speed value for grinder crafts, speed_limits = 2")
            .defineInRange("mediumSpeedValue", 64, 1, 256);
    private static final ForgeConfigSpec.BooleanValue ALLOW_SANDPAPER_POLISHING_ON_GRINDER = BUILDER
            .comment("Belt Grinder")
            .comment("Allows sandpaper crafts on belt grinder, when recipes collides belt grinder recipe have priority")
            .define("allowSandpaperPolishingOnGrinder", false);
    private static final ForgeConfigSpec.IntValue SPEED_LIMITS_FOR_SANDPAPER_POLISHING_RECIPES = BUILDER
            .comment("Works only when \"allowSandpaperPolishingOnGrinder\" is true. Defines speed limits for sandpaper recipes on belt grinder")
            .defineInRange("speedLimitsForSandpaperPolishingRecipes", 1, 0, 3);
    public static final ForgeConfigSpec.DoubleValue BELT_GRINDER_STRESS_IMPACT = BUILDER
            .comment("Value of stress impact for Belt Grinder")
            .defineInRange("beltGrinderStressImpact", 4.0, 0.0, 1024.0);
    public static final ForgeConfigSpec.DoubleValue COILING_MACHINE_STRESS_IMPACT = BUILDER
            .comment("Spring Coiling Machine")
            .comment("Value of stress impact for Spring Coiling Machine")
            .defineInRange("coilingMachineStressImpact", 4.0, 0.0, 1024.0);
    public static final ForgeConfigSpec.DoubleValue VACUUM_CHAMBER_STRESS_IMPACT = BUILDER
            .comment("Vacuum Chamber")
            .comment("Value of stress impact for Vacuum Chamber")
            .defineInRange("vacuumChamberStressImpact", 4.0, 0.0, 1024.0);
    public static final ForgeConfigSpec.DoubleValue VIBRATING_TABLE_STRESS_IMPACT = BUILDER
            .comment("Vibration Table")
            .comment("Value of stress impact for Vibration Table")
            .defineInRange("VibratingTableStressImpact", 2.0, 0.0, 1024.0);
    private static final ForgeConfigSpec.BooleanValue ALLOW_UNPACKING_ON_VIBRATING_TABLE = BUILDER
            .comment("Allows unpacking storage blocks crafts on vibrating table")
            .define("allowUnpackingOnVibratingTable", true);

    private static final ForgeConfigSpec.BooleanValue ALLOW_LEAVES_VIBRATING_ON_VIBRATING_TABLE = BUILDER
            .comment("Allows vibrating leaves on vibrating table to get matching drops")
            .define("allowVibratingLeaves", true);

    public static final ForgeConfigSpec.DoubleValue CENTRIFUGE_STRESS_IMPACT = BUILDER
            .comment("Centrifuge")
            .comment("Value of stress impact for Centrifuge")
            .defineInRange("CentrifugeStressImpact", 2.0, 0.0, 1024.0);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean destroyOnWrongGrinderSpeed;
    public static int lowSpeedValue;
    public static int mediumSpeedValue;
    public static boolean allowSandpaperPolishingOnGrinder;
    public static int speedLimitsForSandpaperPolishingRecipes;
    public static double beltGrinderStressImpact;
    public static double coilingMachineStressImpact;
    public static double vacuumChamberStressImpact;
    public static double vibratingTableStressImpact;
    public static boolean allowUnpackingOnVibratingTable;
    public static boolean allowVibratingLeaves;
    public static double centrifugeStressImpact;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        destroyOnWrongGrinderSpeed = DESTROY_ON_WRONG_GRINDER_SPEED.get();
        lowSpeedValue = LOW_SPEED_VALUE.get();
        mediumSpeedValue = MEDIUM_SPEED_VALUE.get();
        allowSandpaperPolishingOnGrinder = ALLOW_SANDPAPER_POLISHING_ON_GRINDER.get();
        speedLimitsForSandpaperPolishingRecipes = SPEED_LIMITS_FOR_SANDPAPER_POLISHING_RECIPES.get();
        beltGrinderStressImpact = BELT_GRINDER_STRESS_IMPACT.get();
        coilingMachineStressImpact = COILING_MACHINE_STRESS_IMPACT.get();
        vacuumChamberStressImpact = VACUUM_CHAMBER_STRESS_IMPACT.get();
        vibratingTableStressImpact = VIBRATING_TABLE_STRESS_IMPACT.get();
        allowUnpackingOnVibratingTable = ALLOW_UNPACKING_ON_VIBRATING_TABLE.get();
        allowVibratingLeaves = ALLOW_LEAVES_VIBRATING_ON_VIBRATING_TABLE.get();
        centrifugeStressImpact = CENTRIFUGE_STRESS_IMPACT.get();
    }

    public static void loadConfig(ForgeConfigSpec spec, java.nio.file.Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}
