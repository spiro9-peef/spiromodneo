package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import com.github.peeftube.spiromodneo.util.wood.WoodUtilities;

import java.util.ArrayList;
import java.util.List;

public record VariableWoodCollection(VariableWoodMaterial material, WoodCollection wood,
                                     GenericBlockItemCoupling leaves, GenericBlockItemCoupling sapling)
    implements WoodUtilities
{
    public static List<VariableWoodCollection> VARIABLE_WOOD_COLLECTIONS = new ArrayList<>();

    public static VariableWoodCollection registerCollection(VariableWoodMaterial type)
    { return registerCollection(type, 0); }

    public static VariableWoodCollection registerCollection(VariableWoodMaterial type, int li)
    {
        VariableWoodCollection collection = new VariableWoodCollection(type,
                type.getWood(),
                WoodUtilities.pVariableLeaves(type, li),
                WoodUtilities.pVariableSapling(type, li));
        VARIABLE_WOOD_COLLECTIONS.add(collection); return collection;
    }
}
