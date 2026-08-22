package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.customfeature.modifiers;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class NotInStructPlacement extends PlacementModifier
{
    public static final MapCodec<NotInStructPlacement> CODEC = MapCodec.unit(NotInStructPlacement::new);

    public static NotInStructPlacement block() { return new NotInStructPlacement(); }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource rSrc, BlockPos pos)
    {
        // Check if a structure is present at this position using 1.21's structure manager
        ServerLevel level       = context.getLevel().getLevel();
        boolean     inStructure = level.structureManager().hasAnyStructureAt(pos);

        if (inStructure) { return Stream.empty(); /* Cancel generation here! */ }
        return Stream.of(pos); // Allow generation
    }

    @Override
    public PlacementModifierType<?> type()
    { return Registrar.NOT_IN_STRUCT_PLACEMENT.get(); }
}
