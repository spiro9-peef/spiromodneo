package com.github.peeftube.spiromodneo.datagen.modules.loot.subprov;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiConsumer;

public class OtherLootTables implements LootTableSubProvider
{
    // Thanks to 123ini321 on the Neoforge Discord for help with this
    public static final ResourceKey<LootTable> PIG_DROPS =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                    SpiroMod.MOD_ID, "mobs/pig_drops"));
    public static final ResourceKey<LootTable> SHEEP_DROPS =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                    SpiroMod.MOD_ID, "mobs/sheep_drops"));
    public static final ResourceKey<LootTable> COW_DROPS =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                    SpiroMod.MOD_ID, "mobs/cow_drops"));
    public static final ResourceKey<LootTable> PLANT_FIBRE_DROPS_LITE =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                    SpiroMod.MOD_ID, "mobs/plant_fibre_drops_lite"));
    public static final ResourceKey<LootTable> PLANT_FIBRE_DROPS_MEDIUM =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                    SpiroMod.MOD_ID, "mobs/plant_fibre_drops_medium"));
    public static final ResourceKey<LootTable> PLANT_FIBRE_DROPS_HEAVY =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                    SpiroMod.MOD_ID, "mobs/plant_fibre_drops_heavy"));

    public OtherLootTables(HolderLookup.Provider reg)
    { super(); }

    @Override @ParametersAreNonnullByDefault
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
    {
        consumer.accept
        (
                PIG_DROPS,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.LEATHER))
                                .setRolls(UniformGenerator.between(0, 1))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registrar.SINEW))
                                .setRolls(UniformGenerator.between(0, 2))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .withPool(LootPool.lootPool()
                                //.add(LootItem.lootTableItem(Registrar.BACON))
                                //.add(LootItem.lootTableItem(Registrar.HAM))
                                //.add(LootItem.lootTableItem(Registrar.PORK_RIBS))
                                .setRolls(UniformGenerator.between(0, 1))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
        );
        consumer.accept
        (
                SHEEP_DROPS,
                LootTable.lootTable()
                         .withPool(LootPool.lootPool()
                                           .add(LootItem.lootTableItem(Items.STRING))
                                           .add(LootItem.lootTableItem(Registrar.SINEW))
                                           .setRolls(UniformGenerator.between(0, 2))
                                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
        );
        consumer.accept
        (
                COW_DROPS,
                LootTable.lootTable()
                         .withPool(LootPool.lootPool()
                                           .add(LootItem.lootTableItem(Registrar.SINEW))
                                           .setRolls(UniformGenerator.between(0, 2))
                                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                         .withPool(LootPool.lootPool()
                                           //.add(LootItem.lootTableItem(Registrar.BEEF_RIBS))
                                           //.add(LootItem.lootTableItem(Registrar.FILET_MIGNON))
                                           .setRolls(UniformGenerator.between(0, 1))
                                           .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
        );
        consumer.accept
        (
                PLANT_FIBRE_DROPS_LITE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registrar.PLANT_FIBRE))
                                .setRolls(UniformGenerator.between(0, 1))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
        );
        consumer.accept
        (
                PLANT_FIBRE_DROPS_MEDIUM,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registrar.PLANT_FIBRE))
                                .setRolls(UniformGenerator.between(0, 2))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
        );
        consumer.accept
        (
                PLANT_FIBRE_DROPS_HEAVY,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(Registrar.PLANT_FIBRE))
                                .setRolls(UniformGenerator.between(1, 3))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
        );
    }
}
