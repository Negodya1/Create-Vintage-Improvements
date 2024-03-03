package com.negodya1.vintageimprovements.compat.jei;

import com.negodya1.vintageimprovements.foundation.utility.VintageLang;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VintageRecipeUtil {

    public static IRecipeSlotTooltipCallback addTooltip(String lang) {
        return (view, tooltip) -> {
            Component text = VintageLang.translateDirect(lang).withStyle(ChatFormatting.LIGHT_PURPLE);
            if (tooltip.isEmpty())
                tooltip.add(0, text);
            else {
                List<Component> siblings = tooltip.get(0).getSiblings();
                siblings.add(Components.literal(" "));
                siblings.add(text);
            }
        };
    }
}
