package com.negodya1.vintageimprovements.compat.jei.category;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedGrinder;
import com.negodya1.vintageimprovements.infrastructure.config.VintageConfig;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

@ParametersAreNonnullByDefault
public class GrinderSandpaperPolishingCategory extends CreateRecipeCategory<SandPaperPolishingRecipe> {

	private final AnimatedGrinder grinder = new AnimatedGrinder();

	public GrinderSandpaperPolishingCategory(Info<SandPaperPolishingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SandPaperPolishingRecipe recipe, IFocusGroup focuses) {
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
	public void draw(SandPaperPolishingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 70, 6);
		AllGuiTextures.JEI_SHADOW.render(matrixStack, 72 - 17, 42 + 13);

		grinder.draw(matrixStack, 72, 42);

		Minecraft.getInstance().font.draw(matrixStack,  Component.translatable(VintageImprovements.MODID + ".jei.text.required_speed"), 40, 75, 0xFFFFFF);

		int speedLimits = VintageConfig.server().recipes.speedLimitsForSandpaperPolishingRecipes.get();
		switch (speedLimits) {
			case 1:
				Minecraft.getInstance().font.draw(matrixStack,  Component.translatable(VintageImprovements.MODID + ".jei.text.low"), 128, 75, 0x00FF00);
				break;
			case 2:
				Minecraft.getInstance().font.draw(matrixStack,  Component.translatable(VintageImprovements.MODID + ".jei.text.medium"), 128, 75, 0xFFFF00);
				break;
			case 3:
				Minecraft.getInstance().font.draw(matrixStack,  Component.translatable(VintageImprovements.MODID + ".jei.text.high"), 128, 75, 0xFF0000);
				break;
			default:
				Minecraft.getInstance().font.draw(matrixStack,  Component.translatable(VintageImprovements.MODID + ".jei.text.any"), 128, 75, 0xFFFFFF);
				break;
		}
	}

}
