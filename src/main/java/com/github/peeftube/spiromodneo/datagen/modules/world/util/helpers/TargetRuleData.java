package com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.OreCollection;
import com.github.peeftube.spiromodneo.core.init.registry.data.StoneMaterial;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.function.Supplier;

public class TargetRuleData
{
    public static final class TargetRules
    {
        public static final RuleTest BLOCK_STONE = new BlockMatchTest(Blocks.STONE);
        public static final RuleTest BLOCK_DEEPSLATE = new BlockMatchTest(Blocks.DEEPSLATE);
        public static final RuleTest BLOCK_ANDESITE = new BlockMatchTest(Blocks.ANDESITE);
        public static final RuleTest BLOCK_DIORITE = new BlockMatchTest(Blocks.DIORITE);
        public static final RuleTest BLOCK_GRANITE = new BlockMatchTest(Blocks.GRANITE);
        public static final RuleTest BLOCK_CALCITE = new BlockMatchTest(Blocks.CALCITE);
        public static final RuleTest BLOCK_TUFF = new BlockMatchTest(Blocks.TUFF);
        public static final RuleTest BLOCK_DRIPSTONE = new BlockMatchTest(Blocks.DRIPSTONE_BLOCK);
        public static final RuleTest BLOCK_NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
        public static final RuleTest BLOCK_LIMBIPETRA = new BlockMatchTest(Registrar.LIMBIPETRA_SET.getBaseStone().get());
        public static final RuleTest BLOCK_HAEMOLITE = new BlockMatchTest(Registrar.HAEMOLITE_SET.getBaseStone().get());
        public static final RuleTest BLOCK_PACKED_HAEMOLITE = new BlockMatchTest(Registrar.PACKED_HAEMOLITE_SET.getBaseStone().get());

        public static final RuleTest TAG_SANDSTONE = new TagMatchTest(SpiroTags.Blocks.SANDSTONE_ORE_REPLACEABLES);
        public static final RuleTest TAG_RED_SANDSTONE = new TagMatchTest(SpiroTags.Blocks.RED_SANDSTONE_ORE_REPLACEABLES);
        public static final RuleTest TAG_BASALT = new TagMatchTest(SpiroTags.Blocks.BASALT_ORE_REPLACEABLES);
        public static final RuleTest TAG_TERRACOTTA = new TagMatchTest(BlockTags.TERRACOTTA);
    }

