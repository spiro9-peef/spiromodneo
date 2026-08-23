package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.customfeature;

import com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.customfeature.config.PulledSpikesFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec2;

public class PulledSpikesFeature extends Feature<PulledSpikesFeatureConfiguration>
{
    public PulledSpikesFeature(Codec<PulledSpikesFeatureConfiguration> codec)
    { super(codec); }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated, that they can safely generate into.
     *
     * @param context A context object with a reference to the level and the position
     *                the feature is being placed at
     */
    @Override
    public boolean place(FeaturePlaceContext<PulledSpikesFeatureConfiguration> context)
    {
        WorldGenLevel                    level  = context.level();
        BlockPos                         pos    = context.origin();
        RandomSource                     rSrc   = context.random();
        PulledSpikesFeatureConfiguration config = context.config();

        if (!isAirOrWater(level.getBlockState(pos)))
        { return false; }
        else
        {
            boolean isCeiling = level.getBlockState(pos.above()).is(config.blocksForBase);
            boolean isFloor = level.getBlockState(pos.below()).is(config.blocksForBase);

            // We know we're not starting on the proper material type, so cancel.
            if (!isCeiling && !isFloor) { return false; }

            BlockState determinedBaseMaterial = isCeiling ?
                    level.getBlockState(pos.above()) : level.getBlockState(pos.below());
            Direction growthDir = isCeiling ? Direction.DOWN : Direction.UP;

            // We know we're not starting in the correct biome set, so cancel.
            if (!config.safeSpawnBiomes.contains(level.getBiome(pos))) { return false; }

            BiomeGradientResult gradientCheck = sampleBiomeGradient(level, pos, config.safeSpawnBiomes);
            boolean isNearEdge = gradientCheck.isNearEdge;
            float edgeProxFactor = gradientCheck.edgeProximityFactor;
            Direction bias = gradientCheck.interiorBias;

            float spawnThreshold = 0.2f;
            if (edgeProxFactor < spawnThreshold && rSrc.nextFloat() > 0.15f)
            { return false; }

            return buildFeature(level, pos, rSrc, config, isNearEdge, determinedBaseMaterial,
                    growthDir, bias);
        }
    }

    private static boolean isAirOrWater(BlockState state) { return state.isAir() || state.is(Blocks.WATER); }

    public record BiomeGradientResult(boolean isNearEdge, float edgeProximityFactor, Direction interiorBias) {}

    public BiomeGradientResult sampleBiomeGradient(LevelAccessor level, BlockPos center, HolderSet<Biome> targetBiomes)
    {
        int matchingPoints = 0;
        int totalPoints = 0;

        // Epicenter + 4 rings (radii: 2, 4, 6, 8) with 8 cardinal/intercardinal directions each
        int[] radii = {0, 2, 4, 6, 8};
        Vec2 biasAccumulator = new Vec2(0, 0);

        for (int r : radii)
        {
            int pointsInRing = (r == 0) ? 1 : 8;
            for (int i = 0; i < pointsInRing; i++)
            {
                BlockPos samplePos;
                if (r == 0) { samplePos = center; }
                else
                {
                    double angle = (2 * Math.PI * i) / pointsInRing;
                    int dx = (int) Math.round(r * Math.cos(angle));
                    int dz = (int) Math.round(r * Math.sin(angle));
                    samplePos = center.offset(dx, 0, dz);
                }

                Holder<Biome> sampledBiome = level.getBiome(samplePos);
                totalPoints++;

                if (targetBiomes.contains(sampledBiome))
                {
                    matchingPoints++;
                    // Accumulate vector away from foreign biomes / toward interior
                    biasAccumulator = biasAccumulator.add(new Vec2(samplePos.getX() - center.getX(),
                            samplePos.getZ() - center.getZ()));
                }
            }
        }

        float matchRatio = (float) matchingPoints / totalPoints;
        boolean isNearEdge = matchRatio < 0.95f; // If any foreign biomes clipped the 8-block radius grid

        Direction dominantDir = Direction.NORTH; // Default fallback
        if (Math.abs(biasAccumulator.x) > Math.abs(biasAccumulator.y))
        { dominantDir = biasAccumulator.x > 0 ? Direction.EAST : Direction.WEST; }
        else { dominantDir = biasAccumulator.y > 0 ? Direction.SOUTH : Direction.NORTH; }

        return new BiomeGradientResult(isNearEdge, 1.0f - matchRatio, dominantDir);
    }

