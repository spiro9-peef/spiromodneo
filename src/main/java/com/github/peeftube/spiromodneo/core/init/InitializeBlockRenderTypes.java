package com.github.peeftube.spiromodneo.core.init;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.content.blocks.TappableWoodBlock;
import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.util.ore.BaseStone;
import com.github.peeftube.spiromodneo.util.ore.OreCoupling;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;

import java.util.Map;
import java.util.function.Supplier;

public class InitializeBlockRenderTypes
{
    public static void go()
    {
        // Ore handling.
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreSettings(ore); }

        // Grass handling.
        for (GrassLikeCollection grass : GrassLikeCollection.GRASS_COLLECTIONS) { grassSettings(grass); }

        // Wood set handling.
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS) { woodSettings(wood); }
        for (VariableWoodCollection w : VariableWoodCollection.VARIABLE_WOOD_COLLECTIONS) { variableWoodSettings(w); }

        // Berry bushes?
        ItemBlockRenderTypes.setRenderLayer(Registrar.PHANTOM_BERRY_BUSH.get(),
                ChunkRenderTypeSet.of(RenderType.CUTOUT));
    }

    protected static void variableWoodSettings(VariableWoodCollection set)
    {
        ItemBlockRenderTypes.setRenderLayer(
                set.leaves().getBlock().get(),
                ChunkRenderTypeSet.of(RenderType.TRANSLUCENT));
        ItemBlockRenderTypes.setRenderLayer(
                set.sapling().getBlock().get(),
                ChunkRenderTypeSet.of(RenderType.TRANSLUCENT));
    }

    protected static void woodSettings(WoodCollection set)
    {
        for (LivingWoodBlockType t : LivingWoodBlockType.values())
        {
            if (set.bulkData().livingWood().get(t) != null)
            {
                boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().livingWood().get(t).getBlock().get())
                                                           .getNamespace().equalsIgnoreCase("minecraft");
                if (!isVanilla && (t == LivingWoodBlockType.LEAVES || t == LivingWoodBlockType.ROOTS ||
                        t == LivingWoodBlockType.SAPLING))
                {
                    ItemBlockRenderTypes.setRenderLayer(
                            set.bulkData().livingWood().get(t).getBlock().get(),
                            ChunkRenderTypeSet.of(RenderType.TRANSLUCENT));
                }

                if (!isVanilla && (t == LivingWoodBlockType.LOG || t == LivingWoodBlockType.STRIPPED_LOG ||
                        t == LivingWoodBlockType.WOOD || t == LivingWoodBlockType.STRIPPED_WOOD))
                {
                    if (set.bulkData().livingWood().get(t).getBlock().get() instanceof TappableWoodBlock)
                    {
                        ItemBlockRenderTypes.setRenderLayer(set.bulkData().livingWood().get(t).getBlock().get(),
                                RenderType.translucent());
                    }
                }
            }
        }

        for (ManufacturedWoodType t : ManufacturedWoodType.values())
        {
            boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().manufacturables().get(t).getBlock().get())
                                                       .getNamespace().equalsIgnoreCase("minecraft");
            if (!isVanilla && (t == ManufacturedWoodType.DOOR || t == ManufacturedWoodType.TRAPDOOR))
            {
                ItemBlockRenderTypes.setRenderLayer(
                        set.bulkData().manufacturables().get(t).getBlock().get(),
                        ChunkRenderTypeSet.of(RenderType.TRANSLUCENT));
            }
        }
    }

    protected static void grassSettings(GrassLikeCollection set)
    {
        for (Soil s : Soil.values())
        {
            boolean sanityCheckDirt =
                    (!(set.type() == GrassLike.GRASS || set.type() == GrassLike.MYCELIUM));
            boolean sanityCheckNetherrack =
                    (!(set.type() == GrassLike.CRIMSON_NYLIUM || set.type() == GrassLike.WARPED_NYLIUM));
            boolean sanityCheck =
                    s == Soil.DIRT ? sanityCheckDirt : s != Soil.NETHERRACK || sanityCheckNetherrack;

            // I don't know why, I don't want to know why, I shouldn't have to know why, but without this
            // logger call this code doesn't seem to want to work properly.
            // Oracle pls fix
            SpiroMod.LOGGER.info("RUNNING GRASS: " + set.type().getName());

            if (sanityCheck) ItemBlockRenderTypes.setRenderLayer(set.bulkData().get(s).getBlock().get(),
                    ChunkRenderTypeSet.of(RenderType.solid(), RenderType.translucent()));
        }
    }

    protected static void oreSettings(OreCollection set)
    {
        // Flags for what we should ignore.
        boolean ignoreStone = false; // For ignoring default stone, assumes true for deepslate as well
        boolean ignoreNether = false; // For ignoring default Netherrack ore
        // DO NOT include packed ore blocks in this unless you want a headache.

        // Prepare set data.
        OreMaterial                     material = set.getMat();
        Map<StoneMaterial, OreCoupling> bulkData = set.getBulkData();

        if (material == OreMaterial.COAL || material == OreMaterial.IRON || material == OreMaterial.COPPER
                || material == OreMaterial.GOLD || material == OreMaterial.LAPIS || material == OreMaterial.REDSTONE
                || material == OreMaterial.EMERALD || material == OreMaterial.DIAMOND)
        { ignoreStone = true; }

        if (material == OreMaterial.GOLD || material == OreMaterial.QUARTZ)
        { ignoreNether = true; }

        for (StoneMaterial s : StoneMaterial.values())
        {
            if (((s == StoneMaterial.STONE || s == StoneMaterial.DEEPSLATE) && ignoreStone)
                    || ((s == StoneMaterial.NETHERRACK) && ignoreNether))
            { continue; } // Do nothing, we're using a material which already uses this combination...

            // Make this code easier to read, PLEASE..
            Block b = bulkData.get(s).block().get();

            // I don't know why, I don't want to know why, I shouldn't have to know why, but without this
            // logger call this code doesn't seem to want to work properly.
            // Oracle pls fix
            SpiroMod.LOGGER.info("RUNNING ORE: " + s.get().toUpperCase() + material.get().toUpperCase());

            // Change render type status.
            ItemBlockRenderTypes.setRenderLayer(b, ChunkRenderTypeSet.of(RenderType.solid(), RenderType.translucent()));
        }
    }
}
