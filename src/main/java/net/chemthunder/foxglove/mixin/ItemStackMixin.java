package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @WrapMethod(method = "getMaxUseTime")
    private int foxglove$handy(LivingEntity user, Operation<Integer> original) {
        return MagicUtils.getCantripComponent(user).effect().equals(FoxgloveCantripEffects.HANDY) ? original.call(user) / 2 : original.call(user);
    }
}