    private boolean buildFeature(ServerLevelAccessor l, BlockPos p, RandomSource r,
            PulledSpikesFeatureConfiguration c, boolean isNearEdge, BlockState base, Direction growthDir,
            Direction bias)
    {
        int baseRadiusX = isNearEdge && (bias.getAxis() == Direction.Axis.X) ? 2 : 1;
        int baseRadiusZ = isNearEdge && (bias.getAxis() == Direction.Axis.Z) ? 2 : 1;
        int baseLayers = r.nextInt(1, 4);
        int safeBaseLayers = baseLayers;

        // What we want to do now is check 8 points in the given radius. If they all hit solid material, we consider
        // this the base. If some are in air, we go up a layer, add 1 to an additive variable for baseLayers,
        // and try again. Do this the following number of tries before giving up.
        int findSolidBaseTryCount = 5;
        float baseRadiusChange = 1.0F;
        boolean hasFound = false;

        BlockPos newPos = p;

        for (int t = 0; t < findSolidBaseTryCount; t++)
        {
            if (!hasFound)
            {
                int currentRadiusX = Math.max(1, baseRadiusX + Math.round(t / 2.0F));
                int currentRadiusZ = Math.max(1, baseRadiusZ + Math.round(t / 2.0F));

                BlockPos pCheck = p.relative(growthDir.getOpposite(), t);

                int matchingPoints = 0;
                int totalPoints    = 0;

                int checkRadius = Math.max(currentRadiusX, currentRadiusZ);

                for (int i = 0; i < 8; i++)
                {
                    double   angle  = (2 * Math.PI * i) / 8;
                    int      dx     = (int) Math.round(checkRadius * Math.cos(angle));
                    int      dz     = (int) Math.round(checkRadius * Math.sin(angle));
                    BlockPos sample = pCheck.offset(dx, 0, dz);

                    totalPoints++;
                    if (!l.isEmptyBlock(sample)) { matchingPoints++; }
                }

                if (matchingPoints == totalPoints)
                {
                    hasFound = true;
                    baseLayers += t;
                    baseRadiusChange += t / 2.0F;

                    baseRadiusX = Math.round(baseRadiusX * baseRadiusChange);
                    baseRadiusZ = Math.round(baseRadiusZ * baseRadiusChange);

                    newPos = newPos.relative(growthDir.getOpposite(), t);
                }
            }
        }

        // Don't generate if we can't find solid terrain
        if (!hasFound) { return false; }

        int pillarHeight = r.nextInt(c.heightRange.getMinValue(), c.heightRange.getMaxValue());
        BlockPos pillarCurrent = newPos;

        for (int yLayer = 0; yLayer < baseLayers; yLayer++)
        {
            int currentRadiusX = Math.max(1, baseRadiusX - yLayer);
            int currentRadiusZ = Math.max(1, baseRadiusZ - yLayer);

            BlockPos layerCenter = newPos.relative(growthDir, yLayer);

            // Offset the center slightly toward the interior bias on edge formations
            if (isNearEdge && yLayer == 0) { layerCenter = layerCenter.relative(bias, 1); }

            for (int dx = -currentRadiusX; dx <= currentRadiusX; dx++)
            {
                for (int dz = -currentRadiusZ; dz <= currentRadiusZ; dz++)
                {
                    BlockPos targetPos = layerCenter.offset(dx, 0, dz);

                    // Ensure we only replace the blocks we want
                    if (l.isEmptyBlock(targetPos) || l.getBlockState(targetPos).is(base.getBlock()))
                    { l.setBlock(targetPos, base, 2); }
                }
            }
        }

        // Track slight drift coordinates for the vine-like skew effect
        int driftX = 0;
        int driftZ = 0;

        for (int i = 0; i < pillarHeight; i++) {
            // Apply slight procedural drift every couple of blocks to make it look jagged/curved
            if (i % 2 == 0 && r.nextFloat() < 0.4f)
            {
                if (isNearEdge)
                {
                    // Skew deeper into the bias direction if near an edge
                    driftX += bias.getStepX();
                    driftZ += bias.getStepZ();
                }
                else
                {
                    // Random organic drift in deep interior
                    driftX += r.nextInt(-1, 2);
                    driftZ += r.nextInt(-1, 2);
                }
            }

            BlockPos bonePos = pillarCurrent.offset(driftX, 0, driftZ);

            if (l.isEmptyBlock(bonePos) || l.getBlockState(bonePos).is(c.blocksForBase))
            { l.setBlock(bonePos, c.blockForSpike.defaultBlockState(), 2); }

            pillarCurrent = pillarCurrent.relative(growthDir);
        }

        return true;
    }
}
