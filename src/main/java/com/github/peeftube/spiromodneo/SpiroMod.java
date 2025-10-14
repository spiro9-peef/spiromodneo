package com.github.peeftube.spiromodneo;

import com.github.peeftube.spiromodneo.client.renderer.blockentity.ExtensibleChestRenderer;
import com.github.peeftube.spiromodneo.core.init.InitializeBlockRenderTypes;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.content.worldgen.region.NetherColdRegion;
import com.github.peeftube.spiromodneo.core.init.registry.data.Soil;
import com.github.peeftube.spiromodneo.core.screens.ManualCrusherScreen;
import com.github.peeftube.spiromodneo.datagen.modules.world.util.helpers.custombiome.NetherColdRegionSourceRules;
import com.github.peeftube.spiromodneo.util.RLUtility;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
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

            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, MOD_ID,
                    NetherColdRegionSourceRules.rules());
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
                                if (l != null && p != null) { return BiomeColors.getAverageGrassColor(l, p); }
                                else { return GrassColor.getDefaultColor(); }
                            },
                            Registrar.GRASS_TYPE.bulkData().get(s).getBlock().get(),
                            Registrar.VITALIUM_TYPE.bulkData().get(s).getBlock().get());
                }
                else
                {
                    event.register((st, l, p, i) ->
                            {
                                if (l != null && p != null) { return BiomeColors.getAverageGrassColor(l, p); }
                                else { return GrassColor.getDefaultColor(); }
                            },
                            Registrar.VITALIUM_TYPE.bulkData().get(s).getBlock().get());
                }
            }

            event.register((st, l, p, i) ->
            { return FoliageColor.getBirchColor(); }, Registrar.ASHEN_BIRCH_WOOD.getBaseLeaves().get());

            event.register((st, l, p, i) ->
                    {
                        if (l != null && p != null) { return BiomeColors.getAverageFoliageColor(l, p); }
                        else { return FoliageColor.getDefaultColor(); }
                    },
                    Registrar.ASHEN_OAK_WOOD.getBaseLeaves().get(),
                    Registrar.RUBBER_WOOD.wood().getBaseLeaves().get(),
                    Registrar.MAPLE_WOOD.wood().getBaseLeaves().get());
        }

        @SubscribeEvent
        public static void onRegisterBERs(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerBlockEntityRenderer(Registrar.CHEST_ENTITYTYPE.get(), ExtensibleChestRenderer::new);
            event.registerBlockEntityRenderer(Registrar.TRAPPED_CHEST_ENTITYTYPE.get(), ExtensibleChestRenderer::new);

            event.registerBlockEntityRenderer(Registrar.SIGN_ENTITYTYPE.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(Registrar.HANGING_SIGN_ENTITYTYPE.get(), HangingSignRenderer::new);
        }
    }
}
