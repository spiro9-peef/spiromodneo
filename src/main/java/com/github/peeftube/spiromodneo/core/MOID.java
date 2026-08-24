package com.github.peeftube.spiromodneo.core;

import com.github.peeftube.spiromodneo.SpiroMod;

import java.util.ArrayList;
import java.util.List;

/// Contains the data for objects added by this mod.
/// Stands for ***M***od ***O***bject ***ID.***<p>
/// Useful for ensuring that updates to object names don't break
/// large amounts of code or require massive refactors.<p>
/// ---
/// *NOTE: This system may also contain IDs for vanilla and
/// other modded items/blocks/etc. if they are referenced by
/// this mod.*<p>
/// ***TODO: Refactor every ID registration to use MOID.***
public enum MOID
{
    // UTILITY
    NULL(""),

    // SOIL IDS
    // -- Vanilla
    DIRT("dirt"),
    MUD("mud"),
    SOUL_SOIL("soul_soil"),

    // GRASS-LIKE IDS
    // -- Vanilla
    GRASS("grass"),
    MYCELIUM("mycelium"),
    CRIMSON_NYLIUM("crimson_nylium"),
    WARPED_NYLIUM("warped_nylium"),
    // -- SpiroMod NEO
    VITALIUM("vitalium"),

    // MOSS IDS
    // -- Vanilla
    MOSS("moss"),
    // -- SpiroMod NEO
    AZURE_GLOWMOSS("azure_glow_moss"),
    RUBY_GLOWMOSS("ruby_glow_moss"),
    VERDANT_GLOWMOSS("verdant_glow_moss"),
    GILDED_GLOWMOSS("gilded_glow_moss"),
    AMETHYST_GLOWMOSS("amethyst_glow_moss"),

    // STONE IDS
    // -- Vanilla
    STONE("stone"),
    DEEPSLATE("deepslate"),
    ANDESITE("andesite"),
    GRANITE("granite"),
    DIORITE("diorite"),
    CALCITE("calcite"),
    SANDSTONE("sandstone"),
    RED_SANDSTONE("red_sandstone"),
    SMOOTH_SANDSTONE("smooth_sandstone"),
    SMOOTH_RED_SANDSTONE("smooth_red_sandstone"),
    TUFF("tuff"),
    DRIPSTONE("dripstone"),
    /// <h1>REFERENCE</h1><p><hr>
    /// <i>NOTE: This is also considered a soil type</i><hr>
    /// <b>Aliases:</b><br>
    /// 0: 'nether' - use for BaseStone w/ prefix<br>
    NETHERRACK("netherrack", "nether"),
    BASALT("basalt"),
    /// <h1>REFERENCE</h1><p><hr>
    /// <b>Aliases:</b><br>
    /// 0: 'end' - use for BaseStone w/ prefix<br>
    ENDSTONE("endstone", "end"),
    // -- SpiroMod NEO
    LIMBIPETRA("limbipetra"),
    HAEMOLITE("haemolite"),
    PACKED_HAEMOLITE("packed_haemolite"),

    // ORGANICS AND ORGANIC MATERIAL IDS
    // -- Vanilla
    LEATHER("leather"),
    /// <h1>REFERENCE</h1><p><hr>
    /// <i>NOTE: Specifically for wood as a general material, not any specific type</i><hr>
    MAT_WOOD("wooden"),
    // -- SpiroMod NEO
    MAT_SHARPWOOD("sharp_wooden"),
    CAOUTCHOUC("caoutchouc"),
    MAPLE_SAP("maple_sap"),

    // WOOD TYPE IDS
    // -- Vanilla
    OAK("oak"),
    BIRCH("birch"),
    SPRUCE("spruce"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak"),
    CHERRY("cherry"),
    CRIMSON_FUNGUS("crimson_fungus"),
    WARPED_FUNGUS("warped_fungus"),
    MANGROVE("mangrove"),
    // -- SpiroMod NEO
    ASHEN_OAK("ashen_oak"),
    ASHEN_BIRCH("ashen_birch"),
    STONEWOOD("stonewood"),
    AZURE_STONEWOOD("azure_stonewood"),
    RUBY_STONEWOOD("ruby_stonewood"),
    GILDED_STONEWOOD("gilded_stonewood"),
    VERDANT_STONEWOOD("verdant_stonewood"),
    AMETHYST_STONEWOOD("amethyst_stonewood"),
    RUBBERWOOD("rubberwood"),
    MAPLE("maple"),

    // ORE AND MINERAL MATERIAL IDS
    // -- Vanilla
    COAL("coal"),
    FLINT("flint"),
    IRON("iron"),
    /// <h1>REFERENCE</h1><p><hr>
    /// <i>NOTE: Not to be confused with actual chains!</i><hr>
    CHAINMAIL("chainmail"),
    COPPER("copper"),
    /// <h1>REFERENCE</h1><p><hr>
    /// <b>Aliases:</b><br>
    /// 0: 'golden' - use for EquipmentMaterial<br>
    GOLD("gold", "golden"),
    LAPIS("lapis"),
    REDSTONE("redstone"),
    EMERALD("emerald"),
    DIAMOND("diamond"),
    QUARTZ("quartz"),
    NETHERITE("netherite"),
    // -- SpiroMod NEO
    RUBY("ruby"),
    LEAD("lead"),
    STEEL("steel"),
    METHANE_ICE("methane_ice"),
    CRIMSONITE("crimsonite"),
    STRAVIMITE("stravimite"),
    
    PHANTOM_BERRIES("phantom_berries"),
    BLOODTHORN("bloodthorn"),
    EYEFRUIT("eyefruit"),
    COOKED_EYEFRUIT("cooked_eyefruit");

    private final String       key;
    private final List<String> aliases;

    // These all force lowercase just for safety reasons.
    MOID(String key)
    {
        boolean isNull = this.toString().equalsIgnoreCase("NULL");

        this.key = key.toLowerCase();
        this.aliases = new ArrayList<>();

        SpiroMod.LOGGER.info("REGISTERED MOID: '{}'",
                !isNull ? this.key.toLowerCase() : "null");
    }
    MOID(String key, String... aliases)
    {
        this.key = key.toLowerCase();
        this.aliases = new ArrayList<>();

        for (String alias : aliases)
        { this.aliases.add(alias.toLowerCase()); }

        if (this.aliases.size() == 1)
        { SpiroMod.LOGGER.info("REGISTERED MOID: '{}' with 1 alias", this.key.toLowerCase()); }
        else
        { SpiroMod.LOGGER.info("REGISTERED MOID: '{}' with {} aliases", this.key.toLowerCase(), this.aliases.size()); }
    }
    public String getKey() { return key.toLowerCase(); }
    public String getAlias(int index)
    {
        if (this.aliases.size() > index)
        { return aliases.get(index).toLowerCase(); }
        else
        {
            SpiroMod.LOGGER.warn("MOID '{}' does not have alias at index '{}', this may cause issues!",
                    this.key.toLowerCase(), index);
            SpiroMod.LOGGER.error("Could not find value for MOID alias index provided. " +
                    "Returning empty string and hoping nothing breaks.");
            return "";
        }
    }
}
