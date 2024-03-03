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
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.CraftingRecipe;

@ParametersAreNonnullByDefault
public class UnpackingCategory extends CreateRecipeCategory<CraftingRecipe> {

	private final AnimatedVibratingTable table = new AnimatedVibratingTable();

	public UnpackingCategory(Info<CraftingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CraftingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 15, 9)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));

		ProcessingOutput result = new ProcessingOutput(recipe.getResultItem().copy(), 1.0f);

		builder
				.addSlot(RecipeIngredientRole.OUTPUT, 139, 27)
				.setBackground(getRenderedSlot(result), -1, -1)
				.addItemStack(result.getStack())
				.addTooltipCallback(addStochasticTooltip(result));
	}

	@Override
	public void draw(CraftingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_ARROW.render(matrixStack, 85, 32);
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);

		AllGuiTextures.JEI_SHADOW.render(matrixStack, 48 - 17, 35 + 13);

		table.draw(matrixStack, 48, 35);
	}

}
