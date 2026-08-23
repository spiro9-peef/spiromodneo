package com.github.peeftube.spiromodneo.mixin;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin
{
    @Shadow private int foodLevel;
    @Shadow private float exhaustionLevel;
    @Shadow private float saturationLevel;
    @Shadow private int tickTimer;
    @Shadow private int lastFoodLevel;

    @WrapMethod(method = "tick")
    public void onTick(Player player, Operation<Void> original) {
        boolean customHungerActive = !player.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL);
        boolean isPeaceful = player.level().getDifficulty() == Difficulty.PEACEFUL;

        if (customHungerActive && isPeaceful)
        {
            this.lastFoodLevel = this.foodLevel;

            // Manually drive the food stats since vanilla skips tick logic in Peaceful
            // Add a tiny bit of passive exhaustion over time so it feeds into standard food loss
            // this.exhaustionLevel += 0.0005F;

            if (this.exhaustionLevel > 4.0F)
            {
                this.exhaustionLevel = 0.0F;
                if (this.saturationLevel > 0.0F)
                { this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F); }
                else if (this.foodLevel > 0)
                { this.foodLevel = Math.max(this.foodLevel - 1, 0); }
            }

            // Skip the original call in Peaceful so vanilla doesn't override our manual drain,
            // but still handle health regen/starvation logic if food hits zero!
            if (player.isHurt() && player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION))
            {
                if (this.tickTimer % 10 == 0)
                {
                    // Give a grace period but still maintain some exhaustion, unless the value is below 1.0.
                    this.exhaustionLevel = (this.exhaustionLevel > 1.0F) ? Math.max(1.0F, this.exhaustionLevel - 0.5F) :
                            this.exhaustionLevel;
                    float maxHealth  = player.getMaxHealth();
                    float currHealth = player.getHealth();

                    // Draw immediately from saturation pool if it is present.
                    if (this.saturationLevel >= 1.0F && ((maxHealth - currHealth) >= 1.0F))
                    {
                        this.saturationLevel -= 1.0F;
                        player.setHealth(Math.min(maxHealth, currHealth + 1.0F));

                        // Just to be absolutely safe!
                        currHealth = player.getHealth();
                    }

                    // Drop food level by one for every 2 units of health.
                    if (this.saturationLevel < 1.0F && this.foodLevel > 0 && ((maxHealth - currHealth) >= 1.0F))
                    {
                        if (this.tickTimer % 20 == 0)
                        { this.foodLevel -= 1; }
                        player.setHealth(Math.min(maxHealth, currHealth + 1.0F));
                    }

                    // Starvation!
                    else if (this.foodLevel <= 0)
                    {
                        ++this.tickTimer;
                        if (this.tickTimer >= 80)
                        {
                            if (player.getHealth() > 10.0F)
                            { player.hurt(player.damageSources().starve(), 1.0F); }
                            this.tickTimer = 0;
                        }
                    }
                }
            }

            if (player instanceof ServerPlayer serverPlayer)
            {
                // Parameters typically match: (float health, int foodLevel, float saturationLevel)
                serverPlayer.connection.send(new net.minecraft.network.protocol.game.ClientboundSetHealthPacket(
                        serverPlayer.getHealth(),
                        this.foodLevel,
                        this.saturationLevel));
            }

            return;
        }

        original.call(player);
    }
}
