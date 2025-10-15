package com.github.peeftube.spiromodneo.datagen.modules.world.util;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.RLUtility;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeaturesData
{
    public static final ResourceKey<PlacedFeature> COAL_ORE_UPPER = registerKey("coal_ore_upper");
    public static final ResourceKey<PlacedFeature> COAL_ORE_LOWER = registerKey("coal_ore_lower");

    public static final ResourceKey<PlacedFeature> IRON_ORE_UPPER = registerKey("iron_ore_upper");
    public static final ResourceKey<PlacedFeature> IRON_ORE_MID = registerKey("iron_ore_mid");
    public static final ResourceKey<PlacedFeature> IRON_ORE_SMALL = registerKey("iron_ore_small");
    public static final ResourceKey<PlacedFeature> IRON_ORE_NETHER = registerKey("iron_ore_nether");
    public static final ResourceKey<PlacedFeature> IRON_ORE_NETHER_LARGE = registerKey("iron_ore_nether_large");

    public static final ResourceKey<PlacedFeature> COPPER_ORE = registerKey("copper_ore");
    public static final ResourceKey<PlacedFeature> COPPER_ORE_LARGE = registerKey("copper_ore_large");
    public static final ResourceKey<PlacedFeature> COPPER_ORE_NETHER = registerKey("copper_ore_nether");

    public static final ResourceKey<PlacedFeature> DIAMOND_ORE_SMALL = registerKey("diamond_ore_small");
    public static final ResourceKey<PlacedFeature> DIAMOND_ORE = registerKey("diamond_ore");
    public static final ResourceKey<PlacedFeature> DIAMOND_ORE_LARGE = registerKey("diamond_ore_large");
    public static final ResourceKey<PlacedFeature> DIAMOND_ORE_BURIED = registerKey("diamond_ore_buried");

    public static final ResourceKey<PlacedFeature> EMERALD_ORE = registerKey("emerald_ore");

    public static final ResourceKey<PlacedFeature> GOLD_ORE = registerKey("gold_ore");
    public static final ResourceKey<PlacedFeature> GOLD_ORE_EXTRA = registerKey("gold_ore_extra");
    public static final ResourceKey<PlacedFeature> GOLD_ORE_LOWER = registerKey("gold_ore_lower");
    public static final ResourceKey<PlacedFeature> GOLD_ORE_NETHER = registerKey("gold_ore_nether");
    public static final ResourceKey<PlacedFeature> GOLD_ORE_DELTAS = registerKey("gold_ore_deltas");

    public static final ResourceKey<PlacedFeature> LAPIS_ORE = registerKey("lapis_ore");
    public static final ResourceKey<PlacedFeature> LAPIS_ORE_BURIED = registerKey("lapis_ore_buried");

    public static final ResourceKey<PlacedFeature> REDSTONE_ORE = registerKey("redstone_ore");
    public static final ResourceKey<PlacedFeature> REDSTONE_ORE_LOWER = registerKey("redstone_ore_lower");

    public static final ResourceKey<PlacedFeature> QUARTZ_ORE_NETHER = registerKey("quartz_ore_nether");
    public static final ResourceKey<PlacedFeature> QUARTZ_ORE_DELTAS = registerKey("quartz_ore_deltas");
    public static final ResourceKey<PlacedFeature> QUARTZ_ORE_OVERWORLD = registerKey("quartz_ore_overworld");

    public static final ResourceKey<PlacedFeature> LEAD_ORE_UPPER = registerKey("lead_ore_upper");
    public static final ResourceKey<PlacedFeature> LEAD_ORE_MID = registerKey("lead_ore_mid");
    public static final ResourceKey<PlacedFeature> LEAD_ORE_SMALL = registerKey("lead_ore_small");
    public static final ResourceKey<PlacedFeature> LEAD_ORE_NETHER = registerKey("lead_ore_nether");
    public static final ResourceKey<PlacedFeature> LEAD_ORE_NETHER_LARGE = registerKey("lead_ore_nether_large");

    public static final ResourceKey<PlacedFeature> RUBY_ORE = registerKey("ruby_ore");

    public static final ResourceKey<PlacedFeature> METHANE_ICE_ORE_OVERWORLD = registerKey("methane_ore_overworld");
    public static final ResourceKey<PlacedFeature> METHANE_ICE_ORE_NETHER = registerKey("methane_ore_nether");
    public static final ResourceKey<PlacedFeature> METHANE_ICE_ORE_MEGA_NETHER = registerKey("methane_ore_mega_nether");

    public static final ResourceKey<PlacedFeature> TRACE_CRIMSONITE = registerKey("trace_crimsonite_ore");
    public static final ResourceKey<PlacedFeature> TRACE_OVERWORLD_CRIMSONITE = registerKey("deep_crimsonite_ore");
    public static final ResourceKey<PlacedFeature> CRIMSONITE = registerKey("crimsonite_ore");
    public static final ResourceKey<PlacedFeature> MEGA_CRIMSONITE = registerKey("mega_crimsonite_ore");

    public static final ResourceKey<PlacedFeature> TRACE_STRAVIMITE = registerKey("trace_stravimite_ore");
    public static final ResourceKey<PlacedFeature> TRACE_OVERWORLD_STRAVIMITE = registerKey("deep_stravimite_ore");
    public static final ResourceKey<PlacedFeature> STRAVIMITE = registerKey("stravimite_ore");
    public static final ResourceKey<PlacedFeature> MEGA_STRAVIMITE = registerKey("mega_stravimite_ore");

    public static final ResourceKey<PlacedFeature> NETHER_WATER_LAKE      = registerKey("nether_water_lake");
    public static final ResourceKey<PlacedFeature> ASHEN_TREES            = registerKey("ashen_trees");

    public static final ResourceKey<PlacedFeature> NETHER_OVERWORLD_GRASS = registerKey("limbo_garden_grass");
    public static final ResourceKey<PlacedFeature> NETHER_OVERWORLD_FLOWERS = registerKey("limbo_garden_flowers");

    public static final ResourceKey<PlacedFeature> ASHEN_OAK = registerKey("ashen_oak");
    public static final ResourceKey<PlacedFeature> ASHEN_OAK_FANCY = registerKey("ashen_oak_fancy");
    public static final ResourceKey<PlacedFeature> ASHEN_OAK_BEES = registerKey("ashen_oak_bees");
    public static final ResourceKey<PlacedFeature> ASHEN_OAK_FANCY_BEES = registerKey("ashen_oak_fancy_bees");

    public static final ResourceKey<PlacedFeature> ASHEN_BIRCH = registerKey("ashen_birch");
    public static final ResourceKey<PlacedFeature> ASHEN_BIRCH_BEES = registerKey("ashen_birch_bees");

    public static final ResourceKey<PlacedFeature> MAPLE = registerKey("maple");
    public static final ResourceKey<PlacedFeature> MAPLE_FANCY = registerKey("maple_fancy");
    public static final ResourceKey<PlacedFeature> MAPLE_BEES = registerKey("maple_bees");
    public static final ResourceKey<PlacedFeature> MAPLE_FANCY_BEES = registerKey("maple_fancy_bees");

    public static final ResourceKey<PlacedFeature> RUBBERWOOD = registerKey("rubberwood");
    public static final ResourceKey<PlacedFeature> RUBBERWOOD_HUGE = registerKey("rubberwood_huge");

    public static final ResourceKey<PlacedFeature> MAPLE_TREES_01 = registerKey("maple_trees_01");

    public static final ResourceKey<PlacedFeature> RUBBER_TREES_01 = registerKey("rubber_trees_01");
    public static final ResourceKey<PlacedFeature> RUBBER_TREES_02 = registerKey("rubber_trees_02");

    public static final ResourceKey<PlacedFeature> GROUND_STONES = registerKey("ground_stones");

    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, COAL_ORE_UPPER, configuredFeatures.getOrThrow(ConfigFeaturesData.COAL_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(136), VerticalAnchor.belowTop(0))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(30)
        ));
        register(context, COAL_ORE_LOWER, configuredFeatures.getOrThrow(ConfigFeaturesData.COAL_ORE_BURIED), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(192))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(20)
        ));

        register(context, IRON_ORE_UPPER, configuredFeatures.getOrThrow(ConfigFeaturesData.IRON_ORE_GENERIC), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(90)
        ));
        register(context, IRON_ORE_MID, configuredFeatures.getOrThrow(ConfigFeaturesData.IRON_ORE_GENERIC), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, IRON_ORE_SMALL, configuredFeatures.getOrThrow(ConfigFeaturesData.IRON_ORE_SMALL), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(72))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, IRON_ORE_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.IRON_ORE_SMALL), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, IRON_ORE_NETHER_LARGE, configuredFeatures.getOrThrow(ConfigFeaturesData.IRON_ORE_GENERIC), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));

        register(context, COPPER_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.COPPER_ORE_SMALL), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(16)
        ));
        register(context, COPPER_ORE_LARGE, configuredFeatures.getOrThrow(ConfigFeaturesData.COPPER_ORE_LARGE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(16)
        ));
        register(context, COPPER_ORE_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.COPPER_ORE_SMALL), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));

        register(context, DIAMOND_ORE_SMALL, configuredFeatures.getOrThrow(ConfigFeaturesData.DIAMOND_ORE_SMALL), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(7)
        ));
        register(context, DIAMOND_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.DIAMOND_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(2)
        ));
        register(context, DIAMOND_ORE_LARGE, configuredFeatures.getOrThrow(ConfigFeaturesData.DIAMOND_ORE_LARGE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(9)
        ));
        register(context, DIAMOND_ORE_BURIED, configuredFeatures.getOrThrow(ConfigFeaturesData.DIAMOND_ORE_BURIED), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));

        register(context, EMERALD_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.EMERALD_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(256))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(100)
        ));

        register(context, GOLD_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.GOLD_ORE_BURIED), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));
        register(context, GOLD_ORE_EXTRA, configuredFeatures.getOrThrow(ConfigFeaturesData.GOLD_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(50)
        ));
        register(context, GOLD_ORE_LOWER, configuredFeatures.getOrThrow(ConfigFeaturesData.GOLD_ORE_BURIED), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(UniformInt.of(0, 1))
        ));
        register(context, GOLD_ORE_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.NETHER_GOLD_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, GOLD_ORE_DELTAS, configuredFeatures.getOrThrow(ConfigFeaturesData.NETHER_GOLD_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(20)
        ));

        register(context, LAPIS_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.LAPIS_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(2)
        ));
        register(context, LAPIS_ORE_BURIED, configuredFeatures.getOrThrow(ConfigFeaturesData.LAPIS_ORE_BURIED), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(64))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));

        register(context, REDSTONE_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.REDSTONE_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(15))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));
        register(context, REDSTONE_ORE_LOWER, configuredFeatures.getOrThrow(ConfigFeaturesData.REDSTONE_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(8)
        ));

        register(context, QUARTZ_ORE_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.QUARTZ_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(16)
        ));
        register(context, QUARTZ_ORE_DELTAS, configuredFeatures.getOrThrow(ConfigFeaturesData.QUARTZ_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(32)
        ));
        register(context, QUARTZ_ORE_OVERWORLD, configuredFeatures.getOrThrow(ConfigFeaturesData.QUARTZ_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(-16), VerticalAnchor.belowTop(256))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(2)
        ));

        register(context, LEAD_ORE_UPPER, configuredFeatures.getOrThrow(ConfigFeaturesData.LEAD_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(90)
        ));
        register(context, LEAD_ORE_MID, configuredFeatures.getOrThrow(ConfigFeaturesData.LEAD_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, LEAD_ORE_SMALL, configuredFeatures.getOrThrow(ConfigFeaturesData.LEAD_ORE_SMALL), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(72))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, LEAD_ORE_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.LEAD_ORE_SMALL), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, LEAD_ORE_NETHER_LARGE, configuredFeatures.getOrThrow(ConfigFeaturesData.LEAD_ORE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));

        register(context, RUBY_ORE, configuredFeatures.getOrThrow(ConfigFeaturesData.RUBY_ORE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(256))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(100)
        ));

        register(context, METHANE_ICE_ORE_OVERWORLD, configuredFeatures.getOrThrow(ConfigFeaturesData.OVERWORLD_METHANE_ICE), List.of(
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(-256), VerticalAnchor.absolute(-80))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(20)
        ));
        register(context, METHANE_ICE_ORE_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.NETHER_METHANE_ICE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(30)
        ));
        register(context, METHANE_ICE_ORE_MEGA_NETHER, configuredFeatures.getOrThrow(ConfigFeaturesData.MEGA_NETHER_METHANE_ICE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));

        register(context, TRACE_CRIMSONITE, configuredFeatures.getOrThrow(ConfigFeaturesData.TRACE_CRIMSONITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(60)
        ));
        register(context, TRACE_OVERWORLD_CRIMSONITE, configuredFeatures.getOrThrow(ConfigFeaturesData.TRACE_CRIMSONITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(-160))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, CRIMSONITE, configuredFeatures.getOrThrow(ConfigFeaturesData.CRIMSONITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(30)
        ));
        register(context, MEGA_CRIMSONITE, configuredFeatures.getOrThrow(ConfigFeaturesData.MEGA_CRIMSONITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));

        register(context, TRACE_STRAVIMITE, configuredFeatures.getOrThrow(ConfigFeaturesData.TRACE_STRAVIMITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(60)
        ));
        register(context, TRACE_OVERWORLD_STRAVIMITE, configuredFeatures.getOrThrow(ConfigFeaturesData.TRACE_STRAVIMITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(-160))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(10)
        ));
        register(context, STRAVIMITE, configuredFeatures.getOrThrow(ConfigFeaturesData.STRAVIMITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(30)
        ));
        register(context, MEGA_STRAVIMITE, configuredFeatures.getOrThrow(ConfigFeaturesData.MEGA_STRAVIMITE), List.of(
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(5), VerticalAnchor.belowTop(5))),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                CountPlacement.of(4)
        ));

        register(context, NETHER_WATER_LAKE,
                configuredFeatures.getOrThrow(ConfigFeaturesData.NETHER_WATER_LAKE),
                List.of(BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(12),
                        CountOnEveryLayerPlacement.of(UniformInt.of(0, 2))));

        register(context, ASHEN_OAK,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_OAK),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.ASHEN_OAK_WOOD.getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, ASHEN_OAK_FANCY,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_OAK_FANCY),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.ASHEN_OAK_WOOD.getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, ASHEN_OAK_BEES,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_OAK_BEES),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.ASHEN_OAK_WOOD.getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, ASHEN_OAK_FANCY_BEES,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_OAK_FANCY_BEES),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.ASHEN_OAK_WOOD.getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));

        register(context, ASHEN_BIRCH,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_BIRCH),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.ASHEN_BIRCH_WOOD.getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, ASHEN_BIRCH_BEES,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_BIRCH_BEES),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.ASHEN_BIRCH_WOOD.getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));

        register(context, ASHEN_TREES,
                configuredFeatures.getOrThrow(ConfigFeaturesData.ASHEN_TREES),
                List.of(BiomeFilter.biome(),
                        CountOnEveryLayerPlacement.of(8)));

        register(context, NETHER_OVERWORLD_GRASS,
                configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS),
                List.of(BiomeFilter.biome(),
                        CountOnEveryLayerPlacement.of(16)));
        register(context, NETHER_OVERWORLD_FLOWERS,
                configuredFeatures.getOrThrow(VegetationFeatures.FLOWER_PLAIN),
                List.of(BiomeFilter.biome(),
                        CountOnEveryLayerPlacement.of(2)));
        
        register(context, MAPLE,
                configuredFeatures.getOrThrow(ConfigFeaturesData.MAPLE),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.MAPLE_WOOD.wood().getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, MAPLE_FANCY,
                configuredFeatures.getOrThrow(ConfigFeaturesData.MAPLE_FANCY),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.MAPLE_WOOD.wood().getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, MAPLE_BEES,
                configuredFeatures.getOrThrow(ConfigFeaturesData.MAPLE_BEES),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.MAPLE_WOOD.wood().getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, MAPLE_FANCY_BEES,
                configuredFeatures.getOrThrow(ConfigFeaturesData.MAPLE_FANCY_BEES),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.MAPLE_WOOD.wood().getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));

        register(context, MAPLE_TREES_01,
                configuredFeatures.getOrThrow(ConfigFeaturesData.MAPLE_TREES_01),
                List.of(
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        PlacementUtils.countExtra(10, 0.1F, 1)
                ));

        register(context, RUBBERWOOD,
                configuredFeatures.getOrThrow(ConfigFeaturesData.RUBBERWOOD),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.RUBBER_WOOD.wood().getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));
        register(context, RUBBERWOOD_HUGE,
                configuredFeatures.getOrThrow(ConfigFeaturesData.RUBBERWOOD_HUGE),
                List.of(BlockPredicateFilter.forPredicate(
                        BlockPredicate.wouldSurvive(Registrar.RUBBER_WOOD.wood().getBaseSapling().get().defaultBlockState(),
                                Vec3i.ZERO))));

        register(context, RUBBER_TREES_01,
                configuredFeatures.getOrThrow(ConfigFeaturesData.RUBBER_TREES_01),
                List.of(
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        PlacementUtils.countExtra(50, 0.1F, 10)
                ));
        register(context, RUBBER_TREES_02,
                configuredFeatures.getOrThrow(ConfigFeaturesData.RUBBER_TREES_02),
                List.of(
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(50), 5)
                                .add(ConstantInt.of(70), 2)
                                .add(ConstantInt.of(90), 1)
                                .add(ConstantInt.of(110), 1)
                                .add(ConstantInt.of(130), 1)
                                .build()))
                ));

        register(context, GROUND_STONES, configuredFeatures.getOrThrow(ConfigFeaturesData.GROUND_STONES));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name)
    { return ResourceKey.create(Registries.PLACED_FEATURE, RLUtility.makeRL(SpiroMod.MOD_ID, name)); }

    private static void register(BootstrapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers)
    { context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers))); }

    private static void register(BootstrapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers)
    { register(context, key, configuration, List.of(modifiers)); }
}
