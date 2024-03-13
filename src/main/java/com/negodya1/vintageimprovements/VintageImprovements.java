package com.negodya1.vintageimprovements;

import com.negodya1.vintageimprovements.infrastructure.config.VintageConfig;
import com.negodya1.vintageimprovements.infrastructure.ponder.VintagePonder;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.negodya1.vintageimprovements.foundation.data.VintageRegistrate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VintageImprovements.MODID)
public class VintageImprovements {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "vintageimprovements";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void logThis(String str) {
        LOGGER.info(str);
    }

    public static final VintageRegistrate MY_REGISTRATE = VintageRegistrate.create(MODID);

    static {
        MY_REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Item> STEEL_ROD = ITEMS.register("steel_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CALORITE_ROD = ITEMS.register("calorite_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OSTRUM_ROD = ITEMS.register("ostrum_rod", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_ROD = ITEMS.register("desh_rod", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_WIRE = ITEMS.register("steel_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CALORITE_WIRE = ITEMS.register("calorite_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OSTRUM_WIRE = ITEMS.register("ostrum_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_WIRE = ITEMS.register("desh_wire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASS_WIRE = ITEMS.register("brass_wire", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_SPRING = ITEMS.register("steel_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CALORITE_SPRING = ITEMS.register("calorite_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OSTRUM_SPRING = ITEMS.register("ostrum_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_SPRING = ITEMS.register("desh_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_SPRING = ITEMS.register("iron_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SPRING = ITEMS.register("golden_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_SPRING = ITEMS.register("copper_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASS_SPRING = ITEMS.register("brass_spring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMALL_STEEL_SPRING = ITEMS.register("small_steel_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_CALORITE_SPRING = ITEMS.register("small_calorite_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_OSTRUM_SPRING = ITEMS.register("small_ostrum_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_DESH_SPRING = ITEMS.register("small_desh_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_IRON_SPRING = ITEMS.register("small_iron_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_GOLDEN_SPRING = ITEMS.register("small_golden_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_COPPER_SPRING = ITEMS.register("small_copper_spring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_BRASS_SPRING = ITEMS.register("small_brass_spring", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GRINDER_BELT = ITEMS.register("grinder_belt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPRING_COILING_MACHINE_WHEEL = ITEMS.register("spring_coiling_machine_wheel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SULFUR_CHUNK = ITEMS.register("sulfur_chunk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VANADIUM_INGOT = ITEMS.register("vanadium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VANADIUM_NUGGET = ITEMS.register("vanadium_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NETHERITE_SHEET = ITEMS.register("netherite_sheet", () -> new Item(new Item.Properties()));

    public static final RegistryObject<CreativeModeTab> VINTAGE_IMPROVEMENT_TAB = CREATIVE_MODE_TABS.register("vintage_improvement_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> VintageBlocks.BELT_GRINDER.get().asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(STEEL_ROD.get());
                output.accept(CALORITE_ROD.get());
                output.accept(OSTRUM_ROD.get());
                output.accept(DESH_ROD.get());

                output.accept(BRASS_WIRE.get());
                output.accept(STEEL_WIRE.get());
                output.accept(CALORITE_WIRE.get());
                output.accept(OSTRUM_WIRE.get());
                output.accept(DESH_WIRE.get());

                output.accept(GRINDER_BELT.get());
                output.accept(VintageBlocks.BELT_GRINDER.get());

                output.accept(SPRING_COILING_MACHINE_WHEEL.get());
                output.accept(VintageBlocks.SPRING_COILING_MACHINE.get());

                output.accept(IRON_SPRING.get());
                output.accept(GOLDEN_SPRING.get());
                output.accept(COPPER_SPRING.get());
                output.accept(BRASS_SPRING.get());
                output.accept(STEEL_SPRING.get());
                output.accept(CALORITE_SPRING.get());
                output.accept(OSTRUM_SPRING.get());
                output.accept(DESH_SPRING.get());

                output.accept(SMALL_IRON_SPRING.get());
                output.accept(SMALL_GOLDEN_SPRING.get());
                output.accept(SMALL_COPPER_SPRING.get());
                output.accept(SMALL_BRASS_SPRING.get());
                output.accept(SMALL_STEEL_SPRING.get());
                output.accept(SMALL_CALORITE_SPRING.get());
                output.accept(SMALL_OSTRUM_SPRING.get());
                output.accept(SMALL_DESH_SPRING.get());

                output.accept(VintageBlocks.VACUUM_CHAMBER.get());

                output.accept(VintageBlocks.VIBRATING_TABLE.get());

                output.accept(VintageBlocks.CENTRIFUGE.get());

                output.accept(VintageBlocks.CURVING_PRESS.get());

                output.accept(VintageItems.REDSTONE_MODULE.get());

                output.accept(SULFUR_CHUNK.get());
                output.accept(SULFUR.get());
                output.accept(VintageBlocks.SULFUR_BLOCK.get());

                output.accept(VANADIUM_NUGGET.get());
                output.accept(VANADIUM_INGOT.get());
                output.accept(VintageBlocks.VANADIUM_BLOCK.get());

                output.accept(VintageFluids.SULFURIC_ACID.getBucket().get());

                output.accept(VintageItems.COPPER_SULFATE);

                output.accept(VintageBlocks.HELVE.get());
                output.accept(NETHERITE_SHEET.get());
            }).build());

    public VintageImprovements() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MY_REGISTRATE.registerEventListeners(modEventBus);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        VintageBlocks.register();
        VintageBlockEntity.register();
        VintageRecipes.register(modEventBus);
        VintagePartialModels.init();
        VintageItems.register();
        VintageFluids.register();

        onCtor();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void onCtor() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        VintageConfig.register(modLoadingContext);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        VintageFluids.registerFluidInteractions();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        VintageRecipesList.init(event.getServer());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            VintagePonder.register();
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
