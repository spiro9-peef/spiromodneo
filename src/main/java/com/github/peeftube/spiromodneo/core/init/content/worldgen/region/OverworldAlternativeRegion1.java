package com.github.peeftube.spiromodneo.core.init.content.worldgen.region;

import com.github.peeftube.spiromodneo.core.init.content.worldgen.biome.NeoBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

public class OverworldAlternativeRegion1 extends Region
{
    public OverworldAlternativeRegion1(ResourceLocation name, int weight)
    { super(name, RegionType.OVERWORLD, weight); }

    @Override
    public void addBiomes(Registry<Biome> reg, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.NEUTRAL,
                        ParameterUtils.Temperature.HOT))
                .humidity(ParameterUtils.Humidity.HUMID)
                .continentalness(ParameterUtils.Continentalness.FULL_RANGE)
                .erosion(ParameterUtils.Erosion.FULL_RANGE)
                .depth(ParameterUtils.Depth.FULL_RANGE)
                .weirdness(Climate.Parameter.span(0.175F, 0.75F))
                .build().forEach(point -> builder.add(point, NeoBiomes.OVERWORLD_RUBBER_FOREST));

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.NEUTRAL)
                .humidity(ParameterUtils.Humidity.WET)
                .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.COAST,
                        ParameterUtils.Continentalness.FAR_INLAND))
                .erosion(ParameterUtils.Erosion.span(ParameterUtils.Erosion.EROSION_2, ParameterUtils.Erosion.EROSION_5))
                .depth(Climate.Parameter.span(-0.05F, 0.05F))
                .weirdness(ParameterUtils.Weirdness.VALLEY)
                .build().forEach(point -> builder.add(point, NeoBiomes.MATTOLE_RIVER));

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.WARM, ParameterUtils.Temperature.HOT))
                .humidity(ParameterUtils.Humidity.span(ParameterUtils.Humidity.WET, ParameterUtils.Humidity.HUMID))
                .continentalness(ParameterUtils.Continentalness.COAST)
                .erosion(ParameterUtils.Erosion.span(ParameterUtils.Erosion.EROSION_4, ParameterUtils.Erosion.EROSION_6))
                .depth(Climate.Parameter.span(Climate.Parameter.span(-1.0F, -0.93333334F),
                        Climate.Parameter.span(-0.4F, -0.26666668F)))
                .weirdness(ParameterUtils.Weirdness.VALLEY)
                .build().forEach(point -> builder.add(point, NeoBiomes.TROPICAL_BEACH));

        builder.build().forEach(mapper::accept);
    }
}
