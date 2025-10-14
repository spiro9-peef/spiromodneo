package com.github.peeftube.spiromodneo.datagen.modules.loot.subprov;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.util.ore.OreCoupling;
import com.github.peeftube.spiromodneo.util.stone.*;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import com.github.peeftube.spiromodneo.util.wood.PlankBlockSubType;
import com.github.peeftube.spiromodneo.util.wood.SignType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class BlockLootTables extends BlockLootSubProvider
{
    public BlockLootTables(HolderLookup.Provider reg)
    { super(Set.of(), FeatureFlags.REGISTRY.allFlags(), reg); }

    @Override
    protected void generate()
    {
        // Simple drop-self tables
        for (MetalCollection metal : MetalCollection.METAL_COLLECTIONS) { metalTables(metal); }
        for (GrassLikeCollection grass : GrassLikeCollection.GRASS_COLLECTIONS) { grassLikeTables(grass); }
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS) { woodTables(wood); }
        dropSelf(Registrar.MANUAL_CRUSHER.get());
        dropSelf(Registrar.TAPPER.get());

        // Stone tables
        for (StoneCollection stone : StoneCollection.STONE_COLLECTIONS) { stoneTables(stone); }

        // Ore tables
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreTables(ore); }
    }

    private void woodTables(WoodCollection set)
    {
        WoodMaterial mat = set.type();
        boolean isVanillaWood = mat == WoodMaterial.OAK || mat == WoodMaterial.BIRCH || mat == WoodMaterial.SPRUCE ||
                mat == WoodMaterial.JUNGLE || mat == WoodMaterial.ACACIA || mat == WoodMaterial.DARK_OAK ||
                mat == WoodMaterial.CHERRY || mat == WoodMaterial.MANGROVE || mat == WoodMaterial.CRIMSON_FUNGUS ||
                mat == WoodMaterial.WARPED_FUNGUS;

        for (LivingWoodBlockType t : LivingWoodBlockType.values())
        {
            if (!isVanillaWood)
            {
                if (t == LivingWoodBlockType.LEAVES)
                { add(set.getBaseLeaves().get(),
                        createLeavesDrops(set.getBaseLeaves().get(),
                                set.getBaseSapling().get(), NORMAL_LEAVES_SAPLING_CHANCES)); }
                else
                { if ((t != LivingWoodBlockType.ROOTS && t != LivingWoodBlockType.ROOTS_WITH_MUD)
                            || mat.isLikeMangroves()) dropSelf(set.bulkData().livingWood().get(t).getBlock().get()); }
            }
        }

        for (PlankBlockSubType t : PlankBlockSubType.values())
        { if (!isVanillaWood) { dropSelf(set.bulkData().planks().get(t).getBlock().get()); } }

        for (ManufacturedWoodType t : ManufacturedWoodType.values())
        {
            boolean isGenericCraftingTableOrChest = mat == WoodMaterial.OAK;
            boolean isGenericBarrel = mat == WoodMaterial.SPRUCE;

            switch(t)
            {
                case BARREL ->
                { if (!isGenericBarrel) { dropSelf(set.bulkData().manufacturables().get(t).getBlock().get()); } }
                case CRAFTING_TABLE, CHEST, TRAPPED_CHEST ->
                { if (!isGenericCraftingTableOrChest) { dropSelf(set.bulkData().manufacturables().get(t).getBlock().get()); } }
                default ->
                { if (!isVanillaWood) { dropSelf(set.bulkData().manufacturables().get(t).getBlock().get()); } }
            }
        }

        for (SignType t : SignType.values())
        {
            if (!isVanillaWood)
            {
                dropOther(set.bulkData().signs().get(t).getSign().get(),
                        set.bulkData().signs().get(t).getItem().get());
                dropOther(set.bulkData().signs().get(t).getWallSign().get(),
                        set.bulkData().signs().get(t).getItem().get());
            }
        }
    }

    protected void grassLikeTables(GrassLikeCollection set)
    {
        for (Soil s : Soil.values())
        {
            boolean sanityCheckDirt =
                    (!(set.type() == GrassLike.GRASS || set.type() == GrassLike.MYCELIUM));
            boolean sanityCheckNetherrack =
                    (!(set.type() == GrassLike.CRIMSON_NYLIUM || set.type() == GrassLike.WARPED_NYLIUM));
            boolean sanityCheck =
                    s == Soil.DIRT ? sanityCheckDirt : s != Soil.NETHERRACK || sanityCheckNetherrack;

            Supplier<Block> soilToDrop = s.getSoil();
            if (sanityCheck) dropOther(set.bulkData().get(s).getBlock().get(), soilToDrop.get());
        }
    }

    protected void stoneTables(StoneCollection set)
    {
        for (StoneBlockType type0 : StoneBlockType.values())
        {
            switch(type0)
            {
                case BASE ->
                {
                    for (StoneVariantType type1 : StoneVariantType.values())
                    {
                        if (set.bulkData().bulkData().get(type0).containsKey(type1))
                        {
                            switch(type1)
                            {
                                default ->
                                {
                                    for (StoneSubBlockType type2 : StoneSubBlockType.values())
                                    {
                                        if (set.bulkData().bulkData().get(type0).get(type1).containsKey(type2)
                                         && !StoneSetPresets.getPresets()
                                        .containsKey(ExistingStoneCouplings
                                                .getKey(set.material(), type0, type1, type2)))
                                        {
                                            Block self = set.bulkData().getCouplingForKeys(
                                                    type0, type1, type2).getBlock().get();

                                            switch (type2)
                                            {
                                                case DEFAULT -> add(set.getBaseStone().get(), stoneDropTable01(
                                                        set.getBaseStone().get(), set.getCobble().get()));

                                                case GROUND_STONES -> add(self, groundStoneTable(self));

                                                default -> dropSelf(self);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                default ->
                {
                    for (StoneVariantType type1 : StoneVariantType.values())
                    {
                        if (set.bulkData().bulkData().get(type0).containsKey(type1))
                        {
                            switch(type1)
                            {
                                default ->
                                {
                                    for (StoneSubBlockType type2 : StoneSubBlockType.values())
                                    {
                                        if (set.bulkData().bulkData().get(type0).get(type1).containsKey(type2)
                                         && !StoneSetPresets.getPresets()
                                        .containsKey(ExistingStoneCouplings
                                                .getKey(set.material(), type0, type1, type2)))
                                        {
                                            Block self = set.bulkData().getCouplingForKeys(
                                                    type0, type1, type2).getBlock().get();

                                            switch(type2)
                                            {
                                                case GROUND_STONES -> add(self, groundStoneTable(self));
                                                default -> dropSelf(self);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void metalTables(MetalCollection set)
    {
        boolean ignoreIngotBlocks = false; // For ignoring default ingot and metallic block combinations

        MetalMaterial material = set.getMat();

        if (material == MetalMaterial.IRON || material == MetalMaterial.GOLD || material == MetalMaterial.COPPER
                || material == MetalMaterial.NETHERITE )
        { ignoreIngotBlocks = true; }

        if (!ignoreIngotBlocks)
        {
            // Make this code easier to read, PLEASE..
            Block self = set.ingotData().getMetal().getBlock().get();
            dropSelf(self);
        }
    }

    protected void oreTables(OreCollection set)
    {
        // Flags for what we should ignore.
        boolean ignoreStone = false; // For ignoring default stone, assumes true for deepslate as well
        boolean ignoreNether = false; // For ignoring default Netherrack ore
        // NOTE: these two may be used in an OR statement to determine if this is a vanilla block. If so,
        //       code should ignore the raw ore blocks.

        // Prepare set data.
        OreMaterial                     material = set.getMat();
        Map<StoneMaterial, OreCoupling> bulkData = set.getBulkData();

        if (material == OreMaterial.COAL || material == OreMaterial.IRON || material == OreMaterial.COPPER
                || material == OreMaterial.GOLD || material == OreMaterial.LAPIS || material == OreMaterial.REDSTONE
                || material == OreMaterial.EMERALD || material == OreMaterial.DIAMOND)
        { ignoreStone = true; }

        if (material == OreMaterial.GOLD || material == OreMaterial.QUARTZ)
        { ignoreNether = true; }

        for (StoneMaterial s : bulkData.keySet())
        {
            if (((s == StoneMaterial.STONE || s == StoneMaterial.DEEPSLATE) && ignoreStone)
                    || ((s == StoneMaterial.NETHERRACK) && ignoreNether))
            { continue; } // Do nothing, we're using a material which already uses this combination,
                          // you'll want to use loot modifiers instead in this case.

            // Make this code easier to read, PLEASE..
            Block self = bulkData.get(s).getBlock().get();
            Item oreToDrop = set.getRawOre().getRawItem().get();
            NumberProvider oreToDropAmounts = set.getDropCount();

            add(self, oreTable01(self, s, oreToDrop, oreToDropAmounts));
        }

        // For block of this ore, assuming that it doesn't exist in vanilla already.
        if (!(ignoreStone || ignoreNether)) { dropSelf(set.getRawOre().getCoupling().getBlock().get()); };
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    { return Registrar.BLOCKS.getEntries().stream().<Block>map(DeferredHolder::value).toList(); }

    protected LootTable.Builder stoneDropTable01(Block b0, Block b1)
    {
        HolderLookup.RegistryLookup<Enchantment> regLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        LootTable.Builder builder = this.createSilkTouchDispatchTable(b0,
                this.applyExplosionDecay(b0, LootItem.lootTableItem(b1))
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                    .apply(ApplyBonusCount.addOreBonusCount(regLookup.getOrThrow(Enchantments.FORTUNE))));

        return builder;
    }

    protected LootTable.Builder groundStoneTable(Block b)
    {
        HolderLookup.RegistryLookup<Enchantment> regLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createSilkTouchDispatchTable(b,
                this.applyExplosionDecay(b, LootItem.lootTableItem(Registrar.SMALL_STONE.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                        .apply(ApplyBonusCount.addOreBonusCount(regLookup.getOrThrow(Enchantments.FORTUNE))));
    }

    protected LootTable.Builder oreTable01(Block b0, StoneMaterial s, Item item, NumberProvider dropAmtRange)
    {
        HolderLookup.RegistryLookup<Enchantment> regLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        LootTable.Builder builder = this.createSilkTouchDispatchTable(b0,
                this.applyExplosionDecay(b0, LootItem.lootTableItem(item))
                        .apply(SetItemCountFunction.setCount(dropAmtRange))
                        .apply(ApplyBonusCount.addOreBonusCount(regLookup.getOrThrow(Enchantments.FORTUNE))));

        Block b1 = ((s == StoneMaterial.STONE || s == StoneMaterial.DEEPSLATE) ?
                (s == StoneMaterial.STONE) ? Blocks.COBBLESTONE : Blocks.COBBLED_DEEPSLATE :
        BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(SpiroMod.MOD_ID, "cobbled_" + s.get())));

        builder.withPool(
                LootPool.lootPool()
                        .add(LootItem.lootTableItem(b1))
                        .setRolls(UniformGenerator.between(0, 1))
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));

        return builder;
    }

    // Old code from 1.20.x, this no longer works and is here only for reference
    /* protected LootTable.Builder oreTable01(Block b0, Block b1, Item item, NumberProvider dropAmtRange)
    {
        LootPool.Builder mainPool = LootPool.lootPool();

        mainPool.setRolls(ConstantValue.exactly(1))
                .add(AlternativesEntry.alternatives(

                        // Silk touch.
                        LootItem.lootTableItem(b0)
                                .when(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))),

                        // Otherwise:
                        EntryGroup.list(
                                // Generic drop.
                                LootItem.lootTableItem(item)
                                        .apply(SetItemCountFunction.setCount(dropAmtRange))
                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                        .apply(ApplyExplosionDecay.explosionDecay()),

                                // Stone drop type associated with this ore variant.
                                LootItem.lootTableItem(b1)
                                        .when(LootItemRandomChanceCondition.randomChance(0.1f))))); // 10% chance to drop base block.

        return LootTable.lootTable().withPool(mainPool);
    } */
}