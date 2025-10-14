package com.github.peeftube.spiromodneo.core.init.content.blocks;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class GroundStoneBlock extends HorizontalDirectionalBlock
{
    public static final MapCodec<GroundStoneBlock> CODEC = simpleCodec(GroundStoneBlock::new);

    // There's PROBABLY a better way of doing this but I cbb with it as of writing this comment
    public static final IntegerProperty VARIANT = IntegerProperty.create("model_variation", 0, 3);

    public GroundStoneBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(VARIANT, 0));
    }

    @Override
    protected MapCodec<GroundStoneBlock> codec() { return CODEC; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    { builder.add(FACING, VARIANT); }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        for (Direction direction : context.getNearestLookingDirections())
        {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y)
            { blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()); }
            else { blockstate = this.defaultBlockState().setValue(FACING, direction); }

            blockstate = blockstate.setValue(VARIANT, SpiroMod.RNG.nextInt(4));

            if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
            { return blockstate; }
        }

        return null;
    }
}
