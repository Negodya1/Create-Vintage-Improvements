package com.negodya1.vintageimprovements;

import com.negodya1.vintageimprovements.content.kinetics.centrifuge.CentrifugeBlock;
import com.negodya1.vintageimprovements.content.kinetics.centrifuge.CentrifugeGenerator;
import com.negodya1.vintageimprovements.content.kinetics.centrifuge.CentrifugeItem;
import com.negodya1.vintageimprovements.content.kinetics.centrifuge.CentrifugeStructuralBlock;
import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingBlock;
import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingGenerator;
import com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber.VacuumChamberBlock;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableBlock;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableGenerator;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderBlock;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderGenerator;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;

import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MaterialColor;

import static com.negodya1.vintageimprovements.VintageImprovements.MY_REGISTRATE;

public class VintageBlocks {

    static {
        MY_REGISTRATE.creativeModeTab(() -> VintageImprovements.VintageCreativeTab.instance);
    }

    public static final BlockEntry<GrinderBlock> BELT_GRINDER = MY_REGISTRATE.block("belt_grinder", GrinderBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .blockstate(new GrinderGenerator()::generate)
            .transform(BlockStressDefaults.setImpact(VintageConfig.BELT_GRINDER_STRESS_IMPACT.get()))
            .item()
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CoilingBlock> SPRING_COILING_MACHINE = MY_REGISTRATE.block("spring_coiling_machine", CoilingBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate(new CoilingGenerator()::generate)
            .transform(BlockStressDefaults.setImpact(VintageConfig.COILING_MACHINE_STRESS_IMPACT.get()))
            .item()
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<VacuumChamberBlock> VACUUM_CHAMBER = MY_REGISTRATE.block("vacuum_chamber", VacuumChamberBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.noOcclusion().color(MaterialColor.STONE))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(BlockStressDefaults.setImpact(VintageConfig.VACUUM_CHAMBER_STRESS_IMPACT.get()))
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<VibratingTableBlock> VIBRATING_TABLE = MY_REGISTRATE.block("vibrating_table", VibratingTableBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.noOcclusion().color(MaterialColor.STONE))
            .transform(axeOrPickaxe())
            .blockstate(new VibratingTableGenerator()::generate)
            .transform(BlockStressDefaults.setImpact(VintageConfig.VIBRATING_TABLE_STRESS_IMPACT.get()))
            .item()
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CentrifugeBlock> CENTRIFUGE = MY_REGISTRATE.block("centrifuge", CentrifugeBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.noOcclusion().color(MaterialColor.DIRT))
            .transform(axeOrPickaxe())
            .blockstate(new CentrifugeGenerator()::generate)
            .transform(BlockStressDefaults.setImpact(VintageConfig.CENTRIFUGE_STRESS_IMPACT.get()))
            .item(CentrifugeItem::new)
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CentrifugeStructuralBlock> CENTRIFUGE_STRUCTURAL =
            MY_REGISTRATE.block("centrifuge_structure", CentrifugeStructuralBlock::new)
                    .initialProperties(SharedProperties::wooden)
                    .blockstate((c, p) -> p.getVariantBuilder(c.get())
                            .forAllStatesExcept(BlockStateGen.mapToAir(p), CentrifugeStructuralBlock.FACING))
                    .properties(p -> p.noOcclusion().color(MaterialColor.DIRT))
                    .transform(axeOrPickaxe())
                    .lang("Centrifuge")
                    .register();

    public static void register() {}
}
