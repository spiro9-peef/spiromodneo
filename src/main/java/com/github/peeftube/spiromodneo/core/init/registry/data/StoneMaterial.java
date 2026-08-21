package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.ore.BaseStone;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum StoneMaterial
{
    STONE("stone", BaseStone.STONE),
    DEEPSLATE("deepslate", BaseStone.DEEPSLATE),
    ANDESITE("andesite", BaseStone.ANDESITE),
    GRANITE("granite", BaseStone.GRANITE),
    DIORITE("diorite", BaseStone.DIORITE),
    CALCITE("calcite", BaseStone.CALCITE),
    SANDSTONE("sandstone", BaseStone.SMS),
    RED_SANDSTONE("red_sandstone", BaseStone.SMRS),
    TUFF("tuff", BaseStone.TUFF),
    DRIPSTONE("dripstone", BaseStone.DRIPSTONE),
    NETHERRACK("netherrack", BaseStone.NETHERRACK),
    BASALT("basalt", BaseStone.BASALT),
    ENDSTONE("endstone", BaseStone.ENDSTONE),

    // Modded stone types!
    LIMBIPETRA("limbipetra", BaseStone.LIMBIPETRA),
    HAEMOLITE("haemolite", BaseStone.HAEMOLITE),
    PACKED_HAEMOLITE("packed_haemolite", BaseStone.PACKED_HAEMOLITE);

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
