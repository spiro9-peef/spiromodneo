package com.github.peeftube.spiromodneo.mixin;

import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.util.SpiroTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BushBlock.class)
public abstract class BushBlockMixin
{
    @ModifyReturnValue(method = "mayPlaceOn", at = @At(value = "RETURN"))
    protected boolean checkingIfCanPlaceOn(boolean original, BlockState state, BlockGetter level, BlockPos pos)
    { return original || state.is(Registrar.GRASS_TYPE.tags().getBlockTag()) ||
            state.is(Registrar.VITALIUM_TYPE.tags().getBlockTag()); }
}
