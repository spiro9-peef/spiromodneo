package com.github.peeftube.spiromodneo.util.stone;

public enum StoneSubBlockType
{
    DEFAULT(""),
    SLAB("_slab"),
    STAIRS("_stairs"),
    WALL("_wall"),
    BUTTON("_button"),
    PRESSURE_PLATE("_pressure_plate"),
    GROUND_STONES("_ground_stones");

    private final String name;

    StoneSubBlockType(String name)
    { this.name = name; }

    public String getGeneric()
    { return !this.name.isEmpty() ? this.name.substring(1) : this.name; }

    public String getAppendable()
    { return this.name; }
}
