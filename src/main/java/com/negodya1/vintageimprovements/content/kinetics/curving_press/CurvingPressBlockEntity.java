package com.negodya1.vintageimprovements.content.kinetics.curving_press;

import java.util.List;
import java.util.Optional;

import com.negodya1.vintageimprovements.VintageImprovements;
import com.negodya1.vintageimprovements.VintageRecipes;
import com.negodya1.vintageimprovements.VintageRecipesList;
import com.negodya1.vintageimprovements.infrastructure.config.VintageConfig;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;

import com.negodya1.vintageimprovements.content.kinetics.curving_press.CurvingBehaviour.Mode;
import com.negodya1.vintageimprovements.content.kinetics.curving_press.CurvingBehaviour.CurvingBehaviourSpecifics;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CurvingPressBlockEntity extends KineticBlockEntity implements CurvingBehaviourSpecifics {

	private static final Object compressingRecipesKey = new Object();

	public CurvingBehaviour pressingBehaviour;

	public CurvingPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected AABB createRenderBoundingBox() {
		return new AABB(worldPosition).expandTowards(0, -1.5, 0)
			.expandTowards(0, 1, 0);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		pressingBehaviour = new CurvingBehaviour(this);
		behaviours.add(pressingBehaviour);
	}

	public void onItemPressed(ItemStack result) {

	}

	public CurvingBehaviour getPressingBehaviour() {
		return pressingBehaviour;
	}

	@Override
	protected void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
	}

	static public boolean canCurve(Recipe recipe) {
		if (!(recipe instanceof CraftingRecipe)) return false;

		ItemStack item = null;

		NonNullList<Ingredient> in = recipe.getIngredients();
		if (in.get(0).isEmpty()) return false;

		int matches = 0;
		boolean it = false;

		for (Ingredient i : in) {
			it = !it;

			if (it) {
				if (!i.isEmpty()) { if (item == null) item = i.getItems()[0]; }
				else return false;

				if (i.test(item)) {
					matches++;
					continue;
				}
			}
			if (!i.isEmpty()) return false;
		}

		if (matches != 3) return false;

		return true;
	}

	private boolean tryToCurve(ItemEntity itemEntity, boolean simulate) {
		if (!VintageConfig.server().recipes.allowAutoCurvingRecipes.get()) return false;

		List<CraftingRecipe> recipes = VintageRecipesList.getCurving();
		Recipe: for (CraftingRecipe recipe : recipes) {
			if (recipe instanceof ShapelessRecipe) continue;
			if (!recipe.canCraftInDimensions(3, 2)) continue;
			if (recipe.getIngredients().size() != 6) continue;

			ItemStack item = itemEntity.getItem();

			NonNullList<Ingredient> in = recipe.getIngredients();

			int matches = 0;

			for (Ingredient i : in) {
				if (i.test(item)) {
					matches++;
					continue;
				}
				if (!i.isEmpty()) continue Recipe;
			}

			if (matches != 3) continue;

			if (simulate) return true;

			ItemStack itemCreated = ItemStack.EMPTY;
			pressingBehaviour.particleItems.add(item);
			if (canProcessInBulk() || item.getCount() == 1) {
				itemEntity.setItem(new ItemStack(recipe.getResultItem(RegistryAccess.EMPTY).getItem()));
			} else {
				if (itemCreated.isEmpty())
					itemCreated = recipe.getResultItem(RegistryAccess.EMPTY).copy();
				ItemEntity created =
						new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), new ItemStack(recipe.getResultItem(RegistryAccess.EMPTY).getItem()));
				created.setDefaultPickUpDelay();
				created.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, level.random, .05f));
				level.addFreshEntity(created);
				item.shrink(1);
			}
			return true;
		}

		return false;
	}

	private boolean tryToCurve(List<ItemStack> outputList, ItemStack item, boolean simulate) {
		if (!VintageConfig.server().recipes.allowAutoCurvingRecipes.get()) return false;

		List<CraftingRecipe> recipes = VintageRecipesList.getCurving();
		Recipe: for (CraftingRecipe recipe : recipes) {
			if (recipe instanceof ShapelessRecipe) continue;
			if (!recipe.canCraftInDimensions(3, 2)) continue;
			if (recipe.getIngredients().size() != 6) continue;

			NonNullList<Ingredient> in = recipe.getIngredients();

			int matches = 0;

			for (Ingredient i : in) {
				if (i.test(item)) {
					matches++;
					continue;
				}
				if (!i.isEmpty()) continue Recipe;
			}

			if (matches != 3) continue;

			if (simulate) return true;

			outputList.add(new ItemStack(recipe.getResultItem(RegistryAccess.EMPTY).getItem()));
			pressingBehaviour.particleItems.add(item);
			return true;
		}

		return false;
	}

	@Override
	public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
		ItemStack item = itemEntity.getItem();
		Optional<CurvingRecipe> recipe = getRecipe(item);
		if (!recipe.isPresent())
			return tryToCurve(itemEntity, simulate);
		if (simulate)
			return true;

		ItemStack itemCreated = ItemStack.EMPTY;
		pressingBehaviour.particleItems.add(item);
		if (canProcessInBulk() || item.getCount() == 1) {
			RecipeApplier.applyRecipeOn(itemEntity, recipe.get());
			itemCreated = itemEntity.getItem()
				.copy();
		} else {
			for (ItemStack result : RecipeApplier.applyRecipeOn(level, ItemHandlerHelper.copyStackWithSize(item, 1),
				recipe.get())) {
				if (itemCreated.isEmpty())
					itemCreated = result.copy();
				ItemEntity created =
					new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
				created.setDefaultPickUpDelay();
				created.setDeltaMovement(VecHelper.offsetRandomly(Vec3.ZERO, level.random, .05f));
				level.addFreshEntity(created);
			}
			item.shrink(1);
		}

		if (!itemCreated.isEmpty())
			onItemPressed(itemCreated);
		return true;
	}

	@Override
	public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
		Optional<CurvingRecipe> recipe = getRecipe(input.stack);
		if (!recipe.isPresent())
			return tryToCurve(outputList, input.stack, simulate);
		if (simulate)
			return true;
		pressingBehaviour.particleItems.add(input.stack);
		List<ItemStack> outputs = RecipeApplier.applyRecipeOn(level,
			canProcessInBulk() ? input.stack : ItemHandlerHelper.copyStackWithSize(input.stack, 1), recipe.get());

		for (ItemStack created : outputs) {
			if (!created.isEmpty()) {
				onItemPressed(created);
				break;
			}
		}

		outputList.addAll(outputs);
		return true;
	}

	private static final RecipeWrapper pressingInv = new RecipeWrapper(new ItemStackHandler(1));

	public Optional<CurvingRecipe> getRecipe(ItemStack item) {
		Optional<CurvingRecipe> assemblyRecipe =
			SequencedAssemblyRecipe.getRecipe(level, item, VintageRecipes.CURVING.getType(), CurvingRecipe.class);
		if (assemblyRecipe.isPresent())
			return assemblyRecipe;

		pressingInv.setItem(0, item);
		assemblyRecipe = VintageRecipes.CURVING.find(pressingInv, level);
		if (assemblyRecipe.isPresent()) return assemblyRecipe;

		return VintageRecipes.CURVING.find(pressingInv, level);
	}

	@Override
	public float getKineticSpeed() {
		return getSpeed();
	}

	@Override
	public boolean canProcessInBulk() {
		return AllConfigs.server().recipes.bulkPressing.get();
	}

	@Override
	public int getParticleAmount() {
		return 15;
	}

}
