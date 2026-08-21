package com.github.peeftube.spiromodneo.core.init.content.events;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.blocks.ExtensibleBerryBushBlock;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import org.joml.Random;

@EventBusSubscriber(modid = SpiroMod.MOD_ID)
public class RightClickHandler
{
    @SubscribeEvent
    public static void onUseItem(UseItemOnBlockEvent event)
    {
        if (event.getItemStack().is(Items.STICK) && event.getLevel().getBlockState(event.getPos()).is(BlockTags.DIRT))
        {
            // Make this drop only (on average) 0.5% of the time as opposed to 2% now that ground stones are a thing
            // that generate
            if (0.005 >= event.getLevel().getRandom().nextFloat() && event.getPlayer() != null)
            { event.getPlayer().addItem(Registrar.SMALL_STONE.toStack()); }
        }

        boolean isPhantomBerry =
                event.getItemStack()
                     .is(((ExtensibleBerryBushBlock) Registrar.PHANTOM_BERRY_BUSH.get()).getBerry().asItem());
        boolean isSweetBerry =
                event.getItemStack().is(Items.SWEET_BERRIES);

        Level    level      = event.getLevel();
        BlockPos clickedPos = event.getPos();
        Direction face       = event.getFace();

        if (isPhantomBerry || isSweetBerry)
        {
            if (face == Direction.UP && !isSweetBerry)
            {
                BlockPos targetPos = clickedPos.above();

                if (level.isEmptyBlock(targetPos) &&
                        level.getBlockState(clickedPos).is(SpiroTags.Blocks.SUPPORTS_STONE_PLANTABLE_SAPLINGS))
                {
                    if (!level.isClientSide() && event.getPlayer() != null)
                    {
                        // Replace with ternary later
                        if (isPhantomBerry)
                        {
                            Block toPlace = Registrar.PHANTOM_BERRY_BUSH.get();
                            level.setBlock(targetPos, toPlace.defaultBlockState(), 3);

                            // Consume the berry from hand if not in creative
                            if (!event.getPlayer().getAbilities().instabuild)
                            { event.getItemStack().shrink(1); }

                            // Play placement sound
                            level.playSound(null, targetPos, SoundType.SWEET_BERRY_BUSH.getPlaceSound(),
                                    SoundSource.BLOCKS, 1.0F, 1.0F);
                        }

                        event.setCanceled(true);
                    }
                }
            }

            if (face == Direction.DOWN)
            {
                BlockPos targetPos = clickedPos.below();

                if (level.isEmptyBlock(targetPos) &&
                        level.getBlockState(clickedPos).is(SpiroTags.Blocks.SUPPORTS_CAVE_VINES))
                {
                    if (!level.isClientSide() && event.getPlayer() != null)
                    {
                        Block toPlace = isPhantomBerry ? Registrar.PHANTOM_VINES.get() : Registrar.RUBY_VINES.get();
                        level.setBlock(targetPos, toPlace.defaultBlockState(), 3);

                        // Consume the berry from hand if not in creative
                        if (!event.getPlayer().getAbilities().instabuild) { event.getItemStack().shrink(1); }

                        // Play placement sound
                        level.playSound(null, targetPos,
                                SoundType.CAVE_VINES.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    }

                    event.setCanceled(true);
                }
            }

            if ((level.getBlockState(clickedPos).is(SpiroTags.Blocks.PHANTOM_VINES) && isPhantomBerry) ||
                    (level.getBlockState(clickedPos).is(SpiroTags.Blocks.RUBY_VINES) && isSweetBerry))
            {
                BlockPos targetPos = clickedPos.below();
                if (level.isEmptyBlock(targetPos))
                {
                    if (!level.isClientSide() && event.getPlayer() != null)
                    {
                        Block toPlace = isPhantomBerry ?
                                Registrar.PHANTOM_VINES.get() : Registrar.RUBY_VINES.get();
                        Block toUpdate = isPhantomBerry ?
                                Registrar.PHANTOM_VINES_PLANT.get() : Registrar.RUBY_VINES_PLANT.get();

                        level.setBlock(clickedPos, toUpdate.defaultBlockState(), 3);
                        level.setBlock(targetPos, toPlace.defaultBlockState(), 3);

                        // Consume the berry from hand if not in creative
                        if (!event.getPlayer().getAbilities().instabuild) { event.getItemStack().shrink(1); }

                        // Play placement sound
                        level.playSound(null, targetPos,
                                SoundType.CAVE_VINES.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
