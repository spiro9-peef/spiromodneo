package com.github.peeftube.spiromodneo.datagen.modules.tags;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.biome.NeoBiomes;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BiomeTagDataProv extends BiomeTagsProvider
{
    public BiomeTagDataProv(PackOutput output,
            CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper)
    { super(output, provider, SpiroMod.MOD_ID, existingFileHelper); }

    @Override
    protected void addTags(HolderLookup.Provider lookup)
    {
        tag(BiomeTags.IS_OVERWORLD)
                .add(NeoBiomes.OVERWORLD_RUBBER_FOREST);

        tag(BiomeTags.IS_JUNGLE)
                .add(NeoBiomes.OVERWORLD_RUBBER_FOREST);

        tag(SpiroTags.Biomes.IS_LUSH_CAVES_LIKE)
                .add(Biomes.LUSH_CAVES)
                .add(NeoBiomes.AZURE_CAVERNS)
                .add(NeoBiomes.RUBY_CAVERNS)
                .add(NeoBiomes.VERDANT_CAVERNS)
                .add(NeoBiomes.GILDED_CAVERNS)
                .add(NeoBiomes.AMETHYST_CAVERNS);
        
        tag(SpiroTags.Biomes.IS_AZURE_CAVE_BIOME)
                .add(NeoBiomes.AZURE_CAVERNS);
        tag(SpiroTags.Biomes.IS_RUBY_CAVE_BIOME)
                .add(NeoBiomes.RUBY_CAVERNS);
        tag(SpiroTags.Biomes.IS_VERDANT_CAVE_BIOME)
                .add(NeoBiomes.VERDANT_CAVERNS);
        tag(SpiroTags.Biomes.IS_GILDED_CAVE_BIOME)
                .add(NeoBiomes.GILDED_CAVERNS);
        tag(SpiroTags.Biomes.IS_AMETHYST_CAVE_BIOME)
                .add(NeoBiomes.AMETHYST_CAVERNS);

        tag(SpiroTags.Biomes.RUBY_SPAWNABLE)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(SpiroTags.Biomes.IS_LUSH_CAVES_LIKE);

        tag(SpiroTags.Biomes.LIMBO_GARDEN)
                .add(NeoBiomes.NETHER_LIMBO_GARDEN);
        tag(SpiroTags.Biomes.getColdNetherTag())
                .addTag(SpiroTags.Biomes.LIMBO_GARDEN);
        tag(SpiroTags.Biomes.IS_CUSTOM_NETHER)
                .addTag(SpiroTags.Biomes.getColdNetherTag());
        tag(SpiroTags.Biomes.IS_CRIMSON_NETHER)
                .add(Biomes.CRIMSON_FOREST);
        tag(SpiroTags.Biomes.IS_WARPED_NETHER)
                .add(Biomes.WARPED_FOREST);
        tag(BiomeTags.IS_NETHER)
                .addTag(SpiroTags.Biomes.IS_CUSTOM_NETHER)
                .addTag(SpiroTags.Biomes.IS_CRIMSON_NETHER)
                .addTag(SpiroTags.Biomes.IS_WARPED_NETHER);

        tag(SpiroTags.Biomes.IS_VANILLA_AND_CAN_HAVE_MAPLE)
                .add(Biomes.FOREST)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.WINDSWEPT_FOREST);

        tag(SpiroTags.Biomes.IS_VANILLA_AND_CAN_HAVE_RUBBER_01)
                .add(Biomes.JUNGLE);

        tag(SpiroTags.Biomes.IS_MINE_AND_CAN_HAVE_RUBBER)
                .add(NeoBiomes.OVERWORLD_RUBBER_FOREST);

        tag(SpiroTags.Biomes.YELLOW_SAND_BIOME)
                .add(Biomes.DESERT)
                .add(Biomes.BEACH)
                .add(Biomes.SNOWY_BEACH);

        tag(SpiroTags.Biomes.RED_SAND_BIOME)
                .addTag(BiomeTags.IS_BADLANDS);
    }
}
