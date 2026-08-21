package com.github.peeftube.spiromodneo.core.init.registry.data;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

import static com.github.peeftube.spiromodneo.util.SpiroTags.Blocks.tag;

public enum Soil
{
    DIRT("dirt", () -> Blocks.DIRT),
    MUD("mud", () -> Blocks.MUD),
    NETHERRACK("netherrack", () -> Blocks.NETHERRACK),
    SOUL_SOIL("soul_soil", () -> Blocks.SOUL_SOIL);
    // TODO: Add "blood mud"

    private final String name;
    private final Supplier<Block> soilBlock;
    private final TagKey<Block> tag;

    Soil(String name, Supplier<Block> soilBlock)
    { this.name = name; this.soilBlock = soilBlock; this.tag = tag(getTagIDForName(name)); }

    private static String getTagIDForName(String name)
    { return "soil_of_type_" + name; }

    public String getName() { return name; }
    public Supplier<Block> getSoil() { return soilBlock; }
    public TagKey<Block> getTag() { return tag; }
}
