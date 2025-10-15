package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.biome.NeoBiomes;
import com.github.peeftube.spiromodneo.core.init.registry.data.Soil;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public interface RuleSourceOverrides
{
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource MYCELIUM = makeStateRule(Blocks.MYCELIUM);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource CALCITE = makeStateRule(Blocks.CALCITE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource LAVA = makeStateRule(Blocks.LAVA);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource SOUL_SAND = makeStateRule(Blocks.SOUL_SAND);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource SOUL_SOIL = makeStateRule(Blocks.SOUL_SOIL);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource BASALT = makeStateRule(Blocks.BASALT);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource WARPED_WART_BLOCK = makeStateRule(Blocks.WARPED_WART_BLOCK);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource WARPED_NYLIUM = makeStateRule(Blocks.WARPED_NYLIUM);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource NETHER_WART_BLOCK = makeStateRule(Blocks.NETHER_WART_BLOCK);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource CRIMSON_NYLIUM = makeStateRule(Blocks.CRIMSON_NYLIUM);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource GLOWSTONE = makeStateRule(Blocks.GLOWSTONE);
    /** Copied from SurfaceRuleData.java */
    SurfaceRules.RuleSource ENDSTONE = makeStateRule(Blocks.END_STONE);

    // Original block replacement rules
    SurfaceRules.RuleSource SMOOTH_BASALT = makeStateRule(Blocks.SMOOTH_BASALT);
    SurfaceRules.RuleSource VITALIUM = makeStateRule(Registrar.VITALIUM_TYPE.bulkData().get(Soil.SOUL_SOIL).getBlock().get());
    SurfaceRules.RuleSource LIMBIPETRA = makeStateRule(Registrar.LIMBIPETRA_SET.getBaseStone().get());

    /** Copied from SurfaceRuleData.java */
    static SurfaceRules.RuleSource makeStateRule(Block block)
    { return SurfaceRules.state(block.defaultBlockState()); }

    /** Default settings; overload below allows passing in non-default values */
    static SurfaceRules.RuleSource overrideOverworld(boolean aboveGround, boolean bedrockRoof, boolean bedrockFloor)
    { return overrideOverworld(aboveGround, bedrockRoof, bedrockFloor, 320); }

    /** Copied from SurfaceRuleData.java, then modified */
    static SurfaceRules.RuleSource overrideOverworld(boolean aboveGround, boolean bedrockRoof, boolean bedrockFloor,
            int maxHeight)
    {
        int yLevel256CheckVariable = maxHeight - (320 - 256);

        SurfaceRules.ConditionSource unknownUse = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
        SurfaceRules.ConditionSource yLevel256Check = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(yLevel256CheckVariable), 0);
        SurfaceRules.ConditionSource unknownUse3 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
        SurfaceRules.ConditionSource unknownUse4 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
        SurfaceRules.ConditionSource unknownUse_BeneathSeaLevel = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0);
        SurfaceRules.ConditionSource directlyBeneathSeaLevel = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
        SurfaceRules.ConditionSource atSeaLevel = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        SurfaceRules.ConditionSource unknownUse5 = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource somethingPresentAbove = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource unknownUse6 = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.ConditionSource unknownUse7 = SurfaceRules.hole();
        SurfaceRules.ConditionSource isFrozenOcean = SurfaceRules.isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
        SurfaceRules.ConditionSource isSteep = SurfaceRules.steep();
        SurfaceRules.RuleSource preventUnderwaterGrass = SurfaceRules.sequence(SurfaceRules.ifTrue(somethingPresentAbove, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource preventFloatingSand = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
        SurfaceRules.RuleSource preventFloatingGravel = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);
        SurfaceRules.ConditionSource isSandyBiome = SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);
        SurfaceRules.ConditionSource isDesert = SurfaceRules.isBiome(Biomes.DESERT);
        SurfaceRules.RuleSource varietyRules = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.STONY_PEAKS),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125, 0.0125), CALCITE), STONE)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.STONY_SHORE),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.05, 0.05), preventFloatingGravel), STONE)
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_HILLS), SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), STONE)),
                SurfaceRules.ifTrue(isSandyBiome, preventFloatingSand),
                SurfaceRules.ifTrue(isDesert, preventFloatingSand),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DRIPSTONE_CAVES), STONE)
        );
        SurfaceRules.RuleSource createPowderSnow = SurfaceRules.ifTrue(
                SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45, 0.58), SurfaceRules.ifTrue(somethingPresentAbove, POWDER_SNOW)
        );
        SurfaceRules.RuleSource createPowderSnow2 = SurfaceRules.ifTrue(
                SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35, 0.6), SurfaceRules.ifTrue(somethingPresentAbove, POWDER_SNOW)
        );
        SurfaceRules.RuleSource varietyRules2 = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.FROZEN_PEAKS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isSteep, PACKED_ICE),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, -0.5, 0.2), PACKED_ICE),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, -0.0625, 0.025), ICE),
                                SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.SNOWY_SLOPES),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isSteep, STONE),
                                createPowderSnow,
                                SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK)
                        )
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.JAGGED_PEAKS), STONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.GROVE), SurfaceRules.sequence(createPowderSnow, DIRT)),
                varietyRules,
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA), SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), STONE)),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), preventFloatingGravel),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), STONE),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0), DIRT),
                                preventFloatingGravel
                        )
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MANGROVE_SWAMP), MUD),
                DIRT
        );
        SurfaceRules.RuleSource varietyRules3 = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.FROZEN_PEAKS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isSteep, PACKED_ICE),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, 0.0, 0.2), PACKED_ICE),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.025), ICE),
                                SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.SNOWY_SLOPES),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isSteep, STONE),
                                createPowderSnow2,
                                SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.JAGGED_PEAKS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isSteep, STONE), SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.GROVE),
                        SurfaceRules.sequence(createPowderSnow2, SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK))
                ),
                varietyRules,
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), STONE), SurfaceRules.ifTrue(surfaceNoiseAbove(-0.5), COARSE_DIRT))
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), preventFloatingGravel),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.0), STONE),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0), preventUnderwaterGrass),
                                preventFloatingGravel
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(1.75), COARSE_DIRT), SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95), PODZOL))
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.ICE_SPIKES), SurfaceRules.ifTrue(somethingPresentAbove, SNOW_BLOCK)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MANGROVE_SWAMP), MUD),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MUSHROOM_FIELDS), MYCELIUM),
                preventUnderwaterGrass
        );
        SurfaceRules.ConditionSource surfaceNoiseCondition = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909, -0.5454);
        SurfaceRules.ConditionSource surfaceNoiseCondition2 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818, 0.1818);
        SurfaceRules.ConditionSource surfaceNoiseCondition3 = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454, 0.909);
        SurfaceRules.RuleSource varietyRules4 = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(Biomes.WOODED_BADLANDS),
                                        SurfaceRules.ifTrue(
                                                unknownUse,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(surfaceNoiseCondition, COARSE_DIRT),
                                                        SurfaceRules.ifTrue(surfaceNoiseCondition2, COARSE_DIRT),
                                                        SurfaceRules.ifTrue(surfaceNoiseCondition3, COARSE_DIRT),
                                                        preventUnderwaterGrass
                                                )
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(Biomes.SWAMP),
                                        SurfaceRules.ifTrue(
                                                directlyBeneathSeaLevel,
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(atSeaLevel), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER)
                                                )
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(Biomes.MANGROVE_SWAMP),
                                        SurfaceRules.ifTrue(
                                                unknownUse_BeneathSeaLevel,
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(atSeaLevel), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER)
                                                )
                                        )
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(yLevel256Check, ORANGE_TERRACOTTA),
                                                SurfaceRules.ifTrue(
                                                        unknownUse4,
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(surfaceNoiseCondition, TERRACOTTA),
                                                                SurfaceRules.ifTrue(surfaceNoiseCondition2, TERRACOTTA),
                                                                SurfaceRules.ifTrue(surfaceNoiseCondition3, TERRACOTTA),
                                                                SurfaceRules.bandlands()
                                                        )
                                                ),
                                                SurfaceRules.ifTrue(
                                                        unknownUse5, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, RED_SANDSTONE), RED_SAND)
                                                ),
                                                SurfaceRules.ifTrue(SurfaceRules.not(unknownUse7), ORANGE_TERRACOTTA),
                                                SurfaceRules.ifTrue(unknownUse6, WHITE_TERRACOTTA),
                                                preventFloatingGravel
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        unknownUse3,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        atSeaLevel, SurfaceRules.ifTrue(SurfaceRules.not(unknownUse4), ORANGE_TERRACOTTA)
                                                ),
                                                SurfaceRules.bandlands()
                                        )
                                ),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue(unknownUse6, WHITE_TERRACOTTA))
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(
                                unknownUse5,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                                isFrozenOcean,
                                                SurfaceRules.ifTrue(
                                                        unknownUse7,
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(somethingPresentAbove, AIR), SurfaceRules.ifTrue(SurfaceRules.temperature(), ICE), WATER
                                                        )
                                                )
                                        ),
                                        varietyRules3
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        unknownUse6,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(isFrozenOcean, SurfaceRules.ifTrue(unknownUse7, WATER))
                                ),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, varietyRules2),
                                SurfaceRules.ifTrue(isSandyBiome, SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SANDSTONE)),
                                SurfaceRules.ifTrue(isDesert, SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SANDSTONE))
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS), STONE),
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), preventFloatingSand),
                                preventFloatingGravel
                        )
                )
        );

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override deepslate, but not stone. This needs to go first so
         * that the stone override doesn't render deepslate outright impossible to find. */
        SurfaceRules.RuleSource deepstoneOverrideRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST), SMOOTH_BASALT),
                DEEPSLATE
        );

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override stone but not deepslate, which needs to come first. */
        SurfaceRules.RuleSource topstoneOverrideRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST), CALCITE)
        );

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        if (bedrockRoof) {
            builder.add(
                    SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK)
            );
        }

        if (bedrockFloor)
        { builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK)); }

        SurfaceRules.RuleSource unknownRule = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), varietyRules4);
        builder.add(aboveGround ? unknownRule : varietyRules4);
        builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), deepstoneOverrideRule));
        builder.add(topstoneOverrideRule);
        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    /** Copied from SurfaceRuleData.java */
    static SurfaceRules.ConditionSource surfaceNoiseAbove(double value)
    { return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE); }
}
