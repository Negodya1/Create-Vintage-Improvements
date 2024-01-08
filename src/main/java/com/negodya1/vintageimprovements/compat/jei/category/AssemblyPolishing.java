package com.negodya1.vintageimprovements.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.negodya1.vintageimprovements.compat.jei.category.animations.AnimatedGrinder;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;

public class AssemblyPolishing extends SequencedAssemblySubCategory {

    AnimatedGrinder grinder;

    public AssemblyPolishing() {
        super(25);
        grinder = new AnimatedGrinder();
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, PoseStack ms, double mouseX, double mouseY, int index) {
        ms.pushPose();
        ms.translate(0, 51.5f, 0);
        ms.scale(.6f, .6f, .6f);
        grinder.draw(ms, getWidth() / 2, 30);
        ms.popPose();
    }

}
