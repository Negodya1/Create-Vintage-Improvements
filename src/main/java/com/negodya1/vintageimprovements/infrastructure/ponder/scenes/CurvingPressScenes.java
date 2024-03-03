package com.negodya1.vintageimprovements.infrastructure.ponder.scenes;

import com.negodya1.vintageimprovements.content.kinetics.curving_press.CurvingBehaviour;
import com.negodya1.vintageimprovements.content.kinetics.curving_press.CurvingPressBlockEntity;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableBlockEntity;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.BeltItemElement;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class CurvingPressScenes {

	public static void processing(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("curving_press", "Processing Items with the Curving Press");
		scene.configureBasePlate(0, 0, 5);
		scene.world.showSection(util.select.layer(0), Direction.UP);
		scene.idle(5);

		ElementLink<WorldSectionElement> depot =
				scene.world.showIndependentSection(util.select.position(2, 1, 1), Direction.DOWN);
		scene.world.moveSection(depot, util.vector.of(0, 0, 1), 0);
		scene.idle(10);

		Selection pressS = util.select.position(2, 3, 2);
		BlockPos pressPos = util.grid.at(2, 3, 2);
		BlockPos depotPos = util.grid.at(2, 1, 1);
		scene.world.setKineticSpeed(pressS, 0);
		scene.world.showSection(pressS, Direction.DOWN);
		scene.idle(10);

		scene.world.showSection(util.select.fromTo(2, 1, 3, 2, 1, 5), Direction.NORTH);
		scene.idle(3);
		scene.world.showSection(util.select.position(2, 2, 3), Direction.SOUTH);
		scene.idle(3);
		scene.world.showSection(util.select.position(2, 3, 3), Direction.NORTH);
		scene.world.setKineticSpeed(pressS, -32);
		scene.effects.indicateSuccess(pressPos);
		scene.idle(10);

		Vec3 pressSide = util.vector.blockSurface(pressPos, Direction.WEST);
		scene.overlay.showText(60)
				.pointAt(pressSide)
				.placeNearTarget()
				.attachKeyFrame()
				.text("The Curving Press can process items provided beneath it");
		scene.idle(70);
		scene.overlay.showText(60)
				.pointAt(pressSide.subtract(0, 2, 0))
				.placeNearTarget()
				.text("The Input items can be dropped or placed on a Depot under the Press");
		scene.idle(50);
		ItemStack iron = new ItemStack(AllItems.IRON_SHEET.get());
		scene.world.createItemOnBeltLike(depotPos, Direction.NORTH, iron);
		Vec3 depotCenter = util.vector.centerOf(depotPos.south());
		scene.overlay.showControls(new InputWindowElement(depotCenter, Pointing.UP).withItem(iron), 30);
		scene.idle(10);

		Class<CurvingPressBlockEntity> type = CurvingPressBlockEntity.class;
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
				.start(CurvingBehaviour.Mode.BELT));
		scene.idle(30);
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
				.makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), iron));
		scene.world.removeItemsFromBelt(depotPos);
		ItemStack bucket = new ItemStack(Items.BUCKET);
		scene.world.createItemOnBeltLike(depotPos, Direction.UP, bucket);
		scene.idle(10);
		scene.overlay.showControls(new InputWindowElement(depotCenter, Pointing.UP).withItem(bucket), 50);
		scene.idle(60);

		scene.world.hideIndependentSection(depot, Direction.NORTH);
		scene.idle(5);
		scene.world.showSection(util.select.fromTo(0, 1, 3, 0, 2, 3), Direction.DOWN);
		scene.idle(10);
		scene.world.showSection(util.select.fromTo(4, 1, 2, 0, 2, 2), Direction.SOUTH);
		scene.idle(20);
		BlockPos beltPos = util.grid.at(0, 1, 2);
		scene.overlay.showText(40)
				.pointAt(util.vector.blockSurface(beltPos, Direction.WEST))
				.placeNearTarget()
				.attachKeyFrame()
				.text("When items are provided on a belt...");
		scene.idle(30);

		ElementLink<BeltItemElement> ingot = scene.world.createItemOnBelt(beltPos, Direction.SOUTH, iron);
		scene.idle(15);
		ElementLink<BeltItemElement> ingot2 = scene.world.createItemOnBelt(beltPos, Direction.SOUTH, iron);
		scene.idle(15);
		scene.world.stallBeltItem(ingot, true);
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
				.start(CurvingBehaviour.Mode.BELT));

		scene.overlay.showText(50)
				.pointAt(pressSide)
				.placeNearTarget()
				.attachKeyFrame()
				.text("The Press will hold and process them automatically");

		scene.idle(30);
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
				.makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), iron));
		scene.world.removeItemsFromBelt(pressPos.below(2));
		ingot = scene.world.createItemOnBelt(pressPos.below(2), Direction.UP, bucket);
		scene.world.stallBeltItem(ingot, true);
		scene.idle(15);
		scene.world.stallBeltItem(ingot, false);
		scene.idle(15);
		scene.world.stallBeltItem(ingot2, true);
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
				.start(CurvingBehaviour.Mode.BELT));
		scene.idle(30);
		scene.world.modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
				.makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), iron));
		scene.world.removeItemsFromBelt(pressPos.below(2));
		ingot2 = scene.world.createItemOnBelt(pressPos.below(2), Direction.UP, bucket);
		scene.world.stallBeltItem(ingot2, true);
		scene.idle(15);
		scene.world.stallBeltItem(ingot2, false);

	}
}
