package com.negodya1.vintageimprovements.compat.kubejs;


public class CurvingRecipeJS extends ProcessingRecipeJS{

    public CurvingRecipeJS headDamage(int damage){
        json.addProperty("headDamage", damage);
        save();
        return this;
    }

    public CurvingRecipeJS headItem(String item){
        json.addProperty("itemAsHead", item);
        save();
        return this;
    }

    public CurvingRecipeJS mode(int mode){
        json.addProperty("mode", mode);
        save();
        return this;
    }


}
