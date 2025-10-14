package com.github.peeftube.spiromodneo.datagen.modules.recipe;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.recipe.ManualCrusherRecipeBuilder;
import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.util.RLUtility;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.equipment.EquipmentData;
import com.github.peeftube.spiromodneo.util.stone.StoneBlockType;
import com.github.peeftube.spiromodneo.util.stone.StoneSubBlockType;
import com.github.peeftube.spiromodneo.util.stone.StoneVariantType;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import com.github.peeftube.spiromodneo.util.wood.PlankBlockSubType;
import com.github.peeftube.spiromodneo.util.wood.SignType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipeDataProv extends RecipeProvider implements IConditionBuilder
{
    public RecipeDataProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    { super(output, lookup); }

    @Override
    protected void buildRecipes(RecipeOutput output)
    {
        // Equipment crafting handler
        for (EquipmentCollection equip : EquipmentCollection.EQUIP_COLLECTIONS)
        { equipmentCraftingHandler(equip, output); }

        // Metal crafting handler
        for (MetalCollection metal : MetalCollection.METAL_COLLECTIONS) { metalCraftingHandler(metal, output); }

        // Alloying handlers
        steelAlloyHandler(output);

        // Stone crafting handler
        for (StoneCollection stone : StoneCollection.STONE_COLLECTIONS) { stoneCraftingHandler(stone, output); }
        netherBrickOverwrite(output);

        // Wood crafting handler
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS) { woodCraftingHandler(wood, output); }

        // Automatic ore smelting handler, this will later handle ore-to-ingot conversions when that's implemented
        // NOTE: The ore-to-ingot conversion mentioned is not block-to-ingot smelting, but item-to-ingot, which will
        //       need to be handled later when new non-gems are added
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreSmeltingHandler(ore, output); }

        // Additional / Other / Loose
        stringLikeHandler(output);
        manualCrusherCraftingHandler(output);
        tapperRecipe(output);
        naturalRubberProcessing(output);
        copperWireCrafting(output);
        structureRodCrafting(output);
    }

    private void structureRodCrafting(RecipeOutput consumer)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Registrar.IRON_STICK, 4)
                .pattern("X")
                .pattern("X")
                .define('X', Items.IRON_INGOT)
                .unlockedBy("has_ingot", has(Items.IRON_INGOT))
                .save(consumer, RLUtility.makeRL("craft_iron_rods"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Registrar.LEAD_STICK, 4)
                .pattern("X")
                .pattern("X")
                .define('X', Registrar.LEAD_METAL.ingotData().getIngot().get())
                .unlockedBy("has_ingot", has(Registrar.LEAD_METAL.ingotData().getIngot().get()))
                .save(consumer, RLUtility.makeRL("craft_lead_rods"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Registrar.STEEL_ROD, 4)
                .pattern("X")
                .pattern("X")
                .define('X', Registrar.STEEL_METAL.ingotData().getIngot().get())
                .unlockedBy("has_ingot", has(Registrar.STEEL_METAL.ingotData().getIngot().get()))
                .save(consumer, RLUtility.makeRL("craft_steel_rods"));
    }

    private void copperWireCrafting(RecipeOutput consumer)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.COPPER_WIRE, 6)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(consumer, RLUtility.makeRL("craft_copper_wire"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.SHIELDED_COPPER_WIRE, 3)
                .requires(Registrar.COPPER_WIRE.get(), 3)
                .requires(Registrar.NATURAL_RUBBER.get(), 1)
                .unlockedBy("has_wire", has(Registrar.COPPER_WIRE.get()))
                .save(consumer, RLUtility.makeRL("add_shielding_to_copper_wire"));
    }

    private void naturalRubberProcessing(RecipeOutput consumer)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.RUBBER_PRECURSOR, 4)
                .requires(Registrar.CAOUTCHOUC.get(), 3)
                .requires(Registrar.CRUSHED_CARBON)
                .unlockedBy("has_latex", has(Registrar.CAOUTCHOUC.get()))
                .save(consumer, RLUtility.makeRL("latex_preparation"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registrar.RUBBER_PRECURSOR), RecipeCategory.MISC,
                Registrar.NATURAL_RUBBER, 2.5f, 3200)
                .unlockedBy("has_latex", has(Registrar.RUBBER_PRECURSOR))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_vulcanize_rubber_in_furnace"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Registrar.RUBBER_PRECURSOR), RecipeCategory.MISC,
                Registrar.NATURAL_RUBBER, 3.0f, 3200)
                .unlockedBy("has_latex", has(Registrar.RUBBER_PRECURSOR))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_vulcanize_rubber_in_blast_furnace"));
    }

    private void woodCraftingHandler(WoodCollection set, RecipeOutput consumer)
    {
        String setName = set.type().getName().replace("_fungus", "");
        Block planks = set.bulkData().planks().get(PlankBlockSubType.BLOCK).getBlock().get();

        boolean planksAreVanilla = BuiltInRegistries.BLOCK.getKey(planks)
              .getNamespace().equalsIgnoreCase("minecraft");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(set.bulkData().logTags().getItemTag())
                .unlockedBy("has_wood", has(set.bulkData().logTags().getItemTag()))
                .save(consumer,
                        planksAreVanilla ? ResourceLocation.withDefaultNamespace(setName + "_planks") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_planks"));

        // Chest and crafting table handler
        if (set.type() == WoodMaterial.OAK)
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Blocks.CRAFTING_TABLE)
                    .pattern("XX")
                    .pattern("XX")
                    .define('X', Blocks.OAK_PLANKS)
                    .unlockedBy("has_planks", has(Blocks.OAK_PLANKS))
                    .save(consumer, ResourceLocation.withDefaultNamespace("crafting_table"));

            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Blocks.CHEST)
                    .pattern("XXX")
                    .pattern("X X")
                    .pattern("XXX")
                    .define('X', Blocks.OAK_PLANKS)
                    .unlockedBy("has_planks", has(Blocks.OAK_PLANKS))
                    .save(consumer, ResourceLocation.withDefaultNamespace("chest"));
        }
        else
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,
                   set.bulkData().manufacturables().get(ManufacturedWoodType.CRAFTING_TABLE).getBlock().get())
                    .pattern("XX")
                    .pattern("XX")
                    .define('X', planks)
                    .unlockedBy("has_planks", has(planks))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID,
                            "spiro_craft_" + setName + "_crafting_table"));

            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,
                   set.bulkData().manufacturables().get(ManufacturedWoodType.CHEST).getBlock().get())
                    .pattern("XXX")
                    .pattern("X X")
                    .pattern("XXX")
                    .define('X', planks)
                    .unlockedBy("has_planks", has(planks))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID,
                            "spiro_craft_" + setName + "_chest"));
        }

        // Barrel handler
        if (set.type() == WoodMaterial.SPRUCE)
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Blocks.BARREL)
                    .pattern("XxX")
                    .pattern("X X")
                    .pattern("XxX")
                    .define('X', Blocks.SPRUCE_PLANKS)
                    .define('x', Blocks.SPRUCE_SLAB)
                    .unlockedBy("has_planks", has(Blocks.SPRUCE_PLANKS))
                    .save(consumer, ResourceLocation.withDefaultNamespace("barrel"));
        }
        else
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,
                   set.bulkData().manufacturables().get(ManufacturedWoodType.BARREL).getBlock().get())
                    .pattern("XxX")
                    .pattern("X X")
                    .pattern("XxX")
                    .define('X', planks)
                    .define('x', set.bulkData().planks().get(PlankBlockSubType.SLAB).getBlock().get())
                    .unlockedBy("has_planks", has(planks))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID,
                            "spiro_craft_" + setName + "_barrel"));
        }

        Block log = set.getBaseLog().get();
        Block stripLog = set.bulkData().livingWood().get(LivingWoodBlockType.STRIPPED_LOG).getBlock().get();
        Block wood = set.bulkData().livingWood().get(LivingWoodBlockType.WOOD).getBlock().get();
        Block stripWood = set.bulkData().livingWood().get(LivingWoodBlockType.STRIPPED_WOOD).getBlock().get();

        boolean logIsVanilla = BuiltInRegistries.BLOCK.getKey(log)
              .getNamespace().equalsIgnoreCase("minecraft");

        String woodSuffix = set.type().isLikeNetherFungus() ? "_hyphae" : "_wood";

        // Rebalancing to 4 as I always thought 4-to-3 was unfair
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wood, 4)
                .pattern("##")
                .pattern("##")
                .define('#', Ingredient.of(log))
                .unlockedBy("has_wood", has(log))
                .save(consumer,
                        logIsVanilla ? ResourceLocation.withDefaultNamespace(setName + woodSuffix) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + woodSuffix));

        // Rebalancing to 4 as I always thought 4-to-3 was unfair
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stripWood, 4)
                .pattern("##")
                .pattern("##")
                .define('#', Ingredient.of(stripLog))
                .unlockedBy("has_wood", has(stripLog))
                .save(consumer,
                        logIsVanilla ? ResourceLocation.withDefaultNamespace("stripped_" + setName + woodSuffix) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_stripped_" + setName + woodSuffix));

        // Sectioned off for plank recipes and manufacturables
        {
            // Vanilla recipes are irrelevant here, no need to override them.
            // However, we will want to add cutting recipes for all planks so this is kept as a
            // cordoned-off section for non-vanilla planks apart from the general additions.
            // Doors and other manufacturables should not be added for vanilla blocks, either.
            // Barrels, chests and crafting tables have already been added above.
            if (!planksAreVanilla)
            {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().planks().get(PlankBlockSubType.SLAB).getBlock().get(), 6)
                        .pattern("XXX")
                        .define('X', planks)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_slab"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().planks().get(PlankBlockSubType.STAIRS).getBlock().get(), 6)
                        .pattern("X  ")
                        .pattern("XX ")
                        .pattern("XXX")
                        .define('X', planks)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_stairs"));

                // Rebalanced to 6 instead of 3
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().planks().get(PlankBlockSubType.FENCE).getBlock().get(), 6)
                        .pattern("XIX")
                        .pattern("XIX")
                        .define('X', planks)
                        .define('I', Items.STICK)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_fence"));

                // Rebalanced to 2 instead of 1
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().planks().get(PlankBlockSubType.FENCE_GATE).getBlock().get(), 2)
                        .pattern("IXI")
                        .pattern("IXI")
                        .define('X', planks)
                        .define('I', Items.STICK)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_fence_gate"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE,
                        set.bulkData().planks().get(PlankBlockSubType.BUTTON).getBlock().get())
                        .requires(Ingredient.of(planks))
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_button"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().planks().get(PlankBlockSubType.PRESSURE_PLATE).getBlock().get())
                        .pattern("XX")
                        .define('X', planks)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_pressure_plate"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().manufacturables().get(ManufacturedWoodType.DOOR).getBlock().get(), 3)
                        .pattern("XX")
                        .pattern("XX")
                        .pattern("XX")
                        .define('X', planks)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_door"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                        set.bulkData().manufacturables().get(ManufacturedWoodType.TRAPDOOR).getBlock().get(), 2)
                        .pattern("XXX")
                        .pattern("XXX")
                        .define('X', planks)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_trapdoor"));

                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,
                        set.bulkData().signs().get(SignType.BASIC).getItem().get(), 3)
                        .pattern("XXX")
                        .pattern("XXX")
                        .pattern(" I ")
                        .define('X', planks)
                        .define('I', Items.STICK)
                        .unlockedBy("has_planks", has(planks))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_" + setName + "_sign"));

                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS,
                        set.bulkData().signs().get(SignType.HANGING).getItem().get(), 6)
                        .pattern("# #")
                        .pattern("XXX")
                        .pattern("XXX")
                        .define('X',
                                set.bulkData().livingWood().get(LivingWoodBlockType.STRIPPED_LOG).getBlock().get())
                        .define('#', Items.CHAIN)
                        .unlockedBy("has_stripped_log",
                                has(set.bulkData().livingWood().get(LivingWoodBlockType.STRIPPED_LOG).getBlock().get()))
                        .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_craft_" + setName + "_hanging_sign"));
            }

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(planks), RecipeCategory.BUILDING_BLOCKS,
                    set.bulkData().planks().get(PlankBlockSubType.SLAB).getBlock().get(), 2)
                    .unlockedBy("has_planks", has(planks))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_saw_"
                            + setName + "_planks_into_" + setName + "_slab"));

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(planks), RecipeCategory.BUILDING_BLOCKS,
                    set.bulkData().planks().get(PlankBlockSubType.STAIRS).getBlock().get(), 2)
                    .unlockedBy("has_planks", has(planks))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_saw_"
                            + setName + "_planks_into_" + setName + "_stairs"));

            // Doubly efficient compared to normal crafting.
            // This may prove OP, but I don't care :)
            SingleItemRecipeBuilder.stonecutting(
                    Ingredient.of(set.bulkData().logTags().getItemTag()), RecipeCategory.BUILDING_BLOCKS,
                    set.bulkData().planks().get(PlankBlockSubType.BLOCK).getBlock().get(), 8)
                    .unlockedBy("has_logs", has(set.bulkData().logTags().getItemTag()))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_saw_"
                            + setName + woodSuffix + "_into_" + setName + "_planks"));

            // This is also probably OP.
            // I still don't care :)
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(planks), RecipeCategory.BUILDING_BLOCKS,
                    set.bulkData().planks().get(PlankBlockSubType.FENCE).getBlock().get(), 2)
                    .unlockedBy("has_planks", has(planks))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_saw_"
                            + setName + "_planks_into_" + setName + "_fence"));
        }
    }

    /**
     * This method is used to prevent the new smelting recipes from conflicting with nether bricks.
     * Nether bricks now have a more involved crafting recipe which uses cobbled netherrack to produce
     * "nether clay," which is then smelted into nether bricks.
     */
    private void netherBrickOverwrite(RecipeOutput consumer)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.NETHER_CLAY, 4)
                              .requires(Ingredient.of(Registrar.NETHERRACK_SET.getCobble().get()), 2)
                .unlockedBy("has_cobbled_netherrack", has(Registrar.NETHERRACK_SET.getCobble().get()))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID,"spiro_cobbled_netherrack_to_nether_clay"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registrar.NETHER_CLAY), RecipeCategory.MISC,
                Items.NETHER_BRICK, 0.1F, 200)
                                  .unlockedBy("has_nether_clay", has(Registrar.NETHER_CLAY))
                .save(consumer, ResourceLocation.withDefaultNamespace("nether_brick"));
    }

    /**
     * This is a very involved method which performs a lot of operations. If you are reading this,
     * it is preferable to set aside time to make sure you root through the entire thing.
     * @param set This is the stone collection parameter being passed in.
     * @param consumer Accepts the <code>RecipeOutput</code> from the data provider.
     */
    private void stoneCraftingHandler(StoneCollection set, RecipeOutput consumer)
    {
        String matName = set.material().get();
        if (matName.equalsIgnoreCase("endstone")) matName = "end_stone";

        boolean cobbleIsVanilla = BuiltInRegistries.BLOCK.getKey(set.getCobble().get())
                                   .getNamespace().equalsIgnoreCase("minecraft");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(set.getCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                set.getBaseStone().get(), 0.1F, 200)
                .unlockedBy("has_cobble", has(set.getCobble().get()))
                .save(consumer,
                        cobbleIsVanilla ? ResourceLocation.withDefaultNamespace(matName) :
                RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_smelt_cobble_to_base"));

        boolean smoothStoneIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.SMOOTH,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block smooth = set.bulkData().getCouplingForKeys(
                        StoneBlockType.SMOOTH,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT).getBlock().get();

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                smooth, 0.1F, 200)
                .unlockedBy("has_base", has(set.getBaseStone().get()))
                .save(consumer,
                        smoothStoneIsVanilla ? ResourceLocation.withDefaultNamespace("smooth_" + matName) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_smelt_base_to_smooth"));

        boolean cutStoneIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.CUT,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block cut = set.bulkData().getCouplingForKeys(
                StoneBlockType.CUT,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.DEFAULT).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cut, 4)
                .pattern("XX")
                .pattern("XX")
                .define('X', smooth)
                .unlockedBy("has_smooth_stone", has(smooth))
                .save(consumer,
                        cutStoneIsVanilla ? ResourceLocation.withDefaultNamespace("cut_" + matName) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_cut_" + matName));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(smooth), RecipeCategory.BUILDING_BLOCKS,
                cut).unlockedBy("has_smooth_stone", has(smooth))
               .save(consumer,
                cutStoneIsVanilla ? ResourceLocation.withDefaultNamespace(
                                "cut_" + matName + "_from_" + matName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_smooth_into_cut_" + matName));

        Block column = set.bulkData().getCouplingForKeys(
                StoneBlockType.COLUMN,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.DEFAULT).getBlock().get();

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                column).unlockedBy("has_base", has(set.getBaseStone().get()))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" +
                        matName + "_column"));

        Block crackedColumn = set.bulkData().getCouplingForKeys(
                StoneBlockType.COLUMN,
                StoneVariantType.CRACKED,
                StoneSubBlockType.DEFAULT).getBlock().get();

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                crackedColumn).unlockedBy("has_base", has(set.getBaseStone().get()))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + matName +
                        "_cracked_column"));

        Block chiseledColumn = set.bulkData().getCouplingForKeys(
                StoneBlockType.COLUMN,
                StoneVariantType.CHISELED,
                StoneSubBlockType.DEFAULT).getBlock().get();

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                chiseledColumn).unlockedBy("has_base", has(set.getBaseStone().get()))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + matName +
                        "_chiseled_column"));

        Block mossyColumn = set.bulkData().getCouplingForKeys(
                StoneBlockType.COLUMN,
                StoneVariantType.MOSSY,
                StoneSubBlockType.DEFAULT).getBlock().get();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, mossyColumn)
                .requires(Ingredient.of(Blocks.VINE)).requires(column)
                .unlockedBy("has_column", has(column))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_combine_vine_into_" +
                        matName + "_mossy_column"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, mossyColumn)
                .requires(Ingredient.of(Blocks.MOSS_BLOCK)).requires(column)
                .unlockedBy("has_column", has(column))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_combine_moss_into_" +
                        matName + "_mossy_column"));

        // Encapsulated for ease of sectioning: BASE
        {
        boolean baseSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.SLAB).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean baseStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.STAIRS).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean baseWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.WALL).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean baseButtonIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.BUTTON).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean basePlateIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.PRESSURE_PLATE).getBlock().get())
                .getNamespace().equalsIgnoreCase("minecraft");

        Block baseSlab = set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.SLAB).getBlock().get();
        Block baseStair = set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.STAIRS).getBlock().get();
        Block baseWall = set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.WALL).getBlock().get();
        Block baseButton = set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.BUTTON).getBlock().get();
        Block basePlate = set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.PRESSURE_PLATE).getBlock().get();
        Block baseGround = set.bulkData().getCouplingForKeys(
                StoneBlockType.BASE,
                StoneVariantType.DEFAULT,
                StoneSubBlockType.GROUND_STONES).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, baseSlab, 6)
                .pattern("XXX")
                .define('X', set.getBaseStone().get())
                .unlockedBy("has_base_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        baseSlabIsVanilla ? ResourceLocation.withDefaultNamespace(matName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + matName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                baseSlab, 2).unlockedBy("has_base_stone", has(set.getBaseStone().get()))
               .save(consumer,
                baseSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                                matName + "_slab_from_" + matName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + matName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, baseStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', set.getBaseStone().get())
                .unlockedBy("has_base_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        baseStairIsVanilla ? ResourceLocation.withDefaultNamespace(matName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + matName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                baseStair, 2).unlockedBy("has_base_stone", has(set.getBaseStone().get()))
               .save(consumer,
                baseStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                matName + "_stairs_from_" + matName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + matName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, baseWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', set.getBaseStone().get())
                .unlockedBy("has_base_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        baseWallIsVanilla ? ResourceLocation.withDefaultNamespace(matName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + matName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                baseWall).unlockedBy("has_base_stone", has(set.getBaseStone().get()))
               .save(consumer,
                baseWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                                matName + "_wall_from_" + matName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + matName + "_wall"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, baseButton)
                .requires(set.getBaseStone().get())
                .unlockedBy("has_base_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        baseButtonIsVanilla ? ResourceLocation.withDefaultNamespace(matName + "_button") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + matName + "_button"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, basePlate)
                .pattern("XX")
                .define('X', set.getBaseStone().get())
                .unlockedBy("has_base_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        basePlateIsVanilla ? ResourceLocation.withDefaultNamespace(matName + "_pressure_plate") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_"
                                + matName + "_pressure_plate"));

            if (set.material() != StoneMaterial.STONE)
            {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, baseGround, 4)
                    .pattern(" X ")
                    .pattern("sss")
                    .define('X', set.getBaseStone().get())
                    .define('s', Registrar.SMALL_STONE.get())
                    .unlockedBy("has_small_stone", has(Registrar.SMALL_STONE.get()))
                    .save(consumer,
                            RLUtility.makeRL(SpiroMod.MOD_ID,
                                    "spiro_" + matName + "_craft_" + matName + "_ground_stones"));
            }
            else
            {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, baseGround, 3)
                    .pattern("sss")
                    .define('s', Registrar.SMALL_STONE.get())
                    .unlockedBy("has_small_stone", has(Registrar.SMALL_STONE.get()))
                    .save(consumer,
                            RLUtility.makeRL(SpiroMod.MOD_ID,
                                    "spiro_craft_" + matName + "_ground_stones"));
            }
        }

        // Encapsulated for ease of sectioning: COBBLE
        {
        boolean isDefaultStone = set.material() == StoneMaterial.STONE;
        String cobbleName = isDefaultStone ? "cobblestone" : "cobbled_" + matName;

        boolean cobbleSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean cobbleStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean cobbleWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block cobbleSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block cobbleStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block cobbleWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.WALL).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cobbleSlab, 6)
                .pattern("XXX")
                .define('X', set.getCobble().get())
                .unlockedBy("has_cobblestone", has(set.getCobble().get()))
                .save(consumer,
                        cobbleSlabIsVanilla ? ResourceLocation.withDefaultNamespace(cobbleName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + cobbleName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                cobbleSlab, 2).unlockedBy("has_cobblestone", has(set.getCobble().get()))
               .save(consumer,
                cobbleSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                        cobbleName + "_slab_from_" + cobbleName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + cobbleName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cobbleStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', set.getCobble().get())
                .unlockedBy("has_cobblestone", has(set.getCobble().get()))
                .save(consumer,
                        cobbleStairIsVanilla ? ResourceLocation.withDefaultNamespace(cobbleName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + cobbleName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                cobbleStair, 2).unlockedBy("has_cobblestone", has(set.getCobble().get()))
               .save(consumer,
                cobbleStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                        cobbleName + "_stairs_from_" + cobbleName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_"
                                + cobbleName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cobbleWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', set.getCobble().get())
                .unlockedBy("has_cobblestone", has(set.getCobble().get()))
                .save(consumer,
                        cobbleWallIsVanilla ? ResourceLocation.withDefaultNamespace(cobbleName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + cobbleName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                cobbleWall).unlockedBy("has_cobblestone", has(set.getCobble().get()))
               .save(consumer,
                cobbleWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                        cobbleName + "_wall_from_" + cobbleName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + cobbleName + "_cut_into_"
                                + cobbleName + "_wall"));

        boolean mossyCobbleIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.getMossyCobble().get()).getNamespace().equalsIgnoreCase("minecraft");
        String mossCobbleName = "mossy_" + cobbleName;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, set.getMossyCobble().get())
                .requires(Ingredient.of(Blocks.VINE)).requires(set.getCobble().get())
                .unlockedBy("has_cobblestone", has(set.getCobble().get()))
                .save(consumer,
                        mossyCobbleIsVanilla ?
                        ResourceLocation.withDefaultNamespace(mossCobbleName + "_from_vine") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + cobbleName + "_combine_vine_into_"
                        + mossCobbleName));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, set.getMossyCobble().get())
                .requires(Ingredient.of(Blocks.MOSS_BLOCK)).requires(set.getCobble().get())
                .unlockedBy("has_cobblestone", has(set.getCobble().get()))
                .save(consumer,
                        mossyCobbleIsVanilla ?
                        ResourceLocation.withDefaultNamespace(mossCobbleName + "_from_moss_block") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + cobbleName + "_combine_moss_into_"
                        + mossCobbleName));

        boolean mossyCobbleSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean mossyCobbleStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean mossyCobbleWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block mossyCobbleSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block mossyCobbleStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block mossyCobbleWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.WALL).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossyCobbleSlab, 6)
                .pattern("XXX")
                .define('X', set.getMossyCobble().get())
                .unlockedBy("has_cobblestone", has(set.getMossyCobble().get()))
                .save(consumer,
                        cobbleSlabIsVanilla ? ResourceLocation.withDefaultNamespace(mossCobbleName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + mossCobbleName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getMossyCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                mossyCobbleSlab, 2).unlockedBy("has_cobblestone", has(set.getMossyCobble().get()))
               .save(consumer,
                mossyCobbleSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                        mossCobbleName + "_slab_from_" + mossCobbleName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + mossCobbleName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossyCobbleStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', set.getMossyCobble().get())
                .unlockedBy("has_cobblestone", has(set.getMossyCobble().get()))
                .save(consumer,
                        mossyCobbleStairIsVanilla ?
                                ResourceLocation.withDefaultNamespace(mossCobbleName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + mossCobbleName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getMossyCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                mossyCobbleStair, 2).unlockedBy("has_cobblestone", has(set.getMossyCobble().get()))
               .save(consumer,
                mossyCobbleStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                        mossCobbleName + "_stairs_from_" + mossCobbleName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + mossCobbleName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossyCobbleWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', set.getMossyCobble().get())
                .unlockedBy("has_cobblestone", has(set.getMossyCobble().get()))
                .save(consumer,
                        mossyCobbleWallIsVanilla ?
                                ResourceLocation.withDefaultNamespace(mossCobbleName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + mossCobbleName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getMossyCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                mossyCobbleWall).unlockedBy("has_cobblestone", has(set.getMossyCobble().get()))
               .save(consumer,
                mossyCobbleWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                        mossCobbleName + "_wall_from_" + mossCobbleName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mossCobbleName + "_cut_into_"
                                + mossCobbleName + "_wall"));

        Block cobbleGround = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.GROUND_STONES).getBlock().get();
        Block mossCobbleGround = set.bulkData().getCouplingForKeys(
                        StoneBlockType.COBBLE,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.GROUND_STONES).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cobbleGround, 4)
            .pattern(" X ")
            .pattern("sss")
            .define('X', set.getCobble().get())
            .define('s', Registrar.SMALL_STONE.get())
            .unlockedBy("has_small_stone", has(Registrar.SMALL_STONE.get()))
            .save(consumer,
                    RLUtility.makeRL(SpiroMod.MOD_ID,
                            "spiro_" + cobbleName + "_craft_" + cobbleName + "_ground_stones"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossCobbleGround, 4)
            .pattern(" X ")
            .pattern("sss")
            .define('X', set.getMossyCobble().get())
            .define('s', Registrar.SMALL_STONE.get())
            .unlockedBy("has_small_stone", has(Registrar.SMALL_STONE.get()))
            .save(consumer,
                    RLUtility.makeRL(SpiroMod.MOD_ID,
                            "spiro_" + mossCobbleName + "_craft_" + mossCobbleName + "_ground_stones"));
        }

        // Encapsulated for ease of sectioning: BRICKS
        {
        String brickName = matName + "_brick"; String brickBaseName = brickName + "s";

        boolean brickIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean brickSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean brickStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean brickWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block brick = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT).getBlock().get();
        Block brickSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block brickStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block brickWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.WALL).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, brick, 4)
                .pattern("XX")
                .pattern("XX")
                .define('X', Ingredient.of(set.getBaseStone().get()))
                .unlockedBy("has_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        brickIsVanilla ? ResourceLocation.withDefaultNamespace(brickBaseName) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + brickBaseName));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getBaseStone().get()), RecipeCategory.BUILDING_BLOCKS,
                brick).unlockedBy("has_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        brickIsVanilla ? ResourceLocation.withDefaultNamespace(
                                brickBaseName + "_from_" + matName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_cut_into_" + brickBaseName));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, brickSlab, 6)
                .pattern("XXX")
                .define('X', Ingredient.of(brick))
                .unlockedBy("has_brick", has(brick))
                .save(consumer,
                        brickSlabIsVanilla ? ResourceLocation.withDefaultNamespace(brickName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + brickBaseName + "_craft_" + brickName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(brick), RecipeCategory.BUILDING_BLOCKS,
                brickSlab, 2).unlockedBy("has_brick", has(brick))
                .save(consumer,
                        brickSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                                brickName + "_slab_from_" + brickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName
                                + "_cut_into_" + brickName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, brickStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', Ingredient.of(brick))
                .unlockedBy("has_brick", has(brick))
                .save(consumer,
                        brickStairIsVanilla ? ResourceLocation.withDefaultNamespace(brickName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + brickBaseName + "_craft_" + brickName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(brick), RecipeCategory.BUILDING_BLOCKS,
                brickStair, 2).unlockedBy("has_brick", has(brick))
                .save(consumer,
                        brickStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                brickName + "_stairs_from_" + brickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName
                                + "_cut_into_" + brickName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, brickWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Ingredient.of(brick))
                .unlockedBy("has_brick", has(brick))
                .save(consumer,
                        brickWallIsVanilla ? ResourceLocation.withDefaultNamespace(brickName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + brickBaseName + "_craft_" + brickName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(brick), RecipeCategory.BUILDING_BLOCKS,
                brickWall).unlockedBy("has_brick", has(brick))
                .save(consumer,
                        brickWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                                brickName + "_wall_from_" + brickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName
                                + "_cut_into_" + brickName + "_wall"));

        boolean mossBrickIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean mossBrickSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean mossBrickStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean mossBrickWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block mossBrick = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.DEFAULT).getBlock().get();
        Block mossBrickSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block mossBrickStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block mossBrickWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.MOSSY,
                        StoneSubBlockType.WALL).getBlock().get();

        String mossBrickName = "mossy_" + brickName; String mossBrickBaseName = mossBrickName + "s";

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, set.getMossyCobble().get())
                .requires(Ingredient.of(Blocks.VINE)).requires(brick)
                .unlockedBy("has_brick", has(brick))
                .save(consumer,
                        mossBrickIsVanilla ?
                        ResourceLocation.withDefaultNamespace(mossBrickBaseName + "_from_vine") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName + "_combine_vine_into_"
                        + mossBrickBaseName));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, set.getMossyCobble().get())
                .requires(Ingredient.of(Blocks.MOSS_BLOCK)).requires(brick)
                .unlockedBy("has_brick", has(brick))
                .save(consumer,
                        mossBrickIsVanilla ?
                        ResourceLocation.withDefaultNamespace(mossBrickBaseName + "_from_moss_block") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName + "_combine_moss_into_"
                        + mossBrickBaseName));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossBrickSlab, 6)
                .pattern("XXX")
                .define('X', Ingredient.of(mossBrick))
                .unlockedBy("has_brick", has(mossBrick))
                .save(consumer,
                        mossBrickSlabIsVanilla ? ResourceLocation.withDefaultNamespace(mossBrickName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + mossBrickBaseName + "_craft_" + mossBrickName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(mossBrick), RecipeCategory.BUILDING_BLOCKS,
                mossBrickSlab, 2).unlockedBy("has_brick", has(mossBrick))
                .save(consumer,
                        mossBrickSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                                mossBrickName + "_slab_from_" + mossBrickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mossBrickBaseName
                                + "_cut_into_" + mossBrickName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossBrickStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', Ingredient.of(mossBrick))
                .unlockedBy("has_brick", has(mossBrick))
                .save(consumer,
                        mossBrickStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                mossBrickName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + mossBrickBaseName + "_craft_" + mossBrickName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(mossBrick), RecipeCategory.BUILDING_BLOCKS,
                mossBrickStair, 2).unlockedBy("has_brick", has(mossBrick))
                .save(consumer,
                        mossBrickStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                mossBrickName + "_stairs_from_" + mossBrickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mossBrickBaseName
                                + "_cut_into_" + mossBrickName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, mossBrickWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Ingredient.of(mossBrick))
                .unlockedBy("has_brick", has(mossBrick))
                .save(consumer,
                        mossBrickWallIsVanilla ? ResourceLocation.withDefaultNamespace(mossBrickName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + mossBrickBaseName + "_craft_" + mossBrickName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(mossBrick), RecipeCategory.BUILDING_BLOCKS,
                mossBrickWall).unlockedBy("has_brick", has(mossBrick))
                .save(consumer,
                        mossBrickWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                                mossBrickName + "_wall_from_" + mossBrickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mossBrickBaseName
                                + "_cut_into_" + mossBrickName + "_wall"));

        boolean crackedBrickIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean crackedBrickSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean crackedBrickStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean crackedBrickWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block crackedBrick = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.DEFAULT).getBlock().get();
        Block crackedBrickSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block crackedBrickStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block crackedBrickWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.WALL).getBlock().get();

        String crackedBrickName = "cracked_" + brickName; String crackedBrickBaseName = crackedBrickName + "s";

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(brick), RecipeCategory.BUILDING_BLOCKS,
                crackedBrick, 1).unlockedBy("has_brick", has(brick))
                .save(consumer,
                        crackedBrickIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedBrickBaseName + "_from_" + brickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName
                                + "_cut_into_" + crackedBrickBaseName));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, crackedBrickSlab, 6)
                .pattern("XXX")
                .define('X', Ingredient.of(crackedBrick))
                .unlockedBy("has_brick", has(crackedBrick))
                .save(consumer,
                        crackedBrickSlabIsVanilla ? ResourceLocation.withDefaultNamespace(crackedBrickName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + crackedBrickBaseName + "_craft_" + crackedBrickName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(crackedBrick), RecipeCategory.BUILDING_BLOCKS,
                crackedBrickSlab, 2).unlockedBy("has_brick", has(crackedBrick))
                .save(consumer,
                        crackedBrickSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedBrickName + "_slab_from_" + crackedBrickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + crackedBrickBaseName
                                + "_cut_into_" + crackedBrickName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, crackedBrickStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', Ingredient.of(crackedBrick))
                .unlockedBy("has_brick", has(crackedBrick))
                .save(consumer,
                        crackedBrickStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedBrickName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + crackedBrickBaseName + "_craft_" + crackedBrickName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(crackedBrick), RecipeCategory.BUILDING_BLOCKS,
                crackedBrickStair, 2).unlockedBy("has_brick", has(crackedBrick))
                .save(consumer,
                        crackedBrickStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedBrickName + "_stairs_from_" + crackedBrickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + crackedBrickBaseName
                                + "_cut_into_" + crackedBrickName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, crackedBrickWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Ingredient.of(crackedBrick))
                .unlockedBy("has_brick", has(crackedBrick))
                .save(consumer,
                        crackedBrickWallIsVanilla ? ResourceLocation.withDefaultNamespace(crackedBrickName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + crackedBrickBaseName + "_craft_" + crackedBrickName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(crackedBrick), RecipeCategory.BUILDING_BLOCKS,
                crackedBrickWall).unlockedBy("has_brick", has(crackedBrick))
                .save(consumer,
                        crackedBrickWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedBrickName + "_wall_from_" + crackedBrickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + crackedBrickBaseName
                                + "_cut_into_" + crackedBrickName + "_wall"));

        boolean chiseledBrickIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CHISELED,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block chiseledBrick = set.bulkData().getCouplingForKeys(
                        StoneBlockType.BRICKS,
                        StoneVariantType.CHISELED,
                        StoneSubBlockType.DEFAULT).getBlock().get();

        String chiseledBrickName = "chiseled_" + brickName; String chiseledBrickBaseName = chiseledBrickName + "s";

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(brick), RecipeCategory.BUILDING_BLOCKS,
                chiseledBrick, 1).unlockedBy("has_brick", has(brick))
                .save(consumer,
                        chiseledBrickIsVanilla ? ResourceLocation.withDefaultNamespace(
                                chiseledBrickBaseName + "_from_" + brickBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + brickBaseName
                                + "_cut_into_" + chiseledBrickBaseName));
        }

        // Encapsulated for ease of sectioning: TILES
        {
        String tileName = matName + "_tile"; String tileBaseName = tileName + "s";

        boolean tileIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean tileSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean tileStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean tileWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block tile = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT).getBlock().get();
        Block tileSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block tileStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block tileWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.WALL).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tile, 4)
                .pattern("XX")
                .pattern("XX")
                .define('X', Ingredient.of(smooth))
                .unlockedBy("has_smooth_stone", has(smooth))
                .save(consumer,
                        tileIsVanilla ? ResourceLocation.withDefaultNamespace(tileBaseName) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_smooth_" + matName + "_craft_" + tileBaseName));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(smooth), RecipeCategory.BUILDING_BLOCKS,
                tile).unlockedBy("has_smooth_stone", has(smooth))
                .save(consumer,
                        tileIsVanilla ? ResourceLocation.withDefaultNamespace(
                                tileBaseName + "_from_smooth_" + matName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_smooth_" + matName + "_cut_into_" + tileBaseName));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tileSlab, 6)
                .pattern("XXX")
                .define('X', Ingredient.of(tile))
                .unlockedBy("has_tile", has(tile))
                .save(consumer,
                        tileSlabIsVanilla ? ResourceLocation.withDefaultNamespace(tileName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + tileBaseName + "_craft_" + tileName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(tile), RecipeCategory.BUILDING_BLOCKS,
                tileSlab, 2).unlockedBy("has_tile", has(tile))
                .save(consumer,
                        tileSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                                tileName + "_slab_from_" + tileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + tileBaseName
                                + "_cut_into_" + tileName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tileStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', Ingredient.of(tile))
                .unlockedBy("has_tile", has(tile))
                .save(consumer,
                        tileStairIsVanilla ? ResourceLocation.withDefaultNamespace(tileName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + tileBaseName + "_craft_" + tileName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(tile), RecipeCategory.BUILDING_BLOCKS,
                tileStair, 2).unlockedBy("has_tile", has(tile))
                .save(consumer,
                        tileStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                tileName + "_stairs_from_" + tileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + tileBaseName
                                + "_cut_into_" + tileName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tileWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Ingredient.of(tile))
                .unlockedBy("has_tile", has(tile))
                .save(consumer,
                        tileWallIsVanilla ? ResourceLocation.withDefaultNamespace(tileName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + tileBaseName + "_craft_" + tileName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(tile), RecipeCategory.BUILDING_BLOCKS,
                tileWall).unlockedBy("has_tile", has(tile))
                .save(consumer,
                        tileWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                                tileName + "_wall_from_" + tileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + tileBaseName
                                + "_cut_into_" + tileName + "_wall"));
        
        boolean crackedTileIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean crackedTileSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean crackedTileStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean crackedTileWallIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.WALL
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block crackedTile = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.DEFAULT).getBlock().get();
        Block crackedTileSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block crackedTileStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.STAIRS).getBlock().get();
        Block crackedTileWall = set.bulkData().getCouplingForKeys(
                        StoneBlockType.TILES,
                        StoneVariantType.CRACKED,
                        StoneSubBlockType.WALL).getBlock().get();

        String crackedTileName = "cracked_" + tileName; String crackedTileBaseName = crackedTileName + "s";

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(tile), RecipeCategory.BUILDING_BLOCKS,
                crackedTile, 1).unlockedBy("has_tile", has(tile))
                .save(consumer,
                        crackedTileIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedTileBaseName + "_from_" + tileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + tileBaseName
                                + "_cut_into_" + crackedTileBaseName));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, crackedTileSlab, 6)
                .pattern("XXX")
                .define('X', Ingredient.of(crackedTile))
                .unlockedBy("has_tile", has(crackedTile))
                .save(consumer,
                        crackedTileSlabIsVanilla ? ResourceLocation.withDefaultNamespace(crackedTileName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + crackedTileBaseName + "_craft_" + crackedTileName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(crackedTile), RecipeCategory.BUILDING_BLOCKS,
                crackedTileSlab, 2).unlockedBy("has_tile", has(crackedTile))
                .save(consumer,
                        crackedTileSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedTileName + "_slab_from_" + crackedTileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + crackedTileBaseName
                                + "_cut_into_" + crackedTileName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, crackedTileStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', Ingredient.of(crackedTile))
                .unlockedBy("has_tile", has(crackedTile))
                .save(consumer,
                        crackedTileStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedTileName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + crackedTileBaseName + "_craft_" + crackedTileName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(crackedTile), RecipeCategory.BUILDING_BLOCKS,
                crackedTileStair, 2).unlockedBy("has_tile", has(crackedTile))
                .save(consumer,
                        crackedTileStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedTileName + "_stairs_from_" + crackedTileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + crackedTileBaseName
                                + "_cut_into_" + crackedTileName + "_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, crackedTileWall, 6)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Ingredient.of(crackedTile))
                .unlockedBy("has_tile", has(crackedTile))
                .save(consumer,
                        crackedTileWallIsVanilla ? ResourceLocation.withDefaultNamespace(crackedTileName + "_wall") :
                        RLUtility.makeRL(SpiroMod.MOD_ID,
                                "spiro_" + crackedTileBaseName + "_craft_" + crackedTileName + "_wall"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(crackedTile), RecipeCategory.BUILDING_BLOCKS,
                crackedTileWall).unlockedBy("has_tile", has(crackedTile))
                .save(consumer,
                        crackedTileWallIsVanilla ? ResourceLocation.withDefaultNamespace(
                                crackedTileName + "_wall_from_" + crackedTileBaseName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + crackedTileBaseName
                                + "_cut_into_" + crackedTileName + "_wall"));
        }

        // Encapsulated for ease of sectioning: SMOOTH
        {
        String smoothName = "smooth_" + matName;

        boolean smoothSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.SMOOTH,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean smoothStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.SMOOTH,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block smoothSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.SMOOTH,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block smoothStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.SMOOTH,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, smoothSlab, 6)
                .pattern("XXX")
                .define('X', smooth)
                .unlockedBy("has_smooth", has(smooth))
                .save(consumer,
                        smoothSlabIsVanilla ? ResourceLocation.withDefaultNamespace(smoothName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + smoothName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(smooth), RecipeCategory.BUILDING_BLOCKS,
                smoothSlab, 2).unlockedBy("has_smooth", has(smooth))
               .save(consumer,
                smoothSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                        smoothName + "_slab_from_" + smoothName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + smoothName + "_cut_into_" + smoothName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, smoothStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', smooth)
                .unlockedBy("has_smooth", has(smooth))
                .save(consumer,
                        smoothStairIsVanilla ? ResourceLocation.withDefaultNamespace(smoothName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + smoothName + "_craft_" + smoothName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(smooth), RecipeCategory.BUILDING_BLOCKS,
                smoothStair, 2).unlockedBy("has_smooth", has(smooth))
               .save(consumer,
                smoothStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                        smoothName + "_stairs_from_" + smoothName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + smoothName + "_cut_into_"
                                + smoothName + "_stairs"));
        }

        // Encapsulated for ease of sectioning: POLISHED
        {
        String polishedName = "polished_" + matName;

        boolean polishedIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block polished = set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.DEFAULT).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, polished, 4)
                .pattern("XX")
                .pattern("XX")
                .define('X', set.getBaseStone().get())
                .unlockedBy("has_stone", has(set.getBaseStone().get()))
                .save(consumer,
                        cutStoneIsVanilla ? ResourceLocation.withDefaultNamespace(polishedName) :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + polishedName));

        boolean polishedSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean polishedStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block polishedSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block polishedStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, polishedSlab, 6)
                .pattern("XXX")
                .define('X', polished)
                .unlockedBy("has_polished", has(polished))
                .save(consumer,
                        polishedSlabIsVanilla ? ResourceLocation.withDefaultNamespace(polishedName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + polishedName + "_craft_" + polishedName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(polished), RecipeCategory.BUILDING_BLOCKS,
                polishedSlab, 2).unlockedBy("has_polished", has(polished))
               .save(consumer,
                polishedSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                        polishedName + "_slab_from_" + polishedName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + polishedName + "_cut_into_" + polishedName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, polishedStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', polished)
                .unlockedBy("has_polished", has(polished))
                .save(consumer,
                        polishedStairIsVanilla ? ResourceLocation.withDefaultNamespace(polishedName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + polishedName + "_craft_" + polishedName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(polished), RecipeCategory.BUILDING_BLOCKS,
                polishedStair, 2).unlockedBy("has_polished", has(polished))
               .save(consumer,
                polishedStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                        polishedName + "_stairs_from_" + polishedName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + polishedName + "_cut_into_"
                                + polishedName + "_stairs"));

        boolean polishedChiseledIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.CHISELED,
                        StoneSubBlockType.DEFAULT
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block polishedChiseled = set.bulkData().getCouplingForKeys(
                        StoneBlockType.POLISHED,
                        StoneVariantType.CHISELED,
                        StoneSubBlockType.DEFAULT).getBlock().get();

        String polishedChiseledName = "polished_chiseled_" + matName;

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(polished), RecipeCategory.BUILDING_BLOCKS,
                polishedChiseled).unlockedBy("has_polished", has(polished))
               .save(consumer,
                       polishedChiseledIsVanilla ? ResourceLocation.withDefaultNamespace(
                        polishedChiseledName + "_from_" + polishedName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + polishedName + "_cut_into_"
                                + polishedChiseledName));
        }

        // Encapsulated for ease of sectioning: CUT
        {
        String cutName = "cut_" + matName;

        boolean cutSlabIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.CUT,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");
        boolean cutStairIsVanilla = BuiltInRegistries.BLOCK.getKey(
                set.bulkData().getCouplingForKeys(
                        StoneBlockType.CUT,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS
                ).getBlock().get()).getNamespace().equalsIgnoreCase("minecraft");

        Block cutSlab = set.bulkData().getCouplingForKeys(
                        StoneBlockType.CUT,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.SLAB).getBlock().get();
        Block cutStair = set.bulkData().getCouplingForKeys(
                        StoneBlockType.CUT,
                        StoneVariantType.DEFAULT,
                        StoneSubBlockType.STAIRS).getBlock().get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cutSlab, 6)
                .pattern("XXX")
                .define('X', cut)
                .unlockedBy("has_cut", has(cut))
                .save(consumer,
                        cutSlabIsVanilla ? ResourceLocation.withDefaultNamespace(cutName + "_slab") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + matName + "_craft_" + cutName + "_slab"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(set.getCobble().get()), RecipeCategory.BUILDING_BLOCKS,
                cutSlab, 2).unlockedBy("has_cut", has(set.getCobble().get()))
               .save(consumer,
                cutSlabIsVanilla ? ResourceLocation.withDefaultNamespace(
                        cutName + "_slab_from_" + cutName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + cutName + "_cut_into_" + cutName + "_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cutStair, 6)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', cut)
                .unlockedBy("has_cut", has(cut))
                .save(consumer,
                        cutStairIsVanilla ? ResourceLocation.withDefaultNamespace(cutName + "_stairs") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + cutName + "_craft_" + cutName + "_stairs"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(cut), RecipeCategory.BUILDING_BLOCKS,
                cutStair, 2).unlockedBy("has_cut", has(cut))
               .save(consumer,
                cutStairIsVanilla ? ResourceLocation.withDefaultNamespace(
                        cutName + "_stairs_from_" + cutName + "_stonecutting") :
                        RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + cutName + "_cut_into_"
                                + cutName + "_stairs"));
        }
    }

    private void tapperRecipe(RecipeOutput consumer)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Registrar.TAPPER)
               .pattern("ICI")
               .pattern(" B ")
               .define('I', Items.IRON_INGOT)
               .define('C', Items.COPPER_INGOT)
               .define('B', Items.BUCKET)
               .unlockedBy("has_a_bucket", has(Items.BUCKET))
               .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_tapper_crafting"));
    }

    private void manualCrusherCraftingHandler(RecipeOutput consumer)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Registrar.MANUAL_CRUSHER)
               .pattern("LPI")
               .pattern("I I")
               .pattern("SSS")
               .define('L', Items.LEVER)
               .define('P', Items.PISTON)
               .define('I', Items.IRON_INGOT) // TODO: Replace with iron rod
               .define('S', Items.SMOOTH_STONE)
               .unlockedBy("has_iron", has(Items.IRON_INGOT))
               .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_manual_crusher_crafting"));

        ManualCrusherRecipeBuilder.crush(Ingredient.of(ItemTags.COALS),
                RecipeCategory.MISC, Registrar.CRUSHED_CARBON.get(), 2, 1.0f)
                .unlockedBy("has_coal", has(ItemTags.COALS))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_crush_carbon_in_manual_crusher"));
    }

    /** As steel is technically an alloy, but the presence of iron in vanilla MC is so pervasive,
     * this special handler method is being added to deal with cases like smelting from iron ore, etc. */
    private void steelAlloyHandler(RecipeOutput consumer)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.CAST_IRON_MIXTURE, 4)
                              .requires(Ingredient.of(ItemTags.COALS))
                              .requires(Ingredient.of(Items.IRON_INGOT), 4)
                              .unlockedBy("has_iron", has(Items.IRON_INGOT))
                              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_cast_iron_mixing"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.STEEL_MIXTURE, 4)
                              .requires(Ingredient.of(Registrar.CRUSHED_CARBON))
                              .requires(Ingredient.of(Items.IRON_INGOT), 4)
                              .unlockedBy("has_carbon", has(Registrar.CRUSHED_CARBON))
                              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_better_steel_mixing"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.WEAK_STEEL_MIXTURE, 7)
                              .requires(Ingredient.of(Registrar.CRUSHED_CARBON))
                              .requires(Ingredient.of(Registrar.CAST_IRON), 6)
                              .unlockedBy("has_cast_iron", has(Registrar.CAST_IRON))
                              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_weaker_steel_mixing"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registrar.CRUSHED_CARBON, 3)
                              .requires(Ingredient.of(ItemTags.COALS), 2)
                              .requires(Ingredient.of(Items.BRICK))
                              .unlockedBy("has_coal", has(ItemTags.COALS))
                              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_weak_carbon_crushing"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registrar.CAST_IRON_MIXTURE), RecipeCategory.MISC,
                Registrar.CAST_IRON, 0.5f, 1600)
                .unlockedBy("has_cast_iron_mix", has(Registrar.CAST_IRON_MIXTURE))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_smelt_cast_iron_from_mix"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Registrar.CAST_IRON_MIXTURE), RecipeCategory.MISC,
                Registrar.CAST_IRON, 1.0f, 800)
                .unlockedBy("has_cast_iron_mix", has(Registrar.CAST_IRON_MIXTURE))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_blast_cast_iron_from_mix"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(SpiroTags.Items.STEEL_MIXTURES), RecipeCategory.MISC,
                Registrar.STEEL_METAL.ingotData().getIngot().get(), 4.0f, 3200)
                .unlockedBy("has_steel_mix", has(SpiroTags.Items.STEEL_MIXTURES))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_blast_steel_from_mix"));
    }

    private void equipmentCraftingHandler(EquipmentCollection set, RecipeOutput consumer)
    {
        boolean isStock = false;

        EquipmentMaterial material = set.getMat();

        if (material == EquipmentMaterial.CHAIN || material == EquipmentMaterial.WOOD ||
                material == EquipmentMaterial.STONE || material == EquipmentMaterial.IRON ||
                material == EquipmentMaterial.LEATHER || material == EquipmentMaterial.GOLD ||
                material == EquipmentMaterial.DIAMOND || material == EquipmentMaterial.NETHERITE )
        { isStock = true; }

        boolean isSharpWood = material == EquipmentMaterial.SHARPWOOD;

        if (!isStock && !isSharpWood)
        {
            String mat = material.getName();
            EquipmentData bulkData = set.bulkData();

            if (set.checkIfNullThenPass(bulkData.getTools()).getResult())
            {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bulkData.getTools().getSword().get())
                                   .pattern("  I")
                                   .pattern(" I ")
                                   .pattern("/  ")
                                   .define('/', Items.STICK)
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_sword_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, bulkData.getTools().getShovel().get())
                                   .pattern("I")
                                   .pattern("/")
                                   .pattern("/")
                                   .define('/', Items.STICK)
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_shovel_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, bulkData.getTools().getHoe().get())
                                   .pattern("II")
                                   .pattern(" /")
                                   .pattern(" /")
                                   .define('/', Items.STICK)
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_hoe_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, bulkData.getTools().getAxe().get())
                                   .pattern("II")
                                   .pattern("I/")
                                   .pattern(" /")
                                   .define('/', Items.STICK)
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_axe_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, bulkData.getTools().getPickaxe().get())
                                   .pattern("III")
                                   .pattern(" / ")
                                   .pattern(" / ")
                                   .define('/', Items.STICK)
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_pickaxe_crafting"));
            }

            if (set.checkIfNullThenPass(bulkData.getArmor()).getResult())
            {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bulkData.getArmor().getHelmet().get())
                                   .pattern("III")
                                   .pattern("I I")
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_helmet_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bulkData.getArmor().getChestplate().get())
                                   .pattern("I I")
                                   .pattern("III")
                                   .pattern("III")
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_chestplate_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bulkData.getArmor().getLeggings().get())
                                   .pattern("III")
                                   .pattern("I I")
                                   .pattern("I I")
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_leggings_crafting"));
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bulkData.getArmor().getBoots().get())
                                   .pattern("I I")
                                   .pattern("I I")
                                   .define('I', Ingredient.of(material.getAssociatedTag()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_boots_crafting"));
            }

            if (set.checkIfNullThenPass(bulkData.getHorseArmor()).getResult())
            {
                // This is potentially a special case, so I'm holding off on putting anything here for now
            }
        }
        if (isSharpWood)
        {
            String mat = material.getName();
            EquipmentData bulkData = set.bulkData();

            // There's special crafting recipes involved here.
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registrar.SHARPENED_STICK.get(), 4)
                    .requires(Ingredient.of(Items.STICK), 4)
                    .requires(Ingredient.of(Registrar.SMALL_STONE.get()))
                    .unlockedBy("has_stick", has(Items.STICK))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_sharpen_stick"));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registrar.BUNDLE_OF_SHARP_STICKS.get())
                    .requires(Ingredient.of(Registrar.SHARPENED_STICK.get()), 3)
                    .requires(Ingredient.of(SpiroTags.Items.STRING_LIKE))
                    .unlockedBy("has_stick", has(Registrar.SHARPENED_STICK.get()))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_bundle_sharp_sticks"));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, Registrar.WOODEN_TOOL_GRAFTING_KIT.get())
                    .requires(Ingredient.of(Registrar.BUNDLE_OF_SHARP_STICKS.get()))
                    .requires(Ingredient.of(Registrar.SMALL_STONE.get()))
                    .requires(Ingredient.of(SpiroTags.Items.STRING_LIKE))
                    .unlockedBy("has_stick", has(Registrar.BUNDLE_OF_SHARP_STICKS.get()))
                    .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_craft_grafting_kit"));

            if (set.checkIfNullThenPass(bulkData.getTools()).getResult())
            {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, bulkData.getTools().getSword().get())
                        .requires(Ingredient.of(Items.WOODEN_SWORD))
                        .requires(Ingredient.of(Registrar.WOODEN_TOOL_GRAFTING_KIT.get()), 2)
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_sword_crafting"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, bulkData.getTools().getShovel().get())
                        .requires(Ingredient.of(Items.WOODEN_SHOVEL))
                        .requires(Ingredient.of(Registrar.WOODEN_TOOL_GRAFTING_KIT.get()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_shovel_crafting"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, bulkData.getTools().getHoe().get())
                        .requires(Ingredient.of(Items.WOODEN_HOE))
                        .requires(Ingredient.of(Registrar.WOODEN_TOOL_GRAFTING_KIT.get()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_hoe_crafting"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, bulkData.getTools().getAxe().get())
                        .requires(Ingredient.of(Items.WOODEN_AXE))
                        .requires(Ingredient.of(Registrar.WOODEN_TOOL_GRAFTING_KIT.get()))
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_axe_crafting"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, bulkData.getTools().getPickaxe().get())
                        .requires(Ingredient.of(Items.WOODEN_PICKAXE))
                        .requires(Ingredient.of(Registrar.WOODEN_TOOL_GRAFTING_KIT.get()), 2)
                                   .unlockedBy("has_material", has(material.getAssociatedTag()))
                                   .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_pickaxe_crafting"));
            }
        }
    }

    private void metalCraftingHandler(MetalCollection set, RecipeOutput consumer)
    {
        String mat = set.getMat().get();

        Item  ingot    = set.ingotData().getIngot().get();
        Block block = set.ingotData().getMetal().getBlock().get();

        // Item-to-block crafting (ingots only) and vice versa
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                           .pattern("III")
                           .pattern("III")
                           .pattern("III")
                           .define('I', ingot)
                           .unlockedBy("has_" + mat + "_ore", has(ingot))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_block_from_ingots"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                              .requires(Ingredient.of(block))
                              .unlockedBy("has_" + mat + "_ore", has(ingot))
                              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_ingots_from_block"));
    }

    private void oreSmeltingHandler(OreCollection set, RecipeOutput consumer)
    {
        TagKey<Item> tag = set.getOreIT();
        String mat = set.getMat().get();

        Item  rawOre    = set.rawOreCoupling().getRawItem().get();
        Block packedOre = set.rawOreCoupling().getCoupling().getBlock().get();

        Item output = ( set.getMat().getIngotConvertible() != null ) ? set.getMat().getIngotConvertible().get() :
                set.getRawOre().getRawItem().get();

        // TODO: Add data to OreCollection which handles the experience and cooking time; should probably be enumerated
        // TODO: There should eventually be an advanced smelter which can output multiple ores per raw ore/ore block
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(tag), RecipeCategory.MISC, output, 1.0f, 200)
                .unlockedBy("has_packed_" + mat + "_ore", has(tag))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_smelt_" + mat + "_from_ore_block"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawOre), RecipeCategory.MISC, output, 1.0f, 200)
              .unlockedBy("has_" + mat + "_ore", has(rawOre))
              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_smelt_" + mat + "_from_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(tag), RecipeCategory.MISC, output, 1.0f, 100)
                .unlockedBy("has_packed_" + mat + "_ore", has(tag))
                .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_blast_" + mat + "_from_ore_block"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(rawOre), RecipeCategory.MISC, output, 1.0f, 100)
              .unlockedBy("has_" + mat + "_ore", has(rawOre))
              .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_blast_" + mat + "_from_ore"));

        // Item-to-block crafting (raw ore only) and vice versa; ONLY DO THIS FOR NON-GEMS
        if (!set.material().isGem())
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, packedOre)
                               .pattern("RRR")
                               .pattern("RRR")
                               .pattern("RRR")
                               .define('R', rawOre)
                               .unlockedBy("has_" + mat + "_ore", has(rawOre))
                               .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_packed_" + mat + "_ore_from_ore"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, rawOre, 9)
                                  .requires(Ingredient.of(packedOre))
                                  .unlockedBy("has_" + mat + "_ore", has(rawOre))
                                  .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_" + mat + "_ore_from_packed_ore"));
        }
    }

    private void stringLikeHandler(RecipeOutput consumer)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.BOW)
                           .pattern(" /S")
                           .pattern("/ S")
                           .pattern(" /S")
                           .define('/', Items.STICK)
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_wooden_bow_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.BUNDLE)
                           .pattern("S")
                           .pattern("L")
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .define('L', Items.LEATHER)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_bundle_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CROSSBOW)
                           .pattern("/I/")
                           .pattern("SXS")
                           .pattern(" / ")
                           .define('/', Items.STICK)
                           .define('X', Items.TRIPWIRE_HOOK)
                           .define('I', Items.IRON_INGOT)
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_crossbow_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.FISHING_ROD)
                           .pattern("  /")
                           .pattern(" /S")
                           .pattern("/ S")
                           .define('/', Items.STICK)
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_fishing_rod_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEAD, 2)
                           .pattern("SS ")
                           .pattern("SS ")
                           .pattern("  S")
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_leash_crafting")); // leash instead of lead to avoid confusion

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LOOM)
                           .pattern("SS")
                           .pattern("XX")
                           .define('X', Ingredient.of(ItemTags.PLANKS))
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_loom_crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.SCAFFOLDING, 6)
                           .pattern("XSX")
                           .pattern("X X")
                           .pattern("X X")
                           .define('X', Items.BAMBOO)
                           .define('S', SpiroTags.Items.STRING_LIKE)
                           .unlockedBy("has_string_like", has(SpiroTags.Items.STRING_LIKE))
                           .save(consumer, RLUtility.makeRL(SpiroMod.MOD_ID, "spiro_scaffold_crafting"));
    }
}
