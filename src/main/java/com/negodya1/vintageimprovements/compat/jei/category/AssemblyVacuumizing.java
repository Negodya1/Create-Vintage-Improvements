package com.negodya1.vintageimprovements.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedCoiling;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedVacuumChamber;
import com.negodya1.vintageimprovements.content.kinetics.vacuum_chamber.VacuumizingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.utility.Lang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;

public class AssemblyVacuumizing extends SequencedAssemblySubCategory {

    AnimatedVacuumChamber vacuum;

    public AssemblyVacuumizing() {
        super(25);
        vacuum = new AnimatedVacuumChamber();
    }

    public void setRecipe(IRecipeLayoutBuilder builder, SequencedRecipe<?> recipe, IFocusGroup focuses, int x) {
        if (recipe.getRecipe().getIngredients().size() <= 1) return;

        for (int i = 1; i < recipe.getRecipe().getIngredients().size(); i++) {
            IRecipeSlotBuilder slot = builder
                    .addSlot(RecipeIngredientRole.INPUT, x + 4, 15 + (i - 1) * 16)
                    .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                    .addIngredients(recipe.getRecipe().getIngredients().get(i));
        }
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, PoseStack ms, double mouseX, double mouseY, int index) {
        ms.pushPose();
        ms.translate(-4, 31, 0);
        ms.scale(.6f, .6f, .6f);
        vacuum.draw(ms, getWidth() / 2, 30);
        ms.popPose();
    }

}
