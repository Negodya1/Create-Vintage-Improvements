package com.negodya1.vintageimprovements;

import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingBlockEntity;
import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingInstance;
import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingRenderer;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderBlockEntity;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderInstance;
import com.negodya1.vintageimprovements.content.kinetics.grinder.GrinderRenderer;
import com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber.VacuumChamberBlockEntity;
import com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber.VacuumChamberInstance;
import com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber.VacuumChamberRenderer;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableBlockEntity;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableInstance;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.negodya1.vintageimprovements.VintageImprovements.MY_REGISTRATE;

public class VintageBlockEntity {
    public static final BlockEntityEntry<GrinderBlockEntity> GRINDER = MY_REGISTRATE
            .blockEntity("grinder", GrinderBlockEntity::new)
            .instance(() -> GrinderInstance::new)
            .validBlocks(VintageBlocks.BELT_GRINDER)
            .renderer(() -> GrinderRenderer::new)
            .register();

    public static final BlockEntityEntry<CoilingBlockEntity> COILING = MY_REGISTRATE
            .blockEntity("coiling", CoilingBlockEntity::new)
            .instance(() ->  CoilingInstance::new)
            .validBlocks(VintageBlocks.SPRING_COILING_MACHINE)
            .renderer(() -> CoilingRenderer::new)
            .register();

    public static final BlockEntityEntry<VacuumChamberBlockEntity> VACUUM = MY_REGISTRATE
            .blockEntity("vacuum_chamber", VacuumChamberBlockEntity::new)
            .instance(() ->  VacuumChamberInstance::new)
            .validBlocks(VintageBlocks.VACUUM_CHAMBER)
            .renderer(() -> VacuumChamberRenderer::new)
            .register();

    public static final BlockEntityEntry<VibratingTableBlockEntity> VIBRATION = MY_REGISTRATE
            .blockEntity("vibration", VibratingTableBlockEntity::new)
            .instance(() -> VibratingTableInstance::new)
            .validBlocks(VintageBlocks.VIBRATING_TABLE)
            .renderer(() -> VibratingTableRenderer::new)
            .register();

    public static void register() {}
}
