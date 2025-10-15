package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.custombiome;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.biome.NeoBiomes;
import com.github.peeftube.spiromodneo.core.init.registry.data.Soil;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import static com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.RuleSourceOverrides.*;

public class OverworldCustomRegionSourceRules
{
    public static SurfaceRules.RuleSource rules()
    {
        SurfaceRules.RuleSource GRASS_ON_MUD =
                makeStateRule(Registrar.GRASS_TYPE.bulkData().get(Soil.MUD).getBlock().get());

        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK)), DIRT);
        SurfaceRules.RuleSource mudGrassSurface =
                SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_ON_MUD)), MUD);

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override deepslate, but not stone. This needs to go first so
         * that the stone override doesn't render deepslate outright impossible to find. */
        SurfaceRules.RuleSource grassSurfaceOverrideRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NeoBiomes.OVERWORLD_RUBBER_FOREST),
                        SurfaceRules.sequence(SurfaceRules.ifTrue(
                                SurfaceRules.noiseCondition(Noises.SPAGHETTI_2D, -0.075, 0.075),
                                grassSurface), mudGrassSurface)),
                grassSurface
        );

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override deepslate, but not stone. This needs to go first so
         * that the stone override doesn't render deepslate outright impossible to find. */
        SurfaceRules.RuleSource deepstoneOverrideRule = SurfaceRules.sequence(
                // SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST), SMOOTH_BASALT),
                DEEPSLATE
        );

        /** This is a per-biome materials replacer rule. It *should* run as expected.
         * Note that this will override stone but not deepslate, which needs to come first. */
        SurfaceRules.RuleSource topstoneOverrideRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(NeoBiomes.OVERWORLD_RUBBER_FOREST), SMOOTH_BASALT)
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.aboveBottom(5)), BEDROCK),
                SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), grassSurfaceOverrideRule)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient(
                        "deepslate", VerticalAnchor.absolute(0),
                        VerticalAnchor.absolute(8)), deepstoneOverrideRule),
                topstoneOverrideRule);
    }
}
