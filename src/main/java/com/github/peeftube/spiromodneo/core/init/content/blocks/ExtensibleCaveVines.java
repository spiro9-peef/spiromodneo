package com.github.peeftube.spiromodneo.core.init.content.blocks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.ToIntFunction;

public interface ExtensibleCaveVines
{
    static ToIntFunction<BlockState> emission(int berries, int without)
    { return (s) -> (Boolean)s.getValue(BlockStateProperties.BERRIES) ? berries : without; }
}
