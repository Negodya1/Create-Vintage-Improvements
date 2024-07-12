package com.negodya1.vintageimprovements.compat.jei.category;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedGrinder;
import com.negodya1.vintageimprovements.content.kinetics.grinder.PolishingRecipe;
import com.negodya1.vintageimprovements.foundation.gui.VintageGuiTextures;
import com.negodya1.vintageimprovements.foundation.utility.VintageLang;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

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

		int speedLimits = recipe.getSpeedLimits();

		String speedText = 	(speedLimits == 1)? "low":
							(speedLimits == 2)? "medium":
							(speedLimits == 3)? "high":
												"any";
		ChatFormatting style = 	(speedLimits == 1)? ChatFormatting.GREEN:
				 				(speedLimits == 2)? ChatFormatting.YELLOW:
								(speedLimits == 3)? ChatFormatting.RED:
													ChatFormatting.WHITE;

		MutableComponent text = Components.translatable(VintageImprovements.MODID + ".jei.text.required_speed")
						.append(Components.translatable(VintageImprovements.MODID + ".jei.text." + speedText).withStyle(style));

		int width = Minecraft.getInstance().font.width(text);

		Minecraft.getInstance().font.draw(matrixStack,  text, (177 - width)/2.0f, 75, 0xFFFFFF);


		if (recipe.isFragile()) {
			VintageGuiTextures.JEI_FRAGILE.render(matrixStack, 2, 62);
			if (mouseX >= 2 && mouseX <= 15 && mouseY >= 62 && mouseY <= 85)
				Minecraft.getInstance().screen.renderComponentTooltip(matrixStack, addToTooltip(new LinkedList<>()), (int)mouseX,  (int)mouseY);
		}
	}

	private List<Component> addToTooltip(List<Component> list) {
		list.add(VintageLang.translateDirect("jei.text.fragile")
				.withStyle(ChatFormatting.GOLD));
		return list;
	}

}
