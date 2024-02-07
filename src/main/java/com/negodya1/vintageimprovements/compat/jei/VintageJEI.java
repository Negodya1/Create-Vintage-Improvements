package com.negodya1.vintageimprovements.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

import com.negodya1.vintageimprovements.VintageBlocks;
import com.negodya1.vintageimprovements.VintageConfig;
import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.VintageRecipes;
import com.negodya1.vintageimprovements.compat.jei.category.*;
import com.negodya1.vintageimprovements.content.kinetics.centrifuge.CentrifugationRecipe;
import com.negodya1.vintageimprovements.content.kinetics.coiling.CoilingRecipe;
import com.negodya1.vintageimprovements.content.kinetics.grinder.PolishingRecipe;
import com.negodya1.vintageimprovements.content.kinetics.vibration.LeavesVibratingRecipe;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingRecipe;
import com.negodya1.vintageimprovements.content.kinetics.vibration.VibratingTableBlockEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.IShapedRecipe;

import static com.simibubi.create.compat.jei.CreateJEI.consumeAllRecipes;

@JeiPlugin
public class VintageJEI implements IModPlugin {

	private static final ResourceLocation ID = new ResourceLocation(VintageImprovements.MODID, "jei_plugin");

	@Override
	@Nonnull
	public ResourceLocation getPluginUid() {
		return ID;
	}

	public IIngredientManager ingredientManager;
	final List<CreateRecipeCategory<?>> ALL = new ArrayList<>();

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		ALL.clear();

		ALL.add(builder(PolishingRecipe.class)
				.addTypedRecipes(VintageRecipes.POLISHING::getType)
				.catalyst(VintageBlocks.BELT_GRINDER::get)
				.itemIcon(VintageBlocks.BELT_GRINDER.get())
				.emptyBackground(177, 85)
				.build("polishing", GrinderPolishingCategory::new));

		ALL.add(builder(CoilingRecipe.class)
				.addTypedRecipes(VintageRecipes.COILING::getType)
				.catalyst(VintageBlocks.SPRING_COILING_MACHINE::get)
				.itemIcon(VintageBlocks.SPRING_COILING_MACHINE.get())
				.emptyBackground(177, 70)
				.build("coiling", CoilingCategory::new));

		ALL.add(builder(BasinRecipe.class)
				.addTypedRecipes(VintageRecipes.VACUUMIZING::getType)
				.catalyst(VintageBlocks.VACUUM_CHAMBER::get)
				.catalyst(AllBlocks.BASIN::get)
				.doubleItemIcon(VintageBlocks.VACUUM_CHAMBER.get(), AllBlocks.BASIN.get())
				.emptyBackground(177, 103)
				.build("vacuumizing", VacuumizingCategory::new));

		ALL.add(builder(VibratingRecipe.class)
				.addTypedRecipes(VintageRecipes.VIBRATING::getType)
				.catalyst(VintageBlocks.VIBRATING_TABLE::get)
				.itemIcon(VintageBlocks.VIBRATING_TABLE.get())
				.emptyBackground(177, 70)
				.build("vibrating", VibratingCategory::new));

		ALL.add(builder(CentrifugationRecipe.class)
				.addTypedRecipes(VintageRecipes.CENTRIFUGATION::getType)
				.catalyst(VintageBlocks.CENTRIFUGE::get)
				.itemIcon(VintageBlocks.CENTRIFUGE.get())
				.emptyBackground(177, 103)
				.build("centrifugation", CentrifugationCategory::new));

		if (VintageConfig.allowSandpaperPolishingOnGrinder) {
			ALL.add(builder(SandPaperPolishingRecipe.class)
					.addTypedRecipes(AllRecipeTypes.SANDPAPER_POLISHING::getType)
					.catalyst(VintageBlocks.BELT_GRINDER::get)
					.doubleItemIcon(VintageBlocks.BELT_GRINDER.get(), AllItems.SAND_PAPER.get())
					.emptyBackground(177, 85)
					.build("grinder_sandpaper_polishing", GrinderSandpaperPolishingCategory::new));
		}

