package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.TagCoupling;
import com.github.peeftube.spiromodneo.util.equipment.EquipmentUtilities;
import com.github.peeftube.spiromodneo.util.stone.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @param material Passing in a <code>StoneMaterial</code> may seem pointless,
 *                 but there is a reason for it.
 *                 Some stone types are not vanilla and as such the <code>BaseStone</code>
 *                 associated with the <code>StoneMaterial</code> may need to be populated
 *                 after-the-fact. This is by design, to allow extensibility without
 *                 long-term hassle. */
public record StoneCollection(StoneMaterial material, StoneData bulkData, TagCoupling tags) implements StoneUtilities
{
    public static List<StoneCollection> STONE_COLLECTIONS = new ArrayList<>();

    public static StoneCollection registerCollection(StoneMaterial material)
    { return registerCollection(material, false); }

    /**
     * @param isBaseSmooth Checks if this collection behaves like basalt or sandstone; if so, it will use a
     *                     different value to pass into <code>setOreBase()</code>.
     */
    public static StoneCollection registerCollection(StoneMaterial material, boolean isBaseSmooth)
    {
        StoneData data = StoneUtilities.populate(material);
        String tagkey = "spiro_stone_of_type_" + data.name();

        TagCoupling newTags = new TagCoupling
        ( SpiroTags.Blocks.tag(tagkey), SpiroTags.Items.tag(tagkey) );
        StoneCollection collection = new StoneCollection(material, data, newTags);

        Map<StoneBlockType,
                Map<StoneVariantType,
                        Map<StoneSubBlockType,
                                GenericBlockItemCoupling>>> populatedBulkData = data.bulkData();

        collection.material().getOreBase().setOreBase(isBaseSmooth ?
                populatedBulkData.get(StoneBlockType.SMOOTH).get(StoneVariantType.DEFAULT)
                                 .get(StoneSubBlockType.DEFAULT).getBlock() :
                populatedBulkData.get(StoneBlockType.BASE).get(StoneVariantType.DEFAULT)
                                 .get(StoneSubBlockType.DEFAULT).getBlock());
        collection.material().getOreBase().setDefaultBase(
                populatedBulkData.get(StoneBlockType.BASE).get(StoneVariantType.DEFAULT)
                                 .get(StoneSubBlockType.DEFAULT).getBlock());

        STONE_COLLECTIONS.add(collection); return collection;
    }

    public Supplier<? extends Block> getBaseStone()
    {
        return this.bulkData().bulkData()
                   .get(StoneBlockType.BASE).get(StoneVariantType.DEFAULT).get(StoneSubBlockType.DEFAULT)
                .getBlock();
    }

    public Supplier<? extends Block> getCobble()
    {
        return this.bulkData().bulkData()
                   .get(StoneBlockType.COBBLE).get(StoneVariantType.DEFAULT).get(StoneSubBlockType.DEFAULT)
                   .getBlock();
    }

    public Supplier<? extends Block> getMossyCobble()
    {
        return this.bulkData().bulkData()
                   .get(StoneBlockType.COBBLE).get(StoneVariantType.MOSSY).get(StoneSubBlockType.DEFAULT)
                   .getBlock();
    }

    public Supplier<? extends Block> getBaseStoneGround()
    {
        return this.bulkData().bulkData()
                   .get(StoneBlockType.BASE).get(StoneVariantType.DEFAULT).get(StoneSubBlockType.GROUND_STONES)
                .getBlock();
    }

    public Supplier<? extends Block> getCobbleGround()
    {
        return this.bulkData().bulkData()
                   .get(StoneBlockType.COBBLE).get(StoneVariantType.DEFAULT).get(StoneSubBlockType.GROUND_STONES)
                   .getBlock();
    }

    public Supplier<? extends Block> getMossyCobbleGround()
    {
        return this.bulkData().bulkData()
                   .get(StoneBlockType.COBBLE).get(StoneVariantType.MOSSY).get(StoneSubBlockType.GROUND_STONES)
                   .getBlock();
    }
}
