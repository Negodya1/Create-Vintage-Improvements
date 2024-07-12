package com.negodya1.vintageimprovements.compat.kubejs;

public class PressurizingRecipeJS extends ProcessingRecipeJS{

    public PressurizingRecipeJS secondaryFluidInput(int slot){
        json.addProperty("secondaryFluidInputs", slot);
        save();
        return this;
    }
    public PressurizingRecipeJS secondaryFluidOutput(int slot){
        json.addProperty("secondaryFluidResults", slot);
        save();
        return this;
    }


}
