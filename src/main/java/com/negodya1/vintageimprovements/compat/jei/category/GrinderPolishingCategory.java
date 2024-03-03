package com.negodya1.vintageimprovements.compat.jei.category;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedGrinder;
import com.negodya1.vintageimprovements.content.kinetics.grinder.PolishingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Components;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IColorHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

@ParametersAreNonnullByDefault
public class GrinderPolishingCategory extends CreateRecipeCategory<PolishingRecipe> {

	private final AnimatedGrinder grinder = new AnimatedGrinder();

	public GrinderPolishingCategory(Info<PolishingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PolishingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 44, 5)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));

		List<ProcessingOutput> results = recipe.getRollableResults();
		int i = 0;
		for (ProcessingOutput output : results) {
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;
			builder
					.addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
					.setBackground(getRenderedSlot(output), -1, -1)
					.addItemStack(output.getStack())
					.addTooltipCallback(addStochasticTooltip(output));
			i++;
		}
	}

	@Override
	public void draw(PolishingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 70, 6);
		AllGuiTextures.JEI_SHADOW.render(matrixStack, 72 - 17, 42 + 13);

		grinder.draw(matrixStack, 72, 42);

		Minecraft.getInstance().font.draw(matrixStack,  Components.translatable(VintageImprovements.MODID + ".jei.text.required_speed"), 40, 75, 0xFFFFFF);

		int speedLimits = recipe.getSpeedLimits();
		switch (speedLimits) {
			case 1:
				Minecraft.getInstance().font.draw(matrixStack,  Components.translatable(VintageImprovements.MODID + ".jei.text.low"), 128, 75, 0x00FF00);
				break;
			case 2:
				Minecraft.getInstance().font.draw(matrixStack,  Components.translatable(VintageImprovements.MODID + ".jei.text.medium"), 128, 75, 0xFFFF00);
				break;
			case 3:
				Minecraft.getInstance().font.draw(matrixStack,  Components.translatable(VintageImprovements.MODID + ".jei.text.high"), 128, 75, 0xFF0000);
				break;
			default:
				Minecraft.getInstance().font.draw(matrixStack,  Components.translatable(VintageImprovements.MODID + ".jei.text.any"), 128, 75, 0xFFFFFF);
				break;
		}
	}

}
