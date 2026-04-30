package net.chemthunder.foxglove.mixin.handy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @WrapMethod(method = "getMaxUseTime")
    private int foxglove$handy(ItemStack stack, LivingEntity user, Operation<Integer> original) {
        return MagicUtils.getCantripComponent(user).effect().equals(FoxgloveCantripEffects.HANDY) ? original.call(stack, user) / 2 : original.call(stack, user);
    }
}
