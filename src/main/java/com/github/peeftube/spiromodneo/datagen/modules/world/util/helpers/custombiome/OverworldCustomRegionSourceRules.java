package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.custombiome;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.biome.NeoBiomes;
import com.github.peeftube.spiromodneo.core.init.registry.data.Soil;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.RuleSourceOverrides.*;
import static net.minecraft.world.level.levelgen.SurfaceRules.ifTrue;
import static net.minecraft.world.level.levelgen.SurfaceRules.stoneDepthCheck;

public class OverworldCustomRegionSourceRules
{
    public static SurfaceRules.RuleSource rules()
    {
        SurfaceRules.ConditionSource ATOP_FLOOR = stoneDepthCheck(-1, false, CaveSurface.FLOOR);

        SurfaceRules.RuleSource GRASS_ON_MUD =
                makeStateRule(Registrar.GRASS_TYPE.bulkData().get(Soil.MUD).getBlock().get());
        SurfaceRules.RuleSource HAEMOLITE =
                makeStateRule(Registrar.HAEMOLITE_SET.getBaseStone().get());
        SurfaceRules.RuleSource PACKED_HAEMOLITE =
                makeStateRule(Registrar.PACKED_HAEMOLITE_SET.getBaseStone().get());
        SurfaceRules.RuleSource WHITE_SAND = makeStateRule(Registrar.WHITE_SAND.get());
        SurfaceRules.RuleSource WHITE_SANDSTONE =
                makeStateRule(Registrar.WHITE_SANDSTONE_SET.getBaseStone().get());

        SurfaceRules.RuleSource MOSS = makeStateRule(Blocks.MOSS_BLOCK);
        SurfaceRules.RuleSource MOSS_CARPET = makeStateRule(Blocks.MOSS_CARPET);

        SurfaceRules.ConditionSource isTropicalSandBiome = SurfaceRules.isBiome(NeoBiomes.TROPICAL_BEACH);
        SurfaceRules.RuleSource preventFloatingWhiteSand =
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, WHITE_SANDSTONE), WHITE_SAND);
        SurfaceRules.RuleSource preventFloatingWhiteSandRule =
                SurfaceRules.ifTrue(isTropicalSandBiome, preventFloatingWhiteSand);

        SurfaceRules.RuleSource tropicalSand =
                SurfaceRules.ifTrue(isTropicalSandBiome,
                        SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, WHITE_SANDSTONE));

        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK)), DIRT);
        SurfaceRules.RuleSource mudGrassSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_ON_MUD)), MUD);
        SurfaceRules.RuleSource mossSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.sequence(SurfaceRules.ifTrue(ATOP_FLOOR,
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(Noises.NETHERRACK,
                            -0.0075, 0.0075), MOSS_CARPET)),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, MOSS))), DIRT);

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override deepslate, but not stone. This needs to go first so
         * that the stone override doesn't render deepslate outright impossible to find. */
        SurfaceRules.RuleSource grassSurfaceOverrideRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NeoBiomes.OVERWORLD_RUBBER_FOREST),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(
                                SurfaceRules.noiseCondition(Noises.SPAGHETTI_2D, -0.275, 0.275),
                                grassSurface), mudGrassSurface)),
                SurfaceRules.ifTrue(isTropicalSandBiome, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CONTINENTALNESS,
                        -0.13F, 1.0F), SurfaceRules.sequence(SurfaceRules.ifTrue(
                                SurfaceRules.noiseCondition(Noises.NETHERRACK, -0.075, 0.075),
                        mossSurface), grassSurface)), preventFloatingWhiteSand)),
                grassSurface
        );

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override deepslate, but not stone. This needs to go first so
         * that the stone override doesn't render deepslate outright impossible to find. */
        SurfaceRules.RuleSource deepstoneOverrideRule = SurfaceRules.sequence(
                // SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST), SMOOTH_BASALT),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NeoBiomes.FLESH_CAVERNS), PACKED_HAEMOLITE),
                DEEPSLATE
        );

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override stone but not deepslate, which needs to come first. */
        SurfaceRules.RuleSource topstoneOverrideRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NeoBiomes.OVERWORLD_RUBBER_FOREST), SMOOTH_BASALT),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NeoBiomes.FLESH_CAVERNS), HAEMOLITE)
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.aboveBottom(5)), BEDROCK),
                SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), grassSurfaceOverrideRule),
                                tropicalSand, preventFloatingWhiteSandRule)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient(
                        "deepslate", VerticalAnchor.absolute(0),
                        VerticalAnchor.absolute(8)), deepstoneOverrideRule),
                topstoneOverrideRule);
    }
}
