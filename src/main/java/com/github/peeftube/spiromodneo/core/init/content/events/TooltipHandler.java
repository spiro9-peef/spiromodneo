package com.github.peeftube.spiromodneo.core.init.content.events;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.MOID;
import com.github.peeftube.spiromodneo.datagen.modules.lang.util.TooltipUtils;
import com.github.peeftube.spiromodneo.datagen.modules.lang.util.tooltips.LoreCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Map;

import static com.github.peeftube.spiromodneo.core.MOID_Utility.getMOID;

@EventBusSubscriber(modid = SpiroMod.MOD_ID)
public class TooltipHandler
{
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        int pI = event.getToolTip().size() - 3; // Placement Index

        // Stick
        if (stack.is(Items.STICK))
        {
            Map<LoreCategory, Integer> lore = Map.of(
                    LoreCategory.GAMEPLAY_HINTS, 1
            );

            event.getToolTip().addAll(pI, TooltipUtils.autoLore(getMOID(MOID.STICK), lore));
        }

        // Flint
        if (stack.is(Items.FLINT))
        {
            Map<LoreCategory, Integer> lore = Map.of(
                    LoreCategory.FLAVOR_TEXT, 1,
                    LoreCategory.GAMEPLAY_HINTS, 1
            );

            event.getToolTip().addAll(pI, TooltipUtils.autoLore(getMOID(MOID.FLINT), lore));
        }

        // String
        if (stack.is(Items.STRING))
        {
            Map<LoreCategory, Integer> lore = Map.of(
                    LoreCategory.FLAVOR_TEXT, 1,
                    LoreCategory.GAMEPLAY_HINTS, 2
            );

            event.getToolTip().addAll(pI, TooltipUtils.autoLore(getMOID(MOID.STRING), lore));
        }
    }
}