		if (VintageConfig.allowUnpackingOnVibratingTable) {
			ALL.add(builder(CraftingRecipe.class)
					.addAllRecipesIf(r -> r instanceof CraftingRecipe && !(r instanceof IShapedRecipe<?>)
							&& r.getIngredients()
							.size() == 1
							&& VibratingTableBlockEntity.canUnpack(r) && !AllRecipeTypes.shouldIgnoreInAutomation(r))
					.catalyst(VintageBlocks.VIBRATING_TABLE::get)
					.doubleItemIcon(VintageBlocks.VIBRATING_TABLE.get(), Blocks.IRON_BLOCK)
					.emptyBackground(177, 70)
					.build("unpacking", UnpackingCategory::new));
		}

		if (VintageConfig.allowVibratingLeaves) {
			ALL.add(builder(LeavesVibratingRecipe.class)
					.addTypedRecipes(VintageRecipes.LEAVES_VIBRATING::getType)
					.catalyst(VintageBlocks.VIBRATING_TABLE::get)
					.doubleItemIcon(VintageBlocks.VIBRATING_TABLE.get(), Blocks.OAK_LEAVES)
					.emptyBackground(177, 70)
					.build("leaves_vibrating", LeavesVibratingCategory::new));
		}

		ALL.forEach(registration::addRecipeCategories);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ingredientManager = registration.getIngredientManager();
		ALL.forEach(c -> c.registerRecipes(registration));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		ALL.forEach(c -> c.registerCatalysts(registration));
	}

	private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
		return new CategoryBuilder<>(recipeClass);
	}

	private class CategoryBuilder<T extends Recipe<?>> {
		private final Class<? extends T> recipeClass;
		private Predicate<CRecipes> predicate = cRecipes -> true;

		private IDrawable background;
		private IDrawable icon;

		private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
		private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

		public CategoryBuilder(Class<? extends T> recipeClass) {
			this.recipeClass = recipeClass;
		}

		public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
			recipeListConsumers.add(consumer);
			return this;
		}

		public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
			return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
		}

		public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
			catalysts.add(supplier);
			return this;
		}

		public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred) {
			return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
				if (pred.test(recipe)) {
					recipes.add((T) recipe);
				}
			}));
		}

		public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
			return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
				if (pred.test(recipe)) {
					recipes.add(converter.apply(recipe));
				}
			}));
		}

		public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
			return catalystStack(() -> new ItemStack(supplier.get()
					.asItem()));
		}

		public CategoryBuilder<T> icon(IDrawable icon) {
			this.icon = icon;
			return this;
		}

		public CategoryBuilder<T> itemIcon(ItemLike item) {
			icon(new ItemIcon(() -> new ItemStack(item)));
			return this;
		}

		public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
			icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
			return this;
		}

		public CategoryBuilder<T> background(IDrawable background) {
			this.background = background;
			return this;
		}

		public CategoryBuilder<T> emptyBackground(int width, int height) {
			background(new EmptyBackground(width, height));
			return this;
		}

		public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
			Supplier<List<T>> recipesSupplier;
			if (predicate.test(AllConfigs.server().recipes)) {
				recipesSupplier = () -> {
					List<T> recipes = new ArrayList<>();
					for (Consumer<List<T>> consumer : recipeListConsumers)
						consumer.accept(recipes);
					return recipes;
				};
			} else {
				recipesSupplier = () -> Collections.emptyList();
			}

			CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
					new mezz.jei.api.recipe.RecipeType<>(VintageImprovements.asResource(name), recipeClass),
					Component.translatable(VintageImprovements.MODID + ".recipe." + name), background, icon, recipesSupplier, catalysts);
			CreateRecipeCategory<T> category = factory.create(info);
			return category;
		}
	}
}
