package com.negodya1.vintageimprovements.compat.kubejs;

import dev.latvian.mods.kubejs.util.ListJS;

import java.util.HexFormat;

public class CoilingRecipeJS extends ProcessingRecipeJS{

    public CoilingRecipeJS springColor(int color){
        json.addProperty("springColor",  HexFormat.of().toHexDigits(color));
        save();
        return this;
    }
}
