package com.github.peeftube.spiromodneo.util.wood;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.blocks.*;
import com.github.peeftube.spiromodneo.core.init.registry.data.Tappable;
import com.github.peeftube.spiromodneo.core.init.registry.data.VariableWoodMaterial;
import com.github.peeftube.spiromodneo.core.init.registry.data.WoodMaterial;
import com.github.peeftube.spiromodneo.util.GenericBlockItemCoupling;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.github.peeftube.spiromodneo.util.TagCoupling;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public interface WoodUtilities
{
    static WoodData populate(WoodMaterial mat, int li, Map<Boolean, Tappable> isTappable)
    {
        Map<LivingWoodBlockType, GenericBlockItemCoupling> livingDataContent = new HashMap<>();
        Map<PlankBlockSubType, GenericBlockItemCoupling>    plankDataContent  = new HashMap<>();
        Map<ManufacturedWoodType, GenericBlockItemCoupling> manufacturableContent = new HashMap<>();
        Map<SignType, SignBlockItemTriad> signContent = new HashMap<>();

        SoundType baseWoodSound = mat == WoodMaterial.CHERRY ? SoundType.CHERRY_WOOD :
                mat.isLikeNetherFungus() ? SoundType.NETHER_WOOD : SoundType.WOOD;

        String nameParse = mat.getName().replace("_fungus", "");

        for (LivingWoodBlockType t : LivingWoodBlockType.values())
        {
            switch(t)
            {
                case LOG ->
                {
                    switch(mat)
                    {
                        case OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_LOG, () -> Items.OAK_LOG));
                        case BIRCH -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_LOG, () -> Items.BIRCH_LOG));
                        case SPRUCE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_LOG, () -> Items.SPRUCE_LOG));
                        case JUNGLE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_LOG, () -> Items.JUNGLE_LOG));
                        case ACACIA -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_LOG, () -> Items.ACACIA_LOG));
                        case DARK_OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_LOG, () -> Items.DARK_OAK_LOG));
                        case CHERRY -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_LOG, () -> Items.CHERRY_LOG));
                        case CRIMSON_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_STEM, () -> Items.CRIMSON_STEM));
                        case WARPED_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_STEM, () -> Items.WARPED_STEM));
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_LOG, () -> Items.MANGROVE_LOG));

                        default ->
                        {
                            Supplier<? extends Block> b;

                            if (!isTappable.containsKey(true))
                            {
                                b = Registrar.regBlock(nameParse + "_log",
                                        () -> new WoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li)));
                            }
                            else
                            {
                                b = Registrar.regBlock(nameParse + "_log",
                                        () -> new TappableWoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li),
                                                isTappable.get(true)));
                            }

                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            livingDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case STRIPPED_LOG ->
                {
                    switch(mat)
                    {
                        case OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_OAK_LOG, () -> Items.STRIPPED_OAK_LOG));
                        case BIRCH -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_BIRCH_LOG, () -> Items.STRIPPED_BIRCH_LOG));
                        case SPRUCE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_SPRUCE_LOG, () -> Items.STRIPPED_SPRUCE_LOG));
                        case JUNGLE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_JUNGLE_LOG, () -> Items.STRIPPED_JUNGLE_LOG));
                        case ACACIA -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_ACACIA_LOG, () -> Items.STRIPPED_ACACIA_LOG));
                        case DARK_OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_DARK_OAK_LOG, () -> Items.STRIPPED_DARK_OAK_LOG));
                        case CHERRY -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_CHERRY_LOG, () -> Items.STRIPPED_CHERRY_LOG));
                        case CRIMSON_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_CRIMSON_STEM, () -> Items.STRIPPED_CRIMSON_STEM));
                        case WARPED_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_WARPED_STEM, () -> Items.STRIPPED_WARPED_STEM));
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_MANGROVE_LOG, () -> Items.STRIPPED_MANGROVE_LOG));

                        default ->
                        {
                            Supplier<? extends Block> b;

                            if (!isTappable.containsKey(true))
                            {
                                b = Registrar.regBlock("stripped_" + nameParse + "_log",
                                        () -> new WoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li)));
                            }
                            else
                            {
                                b = Registrar.regBlock("stripped_" + nameParse + "_log",
                                        () -> new TappableWoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li),
                                                isTappable.get(true)));
                            }

                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            livingDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case WOOD ->
                {
                    switch(mat)
                    {
                        case OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_WOOD, () -> Items.OAK_WOOD));
                        case BIRCH -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_WOOD, () -> Items.BIRCH_WOOD));
                        case SPRUCE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_WOOD, () -> Items.SPRUCE_WOOD));
                        case JUNGLE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_WOOD, () -> Items.JUNGLE_WOOD));
                        case ACACIA -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_WOOD, () -> Items.ACACIA_WOOD));
                        case DARK_OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_WOOD, () -> Items.DARK_OAK_WOOD));
                        case CHERRY -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_WOOD, () -> Items.CHERRY_WOOD));
                        case CRIMSON_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_HYPHAE, () -> Items.CRIMSON_HYPHAE));
                        case WARPED_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_HYPHAE, () -> Items.WARPED_HYPHAE));
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_WOOD, () -> Items.MANGROVE_WOOD));

                        default ->
                        {
                            Supplier<? extends Block> b;

                            if (!isTappable.containsKey(true))
                            {
                                b = Registrar.regBlock(nameParse + "_wood",
                                        () -> new WoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li)));
                            }
                            else
                            {
                                b = Registrar.regBlock(nameParse + "_wood",
                                        () -> new TappableWoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li),
                                                isTappable.get(true)));
                            }

                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            livingDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case STRIPPED_WOOD ->
                {
                    switch(mat)
                    {
                        case OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_OAK_WOOD, () -> Items.STRIPPED_OAK_WOOD));
                        case BIRCH -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_BIRCH_WOOD, () -> Items.STRIPPED_BIRCH_WOOD));
                        case SPRUCE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_SPRUCE_WOOD, () -> Items.STRIPPED_SPRUCE_WOOD));
                        case JUNGLE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_JUNGLE_WOOD, () -> Items.STRIPPED_JUNGLE_WOOD));
                        case ACACIA -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_ACACIA_WOOD, () -> Items.STRIPPED_ACACIA_WOOD));
                        case DARK_OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_DARK_OAK_WOOD, () -> Items.STRIPPED_DARK_OAK_WOOD));
                        case CHERRY -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_CHERRY_WOOD, () -> Items.STRIPPED_CHERRY_WOOD));
                        case CRIMSON_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_CRIMSON_HYPHAE, () -> Items.STRIPPED_CRIMSON_HYPHAE));
                        case WARPED_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_WARPED_HYPHAE, () -> Items.STRIPPED_WARPED_HYPHAE));
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.STRIPPED_MANGROVE_WOOD, () -> Items.STRIPPED_MANGROVE_WOOD));

                        default ->
                        {
                            Supplier<? extends Block> b;

                            if (!isTappable.containsKey(true))
                            {
                                b = Registrar.regBlock("stripped_" + nameParse + "_wood",
                                        () -> new WoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li)));
                            }
                            else
                            {
                                b = Registrar.regBlock("stripped_" + nameParse + "_wood",
                                        () -> new TappableWoodBlock(
                                    BlockBehaviour.Properties.of().strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li),
                                                isTappable.get(true)));
                            }

                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            livingDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case LEAVES ->
                {
                    switch(mat)
                    {
                        case OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_LEAVES, () -> Items.OAK_LEAVES));
                        case BIRCH -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_LEAVES, () -> Items.BIRCH_LEAVES));
                        case SPRUCE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_LEAVES, () -> Items.SPRUCE_LEAVES));
                        case JUNGLE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_LEAVES, () -> Items.JUNGLE_LEAVES));
                        case ACACIA -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_LEAVES, () -> Items.ACACIA_LEAVES));
                        case DARK_OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_LEAVES, () -> Items.DARK_OAK_LEAVES));
                        case CHERRY -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_LEAVES, () -> Items.CHERRY_LEAVES));
                        case CRIMSON_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.NETHER_WART_BLOCK, () -> Items.NETHER_WART_BLOCK));
                        case WARPED_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_WART_BLOCK, () -> Items.WARPED_WART_BLOCK));
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_LEAVES, () -> Items.MANGROVE_LEAVES));

                        default ->
                        {
                            if (!mat.isLikeNetherFungus())
                            {
                                Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_leaves",
                                        () -> new LeavesBlock(
                                                BlockBehaviour.Properties.of().sound(SoundType.GRASS)
                                             .lightLevel(s -> li).noOcclusion().isSuffocating(Blocks::never)
                                            .randomTicks().strength(0.2F).isViewBlocking(Blocks::never)
                                            .pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never)));
                                Supplier<? extends Item> i = Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                                livingDataContent.put(t, new GenericBlockItemCoupling(b, i));
                            }
                        }
                    }
                }

                case SAPLING ->
                {
                    switch(mat)
                    {
                        case OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_SAPLING, () -> Items.OAK_SAPLING));
                        case BIRCH -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_SAPLING, () -> Items.BIRCH_SAPLING));
                        case SPRUCE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_SAPLING, () -> Items.SPRUCE_SAPLING));
                        case JUNGLE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_SAPLING, () -> Items.JUNGLE_SAPLING));
                        case ACACIA -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_SAPLING, () -> Items.ACACIA_SAPLING));
                        case DARK_OAK -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_SAPLING, () -> Items.DARK_OAK_SAPLING));
                        case CHERRY -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_SAPLING, () -> Items.CHERRY_SAPLING));
                        case CRIMSON_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_FUNGUS, () -> Items.CRIMSON_FUNGUS));
                        case WARPED_FUNGUS -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_FUNGUS, () -> Items.WARPED_FUNGUS));
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_PROPAGULE, () -> Items.MANGROVE_PROPAGULE));

                        default ->
                        {
                            if (!mat.isLikeNetherFungus())
                            {
                                Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_sapling",
                                        () -> new SaplingBlock(mat.getGrower(),
                                                BlockBehaviour.Properties.of().sound(SoundType.GRASS)
                                             .lightLevel(s -> li).noCollission()
                                            .randomTicks().instabreak().pushReaction(PushReaction.DESTROY)));
                                Supplier<? extends Item> i = Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                                livingDataContent.put(t, new GenericBlockItemCoupling(b, i));
                            }
                        }
                    }
                }

                case ROOTS ->
                {
                    switch(mat)
                    {
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_ROOTS, () -> Items.MANGROVE_ROOTS));
                        default -> livingDataContent.put(t, null);
                        // Roots only apply to mangrove-like sets. As
                        // there are currently no mangrove-like sets besides
                        // vanilla, we just use this code.
                    }
                }

                case ROOTS_WITH_MUD ->
                {
                    switch(mat)
                    {
                        case MANGROVE -> livingDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MUDDY_MANGROVE_ROOTS, () -> Items.MUDDY_MANGROVE_ROOTS));
                        default -> livingDataContent.put(t, null);
                        // Roots only apply to mangrove-like sets. As
                        // there are currently no mangrove-like sets besides
                        // vanilla, we just use this code.
                    }
                }
            }
        }

        String logTagKey = nameParse + (mat.isLikeNetherFungus() ? "_stems" : "_logs");
        TagKey<Block> lBTag = SpiroTags.Blocks.tag(logTagKey);
        TagKey<Item> lITag = SpiroTags.Items.tag(logTagKey);
        TagCoupling logTags = new TagCoupling(lBTag, lITag);

        String leafTagKey = nameParse + (mat.isLikeNetherFungus() ? "_wart_blocks" : "_leaves");
        TagKey<Block> leBTag = SpiroTags.Blocks.tag(leafTagKey);
        TagKey<Item> leITag = SpiroTags.Items.tag(leafTagKey);
        TagCoupling leafTags = new TagCoupling(leBTag, leITag);

        String aliveTagKey = "spiro_living_wood_of_type_" + nameParse;
        TagKey<Block> aBTag = SpiroTags.Blocks.tag(aliveTagKey);
        TagKey<Item> aITag = SpiroTags.Items.tag(aliveTagKey);
        TagCoupling aliveTags = new TagCoupling(aBTag, aITag);
        
        for (PlankBlockSubType t : PlankBlockSubType.values())
        {
            switch(t)
            {
                case BLOCK ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_PLANKS, () -> Items.OAK_PLANKS));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_PLANKS, () -> Items.BIRCH_PLANKS));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_PLANKS, () -> Items.SPRUCE_PLANKS));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_PLANKS, () -> Items.JUNGLE_PLANKS));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_PLANKS, () -> Items.ACACIA_PLANKS));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_PLANKS, () -> Items.DARK_OAK_PLANKS));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_PLANKS, () -> Items.CHERRY_PLANKS));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_PLANKS, () -> Items.CRIMSON_PLANKS));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_PLANKS, () -> Items.WARPED_PLANKS));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_PLANKS, () -> Items.MANGROVE_PLANKS));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_planks",
                                    () -> new Block(BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case SLAB ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_SLAB, () -> Items.OAK_SLAB));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_SLAB, () -> Items.BIRCH_SLAB));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_SLAB, () -> Items.SPRUCE_SLAB));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_SLAB, () -> Items.JUNGLE_SLAB));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_SLAB, () -> Items.ACACIA_SLAB));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_SLAB, () -> Items.DARK_OAK_SLAB));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_SLAB, () -> Items.CHERRY_SLAB));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_SLAB, () -> Items.CRIMSON_SLAB));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_SLAB, () -> Items.WARPED_SLAB));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_SLAB, () -> Items.MANGROVE_SLAB));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_slab",
                                    () -> new SlabBlock(BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
                
                case STAIRS ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_STAIRS, () -> Items.OAK_STAIRS));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_STAIRS, () -> Items.BIRCH_STAIRS));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_STAIRS, () -> Items.SPRUCE_STAIRS));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_STAIRS, () -> Items.JUNGLE_STAIRS));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_STAIRS, () -> Items.ACACIA_STAIRS));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_STAIRS, () -> Items.DARK_OAK_STAIRS));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_STAIRS, () -> Items.CHERRY_STAIRS));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_STAIRS, () -> Items.CRIMSON_STAIRS));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_STAIRS, () -> Items.WARPED_STAIRS));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_STAIRS, () -> Items.MANGROVE_STAIRS));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_stairs",
                                    () -> new StairBlock(Objects.requireNonNull(
                                            plankDataContent.get(PlankBlockSubType.BLOCK).getBlock()
                                                            .get().defaultBlockState()),
                                            BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case FENCE ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_FENCE, () -> Items.OAK_FENCE));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_FENCE, () -> Items.BIRCH_FENCE));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_FENCE, () -> Items.SPRUCE_FENCE));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_FENCE, () -> Items.JUNGLE_FENCE));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_FENCE, () -> Items.ACACIA_FENCE));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_FENCE, () -> Items.DARK_OAK_FENCE));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_FENCE, () -> Items.CHERRY_FENCE));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_FENCE, () -> Items.CRIMSON_FENCE));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_FENCE, () -> Items.WARPED_FENCE));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_FENCE, () -> Items.MANGROVE_FENCE));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_fence",
                                    () -> new FenceBlock(BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case FENCE_GATE ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_FENCE_GATE, () -> Items.OAK_FENCE_GATE));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_FENCE_GATE, () -> Items.BIRCH_FENCE_GATE));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_FENCE_GATE, () -> Items.SPRUCE_FENCE_GATE));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_FENCE_GATE, () -> Items.JUNGLE_FENCE_GATE));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_FENCE_GATE, () -> Items.ACACIA_FENCE_GATE));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_FENCE_GATE, () -> Items.DARK_OAK_FENCE_GATE));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_FENCE_GATE, () -> Items.CHERRY_FENCE_GATE));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_FENCE_GATE, () -> Items.CRIMSON_FENCE_GATE));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_FENCE_GATE, () -> Items.WARPED_FENCE_GATE));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_FENCE_GATE, () -> Items.MANGROVE_FENCE_GATE));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_fence_gate",
                                    () -> new FenceGateBlock(BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li), SoundEvents.FENCE_GATE_OPEN,
                                            SoundEvents.FENCE_GATE_CLOSE)); // TODO: Create handler for sound events
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case BUTTON ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_BUTTON, () -> Items.OAK_BUTTON));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_BUTTON, () -> Items.BIRCH_BUTTON));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_BUTTON, () -> Items.SPRUCE_BUTTON));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_BUTTON, () -> Items.JUNGLE_BUTTON));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_BUTTON, () -> Items.ACACIA_BUTTON));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_BUTTON, () -> Items.DARK_OAK_BUTTON));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_BUTTON, () -> Items.CHERRY_BUTTON));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_BUTTON, () -> Items.CRIMSON_BUTTON));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_BUTTON, () -> Items.WARPED_BUTTON));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_BUTTON, () -> Items.MANGROVE_BUTTON));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_button",
                                    () -> new ButtonBlock(BlockSetType.OAK, 30,
                                            BlockBehaviour.Properties.of().sound(baseWoodSound).lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
                
                case PRESSURE_PLATE ->
                {
                    switch(mat)
                    {
                        case OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_PRESSURE_PLATE, () -> Items.OAK_PRESSURE_PLATE));
                        case BIRCH -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_PRESSURE_PLATE, () -> Items.BIRCH_PRESSURE_PLATE));
                        case SPRUCE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_PRESSURE_PLATE, () -> Items.SPRUCE_PRESSURE_PLATE));
                        case JUNGLE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_PRESSURE_PLATE, () -> Items.JUNGLE_PRESSURE_PLATE));
                        case ACACIA -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_PRESSURE_PLATE, () -> Items.ACACIA_PRESSURE_PLATE));
                        case DARK_OAK -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_PRESSURE_PLATE, () -> Items.DARK_OAK_PRESSURE_PLATE));
                        case CHERRY -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_PRESSURE_PLATE, () -> Items.CHERRY_PRESSURE_PLATE));
                        case CRIMSON_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_PRESSURE_PLATE, () -> Items.CRIMSON_PRESSURE_PLATE));
                        case WARPED_FUNGUS -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_PRESSURE_PLATE, () -> Items.WARPED_PRESSURE_PLATE));
                        case MANGROVE -> plankDataContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_PRESSURE_PLATE, () -> Items.MANGROVE_PRESSURE_PLATE));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_pressure_plate",
                                    () -> new PressurePlateBlock(BlockSetType.OAK,
                                            BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            plankDataContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
            }
        }

        String plankTagKey = "spiro_planks_of_type_" + nameParse;
        TagKey<Block> pBTag = SpiroTags.Blocks.tag(plankTagKey);
        TagKey<Item> pITag = SpiroTags.Items.tag(plankTagKey);
        TagCoupling plankTags = new TagCoupling(pBTag, pITag);

        for (ManufacturedWoodType t : ManufacturedWoodType.values())
        {
            switch(t)
            {
                case DOOR ->
                {
                    switch(mat)
                    {
                        case OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_DOOR, () -> Items.OAK_DOOR));
                        case BIRCH -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_DOOR, () -> Items.BIRCH_DOOR));
                        case SPRUCE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_DOOR, () -> Items.SPRUCE_DOOR));
                        case JUNGLE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_DOOR, () -> Items.JUNGLE_DOOR));
                        case ACACIA -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_DOOR, () -> Items.ACACIA_DOOR));
                        case DARK_OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_DOOR, () -> Items.DARK_OAK_DOOR));
                        case CHERRY -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_DOOR, () -> Items.CHERRY_DOOR));
                        case CRIMSON_FUNGUS -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_DOOR, () -> Items.CRIMSON_DOOR));
                        case WARPED_FUNGUS -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_DOOR, () -> Items.WARPED_DOOR));
                        case MANGROVE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_DOOR, () -> Items.MANGROVE_DOOR));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_door",
                                    () -> new DoorBlock(BlockSetType.OAK,
                                            BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            manufacturableContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
                
                case TRAPDOOR ->
                {
                    switch(mat)
                    {
                        case OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.OAK_TRAPDOOR, () -> Items.OAK_TRAPDOOR));
                        case BIRCH -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BIRCH_TRAPDOOR, () -> Items.BIRCH_TRAPDOOR));
                        case SPRUCE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.SPRUCE_TRAPDOOR, () -> Items.SPRUCE_TRAPDOOR));
                        case JUNGLE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.JUNGLE_TRAPDOOR, () -> Items.JUNGLE_TRAPDOOR));
                        case ACACIA -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.ACACIA_TRAPDOOR, () -> Items.ACACIA_TRAPDOOR));
                        case DARK_OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.DARK_OAK_TRAPDOOR, () -> Items.DARK_OAK_TRAPDOOR));
                        case CHERRY -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHERRY_TRAPDOOR, () -> Items.CHERRY_TRAPDOOR));
                        case CRIMSON_FUNGUS -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRIMSON_TRAPDOOR, () -> Items.CRIMSON_TRAPDOOR));
                        case WARPED_FUNGUS -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.WARPED_TRAPDOOR, () -> Items.WARPED_TRAPDOOR));
                        case MANGROVE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.MANGROVE_TRAPDOOR, () -> Items.MANGROVE_TRAPDOOR));

                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(nameParse + "_trapdoor",
                                    () -> new TrapDoorBlock(BlockSetType.OAK,
                                            BlockBehaviour.Properties.of()
                                            .strength(2F, 3F)
                                            .sound(baseWoodSound).lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            manufacturableContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case CRAFTING_TABLE ->
                {
                    switch(mat)
                    {
                        case OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CRAFTING_TABLE, () -> Items.CRAFTING_TABLE));
                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(
                                    nameParse + "_crafting_table",
                                    () -> new CraftingTableBlock(BlockBehaviour.Properties.of()
                                            .strength(2F, 3F).sound(baseWoodSound)
                                            .lightLevel(s -> li)));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            manufacturableContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case CHEST ->
                {
                    switch(mat)
                    {
                        case OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.CHEST, () -> Items.CHEST));
                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(
                                    nameParse + "_chest",
                                    () -> new ExtensibleChestBlock(nameParse));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            manufacturableContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case TRAPPED_CHEST ->
                {
                    switch(mat)
                    {
                        case OAK -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.TRAPPED_CHEST, () -> Items.TRAPPED_CHEST));
                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(
                                    "trapped_" + nameParse + "_chest",
                                    () -> new ExtensibleTrappedChestBlock(nameParse));
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            manufacturableContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }

                case BARREL ->
                {
                    switch(mat)
                    {
                        // This defaults to spruce since barrels look more like
                        // spruce than a material like oak
                        case SPRUCE -> manufacturableContent.put(t,
                                new GenericBlockItemCoupling(() -> Blocks.BARREL, () -> Items.BARREL));
                        default ->
                        {
                            Supplier<? extends Block> b = Registrar.regBlock(
                                    nameParse + "_barrel",
                                    ExtensibleBarrelBlock::new);
                            Supplier<? extends Item> i =
                                    Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

                            manufacturableContent.put(t, new GenericBlockItemCoupling(b, i));
                        }
                    }
                }
            }
        }

        for (SignType t : SignType.values())
        {
            switch(t)
            {
                case BASIC ->
                {
                    switch(mat)
                    {
                        case OAK -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.OAK_SIGN, () -> Blocks.OAK_WALL_SIGN,
                                        () -> Items.OAK_SIGN));
                        case BIRCH -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.BIRCH_SIGN, () -> Blocks.BIRCH_WALL_SIGN,
                                        () -> Items.BIRCH_SIGN));
                        case SPRUCE -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.SPRUCE_SIGN, () -> Blocks.SPRUCE_WALL_SIGN,
                                        () -> Items.SPRUCE_SIGN));
                        case JUNGLE -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.JUNGLE_SIGN, () -> Blocks.JUNGLE_WALL_SIGN,
                                        () -> Items.JUNGLE_SIGN));
                        case ACACIA -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.ACACIA_SIGN, () -> Blocks.ACACIA_WALL_SIGN,
                                        () -> Items.ACACIA_SIGN));
                        case DARK_OAK -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.DARK_OAK_SIGN, () -> Blocks.DARK_OAK_WALL_SIGN,
                                        () -> Items.DARK_OAK_SIGN));
                        case CHERRY -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.CHERRY_SIGN, () -> Blocks.CHERRY_WALL_SIGN,
                                        () -> Items.CHERRY_SIGN));
                        case CRIMSON_FUNGUS -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.CRIMSON_SIGN, () -> Blocks.CRIMSON_WALL_SIGN,
                                        () -> Items.CRIMSON_SIGN));
                        case WARPED_FUNGUS -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.WARPED_SIGN, () -> Blocks.WARPED_WALL_SIGN,
                                        () -> Items.WARPED_SIGN));
                        case MANGROVE -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.MANGROVE_SIGN, () -> Blocks.MANGROVE_WALL_SIGN,
                                        () -> Items.MANGROVE_SIGN));

                        default ->
                        {
                            Supplier<? extends Block> s = Registrar.regBlock(nameParse + "_sign",
                                    () -> new ExtensibleStandingSignBlock(WoodType.OAK, // TODO: Add handler for WoodType
                                            BlockBehaviour.Properties.of().strength(1F)
                                            .sound(baseWoodSound).lightLevel(st -> li)));
                            Supplier<? extends Block> w = Registrar.regBlock(nameParse + "_wall_sign",
                                    () -> new ExtensibleWallSignBlock(WoodType.OAK, // TODO: Add handler for WoodType
                                            BlockBehaviour.Properties.of().strength(1F)
                                            .sound(baseWoodSound).lightLevel(st -> li)));
                            Supplier<? extends Item> i = Registrar.ITEMS.register(nameParse + "_sign",
                                    () -> new SignItem(new Item.Properties().stacksTo(16),
                                            s.get(), w.get()));

                            signContent.put(t, new SignBlockItemTriad(s, w, i));
                        }
                    }
                }

                case HANGING ->
                {
                    switch(mat)
                    {
                        case OAK -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.OAK_HANGING_SIGN, () -> Blocks.OAK_WALL_HANGING_SIGN,
                                        () -> Items.OAK_HANGING_SIGN));
                        case BIRCH -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.BIRCH_HANGING_SIGN, () -> Blocks.BIRCH_WALL_HANGING_SIGN,
                                        () -> Items.BIRCH_HANGING_SIGN));
                        case SPRUCE -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.SPRUCE_HANGING_SIGN, () -> Blocks.SPRUCE_WALL_HANGING_SIGN,
                                        () -> Items.SPRUCE_HANGING_SIGN));
                        case JUNGLE -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.JUNGLE_HANGING_SIGN, () -> Blocks.JUNGLE_WALL_HANGING_SIGN,
                                        () -> Items.JUNGLE_HANGING_SIGN));
                        case ACACIA -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.ACACIA_HANGING_SIGN, () -> Blocks.ACACIA_WALL_HANGING_SIGN,
                                        () -> Items.ACACIA_HANGING_SIGN));
                        case DARK_OAK -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.DARK_OAK_HANGING_SIGN, () -> Blocks.DARK_OAK_WALL_HANGING_SIGN,
                                        () -> Items.DARK_OAK_HANGING_SIGN));
                        case CHERRY -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.CHERRY_HANGING_SIGN, () -> Blocks.CHERRY_WALL_HANGING_SIGN,
                                        () -> Items.CHERRY_HANGING_SIGN));
                        case CRIMSON_FUNGUS -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.CRIMSON_HANGING_SIGN, () -> Blocks.CRIMSON_WALL_HANGING_SIGN,
                                        () -> Items.CRIMSON_HANGING_SIGN));
                        case WARPED_FUNGUS -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.WARPED_HANGING_SIGN, () -> Blocks.WARPED_WALL_HANGING_SIGN,
                                        () -> Items.WARPED_HANGING_SIGN));
                        case MANGROVE -> signContent.put(t,
                                new SignBlockItemTriad(
                                        () -> Blocks.MANGROVE_HANGING_SIGN, () -> Blocks.MANGROVE_WALL_HANGING_SIGN,
                                        () -> Items.MANGROVE_HANGING_SIGN));

                        default ->
                        {
                            Supplier<? extends Block> s = Registrar.regBlock(nameParse + "_hanging_sign",
                                    () -> new ExtensibleHangingSignBlock(WoodType.OAK, // TODO: Add handler for WoodType
                                            BlockBehaviour.Properties.of().strength(1F)
                                            .sound(baseWoodSound).lightLevel(st -> li)));
                            Supplier<? extends Block> w = Registrar.regBlock(nameParse + "_wall_hanging_sign",
                                    () -> new ExtensibleWallHangingSignBlock(WoodType.OAK, // TODO: Add handler for WoodType
                                            BlockBehaviour.Properties.of().strength(1F)
                                            .sound(baseWoodSound).lightLevel(st -> li)));
                            Supplier<? extends Item> i = Registrar.ITEMS.register(nameParse + "_hanging_sign",
                                    () -> new SignItem(new Item.Properties().stacksTo(16),
                                            s.get(), w.get()));

                            signContent.put(t, new SignBlockItemTriad(s, w, i));
                        }
                    }
                }
            }
        }

        boolean isVariableWoodType = mat.equals(WoodMaterial.STONEWOOD);

        return new WoodData(mat.getName(), livingDataContent, logTags, leafTags, aliveTags,
                plankDataContent, plankTags, manufacturableContent, signContent, isVariableWoodType);
    }

    /** Populates the leaves section of the variable wood collection record. */
    static GenericBlockItemCoupling pVariableLeaves(VariableWoodMaterial mat, int li)
    {
        Supplier<? extends Block> b = Registrar.regBlock(mat.getName() + "_leaves",
                () -> new LeavesBlock(
                        BlockBehaviour.Properties.of().sound(SoundType.GRASS)
                     .lightLevel(s -> li).noOcclusion().isSuffocating(Blocks::never)
                    .randomTicks().strength(0.2F).isViewBlocking(Blocks::never)
                    .pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never)));
        Supplier<? extends Item> i = Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

        return new GenericBlockItemCoupling(b, i);
    }

    /** Populates the sapling section of the variable wood collection record. */
    static GenericBlockItemCoupling pVariableSapling(VariableWoodMaterial mat, int li)
    {
        Supplier<? extends Block> b =
                mat.getWood().type().isStonePlantable() ?
                Registrar.regBlock(mat.getName() + "_sapling",
                () -> new StonePlantableSaplingBlock(mat.getGrower(),
                        BlockBehaviour.Properties.of().sound(SoundType.GRASS)
                     .lightLevel(s -> li).noOcclusion().isSuffocating(Blocks::never)
                    .randomTicks().strength(0.2F).isViewBlocking(Blocks::never)
                    .pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never))) :
                Registrar.regBlock(mat.getName() + "_sapling",
                () -> new SaplingBlock(mat.getGrower(),
                        BlockBehaviour.Properties.of().sound(SoundType.GRASS)
                     .lightLevel(s -> li).noOcclusion().isSuffocating(Blocks::never)
                    .randomTicks().strength(0.2F).isViewBlocking(Blocks::never)
                    .pushReaction(PushReaction.DESTROY).isRedstoneConductor(Blocks::never)));
        Supplier<? extends Item> i = Registrar.regSimpleBlockItem((DeferredBlock<? extends Block>) b);

        return new GenericBlockItemCoupling(b, i);
    }
}
