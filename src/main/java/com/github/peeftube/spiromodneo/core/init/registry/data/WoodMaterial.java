package com.github.peeftube.spiromodneo.core.init.registry.data;

import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.util.wood.growers.CustomTreeGrowers;
import net.minecraft.world.level.block.grower.TreeGrower;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

public enum WoodMaterial
{
    OAK(getMOID(MOID.OAK), TreeGrower.OAK),
    BIRCH(getMOID(MOID.BIRCH), TreeGrower.BIRCH),
    SPRUCE(getMOID(MOID.SPRUCE), TreeGrower.SPRUCE),
    JUNGLE(getMOID(MOID.JUNGLE), TreeGrower.JUNGLE),
    ACACIA(getMOID(MOID.ACACIA), TreeGrower.ACACIA),
    DARK_OAK(getMOID(MOID.DARK_OAK), TreeGrower.DARK_OAK),
    CHERRY(getMOID(MOID.CHERRY), TreeGrower.CHERRY),
    CRIMSON_FUNGUS(getMOID(MOID.CRIMSON_FUNGUS), null, true),
    WARPED_FUNGUS(getMOID(MOID.WARPED_FUNGUS), null, true),
    MANGROVE(getMOID(MOID.MANGROVE), TreeGrower.MANGROVE, false, true),

    // Modded types
    ASHEN_OAK(getMOID(MOID.ASHEN_OAK), CustomTreeGrowers.ASHEN_OAK),
    ASHEN_BIRCH(getMOID(MOID.ASHEN_BIRCH), CustomTreeGrowers.ASHEN_BIRCH),
    STONEWOOD(getMOID(MOID.STONEWOOD), CustomTreeGrowers.STONEWOOD,
            false, false, true),

    // Modded tappables
    RUBBERWOOD(getMOID(MOID.RUBBERWOOD), CustomTreeGrowers.RUBBERWOOD),
    MAPLE(getMOID(MOID.MAPLE), CustomTreeGrowers.MAPLE);

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
