package com.negodya1.vintageimprovements;

import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.negodya1.vintageimprovements.content.kinetics.grinder.PolishingRecipe;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class VintageRecipesList {
    static List<CraftingRecipe> unpacking;
    static List<CraftingRecipe> curving;
    static List<PolishingRecipe> polishing;

    static public void init(MinecraftServer level) {
        unpacking = new ArrayList<>();
        curving = new ArrayList<>();

        polishing = level.getRecipeManager().getAllRecipesFor(VintageRecipes.POLISHING.getType());

        initUnpacking(level);
        initCurving(level);
    }

    static void initUnpacking(MinecraftServer level) {
        List<CraftingRecipe> recipes = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
        for (CraftingRecipe recipe : recipes) {
            if (recipe.getIngredients().size() > 1) continue;

            unpacking.add(recipe);
        }
    }

    static void initCurving(MinecraftServer level) {
        List<CraftingRecipe> recipes = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
        Recipe: for (CraftingRecipe recipe : recipes) {
            if (recipe instanceof ShapelessRecipe) continue;
            if (!recipe.canCraftInDimensions(3, 2)) continue;
            if (recipe.getIngredients().size() != 6) continue;

            ItemStack item = null;

            NonNullList<Ingredient> in = recipe.getIngredients();

            int matches = 0;

            Bp: for (Ingredient i : in) {
                for (ItemStack stack : i.getItems()) {
                    if (item == null && stack.getItem() != ItemStack.EMPTY.getItem()) item = stack;
                    if (stack.getItem() == item.getItem()) {
                        matches++;
                        continue Bp;
                    }
                    if (stack.getItem() != ItemStack.EMPTY.getItem()) continue Recipe;
                }
            }

            if (matches != 3) continue;

            curving.add(recipe);
        }
    }

    static public List<CraftingRecipe> getUnpacking() {
        return unpacking;
    }

    static public List<CraftingRecipe> getCurving() {
        return curving;
    }

    static public boolean isPolishing(Recipe<?> r) {
        for (PolishingRecipe recipe : polishing)
            if (recipe.getResultItem(RegistryAccess.EMPTY).getItem() == r.getResultItem(RegistryAccess.EMPTY).getItem())  return false;

        return true;
    }
}
