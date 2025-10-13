package com.github.peeftube.spiromodneo.core.init.content.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GroundStoneBlock extends Block
{
    // There's PROBABLY a better way of doing this but I cbb with it as of writing this comment
    public static final IntegerProperty VARIANT = IntegerProperty.create("model_variation", 0, 3);

    public GroundStoneBlock(Properties properties)
    { super(properties); }
}
