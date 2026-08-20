package com.github.peeftube.spiromodneo.core.init.content.blocks;

import com.github.peeftube.spiromodneo.core.init.registry.data.Tappable;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Map;

public class TapperBlock extends HorizontalDirectionalBlock
{
    public static final MapCodec<TapperBlock> CODEC = simpleCodec(TapperBlock::new);
    public static final IntegerProperty       FILL   = IntegerProperty.create("filled", 0, 3);
    public static final EnumProperty<Tappable> OUTPUT = EnumProperty.create("material", Tappable.class);

    public TapperBlock(Properties properties)
    {
        super(properties.randomTicks());
        this.registerDefaultState(this.stateDefinition.any()
          .setValue(FACING, Direction.NORTH)
          .setValue(FILL, 0)
          .setValue(OUTPUT, Tappable.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    { builder.add(HorizontalDirectionalBlock.FACING, FILL, OUTPUT); }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult)
    { return doUse(state, level, pos, player) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.FAIL; }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hitResult)
    { return doUse(state, level, pos, player) ? InteractionResult.SUCCESS : InteractionResult.FAIL; }

    private boolean doUse(BlockState state, Level level, BlockPos pos, Player player)
    {
        if (state.getValue(FILL) == 0) { return false; }
        else
        {
            Tappable value = state.getValue(OUTPUT);
            if (value == Tappable.EMPTY) { return false; }
            else
            {
                player.addItem(new ItemStack(value.getItem(), state.getValue(FILL)));
                level.setBlock(pos, state.setValue(FILL, 0), 3);
            }
        }

        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        // Make sure the block can at least survive
        if (!canSurvive(state, level, pos)) { level.destroyBlock(pos, true); }

        if (!level.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

        // First we should check if the tapper is empty. Just to be safe, we'll set the material to empty too.
        if (state.getValue(FILL) == 0) { state = state.setValue(OUTPUT, Tappable.EMPTY); }

        Direction toCheck = state.getValue(FACING);
        BooleanProperty propertyForCheck =
                toCheck == Direction.NORTH ? BlockStateProperties.NORTH :
                toCheck == Direction.SOUTH ? BlockStateProperties.SOUTH :
                toCheck == Direction.EAST ? BlockStateProperties.EAST : BlockStateProperties.WEST;

        BlockState checked = level.getBlockState(pos.offset(toCheck.getNormal()));
        if (checked.is(SpiroTags.Blocks.SUPPORTS_TAPPER))
        {
            Block tapSupporter = checked.getBlock();
            if (tapSupporter instanceof TappableWoodBlock woodTapped)
            {
                if (random.nextInt(7) == 0)
                {
                    if (state.getValue(FILL) == 0) { state.setValue(OUTPUT, woodTapped.tapOutput); }
                    state = state.setValue(FILL, state.getValue(FILL) == 3 ? 3 : state.getValue(FILL) + 1);
                }

                level.setBlock(pos.offset(toCheck.getNormal()), checked.setValue(propertyForCheck, true)
                       .setValue(TappableWoodBlock.TAPPED, true), 3);
            }
        }

        super.randomTick(state, level, pos, random);
    }

    // Copied from FaceAttachedHorizontalDirectionalBlock.java
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    { return canAttach(level, pos, state.getValue(FACING).getOpposite()); }

    // Copied from FaceAttachedHorizontalDirectionalBlock.java
    public static boolean canAttach(LevelReader reader, BlockPos pos, Direction direction)
    {
        BlockPos blockpos = pos.relative(direction);
        return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction.getOpposite()) &&
                reader.getBlockState(blockpos).is(SpiroTags.Blocks.SUPPORTS_TAPPER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        for (Direction direction : context.getNearestLookingDirections())
        {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y)
            { blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()); }
            else { blockstate = this.defaultBlockState().setValue(FACING, direction.getOpposite()); }

            if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
            { return blockstate; }
        }

        return null;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos,
            Block neighborBlock, BlockPos neighborPos, boolean movedByPiston)
    {
        // Make sure the block can at least survive
        if (!canSurvive(state, level, pos)) { level.destroyBlock(pos, true); }

        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    protected MapCodec<TapperBlock> codec() { return CODEC; }
}
