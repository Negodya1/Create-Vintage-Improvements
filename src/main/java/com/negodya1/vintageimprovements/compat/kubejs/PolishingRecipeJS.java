package com.negodya1.vintageimprovements.compat.kubejs;

public class PolishingRecipeJS extends ProcessingRecipeJS{

    public PolishingRecipeJS speedLimit(int speed){
        json.addProperty("speed_limits", speed);
        save();
        return this;
    }


}
