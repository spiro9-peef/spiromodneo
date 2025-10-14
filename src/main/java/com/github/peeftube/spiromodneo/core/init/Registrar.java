package com.github.peeftube.spiromodneo.core.init;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.content.blocks.ManualCrusherBlock;
import com.github.peeftube.spiromodneo.core.init.content.blocks.TapperBlock;
import com.github.peeftube.spiromodneo.core.init.content.blocks.entity.ExtensibleChestBlockEntity;
import com.github.peeftube.spiromodneo.core.init.content.blocks.entity.ExtensibleTrappedChestBlockEntity;
import com.github.peeftube.spiromodneo.core.init.content.blocks.entity.ManualCrusherBlockEntity;
import com.github.peeftube.spiromodneo.core.init.content.recipe.ManualCrusherRecipe;
import com.github.peeftube.spiromodneo.core.init.creative.CTProcessor;
import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.core.screens.ManualCrusherMenu;
import com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.customfeature.GroundStoneFeature;
import com.github.peeftube.spiromodneo.util.MinMax;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.equipment.CustomArmorMaterial;
import com.github.peeftube.spiromodneo.util.loot.SwapLootStackModifier;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Registrar
{
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    //public static final DeferredRegister.Items            ITEMS              = DeferredRegister.createItems(MOD_ID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    // public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    // public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    // public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
    // .alwaysEat().nutrition(1).saturationMod(2f).build()));
    // ==[EXAMPLES END]==

    public static void init()
    {
        IEventBus bus = ModLoadingContext.get().getActiveContainer().getEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);
        ARMOR_MATERIALS.register(bus);
        FEATURES.register(bus);
        LOOTMOD_SERIALIZERS.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    // Loot modifier serializers are being put up here just for ease of access.
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOTMOD_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SpiroMod.MOD_ID);

    public static final Supplier<MapCodec<SwapLootStackModifier>> SWAP_LOOT_STACKS =
            LOOTMOD_SERIALIZERS.register("swap_loot_stacks", () -> SwapLootStackModifier.CODEC);

    public static final Supplier<BlockBehaviour.Properties> STONE_BASED_ORE     = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.NORMAL.get()).sound(SoundType.STONE);
    public static final Supplier<BlockBehaviour.Properties> TUFF_BASED_ORE      = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.NORMAL.get()).sound(SoundType.TUFF);
    public static final Supplier<BlockBehaviour.Properties> DRIPSTONE_BASED_ORE = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.NORMAL.get()).sound(SoundType.DRIPSTONE_BLOCK);
    public static final Supplier<BlockBehaviour.Properties> DEEPSLATE_BASED_ORE = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.TOUGH.get()).sound(SoundType.DEEPSLATE);
    public static final Supplier<BlockBehaviour.Properties> CALCITE_BASED_ORE = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.WEAK.get()).sound(SoundType.CALCITE);
    public static final Supplier<BlockBehaviour.Properties> NETHER_BASED_ORE = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.WEAK.get()).sound(SoundType.NETHERRACK);
    public static final Supplier<BlockBehaviour.Properties> BASALT_BASED_ORE = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.WEAK.get()).sound(SoundType.BASALT);
    public static final Supplier<BlockBehaviour.Properties> RAW_ORE = () ->
            BlockBehaviour.Properties.of().strength(BlockToughnessLevel.NORMAL.get()).sound(SoundType.METAL);

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SpiroMod.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpiroMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SpiroMod.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, SpiroMod.MOD_ID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, SpiroMod.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(BuiltInRegistries.FEATURE, SpiroMod.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS  =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, SpiroMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, SpiroMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpiroMod.MOD_ID);

    // Stone collections should go first to avoid long-term problems.
    public static final StoneCollection STONE_SET = StoneCollection.registerCollection(StoneMaterial.STONE);
    public static final StoneCollection DEEPSLATE_SET = StoneCollection.registerCollection(StoneMaterial.DEEPSLATE);
    public static final StoneCollection ANDESITE_SET = StoneCollection.registerCollection(StoneMaterial.ANDESITE);
    public static final StoneCollection GRANITE_SET = StoneCollection.registerCollection(StoneMaterial.GRANITE);
    public static final StoneCollection DIORITE_SET = StoneCollection.registerCollection(StoneMaterial.DIORITE);
    public static final StoneCollection CALCITE_SET = StoneCollection.registerCollection(StoneMaterial.CALCITE);
    public static final StoneCollection DRIPSTONE_SET = StoneCollection.registerCollection(StoneMaterial.DRIPSTONE);
    public static final StoneCollection SANDSTONE_SET = StoneCollection.registerCollection(StoneMaterial.SANDSTONE);
    public static final StoneCollection RED_SANDSTONE_SET = StoneCollection.registerCollection(StoneMaterial.RED_SANDSTONE);
    public static final StoneCollection BASALT_SET = StoneCollection.registerCollection(StoneMaterial.BASALT);
    public static final StoneCollection NETHERRACK_SET = StoneCollection.registerCollection(StoneMaterial.NETHERRACK);
    public static final StoneCollection TUFF_SET = StoneCollection.registerCollection(StoneMaterial.TUFF);
    public static final StoneCollection ENDSTONE_SET = StoneCollection.registerCollection(StoneMaterial.ENDSTONE);
    public static final StoneCollection LIMBIPETRA_SET = StoneCollection.registerCollection(StoneMaterial.LIMBIPETRA);

    public static final DeferredItem<Item> SINEW = ITEMS.registerSimpleItem("sinew");

    public static final DeferredItem<Item> SMALL_STONE = ITEMS.registerSimpleItem("small_stone");

    public static final DeferredItem<Item> SHARPENED_STICK = ITEMS.registerSimpleItem("sharpened_stick");
    public static final DeferredItem<Item> BUNDLE_OF_SHARP_STICKS =
            ITEMS.registerSimpleItem("bundle_of_sharp_sticks");
    public static final DeferredItem<Item> WOODEN_TOOL_GRAFTING_KIT =
            ITEMS.registerSimpleItem("wooden_tool_grafting_kit");

    // Special steel alloying items
    /** Cast iron mixture is made using one coal or charcoal item with 4 iron ingot items. It is the only form of steel
     * precursor which can be made using vanilla techniques.
     * <p> However, it can produce steel if run through an oxygenated smelting process. */
    public static final DeferredItem<Item> CAST_IRON_MIXTURE = ITEMS.registerSimpleItem("cast_iron_mix");
    /** Cast iron is added but has no particular uses, apart from smelting into steel. Iron ore blocks
     * can in theory be smelted into this but this has not been made a final decision as of writing this documentation.
     * <p> It can be converted back to regular iron once oxygenation is available to the player.
     * <p> To further compound upon the matter, cast iron cannot be made into blocks.
     * <p> TODO: Allow oxygenation. */
    public static final DeferredItem<Item> CAST_IRON = ITEMS.registerSimpleItem("cast_iron");
    /** Steel mixture is made using crushed carbon dust with cast iron or regular iron. The cast iron recipe
     * will require more cast iron, making it more expensive in terms of coal, but produces slightly more
     * steel mixture, offsetting this cost. Steel can be oxygenated to produce regular iron if needed.
     * <p> Steel mixture can also be run through oxygenation as a smelting process to produce extra steel. */
    public static final DeferredItem<Item> STEEL_MIXTURE = ITEMS.registerSimpleItem("steel_mix");
    /** To differentiate iron-based steel mix from cast iron-based steel mix, the two are separate items.
     * This allows them to have unique textures while having roughly the same properties in-game. */
    public static final DeferredItem<Item> WEAK_STEEL_MIXTURE = ITEMS.registerSimpleItem("weak_steel_mix");
    /** Crushed carbon is a coal or charcoal derivative. It can be made from regular coal or charcoal, but
     * when made using normal crafting is expensive to produce, requiring extra carbon sources as well as hard
     * "crusher" items, like bricks or stones, consuming those items in the process. This means steel can be
     * produced in the early game but is more of a commodity due to how expensive vanilla processes become
     * for fuel sources, as well as the time needed to make this happen. */
    public static final DeferredItem<Item> CRUSHED_CARBON = ITEMS.registerSimpleItem("crushed_carbon");

    public static final DeferredBlock<Block> MANUAL_CRUSHER = BLOCKS.register("manual_crusher",
            () -> new ManualCrusherBlock(STONE_BASED_ORE.get().noOcclusion()));
    public static final Supplier<BlockEntityType<ManualCrusherBlockEntity>> MANUAL_CRUSHER_ENTITY =
            BLOCK_ENTITIES.register("manual_crusher_entity", () -> BlockEntityType.Builder.of(
                    ManualCrusherBlockEntity::new, MANUAL_CRUSHER.get()).build(null));
    public static final DeferredItem<Item> MANUAL_CRUSHER_ITEM = regSimpleBlockItem(MANUAL_CRUSHER);

    public static final DeferredHolder<MenuType<?>, MenuType<ManualCrusherMenu>> MANUAL_CRUSHER_MENU =
            MENUS.register("manual_crusher_menu", () -> IMenuTypeExtension.create(ManualCrusherMenu::new));

    public static final DeferredHolder<RecipeSerializer<?>,
            RecipeSerializer<ManualCrusherRecipe>> MANUAL_CRUSHER_SERIALIZER =
        RECIPE_SERIALIZERS.register("manual_crusher", ManualCrusherRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>,
            RecipeType<ManualCrusherRecipe>> MANUAL_CRUSHER_TYPE =
        RECIPE_TYPES.register("manual_crusher", () -> new RecipeType<ManualCrusherRecipe>()
        {
            @Override
            public String toString() { return "manual_crusher"; }
        });

    public static final DeferredItem<Item> NETHER_CLAY = ITEMS.registerSimpleItem("nether_clay");

    public static final DeferredItem<Item> CAOUTCHOUC = ITEMS.registerSimpleItem("caoutchouc");
    public static final DeferredItem<Item> MAPLE_SAP = ITEMS.registerSimpleItem("maple_sap");

    public static final DeferredBlock<Block> TAPPER = BLOCKS.register("tapper",
            () -> new TapperBlock(RAW_ORE.get().noCollission().strength(0.5f, 0f)));
    public static final DeferredItem<Item> TAPPER_ITEM = regSimpleBlockItem(TAPPER);

    // Based on Nyfaria's code:
    // https://shorturl.at/bktNR
    public static <B extends Block> DeferredBlock<B> regBlock(String name, Supplier<B> block)
    { return BLOCKS.register(name, block); }

    public static <I extends Item> DeferredItem<I> regSimpleBlockItem(DeferredBlock<? extends Block> block)
    { return (DeferredItem<I>) ITEMS.registerSimpleBlockItem(block); }

    // Metal collections need to go first since some data sets will reference their contents
    public static final MetalCollection LEAD_METAL = MetalCollection.registerCollection(MetalMaterial.LEAD);
    public static final MetalCollection STEEL_METAL = MetalCollection.registerCollection(MetalMaterial.STEEL);
    public static final MetalCollection CRIMSONITE_METAL = MetalCollection.registerCollection(MetalMaterial.CRIMSONITE);
    public static final MetalCollection STRAVIMITE_METAL = MetalCollection.registerCollection(MetalMaterial.STRAVIMITE);

    // Custom tiers and armor materials go here; each of these should correspond to an equipment collection
    /** Tool tier for sharpened wood. TODO: Add appropriate tag */
    public static final SimpleTier T_SHARPWOOD = new SimpleTier(SpiroTags.Blocks.INCORRECT_FOR_SHARPWOOD,
            59, 6.2F, 1.2F, 12,
            () -> Ingredient.of(Registrar.BUNDLE_OF_SHARP_STICKS.get()));
    /** Tool tier for flint. TODO: Add appropriate tag */
    public static final SimpleTier T_FLINT = new SimpleTier(SpiroTags.Blocks.INCORRECT_FOR_FLINT,
            88, 6.2F, 1.2F, 12, () -> Ingredient.of(Items.FLINT));
    /** Tool tier for copper. TODO: Add appropriate tag */
    public static final SimpleTier T_COPPER = new SimpleTier(SpiroTags.Blocks.INCORRECT_FOR_COPPER,
            240, 6.2F, 1.2F, 12, () -> Ingredient.of(Items.COPPER_INGOT));
    /** Armor Material for copper. */
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> A_COPPER = CustomArmorMaterial.register("copper",
            new int[]{1, 3, 2, 1, 4}, SoundEvents.ARMOR_EQUIP_GOLD, 12, 0.0F, 0.0F,
            () -> Items.COPPER_INGOT);
    /** Tool tier for lead. TODO: Add appropriate tag */
    public static final SimpleTier T_LEAD = new SimpleTier(SpiroTags.Blocks.INCORRECT_FOR_LEAD,
            320, 5.2F, 2.5F, 2,
            () -> Ingredient.of(getIngotFromMetal(LEAD_METAL)));
    /** Armor Material for lead. */
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> A_LEAD = CustomArmorMaterial.register("lead",
            new int[]{2, 5, 3, 1, 7}, SoundEvents.ARMOR_EQUIP_IRON, 2, 1.0F, 2.5F,
            () -> getIngotFromMetal(LEAD_METAL));
    /** Tool tier for steel. TODO: Add appropriate tag */
    public static final SimpleTier T_STEEL = new SimpleTier(SpiroTags.Blocks.INCORRECT_FOR_STEEL,
            650, 6.2F, 4.5F, 4,
            () -> Ingredient.of(getIngotFromMetal(STEEL_METAL)));
    /** Armor Material for steel. */
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> A_STEEL = CustomArmorMaterial.register("steel",
            new int[]{3, 5, 3, 2, 7}, SoundEvents.ARMOR_EQUIP_IRON, 4, 2.0F, 1.5F,
            () -> getIngotFromMetal(STEEL_METAL));

    /** Copper equipment collection. */
    public static final EquipmentCollection SHARPWOOD_EQUIPMENT =
            EquipmentCollection.registerCollection(EquipmentMaterial.SHARPWOOD);
    /** Copper equipment collection. */
    public static final EquipmentCollection FLINT_EQUIPMENT =
            EquipmentCollection.registerCollection(EquipmentMaterial.FLINT);
    /** Copper equipment collection. */
    public static final EquipmentCollection COPPER_EQUIPMENT =
            EquipmentCollection.registerCollection(EquipmentMaterial.COPPER);
    /** Lead equipment collection. */
    public static final EquipmentCollection LEAD_EQUIPMENT =
            EquipmentCollection.registerCollection(EquipmentMaterial.LEAD);
    /** Steel equipment collection. */
    public static final EquipmentCollection STEEL_EQUIPMENT =
            EquipmentCollection.registerCollection(EquipmentMaterial.STEEL);

    public static final OreCollection COAL_ORES = OreCollection.registerCollection(OreMaterial.COAL);
    public static final OreCollection IRON_ORES = OreCollection.registerCollection(OreMaterial.IRON);
    public static final OreCollection COPPER_ORES = OreCollection.registerCollection(OreMaterial.COPPER,
            new MinMax(2, 5));
    public static final OreCollection GOLD_ORES = OreCollection.registerCollection(OreMaterial.GOLD);
    public static final OreCollection LAPIS_ORES = OreCollection.registerCollection(OreMaterial.LAPIS,
            new MinMax(2, 5));
    public static final OreCollection REDSTONE_ORES = OreCollection.registerCollection(OreMaterial.REDSTONE,
            new MinMax(2, 5));
    public static final OreCollection DIAMOND_ORES = OreCollection.registerCollection(OreMaterial.DIAMOND);
    public static final OreCollection EMERALD_ORES = OreCollection.registerCollection(OreMaterial.EMERALD);
    public static final OreCollection QUARTZ_ORES = OreCollection.registerCollection(OreMaterial.QUARTZ,
            new MinMax(2, 5));
    public static final OreCollection RUBY_ORES = OreCollection.registerCollection(OreMaterial.RUBY);
    public static final OreCollection LEAD_ORES = OreCollection.registerCollection(OreMaterial.LEAD,
            new MinMax(1, 3));
    public static final OreCollection METHANE_ICE_ORES = OreCollection.registerCollection(OreMaterial.METHANE_ICE,
            new MinMax(1, 3), FuelOreData.asFuel(120.0f)); // 120s = 2400t, 150% of coal/charcoal
    public static final OreCollection CRIMSONITE_ORES = OreCollection.registerCollection(OreMaterial.CRIMSONITE,
            new MinMax(2, 5));
    public static final OreCollection STRAVIMITE_ORES = OreCollection.registerCollection(OreMaterial.STRAVIMITE,
            new MinMax(2, 5), 10);

    public static final GrassLikeCollection GRASS_TYPE =
            GrassLikeCollection.registerCollection(GrassLike.GRASS);
    public static final GrassLikeCollection MYCELIUM_TYPE =
            GrassLikeCollection.registerCollection(GrassLike.MYCELIUM);
    public static final GrassLikeCollection CRIMSON_NYLIUM_TYPE =
            GrassLikeCollection.registerCollection(GrassLike.CRIMSON_NYLIUM);
    public static final GrassLikeCollection WARPED_NYLIUM_TYPE =
            GrassLikeCollection.registerCollection(GrassLike.WARPED_NYLIUM);
    public static final GrassLikeCollection VITALIUM_TYPE =
            GrassLikeCollection.registerCollection(GrassLike.VITALIUM, 3);

    public static final WoodCollection OAK_WOOD = WoodCollection.registerCollection(WoodMaterial.OAK);
    public static final WoodCollection BIRCH_WOOD = WoodCollection.registerCollection(WoodMaterial.BIRCH);
    public static final WoodCollection SPRUCE_WOOD = WoodCollection.registerCollection(WoodMaterial.SPRUCE);
    public static final WoodCollection JUNGLE_WOOD = WoodCollection.registerCollection(WoodMaterial.JUNGLE);
    public static final WoodCollection ACACIA_WOOD = WoodCollection.registerCollection(WoodMaterial.ACACIA);
    public static final WoodCollection DARK_OAK_WOOD = WoodCollection.registerCollection(WoodMaterial.DARK_OAK);
    public static final WoodCollection CHERRY_WOOD = WoodCollection.registerCollection(WoodMaterial.CHERRY);
    public static final WoodCollection CRIMSON_MYCOMATERIAL = WoodCollection.registerCollection(WoodMaterial.CRIMSON_FUNGUS);
    public static final WoodCollection WARPED_MYCOMATERIAL = WoodCollection.registerCollection(WoodMaterial.WARPED_FUNGUS);
    public static final WoodCollection MANGROVE_WOOD = WoodCollection.registerCollection(WoodMaterial.MANGROVE);
    public static final WoodCollection ASHEN_OAK_WOOD = WoodCollection.registerCollection(WoodMaterial.ASHEN_OAK);
    public static final WoodCollection ASHEN_BIRCH_WOOD = WoodCollection.registerCollection(WoodMaterial.ASHEN_BIRCH);

    public static final TappableWoodCollection RUBBER_WOOD =
            TappableWoodCollection.registerCollection(TappableWoodMaterial.RUBBERWOOD);
    public static final TappableWoodCollection MAPLE_WOOD =
            TappableWoodCollection.registerCollection(TappableWoodMaterial.MAPLE);

    // Going to try setting the order of features lower, maybe this will fix weird bugs I'm having
    public static final DeferredHolder<Feature<?>, GroundStoneFeature> GROUND_STONE_FEATURE =
            FEATURES.register("ground_stone_feature", () -> new GroundStoneFeature(NoneFeatureConfiguration.CODEC));

    // Language key for creative tabs
    public static final String TAB_TITLE_KEY_FORMULAIC = "itemGroup." + SpiroMod.MOD_ID;

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MINERALS_TAB = CREATIVE_MODE_TABS.register("minerals_tab",
            () -> CreativeModeTab.builder().title(Component.translatable(TAB_TITLE_KEY_FORMULAIC + ".minerals_tab"))
                                 .withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> RUBY_ORES.getRawOre().getRawItem().get().getDefaultInstance())
                                 .displayItems((parameters, output) -> { output.acceptAll(CTProcessor.precacheMineralsTab()); })
                                 .build());

    public static final Item getIngotFromMetal(MetalCollection metal)
    { return metal.ingotData().getIngot().get(); }

    public static final Supplier<BlockEntityType<SignBlockEntity>> SIGN_ENTITYTYPE =
            Registrar.BLOCK_ENTITIES.register("sign",
                    () -> BlockEntityType.Builder.of(SignBlockEntity::new,
                            Stream.concat(
                                WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getSignAsBlock),
                                WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getWallSignAsBlock)
                            ).toArray(Block[]::new))
                            .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "sign")));

    public static final Supplier<BlockEntityType<SignBlockEntity>> HANGING_SIGN_ENTITYTYPE =
            Registrar.BLOCK_ENTITIES.register("hanging_sign",
                    () -> BlockEntityType.Builder.of(SignBlockEntity::new,
                            Stream.concat(
                                WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getHangingSignAsBlock),
                                WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getWallHangingSignAsBlock)
                            ).toArray(Block[]::new))
                            .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "hanging_sign")));

    public static final Supplier<BlockEntityType<BarrelBlockEntity>> BARREL_ENTITYTYPE =
            Registrar.BLOCK_ENTITIES.register("barrel",
                    () -> BlockEntityType.Builder.of(BarrelBlockEntity::new,
                    WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getBarrelAsBlock)
                                           .toArray(Block[]::new))
                     .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "barrel")));

    public static final Supplier<BlockEntityType<ExtensibleChestBlockEntity>> CHEST_ENTITYTYPE =
            Registrar.BLOCK_ENTITIES.register("chest",
                    () -> BlockEntityType.Builder.of(ExtensibleChestBlockEntity::new,
                    WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getChestAsBlock)
                                           .toArray(Block[]::new))
                     .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "chest")));

    public static final Supplier<BlockEntityType<ExtensibleTrappedChestBlockEntity>> TRAPPED_CHEST_ENTITYTYPE =
            Registrar.BLOCK_ENTITIES.register("trapped_chest",
                    () -> BlockEntityType.Builder.of(ExtensibleTrappedChestBlockEntity::new,
                    WoodCollection.WOOD_COLLECTIONS.stream().map(WoodCollection::getTrappedChestAsBlock)
                                           .toArray(Block[]::new))
                     .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "trapped_chest")));
}
