package com.negodya1.vintageimprovements.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;

public class KubeJSVintageImprovementsPlugin extends KubeJSPlugin {
    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {
        event.register("vintageimprovements:centrifugation", CentrifugationRecipeJS::new);
        event.register("vintageimprovements:coiling", CoilingRecipeJS::new);
        event.register("vintageimprovements:curving", CurvingRecipeJS::new);
        event.register("vintageimprovements:polishing", PolishingRecipeJS::new);
        event.register("vintageimprovements:hammering", HammeringRecipeJS::new);
        event.register("vintageimprovements:pressurizing", PressurizingRecipeJS::new);
        event.register("vintageimprovements:vacuumizing", PressurizingRecipeJS::new);
        event.register("vintageimprovements:vibrating", ProcessingRecipeJS::new);
    }
}