package com.github.peeftube.spiromodneo.datagen;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.datagen.modules.models.BlockstateDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.models.ItemModelDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.lang.en_us.EN_USLangDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.loot.LootModDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.loot.LootTableDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.recipe.RecipeDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.tags.BiomeTagDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.tags.BlockTagDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.tags.ItemTagDataProv;
import com.github.peeftube.spiromodneo.datagen.modules.world.CustomDataMapProv;
import com.github.peeftube.spiromodneo.datagen.modules.world.WorldgenDataProv;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;

@EventBusSubscriber(modid = SpiroMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGeneration
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator      generator          = event.getGenerator();
        PackOutput         output             = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        BlockTagDataProv bTags = new BlockTagDataProv(output, event.getLookupProvider(), existingFileHelper);
        generator.addProvider(true, bTags);
        generator.addProvider(true, new ItemTagDataProv(output, event.getLookupProvider(),
                bTags.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new BiomeTagDataProv(output, event.getLookupProvider(), existingFileHelper));

        generator.addProvider(true, new RecipeDataProv(output, event.getLookupProvider()));
        generator.addProvider(true, new BlockstateDataProv(output, existingFileHelper));
        generator.addProvider(true, new ItemModelDataProv(output, existingFileHelper));
        generator.addProvider(true, new LootTableDataProv(output, event.getLookupProvider()));
        generator.addProvider(true, new LootModDataProv(output, event.getLookupProvider()));

        // Language providers.
        generator.addProvider(true, new EN_USLangDataProv(output, "en_us"));

        generator.addProvider(true, new WorldgenDataProv(output,
                event.getLookupProvider(), Set.of(SpiroMod.MOD_ID, "minecraft")));
        generator.addProvider(true, new CustomDataMapProv(output,
                event.getLookupProvider()));
    }
}