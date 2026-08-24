package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;
import static com.github.peeftube.spiromodneo.util.SpiroTags.Blocks.tag;

public enum Soil
{
    DIRT(getMOID(MOID.DIRT), () -> Blocks.DIRT),
    MUD(getMOID(MOID.MUD), () -> Blocks.MUD),
    NETHERRACK(getMOID(MOID.NETHERRACK), () -> Blocks.NETHERRACK),
    SOUL_SOIL(getMOID(MOID.SOUL_SOIL), () -> Blocks.SOUL_SOIL);
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
