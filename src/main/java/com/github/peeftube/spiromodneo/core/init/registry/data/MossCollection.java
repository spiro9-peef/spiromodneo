package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import com.github.peeftube.spiromodneo.util.moss.MossType;
import com.github.peeftube.spiromodneo.util.moss.MossUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record MossCollection(MossMaterial material,
                             Map<MossType, GenericBlockItemCoupling> bulkData) implements MossUtilities
{
    public static List<MossCollection> MOSS_COLLECTIONS = new ArrayList<>();

    public static MossCollection registerCollection(MossMaterial type)
    { return registerCollection(type, 0); }

    public static MossCollection registerCollection(MossMaterial type, int li)
    {
        MossCollection collection = new MossCollection(type,
                MossUtilities.populate(type, li));
        MOSS_COLLECTIONS.add(collection); return collection;
    }
}