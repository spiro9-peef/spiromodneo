package com.github.peeftube.spiromodneo.core.init.content.blocks;

import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class StonePlantableSaplingBlock extends SaplingBlock
{
    public StonePlantableSaplingBlock(TreeGrower grower, Properties properties)
    { super(grower, properties); }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    { return super.mayPlaceOn(state, level, pos) || state.is(SpiroTags.Blocks.SUPPORTS_STONE_PLANTABLE_SAPLINGS); }
}
