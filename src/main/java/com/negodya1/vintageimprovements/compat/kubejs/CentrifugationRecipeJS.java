package com.negodya1.vintageimprovements.compat.kubejs;

import dev.latvian.mods.kubejs.util.ListJS;

public class CentrifugationRecipeJS extends ProcessingRecipeJS{


    public CentrifugationRecipeJS minimalRPM(int rpm){
        json.addProperty("minimalRPM", rpm);
        save();
        return this;
    }


}
