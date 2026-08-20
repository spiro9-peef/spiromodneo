package com.github.peeftube.spiromodneo.util.moss;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.blocks.SpecialMossBlock;
import com.github.peeftube.spiromodneo.core.init.registry.data.MossMaterial;
import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface MossUtilities
{
    public static Map<MossType, GenericBlockItemCoupling> populate(MossMaterial mat, int li)
    {
        Map<MossType, GenericBlockItemCoupling> output = new HashMap<>();

        for (MossType t : MossType.values()) { switch (t)
            {
                case MOSS_CARPET ->
                {
                    switch (mat)
                    {
                        case MOSS -> output.put(t, new GenericBlockItemCoupling(
                                () -> Blocks.MOSS_CARPET, () -> Items.MOSS_CARPET));
                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(mat.getName() + "_carpet",
                                    () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            output.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
                case MOSS_BLOCK ->
                {
                    switch (mat)
                    {
                        case MOSS -> output.put(t, new GenericBlockItemCoupling(
                                () -> Blocks.MOSS_BLOCK, () -> Items.MOSS_BLOCK));
                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(mat.getName() + "_block",
                                    () -> new SpecialMossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)
                                            .lightLevel(s -> li), mat.getFeatureForBonemeal()));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            output.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
            }
        }

        return output;
    }
}
