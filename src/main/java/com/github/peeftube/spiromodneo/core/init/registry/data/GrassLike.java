package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum GrassLike
{
    GRASS(getMOID(MOID.GRASS)),
    MYCELIUM(getMOID(MOID.MYCELIUM)),
    CRIMSON_NYLIUM(getMOID(MOID.CRIMSON_NYLIUM)),
    WARPED_NYLIUM(getMOID(MOID.WARPED_NYLIUM)),
    VITALIUM(getMOID(MOID.VITALIUM));

    private final String name;

    GrassLike(String name) { this.name = name; }

    public String getName()
    { return name; }
}
