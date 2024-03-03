package com.negodya1.vintageimprovements.compat.jei.category;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVibratingTable;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingRecipe;
import com.simibubi.create.compat.jei.category.animations.AnimatedMillstone;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

@ParametersAreNonnullByDefault
public class VibratingCategory extends CreateRecipeCategory<VibratingRecipe> {

	private final AnimatedVibratingTable table = new AnimatedVibratingTable();

	public VibratingCategory(Info<VibratingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, VibratingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 15, 9)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));

		List<ProcessingOutput> results = recipe.getRollableResults();
		boolean single = results.size() == 1;
		int i = 0;
		for (ProcessingOutput output : results) {
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;

			builder
					.addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 133 + xOffset, 27 + yOffset)
					.setBackground(getRenderedSlot(output), -1, -1)
					.addItemStack(output.getStack())
					.addTooltipCallback(addStochasticTooltip(output));

			i++;
		}
	}

	@Override
	public void draw(VibratingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_ARROW.render(matrixStack, 85, 32);
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);

		AllGuiTextures.JEI_SHADOW.render(matrixStack, 48 - 17, 35 + 13);

		table.draw(matrixStack, 48, 35);
	}

}
