package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.MathUtils;
import com.github.peeftube.spiromodneo.util.TagCoupling;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.ore.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record OreCollection(OreMaterial material, Map<StoneMaterial, OreCoupling> bulkData,
                            RawCoupling rawOreCoupling, TagCoupling oreTags,
                            NumberProvider oreDropData, FuelOreData fuel) implements OreUtilities
{
    public static List<OreCollection> ORE_COLLECTIONS = new ArrayList<>();

    public static OreCollection registerCollection(OreMaterial material)
    { return registerCollection(material, 0, new MathUtils.MinMax(1, 1), FuelOreData.nonFuel()); }

    public static OreCollection registerCollection(OreMaterial material, int li)
    { return registerCollection(material, li, new MathUtils.MinMax(1, 1), FuelOreData.nonFuel()); }

    public static OreCollection registerCollection(OreMaterial material, MathUtils.MinMax minMax)
    { return registerCollection(material, 0, minMax, FuelOreData.nonFuel()); }

    public static OreCollection registerCollection(OreMaterial material, MathUtils.MinMax minMax, int li)
    { return registerCollection(material, li, minMax, FuelOreData.nonFuel()); }

    public static OreCollection registerCollection(OreMaterial material, MathUtils.MinMax minMax, FuelOreData fuel)
    { return registerCollection(material, 0, minMax, fuel); }

    public static OreCollection registerCollection(OreMaterial material, int lightEmissionLevel, MathUtils.MinMax minMax,
            FuelOreData fuel)
    {
        String oreName = material.get() + "_ore";
        int li = lightEmissionLevel;

        // Set up the map.
        Map<StoneMaterial, OreCoupling> mappings = new HashMap<>();

        for(StoneMaterial s : StoneMaterial.values())
        {
            // Light level should never surpass 15, and should be the higher between the base stone and ore light levels
            li = Math.max(li, s.getOreBase().getLightLevel());

            switch(s)
            {
                case STONE, DEEPSLATE ->
                {
                    switch(material)
                    {
                        case COAL, IRON, COPPER, GOLD, LAPIS, REDSTONE, EMERALD, DIAMOND ->
                        { mappings.put(s, findPreset(s.getOreBase(), material)); }
                        default -> { mappings.put(s, createNew(s.getOreBase(), oreName, li)); }
                    }
                }
                case NETHERRACK ->
                {
                    switch(material)
                    {
                        case GOLD, QUARTZ ->
                        { mappings.put(s, findPreset(s.getOreBase(), material)); }
                        default -> { mappings.put(s, createNew(s.getOreBase(), oreName, li)); }
                    }
                }
                default -> { mappings.put(s, createNew(s.getOreBase(), oreName, li)); }
            }
        }
        String tagkey = "spiro_" + material.get() + "_ore";
        TagKey<Block> blockTag = SpiroTags.Blocks.tag(tagkey);
        TagKey<Item> itemTag = SpiroTags.Items.tag(tagkey);
        TagCoupling  tags    = new TagCoupling(blockTag, itemTag);

        NumberProvider oreDrops = (MathUtils.MinMax.getMin() == MathUtils.MinMax.getMax()) ? ConstantValue.exactly(MathUtils.MinMax.getMin()) :
                UniformGenerator.between(MathUtils.MinMax.getMin(), MathUtils.MinMax.getMax());

        OreCollection collection = new OreCollection(material, mappings, OreUtilities.determineRawOre(material, li),
                tags, oreDrops, fuel);

        ORE_COLLECTIONS.add(collection); return collection;
    }

    /* This is only ever called on vanilla blocks.
     * Don't be stupid and try to use this with other use cases unless
     * you insist upon giving yourself a headache.
     * That or you're a dense masochist. Choice is yours, just don't cry to me about it.
     * - spiro9 - */
    private static OreCoupling findPreset(BaseStone b, OreMaterial m)
    {
        // Easy!
        Map<BaseStone, Map<OreMaterial, OreCoupling>> reference = OreUtilities.getComboPresets();
        return reference.get(b).get(m);
    }

    private static OreCoupling createNew(BaseStone b, String m, int li)
    {
        Supplier<Block> block;

        // Quick redstone catch case
        if (m.toLowerCase().contains("redstone"))
        { block = () -> new RedStoneOreBlock(b.getProps().lightLevel(s -> li)); }
        else { block = () -> new Block(b.getProps().lightLevel(s -> li)); }

        block = Registrar.regBlock(b.get() + m, block);
        Supplier<Item> item = Registrar.regSimpleBlockItem((DeferredBlock<Block>) block);

        return new OreCoupling(block, item);
    }

    public OreMaterial getMat()
    { return this.material; }

    public Map<StoneMaterial, OreCoupling> getBulkData()
    { return bulkData; }

    public RawCoupling getRawOre()
    { return rawOreCoupling; }

    public TagKey<Block> getOreBT()
    { return oreTags.getBlockTag(); }

    public TagKey<Item> getOreIT()
    { return oreTags.getItemTag(); }

    public NumberProvider getDropCount()
    { return oreDropData; }
}
