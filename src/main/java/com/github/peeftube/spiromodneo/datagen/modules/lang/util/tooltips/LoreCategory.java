package com.github.peeftube.spiromodneo.datagen.modules.lang.util.tooltips;

public enum LoreCategory
{
    FLAVOR_TEXT("flavor"),
    GAMEPLAY_HINTS("hint");

    private final String key;

    LoreCategory(String key) { this.key = key; }

    public String getKey() { return key; }
}
