package com.github.peeftube.spiromodneo;

import com.github.peeftube.spiromodneo.client.renderer.blockentity.ExtensibleChestRenderer;
import com.github.peeftube.spiromodneo.core.init.InitializeBlockRenderTypes;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.region.NetherColdRegion;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.region.OverworldAlternativeRegion1;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.region.OverworldStrangeRegion1;
import com.github.peeftube.spiromodneo.core.init.registry.data.Soil;
import com.github.peeftube.spiromodneo.core.screens.ManualCrusherScreen;
import com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.custombiome.NetherColdRegionSourceRules;
import com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.custombiome.OverworldCustomRegionSourceRules;
import com.github.peeftube.spiromodneo.util.RLUtility;
import com.github.peeftube.spiromodneo.util.moss.MossType;
import external.com.github.auburn.fastnoiselite.FastNoiseLite;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.Random;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SpiroMod.MOD_ID)
public class SpiroMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "spiromodneo";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Mod RNG (Java)
    public static final Random RNG = new Random();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SpiroMod(IEventBus modEventBus, ModContainer modContainer)
    {
        Registrar.init();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            Regions.register(new NetherColdRegion(RLUtility.makeRL("spiro_cold_nether_region"), 1));
            Regions.register(new OverworldAlternativeRegion1
                    (RLUtility.makeRL("spiro_alt_region01"), 3));
            Regions.register(new OverworldStrangeRegion1
                    (RLUtility.makeRL("spiro_strange_region01"), 1));

            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, MOD_ID,
                    NetherColdRegionSourceRules.rules());

            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
                    OverworldCustomRegionSourceRules.rules());
        });

        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {}
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            InitializeBlockRenderTypes.go();
        }

        @SubscribeEvent
        public static void onScreenRegistration(RegisterMenuScreensEvent event)
        {
            event.register(Registrar.MANUAL_CRUSHER_MENU.get(), ManualCrusherScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event)
        {
            for (Soil s : Soil.values())
            {
                if (s != Soil.DIRT)
                {
                    event.register((st, l, p, i) ->
                            {
                                if (l != null && p != null)
                                { return noiseBasedColorMod(BiomeColors.getAverageGrassColor(l, p), p); }
                                else { return GrassColor.getDefaultColor(); }
                            },
                            Registrar.GRASS_TYPE.bulkData().get(s).getBlock().get(),
                            Registrar.VITALIUM_TYPE.bulkData().get(s).getBlock().get());
                }
                else
                {
                    event.register((st, l, p, i) ->
                            {
                                if (l != null && p != null)
                                { return noiseBasedColorMod(BiomeColors.getAverageGrassColor(l, p), p); }
                                else { return GrassColor.getDefaultColor(); }
                            },
                            Registrar.GRASS_TYPE.bulkData().get(s).getBlock().get(),
                            Registrar.VITALIUM_TYPE.bulkData().get(s).getBlock().get());
                }
            }

            event.register((st, l, p, i) ->
                    p != null ? noiseBasedColorMod(FoliageColor.getBirchColor(), p) : FoliageColor.getBirchColor(),

                    Blocks.BIRCH_LEAVES,
                    Registrar.ASHEN_BIRCH_WOOD.getBaseLeaves().get());

            event.register((st, l, p, i) ->
                    {
                        if (l != null && p != null)
                        { return noiseBasedColorMod(BiomeColors.getAverageFoliageColor(l, p), p); }
                        else { return FoliageColor.getDefaultColor(); }
                    },
                    Blocks.ACACIA_LEAVES,
                    Blocks.DARK_OAK_LEAVES,
                    Blocks.JUNGLE_LEAVES,
                    Blocks.MANGROVE_LEAVES,
                    Blocks.OAK_LEAVES,
                    Blocks.SPRUCE_LEAVES,
                    Registrar.ASHEN_OAK_WOOD.getBaseLeaves().get(),
                    Registrar.RUBBER_WOOD.wood().getBaseLeaves().get(),
                    Registrar.MAPLE_WOOD.wood().getBaseLeaves().get());

            event.register((st, l, p, i) ->
                            p != null ? noiseBasedColorMod(-10784593, p) : -10784593,
                    Registrar.AZURE_STONEWOOD.leaves().getBlock().get(),
                    Registrar.AZURE_STONEWOOD.sapling().getBlock().get(),
                    Registrar.AZURE_GLOWMOSS.bulkData().get(MossType.MOSS_BLOCK).getBlock().get(),
                    Registrar.AZURE_GLOWMOSS.bulkData().get(MossType.MOSS_CARPET).getBlock().get());

            event.register((st, l, p, i) ->
                            p != null ? noiseBasedColorMod(-5744252, p) : -5744252,
                    Registrar.RUBY_STONEWOOD.leaves().getBlock().get(),
                    Registrar.RUBY_STONEWOOD.sapling().getBlock().get(),
                    Registrar.RUBY_GLOWMOSS.bulkData().get(MossType.MOSS_BLOCK).getBlock().get(),
                    Registrar.RUBY_GLOWMOSS.bulkData().get(MossType.MOSS_CARPET).getBlock().get());

            event.register((st, l, p, i) ->
                            p != null ? noiseBasedColorMod(-10835342, p) : -10835342,
                    Registrar.VERDANT_STONEWOOD.leaves().getBlock().get(),
                    Registrar.VERDANT_STONEWOOD.sapling().getBlock().get(),
                    Registrar.VERDANT_GLOWMOSS.bulkData().get(MossType.MOSS_BLOCK).getBlock().get(),
                    Registrar.VERDANT_GLOWMOSS.bulkData().get(MossType.MOSS_CARPET).getBlock().get());

            event.register((st, l, p, i) ->
                            p != null ? noiseBasedColorMod(-6052521, p) : -6052521,
                    Registrar.GILDED_STONEWOOD.leaves().getBlock().get(),
                    Registrar.GILDED_STONEWOOD.sapling().getBlock().get(),
                    Registrar.GILDED_GLOWMOSS.bulkData().get(MossType.MOSS_BLOCK).getBlock().get(),
                    Registrar.GILDED_GLOWMOSS.bulkData().get(MossType.MOSS_CARPET).getBlock().get());

            event.register((st, l, p, i) ->
                            p != null ? noiseBasedColorMod(-7906643, p) : -7906643,
                    Registrar.AMETHYST_STONEWOOD.leaves().getBlock().get(),
                    Registrar.AMETHYST_STONEWOOD.sapling().getBlock().get(),
                    Registrar.AMETHYST_GLOWMOSS.bulkData().get(MossType.MOSS_BLOCK).getBlock().get(),
                    Registrar.AMETHYST_GLOWMOSS.bulkData().get(MossType.MOSS_CARPET).getBlock().get());
        }

        @SubscribeEvent
        public static void onRegisterBERs(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerBlockEntityRenderer(Registrar.CHEST_ENTITYTYPE.get(), ExtensibleChestRenderer::new);
            event.registerBlockEntityRenderer(Registrar.TRAPPED_CHEST_ENTITYTYPE.get(), ExtensibleChestRenderer::new);

            event.registerBlockEntityRenderer(Registrar.SIGN_ENTITYTYPE.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(Registrar.HANGING_SIGN_ENTITYTYPE.get(), HangingSignRenderer::new);
        }

        private static int noiseBasedColorMod(int c, BlockPos p)
        {
            int isoR = (c >> 16) & 0xFF;
            int isoG = (c >> 8) & 0xFF;
            int isoB = c & 0xFF;
            int isoA = (c >> 24) & 0xFF; // Usually 255 or 0xFF for opaque blocks

            FastNoiseLite n0 = new FastNoiseLite();
            n0.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            n0.SetSeed(1202412885);

            float scale0 = 32.0f;
            float nX0 = p.getX() * scale0;
            float nY0 = p.getY() * scale0;
            float nZ0 = p.getZ() * scale0;
            double r0 = (n0.GetNoise(nX0, nY0, nZ0) * 0.375);

            FastNoiseLite n1 = new FastNoiseLite();
            n1.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            n1.SetSeed(13265943);

            float scale1 = 16.0f;
            float nX1 = p.getX() * scale1;
            float nY1 = p.getY() * scale1;
            float nZ1 = p.getZ() * scale1;
            double r1 = (n1.GetNoise(nX1, nY1, nZ1) * 0.25);

            int posHash = (p.getX() * 73856093) ^ (p.getY() * 19349663) ^ (p.getZ() * 83492791);
            FastNoiseLite n2 = new FastNoiseLite();
            n2.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
            n2.SetSeed(posHash);

            float nX2 = p.getX() * (scale1 * 1.25f);
            float nY2 = p.getY() * (scale1 * 1.25f);
            float nZ2 = p.getZ() * (scale1 * 1.25f);
            double r2 = (n2.GetNoise(nX2, nY2, nZ2) * 0.125);

            double r = 1.0 + r0 + r1 + r2;

            int modR = Math.clamp((int)(isoR * r), 0, 255);
            int modG = Math.clamp((int)(isoG * r), 0, 255);
            int modB = Math.clamp((int)(isoB * r), 0, 255);

            return (isoA << 24) | (modR << 16) | (modG << 8) | modB;
        }
    }
}
