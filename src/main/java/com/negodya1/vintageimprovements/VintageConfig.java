package com.negodya1.vintageimprovements;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = VintageImprovements.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VintageConfig
{
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

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean destroyOnWrongGrinderSpeed;
    public  static int lowSpeedValue;
    public  static int mediumSpeedValue;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        destroyOnWrongGrinderSpeed = DESTROY_ON_WRONG_GRINDER_SPEED.get();
        lowSpeedValue = LOW_SPEED_VALUE.get();
        mediumSpeedValue = MEDIUM_SPEED_VALUE.get();
    }
}
