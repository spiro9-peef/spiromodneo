package com.github.peeftube.spiromodneo.core.init.content.blocks;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.Tappable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

// TODO: have this block tick, checking for any tappers attached!!!!
/** Tappable wood blocks are functionally identical to existing <code>RotatedPillarBlock</code>s,
 * except in that they are also able to be "tapped," enabling liquid resources to be gathered from
 * specific trees; for instance, uses might include caoutchouc from rubber trees, or maple sap from
 * maple trees.
 */
public class TappableWoodBlock extends WoodBlock
{
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty TAPPED = BooleanProperty.create("tapped");

    public final Tappable tapOutput;

    public TappableWoodBlock(Properties properties, Tappable tapOutput)
    {
        super(properties.randomTicks());
        this.tapOutput = tapOutput;
        this.registerDefaultState(this.defaultBlockState().setValue(TAPPED, Boolean.FALSE)
			.setValue(NORTH, Boolean.FALSE)
			.setValue(SOUTH, Boolean.FALSE)
			.setValue(WEST, Boolean.FALSE)
			.setValue(EAST, Boolean.FALSE));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        boolean isNorth = !level.getBlockState(pos.offset(Direction.NORTH.getNormal())).is(Blocks.AIR);
        boolean isSouth = !level.getBlockState(pos.offset(Direction.SOUTH.getNormal())).is(Blocks.AIR);
        boolean isEast = !level.getBlockState(pos.offset(Direction.EAST.getNormal())).is(Blocks.AIR);
        boolean isWest = !level.getBlockState(pos.offset(Direction.WEST.getNormal())).is(Blocks.AIR);

        if (random.nextInt(7) == 0 && state.is(Registrar.TAPPER))
        {
            if (state.getValue(NORTH) && isNorth)
            { level.setBlock(pos.offset(Direction.NORTH.getNormal()),
                    level.getBlockState(pos.offset(Direction.NORTH.getNormal()))
                   .setValue(TapperBlock.FILL, Math.clamp(
                   level.getBlockState(pos.offset(Direction.NORTH.getNormal())).getValue(TapperBlock.FILL) + 1, 0, 3))
                   .setValue(TapperBlock.OUTPUT, this.tapOutput), 0); }
            if (state.getValue(EAST) && isEast)
            { level.setBlock(pos.offset(Direction.EAST.getNormal()),
                    level.getBlockState(pos.offset(Direction.EAST.getNormal()))
                   .setValue(TapperBlock.FILL, Math.clamp(
                   level.getBlockState(pos.offset(Direction.EAST.getNormal())).getValue(TapperBlock.FILL) + 1, 0, 3))
                   .setValue(TapperBlock.OUTPUT, this.tapOutput), 0); }
            if (state.getValue(WEST) && isWest)
            { level.setBlock(pos.offset(Direction.WEST.getNormal()),
                    level.getBlockState(pos.offset(Direction.WEST.getNormal()))
                   .setValue(TapperBlock.FILL, Math.clamp(
                   level.getBlockState(pos.offset(Direction.WEST.getNormal())).getValue(TapperBlock.FILL) + 1, 0, 3))
                   .setValue(TapperBlock.OUTPUT, this.tapOutput), 0); }
            if (state.getValue(SOUTH) && isSouth)
            { level.setBlock(pos.offset(Direction.SOUTH.getNormal()),
                    level.getBlockState(pos.offset(Direction.SOUTH.getNormal()))
                   .setValue(TapperBlock.FILL, Math.clamp(
                   level.getBlockState(pos.offset(Direction.SOUTH.getNormal())).getValue(TapperBlock.FILL) + 1, 0, 3))
                    .setValue(TapperBlock.OUTPUT, this.tapOutput), 0); }
        }

        if (!isNorth) level.setBlock(pos, state.setValue(NORTH, false), 0);
        if (!isSouth) level.setBlock(pos, state.setValue(SOUTH, false), 0);
        if (!isEast) level.setBlock(pos, state.setValue(EAST, false), 0);
        if (!isWest) level.setBlock(pos, state.setValue(WEST, false), 0);

        if (!isNorth && !isSouth && !isEast && !isWest)
            level.setBlock(pos, state.setValue(TAPPED, false), 0);

        super.randomTick(state, level, pos, random);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock,
            BlockPos neighborPos, boolean movedByPiston)
    {
        if (level.getBlockState(neighborPos).getBlock() instanceof TapperBlock)
        {
            Direction d = level.getBlockState(neighborPos).getValue(TapperBlock.FACING);
            switch(d)
            {
                case SOUTH -> state = state.setValue(SOUTH, true).setValue(TAPPED, true);
                case NORTH -> state = state.setValue(NORTH, true).setValue(TAPPED, true);
                case EAST -> state = state.setValue(EAST, true).setValue(TAPPED, true);
                case WEST -> state = state.setValue(WEST, true).setValue(TAPPED, true);
            }

            level.setBlock(pos, state, 0);
            level.getBlockState(neighborPos).setValue(TapperBlock.OUTPUT, tapOutput);
        }

        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    { builder.add(AXIS, NORTH, SOUTH, EAST, WEST, TAPPED); }
}
