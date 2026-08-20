package com.github.peeftube.spiromodneo.datagen.modules.models;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.blocks.ExtensibleBerryBushBlock;
import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.util.DataCheckResult;
import com.github.peeftube.spiromodneo.util.RLUtility;
import com.github.peeftube.spiromodneo.util.equipment.ArmorSet;
import com.github.peeftube.spiromodneo.util.equipment.ToolSet;
import com.github.peeftube.spiromodneo.util.moss.MossType;
import com.github.peeftube.spiromodneo.util.ore.OreCoupling;
import com.github.peeftube.spiromodneo.util.stone.*;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import com.github.peeftube.spiromodneo.util.wood.PlankBlockSubType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;
import java.util.function.Supplier;

import static com.github.peeftube.spiromodneo.util.RLUtility.makeRL;
import static com.github.peeftube.spiromodneo.util.stone.StoneSetPresets.getPresets;

public class ItemModelDataProv extends ItemModelProvider
{
    public ItemModelDataProv(PackOutput output, ExistingFileHelper eFH)
    {
        super(output, SpiroMod.MOD_ID, eFH);
    }

    @Override
    protected void registerModels()
    {
        // ============================================================================================================
        // Normal items
        itemParser(Registrar.SINEW);
        itemParser(Registrar.PLANT_FIBRE);
        itemParser(Registrar.SMALL_STONE);
        itemParser(Registrar.SHARPENED_STICK);
        itemParser(Registrar.BUNDLE_OF_SHARP_STICKS);
        itemParser(Registrar.WOODEN_TOOL_GRAFTING_KIT);
        itemParser(Registrar.CAST_IRON_MIXTURE);
        itemParser(Registrar.CAST_IRON);
        itemParser(Registrar.STEEL_MIXTURE);
        itemParser(Registrar.WEAK_STEEL_MIXTURE);
        itemParser(Registrar.CRUSHED_CARBON);
        itemParser(Registrar.CAOUTCHOUC);
        itemParser(Registrar.MAPLE_SAP);
        itemParser(Registrar.RUBBER_PRECURSOR);
        itemParser(Registrar.NATURAL_RUBBER);
        itemParser(Registrar.COPPER_WIRE);
        itemParser(Registrar.SHIELDED_COPPER_WIRE);
        itemParser(Registrar.IRON_STICK);
        itemParser(Registrar.LEAD_STICK);
        itemParser(Registrar.STEEL_ROD);
        for (EquipmentCollection equip : EquipmentCollection.EQUIP_COLLECTIONS) { equipmentSetDesign(equip); }

        // ============================================================================================================
        // Special
        ExtensibleBerryBushBlock ebPhantom = (ExtensibleBerryBushBlock) Registrar.PHANTOM_BERRY_BUSH.get();
        itemParser(ebPhantom.getBerry());

        // ============================================================================================================
        // Block items
        for (MetalCollection metal : MetalCollection.METAL_COLLECTIONS) { metalSetDesign(metal); }
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreSetDesign(ore); }
        for (StoneCollection stone : StoneCollection.STONE_COLLECTIONS) { stoneSetDesign(stone); }
        for (GrassLikeCollection grass : GrassLikeCollection.GRASS_COLLECTIONS) { grassSetDesign(grass); }
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS) { woodSetDesign(wood); }
        for (VariableWoodCollection w : VariableWoodCollection.VARIABLE_WOOD_COLLECTIONS) { variableWoodSetDesign(w); }
        for (MossCollection moss : MossCollection.MOSS_COLLECTIONS) { mossSetDesign(moss); }
        blockParser(Registrar.MANUAL_CRUSHER_ITEM);
        itemParser(Registrar.TAPPER_ITEM); // This is parsed as an item because it has an item texture.
    }

    private void mossSetDesign(MossCollection set)
    {
        if (!set.material().equals(MossMaterial.MOSS))
        {
            couplingParser((DeferredBlock<Block>) set.bulkData().get(MossType.MOSS_BLOCK).getBlock(),
                    (DeferredItem<Item>) set.bulkData().get(MossType.MOSS_BLOCK).getItem());
            couplingParser((DeferredBlock<Block>) set.bulkData().get(MossType.MOSS_CARPET).getBlock(),
                    (DeferredItem<Item>) set.bulkData().get(MossType.MOSS_CARPET).getItem());
        }
    }

    private void variableWoodSetDesign(VariableWoodCollection set)
    {
        couplingParser((DeferredBlock<Block>) set.leaves().getBlock(),
                (DeferredItem<Item>) set.leaves().getItem());
        couplingParser((DeferredBlock<Block>) set.sapling().getBlock(),
                (DeferredItem<Item>) set.sapling().getItem());
    }

    private void woodSetDesign(WoodCollection set)
    {
        for (LivingWoodBlockType t : LivingWoodBlockType.values())
        {
            if (set.bulkData().livingWood().get(t) != null)
            {
                boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().livingWood().get(t).getBlock().get())
                                                           .getNamespace().equalsIgnoreCase("minecraft");

                if (!isVanilla)
                { couplingParser((DeferredBlock<Block>) set.bulkData().livingWood().get(t).getBlock(),
                        (DeferredItem<Item>) set.bulkData().livingWood().get(t).getItem()); }
            }
        }

        for (PlankBlockSubType t : PlankBlockSubType.values())
        {
            if (set.bulkData().planks().get(t) != null)
            {
                boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().planks().get(t).getBlock().get())
                                                           .getNamespace().equalsIgnoreCase("minecraft");

                if (!isVanilla)
                { couplingParser((DeferredBlock<Block>) set.bulkData().planks().get(t).getBlock(),
                        (DeferredItem<Item>) set.bulkData().planks().get(t).getItem()); }
            }
        }

        for (ManufacturedWoodType t : ManufacturedWoodType.values())
        {
            if (set.bulkData().manufacturables().get(t) != null && t != ManufacturedWoodType.CHEST &&
            t != ManufacturedWoodType.TRAPPED_CHEST)
            {
                boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().manufacturables().get(t).getBlock().get())
                                                           .getNamespace().equalsIgnoreCase("minecraft");

                if (!isVanilla)
                { couplingParser((DeferredBlock<Block>) set.bulkData().manufacturables().get(t).getBlock(),
                        (DeferredItem<Item>) set.bulkData().manufacturables().get(t).getItem()); }
            }
        }
    }

    private void grassSetDesign(GrassLikeCollection set)
    {
        for (Soil s : Soil.values())
        {
            boolean sanityCheckDirt =
                    (!(set.type() == GrassLike.GRASS || set.type() == GrassLike.MYCELIUM));
            boolean sanityCheckNetherrack =
                    (!(set.type() == GrassLike.CRIMSON_NYLIUM || set.type() == GrassLike.WARPED_NYLIUM));
            boolean sanityCheck =
                    s == Soil.DIRT ? sanityCheckDirt : s != Soil.NETHERRACK || sanityCheckNetherrack;

            if (sanityCheck) blockParser((DeferredItem<Item>) set.bulkData().get(s).getItem());
        }
    }

    protected void equipmentSetDesign(EquipmentCollection set)
    {
        DataCheckResult toolCheck = set.checkIfNullThenPass(set.bulkData().getTools());
        DataCheckResult armorCheck = set.checkIfNullThenPass(set.bulkData().getArmor());
        DataCheckResult horseCheck = set.checkIfNullThenPass(set.bulkData().getHorseArmor());

        ToolSet tools;
        ArmorSet armor;
        DeferredItem<Item> horseArmor;

        if (toolCheck.getResult())
        {
            tools = set.bulkData().getTools();
            itemParser((DeferredItem<Item>) tools.getSword(), 1);
            itemParser((DeferredItem<Item>) tools.getShovel(), 1);
            itemParser((DeferredItem<Item>) tools.getHoe(), 1);
            itemParser((DeferredItem<Item>) tools.getAxe(), 1);
            itemParser((DeferredItem<Item>) tools.getPickaxe(), 1);
        }

        if (armorCheck.getResult())
        {
            armor = set.bulkData().getArmor();
            itemParser((DeferredItem<Item>) armor.getHelmet());
            itemParser((DeferredItem<Item>) armor.getChestplate());
            itemParser((DeferredItem<Item>) armor.getLeggings());
            itemParser((DeferredItem<Item>) armor.getBoots());
        }

        if (horseCheck.getResult())
        {
            horseArmor = (DeferredItem<Item>) set.bulkData().getHorseArmor();
            itemParser((DeferredItem<Item>) horseArmor);
        }
    }

    protected void metalSetDesign(MetalCollection set)
    {
        boolean ignoreIngotBlocks = false; // For ignoring default ingot and metallic block combinations

        MetalMaterial material = set.getMat();

        if (material == MetalMaterial.IRON || material == MetalMaterial.GOLD || material == MetalMaterial.COPPER
                || material == MetalMaterial.NETHERITE )
        { ignoreIngotBlocks = true; }

        if (!ignoreIngotBlocks)
        {
            // This set does not exist already. Vanilla metals are very thorough on this front.
            // Make this code easier to read, PLEASE..
            Supplier<Item> i = set.ingotData().getIngot();
            Supplier<Item> b = set.ingotData().getMetal().getItem();

            // This part is extremely easy, fortunately.
            blockParser((DeferredItem<Item>) b);
            itemParser((DeferredItem<Item>) i);
        }
    }

    private void stoneSetDesign(StoneCollection set)
    {
        StoneData     data = set.bulkData();
        StoneMaterial mat  = set.material();

        for (StoneBlockType k0 : StoneBlockType.values())
        {
            for (StoneVariantType k1 : StoneVariantType.values())
            {
                for (StoneSubBlockType k2 : StoneSubBlockType.values())
                {
                    boolean available = data.doesCouplingExistForKeys(k0, k1, k2);
                    String  baseKey   = ExistingStoneCouplings.getKey(set.material(), k0, k1, StoneSubBlockType.DEFAULT);
                    String  key       = ExistingStoneCouplings.getKey(set.material(), k0, k1, k2);

                    boolean isDefault      = k2 == StoneSubBlockType.DEFAULT;
                    boolean textureIsStock = getPresets().containsKey(isDefault ? key : baseKey);
                    String  ns             = getPresets().containsKey(baseKey) ? "minecraft" : SpiroMod.MOD_ID;

                    if (available && !getPresets().containsKey(key))
                    { couplingParser((DeferredBlock<Block>) set.bulkData().getCouplingForKeys(k0, k1, k2).getBlock(),
                            (DeferredItem<Item>) set.bulkData().getCouplingForKeys(k0, k1, k2).getItem()); }
                }
            }
        }
    }

    // Ore BlockItem handler subroutine
    // Copied from BlockstateDataProv.java
    protected void oreSetDesign(OreCollection set)
    {
        // Flags for what we should ignore.
        boolean ignoreStone  = false; // For ignoring default stone, assumes true for deepslate as well
        boolean ignoreNether = false; // For ignoring default Netherrack ore
        // NOTE: these two may be used in an OR statement to determine if this is a vanilla block. If so,
        //       code should ignore the raw ore blocks and raw ore item.

        // Prepare set data.
        OreMaterial                 material = set.getMat();
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
            { continue; } // Do nothing, we're using a combination which already has a BlockItem

            // Make this code easier to read, PLEASE..
            Supplier<Item> i = bulkData.get(s).item();

            // This part is extremely easy, fortunately.
            blockParser((DeferredItem<Item>) i);
        }

        // Raw block and item; assume not vanilla.
        if (!(ignoreStone || ignoreNether))
        {
            // Make this code easier to read, PLEASE..
            Supplier<Item> b = set.getRawOre().getCoupling().getItem();
            Supplier<Item> i = set.getRawOre().getRawItem();

            // This part is extremely easy, fortunately.
            blockParser((DeferredItem<Item>) b);
            itemParser((DeferredItem<Item>) i);
        }
    }

    //
    protected void couplingParser(DeferredBlock<Block> block, DeferredItem<Item> item)
    {
        String rawPath = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        String path = "block/" + rawPath;
        boolean modelExists = this.existingFileHelper.exists(makeRL(path), ModelProvider.MODEL);
        boolean isNotSpecialCase = !(path.contains("_button") || path.contains("_wall") || path.contains("_door"));
        if (modelExists && isNotSpecialCase) { blockParser(item); }
        else
        {
            boolean isColumnCheck = this.existingFileHelper.exists(makeRL(path + "_y"), ModelProvider.MODEL);
            boolean isInventoryCheck = this.existingFileHelper.exists(makeRL(path + "_inv"), ModelProvider.MODEL);

            if (isColumnCheck) { withExistingParent(rawPath, makeRL(path + "_y")); }
            if (isInventoryCheck) { withExistingParent(rawPath, makeRL(path + "_inv")); }
        }
    }

    // Block item model creation subroutine
    protected void blockParser(DeferredItem<Item> item)
    {
        String path = "block/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath();
        withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), modLoc(path));
    }

    private ItemModelBuilder itemParser(DeferredItem<Item> item)
    { return itemParser(item, 0, item.getId().getPath()); }

    private ItemModelBuilder itemParser(DeferredItem<Item> item, int type)
    { return itemParser(item, type, item.getId().getPath()); }

    private ItemModelBuilder itemParser(DeferredItem<Item> item, int type, String imageName)
    {
        switch(type)
        {
            case 1 ->
            {
                // Handheld item.
                return withExistingParent(item.getId().getPath(),
                        RLUtility.invokeRL("minecraft:item/handheld")).texture("layer0",
                        makeRL("item/" + imageName));
            }
            default ->
            {
                // Assume basic type.
                return withExistingParent(item.getId().getPath(),
                        RLUtility.invokeRL("minecraft:item/generated")).texture("layer0",
                        makeRL("item/" + imageName));
            }
        }
    }
}
