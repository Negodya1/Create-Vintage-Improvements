package com.negodya1.vintageimprovements;

import static com.negodya1.vintageimprovements.VintageImprovements.MY_REGISTRATE;
import static com.simibubi.create.Create.REGISTRATE;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mojang.math.Vector3f;
import net.minecraftforge.fluids.FluidAttributes;
import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.AllTags.AllFluidTags;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.util.entry.FluidEntry;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class VintageFluids {

	public static final FluidEntry<VirtualFluid> SULFUR_DIOXIDE = MY_REGISTRATE.virtualFluid("sulfur_dioxide")
		.lang("Sulfur Dioxide").register();

	public static final FluidEntry<VirtualFluid> SULFUR_TRIOXIDE = MY_REGISTRATE.virtualFluid("sulfur_trioxide")
			.lang("Sulfur Trioxide").register();


	public static final FluidEntry<ForgeFlowingFluid.Flowing> SULFURIC_ACID =
		MY_REGISTRATE.standardFluid("sulfuric_acid", NoColorFluidAttributes::new)
			.lang("Sulfuric Acid")
				.attributes(b -> b.viscosity(2000)
						.density(1400))
				.properties(p -> p.levelDecreasePerBlock(2)
						.tickRate(25)
						.slopeFindDistance(3)
						.explosionResistance(100f))
			.source(ForgeFlowingFluid.Source::new)
			.bucket()
			.build()
			.register();

	// Load this class

	public static void register() {}

	@Nullable
	public static BlockState getLavaInteraction(FluidState fluidState) {
		Fluid fluid = fluidState.getType();
		if (fluid.isSame(SULFURIC_ACID.get()))
			return AllPaletteStoneTypes.SCORIA.getBaseBlock()
					.get()
					.defaultBlockState();
		return null;
	}

	/**
	 * Removing alpha from tint prevents optifine from forcibly applying biome
	 * colors to modded fluids (Makes translucent fluids disappear)
	 */
	private static class NoColorFluidAttributes extends FluidAttributes {

		protected NoColorFluidAttributes(Builder builder, Fluid fluid) {
			super(builder, fluid);
		}

		@Override
		public int getColor(BlockAndTintGetter world, BlockPos pos) {
			return 0x00ffffff;
		}

	}

}
