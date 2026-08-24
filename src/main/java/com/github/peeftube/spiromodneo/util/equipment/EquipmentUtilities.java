package com.github.peeftube.spiromodneo.util.equipment;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.EquipmentMaterial;
import com.github.peeftube.spiromodneo.util.DataCheckResult;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public interface EquipmentUtilities
{
    static EquipmentData generateEquipmentData(EquipmentMaterial mat)
    {
        ToolSet tools = gatherToolSet(mat); ArmorSet armor = gatherArmorSet(mat);
        Supplier<Item> horseArmor = null;

        switch(mat)
        {
            // Vanilla
            case LEATHER -> { horseArmor = () -> Items.LEATHER_HORSE_ARMOR; }
            case IRON -> { horseArmor = () -> Items.IRON_HORSE_ARMOR; }
            case GOLD -> { horseArmor = () -> Items.GOLDEN_HORSE_ARMOR; }
            case DIAMOND -> { horseArmor = () -> Items.DIAMOND_HORSE_ARMOR; }
            default -> { }
        }

        return new EquipmentData(mat.getName(), tools, armor, horseArmor);
    }

    private static ToolSet gatherToolSet(EquipmentMaterial mat)
    {
        switch(mat)
        {
            // Vanilla cases
            case WOOD -> { return new ToolSet(() -> Items.WOODEN_SWORD, () -> Items.WOODEN_SHOVEL,
                    () -> Items.WOODEN_HOE, () -> Items.WOODEN_AXE, () -> Items.WOODEN_PICKAXE); }
            case STONE -> { return new ToolSet(() -> Items.STONE_SWORD, () -> Items.STONE_SHOVEL,
                    () -> Items.STONE_HOE, () -> Items.STONE_AXE, () -> Items.STONE_PICKAXE); }
            case IRON -> { return new ToolSet(() -> Items.IRON_SWORD, () -> Items.IRON_SHOVEL,
                    () -> Items.IRON_HOE, () -> Items.IRON_AXE, () -> Items.IRON_PICKAXE); }
            case GOLD -> { return new ToolSet(() -> Items.GOLDEN_SWORD, () -> Items.GOLDEN_SHOVEL,
                    () -> Items.GOLDEN_HOE, () -> Items.GOLDEN_AXE, () -> Items.GOLDEN_PICKAXE); }
            case DIAMOND -> { return new ToolSet(() -> Items.DIAMOND_SWORD, () -> Items.DIAMOND_SHOVEL,
                    () -> Items.DIAMOND_HOE, () -> Items.DIAMOND_AXE, () -> Items.DIAMOND_PICKAXE); }
            case NETHERITE -> { return new ToolSet(() -> Items.NETHERITE_SWORD, () -> Items.NETHERITE_SHOVEL,
                    () -> Items.NETHERITE_HOE, () -> Items.NETHERITE_AXE, () -> Items.NETHERITE_PICKAXE); }

            // Modded cases run through another method.
            case LEAD, COPPER, STEEL, SHARPWOOD, FLINT -> { return createNewToolSet(mat); }

            // Fallback case
            default -> { return null; }
        }
    }

    private static ArmorSet gatherArmorSet(EquipmentMaterial mat)
    {
        switch(mat)
        {
            // Vanilla cases
            case LEATHER -> { return new ArmorSet(() -> Items.LEATHER_HELMET, () -> Items.LEATHER_CHESTPLATE,
                    () -> Items.LEATHER_LEGGINGS, () -> Items.LEATHER_BOOTS); }
            case CHAIN -> { return new ArmorSet(() -> Items.CHAINMAIL_HELMET, () -> Items.CHAINMAIL_CHESTPLATE,
                    () -> Items.CHAINMAIL_LEGGINGS, () -> Items.CHAINMAIL_BOOTS); }
            case IRON -> { return new ArmorSet(() -> Items.IRON_HELMET, () -> Items.IRON_CHESTPLATE,
                    () -> Items.IRON_LEGGINGS, () -> Items.IRON_BOOTS); }
            case GOLD -> { return new ArmorSet(() -> Items.GOLDEN_HELMET, () -> Items.GOLDEN_CHESTPLATE,
                    () -> Items.GOLDEN_LEGGINGS, () -> Items.GOLDEN_BOOTS); }
            case DIAMOND -> { return new ArmorSet(() -> Items.DIAMOND_HELMET, () -> Items.DIAMOND_CHESTPLATE,
                    () -> Items.DIAMOND_LEGGINGS, () -> Items.DIAMOND_BOOTS); }
            case NETHERITE -> { return new ArmorSet(() -> Items.NETHERITE_HELMET, () -> Items.NETHERITE_CHESTPLATE,
                    () -> Items.NETHERITE_LEGGINGS, () -> Items.NETHERITE_BOOTS); }

            // Modded cases run through another method.
            case LEAD, COPPER, STEEL -> { return createNewArmorSet(mat); }

            // Fallback case
            default -> { return null; }
        }
    }

    /// TODO: Implement flavor text
    private static ToolSet createNewToolSet(EquipmentMaterial mat)
    {
        Supplier<SwordItem> sword = Registrar.ITEMS.register(mat.getName() + "_sword",
                () -> new SwordItem(mat.getToolTier(), new Item.Properties()));
        Supplier<ShovelItem> shovel = Registrar.ITEMS.register(mat.getName() + "_shovel",
                () -> new ShovelItem(mat.getToolTier(), new Item.Properties()));
        Supplier<HoeItem> hoe = Registrar.ITEMS.register(mat.getName() + "_hoe",
                () -> new HoeItem(mat.getToolTier(), new Item.Properties()));
        Supplier<AxeItem> axe = Registrar.ITEMS.register(mat.getName() + "_axe",
                () -> new AxeItem(mat.getToolTier(), new Item.Properties()));
        Supplier<PickaxeItem> pickaxe = Registrar.ITEMS.register(mat.getName() + "_pickaxe",
                () -> new PickaxeItem(mat.getToolTier(), new Item.Properties()));

        return new ToolSet(sword, shovel, hoe, axe, pickaxe);
    }

    /// TODO: Implement flavor text
    private static ArmorSet createNewArmorSet(EquipmentMaterial mat)
    {
        List<DeferredItem<ArmorItem>> armorItems = new ArrayList<>();

        for (ArmorItem.Type t: ArmorItem.Type.values())
        { if (!t.equals(ArmorItem.Type.BODY))
            { armorItems.add(Registrar.ITEMS.register(mat.getName() + "_" + t.getName(),
                () -> new ArmorItem(mat.getArmorTier(), t, new Item.Properties()))); }
        }

        DeferredItem<ArmorItem> helmet = armorItems.get(0);
        DeferredItem<ArmorItem> chestplate = armorItems.get(1);
        DeferredItem<ArmorItem> leggings = armorItems.get(2);
        DeferredItem<ArmorItem> boots = armorItems.get(3);

        return new ArmorSet(helmet, chestplate, leggings, boots);
    }

    default DataCheckResult checkIfNullThenPass(Object toCheck)
    { return new DataCheckResult(Objects.nonNull(toCheck), toCheck); }
}
