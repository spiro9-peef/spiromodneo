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
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodData.class)
public class FoodDataMixin
{
    @Shadow private int foodLevel;
    @Shadow private float exhaustionLevel;
    @Shadow private float saturationLevel;

    @Shadow private int tickTimer;

    @WrapMethod(method = "tick")
    public void onTick(Player player, Operation<Void> original) {
        boolean customHungerActive = !player.level().getGameRules().getBoolean(Registrar.PEACEFUL_IS_PEACEFUL);
        boolean isPeaceful = player.level().getDifficulty() == Difficulty.PEACEFUL;

        if (customHungerActive && isPeaceful)
        {
            // Manually drive the food stats since vanilla skips tick logic in Peaceful
            // Add a tiny bit of passive exhaustion over time so it feeds into standard food loss
            this.exhaustionLevel += 0.01F;

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
                // Give a grace period but still maintain some exhaustion, unless the value is below 1.0.
                this.exhaustionLevel = (this.exhaustionLevel > 1.0F) ? Math.max(1.0F, this.exhaustionLevel - 0.5F) :
                                        this.exhaustionLevel;
                float maxHealth = player.getMaxHealth();
                float currHealth = player.getHealth();

                // Draw immediately from saturation pool if it is present.
                if (this.saturationLevel >= 1.0F && ((maxHealth - currHealth) >= 1.0F))
                {
                    this.saturationLevel -= 1.0F;
                    player.setHealth(Math.min(maxHealth, currHealth + 1.0F));
                }

                // Drop food level by one for every 2 units of health.
                if (this.foodLevel > 0 && ((maxHealth - currHealth) >= 1.0F))
                {
                    if (this.tickTimer % 2 == 0)
                    {
                        if (this.tickTimer % 4 == 0) { this.foodLevel -= 1; }
                        player.setHealth(Math.min(maxHealth, currHealth + 1.0F));
                    }
                }
            }
            return;
        }

        original.call(player);
    }
}
