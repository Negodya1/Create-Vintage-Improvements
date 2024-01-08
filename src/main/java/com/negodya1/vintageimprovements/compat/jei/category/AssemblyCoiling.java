package com.negodya1.vintageimprovements.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedCoiling;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedGrinder;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;

public class AssemblyCoiling extends SequencedAssemblySubCategory {

    AnimatedCoiling coiling;

    public AssemblyCoiling() {
        super(25);
        coiling = new AnimatedCoiling();
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, PoseStack ms, double mouseX, double mouseY, int index) {
        ms.pushPose();
        ms.translate(0, 51.5f, 0);
        ms.scale(.6f, .6f, .6f);
        coiling.draw(ms, getWidth() / 2, 30);
        ms.popPose();
    }

}
