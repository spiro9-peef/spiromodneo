package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.wood.growers.CustomTreeGrowers;
import net.minecraft.world.level.block.grower.TreeGrower;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum VariableWoodMaterial
{
    // Stonewood
    AZURE_STONEWOOD(getMOID(MOID.AZURE_STONEWOOD), Registrar.STONEWOOD, CustomTreeGrowers.AZURE_STONEWOOD),
    RUBY_STONEWOOD(getMOID(MOID.RUBY_STONEWOOD), Registrar.STONEWOOD, CustomTreeGrowers.RUBY_STONEWOOD),
    VERDANT_STONEWOOD(getMOID(MOID.VERDANT_STONEWOOD), Registrar.STONEWOOD, CustomTreeGrowers.VERDANT_STONEWOOD),
    GILDED_STONEWOOD(getMOID(MOID.GILDED_STONEWOOD), Registrar.STONEWOOD, CustomTreeGrowers.GILDED_STONEWOOD),
    AMETHYST_STONEWOOD(getMOID(MOID.AMETHYST_STONEWOOD), Registrar.STONEWOOD, CustomTreeGrowers.AMETHYST_STONEWOOD);

    private final String name;
    private final WoodCollection wood;
    private final TreeGrower treeGrower;

    VariableWoodMaterial(String name, WoodCollection wood, TreeGrower treeGrower)
    { this.name = name; this.wood = wood; this.treeGrower = treeGrower; }

    public String getName() { return name; }
    public WoodCollection getWood() { return wood; }
    public TreeGrower getGrower() { return treeGrower; }
}
