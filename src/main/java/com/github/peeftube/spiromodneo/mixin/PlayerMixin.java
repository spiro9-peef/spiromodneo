package com.github.peeftube.spiromodneo.mixin;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity
{
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level)
    { super(entityType, level); }

    @WrapOperation(method = "aiStep",
            at = @At(value ="INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    public boolean onCheckPeaceful(GameRules instance, GameRules.Key<GameRules.BooleanValue> key, Operation<Boolean> original)
    {
        boolean originalReturn = original.call(instance, key);
        boolean ourRule = this.level().getServer() != null
            ? this.level().getServer().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL)
            : this.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL);

        return originalReturn && ourRule;
    }
}