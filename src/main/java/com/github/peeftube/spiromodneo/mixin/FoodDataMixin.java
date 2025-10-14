package com.github.peeftube.spiromodneo.mixin;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodData.class)
public class FoodDataMixin
{
    @Shadow private int foodLevel;
    @Shadow private float exhaustionLevel;
    @Shadow private float saturationLevel;

    @WrapMethod(method = "tick")
    public void onTick(Player player, Operation<Void> original)
    {
        if (!player.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL) &&
            player.level().getDifficulty() == Difficulty.PEACEFUL &&
                this.saturationLevel <= 0.0F && this.exhaustionLevel <= 0.0F)
        { this.foodLevel = Math.max(this.foodLevel - 1, 0); }
        original.call(player);
    }
}
