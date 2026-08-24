package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.Holder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;
import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOIDwithAlias;

public enum EquipmentMaterial
{
    LEATHER(getMOID(MOID.LEATHER), SpiroTags.Items.LEATHER_MATERIAL, null, ArmorMaterials.LEATHER),
    WOOD(getMOID(MOID.MAT_WOOD), SpiroTags.Items.WOOD_MATERIAL, Tiers.WOOD, null),
    SHARPWOOD(getMOID(MOID.MAT_SHARPWOOD), SpiroTags.Items.SHARPWOOD_MATERIAL, Registrar.T_SHARPWOOD, null),
    FLINT(getMOID(MOID.FLINT), SpiroTags.Items.FLINT_MATERIAL, Registrar.T_FLINT, null),
    STONE(getMOID(MOID.STONE), SpiroTags.Items.STONE_MATERIAL, Tiers.STONE, null),
    CHAIN(getMOID(MOID.CHAINMAIL), SpiroTags.Items.CHAINMAIL_MATERIAL, null, ArmorMaterials.CHAIN), // Chainmail doesn't have a crafting recipe iirc
    COPPER(getMOID(MOID.COPPER), SpiroTags.Items.COPPER_MATERIAL, Registrar.T_COPPER, Registrar.A_COPPER),
    IRON(getMOID(MOID.IRON), SpiroTags.Items.IRON_MATERIAL, Tiers.IRON, ArmorMaterials.IRON),
    LEAD(getMOID(MOID.LEAD), SpiroTags.Items.LEAD_MATERIAL, Registrar.T_LEAD, Registrar.A_LEAD),
    STEEL(getMOID(MOID.STEEL), SpiroTags.Items.STEEL_MATERIAL, Registrar.T_STEEL, Registrar.A_STEEL),
    GOLD(getMOIDwithAlias(MOID.GOLD, 0), SpiroTags.Items.GOLD_MATERIAL, Tiers.GOLD, ArmorMaterials.GOLD),
    DIAMOND(getMOID(MOID.DIAMOND), SpiroTags.Items.DIAMOND_MATERIAL, Tiers.DIAMOND, ArmorMaterials.DIAMOND),
    NETHERITE(getMOID(MOID.NETHERITE), SpiroTags.Items.NETHERITE_MATERIAL, Tiers.NETHERITE, ArmorMaterials.NETHERITE);

    private final String name;
    private final TagKey<Item> associatedTag;
    private final Tier toolTier;
    private final Holder<ArmorMaterial> armorTier;

    EquipmentMaterial(String name, TagKey<Item> associatedTag, Tier toolTier, Holder<ArmorMaterial> armorTier)
    { this.name = name; this.associatedTag = associatedTag; this.toolTier = toolTier; this.armorTier = armorTier; }

    public String getName()
    { return name; }

    public TagKey<Item> getAssociatedTag()
    { return associatedTag; }

    public Tier getToolTier()
    { return toolTier; }

    public Holder<ArmorMaterial> getArmorTier()
    { return armorTier; }
}
