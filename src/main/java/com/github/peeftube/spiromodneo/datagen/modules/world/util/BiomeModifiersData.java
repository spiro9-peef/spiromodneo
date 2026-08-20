package com.github.peeftube.spiromodneo.datagen.modules.world.util;

import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.Holder.Reference;
import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.util.RLUtility;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BiomeModifiersData
{
    public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_OVERWORLD_ORES = key("remove_overworld_ores");
    public static final ResourceKey<BiomeModifier> REMOVE_VANILLA_NETHER_ORES = key("remove_nether_ores");

    public static final ResourceKey<BiomeModifier> NEW_OVERWORLD_ORES = key("new_overworld_ores");
    public static final ResourceKey<BiomeModifier> NEW_NETHER_ORES = key("new_nether_ores");
    public static final ResourceKey<BiomeModifier> NEW_EMERALD_ORE = key("new_emerald_ore");
    public static final ResourceKey<BiomeModifier> NEW_RUBY_ORE = key("new_ruby_ore");

    public static final ResourceKey<BiomeModifier> COLD_NETHER_WATER = key("cold_nether_water_features");
    public static final ResourceKey<BiomeModifier> LIMBO_GARDEN_FOLIAGE = key("limbo_garden_foliage");

    public static final ResourceKey<BiomeModifier> STRAVIMITE_IN_CRIMSON = key("stravimite_crimson_placements");
    public static final ResourceKey<BiomeModifier> CRIMSONITE_IN_WARPED = key("crimsonite_warped_placements");

    public static final ResourceKey<BiomeModifier> MAPLE_IN_FOREST = key("maple_trees_in_vanilla_forests");

    public static final ResourceKey<BiomeModifier> RUBBER_IN_JUNGLE = key("rubber_trees_in_vanilla_jungles");

    public static final ResourceKey<BiomeModifier> RUBBER_FOREST_FOLIAGE = key("rubber_forest_foliage");

    public static final ResourceKey<BiomeModifier> OVERWORLD_GROUNDSTONES = key("ground_stones_in_overworld");
    public static final ResourceKey<BiomeModifier> NETHER_GROUNDSTONES = key("ground_stones_in_nether");

    public static final ResourceKey<BiomeModifier> AZURE_CAVERNS_FOLIAGE = key("azure_caverns_foliage");
    public static final ResourceKey<BiomeModifier> RUBY_CAVERNS_FOLIAGE = key("ruby_caverns_foliage");
    public static final ResourceKey<BiomeModifier> VERDANT_CAVERNS_FOLIAGE = key("verdant_caverns_foliage");
    public static final ResourceKey<BiomeModifier> GILDED_CAVERNS_FOLIAGE = key("gilded_caverns_foliage");
    public static final ResourceKey<BiomeModifier> AMETHYST_CAVERNS_FOLIAGE = key("amethyst_caverns_foliage");

    public static void bootstrap(final BootstrapContext<BiomeModifier> context)
    {
        final var biomes   = context.lookup(Registries.BIOME);
        final var features = context.lookup(Registries.PLACED_FEATURE);

        context.register(REMOVE_VANILLA_OVERWORLD_ORES, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_OVERWORLD),
                features(features,
                        OrePlacements.ORE_IRON_MIDDLE, OrePlacements.ORE_IRON_UPPER, OrePlacements.ORE_IRON_SMALL,
                        OrePlacements.ORE_COAL_LOWER, OrePlacements.ORE_COAL_UPPER,
                        OrePlacements.ORE_COPPER, OrePlacements.ORE_COPPER_LARGE,
                        OrePlacements.ORE_DIAMOND, OrePlacements.ORE_DIAMOND_BURIED, OrePlacements.ORE_DIAMOND_LARGE,
                        OrePlacements.ORE_DIAMOND_MEDIUM,
                        OrePlacements.ORE_GOLD, OrePlacements.ORE_GOLD_EXTRA, OrePlacements.ORE_GOLD_LOWER,
                        OrePlacements.ORE_EMERALD, OrePlacements.ORE_LAPIS, OrePlacements.ORE_LAPIS_BURIED,
                        OrePlacements.ORE_REDSTONE, OrePlacements.ORE_REDSTONE_LOWER),
                Set.of(GenerationStep.Decoration.UNDERGROUND_ORES)
        ));

        context.register(REMOVE_VANILLA_NETHER_ORES, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_NETHER),
                features(features,
                        OrePlacements.ORE_GOLD_NETHER, OrePlacements.ORE_QUARTZ_NETHER),
                Set.of(GenerationStep.Decoration.UNDERGROUND_ORES)
        ));

        context.register(NEW_OVERWORLD_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_OVERWORLD),
                features(features,
                        // SMPlacedFeatures.CALCITE_DEPOSIT, SMPlacedFeatures.SHALE_DEPOSIT,
                        PlacedFeaturesData.IRON_ORE_UPPER, PlacedFeaturesData.IRON_ORE_MID, PlacedFeaturesData.IRON_ORE_SMALL,
                        PlacedFeaturesData.COAL_ORE_LOWER, PlacedFeaturesData.COAL_ORE_UPPER,
                        PlacedFeaturesData.COPPER_ORE, PlacedFeaturesData.COPPER_ORE_LARGE,
                        PlacedFeaturesData.DIAMOND_ORE, PlacedFeaturesData.DIAMOND_ORE_BURIED, PlacedFeaturesData.DIAMOND_ORE_LARGE,
                        PlacedFeaturesData.DIAMOND_ORE_SMALL,
                        PlacedFeaturesData.GOLD_ORE, PlacedFeaturesData.GOLD_ORE_EXTRA, PlacedFeaturesData.GOLD_ORE_LOWER,
                        PlacedFeaturesData.LAPIS_ORE, PlacedFeaturesData.LAPIS_ORE_BURIED,
                        PlacedFeaturesData.REDSTONE_ORE, PlacedFeaturesData.REDSTONE_ORE_LOWER,
                        PlacedFeaturesData.LEAD_ORE_SMALL, PlacedFeaturesData.LEAD_ORE_MID, PlacedFeaturesData.LEAD_ORE_UPPER,
                        PlacedFeaturesData.METHANE_ICE_ORE_OVERWORLD, PlacedFeaturesData.TRACE_OVERWORLD_CRIMSONITE,
                        PlacedFeaturesData.TRACE_OVERWORLD_STRAVIMITE),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(NEW_NETHER_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_NETHER),
                features(features,
                        PlacedFeaturesData.GOLD_ORE_NETHER, PlacedFeaturesData.QUARTZ_ORE_NETHER,
                        PlacedFeaturesData.METHANE_ICE_ORE_NETHER, PlacedFeaturesData.METHANE_ICE_ORE_MEGA_NETHER,
                        PlacedFeaturesData.IRON_ORE_NETHER, PlacedFeaturesData.IRON_ORE_NETHER_LARGE,
                        PlacedFeaturesData.LEAD_ORE_NETHER, PlacedFeaturesData.LEAD_ORE_NETHER_LARGE,
                        PlacedFeaturesData.COPPER_ORE_NETHER, PlacedFeaturesData.TRACE_CRIMSONITE,
                        PlacedFeaturesData.TRACE_STRAVIMITE),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(NEW_EMERALD_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_MOUNTAIN),
                features(features,
                        PlacedFeaturesData.EMERALD_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(NEW_RUBY_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.RUBY_SPAWNABLE),
                features(features,
                        PlacedFeaturesData.RUBY_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(COLD_NETHER_WATER, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.TEMPERATE_OR_COLD_NETHER_BIOMES),
                features(features,
                        PlacedFeaturesData.NETHER_WATER_LAKE),
                GenerationStep.Decoration.LAKES
        ));

        context.register(LIMBO_GARDEN_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.LIMBO_GARDEN),
                features(features,
                        PlacedFeaturesData.ASHEN_TREES, PlacedFeaturesData.NETHER_OVERWORLD_GRASS,
                        PlacedFeaturesData.NETHER_OVERWORLD_FLOWERS),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(CRIMSONITE_IN_WARPED, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_WARPED_NETHER),
                features(features,
                        PlacedFeaturesData.CRIMSONITE, PlacedFeaturesData.MEGA_CRIMSONITE),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(STRAVIMITE_IN_CRIMSON, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_CRIMSON_NETHER),
                features(features,
                        PlacedFeaturesData.STRAVIMITE, PlacedFeaturesData.MEGA_STRAVIMITE),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(MAPLE_IN_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_VANILLA_AND_CAN_HAVE_MAPLE),
                features(features,
                        PlacedFeaturesData.MAPLE_TREES_01),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(RUBBER_IN_JUNGLE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_VANILLA_AND_CAN_HAVE_RUBBER_01),
                features(features,
                        PlacedFeaturesData.RUBBER_TREES_01),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(RUBBER_FOREST_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_MINE_AND_CAN_HAVE_RUBBER),
                features(features,
                        PlacedFeaturesData.RUBBER_TREES_02, PlacedFeaturesData.RUBBER_TREES_01),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(OVERWORLD_GROUNDSTONES, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_OVERWORLD),
                features(features, PlacedFeaturesData.GROUND_STONES),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION
        ));

        context.register(NETHER_GROUNDSTONES, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_NETHER),
                features(features, PlacedFeaturesData.GROUND_STONES),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION
        ));

        context.register(AZURE_CAVERNS_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_AZURE_CAVE_BIOME),
                features(features,
                        PlacedFeaturesData.AZURE_CAVERN_VEGETATION, PlacedFeaturesData.AZURE_CAVE_TREES),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(RUBY_CAVERNS_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_RUBY_CAVE_BIOME),
                features(features,
                        PlacedFeaturesData.RUBY_CAVERN_VEGETATION, PlacedFeaturesData.RUBY_CAVE_TREES),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(VERDANT_CAVERNS_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_VERDANT_CAVE_BIOME),
                features(features,
                        PlacedFeaturesData.VERDANT_CAVERN_VEGETATION, PlacedFeaturesData.VERDANT_CAVE_TREES),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(GILDED_CAVERNS_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_GILDED_CAVE_BIOME),
                features(features,
                        PlacedFeaturesData.GILDED_CAVERN_VEGETATION, PlacedFeaturesData.GILDED_CAVE_TREES),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(AMETHYST_CAVERNS_FOLIAGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, SpiroTags.Biomes.IS_AMETHYST_CAVE_BIOME),
                features(features,
                        PlacedFeaturesData.AMETHYST_CAVERN_VEGETATION, PlacedFeaturesData.AMETHYST_CAVE_TREES),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> key(final String name)
    { return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, RLUtility.makeRL(SpiroMod.MOD_ID, name)); }

    private static HolderSet<Biome> tag(final HolderGetter<Biome> holderGetter, final TagKey<Biome> key)
    { return holderGetter.getOrThrow(key); }

    private static HolderSet<PlacedFeature> feature(final HolderGetter<PlacedFeature> holderGetter, final ResourceKey<PlacedFeature> feature)
    { return HolderSet.direct(holderGetter.getOrThrow(feature)); }

    private static HolderSet<PlacedFeature> features(final HolderGetter<PlacedFeature> holderGetter, final ResourceKey<PlacedFeature> ... features)
    {
        List<Reference<PlacedFeature>> grabbed = new ArrayList<>();
        for (ResourceKey<PlacedFeature> feature : features)
        { grabbed.add(holderGetter.getOrThrow(feature)); }

        return HolderSet.direct(grabbed);
    }
}
