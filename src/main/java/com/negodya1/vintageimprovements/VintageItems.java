package com.negodya1.vintageimprovements;

import static com.negodya1.vintageimprovements.VintageImprovements.MY_REGISTRATE;
import static com.simibubi.create.AllTags.forgeItemTag;
import static com.simibubi.create.AllTags.AllItemTags.CREATE_INGOTS;
import static com.simibubi.create.AllTags.AllItemTags.CRUSHED_RAW_MATERIALS;
import static com.simibubi.create.AllTags.AllItemTags.PLATES;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.ALUMINUM;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.LEAD;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.NICKEL;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.OSMIUM;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.PLATINUM;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.QUICKSILVER;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.SILVER;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.TIN;
import static com.simibubi.create.foundation.data.recipe.CompatMetals.URANIUM;

import com.negodya1.vintageimprovements.content.equipment.CopperSulfateItem;
import com.simibubi.create.AllTags;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.contraptions.glue.SuperGlueItem;
import com.simibubi.create.content.contraptions.minecart.MinecartCouplingItem;
import com.simibubi.create.content.contraptions.mounted.MinecartContraptionItem;
import com.simibubi.create.content.equipment.BuildersTeaItem;
import com.simibubi.create.content.equipment.TreeFertilizerItem;
import com.simibubi.create.content.equipment.armor.AllArmorMaterials;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankItem.BacktankBlockItem;
import com.simibubi.create.content.equipment.armor.DivingBootsItem;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import com.simibubi.create.content.equipment.blueprint.BlueprintItem;
import com.simibubi.create.content.equipment.extendoGrip.ExtendoGripItem;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.content.equipment.goggles.GogglesModel;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.simibubi.create.content.equipment.symmetryWand.SymmetryWandItem;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.simibubi.create.content.equipment.zapper.terrainzapper.WorldshaperItem;
import com.simibubi.create.content.kinetics.belt.item.BeltConnectorItem;
import com.simibubi.create.content.kinetics.gearbox.VerticalGearboxItem;
import com.simibubi.create.content.legacy.ChromaticCompoundColor;
import com.simibubi.create.content.legacy.ChromaticCompoundItem;
import com.simibubi.create.content.legacy.RefinedRadianceItem;
import com.simibubi.create.content.legacy.ShadowSteelItem;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.materials.ExperienceNuggetItem;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockItem;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.content.redstone.link.controller.LinkedControllerItem;
import com.simibubi.create.content.schematics.SchematicAndQuillItem;
import com.simibubi.create.content.schematics.SchematicItem;
import com.simibubi.create.content.trains.schedule.ScheduleItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.recipe.CompatMetals;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.Tags;

public class VintageItems {

	static {
		MY_REGISTRATE.setCreativeTab(VintageImprovements.VINTAGE_IMPROVEMENT_TAB);
	}

	public static final ItemEntry<Item> REDSTONE_MODULE = ingredient("redstone_module");
	public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_REDSTONE_MODULE = sequencedIngredient("incomplete_redstone_module");
	public static final ItemEntry<CopperSulfateItem> COPPER_SULFATE =
			MY_REGISTRATE.item("copper_sulfate", CopperSulfateItem::new)
					.register();

	private static ItemEntry<Item> ingredient(String name) {
		return MY_REGISTRATE.item(name, Item::new)
			.register();
	}

	private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
		return MY_REGISTRATE.item(name, SequencedAssemblyItem::new)
			.register();
	}

	@SafeVarargs
	private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
		return MY_REGISTRATE.item(name, Item::new)
			.tag(tags)
			.register();
	}

	private static ItemEntry<TagDependentIngredientItem> compatCrushedOre(CompatMetals metal) {
		String metalName = metal.getName();
		return MY_REGISTRATE
			.item("crushed_raw_" + metalName,
				props -> new TagDependentIngredientItem(props, AllTags.forgeItemTag("ores/" + metalName)))
			.tag(CRUSHED_RAW_MATERIALS.tag)
			.register();
	}

	// Load this class

	public static void register() {}

}
