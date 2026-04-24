package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @WrapMethod(method = "getMovementSpeed()F")
    private float foxglove$weighted(Operation<Float> original) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.WEIGHTED)) {
            return original.call() - living.getArmor();
        }

        return original.call();
    }
}
