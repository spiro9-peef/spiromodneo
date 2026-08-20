package com.github.peeftube.spiromodneo.datagen.modules.tags;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.OreCollection;
import com.github.peeftube.spiromodneo.core.init.registry.data.WoodCollection;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.ore.OreCoupling;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import com.github.peeftube.spiromodneo.util.wood.PlankBlockSubType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataProv extends ItemTagsProvider
{
    public ItemTagDataProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
            CompletableFuture<TagLookup<Block>> bLookup, ExistingFileHelper eFH)
    { super(output, lookup, bLookup, SpiroMod.MOD_ID, eFH); }

    @Override
    protected void addTags(HolderLookup.Provider lookup)
    {
        // Ores
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreTags(ore); }

        // Wood types
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS) { woodTags(wood); }

        // Crafting Materials
        tag(SpiroTags.Items.STRING_LIKE) // Loose materials should go first
                .add(Items.STRING)
                .add(Registrar.SINEW.get())
                .add(Registrar.PLANT_FIBRE.get());

        tag(SpiroTags.Items.STEEL_MIXTURES)
                .add(Registrar.STEEL_MIXTURE.get())
                .add(Registrar.WEAK_STEEL_MIXTURE.get());

        generalCraftingTags();

        // Others / Loose
    }

    private void woodTags(WoodCollection set)
    {
        TagKey<Item> tagForLivingWood = set.bulkData().aliveTags().getItemTag();
        TagKey<Item> tagForPlanks = set.bulkData().plankTags().getItemTag();

        tag(tagForPlanks)
                .add(set.bulkData().planks().get(PlankBlockSubType.BLOCK).getItem().get());
        tag(ItemTags.PLANKS)
                .addTag(tagForPlanks);

        tag(set.bulkData().logTags().getItemTag())
                .add(set.bulkData().livingWood().get(LivingWoodBlockType.LOG).getItem().get())
                .add(set.bulkData().livingWood().get(LivingWoodBlockType.STRIPPED_LOG).getItem().get())
                .add(set.bulkData().livingWood().get(LivingWoodBlockType.WOOD).getItem().get())
                .add(set.bulkData().livingWood().get(LivingWoodBlockType.STRIPPED_WOOD).getItem().get());
        tag(set.bulkData().leafTags().getItemTag())
                .add(set.bulkData().livingWood().get(LivingWoodBlockType.LEAVES).getItem().get());
        tag(set.type().isLikeNetherFungus() ? ItemTags.LOGS : ItemTags.LOGS_THAT_BURN)
                .addTag(set.bulkData().logTags().getItemTag());
        if (!set.type().isLikeNetherFungus())
        { tag(ItemTags.LEAVES).addTag(set.bulkData().leafTags().getItemTag()); }

        for (LivingWoodBlockType t : LivingWoodBlockType.values())
        { if (set.bulkData().livingWood().get(t) != null)
            tag(tagForLivingWood).add(set.bulkData().livingWood().get(t).getItem().get()); }
    }

    private void generalCraftingTags()
    {
        tag(SpiroTags.Items.LEATHER_MATERIAL)
                .add(Items.LEATHER);
        tag(SpiroTags.Items.WOOD_MATERIAL)
                .addTag(ItemTags.PLANKS);
        tag(SpiroTags.Items.SHARPWOOD_MATERIAL)
                .add(Registrar.SHARPENED_STICK.get())
                .add(Registrar.BUNDLE_OF_SHARP_STICKS.get());
        tag(SpiroTags.Items.FLINT_MATERIAL)
                .add(Items.FLINT);
        tag(SpiroTags.Items.STONE_MATERIAL)
                .addTag(ItemTags.STONE_CRAFTING_MATERIALS);
        tag(SpiroTags.Items.COPPER_MATERIAL)
                .add(Items.COPPER_INGOT);
        tag(SpiroTags.Items.CHAINMAIL_MATERIAL)
                .add(Items.CHAIN);
        tag(SpiroTags.Items.IRON_MATERIAL)
                .add(Items.IRON_INGOT);
        tag(SpiroTags.Items.LEAD_MATERIAL)
                .add(Registrar.getIngotFromMetal(Registrar.LEAD_METAL));
        tag(SpiroTags.Items.STEEL_MATERIAL)
                .add(Registrar.getIngotFromMetal(Registrar.STEEL_METAL));
        tag(SpiroTags.Items.GOLD_MATERIAL)
                .add(Items.GOLD_INGOT);
        tag(SpiroTags.Items.DIAMOND_MATERIAL)
                .add(Items.DIAMOND);
        tag(SpiroTags.Items.NETHERITE_MATERIAL)
                .add(Items.NETHERITE_INGOT);
    }

    private void oreTags(OreCollection set)
    {
        TagKey<Item> tag       = set.getOreIT();

        for (OreCoupling c : set.getBulkData().values())
        { tag(tag).add(c.getItem().get()); }
    }
}
