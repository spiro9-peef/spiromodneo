package com.github.peeftube.spiromodneo.util.wood;

import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import com.github.peeftube.spiromodneo.util.TagCoupling;

import java.util.Map;

public record WoodData(String name,
                       Map<LivingWoodBlockType, GenericBlockItemCoupling> livingWood,
                       TagCoupling logTags, TagCoupling leafTags, TagCoupling aliveTags,
                       Map<PlankBlockSubType, GenericBlockItemCoupling> planks,
                       TagCoupling plankTags,
                       Map<ManufacturedWoodType, GenericBlockItemCoupling> manufacturables,
                       Map<SignType, SignBlockItemTriad> signs/*, TODO: add support for boats
                       Supplier<? extends Item> boat, Supplier<? extends Item> boatWithChest */,
                       boolean isVariableWoodType)
{
}
