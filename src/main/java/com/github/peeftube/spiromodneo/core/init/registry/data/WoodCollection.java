package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.init.content.blocks.WoodBlock;
import com.github.peeftube.spiromodneo.util.wood.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record WoodCollection(WoodMaterial type, WoodData bulkData) implements WoodUtilities
{
    public static List<WoodCollection> WOOD_COLLECTIONS = new ArrayList<>();

    public static WoodCollection registerCollection(WoodMaterial type)
    { return registerCollection(type, 0); }

    public static WoodCollection registerCollection(WoodMaterial type, int li)
    {
        Map<Boolean, Tappable> tappableMapping = new HashMap<>();
        tappableMapping.put(false, null); // We'll be checking if the boolean is true or false later.

        return registerCollection(type, li, tappableMapping);
    }

    public static WoodCollection registerCollection(WoodMaterial type, Map<Boolean, Tappable> isTappable)
    { return registerCollection(type, 0, isTappable); }

    public static WoodCollection registerCollection(WoodMaterial type, int li, Map<Boolean, Tappable> isTappable)
    {
        WoodData data = WoodUtilities.populate(type, li, isTappable);
        WoodCollection collection = new WoodCollection(type, data);
        WOOD_COLLECTIONS.add(collection); return collection;
    }

    public Supplier<? extends Block> getBaseLog()
    { return this.bulkData.livingWood().get(LivingWoodBlockType.LOG).getBlock(); }
    public Supplier<? extends Block> getBaseLeaves()
    { return this.bulkData.livingWood().get(LivingWoodBlockType.LEAVES).getBlock(); }
    public Supplier<? extends Block> getBaseSapling()
    { return this.bulkData.livingWood().get(LivingWoodBlockType.SAPLING).getBlock(); }

    public Supplier<? extends Block> getChest()
    { return this.bulkData.manufacturables().get(ManufacturedWoodType.CHEST).getBlock(); }
    public Supplier<? extends Block> getTrappedChest()
    { return this.bulkData.manufacturables().get(ManufacturedWoodType.TRAPPED_CHEST).getBlock(); }
    public Supplier<? extends Block> getBarrel()
    { return this.bulkData.manufacturables().get(ManufacturedWoodType.BARREL).getBlock(); }

    public Block getChestAsBlock() { return this.getChest().get(); }
    public Block getTrappedChestAsBlock() { return this.getTrappedChest().get(); }
    public Block getBarrelAsBlock() { return this.getBarrel().get(); }

    public BlockState getStrippedOrElseOriginal(BlockState toStrip)
    {
        if (toStrip.is(this.getBaseLog().get()))
        { return this.bulkData.livingWood().get(LivingWoodBlockType.STRIPPED_LOG).getBlock().get()
                .defaultBlockState().setValue(WoodBlock.AXIS, toStrip.getValue(WoodBlock.AXIS)); }

        if (toStrip.is(this.bulkData.livingWood().get(LivingWoodBlockType.WOOD).getBlock().get()))
        { return this.bulkData.livingWood().get(LivingWoodBlockType.STRIPPED_WOOD).getBlock().get()
                .defaultBlockState().setValue(WoodBlock.AXIS, toStrip.getValue(WoodBlock.AXIS)); }

        return toStrip;
    }

    public Block getSignAsBlock()
    { return this.bulkData.signs().get(SignType.BASIC).getSign().get(); }
    public Block getWallSignAsBlock()
    { return this.bulkData.signs().get(SignType.BASIC).getWallSign().get(); }
    public Block getHangingSignAsBlock()
    { return this.bulkData.signs().get(SignType.HANGING).getSign().get(); }
    public Block getWallHangingSignAsBlock()
    { return this.bulkData.signs().get(SignType.HANGING).getWallSign().get(); }

    public boolean isVariableType()
    { return this.bulkData().isVariableWoodType(); }
}
