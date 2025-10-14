package com.github.peeftube.spiromodneo.util.metal;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.MetalMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface MetalUtilities
{
    static IngotCoupling getIngotData(MetalMaterial material, int li)
    {
        Map<MetalMaterial, IngotCoupling> presets = new HashMap<>();
        presets.put(MetalMaterial.IRON, new IngotCoupling(() -> Items.IRON_INGOT,
                new MetalBlockCoupling(() -> Blocks.IRON_BLOCK, () -> Items.IRON_BLOCK)));
        presets.put(MetalMaterial.COPPER, new IngotCoupling(() -> Items.COPPER_INGOT,
                new MetalBlockCoupling(() -> Blocks.COPPER_BLOCK, () -> Items.COPPER_BLOCK)));
        presets.put(MetalMaterial.GOLD, new IngotCoupling(() -> Items.GOLD_INGOT,
                new MetalBlockCoupling(() -> Blocks.GOLD_BLOCK, () -> Items.GOLD_BLOCK)));
        presets.put(MetalMaterial.NETHERITE, new IngotCoupling(() -> Items.NETHERITE_INGOT,
                new MetalBlockCoupling(() -> Blocks.NETHERITE_BLOCK, () -> Items.NETHERITE_BLOCK)));

        if (presets.containsKey(material)) { return presets.get(material); }
        else
        {
            MetalBlockCoupling c;
            Supplier<Block> b = Registrar.regBlock(material.get() + "_block",
                    () -> new Block(Registrar.RAW_ORE.get().lightLevel(s -> li)));
            Supplier<Item> bi = Registrar.regSimpleBlockItem((DeferredBlock<Block>) b);
            c = new MetalBlockCoupling(b, bi);

            return new IngotCoupling(Registrar.ITEMS.register(
                    material.get() + "_ingot", () -> new Item(new Item.Properties())), c);
        }
    }
}
