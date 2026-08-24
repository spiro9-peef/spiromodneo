package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Supplier;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum OreMaterial
{
    // Vanilla.
    COAL(getMOID(MOID.COAL), false, BlockTags.COAL_ORES, null),
    IRON(getMOID(MOID.IRON), false, BlockTags.IRON_ORES, () -> Items.IRON_INGOT),
    COPPER(getMOID(MOID.COPPER), false, BlockTags.COPPER_ORES, () -> Items.COPPER_INGOT),
    GOLD(getMOID(MOID.GOLD), false, BlockTags.GOLD_ORES, () -> Items.GOLD_INGOT),
    LAPIS(getMOID(MOID.LAPIS), true, BlockTags.LAPIS_ORES, null),
    REDSTONE(getMOID(MOID.REDSTONE), true, BlockTags.REDSTONE_ORES, null),
    EMERALD(getMOID(MOID.EMERALD), true, BlockTags.EMERALD_ORES, null),
    DIAMOND(getMOID(MOID.DIAMOND), true, BlockTags.DIAMOND_ORES, null),
    QUARTZ(getMOID(MOID.QUARTZ), true, null, null),

    // Modded.
    RUBY(getMOID(MOID.RUBY), true, null, null),
    LEAD(getMOID(MOID.LEAD), false, null, Registrar.LEAD_METAL.ingotData().getIngot()),
    METHANE_ICE(getMOID(MOID.METHANE_ICE), true, null, null),
    CRIMSONITE(getMOID(MOID.CRIMSONITE), false, null, Registrar.CRIMSONITE_METAL.ingotData().getIngot()),
    STRAVIMITE(getMOID(MOID.STRAVIMITE), false, null, Registrar.STRAVIMITE_METAL.ingotData().getIngot());

    private final String name;

    // NOTE: May not actually be a gem, this just "asks" whether the material behaves similarly.
    private final boolean isGem;

    // Associated block tag; this is for vanilla ores but can be extended to Forge ores as well if needed
    private final TagKey<Block> associatedOreTag;

    // Associated ingot to smelt to, if one exists; if this is a "high-yield gem" which outputs raw ores, use that instead
    private final Supplier<Item> ingotConvertible;

    OreMaterial(String name, boolean isGem, TagKey<Block> associatedOreTag, Supplier<Item> ingotConvertible)
    { this.name = name; this.isGem = isGem;
        this.associatedOreTag = associatedOreTag; this.ingotConvertible = ingotConvertible; }

    public String get()
    { return name; }

    public boolean isGem()
    { return isGem; }

    public TagKey<Block> getAOT()
    { return associatedOreTag; }

    public Supplier<Item> getIngotConvertible()
    { return ingotConvertible; }
}
