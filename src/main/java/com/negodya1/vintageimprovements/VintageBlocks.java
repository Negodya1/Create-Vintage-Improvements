package com.negodya1.vintageimprovements;

import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingBlock;
import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingGenerator;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderBlock;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderGenerator;
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
            .transform(BlockStressDefaults.setImpact(4.0))
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
            .transform(BlockStressDefaults.setImpact(4.0))
            .item()
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static void register() {}
}
