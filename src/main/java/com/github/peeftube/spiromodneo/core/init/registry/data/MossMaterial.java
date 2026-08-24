package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.datagen.modules.world.util.ConfigFeaturesData;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum MossMaterial
{
    MOSS(getMOID(MOID.MOSS), CaveFeatures.MOSS_PATCH_BONEMEAL),
    AZURE_GLOWMOSS(getMOID(MOID.AZURE_GLOWMOSS), ConfigFeaturesData.AZURE_GLOW_MOSS_PATCH_BONEMEAL),
    RUBY_GLOWMOSS(getMOID(MOID.RUBY_GLOWMOSS), ConfigFeaturesData.RUBY_GLOW_MOSS_PATCH_BONEMEAL),
    VERDANT_GLOWMOSS(getMOID(MOID.VERDANT_GLOWMOSS), ConfigFeaturesData.VERDANT_GLOW_MOSS_PATCH_BONEMEAL),
    GILDED_GLOWMOSS(getMOID(MOID.GILDED_GLOWMOSS), ConfigFeaturesData.GILDED_GLOW_MOSS_PATCH_BONEMEAL),
    AMETHYST_GLOWMOSS(getMOID(MOID.AMETHYST_GLOWMOSS), ConfigFeaturesData.AMETHYST_GLOW_MOSS_PATCH_BONEMEAL);

    private final String name;
    private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;

    MossMaterial(String name, ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature)
    { this.name = name; this.bonemealFeature = bonemealFeature; }

    public String getName() { return name; }
    public ResourceKey<ConfiguredFeature<?, ?>> getFeatureForBonemeal() { return bonemealFeature; }
}
