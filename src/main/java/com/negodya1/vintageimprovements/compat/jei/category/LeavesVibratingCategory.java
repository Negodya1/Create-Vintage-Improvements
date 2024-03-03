package com.negodya1.vintageimprovements.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVibratingTable;
import com.negodya1.vintageimprovements.content.kinetics.vibration.LeavesVibratingRecipe;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Components;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class LeavesVibratingCategory extends CreateRecipeCategory<LeavesVibratingRecipe> {

	private final AnimatedVibratingTable table = new AnimatedVibratingTable();

	public LeavesVibratingCategory(Info<LeavesVibratingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, LeavesVibratingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 15, 9)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));
	}

	@Override
	public void draw(LeavesVibratingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);

		AllGuiTextures.JEI_SHADOW.render(matrixStack, 48 - 17, 35 + 13);

		table.draw(matrixStack, 48, 35);

		Minecraft.getInstance().font.draw(matrixStack, Components.translatable(VintageImprovements.MODID + ".jei.text.leaves_vibrating.text1"), 87, 3, 0xFAFAFA);
		Minecraft.getInstance().font.draw(matrixStack, Components.translatable(VintageImprovements.MODID + ".jei.text.leaves_vibrating.text2"), 87, 14, 0xFAFAFA);
		Minecraft.getInstance().font.draw(matrixStack, Components.translatable(VintageImprovements.MODID + ".jei.text.leaves_vibrating.text3"), 87, 25, 0xFAFAFA);
		Minecraft.getInstance().font.draw(matrixStack, Components.translatable(VintageImprovements.MODID + ".jei.text.leaves_vibrating.text4"), 87, 36, 0xFAFAFA);
		Minecraft.getInstance().font.draw(matrixStack, Components.translatable(VintageImprovements.MODID + ".jei.text.leaves_vibrating.text5"), 87, 47, 0xFAFAFA);
		Minecraft.getInstance().font.draw(matrixStack, Components.translatable(VintageImprovements.MODID + ".jei.text.leaves_vibrating.text6"), 87, 58, 0xFAFAFA);
	}

}
