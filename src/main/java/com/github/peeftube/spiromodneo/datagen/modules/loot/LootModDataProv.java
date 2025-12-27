package com.github.peeftube.spiromodneo.datagen.modules.loot;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.registry.data.OreCollection;
import com.github.peeftube.spiromodneo.core.init.registry.data.StoneCollection;
import com.github.peeftube.spiromodneo.core.init.registry.data.StoneMaterial;
import com.github.peeftube.spiromodneo.datagen.modules.loot.subprov.OreBaseLootTables;
import com.github.peeftube.spiromodneo.datagen.modules.loot.subprov.OtherLootTables;
import com.github.peeftube.spiromodneo.util.loot.SwapLootStackModifier;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootModDataProv extends GlobalLootModifierProvider
{
    public LootModDataProv(PackOutput output, CompletableFuture<HolderLookup.Provider> reg)
    { super(output, reg, SpiroMod.MOD_ID); }

    @Override
    protected void start()
    {
        mobDrops();
        for (StoneCollection stone : StoneCollection.STONE_COLLECTIONS) { baseStoneDrops(stone); }
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreDrops(ore); }

        this.add("short_grass_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/short_grass")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_LITE));

        this.add("fern_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/fern")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_LITE));

        this.add("tall_grass_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/tall_grass")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_MEDIUM));

        this.add("seagrass_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/seagrass")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_MEDIUM));

        this.add("tall_seagrass_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/tall_seagrass")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_HEAVY));

        this.add("kelp_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/kelp")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_MEDIUM));

        this.add("kelp_fibre_2",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/kelp_plant")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_MEDIUM));

        this.add("cave_vines_fibre",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/cave_vines")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_MEDIUM));

        this.add("cave_vines_fibre_2",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("blocks/cave_vines_plant")).build()
                }, OtherLootTables.PLANT_FIBRE_DROPS_MEDIUM));
    }

    private void oreDrops(OreCollection set)
    {
        for (StoneMaterial s : StoneMaterial.values())
        {
            Block o = set.getBulkData().get(s).getBlock().get();
            if (BuiltInRegistries.BLOCK.getKey(o).getNamespace().equalsIgnoreCase("minecraft")
            && (s == StoneMaterial.STONE || s == StoneMaterial.DEEPSLATE || s == StoneMaterial.NETHERRACK))
            {
                this.add("modify_" + set.material().get() + "_drops_for_" + s.get(),
                        new AddTableLootModifier(new LootItemCondition[]{
                                new LootTableIdCondition.Builder(
                                        ResourceLocation.withDefaultNamespace("blocks/" +
                                                BuiltInRegistries.BLOCK.getKey(o).getPath())).build(),
                                new InvertedLootItemCondition(this.hasSilkTouch().build())
                        }, s == StoneMaterial.STONE ? OreBaseLootTables.COBBLE :
                        s == StoneMaterial.DEEPSLATE ? OreBaseLootTables.COBBLE_DEEPSLATE :
                        OreBaseLootTables.COBBLE_NETHERRACK));
            }
        }
    }

    private void baseStoneDrops(StoneCollection set)
    {
        // This modifier is wholly unnecessary for modded blocks, or vanilla blocks
        // which already drop the appropriate cobble block
        if (BuiltInRegistries.BLOCK.getKey(set.getBaseStone().get())
                                   .getNamespace().equalsIgnoreCase("minecraft") &&
                (set.getBaseStone().get() != Blocks.STONE && set.getBaseStone().get() != Blocks.DEEPSLATE))
        {
            /* this.add("modify_" + set.material().get() + "_drops",
                    new SwapLootStackModifier(new LootItemCondition[]{
                            new LootTableIdCondition.Builder(
                                    BuiltInRegistries.BLOCK.getKey(set.getBaseStone().get())).build(),
                            new InvertedLootItemCondition(this.hasSilkTouch().build())
                    }, set.getBaseStone().get().asItem(), set.getCobble().get().asItem())); */

            this.add("modify_" + set.material().get() + "_drops",
                    new SwapLootStackModifier(new LootItemCondition[]{
                            new LootTableIdCondition.Builder(
                                    ResourceLocation.withDefaultNamespace(
                                            "blocks/" + set.material().get())).build(),
                            new InvertedLootItemCondition(this.hasSilkTouch().build())
                    }, set.getBaseStone().get().asItem(), set.getCobble().get().asItem()));
        }
    }

    private void mobDrops()
    {
        this.add("pig_drops",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("entities/pig")).build()
                }, OtherLootTables.PIG_DROPS));

        List<String> sheepColors = new ArrayList<>(List.of(
                "white", "light_gray", "gray", "black", "blue", "light_blue", "brown", "red", "pink",
                "brown", "yellow", "orange", "green", "lime", "purple", "magenta"
        ));

        for (String c : sheepColors)
        {
            this.add("sheep/" + c + "_sheep_drops",
                    new AddTableLootModifier(new LootItemCondition[]{
                            new LootTableIdCondition.Builder(
                                    ResourceLocation.withDefaultNamespace("entities/sheep/" + c)).build()
                    }, OtherLootTables.SHEEP_DROPS));
        }

        this.add("cow_drops",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(
                                ResourceLocation.withDefaultNamespace("entities/cow")).build()
                }, OtherLootTables.COW_DROPS));
    }

    // Copied from BlockLootSubProvider.java
    protected LootItemCondition.Builder hasSilkTouch()
    {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return MatchTool.toolMatches(
            ItemPredicate.Builder.item()
                                 .withSubPredicate(
                    ItemSubPredicates.ENCHANTMENTS,
                    ItemEnchantmentsPredicate.enchantments(
                        List.of(new EnchantmentPredicate(registrylookup.getOrThrow(Enchantments.SILK_TOUCH),
                                MinMaxBounds.Ints.atLeast(1)))
                    )
                )
        );
    }
}
