package net.chemthunder.foxglove.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.chemthunder.foxglove.impl.index.magic.FoxgloveCantripEffects;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @WrapMethod(method = "getMovementSpeed()F")
    private float foxglove$weighted(Operation<Float> original) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (MagicUtils.getCantripComponent(living).effect() == FoxgloveCantripEffects.WEIGHTED) {
            return original.call() - living.getArmor();
        }

        return original.call();
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void foxglove$blighted(CallbackInfo ci) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (MagicUtils.getCantripComponent(living).effect().equals(FoxgloveCantripEffects.BLIGHTED)) {
            if (MagicUtils.hasSkyAbove(living, living.getWorld())) {
                if (!living.isOnFire()) {
                    if (living.getWorld().isDay()) {
                        living.setOnFireFor(8.0f);
                    }
                }
            }
        }
    }
}
