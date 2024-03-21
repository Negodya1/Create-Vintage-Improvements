package com.negodya1.vintageimprovements.infrastructure.ponder.scenes;

import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.VintageItems;
import com.negodya1.vintageimprovements.content.kinetics.helve_hammer.HelveBlockEntity;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableBlockEntity;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class HelveScenes {

	public static void processing(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("helve_hammer", "Processing Items with the Helve Hammer");
		scene.configureBasePlate(0, 0, 5);

		Selection cog = util.select.position(5, 0, 2);

		scene.world.showSection(util.select.layer(0)
				.substract(cog), Direction.UP);

		BlockPos helve = util.grid.at(1, 2, 2);
		Selection helveSelect = util.select.position(1, 2, 2);

		BlockPos helveKinetic = util.grid.at(3, 2, 2);
		Selection helveKineticSelect = util.select.position(3, 2, 2);

		Selection cogs = util.select.fromTo(3, 1, 3, 3, 2, 5);
		scene.world.setKineticSpeed(helveKineticSelect, 0);
		scene.world.setBlock(helve.below(), Blocks.OAK_LOG.defaultBlockState(), false);

		scene.idle(5);
		scene.world.showSection(util.select.fromTo(helve.below(), helveKinetic.below()), Direction.DOWN);
		scene.idle(10);
		scene.world.showSection(util.select.fromTo(helve, helveKinetic), Direction.DOWN);
		scene.idle(10);
		Vec3 helveTop = util.vector.centerOf(helve);
		scene.overlay.showText(40)
				.attachKeyFrame()
				.text("Helve Hammer have two operating modes")
				.pointAt(helveTop)
				.placeNearTarget();
		scene.idle(50);

		scene.overlay.showText(40)
				.attachKeyFrame()
				.text("This mode depends on block under Hammer")
				.pointAt(helveTop)
				.placeNearTarget();
		scene.idle(50);

		scene.overlay.showText(40)
				.attachKeyFrame()
				.text("There are two options: Anvil or Smithing Table")
				.pointAt(helveTop)
				.placeNearTarget();
		scene.idle(50);

		scene.world.setBlock(helve.below(), Blocks.SMITHING_TABLE.defaultBlockState(), true);
		scene.overlay.showText(40)
				.attachKeyFrame()
				.text("With Smithing Table Helve Hammer will process any Smithing recipes")
				.pointAt(helveTop)
				.placeNearTarget();
		scene.idle(50);

		scene.world.setBlock(helve.below(), Blocks.ANVIL.defaultBlockState(), true);
		scene.overlay.showText(40)
				.attachKeyFrame()
				.text("With Anvil Helve Hammer will process special Hammering recipes")
				.pointAt(helveTop)
				.placeNearTarget();
		scene.idle(50);

		scene.world.showSection(cogs, Direction.DOWN);
		scene.idle(10);
		scene.world.setKineticSpeed(helveKineticSelect, 128);
		scene.effects.indicateSuccess(helveKinetic);
		scene.idle(10);

		scene.overlay.showText(40)
				.attachKeyFrame()
				.colored(PonderPalette.GREEN)
				.text("They can be powered from the side using shafts")
				.pointAt(util.vector.topOf(helveKinetic.south()))
				.placeNearTarget();
		scene.idle(50);

		ItemStack itemStack = new ItemStack(Items.NETHERITE_INGOT);
		Vec3 entitySpawn = util.vector.topOf(helve.above(3));

		ElementLink<EntityElement> entity1 =
				scene.world.createItemEntity(entitySpawn, util.vector.of(0, 0.2, 0), itemStack);
		scene.idle(18);
		scene.world.modifyEntity(entity1, Entity::discard);
		scene.world.modifyBlockEntity(helve, HelveBlockEntity.class,
				ms -> ms.inputInv.setStackInSlot(0, itemStack));
		scene.idle(10);
		scene.overlay.showControls(new InputWindowElement(helveTop, Pointing.DOWN).withItem(itemStack), 30);
		scene.idle(7);

		scene.overlay.showText(40)
				.attachKeyFrame()
				.text("Throw or Insert items at the top of Anvil or Smithing Table")
				.pointAt(helveTop.add(0, -0.5, 0))
				.placeNearTarget();
		scene.idle(150);

		ItemStack sheet = new ItemStack(VintageImprovements.NETHERITE_SHEET.get());

		scene.world.modifyBlockEntity(helve, VibratingTableBlockEntity.class,
				ms -> ms.inputInv.setStackInSlot(0, ItemStack.EMPTY));
		scene.world.modifyBlockEntity(helve, VibratingTableBlockEntity.class,
				ms -> ms.outputInv.setStackInSlot(0, sheet));

		scene.overlay.showText(50)
				.text("After required hammer blows, the result can be obtained via Right-click")
				.pointAt(util.vector.blockSurface(helve, Direction.WEST))
				.placeNearTarget();
		scene.idle(60);

		scene.overlay.showControls(
				new InputWindowElement(util.vector.blockSurface(helve, Direction.NORTH), Pointing.RIGHT).rightClick()
						.withItem(sheet),
				40);
		scene.idle(50);

		scene.addKeyframe();
		scene.world.showSection(util.select.position(0, 2, 2), Direction.UP);

		scene.world.modifyBlockEntity(helve, HelveBlockEntity.class,
				ms -> ms.outputInv.setStackInSlot(0, ItemStack.EMPTY));
		scene.idle(20);

		scene.overlay.showText(50)
				.text("The items can also be extracted/inserted by automation")
				.pointAt(util.vector.blockSurface(helve, Direction.WEST)
						.add(-.5, .4, 0))
				.placeNearTarget();
		scene.idle(20);

		scene.world.showSection(util.select.fromTo(3, 1,0,1,2,1), Direction.DOWN);

		scene.idle(40);

		scene.markAsFinished();
		scene.idle(25);
		scene.world.modifyEntities(ItemEntity.class, Entity::discard);
	}
}
