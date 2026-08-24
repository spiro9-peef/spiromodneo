package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.ore.BaseStone;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum StoneMaterial
{
    STONE(getMOID(MOID.STONE), BaseStone.STONE),
    DEEPSLATE(getMOID(MOID.DEEPSLATE), BaseStone.DEEPSLATE),
    ANDESITE(getMOID(MOID.ANDESITE), BaseStone.ANDESITE),
    GRANITE(getMOID(MOID.GRANITE), BaseStone.GRANITE),
    DIORITE(getMOID(MOID.DIORITE), BaseStone.DIORITE),
    CALCITE(getMOID(MOID.CALCITE), BaseStone.CALCITE),
    SANDSTONE(getMOID(MOID.SANDSTONE), BaseStone.SMS),
    RED_SANDSTONE(getMOID(MOID.RED_SANDSTONE), BaseStone.SMRS),
    TUFF(getMOID(MOID.TUFF), BaseStone.TUFF),
    DRIPSTONE(getMOID(MOID.DRIPSTONE), BaseStone.DRIPSTONE),
    NETHERRACK(getMOID(MOID.NETHERRACK), BaseStone.NETHERRACK),
    BASALT(getMOID(MOID.BASALT), BaseStone.BASALT),
    ENDSTONE(getMOID(MOID.ENDSTONE), BaseStone.ENDSTONE),

    // Modded stone types!
    LIMBIPETRA(getMOID(MOID.LIMBIPETRA), BaseStone.LIMBIPETRA),
    HAEMOLITE(getMOID(MOID.HAEMOLITE), BaseStone.HAEMOLITE),
    PACKED_HAEMOLITE(getMOID(MOID.PACKED_HAEMOLITE), BaseStone.PACKED_HAEMOLITE);

    private final String name;
    /** This has been coded in to force any additions to this enum to also have additions
     * for the BaseStone enum, even if those additions are never used in practice.
     * This is intentional, as it should prevent code issues down the line, even though
     * it will prove irritating to work with. */
    private final BaseStone oreBase;

    StoneMaterial(String name, BaseStone oreBase)
    { this.name = name; this.oreBase = oreBase; }

    public String get()
    { return name; }

    public String getAsBlock()
    {
        switch(name)
        {
            case "dripstone" -> { return "dripstone_block"; }
            case "endstone" -> { return "end_stone"; }
            default -> { return name; }
        }
    }

    public BaseStone getOreBase()
    { return oreBase; }
}
