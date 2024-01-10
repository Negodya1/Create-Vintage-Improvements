package com.negodya1.vintageimprovements.compat.jei.category;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVacuumChamber;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;

@ParametersAreNonnullByDefault
public class VacuumizingCategory extends BasinCategory {

	private final AnimatedVacuumChamber vacuum = new AnimatedVacuumChamber();

	public VacuumizingCategory(Info<BasinRecipe> info) {
		super(info, false);
	}

	@Override
	public void draw(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		super.draw(recipe, iRecipeSlotsView, matrixStack, mouseX, mouseY);

		vacuum.draw(matrixStack, getBackground().getWidth() / 2 + 3, 34);
	}

}
