package com.negodya1.vintageimprovements.infrastructure.ponder.scenes;

import com.google.common.collect.ImmutableList;
import com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber.VacuumChamberBlockEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.IntAttached;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.Pointing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class VacuumChamberScenes {

	public static void processing(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("vacuum_chamber", "Processing Items with the Vacuum Chamber");
		scene.configureBasePlate(0, 0, 5);
		scene.world.setBlock(util.grid.at(1, 1, 2), AllBlocks.ANDESITE_CASING.getDefaultState(), false);
		scene.world.showSection(util.select.layer(0), Direction.UP);
		scene.idle(5);
		scene.world.showSection(util.select.fromTo(1, 4, 3, 1, 1, 5), Direction.DOWN);
		scene.idle(5);
		scene.world.showSection(util.select.position(1, 1, 2), Direction.DOWN);
		scene.idle(5);
		scene.world.showSection(util.select.position(1, 2, 2), Direction.DOWN);
		scene.idle(5);
		scene.world.showSection(util.select.position(1, 4, 2), Direction.SOUTH);
		scene.idle(5);
		scene.world.showSection(util.select.fromTo(3, 1, 1, 1, 1, 1), Direction.SOUTH);
		scene.world.showSection(util.select.fromTo(3, 1, 5, 3, 1, 2), Direction.SOUTH);
		scene.idle(20);

		BlockPos basin = util.grid.at(1, 2, 2);
		BlockPos pressPos = util.grid.at(1, 4, 2);
		Vec3 basinSide = util.vector.blockSurface(basin, Direction.WEST);

		ItemStack bucket = new ItemStack(Items.BUCKET);
		ItemStack snow = new ItemStack(Items.SNOW_BLOCK);
		ItemStack result = new ItemStack(Items.POWDER_SNOW_BUCKET);

		scene.overlay.showText(60)
			.pointAt(basinSide)
			.placeNearTarget()
			.attachKeyFrame()
			.text("With a Vacuum Chamber and Basin you obtain some new recipes");
		scene.idle(40);

		scene.overlay.showControls(new InputWindowElement(util.vector.topOf(basin), Pointing.LEFT).withItem(bucket), 30);
		scene.overlay.showControls(new InputWindowElement(util.vector.topOf(basin), Pointing.RIGHT).withItem(snow), 30);
		scene.idle(30);
		Class<VacuumChamberBlockEntity> type = VacuumChamberBlockEntity.class;
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.startProcessingBasin());
		scene.world.createItemOnBeltLike(basin, Direction.UP, bucket);
		scene.world.createItemOnBeltLike(basin, Direction.UP, snow);
		scene.idle(80);
		scene.world.modifyBlockEntityNBT(util.select.position(basin), BasinBlockEntity.class, nbt -> {
			nbt.put("VisualizedItems",
				NBTHelper.writeCompoundList(ImmutableList.of(IntAttached.with(1, result)), ia -> ia.getValue()
					.serializeNBT()));
		});
		scene.idle(4);
		scene.world.createItemOnBelt(util.grid.at(1, 1, 1), Direction.UP, result);
		scene.idle(30);
	}
}
