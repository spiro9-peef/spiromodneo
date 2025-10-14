package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.customfeature;

import com.github.peeftube.spiromodneo.core.init.content.blocks.GroundStoneBlock;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.common.Tags;

import java.util.Random;

import static com.github.peeftube.spiromodneo.core.init.Registrar.*;
import static com.github.peeftube.spiromodneo.core.init.content.worldgen.biome.NeoBiomes.NETHER_LIMBO_GARDEN;

public class GroundStoneFeature extends Feature<NoneFeatureConfiguration>
{
    public GroundStoneFeature(Codec<NoneFeatureConfiguration> codec)
    { super(codec); }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        BlockPos bp    = context.origin();
        WorldGenLevel level = context.level();

        Random r = new Random();

        int q = 0;

        for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++)
        {
            for (int x = 0; x < 16; x++)
            {
                for (int z = 0; z < 16; z++)
                {
                    // Check if this position is correct, then place the appropriate block.
                    // This should only succeed 2% of the time on average.
                    BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos().set(bp).move(x, y, z);
                    if ((level.getBlockState(mbp).is(SpiroTags.Blocks.SUPPORTS_GROUND_STONES)
                                    && level.getBlockState(mbp.offset(0, 1, 0)).isAir()) &&
                        r.nextFloat(1) <= 0.02)
                    {
                        BlockPos.MutableBlockPos mbp2 = new BlockPos.MutableBlockPos().set(bp).move(x, y + 1, z);
                        q++;

                        level.getBiome(mbp2);// Set a default state.
                        BlockState pickState = STONE_SET.getBaseStoneGround().get().defaultBlockState();

                        boolean altStateFlag0 = r.nextFloat() <= 0.1;
                        int altState0 = r.nextInt(3);

                        boolean altStateFlag1 = (mbp2.getY() <= 0);

                        pickState = altStateFlag0 && !altStateFlag1 ?
                                altState0 == 0 ? GRANITE_SET.getBaseStoneGround().get().defaultBlockState() :
                                altState0 == 1 ? ANDESITE_SET.getBaseStoneGround().get().defaultBlockState() :
                                                 DIORITE_SET.getBaseStoneGround().get().defaultBlockState() :
                                altStateFlag1 ?
                                        altStateFlag0 ? TUFF_SET.getBaseStoneGround().get().defaultBlockState() :
                                                        DEEPSLATE_SET.getBaseStoneGround().get().defaultBlockState() :
                                pickState;

                        if (level.getBiome(mbp2).is(Tags.Biomes.IS_BIRCH_FOREST))
                        {
                            pickState =
                                    altStateFlag1 ? BASALT_SET.getBaseStoneGround().get().defaultBlockState() :
                                                    CALCITE_SET.getBaseStoneGround().get().defaultBlockState();
                        }

                        if (level.getBiome(mbp2).is(NETHER_LIMBO_GARDEN))
                        {
                            pickState =
                                    altStateFlag0 ? BASALT_SET.getBaseStoneGround().get().defaultBlockState() :
                                                    LIMBIPETRA_SET.getBaseStoneGround().get().defaultBlockState();
                        }

                        if (level.getBiome(mbp2).is(SpiroTags.Biomes.YELLOW_SAND_BIOME))
                        {
                            pickState =
                                    altStateFlag1 ? pickState :
                                                    SANDSTONE_SET.getBaseStoneGround().get().defaultBlockState();
                        }

                        if (level.getBiome(mbp2).is(SpiroTags.Biomes.RED_SAND_BIOME))
                        {
                            pickState =
                                    altStateFlag1 ? pickState :
                                                    RED_SANDSTONE_SET.getBaseStoneGround().get().defaultBlockState();
                        }

                        int compassDirSelect = r.nextInt(4);
                        Direction dir = Direction.NORTH;

                        switch (compassDirSelect)
                        {
                            case 1 -> dir = Direction.SOUTH;
                            case 2 -> dir = Direction.EAST;
                            case 3 -> dir = Direction.WEST;
                        }

                        level.setBlock(mbp2, pickState
                                .setValue(GroundStoneBlock.FACING, dir)
                                .setValue(GroundStoneBlock.VARIANT, r.nextInt(4)), 3);
                    }
                }
            }
        }
        return q > 0;
    }
}
