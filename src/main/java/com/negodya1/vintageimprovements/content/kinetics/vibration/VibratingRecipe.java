package com.negodya1.vintageimprovements.content.kinetics.vibration;

import com.negodya1.vintageimprovements.VintageBlocks;
import com.negodya1.vintageimprovements.VintageRecipes;
import com.negodya1.vintageimprovements.compat.jei.category.assemblies.AssemblyCoiling;
import com.negodya1.vintageimprovements.compat.jei.category.assemblies.AssemblyVibrating;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class VibratingRecipe extends ProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {
	public VibratingRecipe(ProcessingRecipeParams params) {
		super(VintageRecipes.VIBRATING, params);
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getItem(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

	@Override
	protected int getMaxOutputCount() {
		return 4;
	}

	@Override
	protected boolean canSpecifyDuration() {
		return true;
	}

	@Override
	public void addAssemblyIngredients(List<Ingredient> list) {}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Component getDescriptionForAssembly() {
		return Lang.translateDirect("recipe.assembly.vibrating");
	}
	
	@Override
	public void addRequiredMachines(Set<ItemLike> list) {
		list.add(VintageBlocks.VIBRATING_TABLE.get());
	}
	
	@Override
	public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
		return () -> AssemblyVibrating::new;
	}

}
