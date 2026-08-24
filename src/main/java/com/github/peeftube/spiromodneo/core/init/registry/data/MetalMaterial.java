package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum MetalMaterial
{
    IRON(getMOID(MOID.IRON)),
    COPPER(getMOID(MOID.COPPER)),
    GOLD(getMOID(MOID.GOLD)),
    NETHERITE(getMOID(MOID.NETHERITE)),

    // Modded.
    LEAD(getMOID(MOID.LEAD)),
    STEEL(getMOID(MOID.STEEL)),
    CRIMSONITE(getMOID(MOID.CRIMSONITE)),
    STRAVIMITE(getMOID(MOID.STRAVIMITE));

    private final String name;

    MetalMaterial(String name)
    { this.name = name; }

    public String get()
    { return name; }
}
