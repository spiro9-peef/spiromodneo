package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.util.wood.growers.CustomTreeGrowers;
import net.minecraft.world.level.block.grower.TreeGrower;

public enum WoodMaterial
{
    OAK("oak", TreeGrower.OAK),
    BIRCH("birch", TreeGrower.BIRCH),
    SPRUCE("spruce", TreeGrower.SPRUCE),
    JUNGLE("jungle", TreeGrower.JUNGLE),
    ACACIA("acacia", TreeGrower.ACACIA),
    DARK_OAK("dark_oak", TreeGrower.DARK_OAK),
    CHERRY("cherry", TreeGrower.CHERRY),
    CRIMSON_FUNGUS("crimson_fungus", null, true),
    WARPED_FUNGUS("warped_fungus", null, true),
    MANGROVE("mangrove", TreeGrower.MANGROVE, false, true),

    // Modded types
    ASHEN_OAK("ashen_oak", CustomTreeGrowers.ASHEN_OAK),
    ASHEN_BIRCH("ashen_birch", CustomTreeGrowers.ASHEN_BIRCH),
    STONEWOOD("stonewood", CustomTreeGrowers.STONEWOOD,
            false, false, true),

    // Modded tappables
    RUBBERWOOD("rubberwood", CustomTreeGrowers.RUBBERWOOD),
    MAPLE("maple", CustomTreeGrowers.MAPLE);

    private final String name;
    private final boolean netherFungusLike;
    private final boolean mangroveLike;
    private final TreeGrower treeGrower;
    private final boolean stonePlantable;

    WoodMaterial(String name, TreeGrower treeGrower)
    { this.name = name; this.treeGrower = treeGrower;
        this.netherFungusLike = false; this.mangroveLike = false; this.stonePlantable = false; }

    WoodMaterial(String name, TreeGrower treeGrower, boolean isNetherFungusLike)
    { this.name = name; this.treeGrower = treeGrower;
        this.netherFungusLike = isNetherFungusLike; this.mangroveLike = false; this.stonePlantable = false; }

    WoodMaterial(String name, TreeGrower treeGrower, boolean isNetherFungusLike, boolean isMangroveLike)
    { this.name = name; this.treeGrower = treeGrower;
        this.netherFungusLike = isNetherFungusLike; this.mangroveLike = isMangroveLike; this.stonePlantable = false; }

    WoodMaterial(String name, TreeGrower treeGrower, boolean isNetherFungusLike, boolean isMangroveLike,
                 boolean stonePlantable)
    { this.name = name; this.treeGrower = treeGrower;
        this.netherFungusLike = isNetherFungusLike; this.mangroveLike = isMangroveLike;
        this.stonePlantable = stonePlantable; }

    public String getName() { return name; }
    public boolean isLikeNetherFungus() { return netherFungusLike; }
    public boolean isLikeMangroves() { return mangroveLike; }
    public TreeGrower getGrower() { return treeGrower; }
    public boolean isStonePlantable() { return stonePlantable; }
}
