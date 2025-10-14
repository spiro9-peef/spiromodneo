package com.github.peeftube.spiromodneo.mixin;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity
{
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level)
    { super(entityType, level); }

    @WrapOperation(method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;setSaturation(F)V"))
    public void onSetSaturation(FoodData instance, float saturationLevel, Operation<Void> original)
    {
        if (!this.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL))
        {
            // Only on occasion should this function normally. Otherwise, do nothing.
            if (SpiroMod.RNG.nextFloat() <= 0.05) { original.call(instance, saturationLevel); }
        }
        else original.call(instance, saturationLevel);
    }

    @WrapOperation(method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;setFoodLevel(I)V"))
    public void onSetFoodLevel(FoodData instance, int foodLevel, Operation<Void> original)
    {
        if (!this.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL))
        {
            // Only on occasion should this function normally. Otherwise, do nothing.
            if (SpiroMod.RNG.nextFloat() <= 0.05) { original.call(instance, foodLevel); }
        }
        else original.call(instance, foodLevel);
    }

    @WrapOperation(method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"))
    public void onHeal(Player instance, float v, Operation<Void> original)
    {
        if (!this.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL))
        {
            // Heal on avg. at 50% of the normal rate.
            if (SpiroMod.RNG.nextFloat() <= 0.5) { original.call(instance, v); }
        }
        else original.call(instance, v);
    }
}