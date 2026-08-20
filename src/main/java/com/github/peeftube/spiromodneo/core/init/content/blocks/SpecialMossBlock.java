package com.github.peeftube.spiromodneo.core.init.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SpecialMossBlock extends MossBlock
{
    private final ResourceKey<ConfiguredFeature<?, ?>> featureKey;

    public SpecialMossBlock(Properties properties, ResourceKey<ConfiguredFeature<?, ?>> thisFeature)
    { super(properties); this.featureKey = thisFeature; }

    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
                .flatMap((reg) ->
                        reg.getHolder(this.featureKey))
                .ifPresent((ref) ->
                        ((ConfiguredFeature)ref.value())
                                .place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }
}
