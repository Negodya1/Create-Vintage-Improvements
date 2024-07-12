package com.negodya1.vintageimprovements.compat.kubejs;

public class HammeringRecipeJS extends ProcessingRecipeJS{

    public HammeringRecipeJS hammerBlows(int number){
        json.addProperty("hammerBlows", number);
        save();
        return this;
    }
    public HammeringRecipeJS anvilBlock(String block){
        json.addProperty("anvilBlock", block);
        save();
        return this;
    }
}
