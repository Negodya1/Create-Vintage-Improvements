package com.negodya1.vintageimprovements.compat.jei.category.assemblies;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVacuumChamber;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.*;

public class AssemblyVacuumizing extends SequencedAssemblySubCategory {

    AnimatedVacuumChamber vacuum;

    public AssemblyVacuumizing() {
        super(25);
        vacuum = new AnimatedVacuumChamber();
    }

    public void setRecipe(IRecipeLayoutBuilder builder, SequencedRecipe<?> recipe, IFocusGroup focuses, int x) {
        if (recipe.getRecipe().getIngredients().size() <= 1 && recipe.getRecipe().getFluidIngredients().isEmpty()) return;

        int offset = 0;

        for (int i = 1; i < recipe.getRecipe().getIngredients().size(); i++) {
            IRecipeSlotBuilder slot = builder
                    .addSlot(RecipeIngredientRole.INPUT, x + 4, 15 + offset * 16)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addIngredients(recipe.getRecipe().getIngredients().get(i));
            offset++;
        }

        for (FluidIngredient fluidIngredient : recipe.getRecipe().getFluidIngredients()) {
            builder
                    .addSlot(RecipeIngredientRole.INPUT, x + 4, 15 + offset * 16)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addIngredients(ForgeTypes.FLUID_STACK, CreateRecipeCategory.withImprovedVisibility(fluidIngredient.getMatchingFluidStacks()))
                    .addTooltipCallback(CreateRecipeCategory.addFluidTooltip(fluidIngredient.getRequiredAmount()));
        }
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, PoseStack ms, double mouseX, double mouseY, int index) {
        ms.pushPose();
        ms.translate(-4, 31, 0);
        ms.scale(.6f, .6f, .6f);
        vacuum.draw(ms, getWidth() / 2, 30, false);
        ms.popPose();
    }

}
