package com.github.peeftube.spiromodneo.util.stone;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.StoneMaterial;
import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public interface StoneUtilities
{
    static StoneData populate(StoneMaterial mat)
    {
        Map<StoneBlockType, Map<StoneVariantType, Map<StoneSubBlockType, GenericBlockItemCoupling>>> mappings =
                new HashMap<>();

        for (StoneBlockType set : StoneBlockType.values())
        {
            Map<StoneVariantType, Map<StoneSubBlockType, GenericBlockItemCoupling>> setMappings =
                new HashMap<>();

            for (StoneVariantType variant : StoneVariantType.values())
            {
                Map<StoneSubBlockType, GenericBlockItemCoupling> variantMappings =
                        new HashMap<>();

                if ((variant == StoneVariantType.DEFAULT ||
                        (variant == StoneVariantType.MOSSY && set.isMossVariantAllowed()) ||
                        (variant == StoneVariantType.CRACKED && set.isCrackedVariantAllowed()) ||
                        (variant == StoneVariantType.CHISELED && set.isChiseledVariantAllowed())))
                {
                    for (StoneSubBlockType subSet : StoneSubBlockType.values())
                    {
                        if (validCase(set, variant, subSet)) // Checks if this set can actually be made.
                        {
                            String kv = ExistingStoneCouplings.getKey(mat, set, variant, subSet);
                            variantMappings.put(subSet, findIfExistsElseCreate(kv, mat,
                                    StoneSubBlockType.DEFAULT, variantMappings, mat.getOreBase().getLightLevel()));
                        }
                    }
                }

                setMappings.put(variant, variantMappings);
            }

            mappings.put(set, setMappings);
        }

        return new StoneData(mat.get(), mappings);
    }

    static boolean validCase(StoneBlockType set, StoneVariantType variant, StoneSubBlockType subSet)
    {
        switch(set)
        {
            case CUT, SMOOTH, POLISHED ->
            {
                // The variation is always the base variant, except for polished.
                if (set == StoneBlockType.POLISHED && variant == StoneVariantType.CHISELED)
                {
                    switch(subSet)
                    {
                        // Chiseled variants only ever have the default subtype.
                        case DEFAULT -> { return true; }
                        default -> { return false; }
                    }
                }

                switch(subSet)
                {
                    // These sets do not ever have walls or redstone triggers.
                    // Additionally, they should NEVER have ground stones.
                    case WALL, PRESSURE_PLATE, BUTTON, GROUND_STONES -> { return false; }
                    default -> { return true; }
                }
            }

            case BRICKS, TILES ->
            {
                switch(variant)
                {
                    // Bricks and tiles have automatic variation checks built in, but chiseled blocks
                    // need to be handled differently.
                    case CHISELED ->
                    {
                        switch(subSet)
                        {
                            // Chiseled variants only ever have the default subtype.
                            case DEFAULT -> { return true; }
                            default -> { return false; }
                        }
                    }
                    default ->
                    {
                        switch(subSet)
                        {
                            // Bricks and tiles all have slabs, stairs and wall subtypes.
                            // Additionally, they should NEVER have ground stones.
                            case BUTTON, PRESSURE_PLATE, GROUND_STONES -> { return false; }
                            default -> { return true; }
                        }
                    }
                }
            }

            case COLUMN ->
            {
                // Columns have all variations supported.
                switch(subSet)
                {
                    // Column variants only ever have the default subtype.
                    case DEFAULT -> { return true; }
                    default -> { return false; }
                }
            }

            case COBBLE ->
            {
                // Cobble is always going to be either default or mossy in variation.
                switch(subSet)
                {
                    // Cobble variants all share the same block subtypes.
                    case BUTTON, PRESSURE_PLATE -> { return false; }
                    default -> { return true; }
                }
            }

            default ->
            {
                // Default case should be raw stone only.
                // Raw stone never has its own subvariants, so we should always return true
                // since it will always have one set of all sub-blocks.
                return true;
            }
        }
    }

    static GenericBlockItemCoupling findIfExistsElseCreate(String kv, StoneMaterial mat,
            StoneSubBlockType mappingParser, Map<StoneSubBlockType, GenericBlockItemCoupling> mappings,
            int li)
    {
        Map<String, GenericBlockItemCoupling> presets = StoneSetPresets.getPresets();

        if (presets.containsKey(kv)) { return presets.get(kv); }
        else
        {
            Properties props = mat.getOreBase().getProps().lightLevel(s -> li);

            // This should default to 1s or 20t.
            int buttonPressTickSetting = (int) (20 * (props.destroyTime / 3F));

            if (kv.contains("slab"))
            {
                Supplier<Block> b = Registrar.regBlock(kv, () -> new SlabBlock(props));
                Supplier<Item> i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
                return new GenericBlockItemCoupling(b, i);
            }
            if (kv.contains("stairs"))
            {
                Supplier<Block> b = Registrar.regBlock(kv,
                        () -> new StairBlock(
                                Objects.requireNonNull(mappings.get(mappingParser))
                                       .getBlock().get().defaultBlockState(), props));
                Supplier<Item> i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
                return new GenericBlockItemCoupling(b, i);
            }
            if (kv.contains("button"))
            {
                Supplier<Block> b = Registrar.regBlock(kv,
                        () -> new ButtonBlock(BlockSetType.STONE,
                                buttonPressTickSetting,
                                props));
                Supplier<Item> i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
                return new GenericBlockItemCoupling(b, i);
            }
            if (kv.contains("pressure_plate"))
            {
                Supplier<Block> b = Registrar.regBlock(kv,
                        () -> new PressurePlateBlock(BlockSetType.STONE,
                                props));
                Supplier<Item> i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
                return new GenericBlockItemCoupling(b, i);
            }
            if (kv.endsWith("_wall"))
            {
                Supplier<Block> b = Registrar.regBlock(kv, () -> new WallBlock(props));
                Supplier<Item> i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
                return new GenericBlockItemCoupling(b, i);
            }

            boolean isNotSubBlockVariant = !(kv.contains("slab") || kv.contains("stairs") ||
                    kv.contains("button") || kv.contains("pressure_plate") || kv.endsWith("_wall"));
            if (isNotSubBlockVariant)
            {
                boolean isBaseStoneOrColumn = !(kv.contains("smooth") || kv.contains("polished") ||
                        kv.contains("brick") || kv.contains("tile") || kv.contains("cobble") ||
                        kv.contains("cut"));
                if ((kv.contains("basalt") && isBaseStoneOrColumn ||
                        (kv.contains("deepslate") && isBaseStoneOrColumn)) ||
                        (kv.contains("column")))
                {
                    Supplier<Block> b = Registrar.regBlock(kv, () -> new RotatedPillarBlock(props));
                    Supplier<Item>  i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
                    return new GenericBlockItemCoupling(b, i);
                }
            }

            Supplier<Block> b = Registrar.regBlock(kv, () -> new Block(props));
            Supplier<Item> i = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
            return new GenericBlockItemCoupling(b, i);
        }
    }
}
