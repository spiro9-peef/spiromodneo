package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.customfeature.config;

import com.github.peeftube.spiromodneo.util.MathUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;

public class PulledSpikesFeatureConfiguration implements FeatureConfiguration
{
    public static final Codec<PulledSpikesFeatureConfiguration> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
                    RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks_for_base")
                          .forGetter(config -> config.blocksForBase),
                    BuiltInRegistries.BLOCK
                        .byNameCodec()
                        .fieldOf("block")
                        .forGetter(config -> config.blockForSpike),
                    RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("safe_spawn_biomes")
                          .forGetter(config -> config.safeSpawnBiomes),
                    IntProvider.codec(1, 60).fieldOf("height_range")
                           .forGetter(config -> config.heightRange)
                )
                .apply(instance, PulledSpikesFeatureConfiguration::new)
    );

    public final HolderSet<Block> blocksForBase;
    public final Block            blockForSpike;
    public final HolderSet<Biome> safeSpawnBiomes;
    public final IntProvider heightRange;

    public PulledSpikesFeatureConfiguration(HolderSet<Block> blocksForBase, Block blockForSpike,
            HolderSet<Biome> safeSpawnBiomes, IntProvider heightRange)
    {
        this.blocksForBase = blocksForBase;
        this.blockForSpike = blockForSpike;
        this.safeSpawnBiomes = safeSpawnBiomes;
        this.heightRange = heightRange;
    }
}