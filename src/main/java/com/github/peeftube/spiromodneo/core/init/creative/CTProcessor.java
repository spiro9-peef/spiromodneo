package com.github.peeftube.spiromodneo.core.init.creative;

import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.util.ore.OreCoupling;
import com.github.peeftube.spiromodneo.util.stone.StoneBlockType;
import com.github.peeftube.spiromodneo.util.stone.StoneSubBlockType;
import com.github.peeftube.spiromodneo.util.stone.StoneVariantType;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import com.github.peeftube.spiromodneo.util.wood.PlankBlockSubType;
import com.github.peeftube.spiromodneo.util.wood.SignType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashSet;
import java.util.Set;

public class CTProcessor
{
    public static Set<ItemStack> precacheMineralsTab()
    {
        Set<ItemStack> output = new LinkedHashSet<>();

        // Run over all ore sets.
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS)
        {
            for (OreCoupling c : ore.getBulkData().values())
            {
                ItemStack iS = c.getItem().get().getDefaultInstance();
                output.add(iS);
            }

            ItemStack iSBlock = ore.getRawOre().getCoupling().getItem().get().getDefaultInstance();
            ItemStack iSItem = ore.getRawOre().getRawItem().get().getDefaultInstance();
            output.add(iSBlock); output.add(iSItem);
        }

        return output;
    }

    public static Set<ItemStack> precacheWoodsTab()
    {
        Set<ItemStack> output = new LinkedHashSet<>();

        // Run over all wood sets.
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS)
        {
            for (LivingWoodBlockType t : LivingWoodBlockType.values())
            {
                if (wood.bulkData().livingWood().get(t) != null)
                { output.add(wood.bulkData().livingWood().get(t).getItem().get().getDefaultInstance()); }
            }

            if (wood.isVariableType())
            {
                for (VariableWoodCollection vWood : VariableWoodCollection.VARIABLE_WOOD_COLLECTIONS)
                {
                    if (vWood.material().getWood().equals(wood))
                    {
                        output.add(vWood.leaves().getItem().get().getDefaultInstance());
                        output.add(vWood.sapling().getItem().get().getDefaultInstance());
                    }
                }
            }

            for (PlankBlockSubType p : PlankBlockSubType.values())
            {
                if (wood.bulkData().planks().get(p) != null)
                { output.add(wood.bulkData().planks().get(p).getItem().get().getDefaultInstance()); }
            }

            for (ManufacturedWoodType m : ManufacturedWoodType.values())
            {
                if (wood.bulkData().manufacturables().get(m) != null)
                { output.add(wood.bulkData().manufacturables().get(m).getItem().get().getDefaultInstance()); }
            }

            for (SignType s : SignType.values())
            {
                if (wood.bulkData().signs().get(s) != null)
                { output.add(wood.bulkData().signs().get(s).getItem().get().getDefaultInstance()); }
            }
        }

        return output;
    }

    public static Set<ItemStack> precacheStoneTab()
    {
        Set<ItemStack> output = new LinkedHashSet<>();

        // Run over all stone sets.
        for (StoneCollection stone : StoneCollection.STONE_COLLECTIONS)
        {
            for (StoneBlockType k0 : StoneBlockType.values())
            {
                for (StoneVariantType k1 : StoneVariantType.values())
                {
                    for (StoneSubBlockType k2 : StoneSubBlockType.values())
                    {
                        boolean exists = stone.bulkData().doesCouplingExistForKeys(k0, k1, k2);
                        if (exists) { output.add(stone.bulkData().getCouplingForKeys(k0, k1, k2)
                                .getItem().get().getDefaultInstance()); }
                    }
                }
            }
        }

        return output;
    }
}