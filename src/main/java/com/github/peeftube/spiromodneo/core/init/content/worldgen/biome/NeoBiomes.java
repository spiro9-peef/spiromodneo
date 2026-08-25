package com.github.peeftube.spiromodneo.core.init.content.worldgen.biome;

import com.github.peeftube.spiromodneo.util.RLUtility;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class NeoBiomes
{
    /**
     * A biome reminiscent of the overworld, but with ashen trees and fake skies,
     * made of a strange rock unique to the biome. This place scares the natives. <p>
     * "It's cold... too cold..."
     */
    public static final ResourceKey<Biome> NETHER_LIMBO_GARDEN =
            registerKey("spiro_nether_limbo_garden");

    /**
     * A strange alternative to the jungle biome, with trees that bleed white
     * and layers of smooth basalt. Perhaps something here will prove of use? <p>
     * "It's sticky!"
     */
    public static final ResourceKey<Biome> OVERWORLD_RUBBER_FOREST =
            registerKey("spiro_overworld_rubber_forest");

    /**
     * A strange alternative to the lush caves biome, with a vague bioluminescence
     * and strange stone trees. Foliage here takes on a blue-ish hue. <p>
     * There are also berry bushes here with a ghostly appearance. <p>
     * "It's... blue?"
     */
    public static final ResourceKey<Biome> AZURE_CAVERNS =
            registerKey("spiro_azure_caverns");

    /**
     * A strange alternative to the lush caves biome, with a vague bioluminescence
     * and strange stone trees. Foliage here takes on a vaguely purple hue. <p>
     * "It glistens like amethyst!"
     */
    public static final ResourceKey<Biome> AMETHYST_CAVERNS =
            registerKey("spiro_amethyst_caverns");

    /**
     * A strange alternative to the lush caves biome, with a vague bioluminescence
     * and strange stone trees. Foliage here takes on a lush green hue. <p>
     * "It's like an underground forest!"
     */
    public static final ResourceKey<Biome> VERDANT_CAVERNS =
            registerKey("spiro_verdant_caverns");

    /**
     * A strange alternative to the lush caves biome, with a vague bioluminescence
     * and strange stone trees. Foliage here takes on a golden hue. <p>
     * Additionally, gold is easier to find here. <p>
     * "You see, Mr. Steve... I love goooooooold!"
     */
    public static final ResourceKey<Biome> GILDED_CAVERNS =
            registerKey("spiro_gilded_caverns");

    /**
     * A strange alternative to the lush caves biome, with a vague bioluminescence
     * and strange stone trees. Foliage here takes on a reddish hue. <p>
     * "This is just a bootleg Nether!"
     */
    public static final ResourceKey<Biome> RUBY_CAVERNS =
            registerKey("spiro_ruby_caverns");

    /**
     * A living tomb, of flesh and gore, that not even the creatures of the dark and night
     * would enter, lest it claim them for itself. Do you dare enter the depths of the damned?
     */
    public static final ResourceKey<Biome> FLESH_CAVERNS =
            registerKey("spiro_flesh_caverns");

    /**
     * A black-sand river biome based on the real-life Mattole River Outlet.
     */
    public static final ResourceKey<Biome> MATTOLE_RIVER =
            registerKey("spiro_black_sand_river");

    /**
     * Needs no explanation, I hope.
     */
    public static final ResourceKey<Biome> TROPICAL_BEACH =
            registerKey("spiro_white_sand_beach");

    private static ResourceKey<Biome> registerKey(String name)
    { return ResourceKey.create(Registries.BIOME, RLUtility.makeRL(name)); }
}
