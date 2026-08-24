package com.github.peeftube.spiromodneo.datagen.modules.lang.util.helpers;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.datagen.modules.lang.util.helpers.tooltips.LoreCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TooltipUtils
{
    public static String translateLoreKey(String oID, int index, LoreCategory category)
    { return String.format("%s.%s.tooltip.lore.%s.%d", SpiroMod.MOD_ID, oID, category.getKey(), index); }

    public static MutableComponent lore(String oID, int index, LoreCategory category)
    { return Component.translatable(translateLoreKey(oID, index, category)); }
}
