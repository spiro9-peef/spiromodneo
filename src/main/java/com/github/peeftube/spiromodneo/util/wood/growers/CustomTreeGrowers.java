package com.github.peeftube.spiromodneo.util.wood.growers;

import com.github.peeftube.spiromodneo.datagen.modules.world.util.ConfigFeaturesData;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class CustomTreeGrowers
{
    public static TreeGrower ASHEN_OAK = new TreeGrower(
            "ashen_oak", 0.1F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ConfigFeaturesData.ASHEN_OAK),
            Optional.of(ConfigFeaturesData.ASHEN_OAK_FANCY),
            Optional.of(ConfigFeaturesData.ASHEN_OAK_BEES),
            Optional.of(ConfigFeaturesData.ASHEN_OAK_FANCY_BEES)
    );
    public static final TreeGrower ASHEN_BIRCH = new TreeGrower(
            "ashen_birch", Optional.empty(),
            Optional.of(ConfigFeaturesData.ASHEN_BIRCH),
            Optional.of(ConfigFeaturesData.ASHEN_BIRCH_BEES)
    );
    public static TreeGrower MAPLE = new TreeGrower(
            "maple", 0.1F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ConfigFeaturesData.MAPLE),
            Optional.of(ConfigFeaturesData.MAPLE_FANCY),
            Optional.of(ConfigFeaturesData.MAPLE_BEES),
            Optional.of(ConfigFeaturesData.MAPLE_FANCY_BEES)
    );
    public static TreeGrower RUBBERWOOD = new TreeGrower(
            "rubberwood",
            Optional.of(ConfigFeaturesData.RUBBERWOOD_HUGE),
            Optional.of(ConfigFeaturesData.RUBBERWOOD),
            Optional.empty()
    );
    public static TreeGrower STONEWOOD = new TreeGrower(
            "stonewood", Optional.empty(), Optional.of(ConfigFeaturesData.STONEWOOD),
            Optional.empty());
    public static TreeGrower AZURE_STONEWOOD = new TreeGrower(
            "azure_stonewood", Optional.empty(), Optional.of(ConfigFeaturesData.AZURE_STONEWOOD),
            Optional.empty());
    public static TreeGrower RUBY_STONEWOOD = new TreeGrower(
            "ruby_stonewood", Optional.empty(), Optional.of(ConfigFeaturesData.RUBY_STONEWOOD),
            Optional.empty());
    public static TreeGrower VERDANT_STONEWOOD = new TreeGrower(
            "verdant_stonewood", Optional.empty(), Optional.of(ConfigFeaturesData.VERDANT_STONEWOOD),
            Optional.empty());
    public static TreeGrower GILDED_STONEWOOD = new TreeGrower(
            "gilded_stonewood", Optional.empty(), Optional.of(ConfigFeaturesData.GILDED_STONEWOOD),
            Optional.empty());
    public static TreeGrower AMETHYST_STONEWOOD = new TreeGrower(
            "amethyst_stonewood", Optional.empty(), Optional.of(ConfigFeaturesData.AMETHYST_STONEWOOD),
            Optional.empty());
}
