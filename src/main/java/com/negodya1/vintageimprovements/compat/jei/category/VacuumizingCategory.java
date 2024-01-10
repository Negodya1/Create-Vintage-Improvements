package com.negodya1.vintageimprovements.compat.jei.category;

import javax.annotation.ParametersAreNonnullByDefault;

import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVacuumChamber;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.client.gui.GuiGraphics;

@ParametersAreNonnullByDefault
public class VacuumizingCategory extends BasinCategory {

	private final AnimatedVacuumChamber vacuum = new AnimatedVacuumChamber();

	public VacuumizingCategory(Info<BasinRecipe> info) {
		super(info, false);
	}

	@Override
	public void draw(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		super.draw(recipe, iRecipeSlotsView, graphics, mouseX, mouseY);

		vacuum.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
	}

}
