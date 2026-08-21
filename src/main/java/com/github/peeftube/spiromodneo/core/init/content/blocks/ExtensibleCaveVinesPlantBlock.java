package com.github.peeftube.spiromodneo.core.init.content.blocks;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ExtensibleCaveVinesPlantBlock extends CaveVinesPlantBlock implements ExtensibleCaveVines
{
    private final ItemLike             berry;
    private final ExtensibleCaveVinesType type;
    private final boolean              isDamageDisabled;

    public ExtensibleCaveVinesPlantBlock(Properties properties,
            ItemLike berry, ExtensibleCaveVinesType type, boolean isDamageDisabled)
    {
        super(properties);

        this.berry = berry;
        this.type = type;
        this.isDamageDisabled = isDamageDisabled;
    }

    public ExtensibleCaveVinesType checkType()
    { return this.type; }

    protected GrowingPlantHeadBlock getHeadBlock()
    {
        switch (this.type)
        {
            case PHANTOM ->
            { return (GrowingPlantHeadBlock) Registrar.PHANTOM_VINES.get(); }
            case RUBY ->
            { return (GrowingPlantHeadBlock) Registrar.RUBY_VINES.get(); }
            case VISCERAL ->
            { return (GrowingPlantHeadBlock) Registrar.EVISCERA.get(); }
            default ->
            { return (GrowingPlantHeadBlock) Blocks.CAVE_VINES; }
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos blockpos = pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return !this.type.equals(ExtensibleCaveVinesType.VISCERAL) ?
                this.canAttachTo(blockstate) && (blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock())
                || blockstate.isFaceSturdy(level, blockpos, this.growthDirection)) :
                this.canAttachTo(blockstate) && (blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock())
                        || (blockstate.isFaceSturdy(level, blockpos, this.growthDirection) &&
                        blockstate.is(SpiroTags.Blocks.VISCERA_SOLID)));
    }

    public ItemLike getBerry()
    { return this.berry; }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state)
    { return new ItemStack(this.berry); }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (!this.isDamageDisabled && (entity instanceof LivingEntity &&
                entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE))
        {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75, 0.8F));
            if (!level.isClientSide &&
                    (entity.xOld != entity.getX() || entity.zOld != entity.getZ()))
            {
                double d0 = Math.abs(entity.getX() - entity.xOld);
                double d1 = Math.abs(entity.getZ() - entity.zOld);
                if (d0 >= 0.003F || d1 >= 0.003F) {
                    entity.hurt(level.damageSources().sweetBerryBush(), 1.0F);
                }
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState b, Level l, BlockPos pos, Player p, BlockHitResult h)
    {
        if ((Boolean)b.getValue(BERRIES))
        {
            Block.popResource(l, pos, new ItemStack(getBerry(), 1));
            float f = Mth.randomBetween(l.random, 0.8F, 1.2F);
            l.playSound((Player)null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            BlockState blockstate = (BlockState)b.setValue(BERRIES, false);
            l.setBlock(pos, blockstate, 2);
            l.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(p, blockstate));
            return InteractionResult.sidedSuccess(l.isClientSide);
        }
        else
        { return InteractionResult.PASS; }
    }
}
