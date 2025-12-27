package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.datagen.modules.world.util.ConfigFeaturesData;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public enum MossMaterial
{
    MOSS("moss", CaveFeatures.MOSS_PATCH_BONEMEAL),
    AZURE_GLOWMOSS("azure_glow_moss", ConfigFeaturesData.AZURE_GLOW_MOSS_PATCH_BONEMEAL),
    RUBY_GLOWMOSS("ruby_glow_moss", ConfigFeaturesData.RUBY_GLOW_MOSS_PATCH_BONEMEAL),
    VERDANT_GLOWMOSS("verdant_glow_moss", ConfigFeaturesData.VERDANT_GLOW_MOSS_PATCH_BONEMEAL),
    GILDED_GLOWMOSS("gilded_glow_moss", ConfigFeaturesData.GILDED_GLOW_MOSS_PATCH_BONEMEAL),
    AMETHYST_GLOWMOSS("amethyst_glow_moss", ConfigFeaturesData.AMETHYST_GLOW_MOSS_PATCH_BONEMEAL);

    private final String name;
    private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;

    MossMaterial(String name, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature)
    { this.name = name; this.bonemealFeature = bonemealFeature; }

    public String getName() { return name; }
    public ResourceKey<ConfiguredFeature<?, ?>> getFeatureForBonemeal() { return bonemealFeature; }
}
