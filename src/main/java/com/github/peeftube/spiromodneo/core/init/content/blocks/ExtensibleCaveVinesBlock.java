package com.github.peeftube.spiromodneo.core.init.content.blocks;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ExtensibleCaveVinesBlock extends CaveVinesBlock implements ExtensibleCaveVines
{
    private final ItemLike                                     berry;
    private final DeferredBlock<ExtensibleCaveVinesPlantBlock> body;
    private final boolean                                      isDamageDisabled;
    private final boolean                                      selfPropagating;

    public ExtensibleCaveVinesBlock(Properties properties,
            ItemLike berry, DeferredBlock<ExtensibleCaveVinesPlantBlock> body, boolean isDamageDisabled)
    {
        this(properties, berry, body, isDamageDisabled, false);
    }

    public ExtensibleCaveVinesBlock(Properties properties,
            ItemLike berry, DeferredBlock<ExtensibleCaveVinesPlantBlock> body, boolean isDamageDisabled,
            boolean selfPropagating)
    {
        super(properties);

        this.berry = berry;
        this.body = body;

        this.isDamageDisabled = isDamageDisabled;
        this.selfPropagating = selfPropagating;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (selfPropagating)
        {
            if (this.body.get().checkType().equals(ExtensibleCaveVinesType.VISCERAL))
            {
                int tries = 1 + random.nextInt(1);
                for (int attempt = 0; attempt < tries; attempt++)
                {
                    int      radiusToCheck = state.getValue(AGE) / 2 < 10 ? 5 : 11;
                    BlockPos sampler       = getRandomPositionInSphere(pos, radiusToCheck, random);

                    // Because we're checking from within a large area, we can't guarantee that we'll actually
                    // find a viable position, therefore this won't show often.
                    if (level.getBlockState(sampler).is(SpiroTags.Blocks.VISCERA_SOLID) &&
                            level.getBlockState(sampler.below()).isAir())
                    {
                        if (random.nextFloat() < 0.025F) // ~2.5% of successful finds should succeed.
                        {
                            SpiroMod.LOGGER.debug("Eviscera tip found a viable propagation location below site.");
                            level.setBlock(sampler.below(), this.defaultBlockState(), 3);
                            attempt = tries; // We've successfully propagated, cancel.
                        }
                    }

                    // We can't guarantee that the block we're checking is correct, so check if the current block
                    // is air and the block above that is the correct source.
                    else if (level.getBlockState(sampler.above()).is(SpiroTags.Blocks.VISCERA_SOLID) &&
                            level.getBlockState(sampler).isAir())
                    {
                        if (random.nextFloat() < 0.025F) // ~2.5% of successful finds should succeed.
                        {
                            SpiroMod.LOGGER.debug("Eviscera tip found a viable propagation location on-site.");
                            level.setBlock(sampler, this.defaultBlockState(), 3);
                            attempt = tries; // We've successfully propagated, cancel.
                        }
                    }
                }
            }
        }

        if (!level.isClientSide() && this.body.get().checkType().equals(ExtensibleCaveVinesType.VISCERAL))
        {
            // 1. Define a spherical radius (e.g., 3 to 4 blocks out)
            double radius    = 4.0;
            AABB   searchBox = new AABB(pos).inflate(radius);

            boolean foundThistle = false;

            // 2. Scan the bounding box for your Eyefruit Thistle blocks
            // (You can check against your specific Block instance or a Block class type)
            for (BlockPos targetPos : BlockPos.betweenClosed(
                    BlockPos.containing(searchBox.minX, searchBox.minY, searchBox.minZ),
                    BlockPos.containing(searchBox.maxX, searchBox.maxY, searchBox.maxZ)))
            {
                if (level.getBlockState(targetPos).getBlock() instanceof ExtensibleBerryBushBlock eb)
                {
                    if (eb.getBerry().get() == Registrar.EYEFRUIT.get())
                    {
                        // Verify it's roughly within the actual spherical distance, not just the bounding box corners
                        if (pos.distSqr(targetPos) <= radius * radius)
                        {
                            foundThistle = true;
                            break;
                        }
                    }
                }
            }

            // 3. If an eyefruit thistle is detected nearby, apply the 5% chance rule
            if (foundThistle)
            {
                if (random.nextFloat() < 0.05f)
                { // 5% chance
                    destroyHighestEvisceraSegment(level, pos);
                }
            }
        }

        super.randomTick(state, level, pos, random);
    }

    private void destroyHighestEvisceraSegment(ServerLevel level, BlockPos currentPos)
    {
        BlockPos highestPos = currentPos;

        // Trace upward as long as the block above is also part of the Eviscera chain
        BlockPos checkPos = currentPos.above();
        while (level.getBlockState(checkPos).is(SpiroTags.Blocks.EVISCERA_STEM))
        {
            highestPos = checkPos;
            checkPos = checkPos.above();
        }

        // Replace the highest segment with air (or play a squelch/break sound effect here!)
        level.destroyBlock(highestPos, true); // true drops items/loot if desired, false just deletes it
    }

    public static BlockPos getRandomPositionInSphere(BlockPos center, double radius, RandomSource random)
    {
        // 1. Generate random spherical coordinates
        // Using cube root for distance ensures uniform distribution throughout the volume of the sphere
        double u = random.nextDouble();
        double theta = random.nextDouble() * 2.0 * Math.PI; // Azimuthal angle [0, 2π]
        double phi = Math.acos(2.0 * random.nextDouble() - 1.0); // Polar angle [0, π]

        double r = radius * Math.cbrt(u); // If you want it strictly on the surface,
                                          // just use `double r = radius;` instead of cbrt(u)

        // 2. Convert spherical to Cartesian coordinates (offsets from center)
        double offsetX = r * Math.sin(phi) * Math.cos(theta);
        double offsetY = r * Math.sin(phi) * Math.sin(theta);
        double offsetZ = r * Math.cos(phi);

        // 3. Add offsets to the block ticking position and floor/round to a BlockPos
        return center.offset(
                (int) Math.round(offsetX),
                (int) Math.round(offsetY),
                (int) Math.round(offsetZ)
        );
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
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos blockpos = pos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return !this.body.get().checkType().equals(ExtensibleCaveVinesType.VISCERAL) ?
                this.canAttachTo(blockstate) && (blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock())
                || blockstate.isFaceSturdy(level, blockpos, this.growthDirection)) :
                this.canAttachTo(blockstate) && (blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock())
                        || (blockstate.isFaceSturdy(level, blockpos, this.growthDirection) &&
                        blockstate.is(SpiroTags.Blocks.VISCERA_SOLID)));
    }

    @Override
    protected Block getBodyBlock() {
        return this.body.get();
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
