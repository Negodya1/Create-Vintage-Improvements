package com.negodya1.vintageimprovements;

import java.util.Random;

import com.negodya1.vintageimprovements.infrastructure.ponder.VintagePonder;
import com.negodya1.vintageimprovements.infrastructure.ponder.scenes.BeltGrinderScenes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.behaviour.BlockSpoutingBehaviour;
import com.simibubi.create.compat.Mods;
import com.simibubi.create.compat.computercraft.ComputerCraftProxy;
import com.simibubi.create.compat.curios.Curios;
import com.simibubi.create.content.contraptions.ContraptionMovementSetting;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.content.equipment.potatoCannon.BuiltinPotatoProjectileTypes;
import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.kinetics.TorquePropagator;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.content.redstone.link.RedstoneLinkNetworkHandler;
import com.simibubi.create.content.schematics.ServerSchematicLoader;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.track.AllPortalTracks;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.advancement.AllTriggers;
import com.simibubi.create.foundation.block.CopperRegistries;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.simibubi.create.foundation.utility.AttachedRegistry;
import com.simibubi.create.infrastructure.command.ServerLagger;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.data.CreateDatagen;
import com.simibubi.create.infrastructure.worldgen.AllFeatures;
import com.simibubi.create.infrastructure.worldgen.AllPlacementModifiers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.mojang.logging.LogUtils;
import com.negodya1.vintageimprovements.foundation.data.VintageRegistrate;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import com.negodya1.vintageimprovements.VintageBlocks;

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

    public static final RegistryObject<Item> STEEL_ROD = ITEMS.register("steel_rod", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> CALORITE_ROD = ITEMS.register("calorite_rod", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> OSTRUM_ROD = ITEMS.register("ostrum_rod", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> DESH_ROD = ITEMS.register("desh_rod", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));

    public static final RegistryObject<Item> STEEL_WIRE = ITEMS.register("steel_wire", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> CALORITE_WIRE = ITEMS.register("calorite_wire", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> OSTRUM_WIRE = ITEMS.register("ostrum_wire", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> DESH_WIRE = ITEMS.register("desh_wire", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));

    public static final RegistryObject<Item> STEEL_SPRING = ITEMS.register("steel_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> CALORITE_SPRING = ITEMS.register("calorite_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> OSTRUM_SPRING = ITEMS.register("ostrum_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> DESH_SPRING = ITEMS.register("desh_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> IRON_SPRING = ITEMS.register("iron_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> GOLDEN_SPRING = ITEMS.register("golden_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> COPPER_SPRING = ITEMS.register("copper_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));

    public static final RegistryObject<Item> SMALL_STEEL_SPRING = ITEMS.register("small_steel_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SMALL_CALORITE_SPRING = ITEMS.register("small_calorite_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SMALL_OSTRUM_SPRING = ITEMS.register("small_ostrum_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SMALL_DESH_SPRING = ITEMS.register("small_desh_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SMALL_IRON_SPRING = ITEMS.register("small_iron_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SMALL_GOLDEN_SPRING = ITEMS.register("small_golden_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SMALL_COPPER_SPRING = ITEMS.register("small_copper_spring", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));

    public static final RegistryObject<Item> GRINDER_BELT = ITEMS.register("grinder_belt", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));
    public static final RegistryObject<Item> SPRING_COILING_MACHINE_WHEEL = ITEMS.register("spring_coiling_machine_wheel", () -> new Item(new Item.Properties().tab(VintageCreativeTab.instance)));


    public VintageImprovements() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VintageConfig.SPEC);
        VintageConfig.loadConfig(VintageConfig.SPEC, FMLPaths.CONFIGDIR.get().resolve("vintageimprovements-common.toml"));

        MY_REGISTRATE.registerEventListeners(modEventBus);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        VintageBlocks.register();
        VintageBlockEntity.register();
        VintageRecipes.register(modEventBus);
        VintagePartialModels.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

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

    public static class VintageCreativeTab extends CreativeModeTab {
        private VintageCreativeTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(VintageBlocks.BELT_GRINDER.get());
        }

        public static final VintageCreativeTab instance = new VintageCreativeTab(CreativeModeTab.TABS.length, "vintageimprovements");
    }
}