    /** This is placed last in the file due to how obscenely long it tends to get.
     * I'll have to look into finding a more efficient method of doing this that doesn't use as many lines of code.
     * That being said... TODO: Find a more efficient way of doing this!!! */
    public static class OreTargets
    {
        public static final Supplier<List<OreConfiguration.TargetBlockState>> COAL_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.COAL_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.COAL_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.COAL_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.COAL_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.COAL_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.COAL_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.COAL_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.COAL_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.COAL_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.COAL_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.COAL_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.COAL_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.COAL_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.COAL_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.COAL_ORES, StoneMaterial.CALCITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> IRON_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.IRON_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.IRON_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.IRON_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.IRON_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.IRON_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.IRON_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.IRON_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.IRON_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.IRON_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.IRON_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.IRON_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.IRON_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.IRON_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.IRON_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.IRON_ORES, StoneMaterial.GRANITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> COPPER_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.COPPER_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.COPPER_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.COPPER_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.COPPER_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.COPPER_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.COPPER_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.COPPER_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.COPPER_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.COPPER_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.COPPER_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.COPPER_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.COPPER_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.COPPER_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.COPPER_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.COPPER_ORES, StoneMaterial.ANDESITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> GOLD_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.GOLD_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.GOLD_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.GOLD_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.GOLD_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.GOLD_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.GOLD_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.GOLD_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, Blocks.NETHER_GOLD_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.GOLD_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.GOLD_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.GOLD_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.GOLD_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.GOLD_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.GOLD_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.GOLD_ORES, StoneMaterial.GRANITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> DIAMOND_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.DIAMOND_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.DIAMOND_ORES, StoneMaterial.ANDESITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> REDSTONE_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.REDSTONE_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.REDSTONE_ORES, StoneMaterial.BASALT))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> EMERALD_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.EMERALD_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.EMERALD_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.EMERALD_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.EMERALD_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.EMERALD_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.EMERALD_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.EMERALD_ORES, StoneMaterial.BASALT))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> QUARTZ_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.STONE)),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.DEEPSLATE)),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, Blocks.NETHER_QUARTZ_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.QUARTZ_ORES, StoneMaterial.GRANITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> LAPIS_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, Blocks.LAPIS_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState()),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.LAPIS_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.LAPIS_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.LAPIS_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.LAPIS_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.LAPIS_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.LAPIS_ORES, StoneMaterial.CALCITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> LEAD_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, rDBS(Registrar.LEAD_ORES, StoneMaterial.STONE)),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, rDBS(Registrar.LEAD_ORES, StoneMaterial.DEEPSLATE)),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.LEAD_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.LEAD_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.LEAD_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.LEAD_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.LEAD_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.LEAD_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.LEAD_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.LEAD_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.LEAD_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.LEAD_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.LEAD_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.LEAD_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.LEAD_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.LEAD_ORES, StoneMaterial.CALCITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> RUBY_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, rDBS(Registrar.RUBY_ORES, StoneMaterial.STONE)),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, rDBS(Registrar.RUBY_ORES, StoneMaterial.DEEPSLATE)),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.RUBY_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.RUBY_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.RUBY_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.RUBY_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.RUBY_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.RUBY_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.RUBY_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.RUBY_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.RUBY_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.RUBY_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.RUBY_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.RUBY_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.RUBY_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.RUBY_ORES, StoneMaterial.CALCITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> METHANE_ICE_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.STONE)),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.DEEPSLATE)),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.HAEMOLITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.PACKED_HAEMOLITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.METHANE_ICE_ORES, StoneMaterial.BASALT))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> CRIMSONITE_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.STONE)),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.DEEPSLATE)),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.LIMBIPETRA)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.CRIMSONITE_ORES, StoneMaterial.CALCITE))
        ));

        public static final Supplier<List<OreConfiguration.TargetBlockState>> STRAVIMITE_ORE_TARGETS
                = Suppliers.memoize(() -> List.of(
                OreConfiguration.target(TargetRules.BLOCK_STONE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.STONE)),
                OreConfiguration.target(TargetRules.BLOCK_DEEPSLATE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.DEEPSLATE)),
                OreConfiguration.target(TargetRules.BLOCK_ANDESITE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.ANDESITE)),
                OreConfiguration.target(TargetRules.BLOCK_GRANITE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.GRANITE)),
                OreConfiguration.target(TargetRules.BLOCK_DIORITE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.DIORITE)),
                OreConfiguration.target(TargetRules.BLOCK_CALCITE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_TUFF, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.TUFF)),
                OreConfiguration.target(TargetRules.BLOCK_DRIPSTONE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.DRIPSTONE)),
                OreConfiguration.target(TargetRules.BLOCK_NETHERRACK, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.NETHERRACK)),
                OreConfiguration.target(TargetRules.BLOCK_LIMBIPETRA, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.BLOCK_HAEMOLITE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.BLOCK_PACKED_HAEMOLITE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.CALCITE)),
                OreConfiguration.target(TargetRules.TAG_BASALT, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.BASALT)),
                OreConfiguration.target(TargetRules.TAG_SANDSTONE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_RED_SANDSTONE, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.RED_SANDSTONE)),
                OreConfiguration.target(TargetRules.TAG_TERRACOTTA, rDBS(Registrar.STRAVIMITE_ORES, StoneMaterial.BASALT))
        ));
    }

    /** Returns the default block state. */
    private static BlockState rDBS(OreCollection oreType, StoneMaterial baseStone)
    { return oreType.getBulkData().get(baseStone).getBlock().get().defaultBlockState(); }
}

