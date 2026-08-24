package com.github.peeftube.spiromodneo.util.ore;

import com.github.peeftube.spiromodneo.core.MOID;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.*;
import static com.github.peeftube.spiromodneo.core.init.Registrar.*;

public enum BaseStone
{
    // Stone uses NULL here because prefixing any stone derivatives with "stone" is ridiculous
    STONE(getMOID(MOID.NULL), STONE_BASED_ORE, () -> Blocks.STONE, true),
    ANDESITE(prefixMOID(MOID.ANDESITE), STONE_BASED_ORE, () -> Blocks.ANDESITE, true),
    DIORITE(prefixMOID(MOID.DIORITE), STONE_BASED_ORE, () -> Blocks.DIORITE, true),
    GRANITE(prefixMOID(MOID.GRANITE), STONE_BASED_ORE, () -> Blocks.GRANITE, true),
    CALCITE(prefixMOID(MOID.CALCITE), CALCITE_BASED_ORE, () -> Blocks.CALCITE, true),
    SMS(prefixMOID(MOID.SMOOTH_SANDSTONE), STONE_BASED_ORE,
            () -> Blocks.SMOOTH_SANDSTONE, () -> Blocks.SANDSTONE, true),
    SMRS(prefixMOID(MOID.SMOOTH_RED_SANDSTONE), STONE_BASED_ORE,
            () -> Blocks.SMOOTH_RED_SANDSTONE, () -> Blocks.RED_SANDSTONE, true),
    DEEPSLATE(prefixMOID(MOID.DEEPSLATE), DEEPSLATE_BASED_ORE, () -> Blocks.DEEPSLATE, true),
    TUFF(prefixMOID(MOID.TUFF), TUFF_BASED_ORE, () -> Blocks.TUFF, true),
    DRIPSTONE(prefixMOID(MOID.DRIPSTONE), DRIPSTONE_BASED_ORE, () -> Blocks.DRIPSTONE_BLOCK, true),
    NETHERRACK(prefixMOIDwithAlias(MOID.NETHERRACK, 0), NETHER_BASED_ORE, () -> Blocks.NETHERRACK, true),
    BASALT(prefixMOID(MOID.BASALT), BASALT_BASED_ORE, () -> Blocks.SMOOTH_BASALT, () -> Blocks.BASALT, true),
    ENDSTONE(prefixMOIDwithAlias(MOID.ENDSTONE, 0), STONE_BASED_ORE, () -> Blocks.END_STONE, true),

    // Modded stone types.
    LIMBIPETRA(prefixMOID(MOID.LIMBIPETRA), TUFF_BASED_ORE, 15),
    HAEMOLITE(prefixMOID(MOID.HAEMOLITE), NETHER_BASED_ORE, 0),
    PACKED_HAEMOLITE(prefixMOID(MOID.PACKED_HAEMOLITE), DEEPSLATE_BASED_ORE, 0);

    private final String                    name;
    private final Supplier<BlockBehaviour.Properties> props;
    private final boolean                   prePopulated;
    private Supplier<? extends Block>           oreBase;
    private Supplier<? extends Block>           defaultBase;
    private int lightLevel = 0;

    BaseStone(String name, Supplier<BlockBehaviour.Properties> props)
    { this.name = name; this.props = props; this.prePopulated = false; }

    BaseStone(String name, Supplier<BlockBehaviour.Properties> props, int li)
    { this.name = name; this.props = props; this.prePopulated = false; this.lightLevel = Math.max(0, Math.min(li, 15)); }

    BaseStone(String name, Supplier<BlockBehaviour.Properties> props, Supplier<? extends Block> oreBase, boolean isPopulated)
    { this.name = name; this.props = props; this.oreBase = oreBase; this.defaultBase = oreBase; this.prePopulated = isPopulated; }

    BaseStone(String name, Supplier<BlockBehaviour.Properties> props, Supplier<? extends Block> oreBase,
            Supplier<? extends Block> defaultBase, boolean isPopulated)
    { this.name = name; this.props = props; this.oreBase = oreBase; this.defaultBase = defaultBase; this.prePopulated = isPopulated; }

    public String get()
    { return name; }

    public BlockBehaviour.Properties getProps()
    { return props.get(); }

    /** Will only be set if this enum value is not pre-populated.
     * For vanilla stone types, this should always return preset values. */
    public void setOreBase(Supplier<? extends Block> oreBase)
    { this.oreBase = this.prePopulated ? this.oreBase : oreBase; }

    /** Will only be set if this enum value is not pre-populated.
     * For vanilla stone types, this should always return preset values. */
    public void setDefaultBase(Supplier<? extends Block> defaultBase)
    { this.defaultBase = this.prePopulated ? this.defaultBase : defaultBase; }

    public boolean isPrePopulated()
    { return prePopulated; }

    public Supplier<? extends Block> getOreBase()
    { return oreBase; }

    public Supplier<? extends Block> getDefault()
    { return defaultBase; }

    public int getLightLevel() { return lightLevel; }
}
