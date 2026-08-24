package com.github.peeftube.spiromodneo.datagen.modules.lang.util;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.datagen.modules.lang.util.tooltips.LoreCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TooltipUtils
{
    public static String translateLoreKey(String oID, int index, LoreCategory category)
    { return String.format("%s.%s.tooltip.lore.%s.%d", SpiroMod.MOD_ID, oID, category.getKey(), index); }

    private static MutableComponent lore(String oID, int index, LoreCategory category)
    { return Component.translatable(translateLoreKey(oID, index, category)); }

    public static MutableComponent flavorText(String oID, int index, ChatFormatting color)
    { return lore(oID, index, LoreCategory.FLAVOR_TEXT).withStyle(color); }

    public static MutableComponent hint(String oID, int index)
    {
        return lore(oID, index, LoreCategory.GAMEPLAY_HINTS)
            .withStyle(style -> style.withColor(ChatFormatting.DARK_GRAY).withItalic(false));
    }

    public static List<Component> autoLore(String oID, Map<LoreCategory, Integer> counts)
    { return autoLore(oID, counts, ChatFormatting.GOLD); }

    public static List<Component> autoLore(String oID, Map<LoreCategory, Integer> counts, ChatFormatting flavorColor)
    {
        List<Component> toReturn = new ArrayList<>();
        int totalComponents = 0;

        for (LoreCategory cat : LoreCategory.values())
        {
            // getOrDefault prevents crashes if a category isn't specified in the map
            int count = counts.getOrDefault(cat, 0);

            if (count != 0)
            {
                for (int i = 0; i < count; i++)
                {
                    switch (cat)
                    {
                        case FLAVOR_TEXT -> toReturn.add(flavorText(oID, i, flavorColor));
                        case GAMEPLAY_HINTS -> toReturn.add(hint(oID, i));
                        default -> toReturn.add(lore(oID, i, cat));
                    }

                    totalComponents++;
                }
            }
        }

        if (totalComponents != 0) { SpiroMod.LOGGER.info("AUTOLORE FOR '{}' CREATED {} COMPONENTS",
                oID.toLowerCase(), totalComponents); }
        return toReturn;
    }

    public static List<Component> hints(String oID, int count)
    { return autoLore(oID, Map.of(LoreCategory.GAMEPLAY_HINTS, count)); }
}
