package com.negodya1.vintageimprovements.compat.jei.category;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.*;

import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedGrinder;
import com.negodya1.vintageimprovements.content.kinetics.grinder.PolishingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import com.simibubi.create.foundation.utility.Color;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IColorHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
	public void draw(PolishingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 70, 6);
		AllGuiTextures.JEI_SHADOW.render(graphics, 72 - 17, 42 + 13);

		grinder.draw(graphics, 72, 42);

		graphics.drawString(Minecraft.getInstance().font,  Component.translatable(VintageImprovements.MODID + ".jei.text.required_speed"), 40, 75, 0xFFFFFF);

		int speedLimits = recipe.getSpeedLimits();
		switch (speedLimits) {
			case 1:
				graphics.drawString(Minecraft.getInstance().font,  Component.translatable(VintageImprovements.MODID + ".jei.text.low"), 128, 75, 0x00FF00);
				break;
			case 2:
				graphics.drawString(Minecraft.getInstance().font,  Component.translatable(VintageImprovements.MODID + ".jei.text.medium"), 128, 75, 0xFFFF00);
				break;
			case 3:
				graphics.drawString(Minecraft.getInstance().font,  Component.translatable(VintageImprovements.MODID + ".jei.text.high"), 128, 75, 0xFF0000);
				break;
			default:
				graphics.drawString(Minecraft.getInstance().font,  Component.translatable(VintageImprovements.MODID + ".jei.text.any"), 128, 75, 0xFFFFFF);
				break;
		}
	}

}
