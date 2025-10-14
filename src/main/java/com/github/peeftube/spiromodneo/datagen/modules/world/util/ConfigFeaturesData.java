package com.github.peeftube.spiromodneo.datagen.modules.world.util;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.Soil;
import com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.TargetRuleData;
import com.github.peeftube.spiromodneo.util.RLUtility;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ConfigFeaturesData
{
    private static      HolderGetter<Block>                  blocks;

    /** This is an ancient holdout from a less efficient method of determining per-biome stone types.
     * Good riddance. */
    @Deprecated
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERRIDE_STOCK = registerKey("spiro_override_stock");

    public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE = registerKey("coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE_BURIED = registerKey("coal_ore_buried");

    public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE_GENERIC = registerKey("iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE_SMALL = registerKey("iron_ore_small");

    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE_SMALL = registerKey("copper_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE_LARGE = registerKey("copper_ore_large");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_SMALL = registerKey("diamond_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE = registerKey("diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_LARGE = registerKey("diamond_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_BURIED = registerKey("diamond_ore_buried");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_ORE = registerKey("emerald_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE = registerKey("gold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE_BURIED = registerKey("gold_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_GOLD_ORE = registerKey("nether_gold_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_ORE = registerKey("lapis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_ORE_BURIED = registerKey("lapis_ore_buried");

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDSTONE_ORE = registerKey("redstone_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_ORE = registerKey("quartz_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LEAD_ORE = registerKey("lead_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEAD_ORE_SMALL = registerKey("lead_ore_small");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ORE = registerKey("ruby_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_METHANE_ICE = registerKey("methane_ice_ore_overworld");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_METHANE_ICE = registerKey("methane_ice_ore_nether");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_NETHER_METHANE_ICE = registerKey("methane_ice_ore_mega_nether");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TRACE_CRIMSONITE = registerKey("trace_crimsonite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSONITE = registerKey("crimsonite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CRIMSONITE = registerKey("mega_crimsonite_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TRACE_STRAVIMITE = registerKey("trace_stravimite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STRAVIMITE = registerKey("stravimite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_STRAVIMITE = registerKey("mega_stravimite_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_WATER_LAKE = registerKey("nether_water_lake");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_OAK = registerKey("ashen_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_OAK_FANCY = registerKey("ashen_oak_fancy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_OAK_BEES = registerKey("ashen_oak_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_OAK_FANCY_BEES = registerKey("ashen_oak_fancy_bees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_BIRCH = registerKey("ashen_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_BIRCH_BEES = registerKey("ashen_birch_bees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_TREES = registerKey("ashen_trees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE = registerKey("maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FANCY = registerKey("maple_fancy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_BEES = registerKey("maple_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FANCY_BEES = registerKey("maple_fancy_bees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBERWOOD = registerKey("rubberwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBERWOOD_HUGE = registerKey("rubberwood_huge");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREES_01 = registerKey("maple_trees_01");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBER_TREES_01 = registerKey("rubber_trees_01");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GROUND_STONES = registerKey("ground_stones");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context)
    {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        blocks = context.lookup(Registries.BLOCK);

        register(context, COAL_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.COAL_ORE_TARGETS.get(), 17));
        register(context, COAL_ORE_BURIED, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.COAL_ORE_TARGETS.get(), 17, 0.5F));

        register(context, IRON_ORE_GENERIC, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.IRON_ORE_TARGETS.get(), 9));
        register(context, IRON_ORE_SMALL, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.IRON_ORE_TARGETS.get(), 4));

        register(context, COPPER_ORE_SMALL, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.COPPER_ORE_TARGETS.get(), 10));
        register(context, COPPER_ORE_LARGE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.COPPER_ORE_TARGETS.get(), 20));

        register(context, DIAMOND_ORE_BURIED, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.DIAMOND_ORE_TARGETS.get(), 8, 1.0F));
        register(context, DIAMOND_ORE_LARGE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.DIAMOND_ORE_TARGETS.get(), 12, 0.7F));
        register(context, DIAMOND_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.DIAMOND_ORE_TARGETS.get(), 8, 0.5F));
        register(context, DIAMOND_ORE_SMALL, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.DIAMOND_ORE_TARGETS.get(), 4, 0.4F));

        register(context, EMERALD_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.EMERALD_ORE_TARGETS.get(), 3));

        register(context, GOLD_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.GOLD_ORE_TARGETS.get(), 9));
        register(context, GOLD_ORE_BURIED, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.GOLD_ORE_TARGETS.get(), 9, 0.5F));
        register(context, NETHER_GOLD_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.GOLD_ORE_TARGETS.get(), 10));

        register(context, LAPIS_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.LAPIS_ORE_TARGETS.get(), 7));
        register(context, LAPIS_ORE_BURIED, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.LAPIS_ORE_TARGETS.get(), 7, 1.0F));

        register(context, QUARTZ_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.QUARTZ_ORE_TARGETS.get(), 14));

        register(context, REDSTONE_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.REDSTONE_ORE_TARGETS.get(), 8));

        register(context, LEAD_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.LEAD_ORE_TARGETS.get(), 9));
        register(context, LEAD_ORE_SMALL, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.LEAD_ORE_TARGETS.get(), 4));

        register(context, RUBY_ORE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.RUBY_ORE_TARGETS.get(), 3));

        register(context, OVERWORLD_METHANE_ICE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.METHANE_ICE_ORE_TARGETS.get(), 8));
        register(context, NETHER_METHANE_ICE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.METHANE_ICE_ORE_TARGETS.get(), 8));
        register(context, MEGA_NETHER_METHANE_ICE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.METHANE_ICE_ORE_TARGETS.get(), 18, 0.9F));

        register(context, TRACE_CRIMSONITE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.CRIMSONITE_ORE_TARGETS.get(), 4, 0.1F));
        register(context, CRIMSONITE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.CRIMSONITE_ORE_TARGETS.get(), 12));
        register(context, MEGA_CRIMSONITE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.CRIMSONITE_ORE_TARGETS.get(), 28, 0.7F));

        register(context, TRACE_STRAVIMITE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.STRAVIMITE_ORE_TARGETS.get(), 4, 0.1F));
        register(context, STRAVIMITE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.STRAVIMITE_ORE_TARGETS.get(), 12));
        register(context, MEGA_STRAVIMITE, Feature.ORE,
                new OreConfiguration(TargetRuleData.OreTargets.STRAVIMITE_ORE_TARGETS.get(), 28, 0.7F));

        register(context, NETHER_WATER_LAKE, Feature.LAKE,
                new LakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                        BlockStateProvider.simple(Blocks.CALCITE.defaultBlockState())));

        register(context, ASHEN_OAK, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLog().get()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get()))
                .ignoreVines().build());
        register(context, ASHEN_OAK_FANCY, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLog().get()),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLeaves().get()),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get()))
                .ignoreVines().build());
        register(context, ASHEN_OAK_BEES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLog().get()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get()))
                .decorators(List.of(new BeehiveDecorator(0.02F)))
                .ignoreVines().build());
        register(context, ASHEN_OAK_FANCY_BEES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLog().get()),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(Registrar.ASHEN_OAK_WOOD.getBaseLeaves().get()),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get()))
                .decorators(List.of(new BeehiveDecorator(0.05F)))
                .ignoreVines().build());

        register(context, ASHEN_BIRCH, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.ASHEN_BIRCH_WOOD.getBaseLog().get()),
                        new StraightTrunkPlacer(5, 2, 0),
                        BlockStateProvider.simple(Registrar.ASHEN_BIRCH_WOOD.getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get()))
                .ignoreVines().build());
        register(context, ASHEN_BIRCH_BEES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.ASHEN_BIRCH_WOOD.getBaseLog().get()),
                        new StraightTrunkPlacer(5, 2, 0),
                        BlockStateProvider.simple(Registrar.ASHEN_BIRCH_WOOD.getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get()))
                .decorators(List.of(new BeehiveDecorator(0.02F)))
                .ignoreVines().build());

        register(context, ASHEN_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(PlacedFeaturesData.ASHEN_OAK_FANCY_BEES), 0.1F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(PlacedFeaturesData.ASHEN_BIRCH_BEES), 0.2F)),
                        placedFeatures.getOrThrow(PlacedFeaturesData.ASHEN_OAK_BEES)));
        
        register(context, MAPLE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLog().get()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .ignoreVines().build());
        register(context, MAPLE_FANCY, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLog().get()),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLeaves().get()),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .ignoreVines().build());
        register(context, MAPLE_BEES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLog().get()),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .decorators(List.of(new BeehiveDecorator(0.02F)))
                .ignoreVines().build());
        register(context, MAPLE_FANCY_BEES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLog().get()),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(Registrar.MAPLE_WOOD.wood().getBaseLeaves().get()),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .decorators(List.of(new BeehiveDecorator(0.05F)))
                .ignoreVines().build());

        register(context, MAPLE_TREES_01, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(PlacedFeaturesData.MAPLE_FANCY_BEES), 0.05F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(PlacedFeaturesData.MAPLE_BEES), 0.1F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_002), 0.1F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F)),
                        placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002)));

        register(context, RUBBERWOOD, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.RUBBER_WOOD.wood().getBaseLog().get()),
                        new StraightTrunkPlacer(4, 8, 0),
                        BlockStateProvider.simple(Registrar.RUBBER_WOOD.wood().getBaseLeaves().get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .decorators(List.of(new TrunkVineDecorator(), new LeaveVineDecorator(0.25F)))
                .ignoreVines().build());
        register(context, RUBBERWOOD_HUGE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Registrar.RUBBER_WOOD.wood().getBaseLog().get()),
                        new MegaJungleTrunkPlacer(10, 2, 19),
                        BlockStateProvider.simple(Registrar.RUBBER_WOOD.wood().getBaseLeaves().get()),
                        new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                        new TwoLayersFeatureSize(1, 1, 2))
                .dirt(BlockStateProvider.simple(Blocks.DIRT))
                .decorators(List.of(new TrunkVineDecorator(), new LeaveVineDecorator(0.25F)))
                .build());

        register(context, RUBBER_TREES_01, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_CHECKED), 0.1F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(PlacedFeaturesData.RUBBERWOOD_HUGE), 0.025F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(PlacedFeaturesData.RUBBERWOOD), 0.125F),
                        new WeightedPlacedFeature(
                                placedFeatures.getOrThrow(TreePlacements.MEGA_JUNGLE_TREE_CHECKED), (float) 1 / 3)),
                        placedFeatures.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED)));

        register(context, GROUND_STONES, Registrar.GROUND_STONE_FEATURE.get(), new NoneFeatureConfiguration());
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name)
    { return ResourceKey.create(Registries.CONFIGURED_FEATURE, RLUtility.makeRL(SpiroMod.MOD_ID, name)); }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void
    register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<? ,?>> key, F feature, FC configuration)
    { context.register(key, new ConfiguredFeature<>(feature, configuration)); }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value)
    {
        Registrar.FEATURES.register(key, () -> value);
        return value;
    }

    public static void register() {}
}
