package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum TappableWoodMaterial
{
    RUBBERWOOD(getMOID(MOID.RUBBERWOOD), WoodMaterial.RUBBERWOOD, Tappable.CAOUTCHOUC),
    MAPLE(getMOID(MOID.MAPLE), WoodMaterial.MAPLE, Tappable.MAPLE_SAP);

    private final String name;
    private final WoodMaterial wood;
    private final Tappable output;

    TappableWoodMaterial(String name, WoodMaterial wood, Tappable output)
    { this.name = name; this.wood = wood; this.output = output; }

    public String getName() { return name; }
    public WoodMaterial getWoodMaterial() { return wood; }
    public Tappable getTapOutput() { return output; }
}
